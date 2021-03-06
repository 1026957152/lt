package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.PublicationResp;
import com.lt.dom.error.No_voucher_suitable_for_publicationException;
import com.lt.dom.notification.EventHandler;
import com.lt.dom.notification.VouncherPublishedVo;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PublicationPojo;
import com.lt.dom.otcReq.PublicationSearchPojo;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.repository.PublicationEntryRepository;
import com.lt.dom.repository.PublicationRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.repository.VoucherRepository;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PublicationServiceImpl {

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationEntryRepository publicationEntryRepository;


    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private EventHandler eventHandler;

    public PublicationResp CreatePublication(Campaign campaign, PublicationPojo publicationPojo) {

        Optional<Voucher> optionalVoucher = voucherRepository.findFirstByCampaign(campaign.getId());



        if(optionalVoucher.isPresent()){
            Voucher voucher = optionalVoucher.get();

            Publication publication = new Publication();
            publication = publicationRepository.save(publication);

            PublicationEntry publicationEntry = new PublicationEntry();
            publicationEntry.setPublicationId(publication.getId());
            publicationEntry.setToWhoType(publicationPojo.getType());

            Optional<Supplier> supplierOptional = null;
            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.business)){

                supplierOptional = supplierRepository.findById(publicationPojo.getSupplier());

                publicationEntry.setToWho(supplierOptional.get().getId());

            }
            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.customer)){
                publicationEntry.setToWho(publicationPojo.getUser());
            }
            publicationEntry.setPublished_at(LocalDate.now());
            publicationEntry.setCampaign_id(campaign.getId());
            publicationEntry.setVoucherId(optionalVoucher.get().getId());



            publicationEntry = publicationEntryRepository.save(publicationEntry);



            voucher.setPublished(true);
            voucher.setAdditionalInfo("");
            voucher = voucherRepository.save(voucher);


            VouncherPublishedVo orderPaidVo = new VouncherPublishedVo();
            orderPaidVo.setPublicationEntry(publicationEntry);
            orderPaidVo.setPublication(publication);
            orderPaidVo.setVoucher(voucher);
            orderPaidVo.setPublicationToType(publicationEntry.getToWhoType());

            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.business)){
                orderPaidVo.setSupplier(supplierOptional.get());
            }
            eventHandler.voucher_published(orderPaidVo);


            return PublicationResp.from(publicationEntry);
        }
        throw new RuntimeException();

    }

    public List<PublicationResp> listPublication(PublicationSearchPojo pojo) {
        return null;
    }




    public PublicationResp publish(List<Voucher> voucherList,User user) {   // ???????????????
        Publication publication = new Publication();
        publication.setCount(voucherList.size());
        publication = publicationRepository.save(publication);

        Publication finalPublication = publication;
        List<PublicationEntry> publicationEntryList = voucherList.stream().map(x->{
            PublicationEntry publicationEntry = new PublicationEntry();
            publicationEntry.setPublicationId(finalPublication.getId());
            publicationEntry.setToWho(1);
            publicationEntry.setPublished_at(LocalDate.now());
            return publicationEntry;


        }).collect(Collectors.toList());

        publicationEntryList = publicationEntryRepository.saveAll(publicationEntryList);
        return new PublicationResp();
    }




    private List<Voucher> lockClain(long campaign,int count){
        Page<Voucher> page = voucherRepository.findAll(
                PageRequest.of(0, count, Sort.by(Sort.Direction.ASC, "id")));


        if(page.getSize() == 0 || count > page.getSize() ){
            throw new No_voucher_suitable_for_publicationException(campaign,Reservation.class.getSimpleName(),"?????????????????????????????????????????????"+count+"????????????"+page.getSize());
        }



        List<Voucher> voucher = voucherRepository.saveAll(page.stream().map(x->{
            x.setPublished(true);
            x.setAdditionalInfo("");
            return x;
        }).collect(Collectors.toList()));

        return voucher;
    }



    public List<Pair<Long,Voucher>> bulkPublish(Supplier supplier, List<Long> travelers, long campaign) {



        List<Voucher> list = lockClain(campaign,travelers.size());
        Publication publication = new Publication();
        publication.setCount(list.size());
        publication.setPublished_at(LocalDateTime.now());
        publication.setCampaign(campaign);
        publication = publicationRepository.save(publication);



        Publication finalPublication = publication;
        List<PublicationEntry> publicationList = list.stream().map(x->{
            PublicationEntry publicationEntry = new PublicationEntry();
            publicationEntry.setPublicationId(finalPublication.getId());
            publicationEntry.setToWhoType(EnumPublicationObjectType.business);
            publicationEntry.setToWho(supplier.getId());
            publicationEntry.setPublished_at(LocalDate.now());
            publicationEntry.setCampaign_id(campaign);
            publicationEntry.setVoucherId(x.getId());
            return publicationEntry;

        }).collect(Collectors.toList());

        publicationList = publicationEntryRepository.saveAll(publicationList);


        return IntStream
                .range(0, Math.min(travelers.size(), list.size()))
                .mapToObj(i -> Pair.with(travelers.get(i),list.get(i))).collect(Collectors.toList());


    }

}

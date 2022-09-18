package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.PublicationResp;
import com.lt.dom.error.No_voucher_suitable_for_publicationException;
import com.lt.dom.notification.EventHandler;
import com.lt.dom.notification.VouncherPublishedVo;
import com.lt.dom.notification.event.OnVoucherPublishedEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PublicationPojo;
import com.lt.dom.otcReq.PublicationSearchPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.ProductSubVo;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.vo.VoucherPublicationPaymentVo;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private CampaignServiceImpl campaignService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    ApplicationEventPublisher eventPublisher;


    public Quartet<PublicationEntry,Voucher,Campaign,PublishTowhoVo> CreatePublication(Campaign campaign, PublicationPojo publicationPojo, Session session, PublishTowhoVo publishTowhoVo, VoucherPublicationPaymentVo voucherPublicationPaymentVo) {

      //  asset(publishTowhoVo.getToWhoTyp());


        Voucher voucher = campaignService.retain(campaign);



            Publication publication = new Publication();
            publication = publicationRepository.save(publication);





            PublicationEntry publicationEntry = publish(publication,campaign,voucher,publishTowhoVo,voucherPublicationPaymentVo,session);

            publicationEntry = publicationEntryRepository.save(publicationEntry);


            session.setToWho(publicationEntry.getId());
            session.setTo(EnumSessionFor.publication_entry);
            sessionRepository.save(session);



            voucher.setPublished(true);
            voucher.setAdditionalInfo("");
            voucher.setExpiry_datetime(LocalDateTime.now().plusDays(campaign.getExpiry_days()));
            voucher.setExpiration_date(LocalDateTime.now().plusDays(campaign.getExpiry_days()));

            voucher.setStart_date(LocalDateTime.now());
            voucher.setStatus(EnumVoucherStatus.Published);
            voucher.setQuantity(1l);
            voucher.setActive(true);
            voucher.setPublishToType(publishTowhoVo.getToWhoTyp());
            voucher.setPublishTo(publishTowhoVo.getToWho());

            voucher = voucherRepository.save(voucher);




            VouncherPublishedVo vouncherPublishedVo = new VouncherPublishedVo();
            vouncherPublishedVo.setPublicationEntry(publicationEntry);
            vouncherPublishedVo.setPublication(publication);
            vouncherPublishedVo.setVoucher(voucher);
            vouncherPublishedVo.setPublicationToType(publicationEntry.getToWhoType());

            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.business)){
                vouncherPublishedVo.setSupplier(publishTowhoVo.getSupplier());
            }




        campaign.setTotal_published(campaign.getTotal_published()+1);
            if(campaign.getTotal_published() >= campaign.getVoucher_count()){
                campaign.setActive(false);
            }
        campaignService.updateSummary(campaign,EnumEvents.voucher$published);


        eventPublisher.publishEvent(new OnVoucherPublishedEvent(new User(),
                null, EnumEvents.voucher$published));

            return Quartet.with(publicationEntry,voucher,campaign,publishTowhoVo);



    }




    public PublicationEntry publish(Publication publication,Campaign campaign ,
                                   Voucher voucher,PublishTowhoVo publishTowhoVo,
                                   VoucherPublicationPaymentVo voucherPublicationPaymentVo,Session session) {   // 购买的时候


        PublicationEntry publicationEntry = new PublicationEntry();
        publicationEntry.setPublication(publication.getId());



        publicationEntry.setToWhoType(publishTowhoVo.getToWhoTyp());



        if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.business)){
            publicationEntry.setToWho(publishTowhoVo.getSupplier().getId());
        }
        if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.customer)){
            publicationEntry.setToWho(publishTowhoVo.getUser().getId());
        }
        if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.traveler)){
            publicationEntry.setToWho(publishTowhoVo.getTraveler().getId());

        }

        publicationEntry.setPublished_at(LocalDateTime.now());
        publicationEntry.setCampaign(campaign.getId());
        publicationEntry.setVoucher(voucher.getId());
        publicationEntry.setAssociatedType(EnumAssociatedType.none);
        publicationEntry.setFree(voucherPublicationPaymentVo.getFree());
        publicationEntry.setPaied(voucherPublicationPaymentVo.getPaied());
        publicationEntry.setSession_ip(session.getIp());

        if(voucherPublicationPaymentVo.getPaied()){
            publicationEntry.setCharge(voucherPublicationPaymentVo.getCharge().getId());
        }

        return publicationEntry;
    }




    private List<Voucher> lockClain(long campaign,int count){
        Page<Voucher> page = voucherRepository.findAll(
                PageRequest.of(0, count, Sort.by(Sort.Direction.ASC, "id")));

        if(page.getSize() == 0 || count > page.getSize() ){
            throw new No_voucher_suitable_for_publicationException(campaign,Reservation.class.getSimpleName(),"没有足够的可用的券可以领，需要"+count+"但找到了"+page.getSize());
        }



        List<Voucher> voucher = voucherRepository.saveAll(page.stream().map(x->{
            x.setPublished(true);
            x.setAdditionalInfo("");
            return x;
        }).collect(Collectors.toList()));

        return voucher;
    }



    public void bulkPublish(Supplier supplier_, List<Traveler> travelers, Campaign campaign, EnumAssociatedType tour_booking, long id, ProductSubVo productSubVo, Session session) {



        List<Voucher> list = lockClain(campaign.getId(),travelers.size());
        Publication publication = new Publication();
        publication.setCount(list.size());
        publication.setPublished_at(LocalDateTime.now());
        publication.setCampaign(campaign.getId());
        publication = publicationRepository.save(publication);



        Publication finalPublication = publication;




        List<PublicationEntry> publicationList = IntStream.range(0, list.size()).mapToObj(x->{
            Voucher voucher=     list.get(x);
            Traveler traveler=     travelers.get(x);

            PublishTowhoVo publishTowhoVo = new PublishTowhoVo();

            publishTowhoVo.setToWhoTyp(EnumPublicationObjectType.traveler);
            publishTowhoVo.setTraveler(traveler);
            publishTowhoVo.setToWho(publishTowhoVo.getTraveler().getId());

            VoucherPublicationPaymentVo voucherPublicationPaymentVo = new VoucherPublicationPaymentVo();

            voucherPublicationPaymentVo.setFree(true);
            voucherPublicationPaymentVo.setPaied(false);

            PublicationEntry publicationEntry = publish(finalPublication,campaign,voucher,publishTowhoVo,voucherPublicationPaymentVo,session);

            publicationEntry.setToWhoType(EnumPublicationObjectType.business);
            publicationEntry.setToWho(1);
            publicationEntry.setAssociatedId(id);
            publicationEntry.setAssociatedType(tour_booking);
            return publicationEntry;
        })  .collect(Collectors.toList());


        publicationList = publicationEntryRepository.saveAll(publicationList);




        campaign.setTotal_published(campaign.getTotal_published()+publicationList.stream().count());
        if(campaign.getTotal_published() >= campaign.getVoucher_count()){
            campaign.setActive(false);
        }
        campaignService.updateSummary(campaign,EnumEvents.voucher$bulk_published);


        eventPublisher.publishEvent(new OnVoucherPublishedEvent(new User(),
                null, EnumEvents.voucher$bulk_published));


/*        List<Pair<Long,Voucher>> v =  IntStream
                .range(0, Math.min(travelers.size(), list.size()))
                .mapToObj(i -> Pair.with(travelers.get(i),list.get(i))).collect(Collectors.toList());*/


    }

    public Page<PublicationEntry> pagePublication(User user, PublicationSearchPojo pojo, Pageable pageable) {
        Page<PublicationEntry> publicationEntryList = publicationEntryRepository.findByToWhoTypeAndToWho(EnumPublicationObjectType.customer,user.getId(),pageable);

        return publicationEntryList;
    }

    public List<PublicationEntry> list(EnumAssociatedType tour_booking, long code) {

        List<PublicationEntry> publicationEntryList = publicationEntryRepository.findByAssociatedTypeAndAssociatedId(EnumAssociatedType.tour_booking,code);

        return publicationEntryList;
    }

    public void refundPublication(long voucher_id) {

        Optional<Voucher> voucherOptional = voucherRepository.findById(voucher_id);
        Voucher voucher = voucherOptional.get();

        voucher.setStatus(EnumVoucherStatus.Unavailable);
        voucherRepository.save(voucher);

       // Optional<PublicationEntry> optionalPublicationEntry = publicationEntryRepository.findByVoucher(voucher_id);
        List<PublicationEntry> publicationEntryList = publicationEntryRepository.findByVoucher(voucher_id);

        PublicationEntry publicationEntry = publicationEntryList.get(0);
    }
}

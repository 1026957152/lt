package com.lt.dom;


import com.lt.dom.config.LtConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PassCreatePojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.AssetServiceImpl;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.util.AtomicSequenceGenerator;
import com.lt.dom.util.CodeConfig;
import com.lt.dom.util.VoucherCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class PassAsyncServiceImpl {

    @Autowired
    private PassRepository passRepository;

    @Autowired
    private BulkIssuanceCardRequestRepository bulkIssuanceCardRequestRepository;
    @Autowired
    private IdGenServiceImpl idGenService;
    @Autowired
    private PassProductRepository passProductRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetServiceImpl assetService;

    @Async
    @Transactional
    public void 异步新建(CodeConfig config, BulkIssuanceCardRequest bulkIssuance) {


        BulkIssuanceCardRequest  finalBulkIssuance= bulkIssuance;
        List<Pass> vouchers = LongStream.range(0,bulkIssuance.getNumber_of_cards()).mapToObj(x->{

            return create(finalBulkIssuance);

        }).collect(Collectors.toList());


        vouchers = passRepository.saveAll(vouchers);





/*        List<Asset> assets = assetService.newQr(vouchers);

        assets = assetRepository.saveAll(assets);*/





        //Optional<Campaign> optional = campaignRepository.findById(campaign.getId());

/*
        System.out.println(finalCampaign.getCode()+ finalCampaign.getName()+"我这里异步存储一下 Execute method asynchronously. "
                + Thread.currentThread().getName());*/
        bulkIssuance.setStatus(EnumBulkIssuanceCardRequestStatus.SUBMITTED);
        bulkIssuance = bulkIssuanceCardRequestRepository.save(bulkIssuance);
    }

    AtomicSequenceGenerator atomicSequenceGenerator = new AtomicSequenceGenerator(1);


    public Pass create(BulkIssuanceCardRequest bulkIssuanceCardRequest) {


        Optional<PassProduct> productOptional = passProductRepository.findByProduct(bulkIssuanceCardRequest.getCard_product_id());

        if(productOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到 card 产品");
        }
        PassProduct passProduct = productOptional.get();


        Pass pass = new Pass();
        pass.setSupplier(bulkIssuanceCardRequest.getSupplier());

        pass.setCode(idGenService.passCode());
        pass.setNumber(atomicSequenceGenerator.getNextString());
        pass.setProduct(passProduct.getProduct());
        pass.setBulkIssuanceId(bulkIssuanceCardRequest.getId());
        pass.setProductPass(passProduct.getId());
        pass.setDuration(passProduct.getDuration());
        pass.setDurationUnit(passProduct.getDurationUnit());
        pass.setPersonal(EnumCardPersonalized.NonPersonalized);
        pass.setFulfillment_status(EnumCardFullfullmentStatus.Created);

        pass.setLabel(passProduct.getLabel());

        pass.setType(EnumCardType.GOOD_FUNDS_CREDIT);
        pass.setFormFactor(EnumFormFactorType.physical);
        pass.setActive(false);
        pass.setStatus(EnumCardStatus.inactive);
        pass.setShipping_statis(EnumPassShippingStatus.delivered);

        if(passProduct.getActiveExpiryDurationUnit().equals(EnumPassDorationUnit.months)){
            pass.setMaxActivationDate(LocalDateTime.now().plusMonths(passProduct.getActiveExpiryDuration()));
        }
        if(passProduct.getActiveExpiryDurationUnit().equals(EnumPassDorationUnit.days)){
            pass.setMaxActivationDate(LocalDateTime.now().plusDays(passProduct.getActiveExpiryDuration()));
        }




        return pass;
    }





}

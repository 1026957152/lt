package com.lt.dom.notification;

import com.google.common.eventbus.Subscribe;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumPublicationObjectType;
import com.lt.dom.otcenum.EnumValidatorRedemExtent;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.serviceOtc.RoyaltySettlementServiceImpl;
import com.lt.dom.serviceOtc.ValidatorServiceImpl;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventListener {
/*    @Autowired
    private AbExpBucketUserMapper abExpBucketUserMapper;

    @Autowired
    private AbExpBucketUserCountMapper abExpBucketUserCountMapper;*/


    @Autowired
    private RoyaltySettlementServiceImpl royaltySettlementService;

   // @Autowired
   // private PublicationServiceImpl publicationService;

    @Autowired
    private ValidatorServiceImpl validatorService;

    @Subscribe
    public void onMessageEvent(OrderPaidVo expBucketUserVo) { //订单支付了



        royaltySettlementService.订单支付后进行分润记录(expBucketUserVo.getComponents()); // 分容


        List<Triplet<Voucher, ComponentVounch, AccessValidator>> vouchers = expBucketUserVo.getComponents().stream().map(x->{


            ComponentVounch componentVounch = new ComponentVounch();
            componentVounch.setComponentRightId(x.getComponentRightId());

            Voucher voucher = new Voucher();
            voucher.setType(EnumVoucherType.RIGHT_VOUCHER);
            voucher.setQuantity(x.getUnit_off());
            voucher.setComponentVounch(componentVounch);
            voucher.setActive(true);

            voucher.setStart_date(LocalDateTime.now().now());
            voucher.setExpiration_date(LocalDateTime.now());


            Triplet<Voucher, ComponentVounch, AccessValidator> triplet = Triplet.with(voucher, componentVounch, x.getAccessValidator());
            return triplet;

        }).collect(Collectors.toList());


     //   publicationService.publish(vouchers.stream().map(x->x.getValue0()).collect(Collectors.toList()), expBucketUserVo.getUser());  // 给改人发送权益券。

        //直接抢购权益券。





       // 首先找到一组核销机器。 然后下发。  订单关联的核销机器。
       // AddPersons;///



        vouchers.stream().forEach(x->{

            if(x.getValue2().getExtend().equals(EnumValidatorRedemExtent.权益券)){

                validatorService.push(x.getValue2().getValidators(),x.getValue0());
            }



        });




       // 这里要下发给人脸识别机器





/*        try {
            //CommonResponse<Integer> response = expBucketUserService.insert(expBucketUserVo);
            ExperimentBucketUser experimentBucketUser = ExpBucketUserResponseConverter.INSTANCE.convertFrom(expBucketUserVo);
            Integer i = abExpBucketUserMapper.insert(experimentBucketUser);
            log.info("sync insert into ExperimentBucketUser,param={},result={}",expBucketUserVo,i);
        }catch (Exception e){
            log.error("sync insert into ExperimentBucketUser error,param={},error={}",expBucketUserVo,e);
        }*/
    }



/*    @Subscribe
    public void onMessageEvent(ExperimentBucketUserCountVo expBucketUserCountVo) {
        try {
            //CommonResponse<Integer> response = expBucketUserCountService.incrementCountBy(experimentBucketUserCountVo);
            Integer i=  abExpBucketUserCountMapper.incrementCountBy(expBucketUserCountVo.getExperimentId(),expBucketUserCountVo.getBucketId());
            if(i<1){
                //说明没有初始化
                ExperimentBucketUserCount expBucketUserCount = ExpBucketUserCountResponseConverter.INSTANCE
                        .convertFrom(expBucketUserCountVo);
                i= abExpBucketUserCountMapper.insert(expBucketUserCount);
            }

            log.info("sync insert into ExperimentBucketUserCount,param={},result={}",expBucketUserCountVo,i);
        }catch (Exception e){
            log.error("sync insert into ExperimentBucketUserCountCount error,param={},error={}",expBucketUserCountVo,e);
        }
    }*/



    @Subscribe
    public void onMessageEvent(VouncherPublishedVo vouncherPublishedVo) { //订单支付了

        Publication publication = vouncherPublishedVo.getPublication();
        PublicationEntry publicationEntry = vouncherPublishedVo.getPublicationEntry();
        Voucher voucher = vouncherPublishedVo.getVoucher();


        if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.business)){
            Supplier supplier = vouncherPublishedVo.getSupplier();

        }
        System.out.println("=================================== 领券了啊啊啊 啊啊");
    }



}
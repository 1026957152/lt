package com.lt.dom.serviceOtc;


import com.google.protobuf.InvalidProtocolBufferException;
import com.lt.dom.OctResp.RedemptionTryResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.AccessValidatorPojo;
import com.lt.dom.otcReq.ComponentRightPojo;
import com.lt.dom.otcReq.ProductPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.util.SecurityUtils;
import com.lt.dom.vo.*;
import org.hashids.Hashids;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComponentRightServiceImpl {
    @Autowired
    private ValidatorRepository validatorRepository;

    @Autowired
    private AccessValidatorRepository accessValidatorRepository;

    @Autowired
    private ComponentRightRepository componentRightRepository;
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private CryptoServiceImpl cryptoService;

    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;

    @Autowired
    private ComponentVounchRepository componentVounchRepository;
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;

    @Autowired
    private BalanceServiceImpl balanceService;

    @Autowired
    private RedemptionServiceImpl redemptionService;

    @Autowired
    private BillServiceImpl billService;


    @Autowired
    private RoyaltyServiceImp royaltyServiceImp;

    @Autowired
    private SupplierServiceImp supplierServiceImp;


    @Autowired
    private LineItemRepository lineItemRepository;



    public ComponentRight createComponentRight(Supplier supplier, ComponentRightPojo pojo,
                                               Subscription subscription, ComponentRight componentRightSeller) {


        ComponentRight componentRight = new ComponentRight();

        componentRight.setSupplier(supplier.getId());
        componentRight.setCode(idGenService.componentRightCode());
        componentRight.setName(pojo.getName());
        componentRight.setLong_desc(pojo.getLong_desc());
        componentRight.setName_long(pojo.getName_long());
        componentRight.setPrivate_(pojo.getPrivate_());
        componentRight.setDuration(pojo.getDuration());
        componentRight.setStatus(EnumComponentStatus.Created);
        if(componentRight.getDuration().equals(EnumDuration.repeating)){
            componentRight.setLimit_quantity(pojo.getQuantity());
        }
        if(componentRight.getDuration().equals(EnumDuration.once)){
            componentRight.setLimit_quantity(1l);
        }
        if(componentRight.getDuration().equals(EnumDuration.forever)){
            componentRight.setLimit_quantity(Long.MAX_VALUE);
        }

        Assert.notNull(subscription.getId(), "系统已有 componment的 Subscription 不能为空，当前空，新建 Usage 失败");
        Assert.notNull(subscription.getRatePlan(), "系统已有 componment RatePlan 不能为空，当前空，新建 Usage 失败");


        componentRight.setSource(EnumProductComponentSource.partner);
        componentRight.setRelateRight(componentRightSeller.getId());
        componentRight.setRelateRightCode(componentRightSeller.getCode());
        componentRight.setRatePlan(subscription.getRatePlan());
        componentRight.setSubscription(subscription.getId());

        componentRight.setPrivacy_level(pojo.getPrivacy_level());
        componentRight = componentRightRepository.save(componentRight);
        return componentRight;
    }




    public ComponentRight createComponentRight(Supplier supplier, ComponentRightPojo pojo) {


        ComponentRight componentRight = new ComponentRight();

        componentRight.setSupplier(supplier.getId());
        componentRight.setCode(idGenService.componentRightCode());
        componentRight.setName(pojo.getName());
        componentRight.setLong_desc(pojo.getLong_desc());
        componentRight.setName_long(pojo.getName_long());
        componentRight.setPrivate_(pojo.getPrivate_());
        componentRight.setDuration(pojo.getDuration());
        componentRight.setStatus(EnumComponentStatus.Created);
        if(componentRight.getDuration().equals(EnumDuration.repeating)){
            componentRight.setLimit_quantity(pojo.getQuantity());
        }
        if(componentRight.getDuration().equals(EnumDuration.once)){
            componentRight.setLimit_quantity(1l);
        }
        if(componentRight.getDuration().equals(EnumDuration.forever)){
            componentRight.setLimit_quantity(Long.MAX_VALUE);
        }
        componentRight.setSource(EnumProductComponentSource.own);
        componentRight.setPrivacy_level(pojo.getPrivacy_level());
        componentRight = componentRightRepository.save(componentRight);


/*
        ComponentRight finalComponentRight = componentRight;
        List<AccessValidator> componentRights = pojo.getValidators().stream().map(x->{
            AccessValidator accessValidator = new AccessValidator();
            accessValidator.setValidatorId(x.getId());
            accessValidator.setExtend(EnumValidatorRedemExtent.特定权益券);
            accessValidator.setRalativeId(finalComponentRight.getId());
            return accessValidator;
        }).collect(Collectors.toList());

        accessValidatorRepository.saveAll(componentRights);
*/


        return componentRight;
    }

    public AccessValidator addAccessValidator(ComponentRight componentRight, AccessValidatorPojo pojo) {

        Optional<Validator_> validatorOptional = validatorRepository.findById(pojo.getValidatorId());




        AccessValidator probe = new AccessValidator();
        probe.setValidatorId(validatorOptional.get().getId());
        Example<AccessValidator> example = Example.of(probe);


        Optional<AccessValidator> optional = accessValidatorRepository.findOne(example);

        if(optional.isEmpty()){
            AccessValidator accessValidator = new AccessValidator();
            accessValidator.setValidatorId(validatorOptional.get().getId());
            accessValidator.setExtend(EnumValidatorRedemExtent.特定权益券);
            accessValidator.setRalativeId(componentRight.getId());
            accessValidator = accessValidatorRepository.save(accessValidator);
            return accessValidator;
        }

        throw new RuntimeException();
    }








    public List<ComponentVounch> createComponentVounch(Long id,List<Component> componentRightList, User user) {




        List<ComponentVounch> componentVounchList = componentRightList.stream().map(e->{

            ComponentVounch componentVounch = new ComponentVounch();
            componentVounch.setComponentRight(e.getComponentRightId());

            componentVounch.setCode(idGenService.componentVouncherCode());
            componentVounch.setComponent(e.getId());

            if(id != null){
                componentVounch.setReservation(id);
            }



            componentVounch.setLimit(e.getUnit_off());
            componentVounch.setRedeemed_quantity(0l);
            componentVounch.setDuration(e.getDuration());
            componentVounch.setUser(user.getId());
            componentVounch.setSupplier(e.getSupplier());
            componentVounch.setStatus(EnumComponentVoucherStatus.NotRedeemed);
            return componentVounch;
        }).collect(Collectors.toList());



        componentVounchList = componentVounchRepository.saveAll(componentVounchList);


        return componentVounchList;
    }


    public List<ComponentVounch> createComponentVounch(Reservation reservation, User user) {

        List<LineItem> lineItemList = lineItemRepository.findAllByBooking(reservation.getId());



     //   Attraction attraction = attractionRepository.findBy()

        List<Component> componentRightList = componentRepository.findAllByProductIn(lineItemList.stream().map(e->e.getProduct()).collect(Collectors.toList()));




        List<ComponentVounch> componentVounchList = createComponentVounch(reservation.getId(),componentRightList,user);

        return componentVounchList;
    }


    public void redeem(List<Triplet<ValidatedByTypeVo,User,ComponentVounch>> optionalPass) {


        List<Component> componentList = componentRepository.findAllById(optionalPass.stream().map(e->e.getValue2().getComponent()).collect(Collectors.toList()));

        Map<Long, Component> longComponentMap = componentList.stream().collect(Collectors.toMap(e->e.getId(),e->e));



        List<Triplet<ComponentVounch,Component,Integer>> componentVounchList = optionalPass.stream().map(pair-> {
            Component component = longComponentMap.get(pair.getValue2().getComponent());
            ComponentVounch e= pair.getValue2();
            if(e.getDuration().equals(EnumDuration.once)){
                e.setStatus(EnumComponentVoucherStatus.AlreadyRedeemed);

                e.setRedeemed_quantity(1l);
            }
            if(e.getDuration().equals(EnumDuration.repeating)){
                e.setRedeemed_quantity(e.getRedeemed_quantity()+1);
                if(e.getLimit() == e.getRedeemed_quantity()){
                    e.setStatus(EnumComponentVoucherStatus.AlreadyRedeemed);
                }else{
                    e.setStatus(EnumComponentVoucherStatus.PartialyRedeemed);
                }

            }


            //component.get

            return Triplet.with(e,component,1);

        }).collect(Collectors.toList());


        componentVounchRepository.saveAll(componentVounchList.stream().map(e->e.getValue0()).collect(Collectors.toList()));



        //
        List<RightRedemptionEntry> redemptionEntryList = optionalPass.stream().map(x->{
            RedemptionForCustomerVo redemptionForCustomerVo = new RedemptionForCustomerVo();
            User traveler用户 = x.getValue1();
            redemptionForCustomerVo.setId(traveler用户.getId());
            redemptionForCustomerVo.setRealName(traveler用户.getRealName());
            redemptionForCustomerVo.setCode(traveler用户.getCode());

            return redemptionService.redeemRightForeach(new Redemption(),x.getValue0(),Optional.of(redemptionForCustomerVo),x.getValue2());
        }).collect(Collectors.toList());

        redemptionEntryList = rightRedemptionEntryRepository.saveAll(redemptionEntryList);

     //   redemptionService.redeemRight(optionalPass);

        billService.billUpdate(componentVounchList);

      //  royaltyServiceImp.getReferral();

    }



    public void assingtoProduct_backup(Product product ,List<ProductPojo.Royalty> royalties) {

        componentRepository.deleteAllByProduct(product.getId());

        List<ComponentRight> componentRights = componentRightRepository.findAllById(royalties.stream().map(e->e.getComponent_right()).collect(Collectors.toList()));


        if((componentRights.size() == 0)  || (componentRights.size() != royalties.size())){
            throw new BookNotFoundException(Enumfailures.resource_not_found,royalties.stream().map(e->e.getComponent_right()).collect(Collectors.toList())+ "找不到权益，或权益为空");
        }



        Map<Long,ComponentRight> longStringMap = componentRights.stream().collect(Collectors.toMap(e->e.getId(), e->e));

        List<Component> list =  royalties.stream().map(royalty->{

            ComponentRight right = longStringMap.get(royalty.getComponent_right());

            Component component = new Component();
            component.setUnit_off(right.getLimit_quantity());
            component.setDuration(right.getDuration());

            component.setComponentRight(right.getId());

            component.setProduct(product.getId());
            component.setSupplier(right.getSupplier());
            component.setRoyalty_mode(right.getRoyalty_mode());
            component.setRoyaltyPercent(right.getValue());
            component.setRoyaltyAmount(right.getValue());


            component.setRoyaltyAmount(right.getValue());
            component.setCollection_method(royalty.getCollection_method());

            component.setValidate_way(royalty.getValidate_way());


                component.setSource(right.getSource());


            if(right.getSource().equals(EnumProductComponentSource.partner)){
                Assert.notNull(right.getSubscription(), "系统已有 componment Right 不能为空，当前空，新建 component 失败");
                Assert.notNull(right.getRatePlan(), "系统已有 componment RatePlan 不能为空，当前空，新建 component 失败");
                component.setRatePlan(right.getRatePlan());


                component.setSubscription(right.getSubscription());
            }

            component.setRoyaltyPercent(royalty.getPercent());


            return component;
        }).collect(Collectors.toList());

        list = componentRepository.saveAll(list);


        royaltyRuleRepository.saveAll(list.stream().map(e->{

            Balance balance = balanceService.balance(e.getSupplier(),EnumUserType.business);

            RoyaltyRule royaltyRule = new RoyaltyRule();
            royaltyRule.setPercent(e.getRoyaltyPercent());
            royaltyRule.setComponentRight(e.getComponentRightId());
            royaltyRule.setComponent(e.getId());
            royaltyRule.setProductId(product.getId());
            royaltyRule.setSplitCode(product.getCode());
            royaltyRule.setRoyalty_mode(e.getRoyalty_mode());
            royaltyRule.setAmount(e.getRoyaltyAmount());
            royaltyRule.setSettle_account(balance.getId());
            royaltyRule.setSupplier(e.getSupplier());
            royaltyRule.setRoyaltyPercent(e.getRoyaltyPercent());
            royaltyRule.setCollection_method(e.getCollection_method());





            return royaltyRule;
        }).collect(Collectors.toList()));

    }


    public void assingtoProduct(Product product ,List<RoyaltyVo> royalties) {

       // componentRepository.deleteAllByProduct(product.getId());

        List<ComponentRight> componentRights = componentRightRepository.findAllById(royalties.stream()
                       // .filter(e->e.getId()== null)
                .map(e->e.getComponent_right()).collect(Collectors.toList()));


        if((componentRights.size() == 0)  || (componentRights.size() != royalties.size())){
            throw new BookNotFoundException(Enumfailures.resource_not_found,royalties.stream().map(e->e.getComponent_right()).collect(Collectors.toList())+ "找不到权益，或权益为空");
        }



        Map<Long,ComponentRight> longStringMap = componentRights.stream().collect(Collectors.toMap(e->e.getId(), e->e));

        Map<Long,Component> componentMap = componentRepository.findAllById(royalties.stream().map(e->e.getId()).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(e->e.getId(),e->e));


        List<Component> list =  royalties.stream().map(royalty->{
            System.out.println("------getLimit_quantity---------"+ royalty.getLimit_quantity());
            ComponentRight right = longStringMap.get(royalty.getComponent_right());

            Component component = componentMap.get(royalty.getId());
            if(component == null){

                component =new Component();
                component.setProduct(product.getId());
                component.setOriginalProduct(product.getId());
                component.setSupplier(right.getSupplier());

            }
            component.setComponentRight(right.getId());
            component.setSource(right.getSource());
            if(right.getSource().equals(EnumProductComponentSource.partner)){
                Assert.notNull(right.getSubscription(), "系统已有 componment Right 不能为空，当前空，新建 component 失败");
                Assert.notNull(right.getRatePlan(), "系统已有 componment RatePlan 不能为空，当前空，新建 component 失败");
                component.setRatePlan(right.getRatePlan());

                component.setSubscription(right.getSubscription());
            }



            component.setUnit_off(royalty.getLimit_quantity()==null?right.getLimit_quantity():royalty.getLimit_quantity());
            component.setDuration(right.getDuration());


            component.setRoyalty_mode(right.getRoyalty_mode());
            component.setRoyaltyPercent(right.getValue());
            component.setRoyaltyAmount(right.getValue());
            component.setRoyaltyAmount(right.getValue());
            component.setCollection_method(royalty.getCollection_method());
            component.setValidate_way(royalty.getValidate_way());
            component.setType(EnumComponentVoucherType.Right);

/*
            if(componentRight.getSupplier().equals(product.getSupplierId())){
            }else{
                component.setSource(EnumProductComponentSource.partner);
                //    component.setComponentRight(supplierServiceImp.get(x));
            }*/
            component.setRoyaltyPercent(royalty.getPercent());
            return component;
        }).collect(Collectors.toList());

        list = componentRepository.saveAll(list);






/*

        royaltyRuleRepository.saveAll(list.stream().map(e->{

            Balance balance = balanceService.balance(e.getSupplier(),EnumUserType.business);

            RoyaltyRule royaltyRule = new RoyaltyRule();
            royaltyRule.setPercent(e.getRoyaltyPercent());
            royaltyRule.setComponentRight(e.getComponentRightId());
            royaltyRule.setComponent(e.getId());
            royaltyRule.setProductId(product.getId());
            royaltyRule.setSplitCode(product.getCode());
            royaltyRule.setRoyalty_mode(e.getRoyalty_mode());
            royaltyRule.setAmount(e.getRoyaltyAmount());
            royaltyRule.setSettle_account(balance.getId());
            royaltyRule.setSupplier(e.getSupplier());
            royaltyRule.setRoyaltyPercent(e.getRoyaltyPercent());
            royaltyRule.setCollection_method(e.getCollection_method());

            return royaltyRule;
        }).collect(Collectors.toList()));*/

    }


    public void assingtoProduct(Product product , ProductBundle royalties, Product burdle) {




        Component component =new Component();
        component.setProduct(product.getId());
        component.setOriginalProduct(product.getId());
        component.setSupplier(burdle.getSupplierId());
        component.setAgent(royalties.getAgentProduct().getAgent().getSupplier());
        component.setSource(EnumProductComponentSource.partner);
        component.setValidate_way(EnumValidateWay.none);
        component.setAgentProductCode(royalties.getAgentProduct().getCode());
        component.setType(EnumComponentVoucherType.Burdle);

        component.setComponentRight(0);



/*        component.setSource(right.getSource());

            if(right.getSource().equals(EnumProductComponentSource.partner)){
                Assert.notNull(right.getSubscription(), "系统已有 componment Right 不能为空，当前空，新建 component 失败");
                Assert.notNull(right.getRatePlan(), "系统已有 componment RatePlan 不能为空，当前空，新建 component 失败");
                component.setRatePlan(right.getRatePlan());
                component.setSubscription(right.getSubscription());
            }*/



            component.setUnit_off(1l);
            component.setDuration(EnumDuration.once);


/*            component.setRoyalty_mode(right.getRoyalty_mode());
            component.setRoyaltyPercent(right.getValue());
            component.setRoyaltyAmount(right.getValue());
            component.setRoyaltyAmount(right.getValue());
            component.setCollection_method(royalty.getCollection_method());
            component.setValidate_way(royalty.getValidate_way());


            component.setRoyaltyPercent(royalty.getPercent());*/




            componentRepository.save(component);

    }





    public String checkPublishProduct(Product product) {

        List<Component> componentRights = componentRepository.findAllByProduct(product.getId());

        if(componentRights.size() == 0){

            return "至少包含一个权益";
        }
        return null;


    }





/*



    public void assingtoTicket(VoucherTicket voucherTicket, List<Component>  components, Long limit) {


        System.out.println("这里给 VOUcherTicket 复议权益呀啊啊");
        // componentRepository.deleteAllByProduct(product.getId());

        List<ComponentVounch> list =  components.stream().map(right->{

            ComponentVounch componentVounch = new ComponentVounch();
            componentVounch.setCode(idGenService.componentVouncherCode());

            componentVounch.setName(right.getName());

            componentVounch.setProduct(voucherTicket.getId());
            componentVounch.setSupplier(right.getSupplier());


            componentVounch.setComponentRight(right.getComponentRightId());


            componentVounch.setReference(voucherTicket.getCode());
            componentVounch.setStatus(EnumComponentVoucherStatus.NotRedeemed);



        //    componentVounch.setRatePlan(EnumReferralEvents.);
            componentVounch.setSource(right.getSource());
            if(right.getSource().equals(EnumProductComponentSource.partner)){
                Assert.notNull(right.getSubscription(), "系统已有 componment Right 不能为空，当前空，新建 componentVounch 失败");
                Assert.notNull(right.getRatePlan(), "系统已有 componment RatePlan 不能为空，当前空，新建 componentVounch 失败");
                componentVounch.setRatePlan(right.getRatePlan());

                componentVounch.setSubscription(right.getSubscription());
            }

            componentVounch.setVoucherId(voucherTicket.getId());
            componentVounch.setLimit(limit);
            componentVounch.setTry_(1l);
            componentVounch.setRedeemed_quantity(0l);
            componentVounch.setUnit_off(right.getUnit_off());
            componentVounch.setDuration(right.getDuration());
            componentVounch.setReservation(voucherTicket.getBooking());


            componentVounch.setRoyalty_mode(right.getRoyalty_mode());
            componentVounch.setValidate_way(right.getValidate_way());
            componentVounch.setSupplier(right.getSupplier());
*/
/*            componentVounch.setRoyaltyPercent(right.getValue());
            componentVounch.setRoyaltyAmount(right.getValue());
            componentVounch.setRoyaltyAmount(right.getValue());
            componentVounch.setCollection_method(royalty.getCollection_method());
            componentVounch.setValidate_way(royalty.getValidate_way());

            componentVounch.setRoyaltyPercent(royalty.getPercent());*//*

            return componentVounch;
        }).collect(Collectors.toList());

        list = componentVounchRepository.saveAll(list);







    }


*/


    public void assingtoComponent(CompoentRightAssigtToTargeVo voucherTicket, List<Triplet<Component,LineItem,EnumComponentVoucherType>> components , Long limit) {


        Reservation reservation = voucherTicket.getBooking();

        System.out.println("这里给 VOUcherTicket 复议权益呀啊啊");
        // componentRepository.deleteAllByProduct(product.getId());

        List<ComponentVounch> list =  components.stream().map(ccc->{



            Component right = ccc.getValue0();

            LineItem lineItem = ccc.getValue1();
            EnumComponentVoucherType type = ccc.getValue2();
            ComponentVounch componentVounch = new ComponentVounch();
            componentVounch.setCode(idGenService.componentVouncherCode());

            componentVounch.setRedeem_voucher_key(idGenService.nextId(""));



            componentVounch.setProduct(lineItem.getProduct());
            componentVounch.setLineItem(lineItem.getId());

            componentVounch.setType(type);



            componentVounch.setSupplier(right.getSupplier());
            componentVounch.setAgent(reservation.getSupplier());






            componentVounch.setComponentRight(right.getComponentRightId());


            componentVounch.setReference(voucherTicket.getReference());
            componentVounch.setStatus(EnumComponentVoucherStatus.NotRedeemed);



            //    component.setRatePlan(EnumReferralEvents.);
            componentVounch.setSource(right.getSource());
            if(right.getType().equals(EnumComponentVoucherType.Burdle)){

             //   if(right.getSource().equals(EnumProductComponentSource.partner)){
             //   Assert.notNull(right.getSubscription(), "系统已有 componment Right 不能为空，当前空，新建 component 失败");
            //    Assert.notNull(right.getRatePlan(), "系统已有 componment RatePlan 不能为空，当前空，新建 component 失败");
           //     componentVounch.setRatePlan(right.getRatePlan());
           //     componentVounch.setSubscription(right.getSubscription());
                componentVounch.setName("这是一个代理的产品");
                componentVounch.setLimit(1l);
            }else{
                componentVounch.setName(right.getName());
                componentVounch.setLimit(limit);
            }

            componentVounch.setReferenceId(voucherTicket.getReferenceId());
            componentVounch.setReferenceType(voucherTicket.getReferenceType());

            componentVounch.setOriginalProduct(right.getOriginalProduct());
            componentVounch.setTry_(1l);
            componentVounch.setRedeemed_quantity(0l);
            componentVounch.setUnit_off(right.getUnit_off());
            componentVounch.setDuration(right.getDuration());
            componentVounch.setReservation(reservation.getId());
            componentVounch.setLog_Reservation_code(reservation.getCode());


            componentVounch.setRoyalty_mode(right.getRoyalty_mode());
            componentVounch.setValidate_way(right.getValidate_way());
            componentVounch.setSupplier(right.getSupplier());
/*            component.setRoyaltyPercent(right.getValue());
            component.setRoyaltyAmount(right.getValue());
            component.setRoyaltyAmount(right.getValue());
            component.setCollection_method(royalty.getCollection_method());
            component.setValidate_way(royalty.getValidate_way());

            component.setRoyaltyPercent(royalty.getPercent());*/
            return componentVounch;
        }).collect(Collectors.toList());

        list = componentVounchRepository.saveAll(list);







    }



    public  List<ComponentVounch> groupby(List<ComponentVounch> componentVounchList, Pass pass) {
         List<ComponentVounch> componentVounches = componentVounchList.stream().map(e->{
            e.setPass(pass.getId());
            e.setBelong(pass.getId());
            e.setBelongType(EnumBelongType.Pass);
            return e;
        }).collect(Collectors.toList());
        return componentVounches;
    }

    public void bulkUpdata(List<ComponentVounch> componentVounchList) {
        componentVounchRepository.saveAll(componentVounchList);
    }

    public void sentEntries(String code, RedemptionTryResp resp, List<ComponentVounch> componentVounchList) {

        Hashids hashids = new Hashids("this is my pepper");



        LocalDateTime now = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(now);


        resp.setEntries(componentVounchList.stream()
                .map(e->{
                    RedemptionTryResp.RedemptionEntryResp redemptionEntryResp = new RedemptionTryResp.RedemptionEntryResp();




                    ;



                    //redemptionEntryResp.setRedeem_voucher_key(e.getRedeem_voucher_key());
                    redemptionEntryResp.setLable(e.getName());
                   // redemptionEntryResp.setLimit(e.getLimit().intValue());

                    redemptionEntryResp.setStart_date(e.getStart_date());
                    redemptionEntryResp.setEnd_date(e.getEnd_date());
                    redemptionEntryResp.setRemaining(e.getLimit().intValue()-e.getRedeemed_quantity().intValue());
                    redemptionEntryResp.setTryRedeem(e.getTry_());



                    redemptionEntryResp.setCheck_in(redemptionEntryResp.getRemaining() >= e.getTry_());

                    if(redemptionEntryResp.isCheck_in()){
                        redemptionEntryResp.setRedeem_voucher_key_crypt(cryptoService.encode(e.getRedeem_voucher_key()));

                    }

                    return redemptionEntryResp;

                })
                .sorted(Comparator.comparing(RedemptionTryResp.RedemptionEntryResp::isCheck_in))
                .collect(Collectors.toList()));


        resp.setRedeemAllowed(resp.getEntries().stream().filter(e->e.isCheck_in()).findAny().isPresent());








        resp.setCrypto_code(cryptoService.decorated_encode(code));
    }
}

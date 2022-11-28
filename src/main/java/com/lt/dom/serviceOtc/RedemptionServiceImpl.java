package com.lt.dom.serviceOtc;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.ValidatedByTypeVo;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;


@Service
public class RedemptionServiceImpl {
    @Autowired
    private ScenarioAssignmentRepository scenarioAssignmentRepository;
    @Autowired
    private QuotaRepository quotaRepository;


    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private DeviceRepository deviceRepository;


    @Autowired
    private RedemptionRepository redemptionRepository;


    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;

    @Autowired
    private WriteoffEquipServiceImpl writeoffEquipService;
    @Autowired
    private ComponentRightRepository componentRightRepository;

    @Autowired
    private RightRedemptionEntryRepository rightRedemptionEntryRepository;

    @Autowired
    UsageRepository usageRepository;
    @Autowired
    private ComponentVounchRepository componentVounchRepository;
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private RoyaltySettlementRepository royaltySettlementRepository;

    @Autowired
    private ScenarioRepository scenarioRepository;


    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;

    @Autowired
    private RoyaltyTransactionRepository royaltyTransactionRepository;


    public ComponentVounch redeemCompo(ComponentVounch componentVounch1){


        Optional<RoyaltyRule> royaltyRuleOptional = royaltyRuleRepository.findById(componentVounch1.getRoyaltyRule().getId());
        RoyaltyRule royaltyRule = royaltyRuleOptional.get();





        Charge charge = new Charge();

        RoyaltySettlement royaltySettlement = new RoyaltySettlement();
        royaltySettlement.setChargeId(charge.getId());
        royaltySettlement.setReservationId(componentVounch1.getReservation());
        royaltySettlement = royaltySettlementRepository.save(royaltySettlement);

        RoyaltyTransaction royaltyTransaction = new RoyaltyTransaction(royaltySettlement.getId(),royaltyRule.getRecipient(),royaltyRule.getAmount());


        royaltyTransaction = royaltyTransactionRepository.save(royaltyTransaction);

        return componentVounch1;


    }




    private List<ComponentVounch> 找到当前权益(Validator_ validator, Voucher voucher) throws Exception {


        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();



        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        ComponentVounch probe = new ComponentVounch();
        probe.setComponentRight(componentRight.getId());
        probe.setVoucherId(voucher.getId());

        Example<ComponentVounch> example = Example.of(probe, modelMatcher);

        List<ComponentVounch> componentVounches1 = componentVounchRepository.findAll(example);


        if(componentVounches1.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Foo Not Found", new Exception("DDDDDDDDDD"));
        }


        return componentVounches1;


    }


    public void redemOnceVounchor(UserDetails userDetails, Voucher voucher){


      //  List<Validator> validator = validatorRepository.findAllByUserId(userDetails.getUsername());

        if(voucher.getType().equals(EnumVoucherType.RIGHT_VOUCHER)){  // 核销的时候
            PublicationEntry entry = new PublicationEntry();

            RedemptionEntry redemptionEntry = new RedemptionEntry();
            redemptionEntry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);
            redemptionEntry.setVoucher(voucher.getId());

            redemptionEntry.setCustomer_id(entry.getToWho());
        }


        if(voucher.getType().equals(EnumVoucherType.DISCOUNT_VOUCHER)){  //下单的时候
            PublicationEntry entry = new PublicationEntry();

            RedemptionEntry redemptionEntry = new RedemptionEntry();
            redemptionEntry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);
            redemptionEntry.setVoucher(voucher.getId());

            redemptionEntry.setCustomer_id(entry.getToWho());
        }



    }





    public boolean 是否符合验证(Campaign campaign, Supplier supplier) {


            List<Quota> quotas = quotaRepository.findByCompaign(campaign.getId());
            List<Long> scenariolongList = quotas.stream().filter(x->x.getType().equals(EnumQuotaType.Scenario)).map(x->x.getScenario()).collect(Collectors.toList());

            List<Long> supplierIds = scenariolongList.stream().map(x->{
                List<ScenarioAssignment> scenarioAssignments = scenarioAssignmentRepository.findByScenario(x);
                return scenariolongList.stream().collect(Collectors.toList());
            }).flatMap(Collection::stream).collect(Collectors.toList());

            List<Long> supplierIds_ = quotas.stream().filter(x->x.getType().equals(EnumQuotaType.NominatedSupplier)).map(x->x.getScenario()).collect(Collectors.toList());


            return supplierIds_.contains(supplier.getId()) || supplierIds_.contains(supplier.getId());

    }


/*
    public Pair<RedemptionEntry,Redemption> redeemVounchor(Voucher voucher, Campaign campaign, RedemPojo pojo) {




        Optional<Validator> optionalValidator = null;
        if(pojo.getType().equals(EnumValidatorType.特定的人员)){
            Validator user = new Validator();
            user.setType(pojo.getType());
            user.setUserId(pojo.getUser());
            Example<Validator> example = Example.of(user);

            optionalValidator = validatorRepository.findOne(example);
        }else{
            Validator user = new Validator();
            user.setType(pojo.getType());
            user.setEquipmentId(pojo.getQuipment());

            Example<Validator> example = Example.of(user);

            optionalValidator = validatorRepository.findOne(example);
        }



        if(optionalValidator.isPresent()){
          //  return writeoffEquipService.writeOff(optionalValidator.get(),voucher);
        }


        if(pojo.getRedeemptionTyppe().equals("aaa")){
            pojo.getTraveler().getIdNo();
            pojo.getTraveler().getName();
            pojo.getTraveler().getPhone();
        }
        Redemption redemption = new Redemption();
        List<Redemption> redemptions = redemptionRepository.findByRelatedObjectIdAndRelatedObjectType(voucher.getId(),"voucher");
        if(redemptions.isEmpty()){

            redemption.setRelatedObjectId(voucher.getId());
            redemption.setRelatedObjectType("voucher");
            redemption.setQuantity(voucher.getQuantity());
            redemption = redemptionRepository.save(redemption);
        }else {
            redemption = redemptions.get(0);
            redemption.setRedeemed_quantity(redemption.getRedeemed_quantity()-1);
            redemption = redemptionRepository.save(redemption);
        }




        RedemptionEntry entry = new RedemptionEntry();
        entry.setRedemption(redemption.getId());
        entry.setVoucher(voucher.getId());
        entry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);
        entry.setRelatedObjectId(voucher.getId());
        entry.setRelatedObjectType("voucher");

        entry.setCampaign(campaign.getId());
        entry = redemptionEntryRepository.save(entry);


        voucher.setActive(false);
        voucherRepository.save(voucher);





        return Pair.with(entry,redemption);
    }

*/





    public List<Redemption> rollback(Redemption redemption) {
        return null;
    }




    public void RedeemVounchor(List<Pair<User, ComponentVounch>> vouchers) {

        Redemption redemption = new Redemption();

/*        redemption.setRelatedObjectId(id);
        redemption.setRelatedObjectType(booking);*/
        redemption.setCreated_at(LocalDateTime.now());
        redemption.setQuantity(Integer.valueOf(vouchers.size()).longValue());
        redemption.setValidatorSupplier(1);
        redemption = redemptionRepository.save(redemption);


       // Map<Long,Pair<Traveler, Voucher>> voucherMap = vouchers.stream().collect(toMap(x->x.getValue1().getId(), x->x));

        Redemption finalRedemption = redemption;
        List<RedemptionEntry> redemptionEntryList = vouchers.stream().map(x->{

            ComponentVounch voucher = x.getValue1();
            User traveler = x.getValue0();

            RedemptionEntry entry = new RedemptionEntry();
            entry.setCode(idGenService.redemptionEntryCode());
            entry.setRedemption(finalRedemption.getId());
            entry.setBulk(true);
            entry.setVoucher(voucher.getId());
            entry.setVoucher_code(voucher.getCode());
            entry.setPublished_at(LocalDateTime.now());
            entry.setRedeemed_at(LocalDateTime.now());
            entry.setCreatedAt(LocalDateTime.now());
            entry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);
            entry.setRelatedObjectId(traveler.getId());
            entry.setRelatedObjectType(EnumPublicationObjectType.traveler);

            entry.setSupplier(voucher.getSupplier());
            return entry;
        }).collect(Collectors.toList());

        redemptionEntryList = redemptionEntryRepository.saveAll(redemptionEntryList);

    }


    public  List<RightRedemptionEntry> redeemRight(VoucherTicket voucher, ValidatedByTypeVo  verifier核销人员, User traveler用户, List<ComponentVounch> componentVounchList) {


        Redemption redemption = new Redemption();

        redemption.setCode(idGenService.redemptionNo());
        redemption.setRelatedObjectId(voucher.getId());
        redemption.setRelatedObjectType(EnumRelatedObjectType.voucher);
        redemption.setQuantity(1l);
        redemption.setRedeemed_quantity(0l);

        redemption.setLog_RelatedObject_lable(voucher.getLable());
        redemption.setLog_RelatedObject_code(voucher.getCode());
        redemption.setLog_RelatedObject_type(voucher.getType());


        redemption.setValidatorType(verifier核销人员.getValidatorType());
        redemption.setValidator(verifier核销人员.funGetValidator());


        redemption.setCustomer(traveler用户.getId());
        redemption.setLog_Customer_name(traveler用户.getRealName());
        redemption.setLog_Customer_code(traveler用户.getCode());




        if(verifier核销人员.getValidatorType().equals(EnumValidatorType.特定的人员)){

            redemption.setLog_Validator_user_name(verifier核销人员.getUser().getRealName());
            redemption.setLog_Validator_user_code(verifier核销人员.getUser().getCode());

        }
        if(verifier核销人员.getValidatorType().equals(EnumValidatorType.特定机器)){

            Device device =  verifier核销人员.getDevice();//= deviceRepository.findById(verifier核销人员.getDevice()).get();

            redemption.setLog_Validator_device_name(device.getName());
            redemption.setLog_Validator_device_code(device.getCode());
            redemption.setLog_Validator_device_type(device.getType());
        }





        redemption = redemptionRepository.save(redemption);

        Redemption finalRedemption = redemption;
        List<RightRedemptionEntry> redemptionEntryList = componentVounchList.stream()

                .filter(e->!e.getStatus().equals(EnumComponentVoucherStatus.AlreadyRedeemed))

                .map(x->{

                    return redeemRightForeach(finalRedemption,verifier核销人员,traveler用户,x);

                }).collect(Collectors.toList());



        return redemptionEntryList;
    }

    public RightRedemptionEntry redeemRightForeach(Redemption redemption ,ValidatedByTypeVo  verifier核销人员, User traveler用户, ComponentVounch voucher权益) {


        if(voucher权益.getStatus().equals(EnumComponentVoucherStatus.AlreadyRedeemed)){

            throw new BookNotFoundException(Enumfailures.resource_not_found,"已完全核销，无法核销");
        }
        if(voucher权益.getLimit()< (voucher权益.getRedeemed_quantity()+voucher权益.getTry_())){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"核销权益超出限制，无法核销");

        }








 /*       Optional<Component> componentOptional = componentRepository.findById(voucher权益.getComponent());

        if(componentOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.resource_not_found,"无法找到");
        }
        Component component = componentOptional.get();
        if(component.getSource().equals(EnumProductComponentSource.partner)){
            Assert.notNull(component.getSubscription(), "系统已有 componment的 Subscription 不能为空，当前空，新建 Usage 失败");
            Assert.notNull(component.getRatePlan(), "系统已有 componment RatePlan 不能为空，当前空，新建 Usage 失败");

            component.getSubscription();  // 对他进行了一次核销， 对他进行了二次核销

            Usage usage = new Usage();
            usage.setQty(1l);
            usage.setSubscription(component.getSubscription());
            usage.setComponentRight(component.getComponentRightId());

            usage.setRatePlan(component.getRatePlan());
            usage.setStartDate(LocalDate.now());
            usage.setEndDate(LocalDate.now());
            usageRepository.save(usage);
        }
*/

        RightRedemptionEntry entry = new RightRedemptionEntry();
        entry.setCode(idGenService.redemptionEntryCode());

        entry.setRelatedObjectType(EnumRelatedObjectType.voucher);
        entry.setRelatedObjectId(voucher权益.getVoucherId());



        entry.setComponentVoucher(voucher权益.getId());

        Optional<ComponentRight> componentOptional = componentRightRepository.findById(voucher权益.getComponentRight());
        ComponentRight componentRight = componentOptional.get();

        entry.setComponent_right(voucher权益.getComponentRight());
        entry.setRedeemed_quantity(voucher权益.getTry_());
        entry.setLog_Component_right_name(componentRight.getName());
        entry.setLog_Component_right_code(componentRight.getCode());


        entry.setRedemption(redemption.getId());
        entry.setCustomer_id(traveler用户.getId());

        entry.setLog_Customer_name(traveler用户.getRealName());

        entry.setValidatorType(verifier核销人员.getValidatorType());

        if(verifier核销人员.getValidatorType().equals(EnumValidatorType.特定的人员)){
            entry.setUser(verifier核销人员.getUser().getId());

            entry.setLog_Verifier_user_name(verifier核销人员.getUser().getRealName());
            entry.setLog_Verifier_user_code(verifier核销人员.getUser().getCode());

        }
        if(verifier核销人员.getValidatorType().equals(EnumValidatorType.特定机器)){

            Device device = verifier核销人员.getDevice();
            entry.setDevice(device.getId());
            entry.setLog_Verifier_device_name(device.getName());
            entry.setLog_Verifier_device_code(device.getCode());
            entry.setLog_Verifier_device_type(device.getType());
        }
        entry.setCreatedAt(LocalDateTime.now());

        entry.setSupplier(voucher权益.getSupplier());

        voucher权益.setRedeemed_quantity(voucher权益.getRedeemed_quantity()+voucher权益.getTry_());

        if(voucher权益.getLimit()== voucher权益.getRedeemed_quantity()){
            voucher权益.setStatus(EnumComponentVoucherStatus.AlreadyRedeemed);
        }else{
            voucher权益.setStatus(EnumComponentVoucherStatus.PartialyRedeemed);
        }


        componentVounchRepository.save(voucher权益);

        return entry;

    }


    public void redeemRightForeach(List<Triplet<ValidatedByTypeVo,User, ComponentVounch>> vouchers) {





    }


}

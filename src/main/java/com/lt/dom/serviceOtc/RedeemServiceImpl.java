package com.lt.dom.serviceOtc;

import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RedemPojo;
import com.lt.dom.otcenum.EnumQuotaType;
import com.lt.dom.otcenum.EnumVoucherType;
import com.lt.dom.repository.*;
import org.javatuples.Pair;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

import static java.util.stream.Collectors.groupingBy;

@Service
public class RedeemServiceImpl {
    @Autowired
    private ScenarioAssignmentRepository scenarioAssignmentRepository;
    @Autowired
    private QuotaRepository quotaRepository;
    @Autowired
    private SupplierRepository supplierRepository;


    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private RedemptionRepository redemptionRepository;


    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;

    @Autowired
    private WriteoffEquipServiceImpl writeoffEquipService;
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private ValidatorRepository validatorRepository;


    @Autowired
    private ComponentVounchRepository componentVounchRepository;

    @Autowired
    private RoyaltySettlementRepository royaltySettlementRepository;

    @Autowired
    private IdGenServiceImpl idGenService;


    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;

    @Autowired
    private RoyaltyTransactionRepository royaltyTransactionRepository;

    @Autowired
    private SummaryServiceImpl summaryService;



    public ComponentVounch redeemCompo(ComponentVounch componentVounch1){


        Optional<RoyaltyRule> royaltyRuleOptional = royaltyRuleRepository.findById(componentVounch1.getRoyaltyRule().getId());
        RoyaltyRule royaltyRule = royaltyRuleOptional.get();





        Charge charge = new Charge();

        RoyaltySettlement royaltySettlement = new RoyaltySettlement();
        royaltySettlement.setChargeId(charge.getId());
        royaltySettlement.setReservationId(componentVounch1.getReservationId());
        royaltySettlement = royaltySettlementRepository.save(royaltySettlement);

        RoyaltyTransaction royaltyTransaction = new RoyaltyTransaction(royaltySettlement.getId(),royaltyRule.getRecipient(),royaltyRule.getAmount());


        royaltyTransaction = royaltyTransactionRepository.save(royaltyTransaction);

        return componentVounch1;


    }




    private List<ComponentVounch> 找到当前权益(Validator validator, Voucher voucher) throws Exception {


        ValidatorGroup validatorGroup = validator.getValidatorGroup();

        AccessValidator accessValidator = validatorGroup.getAccessValidator();


        ComponentRight componentRight = accessValidator.getComponentRight();



        ExampleMatcher modelMatcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withMatcher("model", ignoreCase());

        ComponentVounch probe = new ComponentVounch();
        probe.setComponentRightId(componentRight.getId());
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


            return true;// supplierIds_.contains(supplier.getId()) || supplierIds_.contains(supplier.getId());

    }


    public Pair<RedemptionEntry,Redemption> bulkRedeemVounchor(Voucher voucher, Campaign campaign, RedemPojo pojo, Supplier supplier) {




        Optional<Validator> optionalValidator = Optional.empty();
   /*     if(pojo.getType().equals(EnumValidatorType.特定的人员)){
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
*/


        if(optionalValidator.isPresent()){
          //  return writeoffEquipService.writeOff(optionalValidator.get(),voucher);
        }


  /*      if(pojo.getRedeemptionTyppe().equals("aaa")){
            pojo.getTraveler().getIdNo();
            pojo.getTraveler().getName();
            pojo.getTraveler().getPhone();
        }*/
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
        entry.setSupplier(supplier.getId());

        entry = redemptionEntryRepository.save(entry);


        voucher.setActive(false);
        voucherRepository.save(voucher);

        summaryService.redemptionSummary(entry,campaign,supplier);


        return Pair.with(entry,redemption);
    }




    public int bulkRedeemVounchor(long id, Class<Reservation> reservationClass, List<Pair<Traveler, Voucher>> vouchers) {

        Redemption redemption = new Redemption();

        redemption.setRelatedObjectId(id);
        redemption.setRelatedObjectType(reservationClass.getTypeName());
        redemption.setCreated_at(LocalDateTime.now());
        redemption.setQuantity(vouchers.size());
        redemption.setValidatorSupplier(1);
        redemption = redemptionRepository.save(redemption);


       // Map<Long,Voucher> voucherMap = vouchers.stream().map(x->x.getValue1()).collect(toMap(x->x.getId(),x->x));

        Map<Long,Pair<Traveler, Voucher>> voucherMap = vouchers.stream().collect(toMap(x->x.getValue1().getId(),x->x));


        Redemption finalRedemption = redemption;
        List<RedemptionEntry> redemptionEntryList = vouchers.stream().map(x->{

            Voucher voucher = x.getValue1();
            Traveler traveler = x.getValue0();

            RedemptionEntry entry = new RedemptionEntry();
            entry.setCode(idGenService.redemptionEntryCode());
            entry.setRedemption(finalRedemption.getId());
            entry.setBulk(true);
            entry.setVoucher(voucher.getId());
            entry.setVoucherCode(voucher.getCode());
            entry.setIssued_at(LocalDateTime.now());
            entry.setRedeem_at(LocalDateTime.now());
            entry.setCreated_at(LocalDateTime.now());
            entry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);
            entry.setRelatedObjectId(traveler.getId());
            entry.setRelatedObjectType("traveler");

            entry.setCampaign(voucher.getCampaign());
            entry.setSupplier(1);
            return entry;
        }).collect(Collectors.toList());

        redemptionEntryList = redemptionEntryRepository.saveAll(redemptionEntryList);

        voucherRepository.saveAll(vouchers.stream().map(x->{

            Voucher voucher = x.getValue1();
            voucher.setActive(false);
            return voucher;
        }).collect(Collectors.toList()));

        return 0;
    }
}

package com.lt.dom.serviceOtc;

import com.lt.dom.notification.event.OnVoucherRedeamedEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import org.javatuples.Pair;
import org.javatuples.Quintet;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private TravelerRepository travelerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscountRepository discountRepository;

    @Autowired
    private RedemptionRepository redemptionRepository;

    @Autowired
    private PublicationEntryRepository publicationEntryRepository;

    @Autowired
    private RedemptionEntryRepository redemptionEntryRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private CampaignAssignToTourBookingRepository campaignAssignToTourBookingRepository;



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

    @Autowired
    ApplicationEventPublisher eventPublisher;


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

    public Quintet<PublicationEntry,Voucher,PublishTowhoVo,Campaign,Supplier> ValidateVounchor(Voucher voucher, Campaign campaign, Supplier supplier) {

        Optional<PublicationEntry> optionalPublicationEntry = publicationEntryRepository.findByVoucher(voucher.getId());

        PublishTowhoVo publishTowhoVo = new PublishTowhoVo();

        if(optionalPublicationEntry.isPresent()){
            PublicationEntry publicationEntry = optionalPublicationEntry.get();
            publishTowhoVo.setToWhoTyp(publicationEntry.getToWhoType());

            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.traveler)){
                Optional<Traveler> traveler = travelerRepository.findById(publicationEntry.getToWho());
                publishTowhoVo.setTraveler(traveler.get());
                publishTowhoVo.setToWho(traveler.get().getId());
            }

            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.business)){
                Optional<Supplier> traveler = supplierRepository.findById(publicationEntry.getToWho());
                publishTowhoVo.setSupplier(traveler.get());
                publishTowhoVo.setToWho(traveler.get().getId());
            }
            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.customer)){
                Optional<User> user = userRepository.findById(publicationEntry.getToWho());
                publishTowhoVo.setUser(user.get());
                publishTowhoVo.setToWho(user.get().getId());
            }
        }



        if(voucher.getType().equals(EnumVoucherType.DISCOUNT_VOUCHER)){
            Optional<Discount> optional = discountRepository.findById(voucher.getRelateId());
            if(optional.get().getType().equals(EnumDiscountVoucherCategory.AMOUNT)){
                voucher.setRedeemed_amount(optional.get().getAmount_off());
                voucher.setRedeemed_quantity(voucher.getRedeemed_quantity()+1);
            }
        }

        return Quintet.with(optionalPublicationEntry.get(),voucher,publishTowhoVo,campaign,supplier);
    }


    public List<Triplet<RedemptionEntry,Redemption,PublishTowhoVo>> bulkRedeemVounchor(CampaignAssignToTourBooking campaignAssignToTourBooking, TourBooking tourBooking, Campaign campaign, Supplier supplier) {

        List<Traveler> travelerList = travelerRepository.findAllByBooking(tourBooking.getId());


      //  List<PublicationEntry> publicationEntryList= publicationEntryRepository.findByToWhoTypeAndToWhoIn(EnumPublicationObjectType.traveler,travelerList.stream().map(x->x.getId()).collect(Collectors.toList()));

        List<PublicationEntry> publicationEntryList= publicationEntryRepository.findByAssociatedTypeAndAssociatedId(EnumAssociatedType.tour_booking,tourBooking.getId());

        Redemption redemption = new Redemption();
        List<Redemption> redemptions = redemptionRepository.findByRelatedObjectIdAndRelatedObjectType(tourBooking.getId(),EnumAssociatedType.tour_booking);
        if(redemptions.isEmpty()){

            redemption.setRelatedObjectId(tourBooking.getId());
            redemption.setRelatedObjectType(EnumAssociatedType.tour_booking);
            redemption.setQuantity(tourBooking.getTotal_amount());
            redemption = redemptionRepository.save(redemption);
        }else {
            redemption = redemptions.get(0);
            redemption.setRedeemed_quantity(redemption.getRedeemed_quantity()-1);
            redemption = redemptionRepository.save(redemption);
        }



        Redemption finalRedemption = redemption;


        List<Triplet<RedemptionEntry,Redemption,PublishTowhoVo>> tripletList =  publicationEntryList.stream().map(x->{

            Optional<Voucher> voucherOptional = voucherRepository.findById(x.getVoucher());
            PublishTowhoVo publishTowhoVo = new PublishTowhoVo();
            publishTowhoVo.setToWhoTyp(x.getToWhoType());
            if(x.getToWhoType().equals(EnumPublicationObjectType.traveler)){
                Optional<Traveler> traveler = travelerRepository.findById(x.getToWho());
                publishTowhoVo.setTraveler(traveler.get());
                publishTowhoVo.setToWho(traveler.get().getId());

            }

            if(x.getToWhoType().equals(EnumPublicationObjectType.business)){
                Optional<Supplier> traveler = supplierRepository.findById(x.getToWho());
                publishTowhoVo.setSupplier(traveler.get());
                publishTowhoVo.setToWho(traveler.get().getId());
            }
            if(x.getToWhoType().equals(EnumPublicationObjectType.customer)){
                Optional<User> user = userRepository.findById(x.getToWho());
                publishTowhoVo.setUser(user.get());
                publishTowhoVo.setToWho(user.get().getId());
            }
            Triplet<RedemptionEntry,Redemption,PublishTowhoVo> triplet =createRedemptions(voucherOptional.get(),campaign,new Supplier(), finalRedemption,publishTowhoVo);
            return triplet;
        }).collect(Collectors.toList());




        campaignAssignToTourBooking.setRedeemed_count(travelerList.size());
        campaignAssignToTourBooking.setUnredeemed_count(0);
        campaignAssignToTourBooking.setStatus(EnumCampaignToTourBookingStatus.AlreadyRedeemed);

        campaignAssignToTourBookingRepository.save(campaignAssignToTourBooking);

        return tripletList;
    }






    public Triplet<RedemptionEntry,Redemption,PublishTowhoVo> bulkRedeemVounchor(Voucher voucher, Campaign campaign,  Supplier supplier) {

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






        Optional<PublicationEntry> optionalPublicationEntry = publicationEntryRepository.findByVoucher(voucher.getId());


        PublishTowhoVo publishTowhoVo = new PublishTowhoVo();

        String holder = "";
        if(optionalPublicationEntry.isPresent()){

            PublicationEntry publicationEntry = optionalPublicationEntry.get();
            publishTowhoVo.setToWhoTyp(publicationEntry.getToWhoType());


            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.traveler)){
                Optional<Traveler> traveler = travelerRepository.findById(publicationEntry.getToWho());
                publishTowhoVo.setTraveler(traveler.get());
                publishTowhoVo.setToWho(traveler.get().getId());
                holder = traveler.get().getName();
            }

            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.business)){
                Optional<Supplier> traveler = supplierRepository.findById(publicationEntry.getToWho());
                publishTowhoVo.setSupplier(traveler.get());
                publishTowhoVo.setToWho(traveler.get().getId());
                holder = traveler.get().getName();
            }
            if(publicationEntry.getToWhoType().equals(EnumPublicationObjectType.customer)){
                Optional<User> user = userRepository.findById(publicationEntry.getToWho());
                publishTowhoVo.setUser(user.get());
                publishTowhoVo.setToWho(user.get().getId());
                holder = user.get().getRealName();
            }
        }


        if(optionalValidator.isPresent()){
          //  return writeoffEquipService.writeOff(optionalValidator.get(),voucher);
        }


  /*      if(pojo.getRedeemptionTyppe().equals("aaa")){
            pojo.getTraveler().getIdNo();
            pojo.getTraveler().getName();
            pojo.getTraveler().getPhone();
        }*/




        Redemption redemption = new Redemption();
        List<Redemption> redemptions = redemptionRepository.findByRelatedObjectIdAndRelatedObjectType(voucher.getId(),EnumAssociatedType.voucher);
        if(redemptions.isEmpty()){

            redemption.setRelatedObjectId(voucher.getId());
            redemption.setRelatedObjectType(EnumAssociatedType.voucher);

            redemption.setQuantity(voucher.getQuantity());
            redemption = redemptionRepository.save(redemption);
        }else {
            redemption = redemptions.get(0);
            redemption.setRedeemed_quantity(redemption.getRedeemed_quantity()-1);
            redemption = redemptionRepository.save(redemption);
        }


        if(voucher.getType().equals(EnumVoucherType.DISCOUNT_VOUCHER)){

            Optional<Discount> optional = discountRepository.findById(voucher.getRelateId());
            if(optional.get().getType().equals(EnumDiscountVoucherCategory.AMOUNT)){
                voucher.setRedeemed_amount(optional.get().getAmount_off());
                voucher.setRedeemed_quantity(voucher.getRedeemed_quantity()+1);
            }

        }


        RedemptionEntry entry = new RedemptionEntry();
        entry.setCode(idGenService.redemptionEntryCode());
        entry.setRelatedObjectType(publishTowhoVo.getToWhoTyp());
        entry.setRelatedObjectId(publishTowhoVo.getToWho());
        entry.setHolder(holder);
        entry.setRedemption(redemption.getId());
        entry.setVoucher(voucher.getId());
        entry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);

        entry.setRedeemed_amount(voucher.getRedeemed_amount());
        entry.setRedeemed_quantity(voucher.getRedeemed_quantity());
        entry.setCampaign_name(campaign.getName());
        entry.setVoucherCode(voucher.getCode());
        entry.setRedeem_at(LocalDateTime.now());
        entry.setCampaign(campaign.getId());
        entry.setSupplier(supplier.getId());

        entry = redemptionEntryRepository.save(entry);


        voucher.setActive(false);
        voucherRepository.save(voucher);

        summaryService.redemptionSummary(entry,campaign,supplier);


        eventPublisher.publishEvent(new OnVoucherRedeamedEvent(new User(),
                null, null));





        return Triplet.with(entry,redemption,publishTowhoVo);
    }




    public int bulkRedeemVounchor(long id, EnumAssociatedType booking, List<Pair<Traveler, Voucher>> vouchers) {

        Redemption redemption = new Redemption();

        redemption.setRelatedObjectId(id);
        redemption.setRelatedObjectType(booking);
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
            entry.setPublished_at(LocalDateTime.now());
            entry.setRedeem_at(LocalDateTime.now());
            entry.setCreatedAt(LocalDateTime.now());
            entry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);
            entry.setRelatedObjectId(traveler.getId());
            entry.setRelatedObjectType(EnumPublicationObjectType.traveler);

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




















    public Triplet<RedemptionEntry,Redemption,PublishTowhoVo> createRedemptions(Voucher voucher, Campaign campaign,  Supplier supplier,Redemption redemption,PublishTowhoVo publishTowhoVo) {


        RedemptionEntry entry = new RedemptionEntry();
        entry.setCode(idGenService.redemptionEntryCode());
        entry.setRelatedObjectType(publishTowhoVo.getToWhoTyp());
        entry.setRelatedObjectId(publishTowhoVo.getToWho());
       // entry.setHolder(holder);
        entry.setRedemption(redemption.getId());
        entry.setVoucher(voucher.getId());
        entry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);

        entry.setRedeemed_amount(voucher.getRedeemed_amount());
        entry.setRedeemed_quantity(voucher.getRedeemed_quantity());
        entry.setCampaign_name(campaign.getName());
        entry.setVoucherCode(voucher.getCode());
        entry.setRedeem_at(LocalDateTime.now());
        entry.setCampaign(campaign.getId());
        entry.setSupplier(supplier.getId());

        entry = redemptionEntryRepository.save(entry);


        voucher.setActive(false);
        voucherRepository.save(voucher);

        return Triplet.with(entry,redemption,publishTowhoVo);
    }


}

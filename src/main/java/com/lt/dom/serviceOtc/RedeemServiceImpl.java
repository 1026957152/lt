package com.lt.dom.serviceOtc;

import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.notification.event.OnVoucherRedeamedEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.vo.CodeWithLatLngVo;
import com.lt.dom.vo.UserVo;
import com.lt.dom.vo.WrittenOffMerchantVo;
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
    @Autowired
    CampaignServiceImpl campaignService;
    @Autowired
    ClainQuotaServiceImpl clainQuotaService;



    @Autowired
    VoucherServiceImpl voucherService;

/*
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

*/



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


            return true;// supplierIds_.contains(supplier.getId()) || supplierIds_.contains(supplier.getId());

    }

    public Quintet<PublicationEntry,Voucher,PublishTowhoVo,Campaign,Supplier> ValidateVounchor(Voucher voucher, Campaign campaign, Supplier supplier) {

        List<PublicationEntry> optionalPublicationEntry = publicationEntryRepository.findByVoucher(voucher.getId());
        PublishTowhoVo publishTowhoVo = new PublishTowhoVo();

        if(optionalPublicationEntry.size() >0){
            PublicationEntry publicationEntry = optionalPublicationEntry.get(0);

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

        return Quintet.with(optionalPublicationEntry.get(0),voucher,publishTowhoVo,campaign,supplier);
    }


    public List<Triplet<RedemptionEntry,Redemption,PublishTowhoVo>> RedeemVounchor(CampaignAssignToTourBooking campaignAssignToTourBooking, TourBooking tourBooking, Campaign campaign, WrittenOffMerchantVo writtenOffMerchantVo, CodeWithLatLngVo codeWithLatLngVo) {

        List<Traveler> travelerList = travelerRepository.findAllByBooking(tourBooking.getId());


      //  List<PublicationEntry> publicationEntryList= publicationEntryRepository.findByToWhoTypeAndToWhoIn(EnumPublicationObjectType.traveler,travelerList.stream().map(x->x.getId()).collect(Collectors.toList()));

        List<PublicationEntry> publicationEntryList= publicationEntryRepository.findByAssociatedTypeAndAssociatedIdAndCampaign(EnumAssociatedType.tour_booking,tourBooking.getId(),campaign.getId());

        Redemption redemption = new Redemption();
        List<Redemption> redemptions = redemptionRepository.findByRelatedObjectIdAndRelatedObjectType(tourBooking.getId(),EnumAssociatedType.tour_booking);
        if(redemptions.isEmpty()){

            redemption.setRelatedObjectId(tourBooking.getId());
            redemption.setRelatedObjectType(EnumRelatedObjectType.tour_booking);
            redemption.setQuantity(1l);
            redemption = redemptionRepository.save(redemption);
        }else {
            redemption = redemptions.get(0);
            redemption.setRedeemed_quantity(redemption.getRedeemed_quantity()-1);
            redemption = redemptionRepository.save(redemption);
        }



        Redemption finalRedemption = redemption;

        List<Voucher> voucherList = voucherRepository
                .findAllById(publicationEntryList.stream().map(x->x.getVoucher()).collect(Collectors.toList()));

        Map<Long,Voucher> longVoucherMap = voucherList.stream().collect(Collectors.toMap(e->e.getId(),e->e));

        List<Triplet<RedemptionEntry,Redemption,PublishTowhoVo>> tripletList =  publicationEntryList.stream().map(x->{

            Voucher voucher = longVoucherMap.get(x.getVoucher());

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

            Triplet<RedemptionEntry,Redemption,PublishTowhoVo> triplet =createRedemptions(x, voucher,campaign,writtenOffMerchantVo, finalRedemption,publishTowhoVo,codeWithLatLngVo);
            return triplet;
        }).collect(Collectors.toList());


        List<RedemptionEntry> redemptionEntryList = redemptionEntryRepository.saveAll(tripletList.stream().map(e->e.getValue0()).collect(Collectors.toList()));



        voucherList = voucherRepository.saveAll(voucherList.stream().map(e->{
            e.setActive(false);
            return e;
        }).collect(Collectors.toList()));



        campaignAssignToTourBooking.setRedeemed_count(travelerList.size());
        campaignAssignToTourBooking.setUnredeemed_count(0);
        campaignAssignToTourBooking.setStatus(EnumCampaignToTourBookingStatus.AlreadyRedeemed);

        campaignAssignToTourBookingRepository.save(campaignAssignToTourBooking);







        campaign.setTotal_redeemed(tripletList.stream().count()+campaign.getTotal_redeemed());
        campaign.setTotal_succeeded(tripletList.stream().count()+campaign.getTotal_succeeded());
        campaignService.updateSummary(campaign,EnumEvents.bulk_redemption$succeeded);



        voucherList.stream().forEach(x->{
            eventPublisher.publishEvent(new OnVoucherRedeamedEvent(new User(),x,
                    null, EnumEvents.bulk_redemption$succeeded));
        });



        return tripletList;
    }






    public Triplet<RedemptionEntry,Redemption,PublishTowhoVo> RedeemVounchor(Voucher voucher, Campaign campaign, WrittenOffMerchantVo writtenOffMerchantVo, CodeWithLatLngVo codeWithLatLngVo) {

        Optional<Validator_> optionalValidator = Optional.empty();
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






        List<PublicationEntry> publicationEntryList = publicationEntryRepository.findByVoucher(voucher.getId());


        PublishTowhoVo publishTowhoVo = new PublishTowhoVo();

        String holder = "";
        if(publicationEntryList.size() == 0) {

            throw new BookNotFoundException(Enumfailures.voucher_disabled,"找不到 该券的发布信息 publicationEntry");
        }
            PublicationEntry publicationEntry = publicationEntryList.get(0);
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
            redemption.setRelatedObjectType(EnumRelatedObjectType.tour_booking);

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


        Triplet<RedemptionEntry,Redemption,PublishTowhoVo> triplet =createRedemptions(publicationEntry,voucher,campaign,writtenOffMerchantVo, redemption,publishTowhoVo,codeWithLatLngVo);

        RedemptionEntry redemptionEntry = triplet.getValue0();
        redemptionEntry = redemptionEntryRepository.save(redemptionEntry);




        voucher.setActive(false);
        voucher.setStatus(EnumVoucherStatus.Redeemed);
        voucherService.update(voucher,EnumEvents.redemption$succeeded);


        summaryService.redemptionSummary(redemptionEntry,campaign,writtenOffMerchantVo);

        clainQuotaService.writenOff(writtenOffMerchantVo.getSupplier(),campaign);


        campaign.setTotal_redeemed(campaign.getTotal_redeemed()+1);
        campaign.setTotal_succeeded(campaign.getTotal_redeemed()+1);
        campaignService.updateSummary(campaign,EnumEvents.redemption$succeeded);


        eventPublisher.publishEvent(new OnVoucherRedeamedEvent(new User(),
                null, EnumEvents.redemption$succeeded));


        return Triplet.with(redemptionEntry,redemption,publishTowhoVo);
    }




    public int RedeemVounchor(long id, EnumRelatedObjectType booking, List<Pair<Traveler, Voucher>> vouchers) {

        Redemption redemption = new Redemption();

        redemption.setRelatedObjectId(id);
        redemption.setRelatedObjectType(booking);
        redemption.setCreated_at(LocalDateTime.now());
        redemption.setQuantity(Integer.valueOf(vouchers.size()).longValue());
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
            entry.setVoucher_code(voucher.getCode());
            entry.setPublished_at(LocalDateTime.now());
            entry.setRedeemed_at(LocalDateTime.now());
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




















    public Triplet<RedemptionEntry,Redemption,PublishTowhoVo> createRedemptions(PublicationEntry publicationEntry, Voucher voucher, Campaign campaign, WrittenOffMerchantVo writtenOffMerchantVo, Redemption redemption, PublishTowhoVo publishTowhoVo, CodeWithLatLngVo codeWithLatLngVo) {






        Supplier supplier = writtenOffMerchantVo.getSupplier();
        UserVo userVo = writtenOffMerchantVo.getUser();


        RedemptionEntry entry = new RedemptionEntry();
        entry.setCode(idGenService.redemptionEntryCode());
        entry.setRelatedObjectType(publishTowhoVo.getToWhoTyp());
        entry.setRelatedObjectId(publishTowhoVo.getToWho());

        entry.setCreatedAt(LocalDateTime.now());
        entry.setPublished_at(publicationEntry.getPublished_at());



        entry.setRedemption(redemption.getId());
        entry.setVoucher(voucher.getId());
        entry.setResult(RedemptionEntry.RedemptionStatus.SUCCESS);

        entry.setRedeemed_amount(voucher.getRedeemed_amount());
        entry.setRedeemed_quantity(voucher.getRedeemed_quantity());
        entry.setVoucher_code(voucher.getCode());
        entry.setRedeemed_at(LocalDateTime.now());
        entry.setCampaign(campaign.getId());
        entry.setSupplier(supplier.getId());












        entry.setCustomer_latitude(codeWithLatLngVo.getCt());
        entry.setCustomer_longitude(codeWithLatLngVo.getCg());
        entry.setMerchant_latitude(codeWithLatLngVo.getSt());
        entry.setMerchant_longitude(codeWithLatLngVo.getSg());
        entry.setMerchant_name(writtenOffMerchantVo.getSupplier().getName());
        entry.setMerchant_code(writtenOffMerchantVo.getSupplier().getCode());
        entry.setSupplier(writtenOffMerchantVo.getSupplier().getId());






        entry.setCampaign(campaign.getId());
        entry.setCampaign_code(campaign.getCode());
        entry.setCampaign_name(campaign.getName());


        entry.setWritten_off_staff_name(writtenOffMerchantVo.getUser().getReal_name());
        entry.setWritten_off_staff_phone(writtenOffMerchantVo.getUser().getPhone());
        entry.setWritten_off_staff_id(writtenOffMerchantVo.getUser().getId_card());


        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.traveler)){
            Traveler traveler = publishTowhoVo.getTraveler();
            entry.setHolder_real_name(traveler.getName());
            entry.setHolder_id_card(traveler.getIdNo());
            entry.setHolder_phone(traveler.getTel_home());
        }
        if(publishTowhoVo.getToWhoTyp().equals(EnumPublicationObjectType.customer)){
            User user = publishTowhoVo.getUser();
            entry.setHolder_real_name(user.getRealName());
            entry.setHolder_id_card(user.getIdCard());
            entry.setHolder_phone(user.getPhone());
        }





        return Triplet.with(entry,redemption,publishTowhoVo);
    }


















/*


    public Triplet<RedemptionEntry,Redemption,PublishTowhoVo> redeamTicket(VoucherTicket voucher, List<Component> componentVounchList, CodeWithLatLngVo codeWithLatLngVo) {



        PublishTowhoVo publishTowhoVo = new PublishTowhoVo();


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


        Triplet<RedemptionEntry,Redemption,PublishTowhoVo> triplet =createRedemptions(publicationEntry,voucher,campaign,writtenOffMerchantVo, redemption,publishTowhoVo,codeWithLatLngVo);

        RedemptionEntry redemptionEntry = triplet.getValue0();
        redemptionEntry = redemptionEntryRepository.save(redemptionEntry);




        voucher.setActive(false);
        voucher.setStatus(EnumVoucherStatus.Redeemed);

        voucherService.update(voucher,EnumEvents.redemption$succeeded);




        eventPublisher.publishEvent(new OnVoucherRedeamedEvent(new User(),
                null, EnumEvents.redemption$succeeded));


        return Triplet.with(redemptionEntry,redemption,publishTowhoVo);
    }*/


}

package com.lt.dom.serviceOtc;


import com.google.gson.Gson;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Campaign_inactiveException;
import com.lt.dom.error.ExistException;
import com.lt.dom.notification.event.OnChargePaidCompleteEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.UserVo;
import com.lt.dom.vo.VoucherPublicationPaymentVo;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentFreeServiceImpl {

    org.slf4j.Logger logger = LoggerFactory.getLogger(PaymentFreeServiceImpl.class);

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private BalanceServiceImpl balanceService;

    @Autowired
    private ChargeRepository chargeRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CampaignRepository campaignRepository;


    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RefundRepository refundRepository;

    @Autowired
    private ApplicationFeeRepository applicationFeeRepository;

    @Autowired
    private PassServiceImpl passService;

    @Autowired
    private ApplicationFeeServiceImpl applicationFeeService;
    @Autowired
    private PublicationServiceImpl publicationService;
    @Autowired
    private ComponentRightServiceImpl componentRightService;
    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ComponentRepository componentRepository;


    @Autowired
    private ComponentRightResolveServiceImpl componentRightResolveService;



    public Charge createCharge(String ip, Payment payment, Reservation reservation, long totalproce, String metadata, UserVo userVo) {

        if(reservation.getStatus().equals(EnumBookingStatus.PAID)){

            logger.debug("已经支付 ------------------------");
            System.out.println("已经支付");
            throw new ExistException(Enumfailures.payment_login_error,"已经支付,无需重复支付");
        }


                Charge charge = new Charge();
                charge.setChannel(EnumPayChannel.free);
                charge.setPaid(false);
                charge.setApp("freeType");
                charge.setClientIp(ip);
                charge.setAmount(Double.valueOf(totalproce).intValue());

                charge.setLivemode(false);
                charge.setPayment_code(payment.getCode());

                charge.setCreated(LocalDateTime.now());

                charge.setBody("freeType");
                charge.setPayer(userVo.getUser_id());
                charge.setCurrency("currency");
                charge.setCode(idGenService.chargeCode());
                charge.setStatus(EnumChargeStatus.pending);
                charge.setApplication_fee_amount(0);
                charge.setBooking(reservation.getId());
                charge.setMetadata(metadata);

                chargeRepository.save(charge);



                reservation.setStatus(EnumBookingStatus.Cancelled);
                reservation.setPaymentMethod(charge.getChannel());
                reservationRepository.save(reservation);



                return charge;



        }





    public void freePayFree(Reservation reservation, UserVo userVo) {

        reservation.setStatus(EnumBookingStatus.Submitted_Confirmed);

        reservation.setPaied_at(LocalDateTime.now());
        reservationRepository.save(reservation);


        componentRightResolveService.resolve(reservation,reservation.getFollowupPaid());


    }


    public void freePay(Reservation reservation, UserVo userVo) {



        Payment payment = paymentService.createPayment(new Gson().toJson(Arrays.asList(EnumPayChannel.wx)),0,userVo,reservation);
        payment.setRecipient(reservation.getSupplier());


        int totalAmount = payment.getAmount();
        // 微信的支付单位是分所以要转换一些单位
        long  totalproce =Long.parseUnsignedLong(totalAmount+"");//Long.parseUnsignedLong(totalAmount*100+"");


        Gson gson = new Gson();
        ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();

        chargeMetadataVo.setBooking(reservation.getId());
        String metadata = gson.toJson(chargeMetadataVo);


        Charge  charge = createCharge("ip",payment,reservation,totalproce,metadata,userVo);

        paidChage(charge,payment,reservation);

    }













    public void paidChage(Charge charge, Payment payment,Reservation reservation) {



        List<Integer> fees = applicationFeeService.getFees();


        Balance appBalance = balanceService.AppBalance();


        //Balance balance = balance(payment.getRecipient(),EnumUserType.business);


        charge.setPaid(true);
        charge.setStatus(EnumChargeStatus.succeeded);
        charge.setTimePaid(LocalDateTime.now());
        charge.setPayment(payment.getId());

        charge = chargeRepository.save(charge);





        BalanceTransaction balanceTransaction = new BalanceTransaction();
        balanceTransaction.setCode(idGenService.tranNo());
        balanceTransaction.setAmount(charge.getAmount());
        balanceTransaction.setBalance(appBalance.getId());
        balanceTransaction.setSource(charge.getId());
        balanceTransaction.setSourceId(charge.getCode());
        balanceTransaction.setFlowType(EnumTranType.payment);
        balanceTransaction.setStatus(EnumBalanceTranStatus.available);
        balanceTransaction.setPosted_at(LocalDateTime.now());
        balanceTransaction = transactionRepository.save(balanceTransaction);











        BalanceTransaction finalBalanceTransaction = balanceTransaction;
        Charge finalCharge = charge;
        Balance finalBalance = appBalance;
        Charge finalCharge1 = charge;
        List<Pair<ApplicationFee,BalanceTransaction>> applicationFees = fees.stream().map(x->{
            ApplicationFee applicationFee = new ApplicationFee();
            applicationFee.setAmount(x);
            applicationFee.setBalance(finalBalance.getId());
            applicationFee.setBalanceTransaction(finalBalanceTransaction.getId());
            applicationFee.setCharge(finalCharge.getId());



            BalanceTransaction balanceTransaction_ = new BalanceTransaction();
            balanceTransaction_.setCode(idGenService.tranNo());
            balanceTransaction_.setAmount(x);
            balanceTransaction_.setBalance(appBalance.getId());
            balanceTransaction_.setSource(finalCharge1.getId());
            balanceTransaction_.setSourceId(finalCharge1.getCode());
            // balanceTransaction.setTransaction_no(charge.getAmount());
            balanceTransaction_.setFlowType(EnumTranType.application_fee);
            balanceTransaction_.setStatus(EnumBalanceTranStatus.available);
            balanceTransaction_.setFee(0);
            balanceTransaction_.setNet(balanceTransaction_.getAmount()-balanceTransaction_.getFee());
            balanceTransaction_.setPosted_at(LocalDateTime.now());
            return Pair.with(applicationFee,balanceTransaction_);
        }).collect(Collectors.toList());

        List<ApplicationFee> applicationFeeList = applicationFeeRepository.saveAll(applicationFees.stream().map(x->x.getValue0()).collect(Collectors.toList()));


        transactionRepository.saveAll(applicationFees.stream().map(x->x.getValue1()).collect(Collectors.toList()));



        balanceTransaction.setFee(applicationFeeList.stream().mapToInt(x->x.getAmount()).sum());
        balanceTransaction.setNet(balanceTransaction.getAmount()- balanceTransaction.getFee());
        balanceTransaction = transactionRepository.save(balanceTransaction);

        balanceService.updateBalance(appBalance,balanceTransaction.getNet());





        Gson gson = new Gson();
        ChargeMetadataVo chargeMetadataVo = gson.fromJson(charge.getMetadata(),ChargeMetadataVo.class);

        if(chargeMetadataVo.getVoucher()!= null){

            Optional<Campaign> validatorOptional = campaignRepository.findById(chargeMetadataVo.getCampaign());
            if(validatorOptional.isEmpty()) {
                System.out.println("找不到消费活动");
                throw new BookNotFoundException(1,Campaign.class.getSimpleName());
            }
            Campaign campaign = validatorOptional.get();
            if(!validatorOptional.get().isActive()) {
                throw new Campaign_inactiveException(campaign.getId(),Campaign.class.getSimpleName(),"活动未开放");
            }




            PublishTowhoVo publishTowhoVo = new PublishTowhoVo();
            Optional<User> objectUser = userRepository.findById(charge.getPayer());
            publishTowhoVo.setToWhoTyp(EnumPublicationObjectType.customer);
            publishTowhoVo.setUser(objectUser.get());
            publishTowhoVo.setToWho(publishTowhoVo.getUser().getId());

            VoucherPublicationPaymentVo voucherPublicationPaymentVo = new VoucherPublicationPaymentVo();

            voucherPublicationPaymentVo.setFree(false);
            voucherPublicationPaymentVo.setPaied(true);
            voucherPublicationPaymentVo.setCharge(charge);
            Session session = new Session();

            Quartet<PublicationEntry, Voucher,Campaign, PublishTowhoVo> voucherPair = publicationService.CreatePublication(campaign,null,session,publishTowhoVo,voucherPublicationPaymentVo);




            Voucher voucher = voucherPair.getValue1();
            chargeMetadataVo.setVoucher(voucher.getId());


        }

        charge.setMetadata(gson.toJson(chargeMetadataVo));
        charge.setTransactionNo(balanceTransaction.getCode());
        charge = chargeRepository.save(charge);






        reservation.setStatus(EnumBookingStatus.Submitted_Confirmed);

        reservation.setPaied_at(LocalDateTime.now());
        reservationRepository.save(reservation);



        componentRightResolveService.resolve(reservation,reservation.getFollowupPaid());




        // componentRightService.createComponentRight()
        eventPublisher.publishEvent(new OnChargePaidCompleteEvent(new User(),
                null, EnumEvents.charge$succeeded));
    }



}

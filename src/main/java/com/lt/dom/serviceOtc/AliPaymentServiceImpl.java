package com.lt.dom.serviceOtc;


import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.google.gson.Gson;
import com.lt.dom.config.pay.AlipayProperties;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Campaign_inactiveException;
import com.lt.dom.notification.event.OnChargePaidCompleteEvent;
import com.lt.dom.notification.event.OnRefundCompleteEvent;
import com.lt.dom.notification.event.OnRefundCreatedEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.UserVo;
import com.lt.dom.vo.VoucherPublicationPaymentVo;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AliPaymentServiceImpl {
    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private PaymentRepository paymentRepository;

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
    private BalanceRepository balanceRepository;

    @Autowired
    private ApplicationFeeServiceImpl applicationFeeService;
    @Autowired
    private PublicationServiceImpl publicationService;

    @Resource
    private AlipayClient alipayClient;

    @Resource
    private AlipayProperties alipayProperties;


    public AlipayTradePrecreateResponse createCharge(String ip, String code, Reservation reservation, long totalproce, UserVo userVo, String metadata)  {




        String chargeCode = idGenService.chargeCode();


        String subject = chargeCode+"海贼王";

        AlipayTradePrecreateModel model=new AlipayTradePrecreateModel();
        model.setOutTradeNo(chargeCode);             //商户订单号
        //   model.setProductCode("QUICK_WAP_WAY");  //手机网站支付销售码
        model.setSubject(subject);              //订单标题
        model.setTotalAmount(reservation.getAmount()+"");            //支付金额
        // model.setTimeoutExpress("2h");           //订单支付超时时间
        //    model.setStoreId("123");
        //   model.setBuyerLogonId("fddknu8195@sandbox.com");

        AlipayTradePrecreateRequest request=new AlipayTradePrecreateRequest ();
        request.setBizModel(model);
        //    request.setReturnUrl(alipayProperties.getReturnUrl());
        request.setNotifyUrl(alipayProperties.getNotifyUrl());

        AlipayTradePrecreateResponse response=null;
        try{
            response= alipayClient.execute(request);



            Charge charge = new Charge();
            charge.setChannel(EnumPayChannel.alipay_qr);
            charge.setPaid(false);
            charge.setApp(alipayProperties.getAppId());
            charge.setClientIp(ip);
            charge.setAmount(Double.valueOf(totalproce).intValue());

            charge.setLivemode(false);
            charge.setPayment_code(code);

            charge.setCreated(LocalDateTime.now());

            charge.setBody(subject);
            charge.setPayer(userVo.getUser_id());
            charge.setCurrency("currency");
            charge.setCode(chargeCode);
            charge.setStatus(EnumChargeStatus.pending);
            charge.setApplication_fee_amount(1);
            charge.setBooking(reservation.getId());
            charge.setMetadata(metadata);
            chargeRepository.save(charge);


            reservation.setStatus(EnumBookingStatus.Pending);
            reservationRepository.save(reservation);


        }catch (Exception e){
            e.printStackTrace();
        }


        return response;


        }

    public Charge getOneOrderStatusByPayNo(String out_trade_no) {

        Optional<Charge> optional = chargeRepository.findByCode(out_trade_no);
        Charge charge = optional.get();


        return charge;
    }


    public Balance AppBalance() {
        Optional<Balance> balanceOptional = balanceRepository.findByType(EnumUserType.app);

        return balanceOptional.get();

    }

    public void setupData() {
        Balance balance = new Balance();
        balance.setType(EnumUserType.app);
        balance.setAvailable_amount(0);
        balance.setStatus(EnumFinancialAccountStatus.open);
        balanceRepository.save(balance);

    }

    public Balance balance(long user, EnumUserType payment) {
        Optional<Balance> balanceOptional = balanceRepository.findByUserAndType(user,payment);
        if(balanceOptional.isEmpty()){
            Balance balance = new Balance();
            balance.setUser(user);
            balance.setType(payment);
            balance.setAvailable_amount(0);
            balance.setStatus(EnumFinancialAccountStatus.open);
            return balanceRepository.save(balance);
        }

        return balanceOptional.get();

    }
    public Balance updateBalance(Balance balance, int net) {

            balance.setAvailable_amount(net+balance.getAvailable_amount());

            return balanceRepository.save(balance);
        }

    public void paidChage(Charge charge, Payment payment) {



        List<Integer> fees = applicationFeeService.getFees();


        Balance appBalance = AppBalance();


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
        // balanceTransaction.setTransaction_no(charge.getAmount());
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

        updateBalance(appBalance,balanceTransaction.getNet());





        Gson gson = new Gson();
        ChargeMetadataVo chargeMetadataVo = gson.fromJson(charge.getMetadata(),ChargeMetadataVo.class);


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
        Quartet<PublicationEntry, Voucher,Campaign, PublishTowhoVo> voucherPair =
                publicationService.CreatePublication(campaign,null,session,publishTowhoVo,voucherPublicationPaymentVo);




        Voucher voucher = voucherPair.getValue1();
        chargeMetadataVo.setVoucher(voucher.getId());


        charge.setMetadata(gson.toJson(chargeMetadataVo));
        charge.setTransactionNo(balanceTransaction.getCode());
        charge = chargeRepository.save(charge);


/*

        BalanceTransaction balanceTransaction_tax = new BalanceTransaction();
        balanceTransaction_tax.setCode(idGenService.tranNo());
        balanceTransaction_tax.setAmount(balanceTransaction_.getFee());
        balanceTransaction_tax.setBalance(appBalance.getId());
        balanceTransaction_tax.setSource(charge.getId());
        balanceTransaction_tax.setSourceId(charge.getCode());
        // balanceTransaction.setTransaction_no(charge.getAmount());
        balanceTransaction_tax.setFlowType(EnumTranType.application_fee);
        balanceTransaction_tax.setStatus(EnumBalanceTranStatus.available);
        balanceTransaction_tax.setFee(0);
        balanceTransaction_tax.setNet(balanceTransaction_.getFee());
        balanceTransaction_tax = transactionRepository.save(balanceTransaction_tax);
*/


       // Balance customerBalance = balance(charge.getCustomer(),EnumUserType.customer);

/*
        if( optionalBalance.isPresent()){
            throw new Exception();

        }*/




/*
        TransactionEntry transactionEntry = new TransactionEntry();
        transactionEntry.setTransactionId(transaction.getId());
        transactionEntry.setType(EnumTranType.payment); //入账
        transactionEntry.setAmount(charge.getAmount());
        transactionEntry.setAvailable_balance(balance.getCash());
        //transactionEntry.setUser(charge.getCustomer());
        transactionEntry.setBalance(balance.getId());

        transactionEntry = transactionEntryRepository.save(transactionEntry);  //  首先结算到 产品发布者的结算账户里，

*/

        eventPublisher.publishEvent(new OnChargePaidCompleteEvent(new User(),
                null, EnumEvents.charge$pending));
        }





    public Refund refundCompleted(Refund refund) {


        Optional<Charge> optional = chargeRepository.findById(refund.getCharge());


        Charge charge = optional.get();//

/*        Optional<Payment> optionalPayment = paymentRepository.findById(charge.getOrderNo());
        Payment payment = optionalPayment.get();//*/
       // Balance balance = balance(payment.getRecipient(),EnumUserType.business);


        Balance balance = AppBalance();

        charge.setRefunded(true);

        charge = chargeRepository.save(charge);

        refund.setStatus(EnumRefundStatus.succeeded);
        refundRepository.save(refund);

        BalanceTransaction balanceTransaction = new BalanceTransaction();
        balanceTransaction.setAmount(charge.getAmount());
        balanceTransaction.setSource(refund.getId());
        balanceTransaction.setSourceId(refund.getCode());
        balanceTransaction.setBalance(balance.getId());
        balanceTransaction.setTransaction_no(charge.getAmount());
        balanceTransaction.setFlowType(EnumTranType.payment_refund);
        balanceTransaction.setStatus(EnumBalanceTranStatus.available);
        balanceTransaction.setFee(refund.getAmount());
        balanceTransaction.setNet(-refund.getAmount());
        balanceTransaction.setPosted_at(LocalDateTime.now());
        balanceTransaction = transactionRepository.save(balanceTransaction);
        updateBalance(balance,balanceTransaction.getNet());



        Optional<Reservation> optionalReservation = reservationRepository.findById(charge.getBooking());
        Reservation reservation =optionalReservation.get();
        reservation.setStatus(EnumBookingStatus.Refunded);
        reservationRepository.save(reservation);



/*
        List<ApplicationFee> applicationFees = applicationFeeRepository.findByCharge(charge.getId());



        applicationFees = applicationFees.stream().map(x->{
            x.setRefunded(true);
            x.setAmount_refunded(x.getAmount());
            x.setRefund(refund.getId());
            return x;
        }).collect(Collectors.toList());

        applicationFeeRepository.saveAll(applicationFees);


        BalanceTransaction balanceTransaction_fee = new BalanceTransaction();
        balanceTransaction_fee.setAmount(charge.getAmount());
        balanceTransaction_fee.setSource(refund.getId());
        balanceTransaction_fee.setSourceId(refund.getCode());
        balanceTransaction_fee.setBalance(balance.getId());
        balanceTransaction_fee.setTransaction_no(charge.getAmount());
        balanceTransaction_fee.setFlowType(EnumTranType.application_fee);
        balanceTransaction_fee.setStatus(EnumBalanceTranStatus.available);
        balanceTransaction_fee.setFee(refund.getAmount());
        balanceTransaction_fee.setNet(-refund.getAmount());
        balanceTransaction = transactionRepository.save(balanceTransaction);

        updateBalance(balance,balanceTransaction.getNet());*/

        Gson gson = new Gson();
        ChargeMetadataVo metadataVo = gson.fromJson(charge.getMetadata(),ChargeMetadataVo.class);


        publicationService.refundPublication(metadataVo.getVoucher());

        eventPublisher.publishEvent(new OnRefundCompleteEvent(new User(),
                charge, EnumEvents.charge$refunded));


        return refund;
    }

    public Payment createPayment(Payment payment) {
        payment.setCode(idGenService.paymentNo());
        payment = paymentRepository.save(payment);
        return payment;
    }


    public Refund createCharge(String refundCode, Charge charge, Reservation reservation) {

        Refund refund = new Refund();
        refund.setCode(refundCode);
        refund.setReason(EnumRefundReason.requested_by_customer);
        refund.setCreated(LocalDateTime.now());
        refund.setStatus(EnumRefundStatus.pending);
        refund.setCharge(charge.getId());
        refund.setCharge_Code(charge.getCode());
        refund.setAmount(charge.getAmount());
        refund =  refundRepository.save(refund);


        reservation.setStatus(EnumBookingStatus.RefundInProgress);
        reservationRepository.save(reservation);

        charge.setRefund(refund.getId());
        charge.setRefundStatus(EnumRefundStatus.pending);
        chargeRepository.save(charge);

        eventPublisher.publishEvent(new OnRefundCreatedEvent(new User(),
                null, EnumEvents.charge$pending));

        return refund;

    }

    public Charge refundCompleted(Charge charge) {
        return null;
    }





}

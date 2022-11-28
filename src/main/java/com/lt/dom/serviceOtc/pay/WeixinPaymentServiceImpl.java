package com.lt.dom.serviceOtc.pay;


import com.github.wxpay.sdk.WXPay;
import com.google.gson.Gson;
import com.lt.dom.controllerOct.MyConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.Campaign_inactiveException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.PaymentException;
import com.lt.dom.notification.event.OnChargePaidCompleteEvent;
import com.lt.dom.notification.event.OnRefundCreatedEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.requestvo.PublishTowhoVo;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.VoucherPublicationPaymentVo;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeixinPaymentServiceImpl {

    org.slf4j.Logger logger = LoggerFactory.getLogger(WeixinPaymentServiceImpl.class);

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
    @Autowired
    private ComponentRightServiceImpl componentRightService;
    @Autowired
    private RoyaltyRuleRepository royaltyRuleRepository;

    @Autowired
    private SplitRepository splitRepository;

    @Autowired
    private ComponentRightResolveServiceImpl componentRightResolveService;



    public Charge createCharge(String auth_code,String ip, String code, Reservation reservation, long totalproce, String metadata) {

        if(reservation.getStatus().equals(EnumBookingStatus.PAID)){

            logger.debug("已经支付 ------------------------");
            System.out.println("已经支付");
            throw new ExistException(Enumfailures.payment_login_error,"已经支付,无需重复支付");
        }


        MyConfig wxPayConfig = null;
        try {
            wxPayConfig = new MyConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        WXPay wxpay = null;
        try {
            wxpay = new WXPay(wxPayConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String body = "123rrrr";
        String currency = "CNY";
        String chargeCode = idGenService.chargeCode();
        Map<String, String> data = new HashMap<String, String>();
        data.put("body",body);
        data.put("out_trade_no", chargeCode);//"2016090910595900000012"
        data.put("device_info", "");
        data.put("fee_type", currency);
        data.put("total_fee", totalproce+"");
        data.put("spbill_create_ip","117.23.108.250");// ip);
       // data.put("notify_url", "http://39.106.34.220:8080/oct/xxxxxx");
      //  data.put("trade_type", "JSAPI");  // 此处指定为扫码支付
      //  data.put("product_id", "12");
        data.put("auth_code", auth_code);


        try {
            Map<String, String> resp_wx = wxpay.microPay(data);


            System.out.println(resp_wx+"给的值——————————");

        System.out.println(data+"给的值");
       //     mapBack.get("err_code_des")
            String returnCode = resp_wx.get("return_code");
            String resultCode = resp_wx.get("result_code");
            if (!"SUCCESS".equals(returnCode) ) {
                throw new PaymentException(Enumfailures.payment_login_error,"付款吗付款失败"+resp_wx.get("return_msg"));
            }


                if(!"SUCCESS".equals(resultCode)){
                    throw new PaymentException(Enumfailures.payment_login_error,"付款吗付款失败"+resp_wx.get("err_code_des"));
                }



            String prepayId = resp_wx.get("transaction_id");




            if(ObjectUtils.isEmpty(prepayId)){

                System.out.println("这个史空啊啊啊啊  transaction_id============="+ prepayId);
                throw new PaymentException(Enumfailures.payment_login_error," 支付失败   ");
            }else{
                System.out.println("不空啊啊，不空啊啊  transaction_id============="+ prepayId);

            }

                Charge charge = new Charge();
                charge.setChannel(EnumPayChannel.wx);
                charge.setPaid(false);
                charge.setApp(wxPayConfig.getAppID());
                charge.setClientIp(ip);
                charge.setAmount(Double.valueOf(totalproce).intValue());

                charge.setLivemode(false);
                charge.setPayment_code(code);

                charge.setCreated(LocalDateTime.now());

                charge.setBody(body);
                charge.setPayer(1l);
                charge.setCurrency(currency);
                charge.setCode(chargeCode);
                charge.setStatus(EnumChargeStatus.pending);
                charge.setApplication_fee_amount(1);
                charge.setBooking(reservation.getId());
                charge.setMetadata(metadata);
                charge.setPrepayId(prepayId);
                chargeRepository.save(charge);



                reservation.setStatus(EnumBookingStatus.PAID);
                reservation.setPaymentMethod(EnumPayChannel.wx);
                reservationRepository.save(reservation);



                return charge;

        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentException(Enumfailures.payment_login_error," 支付失败   ");

        }


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

    public void paidChage(Charge charge, Payment payment,Reservation reservation) {



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
            Quartet<PublicationEntry, Voucher,Campaign, PublishTowhoVo> voucherPair =
                    publicationService.CreatePublication(campaign,null,session,publishTowhoVo,voucherPublicationPaymentVo);




            Voucher voucher = voucherPair.getValue1();
            chargeMetadataVo.setVoucher(voucher.getId());


        }

        charge.setMetadata(gson.toJson(chargeMetadataVo));
        charge.setTransactionNo(balanceTransaction.getCode());
        charge = chargeRepository.save(charge);



        reservation.setStatus(EnumBookingStatus.PAID);
        reservation.setPaied_at(LocalDateTime.now());
        reservationRepository.save(reservation);
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



        componentRightResolveService.resolve(reservation,reservation.getFulfillment_behavior());

       // componentRightService.createComponentRight()
        eventPublisher.publishEvent(new OnChargePaidCompleteEvent(new User(),
                null, EnumEvents.charge$succeeded));
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
    public void refundPending(Refund refund ,Charge charge, Reservation reservation) {
        if(!charge.getChannel().equals(EnumPayChannel.wx)){

            return;
        }
        refund.setRefundType(EnumRefundType.Electronic);

        refund.setSourceType(EnumRefundSourceType.Payment);
        refundRepository.save(refund);

        reservation.setStatus(EnumBookingStatus.RefundInProgress);
        reservationRepository.save(reservation);

        charge.setRefund(refund.getId());
        charge.setRefundStatus(EnumRefundStatus.pending);
        chargeRepository.save(charge);

    }


    public void refundCompleted(Refund refund ,Charge charge, Reservation reservation) {
        if(!charge.getChannel().equals(EnumPayChannel.wx)){

            return;
        }

        reservation.setStatus(EnumBookingStatus.RefundInProgress);
        reservationRepository.save(reservation);


        List<Refund> refundList = refundRepository.findAllByCharge(charge.getId());
        Integer integer = refundList.stream().filter(e->e.getStatus().equals(EnumRefundStatus.succeeded)).mapToInt(e->{
            return e.getRefund_fee();
        }).sum();


        charge.setAmountRefunded(charge.getAmountRefunded()+refund.getRefund_fee());
        //charge.setRefunded(true);
        charge.setRefundStatus(EnumRefundStatus.succeeded);
        chargeRepository.save(charge);

    }



    public Payment createPayment(String enumPayChannels , int amount, long id, Reservation reservation) {

        List<Payment> optionalPayment = paymentRepository.findByReference(reservation.getCode());

        if(optionalPayment.isEmpty()){
            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setBalance(amount);
            payment.setDate(LocalDateTime.now());
            payment.setVoided(false);
            payment.setCustomer(id);

            payment.setReference(reservation.getCode());
            Gson gson = new Gson();

            payment.setPayment_method_json(enumPayChannels);
            payment.setStatus(EnumPaymentStatus.PENDING);
            payment.setCode(idGenService.paymentNo());
            payment.setPayment_flow(EnumPaymentFlow.one_step);
            payment.setSplit(reservation.getPaymentSplit());
            payment = paymentRepository.save(payment);


            if(reservation.getPaymentSplit()){

                List<RoyaltyRule> royaltyRules = royaltyRuleRepository.findAllBySplitCode(reservation.getPaymentSplitCode());

                Payment finalPayment = payment;
                List<Splits> splitsList = royaltyRules.stream().map(e->{
                    Splits splits = new Splits();
                    splits.setType(EnumSpliteType.percentage);
                    splits.setShares(e.getPercent());
                    splits.setBearer_type(EnumSplitBearerType.account);
                    splits.setPayment(finalPayment.getId());
                    return splits;
                }).collect(Collectors.toList());

                splitRepository.saveAll(splitsList);
            }
            return payment;
        }
        Payment payment = optionalPayment.get(0);

        return payment;

    }


}

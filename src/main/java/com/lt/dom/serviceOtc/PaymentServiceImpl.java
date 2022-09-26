package com.lt.dom.serviceOtc;


import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.Gson;
import com.lt.dom.OctResp.PaymentMethodResp;
import com.lt.dom.controllerOct.*;
import com.lt.dom.controllerOct.pay.AlipayController;
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
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PaymentServiceImpl {

    @Autowired
    private com.lt.dom.config.WxPayConfig wxPayConfig;

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
    private BalanceServiceImpl balanceService;
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



    public Map createCharge(String ip, String code, Long booking_id, long totalproce, String openid, UserVo userVo, String metadata) {





        MyConfig myConfig = null;
        try {
            myConfig = new MyConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }

        WXPay wxpay = null;
        try {
            wxpay = new WXPay(myConfig);
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
        data.put("notify_url",wxPayConfig.getTENPAY_ORDER_CALLBACK());
        data.put("trade_type", "JSAPI");  // 此处指定为扫码支付
        data.put("product_id", "12");
        data.put("openid", openid);

        try {
            Map<String, String> resp_wx = wxpay.unifiedOrder(data);


         //resp_wx;

            System.out.println(resp_wx+"给的值——————————");

        System.out.println(data+"给的值");


            Date date=new Date();
            String sec=String.valueOf(date.getTime()/1000);
            Map map = new HashMap();
            map.put("appId", myConfig.getAppID());// 公众号id
            map.put("nonceStr",resp_wx.get("nonce_str"));// 随机字符串
            map.put("timeStamp",sec);
            map.put("package", "prepay_id="+resp_wx.get("prepay_id"));
            map.put("signType", "MD5");
            String sign = WXPayUtil.generateSignature(map,myConfig.getKey(),myConfig.getSignType());
            map.put("paySign", sign);



            PrepayResp prepayResp = new PrepayResp();
            prepayResp.setNonceStr(resp_wx.get("nonce_str"));
          //  prepayResp.setAppid(resp_wx.get("appid"));
            prepayResp.setSign(resp_wx.get("sign"));
            prepayResp.setTradeType(resp_wx.get("trade_type"));
        //    prepayResp.setReturnMsg(resp_wx.get("return_msg"));
        //    prepayResp.setResultCode(resp_wx.get("result_code"));
         //   prepayResp.setMchId(resp_wx.get("mch_id"));
         //   prepayResp.setReturnCode(resp_wx.get("return_code"));
            prepayResp.setPrepayId(resp_wx.get("prepay_id"));
            Long time = System.currentTimeMillis() / 1000;
            String timestamp = time.toString();
            prepayResp.setTimestamp(timestamp);


            Charge charge = new Charge();
            charge.setChannel(EnumPayChannel.wx);
            charge.setPaid(false);
            charge.setApp(myConfig.getAppID());
            charge.setClientIp(ip);
            charge.setAmount(Double.valueOf(totalproce).intValue());

            charge.setLivemode(false);
            charge.setPayment_code(code);

            charge.setCreated(LocalDateTime.now());

            charge.setBody(body);
            charge.setPayer(userVo.getUser_id());
            charge.setCurrency(currency);
            charge.setCode(chargeCode);
            charge.setStatus(EnumChargeStatus.pending);
            charge.setApplication_fee_amount(1);
            charge.setBooking(booking_id);
            charge.setMetadata(metadata);
            chargeRepository.save(charge);



            return map;

         //   return prepayResp;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
        }

    public Charge getOneOrderStatusByPayNo(String out_trade_no) {

        Optional<Charge> optional = chargeRepository.findByCode(out_trade_no);
        Charge charge = optional.get();


        return charge;
    }




    public void setupData() {
        Balance balance = new Balance();
        balance.setType(EnumUserType.app);
        balance.setAvailable_amount(0);
        balance.setStatus(EnumFinancialAccountStatus.open);
        balanceRepository.save(balance);

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

        payment.setStatus(EnumPaymentStatus.COMPLETE);
        paymentRepository.save(payment);
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





    public Refund refundCompleted(Refund refund) {


        Optional<Charge> optional = chargeRepository.findById(refund.getCharge());


        Charge charge = optional.get();//

/*        Optional<Payment> optionalPayment = paymentRepository.findById(charge.getOrderNo());
        Payment payment = optionalPayment.get();//*/
       // Balance balance = balance(payment.getRecipient(),EnumUserType.business);


        Balance balance = balanceService.AppBalance();

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
        balanceService.updateBalance(balance,balanceTransaction.getNet());



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



    public Payment createPayment(String enumPayChannels , int amount, UserVo userVo, Reservation reservation) {
        Optional<Payment> optionalPayment = paymentRepository.findByReference(reservation.getCode());

        if(optionalPayment.isEmpty()){
            Payment payment = new Payment();
            payment.setAmount(amount);
            payment.setBalance(amount);
            payment.setDate(LocalDateTime.now());
            payment.setVoided(false);
            payment.setCustomer(userVo.getUser_id());

            payment.setReference(reservation.getCode());
            Gson gson = new Gson();

            payment.setPayment_method_json(enumPayChannels);
            payment.setStatus(EnumPaymentStatus.Initialized);
            payment.setCode(idGenService.paymentNo());

            payment.setExpireTime(LocalDateTime.now().plusMinutes(30));

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



            reservation.setStatus(EnumBookingStatus.Payment_Pending);
            reservation = reservationRepository.save(reservation);
            return payment;
        }
        Payment payment = optionalPayment.get();

        return payment;

    }


    public Optional<Payment>  getByBooking(Reservation reservation) {

        Optional<Payment> optionalPayment = paymentRepository.findByReference(reservation.getCode());

        return optionalPayment;

    }

    public List<EntityModel<PaymentMethodResp>> getPaymentMethods(Payment payment) {


        EnumPayChannel[] payChannelList = new Gson().fromJson(payment.getPayment_method_json(),EnumPayChannel[].class);

        List<EntityModel<PaymentMethodResp>>  entityModelList = Arrays.stream(payChannelList).map(e->{

            EntityModel<PaymentMethodResp> entityModel = EntityModel.of(PaymentMethodResp.of(e));

            switch (e){
                case wx:{
                    entityModel.add(linkTo(methodOn(PaymentRestController.class).wxPayRequest(payment.getId(),null)).withSelfRel());
                }
                break;
                case alipay:{
                    entityModel.add(linkTo(methodOn(AlipayController.class).wapPayq_(payment.getId(),null)).withSelfRel());
                }
                case balance:{
                    entityModel.add(linkTo(methodOn(BookingRestController.class).惠民卡支付(payment.getId())).withSelfRel());
                }
                break;
                default:{}
            }

            return entityModel;

        }).collect(Collectors.toList());

        return entityModelList;
    }
}

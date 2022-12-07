package com.lt.dom.serviceOtc.pay;


import com.google.gson.Gson;
import com.lt.dom.notification.event.OnChargePaidCompleteEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.BookingPayPojo;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.UserVo;
import org.javatuples.Pair;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BalancePaymentServiceImpl {

    org.slf4j.Logger logger = LoggerFactory.getLogger(BalancePaymentServiceImpl.class);

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
    private ReservationRepository reservationRepository;

    @Autowired
    private BalanceServiceImpl balanceService;
    @Autowired
    private ApplicationFeeServiceImpl applicationFeeService;

    @Autowired
    private ApplicationFeeRepository applicationFeeRepository;

    @Autowired
    private ComponentRightResolveServiceImpl componentRightResolveService;





    public Charge pay(Payment payment, Reservation reservation, BookingPayPojo transferPojo, UserVo userVo) {



        reservation.setStatus(EnumBookingStatus.Payment_Pending);
        reservation = reservationRepository.save(reservation);



            Charge charge = new Charge();
            charge.setAmount(reservation.getAmount());
            charge.setChannel(EnumPayChannel.balance);
        charge.setBooking(reservation.getId());
        charge.setPayment(payment.getId());
        charge.setStatus(EnumChargeStatus.pending);
        charge.setAmount(payment.getAmount());
        charge.setCode(idGenService.chargeCode());
        charge.setPayer(userVo.getUser_id());
        charge.setCreated(LocalDateTime.now());
        charge.setBody("余额支付的详情");
            charge.setPaid(false);
            charge.setBooking(reservation.getId());


        charge =  chargeRepository.save(charge);

        return charge;

    }







    public void paidChage(Charge charge, Payment payment,Reservation reservation) {



        List<Integer> fees = applicationFeeService.getFees();


        Balance appBalance = balanceService.AppBalance();


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


        charge.setMetadata(gson.toJson(chargeMetadataVo));
        charge.setTransactionNo(balanceTransaction.getCode());
        charge = chargeRepository.save(charge);



        reservation.setStatus(EnumBookingStatus.PAID);
        reservation.setPaied_at(LocalDateTime.now());
        reservationRepository.save(reservation);

        componentRightResolveService.resolve(reservation,reservation.getFulfillment_behavior());

        // componentRightService.createComponentRight()
        eventPublisher.publishEvent(new OnChargePaidCompleteEvent(new User(),
                null, EnumEvents.charge$succeeded));
    }




}

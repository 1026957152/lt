package com.lt.dom.serviceOtc;


import com.lt.dom.notification.event.OnRefundCreatedEvent;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.RefundReq;
import com.lt.dom.otcenum.*;
import com.lt.dom.repository.*;
import com.lt.dom.vo.PlatRefundVo;
import com.lt.dom.vo.RefundReqVo;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RefundServiceImpl {

    org.slf4j.Logger logger = LoggerFactory.getLogger(RefundServiceImpl.class);

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
    private PaymentServiceImpl paymentService;


    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RefundRepository refundRepository;

    private Refund createCharge(String refundCode,Integer amount, Reservation reservation) {

        Refund refund = new Refund();
        refund.setCode(refundCode);
        refund.setReason(EnumRefundReason.requested_by_customer);
        refund.setCreated(LocalDateTime.now());
        refund.setStatus(EnumRefundStatus.pending);
        refund.setBooking(reservation.getId());


        //  refund.setCharge(charge.getId());
        //  refund.setCharge_Code(charge.getCode());



        return refund;

    }

    public Refund createRefund(String refundCode, Reservation reservation, PlatRefundVo platRefundVo) {


/*        List<Payment> payments = paymentRepository.findByReference(reservation.getCode());

        Payment payment = payments.get(0);*/

        Refund refund =null;

        if(platRefundVo.getPlatform().equals(EnumPlatform.TS)){
            refund = createCharge(refundCode,reservation.getAmount(),reservation);

            refund.setUnlinked(true);
            refund.setTransactionId(platRefundVo.getSerial_no());
            refund.setPlatform(platRefundVo.getPlatform());


            refund =  refundRepository.save(refund);
        }
        if(platRefundVo.getPlatform().equals(EnumPlatform.DERECT)){

            List<Charge> chargeList = chargeRepository.findByBooking(reservation.getId());
            Charge charge = chargeList.get(0);


            RefundReqVo refundReq = new RefundReqVo();
            refundReq.setReason(EnumRefundReason.requested_by_customer);

            refundReq.setRefund_fee(charge.getAmount());
            refundReq.setUnlinked(true);
            refundReq.setPlatform(platRefundVo.getPlatform());
            refund =  paymentService.createRefund(refundCode,charge,reservation, refundReq);



        }
        reservation.setStatus(EnumBookingStatus.Refunded);
        reservationRepository.save(reservation);


        return refund;

    }


    public Refund createCharge(String refundCode, Charge charge, Reservation reservation) {


        Refund refund = createCharge(refundCode,charge.getAmount(),reservation);

        refund.setCharge(charge.getId());
        refund.setCharge_Code(charge.getCode());
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



}

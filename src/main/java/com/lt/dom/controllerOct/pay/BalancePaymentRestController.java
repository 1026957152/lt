package com.lt.dom.controllerOct.pay;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.gson.Gson;
import com.lt.dom.config.WxPayConfig;
import com.lt.dom.controllerOct.MyConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumRefundReason;
import com.lt.dom.otcenum.EnumRefundStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.serviceOtc.pay.BalancePaymentServiceImpl;
import com.lt.dom.util.HttpUtils;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/oct")
public class BalancePaymentRestController {

    Logger logger = LoggerFactory.getLogger(BalancePaymentRestController.class);

    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private WxPayConfig wxPayConfig;


    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CampaineAssignToBookingServiceImpl campaineAssignToBookingService;

    @Autowired
    private ApplicationFeeRepository applicationFeeRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ChargeRepository chargeRepository;

    @Autowired
    private IdGenServiceImpl idGenService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BalancePaymentServiceImpl balancePaymentService;

    @Autowired
    private AuthenticationFacade authenticationFacade;


    @PostMapping(value = "/balance_payments/{CHARGE_ID}/charges")
    public Charge doPay(@PathVariable long CHARGE_ID, HttpServletRequest request) {
        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);



        String ip = HttpUtils.getRequestIP(request);

        Optional<Charge> optionalCharge = chargeRepository.findById(CHARGE_ID);

        if(optionalCharge.isEmpty()){
            System.out.println("找不到产品");
            throw new BookNotFoundException(Enumfailures.not_found,"找不到支付");
        }


        Charge charge = optionalCharge.get();



        Optional<Payment> optionalPayment = paymentRepository.findById(charge.getPayment());

        if(optionalPayment.isEmpty()){

            throw new BookNotFoundException(Enumfailures.not_found,"找不到支付");
        }
        Payment payment = optionalPayment.get();



        Optional<Reservation> reservationOptional = reservationRepository.findByCode(payment.getReference());



        if(reservationOptional.isPresent()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到订单");

        }
        Reservation reservation = reservationOptional.get();


        balancePaymentService.paidChage(charge,payment,reservation);


        return charge;
    }





}
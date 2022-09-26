package com.lt.dom.controllerOct;

import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;


import com.google.gson.Gson;
import com.lt.dom.config.WxPayConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.ExistException;
import com.lt.dom.error.PaymentException;
import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumRefundReason;
import com.lt.dom.otcenum.EnumRefundStatus;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
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
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/oct")
public class PaymentRestController {

    Logger logger = LoggerFactory.getLogger(PaymentRestController.class);

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
    private OpenidRepository openidRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;



    @GetMapping(value = "/payments", produces = "application/json")
    public ResponseEntity<PagedModel> listCampaigns(Pageable pageable, PagedResourcesAssembler<EntityModel<Payment>> assembler) {
            Page<Payment> documents = paymentRepository.findAll(pageable);
            return ResponseEntity.ok(assembler.toModel(documents.map(x->{
                EntityModel entityModel = EntityModel.of(x);

             entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(x.getId(),null)).withRel("createRufund"));

                return entityModel;
            })));
    }

    @GetMapping(value = "/application_fees", produces = "application/json")
    public ResponseEntity<PagedModel<ApplicationFee>> getApplicationFeesList(Pageable pageable, PagedResourcesAssembler<EntityModel<ApplicationFee>> assembler) {
        Page<ApplicationFee> documents = applicationFeeRepository.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(documents.map(x->{
            EntityModel entityModel = EntityModel.of(x);
          //  entityModel.add(linkTo(methodOn(PaymentRestController.class).refund(x.getId(),null)).withRel("createRufund"));
            return entityModel;
        })));
    }





    @RequestMapping(value = "/payments/{PAYMENT_ID}/charges")
    public Map<String, String> wxPayRequest(@PathVariable long PAYMENT_ID,HttpServletRequest request) {


        String ip = HttpUtils.getRequestIP(request);

        Optional<Payment> optionalReservation = paymentRepository.findById(PAYMENT_ID);

        if(optionalReservation.isEmpty()){
            System.out.println("找不到产品");
            throw new BookNotFoundException(PAYMENT_ID,Product.class.getSimpleName());
        }


        Payment payment = optionalReservation.get();


        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);

        Optional<Openid> optional = openidRepository.findByUserIdAndLink(userVo.getUser_id(),true);

        if(optional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"未找到产品");
        }


        Optional<Reservation> reservationOptional = reservationRepository.findByCode(payment.getReference());


        if(reservationOptional.isEmpty()){
            throw new BookNotFoundException(Enumfailures.not_found,"找不到支付相关的订单");
        }
        Reservation reservation = reservationOptional.get();

        ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();
      //  chargeMetadataVo.setCampaign(campaign.getId());
     //   chargeMetadataVo.setCampaign_code(campaign.getCode());
     //   chargeMetadataVo.setVolume_up_voucher(voucher.getId());
        chargeMetadataVo.setPayer(userVo.getUser_id());
       // chargeMetadataVo.setBooking(reservation.getId());
        String metadata = new Gson().toJson(chargeMetadataVo);
        Map  charge = paymentService.createCharge(ip,payment.getCode(),reservation.getId(),payment.getAmount(),optional.get().getOpenid(),userVo,metadata);




        return charge;
    }




    @PostMapping(value = "/xxxxxx")
    public String wxPayNotify(HttpServletRequest request) {



        System.out.println("================这里是oct 支付异步返回");
        String resXml = "";
        try {
            InputStream inputStream = request.getInputStream();
            //将InputStream转换成xmlString
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            resXml = sb.toString();
            String result = payBack(resXml);
            return result;
        } catch (Exception e) {
            //logger.info("微信手机支付失败:" + e.getMessage());
            String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            return result;
        }
    }




    public String payBack(String resXml) {

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

/*
          WxConfigUtil config = null;
        try {
            config = new WxConfigUtil(wxPayConfig.getCertPath(),wxPayConfig.getAppId(),wxPayConfig.getMchId(),wxPayConfig.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        WXPay wxpay = new WXPay(config);*/


        String xmlBack = "";
        Map<String, String> notifyMap = null;
        try {
            notifyMap = WXPayUtil.xmlToMap(resXml);// 调用官方 SDK 转换成 map 类型数据
            if (wxpay.isPayResultNotifySignatureValid(notifyMap)) {// 验证签名是否有效，有效则进一步处理
                String return_code = notifyMap.get("return_code");// 状态
                if (return_code.equals("SUCCESS")) {
                    String out_trade_no = notifyMap.get("out_trade_no");// 商户订单号
                    String type = notifyMap.get("attach");
                    if (out_trade_no != null) {
                        // 根据交易编号加锁，处理高并发
                        synchronized (out_trade_no) {
                            // 该代码块为业务逻辑，可改成自己的
                            Charge charge = paymentService.getOneOrderStatusByPayNo(out_trade_no);
                            if(!charge.getPaid()){

                                Optional<Payment> payment = paymentRepository.findByCode(charge.getPayment_code());
                                Optional<Reservation> reservationOptional = reservationRepository.findByCode(payment.get().getReference());

                                paymentService.paidChage(charge,payment.get(),reservationOptional.get());
                            }else{
                               logger.info("该订单已支付处理,交易编号为: " + out_trade_no);

                            }
/*
                           if (status == null || status != 2) {
                                // 修改订单状态
                               // orderBiz.order(type,out_trade_no);
                            } else {
                         //       logger.info("该订单已支付处理,交易编号为: " + out_trade_no);
                            }*/

                        }
                        xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
                         logger.info("微信手机支付回调失败订单号为空");
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }
                }
                return xmlBack;
            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                 logger.info("手机支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("手机支付回调通知失败:"+ e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }





/*
    public WxPayService wxPayService() {
        com.github.binarywang.wxpay.config.WxPayConfig payConfig = new com.github.binarywang.wxpay.config.WxPayConfig();
        payConfig.setAppId(wxPayConfig.getAppId());
        payConfig.setMchId(wxPayConfig.getMchId());
        payConfig.setMchKey(wxPayConfig.getMchId());
        payConfig.setKeyPath(wxPayConfig.getKey());
        //payConfig.setPrivateKeyPath(wxPayConfig.getPrivateKeyPath());
        //payConfig.setPrivateCertPath(wxPayConfig.getPrivateCertPath());
        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);
        payConfig.setSignType("MD5");

        WxPayService wxPayService = new WxPayServiceImpl();
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }
*/



    /**
     * 退款
     *
     * @param CHARGE_ID
     */
    @PostMapping(value = "/payments/{CHARGE_ID}/refunds")
    public ResponseEntity<Void> refund(@PathVariable long CHARGE_ID,
                       HttpServletRequest request) {
        // totalFee 必须要以分为单位,退款的价格可以这里只做的全部退款

        Optional<Charge> optionalCharge = chargeRepository.findById(CHARGE_ID);
        if(optionalCharge.isEmpty()){
            System.out.println("找不到产品");
            throw new BookNotFoundException(CHARGE_ID,Product.class.getSimpleName());
        }
        Charge charge = optionalCharge.get();
        if(charge.getRefunded()){
            throw new ExistException(Enumfailures.general_exists_error,"已经退款，无需退款");
        }

        if(EnumRefundStatus.pending.equals(charge.getRefundStatus())){
            throw new ExistException(Enumfailures.general_exists_error,"退款中，无需在提提交");
        }
        Optional<Payment> payment = paymentRepository.findById(optionalCharge.get().getPayment());
        Optional<Reservation> reservation = reservationRepository.findById(optionalCharge.get().getBooking());









        String tradeNo = charge.getCode();///transaction_id;
        Integer totalFee = charge.getAmount();//


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


/*        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setTransactionId(tradeNo);
        wxPayRefundRequest.setOutRefundNo(String.valueOf(System.currentTimeMillis()));
        wxPayRefundRequest.setTotalFee(totalFee);
        wxPayRefundRequest.setRefundFee(totalFee);
        wxPayRefundRequest.setNotifyUrl(wxPayConfig.getRefundNotifyUrl());*/

        String out_trade_no = "";   //退款订单
        int   all_total_fee = 100;    //订单金额(这里是按分来计算的)
        int      refund_fee = 0;      //退款金额(这里是按分来计算的)

        Map<String, String> paramesMap = new HashMap<>();
/*        paramesMap.put("out_trade_no",charge.getCode());
        paramesMap.put("all_total_fee", charge.getAmount()+"");//"2016090910595900000012"
        paramesMap.put("refund_fee", charge.getAmount()+"");*/

 /*       data.put("spbill_create_ip","117.23.108.250");// ip);
        data.put("notify_url", "http://39.106.34.220:8080/oct/xxxxxx");
        data.put("trade_type", "JSAPI");  // 此处指定为扫码支付
        data.put("product_id", "12");
        data.put("openid", openid);*/
        try {

   /*         dataMap.put("appid","wx#################");

            dataMap.put("mch_id","137#############");

//自行实现该随机串

            dataMap.put("nonce_str",Core.MD5("12344"));

            dataMap.put("out_trade_no","P190808170038402889c5318502");

            dataMap.put("out_refund_no","P190808170038402889c5318502");

            dataMap.put("total_fee","1");

            dataMap.put("refund_fee","1");

            dataMap.put("refund_desc","退款");


        //    SortedMap<String, Object> paramesMap = new TreeMap<>();
*/

            String refundCode = idGenService.refundCode();





         paramesMap.put("appid", wxPayConfig.getAppID());
            paramesMap.put("mch_id", wxPayConfig.getMchID());
            paramesMap.put("nonce_str", "date");
            paramesMap.put("out_trade_no", tradeNo);//商户订单号，也可用微信的订单号，二选已，具体看文档
            paramesMap.put("out_refund_no", refundCode);//商户退款单号，商户方自己生成
            paramesMap.put("total_fee", charge.getAmount()+"");//订单总金额
            paramesMap.put("refund_fee", charge.getAmount()+"");//退款金额
            paramesMap.put("refund_desc","退款");
            paramesMap.put("notify_url","http://39.106.34.220:8080/oct/wechat/pay/refund_notify");

            String sign = WXPayUtil.generateSignature(paramesMap,wxPayConfig.getKey(),wxPayConfig.getSignType());

            paramesMap.put("sign",sign);//签名，同支付签名一样*/

       //
            Map<String, String> map = wxpay.refund(paramesMap);
       //     WxPayRefundResult refund = null;//wxPayService().refundV2(wxPayRefundRequest);
          //  if (refund.getReturnCode().equals("SUCCESS") && refund.getResultCode().equals("SUCCESS")) {

            System.out.println("=================这里是退款的返回细腻些"+ map);
                if (map.get("return_code").equals("SUCCESS") && map.get("return_code").equals("SUCCESS")) {


                paymentService.createCharge(refundCode,charge,reservation.get());

            }

        } catch(Exception e) {// (WxPayException e) {
            e.printStackTrace();


        }


        return ResponseEntity.ok().build();
       // return null;

        // 实现自己的逻辑
      //  logger.info("退款本地回调:{}", refund);
    }























    /**
     * 退款
     *
     * @param PAYMENT_ID
     */
    @PostMapping(value = "/payments/{PAYMENT_ID}/payouts")
    public void payouts(@PathVariable long PAYMENT_ID,
                       HttpServletRequest request) {
        // totalFee 必须要以分为单位,退款的价格可以这里只做的全部退款



        Optional<Payment> optionalReservation = paymentRepository.findById(PAYMENT_ID);


        if(optionalReservation.isEmpty()){
            System.out.println("找不到产品");
            throw new BookNotFoundException(PAYMENT_ID,Product.class.getSimpleName());
        }


        Payment payment = optionalReservation.get();



        String tradeNo = payment.getCode();///transaction_id;
        Integer totalFee = payment.getAmount();//

        WxPayRefundRequest wxPayRefundRequest = new WxPayRefundRequest();
        wxPayRefundRequest.setTransactionId(tradeNo);
        wxPayRefundRequest.setOutRefundNo(String.valueOf(System.currentTimeMillis()));
        wxPayRefundRequest.setTotalFee(totalFee);
        wxPayRefundRequest.setRefundFee(totalFee);
        wxPayRefundRequest.setNotifyUrl(wxPayConfig.getRefundNotifyUrl());
        try {
            WxPayRefundResult refund = null;//wxPayService().refundV2(wxPayRefundRequest);
            if (refund.getReturnCode().equals("SUCCESS") && refund.getResultCode().equals("SUCCESS")) {

                Refund refund1 = new Refund();
                refund1.setReason(EnumRefundReason.requested_by_customer);
                refund1.setCreated(LocalDateTime.now());
                refund1.setStatus(EnumRefundStatus.pending);
                refund1.setCharge(payment.getId());
                paymentService.createCharge(null,new Charge(),null);


                //  WxPayRefundResult refund
            }

        } catch(Exception e){// (WxPayException e) {
            e.printStackTrace();
        }
        // return null;

        // 实现自己的逻辑
        //  logger.info("退款本地回调:{}", refund);
    }


}
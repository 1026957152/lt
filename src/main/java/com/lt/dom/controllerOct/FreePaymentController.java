package com.lt.dom.controllerOct;

import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.google.gson.Gson;
import com.lt.dom.config.pay.AlipayProperties;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.oct.Charge;
import com.lt.dom.oct.Payment;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Reservation;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.repository.PaymentRepository;
import com.lt.dom.repository.ReservationRepository;
import com.lt.dom.serviceOtc.pay.AliPaymentServiceImpl;
import com.lt.dom.serviceOtc.AuthenticationFacade;
import com.lt.dom.serviceOtc.IdGenServiceImpl;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import com.lt.dom.util.HttpUtils;
import com.lt.dom.util.ZxingBarcodeGenerator;
import com.lt.dom.vo.ChargeMetadataVo;
import com.lt.dom.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/payment_free")
public class FreePaymentController {
    Logger logger = LoggerFactory.getLogger(FreePaymentController.class);

    @Resource
    private AlipayClient alipayClient;
 
    @Resource
    private AlipayProperties alipayProperties;


    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ReservationRepository reservationRepository;


    @Autowired
    private AliPaymentServiceImpl aliPaymentService;

    @Autowired
    private AuthenticationFacade authenticationFacade;


    @Resource
    private IdGenServiceImpl idGenService;


    @Autowired
    private PaymentServiceImpl paymentService;




    @RequestMapping(value = "/wapPayq/{PAYMENT_ID}",produces = MediaType.IMAGE_PNG_VALUE)       //手机网站支付
    public ResponseEntity<BufferedImage> wapPayq_(@PathVariable long PAYMENT_ID,HttpServletRequest request_)  {

        // 输入一个 支付类型， 然后，进行 支付
        Authentication authentication = authenticationFacade.getAuthentication();

        UserVo userVo = authenticationFacade.getUserVo(authentication);



        String ip = HttpUtils.getRequestIP(request_);
        Optional<Payment> paymentOptional = paymentRepository.findById(PAYMENT_ID);

        if(paymentOptional.isEmpty()){
            System.out.println("找不到产品");
            throw new BookNotFoundException(Enumfailures.not_found, "找不到");
        }
        Payment payment = paymentOptional.get();

        Optional<Reservation> optionalReservation = reservationRepository.findByCode(payment.getReference());


        if(optionalReservation.isEmpty()) {
            throw new BookNotFoundException(payment.getReference(),Product.class.getSimpleName());
        }


        Reservation reservation = optionalReservation.get();


        ChargeMetadataVo chargeMetadataVo = new ChargeMetadataVo();
        // chargeMetadataVo.setCampaign(campaign.getId());
        // chargeMetadataVo.setCampaign_code(campaign.getCode());
        //  chargeMetadataVo.setVolume_up_voucher(voucher.getId());
        chargeMetadataVo.setPayer(userVo.getUser_id());
        chargeMetadataVo.setPayer(userVo.getUser_id());
        chargeMetadataVo.setBooking(reservation.getId());
        String metadata = (new Gson()).toJson(chargeMetadataVo);


        AlipayTradePrecreateResponse  response = aliPaymentService.createCharge(ip,payment.getCode(),reservation,reservation.getAmount(),userVo,metadata);


        System.out.print(response.getBody());
        System.out.print(response.getQrCode());
        assert response != null;
        //return response.getBody();

        try {
            return okResponse(ZxingBarcodeGenerator.generateQRCodeImage(response.getQrCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }




    @RequestMapping(value = "/wapPayq",produces = MediaType.IMAGE_PNG_VALUE)       //手机网站支付
    public ResponseEntity<BufferedImage> wapPayq() throws Exception {
        AlipayTradePrecreateModel model=new AlipayTradePrecreateModel();
        model.setOutTradeNo(idGenService.chargeCode());             //商户订单号
     //   model.setProductCode("QUICK_WAP_WAY");  //手机网站支付销售码
        model.setSubject("海贼王");              //订单标题
        model.setTotalAmount("1");            //支付金额
       // model.setTimeoutExpress("2h");           //订单支付超时时间
    //    model.setStoreId("123");
     //   model.setBuyerLogonId("fddknu8195@sandbox.com");

        AlipayTradePrecreateRequest  request=new AlipayTradePrecreateRequest ();
        request.setBizModel(model);
   //    request.setReturnUrl(alipayProperties.getReturnUrl());
        request.setNotifyUrl(alipayProperties.getNotifyUrl());

        AlipayTradePrecreateResponse response=null;
        try{
            response= alipayClient.execute(request);
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.print(response.getBody());
        System.out.print(response.getQrCode());
        assert response != null;
        //return response.getBody();

        return okResponse(ZxingBarcodeGenerator.generateQRCodeImage(response.getQrCode()));

    }



    private ResponseEntity<BufferedImage> okResponse(BufferedImage image) {
        return new ResponseEntity<>(image, HttpStatus.OK);
    }



    @RequestMapping("/query")
    public String query() throws Exception{         //支付结果查询
        AlipayTradeQueryModel model=new AlipayTradeQueryModel();
        model.setOutTradeNo("101");
 
        AlipayTradeQueryRequest request=new AlipayTradeQueryRequest();
        request.setBizModel(model);
 
        return alipayClient.execute(request).getTradeStatus();
    }
 
    @RequestMapping("/close")
    public String close() throws Exception{       //订单关闭
        AlipayTradeCloseModel model=new AlipayTradeCloseModel();
        model.setOutTradeNo("101");
 
        AlipayTradeCloseRequest request=new AlipayTradeCloseRequest();
        request.setBizModel(model);
 
        return alipayClient.execute(request).getBody();
    }
 
    @RequestMapping("/refund")
    public String refund() throws Exception{      //订单退款
        AlipayTradeRefundModel model=new AlipayTradeRefundModel();
        model.setOutTradeNo("101");
        model.setRefundAmount("20");
        model.setOutRequestNo("5");
 
        AlipayTradeRefundRequest refundRequest=new AlipayTradeRefundRequest();
        refundRequest.setBizModel(model);
 
        AlipayTradeRefundResponse refundResponse=alipayClient.execute(refundRequest);
 
        return "退款总金额："+refundResponse.getRefundFee();
    }
 
    @RequestMapping("/refundQuery")
    public String refundQuery() throws Exception{ //订单退款查询
        AlipayTradeFastpayRefundQueryModel model=new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo("101");
        model.setOutRequestNo("5");
 
        AlipayTradeFastpayRefundQueryRequest refundQueryRequest=new AlipayTradeFastpayRefundQueryRequest();
        refundQueryRequest.setBizModel(model);
 
        AlipayTradeFastpayRefundQueryResponse refundQueryResponse=alipayClient.execute(refundQueryRequest);
 
        return "本次退款金额为："+refundQueryResponse.getRefundAmount()+
                "\n订单总金额为："+refundQueryResponse.getTotalAmount();
    }
 
    @RequestMapping("/queryDownload")
    public String downloadUrl() throws Exception{  //对账单下载地址
        AlipayDataDataserviceBillDownloadurlQueryModel model=new AlipayDataDataserviceBillDownloadurlQueryModel();
        model.setBillType("trade");
        model.setBillDate("2020-04-06");
 
        AlipayDataDataserviceBillDownloadurlQueryRequest request=new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizModel(model);
 
        AlipayDataDataserviceBillDownloadurlQueryResponse response=alipayClient.execute(request);
 
        return response.getBillDownloadUrl();
    }
 
    @RequestMapping("/return")
    public String returnUrl(HttpServletRequest request) throws Exception{  //支付成功后跳转接口
        if (check(request.getParameterMap())){
            return "success";
        }else {
            return "false";
        }
    }
 
    @RequestMapping("/notify")
    public void notify(HttpServletRequest request) throws Exception{  //交易结果异步通知，trade_succcess状态下会触发
        if (check(request.getParameterMap())){
            System.out.println(request.getParameter("trade_status"));
            if(request.getParameter("trade_status").equals("TRADE_SUCCESS")){


                String out_trade_no = request.getParameter("out_trade_no");

                synchronized (out_trade_no) {
                    // 该代码块为业务逻辑，可改成自己的
                    Charge charge = paymentService.getOneOrderStatusByPayNo(out_trade_no);
                    if (!charge.getPaid()) {

                        Optional<Payment> paymentOptional = paymentRepository.findByCode(charge.getPayment_code());
                        Payment payment = paymentOptional.get();
                        Optional<Reservation> reservationOptional = reservationRepository.findByCode(payment.getReference());
                        paymentService.paidChage(charge, payment,reservationOptional.get());
                    } else {
                        logger.info("该订单已支付处理,交易编号为: " + out_trade_no);

                    }
                }

            }
            System.out.println("异步通知 "+ Instant.now());
        }else {
            System.out.println("验签失败");
        }
    }


    @PostMapping("/notify_manual/{out_trade_no}")
    public void notify(@PathVariable String  out_trade_no,  HttpServletRequest request) throws Exception{  //交易结果异步通知，trade_succcess状态下会触发


        synchronized (out_trade_no) {
            // 该代码块为业务逻辑，可改成自己的
            Charge charge = paymentService.getOneOrderStatusByPayNo(out_trade_no);
            if (!charge.getPaid()) {

                Optional<Payment> paymentOptional = paymentRepository.findByCode(charge.getPayment_code());
                Payment payment = paymentOptional.get();
                Optional<Reservation> reservationOptional = reservationRepository.findByCode(payment.getReference());
                paymentService.paidChage(charge, payment,reservationOptional.get());
            } else {
                logger.info("该订单已支付处理,交易编号为: " + out_trade_no);

            }
        }
    }
    private boolean check(Map<String,String[]> requestParams) throws Exception{
        Map<String,String> params = new HashMap<>();
 
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
 
            params.put(name, valueStr);
            System.out.println(name+" ==> "+valueStr);
        }
 
        return AlipaySignature.rsaCheckV1(params, alipayProperties.getAlipayPublicKey(),
                alipayProperties.getCharset(), alipayProperties.getSignType()); //调用SDK验证签名
    }
}


package com.lt.dom.controllerOct;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.lt.dom.config.WxConfigUtil;
import com.lt.dom.config.WxPayConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.PaymentException;
import com.lt.dom.oct.Charge;
import com.lt.dom.oct.Payment;
import com.lt.dom.oct.Product;
import com.lt.dom.oct.Refund;
import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.EnumRefundReason;
import com.lt.dom.otcenum.EnumRefundStatus;
import com.lt.dom.repository.*;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/oct")
public class PaymentRestController {


    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private WxPayConfig wxPayConfig;


    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CampaineAssignToBookingServiceImpl campaineAssignToBookingService;

    @Autowired
    private GuideRepository guideRepository;
    @Autowired
    private PaymentRepository paymentRepository;




    @RequestMapping(value = "/payments/{PAYMENT_ID}/charges")
    public Map<String, String> wxPayRequest(@PathVariable long PAYMENT_ID,
                                            HttpServletRequest request
    ) {

        String ip = HttpUtils.getRequestIP(request);


        Optional<Payment> optionalReservation = paymentRepository.findById(PAYMENT_ID);


        if(optionalReservation.isEmpty()){
            System.out.println("找不到产品");
            throw new BookNotFoundException(PAYMENT_ID,Product.class.getSimpleName());
        }


        Payment payment = optionalReservation.get();

        try {

            String orderId =  payment.getCode();
            // double totalAmount = 0.01;
            double totalAmount = payment.getAmount();
            // 微信的支付单位是分所以要转换一些单位
            String totalproce = String.valueOf(Float.parseFloat(totalAmount+"") * 100);
            WxConfigUtil configUtil = new WxConfigUtil(wxPayConfig.getCertPath(),wxPayConfig.getAppId(),wxPayConfig.getMchId(),wxPayConfig.getKey());
            WXPay wxPay = new WXPay(configUtil);
            Map<String, String> data = new HashMap<>();

            // 查询获得订单id
            data.put("appid", configUtil.getAppID()); // 服务商的APPID
            data.put("mch_id", configUtil.getMchID()); // 商户号
            // 这里根据具体业务，查询出不同的子商户号，从而实现不同商户收款。这里只有一个，写在配置文件中
            data.put("sub_mch_id", wxPayConfig.getSubMchId()); // 子商户号
            data.put("nonce_str", WXPayUtil.generateNonceStr());// 随机字符串
            data.put("out_trade_no", orderId); // 商品订单号
            data.put("total_fee", totalproce);  // 总金额
            InetAddress inetAddress = InetAddress.getLocalHost();// 用户端ip
            String spbillCreateIp= inetAddress== null? "": inetAddress.getHostAddress();
            data.put("spbill_create_ip", spbillCreateIp);// 终端IP
            data.put("notify_url", wxPayConfig.getTENPAY_ORDER_CALLBACK());// 回调地址
            data.put("trade_type", wxPayConfig.getTRADE_TYPE_APP());// 交易类型
            String sign= WXPayUtil.generateSignature(data, configUtil.getKey(), WXPayConstants.SignType.MD5);	// 生成第一次签名
            data.put("sign", sign);
            // 使用官方API请求预付订单
            Map<String, String> response = wxPay.unifiedOrder(data);
            String returnCode = response.get("return_code");    // 获取返回码
            Map<String, String> param = new LinkedHashMap<>();
            // 判断返回状态码是否成功
            if (returnCode.equals("SUCCESS")) {
                // 成功后接受微信返回的参数
                String resultCode = response.get("result_code");
                param.put("appid", response.get("sub_appid"));// 子商户应用id
                param.put("partnerid", response.get("sub_mch_id"));// 子商户号
                param.put("package", wxPayConfig.getPackageValue());
                param.put("noncestr", WXPayUtil.generateNonceStr());// 随机字符串
                param.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));     // 时间戳
                if (resultCode.equals("SUCCESS")) {
                    param.put("prepayid", response.get("prepay_id"));// 预支付交易会话 ID
                    String retutnSign = WXPayUtil.generateSignature(param, configUtil.getKey(), WXPayConstants.SignType.MD5);	// 第二次签名
                    param.put("sign", retutnSign);
                    String str1 = WXPayUtil.mapToXml(param);
                    param.put("packages", wxPayConfig.getPackageValue());



                    Charge charge = new Charge();
                    charge.setChannel(EnumPayChannel.wx);
                    charge.setPaid(false);
                    charge.setApp(wxPayConfig.getAppId());
                    charge.setClientIp(ip);
                    charge.setAmount(Double.valueOf(totalAmount).intValue());
                    charge.setOrderNo(payment.getId());
                    charge.setLivemode(false);
                    charge.setOrderId(orderId);

                    charge.setCreated(LocalDateTime.now());
                    charge = paymentService.createCharge(charge);


                    return param;
                } else {
                    throw new PaymentException(PAYMENT_ID,"支付第二次签名请求失败","支付第二次签名请求失败");
                }
            } else {
                throw new PaymentException(PAYMENT_ID,"支付第一次签名请求失败","支付第一次签名请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentException(1,Charge.class.getSimpleName(),e.getMessage());
        }

    }




    @PostMapping(value = "/xxxxx")
    public String wxPayNotify(HttpServletRequest request) {
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


    //  @Override
    public String payBack(String resXml) {
        WxConfigUtil config = null;
        try {
            config = new WxConfigUtil(wxPayConfig.getCertPath(),wxPayConfig.getAppId(),wxPayConfig.getMchId(),wxPayConfig.getKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        WXPay wxpay = new WXPay(config);
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
                            Charge status = paymentService.getOneOrderStatusByPayNo(out_trade_no);
                            if(!status.getPaid()){

                                Optional<Payment> payment = paymentRepository.findByReference(status.getOrderId());
                                paymentService.paidChage(status,payment.get());
                            }else{

                            }
/*                            if (status == null || status != 2) {
                                // 修改订单状态
                               // orderBiz.order(type,out_trade_no);
                            } else {
                         //       logger.info("该订单已支付处理,交易编号为: " + out_trade_no);
                            }*/
                        }
                        xmlBack = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                    } else {
                        //  logger.info("微信手机支付回调失败订单号为空");
                        xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }
                }
                return xmlBack;
            } else {
                // 签名错误，如果数据里没有sign字段，也认为是签名错误
                //  logger.info("手机支付回调通知签名错误");
                xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                return xmlBack;
            }
        } catch (Exception e) {
            //logger.info("手机支付回调通知失败:"+ e);
            xmlBack = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }
        return xmlBack;
    }














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



    /**
     * 退款
     *
     * @param transaction_id
     */
    @PostMapping(value = "/payments/{PAYMENT_ID}/refunds")
    public void refund(@PathVariable long PAYMENT_ID,
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
            WxPayRefundResult refund = wxPayService().refundV2(wxPayRefundRequest);
            if (refund.getReturnCode().equals("SUCCESS") && refund.getResultCode().equals("SUCCESS")) {

                Refund refund1 = new Refund();
                refund1.setReason(EnumRefundReason.requested_by_customer);
                refund1.setCreated(LocalDateTime.now());
                refund1.setStatus(EnumRefundStatus.pending);
                refund1.setCharge(payment.getId());
                paymentService.createCharge(refund1,payment);


                //  WxPayRefundResult refund
            }

        } catch (WxPayException e) {
            e.printStackTrace();
        }
       // return null;

        // 实现自己的逻辑
      //  logger.info("退款本地回调:{}", refund);
    }

    /**
     * 退款回调
     *
     * @param xmlData
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "refund_notify")
    public String refundNotify(@RequestBody String xmlData) {

        paymentService.refundCharge(new Refund());

        // 实现自己的逻辑
    //    logger.info("退款远程回调:{}", xmlData);
        // 必须要返回 SUCCESS 不过有 WxPayNotifyResponse 给整合成了 xml了
        return WxPayNotifyResponse.success("成功");
    }






















    /**
     * 退款
     *
     * @param transaction_id
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
            WxPayRefundResult refund = wxPayService().refundV2(wxPayRefundRequest);
            if (refund.getReturnCode().equals("SUCCESS") && refund.getResultCode().equals("SUCCESS")) {

                Refund refund1 = new Refund();
                refund1.setReason(EnumRefundReason.requested_by_customer);
                refund1.setCreated(LocalDateTime.now());
                refund1.setStatus(EnumRefundStatus.pending);
                refund1.setCharge(payment.getId());
                paymentService.createCharge(refund1,payment);


                //  WxPayRefundResult refund
            }

        } catch (WxPayException e) {
            e.printStackTrace();
        }
        // return null;

        // 实现自己的逻辑
        //  logger.info("退款本地回调:{}", refund);
    }


}
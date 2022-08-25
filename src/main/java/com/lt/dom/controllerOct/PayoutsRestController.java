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
//import com.lt.dom.config.WxConfigUtil;
import com.lt.dom.config.WxPayConfig;
import com.lt.dom.domain.SettleAccount;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.error.PaymentException;
import com.lt.dom.oct.*;
import com.lt.dom.otcReq.PayoutReq;
import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.EnumPayoutMethod;
import com.lt.dom.otcenum.EnumRefundReason;
import com.lt.dom.otcenum.EnumRefundStatus;
import com.lt.dom.repository.GuideRepository;
import com.lt.dom.repository.PaymentRepository;
import com.lt.dom.repository.PayoutRepository;
import com.lt.dom.serviceOtc.CampaineAssignToBookingServiceImpl;
import com.lt.dom.serviceOtc.FileStorageService;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import com.lt.dom.serviceOtc.PayoutServiceImpl;
import com.lt.dom.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class PayoutsRestController {   // 微信的企业支付，


    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private WxPayConfig wxPayConfig;


    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    CampaineAssignToBookingServiceImpl campaineAssignToBookingService;

    @Autowired
    private PayoutRepository payoutRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PayoutServiceImpl payoutService;








    /**
     * 退款
     *
     * @param transaction_id
     */
    @PostMapping(value = "/payouts")
    public void payouts(@PathVariable long PAYMENT_ID,
                        @RequestBody PayoutReq payoutReq) {
        // totalFee 必须要以分为单位,退款的价格可以这里只做的全部退款


        SettleAccount settleAccount = new SettleAccount();


        Payout payout = payoutService.createPayout(payoutReq.getAmount(),settleAccount);

        // 实现自己的逻辑
      //  logger.info("退款本地回调:{}", refund);
    }

















}
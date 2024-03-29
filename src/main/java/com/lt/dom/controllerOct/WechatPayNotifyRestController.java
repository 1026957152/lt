package com.lt.dom.controllerOct;

import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.lt.dom.oct.Refund;
import com.lt.dom.repository.RefundRepository;
import com.lt.dom.serviceOtc.PaymentServiceImpl;
import com.lt.dom.util.WxDecodeUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/oct")
public class WechatPayNotifyRestController {
    Logger logger = LoggerFactory.getLogger(WechatPayNotifyRestController.class);



    @Autowired
    private WxDecodeUtil wxDecodeUtil;
    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private RefundRepository refundRepository;


    /**
     * 退款回调
     *
     * @param xmlData
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/wechat/pay/refund_notify")
    public String refundNotify(@RequestBody String xmlData) {




        String returnXmlMessage = null;
        String notifyXmlData = xmlData;
        System.out.println("ddddddddddd退款的回调函数"+xmlData);

        try {


            Map<String, String> notifyMapData = WXPayUtil.xmlToMap(notifyXmlData);

            System.out.println("xmlToMap退款的回调函数"+notifyMapData);


          //  log.info("[refundAsyncNotify] [xml转换为map数据成功] [notifyMapData:{}]", notifyMapData);

           if (WXPayConstants.SUCCESS.equals(notifyMapData.get("return_code"))) {//  RETURN_CODE
                // 获得加密信息
                String reqInfo = notifyMapData.get("req_info");
                //进行AES解密 获取req_info中包含的相关信息(解密失败会抛出异常)

                String refundDecryptedData = wxDecodeUtil.decryptData(reqInfo);
                Map<String, String> reqInfoMap = WXPayUtil.xmlToMap(refundDecryptedData);
               logger.info("[refundAsyncNotify] [reqInfo解密成功] [reqInfoMap:{}]", reqInfoMap);

               System.out.println("====================="+ reqInfoMap);

               logger.info("[refundAsyncNotify] [reqInfo解密成功] [reqInfoMap:{}]", reqInfoMap);
                // TODO 订单退款成功后相关业务逻辑...

               Optional<Refund> optional = refundRepository.findByCode((String)reqInfoMap.get("out_refund_no"));
               Refund refund = optional.get();





               //支付交易号
               String transactionId = reqInfoMap.get("transaction_id");
               //订单号sys/sysDepart/batchSyncSysDepart
               String outTradeNo = reqInfoMap.get("out_trade_no");
               //退款交易号
               String refundId = reqInfoMap.get("refund_id");
               //退款单号
               String outRefundNo = reqInfoMap.get("out_refund_no");
               //订单金额
               String totalFee = reqInfoMap.get("total_fee");
               String settlementTotalFee = reqInfoMap.get("settlement_total_fee");
               //申请金额
               String refundFee = reqInfoMap.get("refund_fee");
               //退款金额
               String settlementRefundFee = reqInfoMap.get("settlement_refund_fee");
               //退款状态
               String refundStatus = reqInfoMap.get("refund_status");
               //成功时间
               String successTime = reqInfoMap.get("success_time");
               String refundRecvAccout = reqInfoMap.get("refund_recv_accout");
               String refundAccount = reqInfoMap.get("refund_account");
               String refundRequestSource = reqInfoMap.get("refund_request_source");



               refund.setTransactionId(transactionId);




               if (null == settlementRefundFee) {
                   logger.info("退款金额为空");
                   refund.setReturn_msg("退款金额为空");
                   paymentService.refundError(refund);

                   return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>";


               }
               if (null == outRefundNo) {
                   logger.info("商户退款单号为空");
                   refund.setReturn_msg("微信退款单号为空");
                   paymentService.refundError(refund);

                   return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>";
               }
               if (null == refundId) {
                   logger.info("微信退款单号为空");
                   refund.setReturn_msg("微信退款单号为空");
                   paymentService.refundError(refund);


                   return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg></xml>";
               }
/*————————————————
               版权声明：本文为CSDN博主「by_ing!」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
               原文链接：https://blog.csdn.net/qq_40506288/article/details/111353331*/







                paymentService.refundCompleted(refund);
                // 实现自己的逻辑
                //    logger.info("退款远程回调:{}", xmlData);
                // 必须要返回 SUCCESS 不过有 WxPayNotifyResponse 给整合成了 xml了
                return WxPayNotifyResponse.success("成功");



                // 组装返回给微信的xml数据
              //  returnXmlMessage = setReturnXml(WXPayConstants.SUCCESS, "OK");
       //        log.info("[refundAsyncNotify]  [out_trade_no:{}] [out_refund_no:{}] [退款异步消息处理成功:{}]",
            //            reqInfoMap.get("out_trade_no"), reqInfoMap.get("out_refund_no"), returnXmlMessage);


            } else {
                returnXmlMessage = setReturnXml(WXPayConstants.FAIL, "return_code不为success");
            }
        } catch (IOException e) {
            logger.error("[refundAsyncNotify] [读取微信服务器返回流中xml数据时发生异常:{}] ", ExceptionUtils.getStackTrace(e));
            returnXmlMessage = setReturnXml(WXPayConstants.FAIL, "An exception occurred while reading the WeChat server returning xml data in the stream.");
        } catch (Exception e) {
            logger.error("[refundAsyncNotify [处理异常]] [xml数据:{}] [异常:{}] ", notifyXmlData, ExceptionUtils.getStackTrace(e));

            returnXmlMessage = setReturnXml(WXPayConstants.FAIL, "Refund successful, exception occurred during asynchronous notification processing.");
            logger.warn("[refundAsyncNotify] [退款异步消息处理失败:{}]", returnXmlMessage);
        }

        return returnXmlMessage;

    }


    /**
     * 设置返回给微信服务器的xml信息
     *
     * @param returnCode
     * @param returnMsg
     * @return
     */
    private String setReturnXml(String returnCode, String returnMsg) {
        return "<xml><return_code><![CDATA[" + returnCode + "]]></return_code><return_msg><![CDATA[" + returnMsg + "]]></return_msg></xml>";
    }

}
package com.lt.dom.thirdMt;


import cn.gjing.EncryptionUtil;
import com.beust.jcommander.internal.Lists;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.thirdMt.mtTolt.*;
import com.lt.dom.thirdPft.EnumMethord;
import com.lt.dom.thirdPft.RequestPojo;
import com.lt.dom.thirdPft.domainLtToPft.LtToPftRetPftCancel;
import com.lt.dom.thirdPft.domainPftTolt.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class MtToLtServiceImpl {


    @Autowired
    private BookingRuleRepository bookingRuleRepository;
    /**
     * sign 签名 （参数名按ASCII码从小到大排序（字典序）+key+MD5+转大写签名）
     * @param map
     * @return
     */
    public static String encodeSign(SortedMap<String,String> map){
/*        if(StringUtils.isEmpty(key)){
            throw new RuntimeException("签名key不能为空");
        }*/
        Set<Map.Entry<String, String>> entries = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
        List<String> values = Lists.newArrayList();

        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String k = String.valueOf(entry.getKey());
            String v = String.valueOf(entry.getValue());
            if (StringUtils.isNotEmpty(v) && entry.getValue() !=null && !"sign".equals(k) && !"key".equals(k)) {
                values.add(k + "=" + v);
            }
        }
     //   values.add("key="+ key);
        String sign = StringUtils.join(values, "&");
        //return encodeByMD5(sign);
        return EncryptionUtil.md5(sign);
    }
    /**
     * 通过MD5加密
     *
     * param algorithmStr
     * @return String
     */
/*    public static String encodeByMD5(String algorithmStr) {
        if (algorithmStr == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("ALGORITHM_MD5");
            messageDigest.update(algorithmStr.getBytes("utf-8"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }*/

/*————————————————
        版权声明：本文为CSDN博主「lv_hang515888」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/lv_hang515888/article/details/86621898*/
        RequestPojo get(EnumMethord methord, String data){
        RequestPojo requestPojo = new RequestPojo();
        requestPojo.setAppid("--------------------");
        requestPojo.setMethod(methord.getName());
        requestPojo.setData(data);
        requestPojo.setVersion("V1");
        requestPojo.setTimeStamp((int)Instant.now().getEpochSecond());

        SortedMap sortedMap = new TreeMap();
        sortedMap.put("data", requestPojo.getVersion());
        sortedMap.put("version", requestPojo.getVersion());
        sortedMap.put("method", requestPojo.getMethod());
        sortedMap.put("timeStamp", requestPojo.getTimeStamp());
        sortedMap.put("appid", requestPojo.getAppid());



        requestPojo.setSign(encodeSign(sortedMap));

        return requestPojo;
    }




    public PftToLvRet ok(String data){
        PftToLvRet pft =  new PftToLvRet();
        pft.setCode(200);
        pft.setMsg("success");
        pft.setData(data);
        return pft;
    }
    public PftToLvRet ok(){
        PftToLvRet pft =  new PftToLvRet();
        pft.setCode(200);
        pft.setMsg("success");
        pft.setData("");
        return pft;
    }

    public PftToLvRet fail(){
        PftToLvRet pft =  new PftToLvRet();
        pft.setCode(1000);
        pft.setMsg("失败信息");
        pft.setData("");
        return pft;
    }


    //请求系统存活检查推送
    public PftToLvRet Server_meituan_trip_order_create(MtpOrderCreateRequest pft) {

        return null;
    }
    //请求下单通知推送
    public PftToLvRet Order_meituan_trip_order_pay_订单出票(PftToLvOrderCreate pft) {

        return null;
    }

    //请求支付通知推送
    public MtpOrderRefundResponse Order_meituan_trip_order_refund_订单退款(MtpOrderRefundRequest实名 pft) {
        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiVerify,JSONObject.valueToString(pft));
        return null;

    }


    //请求支付通知推送
    public MtpOrderRefundResponse Order_meituan_trip_order_refund_订单退款(MtpOrderRefundRequest非实名 pft) {
        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiVerify,JSONObject.valueToString(pft));
        return null;

    }

    //请求取消通知推送
    public orderRescheduleResponse改签游玩日 Order_meituan_trip_order_reschedule_订单改签(OrderRescheduleRequest改签游玩日 cancel) {

        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiCancel,JSONObject.valueToString(cancel));
        return null;

    }

    //请求取消通知推送
    public orderRescheduleResponse改签游玩人 Order_meituan_trip_order_reschedule_订单改签(OrderRescheduleRequest改签游玩人 cancel) {

        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiCancel,JSONObject.valueToString(cancel));
        return null;

    }













    //请求重发短信通知推送
    public  mtpOrderQueryResponse_不需要核销 Order_meituan_trip_order_query_订单查询_不需要核销(MtpOrderQueryRequest pft) {
        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiGetOrderDetail,JSONObject.valueToString(pft));


        return null;
    }
    //请求重发短信通知推送
    public  mtpOrderQueryResponse_需要核销码 Order_meituan_trip_order_query_订单查询_需要核销码(MtpOrderQueryRequest pft) {
        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiGetOrderDetail,JSONObject.valueToString(pft));


        return null;
    }

    
    
    //请求查询三方系统订单推送
    public  PftToLvRet  Order_ApiOrderDetail(PftToLvApiOrderDetail pft) {
        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiGetOrderDetail,JSONObject.valueToString(pft));


        return ok(JSONObject.valueToString(new PftToLvRetApiOrderDetail()));
    }

}

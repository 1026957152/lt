package com.lt.dom.thirdMt;


import cn.gjing.EncryptionUtil;
import com.beust.jcommander.internal.Lists;
import com.lt.dom.repository.BookingRuleRepository;
import com.lt.dom.thirdPft.EnumMethord;
import com.lt.dom.thirdPft.RequestPojo;
import com.lt.dom.thirdPft.domainLtToPft.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class LtToMtServiceImpl {


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
      //  values.add("key="+ key);
        String sign = StringUtils.join(values, "&");
        //return encodeByMD5(sign);
        return EncryptionUtil.md5(sign);
    }
    /**
     * 通过MD5加密
     *
    // * @param algorithmStr
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

    public void ApiTicket(LvToPftRetTicket ticket) {

        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiTicket,JSONObject.valueToString(ticket));


    }

    public void Order_ApiTicketFail(LvToPftRetTicketFail ticketFail) {


    }


    public void Order_ApiVerify(LtToPftRetVerify verify) {
        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiVerify,JSONObject.valueToString(verify));



    }
    public void Order_ApiCancel(LtToPftRetPftCancel cancel) {

        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiCancel,JSONObject.valueToString(cancel));



    }
    public LvToPftOrderDetail Order_ApiGetOrderDetail(String orderNum) {
        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiGetOrderDetail,JSONObject.valueToString(orderNum));


        return new LvToPftOrderDetail();
    }
    public LvToPftOrderDetailPro Order_ApiGetOrderDetailPro(String orderNum) {

        RequestPojo requestPojo = get(EnumMethord.LtToPft_Order_ApiGetOrderDetail,JSONObject.valueToString(orderNum));

        return new LvToPftOrderDetailPro();

    }

}

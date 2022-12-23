package com.lt.dom.thirdTS;


import cn.gjing.EncryptionUtil;
import com.beust.jcommander.internal.Lists;
import com.lt.dom.repository.ProductRepository;
import com.lt.dom.repository.ReservationRepository;
import com.lt.dom.repository.SupplierRepository;
import com.lt.dom.serviceOtc.*;
import com.lt.dom.thirdTS.domainLtToTs.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.util.*;

@Service
public class LtToTsServiceImpl {


    String baseUrl_请求地址 = "http://ylltjs.sjdzp.com/Api/LocalCom/api.json?g_cid=95029";//
    String 合作伙伴ID = "0912719100LV20221125";//
    String 授权编码 = "lt0912";//






    //TODO 验证核销通知(第三方通知天时同城)

    public TsRespLt验证核销通知 ltReqTs验证核销通知(LtReqTs验证核销通知.ToLtReqTs验证核销通知 to) {




        LtReqTs验证核销通知 ltReqTs验证核销通知 = to.To();
        ltReqTs验证核销通知.set_pid(合作伙伴ID);
        ltReqTs验证核销通知.setFormat("json");
        ltReqTs验证核销通知.setMethod(EnumMethord.validate.name());


        String url = String.format("%s",baseUrl_请求地址);

    //    POST  / HTTP/1.1
     //   Host: demo.json
      //  Content-Type:application/x-www-form-urlencoded
       // method=item_list&amount=2&another_orders_id=91474102737444075801&my_orders_id=&codes=&_sig=B8C3AA43C70D7FE464CC180AA78E3F90&_pid=4&format=json

            // 发送请求参数
            Map<String,Object> paramJson = new HashMap<>();
            //  paramJson.put("username","admin111");
            //  paramJson.put("password","admin123");
          //  paramJson.put("username",username);
         //   paramJson.put("password",password);
            RestTemplate restTemplate = new RestTemplate();



        MultiValueMap<String, String> lt验证核销通知= getTsRespLt验证核销通知(授权编码,EnumMethord.validate,ltReqTs验证核销通知);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        //  headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
      //  headers.set("Authorization","bearer "+xhToYxdLoginResponse.getAccessToken() );

        System.out.println("---############-------############---"+ltReqTs验证核销通知.toString());

        HttpEntity<MultiValueMap> entity = new HttpEntity<MultiValueMap>(lt验证核销通知,headers);


            ResponseEntity<TsRespLt验证核销通知> responseEntity = restTemplate.postForEntity(url,entity, TsRespLt验证核销通知.class);


            System.out.println("getStatusCode"+responseEntity.getStatusCode());
            TsRespLt验证核销通知 buffer = responseEntity.getBody();
            System.out.println("返回数据"+buffer.getMessage());

            return buffer;

        }




    public   MultiValueMap<String, String> getTsRespLt验证核销通知(String 授权编码,EnumMethord methord, LtReqTs验证核销通知 data){



      //  111&_pid=4&format=json&item_id=12345965&method=item_orders&mobile=15597207761&orders_id=2019122011460002&players=[0=[id_number=632128200104030018&mobile=15597207761&name=韩紫阳]&1=[id_number=460100197605131824&mobile=15597207761&name=韩阳]]&size=2&start_date=1576822388&111





        SortedMap sortedMap = new TreeMap();

        sortedMap.put("_pid", data.get_pid());
       // sortedMap.put("amount_used", data.getAmount_used());
        sortedMap.put("amount", data.getAmount());
        sortedMap.put("another_orders_id", data.getAnother_orders_id());
        sortedMap.put("codes", data.getCodes());
        sortedMap.put("format", data.getFormat());

        sortedMap.put("my_orders_id", data.getMy_orders_id());
        sortedMap.put("method", data.getMethod());



  //      method=item_list&amount=2&another_orders_id=91474102737444075801&my_orders_id=&codes=&_sig=B8C3AA43C70D7FE464CC180AA78E3F90&_pid=4&format=json



        String aa  = 授权编码+"&"+获得拼接EncodeSign(sortedMap)+"&"+授权编码;


        System.out.println("--------------"+ aa);

        String _sig =EncryptionUtil.md5(aa);//  EncryptionUtil.md5(aa);

        data.set_sig(_sig);


        MultiValueMap<String, String> bodyPair = new LinkedMultiValueMap();

        bodyPair.add("method", data.getMethod());
        bodyPair.add("amount", data.getAmount()+"");
        bodyPair.add("another_orders_id", data.getAnother_orders_id());
        bodyPair.add("my_orders_id", data.getMy_orders_id());
        bodyPair.add("codes", data.getCodes());
        bodyPair.add("_sig", data.get_sig().toUpperCase());
        bodyPair.add("_pid", data.get_pid());
        bodyPair.add("format", data.getFormat());
        return bodyPair;
    }
    public static String 获得拼接EncodeSign(SortedMap<String,String> map){
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

            System.out.println("拼接 "+k +"--"+ v);
            if (entry.getValue() !=null && !"sign".equals(k) && !"key".equals(k)) {

               // if (StringUtils.isNotEmpty(v) && entry.getValue() !=null && !"sign".equals(k) && !"key".equals(k)) {
                values.add(k + "=" + v);
            }
        }
        //   values.add("key="+ key);
        String sign = StringUtils.join(values, "&");

        return sign;
        //return encodeByMD5(sign);
    }

































    //TODO 验证核销通知(第三方通知天时同城)

    public TsRespLt退单审核通知 ltReqTs退单审核通知(LtReqTs退单审核通知.ToLtReqTs退单审核通知 to) {




        LtReqTs退单审核通知 ltReqTs退单审核通知 = to.To();
        ltReqTs退单审核通知.set_pid(合作伙伴ID);
        ltReqTs退单审核通知.setMethod(EnumMethord.refundResult.name());
        ltReqTs退单审核通知.setFormat("json");


        String url = String.format("%s/back-center/login",baseUrl_请求地址);

        //    POST  / HTTP/1.1
        //   Host: demo.json
        //  Content-Type:application/x-www-form-urlencoded
        // method=item_list&amount=2&another_orders_id=91474102737444075801&my_orders_id=&codes=&_sig=B8C3AA43C70D7FE464CC180AA78E3F90&_pid=4&format=json


        RestTemplate restTemplate = new RestTemplate();



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap ltReqTs退单审核通知map= getLtReqTs退单审核通知(授权编码,EnumMethord.refundResult,ltReqTs退单审核通知);

        HttpEntity<MultiValueMap> entity = new HttpEntity<MultiValueMap>(ltReqTs退单审核通知map,headers);

        ResponseEntity<TsRespLt退单审核通知> responseEntity = restTemplate.postForEntity(url,entity, TsRespLt退单审核通知.class);

        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        TsRespLt退单审核通知 buffer = responseEntity.getBody();
        System.out.println("登录获得的 accessToken"+buffer.getMessage());

        return buffer;

    }



    public   MultiValueMap getLtReqTs退单审核通知(String 授权编码,EnumMethord methord, LtReqTs退单审核通知 data){



        //  111&_pid=4&format=json&item_id=12345965&method=item_orders&mobile=15597207761&orders_id=2019122011460002&players=[0=[id_number=632128200104030018&mobile=15597207761&name=韩紫阳]&1=[id_number=460100197605131824&mobile=15597207761&name=韩阳]]&size=2&start_date=1576822388&111



        SortedMap sortedMap = new TreeMap();
        sortedMap.put("method", data.getMethod());

        sortedMap.put("serial_no", data.getSerial_no());
        sortedMap.put("orders_id", data.getOrders_id());
        sortedMap.put("type", data.getType());
        sortedMap.put("message", data.getMessage());


        sortedMap.put("_pid", data.get_pid());
        sortedMap.put("format", data.getFormat());


        String aa  = 授权编码+"&"+获得拼接EncodeSign(sortedMap)+"&"+授权编码;


        String _sig =  EncryptionUtil.md5(aa);

        data.set_sig(_sig);


        MultiValueMap<String, String> bodyPair = new LinkedMultiValueMap();



        bodyPair.add("method", data.getMethod());

        bodyPair.add("serial_no", data.getSerial_no());
        bodyPair.add("orders_id", data.getOrders_id());
        bodyPair.add("type", data.getType()+"");
        bodyPair.add("message", data.getMessage());

        bodyPair.add("_sig", data.get_sig());

        bodyPair.add("_pid", data.get_pid());
        bodyPair.add("format", data.getFormat());
        return bodyPair;
    }




















































    //TODO 验证核销通知(第三方通知天时同城)



    public TsRespLt码号推送通知 ltReqTs码号推送通知(LtReqTs码号推送通知.ToLtReqTs码号推送通知 to) {




        LtReqTs码号推送通知 ltReqTs码号推送通知 = to.To();
        ltReqTs码号推送通知.set_pid(合作伙伴ID);

        ltReqTs码号推送通知.setMethod(EnumMethord.send.name());
        ltReqTs码号推送通知.setFormat("json");




        String url = String.format("%s",baseUrl_请求地址);

        //    POST  / HTTP/1.1
        //   Host: demo.json
        //  Content-Type:application/x-www-form-urlencoded
        // method=item_list&amount=2&another_orders_id=91474102737444075801&my_orders_id=&codes=&_sig=B8C3AA43C70D7FE464CC180AA78E3F90&_pid=4&format=json

        // 发送请求参数
        Map<String,Object> paramJson = new HashMap<>();
        //  paramJson.put("username","admin111");
        //  paramJson.put("password","admin123");
        //  paramJson.put("username",username);
        //   paramJson.put("password",password);
        RestTemplate restTemplate = new RestTemplate();





        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        //  headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        //  headers.set("Authorization","bearer "+xhToYxdLoginResponse.getAccessToken() );





        MultiValueMap<String, String> ltReqTs码号推送通知Map = getLtReqTs码号推送通知(授权编码,EnumMethord.send,ltReqTs码号推送通知);


        HttpEntity<MultiValueMap> entity = new HttpEntity<MultiValueMap>(ltReqTs码号推送通知Map,headers);


        ResponseEntity<TsRespLt码号推送通知> responseEntity = restTemplate.postForEntity(url,entity, TsRespLt码号推送通知.class);


        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        TsRespLt码号推送通知 buffer = responseEntity.getBody();
        System.out.println("登录获得的 accessToken"+buffer.getMessage());

        return buffer;

    }




    public   MultiValueMap getLtReqTs码号推送通知(String 授权编码,EnumMethord methord, LtReqTs码号推送通知 data){



        //  111&_pid=4&format=json&item_id=12345965&method=item_orders&mobile=15597207761&orders_id=2019122011460002&players=[0=[id_number=632128200104030018&mobile=15597207761&name=韩紫阳]&1=[id_number=460100197605131824&mobile=15597207761&name=韩阳]]&size=2&start_date=1576822388&111



        SortedMap sortedMap = new TreeMap();
        sortedMap.put("method", data.getMethod());


        sortedMap.put("orders_id", data.getOrders_id()); //本平台订单ID（天时同城）
        sortedMap.put("out_orders_id", data.getOut_orders_id());//out_orders_id:第三方平台订单ID
        sortedMap.put("out_code", data.getOut_code());//out_code:码号，存在多个码号时默认展示第一个


/*

        sortedMap.put("out_codes", data.getOut_codes());//out_codes:多个码号时以英文逗号分隔(,)
        sortedMap.put("qrcode_images", data.getQrcode_images());//qrcode_images:二维码图片，多个用英文逗号(,)分隔
        sortedMap.put("qrcode_image", data.getQrcode_image());//qrcode_image:二维码图片
        sortedMap.put("qrcode_href", data.getQrcode_href());//qrcode_href:二维码链接
        sortedMap.put("qrcode", data.getQrcode());//qrcode:二维码数据(用于生成二维码图片)

        sortedMap.put("out_money_send", data.getOut_money_send());////out_money_send:采购发送费
        sortedMap.put("out_money_one", data.getOut_money_one());////out_money_one:采购单价
        sortedMap.put("out_send_content", data.getOut_send_content());//out_send_content:发送内容
        sortedMap.put("is_real_code", data.getIs_real_code());//is_real_code:是否真实码号
        sortedMap.put("post_tracking_no", data.getPost_tracking_no());////post_tracking_no:快递单号
*/




        sortedMap.put("_pid", data.get_pid());
        sortedMap.put("format", data.getFormat());


        String aa  = 授权编码+"&"+获得拼接EncodeSign(sortedMap)+"&"+授权编码;


        String _sig =  EncryptionUtil.md5(aa);

        data.set_sig(_sig);


        MultiValueMap<String, String> bodyPair = new LinkedMultiValueMap();

        bodyPair.add("method", data.getMethod());


        bodyPair.add("orders_id", data.getOrders_id()); //本平台订单ID（天时同城）
        bodyPair.add("out_orders_id", data.getOut_orders_id());//out_orders_id:第三方平台订单ID
        bodyPair.add("out_code", data.getOut_code());//out_code:码号，存在多个码号时默认展示第一个



/*
        bodyPair.add("out_codes", data.getOut_codes());//out_codes:多个码号时以英文逗号分隔(,)
        bodyPair.add("qrcode_images", data.getQrcode_images());//qrcode_images:二维码图片，多个用英文逗号(,)分隔
        bodyPair.add("qrcode_image", data.getQrcode_image());//qrcode_image:二维码图片

        bodyPair.add("qrcode_href", data.getQrcode_href());//qrcode_href:二维码链接
        bodyPair.add("qrcode", data.getQrcode());//qrcode:二维码数据(用于生成二维码图片)

        bodyPair.add("out_money_send", data.getOut_money_send());////out_money_send:采购发送费
        bodyPair.add("out_money_one", data.getOut_money_one());////out_money_one:采购单价
        bodyPair.add("out_send_content", data.getOut_send_content());//out_send_content:发送内容
        bodyPair.add("is_real_code", data.getIs_real_code());//is_real_code:是否真实码号
        bodyPair.add("post_tracking_no", data.getPost_tracking_no());////post_tracking_no:快递单号
*/


        bodyPair.add("_sig", data.get_sig().toUpperCase());
        bodyPair.add("_pid", data.get_pid());
        bodyPair.add("format", data.getFormat());
        return bodyPair;
    }
















    public TsRespLt产品信息变更通知 ltReqTs产品信息变更通知(LtReqTs产品信息变更通知.ToLtReqTs产品信息变更通知 to) {




        LtReqTs产品信息变更通知 ltReqTs码号推送通知 = to.To();
        ltReqTs码号推送通知.set_pid(合作伙伴ID);
        ltReqTs码号推送通知.setMethod(EnumMethord.goods.name());
        ltReqTs码号推送通知.setFormat("json");



        String url = String.format("%s",baseUrl_请求地址);

        //    POST  / HTTP/1.1
        //   Host: demo.json
        //  Content-Type:application/x-www-form-urlencoded
        // method=item_list&amount=2&another_orders_id=91474102737444075801&my_orders_id=&codes=&_sig=B8C3AA43C70D7FE464CC180AA78E3F90&_pid=4&format=json

        // 发送请求参数


        RestTemplate restTemplate = new RestTemplate();





        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> ltReqTs码号推送通知Map = getLtReqTs产品信息变更通知(授权编码,EnumMethord.goods,ltReqTs码号推送通知);


        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(ltReqTs码号推送通知Map,headers);


        ResponseEntity<TsRespLt产品信息变更通知> responseEntity = restTemplate.postForEntity(url,entity, TsRespLt产品信息变更通知.class);


        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        TsRespLt产品信息变更通知 buffer = responseEntity.getBody();
        System.out.println("返回数据 "+buffer.getMessage());

        return buffer;

    }




    public   MultiValueMap<String, String> getLtReqTs产品信息变更通知(String 授权编码,EnumMethord methord, LtReqTs产品信息变更通知 data){



        //  111&_pid=4&format=json&item_id=12345965&method=item_orders&mobile=15597207761&orders_id=2019122011460002&players=[0=[id_number=632128200104030018&mobile=15597207761&name=韩紫阳]&1=[id_number=460100197605131824&mobile=15597207761&name=韩阳]]&size=2&start_date=1576822388&111



        SortedMap sortedMap = new TreeMap();
        sortedMap.put("method", data.getMethod());
        sortedMap.put("status", data.getStatus());
        sortedMap.put("seller_code", data.getSeller_code());
        //   sortedMap.put("_sig", data.get_sig());
        sortedMap.put("_pid", data.get_pid());
        sortedMap.put("format", data.getFormat());




        String aa  = 授权编码+"&"+获得拼接EncodeSign(sortedMap)+"&"+授权编码;

        System.out.println("-----------" + aa);
        String _sig =  EncryptionUtil.md5(aa);

        System.out.println("-----------" + _sig);
        data.set_sig(_sig);

        MultiValueMap<String, String> bodyPair = new LinkedMultiValueMap();

        bodyPair.add("method", data.getMethod());
        bodyPair.add("status", data.getStatus()+"");
        bodyPair.add("seller_code", data.getSeller_code());
        bodyPair.add("_sig", data.get_sig());
        bodyPair.add("_pid", data.get_pid());
        bodyPair.add("format", data.getFormat());
        return bodyPair;

    }


}

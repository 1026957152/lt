package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.EmpowerResp;
import com.lt.dom.OctResp.PhoneResp;
import com.lt.dom.OctResp.SmsResp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SMSServiceImpl {

    // 服务商 AppID
    @Value("${yunpian_sms.apikey}")
    private String apikey;

    /**
     * 单条短信发送,智能匹配短信模板
     *
     * @param apikey成功注册后登录云片官网,进入后台可查看
     * @param text需要使用已审核通过的模板或者默认模板
     * @param mobile接收的手机号,仅支持单号码发送
     * @return json格式字符串
     */

    public String singleSend(String text, String mobile) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("apikey", apikey);
        params.add("text", text);
        params.add("mobile", mobile);



        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        //  ResponseEntity<PhoneResp> responseEntity = restTemplate.getForEntity(url,PhoneResp.class);

        ResponseEntity<SmsResp> responseEntity = restTemplate.postForEntity("https://sms.yunpian.com/v2/sms/single_send.json",requestEntity,SmsResp.class);




        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        SmsResp buffer = responseEntity.getBody();

        System.out.println(buffer);

        return null;
    }




    public static void main(String[] text) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("apikey", "apikey");
        params.add("text", "text");
        params.add("mobile", "mobile");



        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
       // headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, requestHeaders);

        RestTemplate restTemplate = new RestTemplate();
        //  ResponseEntity<PhoneResp> responseEntity = restTemplate.getForEntity(url,PhoneResp.class);

        ResponseEntity<SmsResp> responseEntity = restTemplate.postForEntity("https://sms.yunpian.com/v2/sms/single_send.json",requestEntity,SmsResp.class);


        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        SmsResp buffer = responseEntity.getBody();

        System.out.println(buffer);


    }
}

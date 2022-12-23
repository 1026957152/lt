package com.lt.dom.serviceOtc;


import com.lt.dom.OctResp.SmsResp;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.proto.rabit.CityPassBooking;
import com.lt.dom.rabbit.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static com.lt.dom.serviceOtc.JsonParse.GSON;

@Service
public class SMSServiceImpl {

    Logger logger = LoggerFactory.getLogger(SMSServiceImpl.class);
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // 服务商 AppID
    @Value("${yunpian_sms.apikey}")
    private String apikey;
    public String send_(CityPassBooking cityPassBooking, String tel_home){


        logger.debug("发送了消息 rabit {}",cityPassBooking);

        CorrelationData messageId = new CorrelationData(UUID.randomUUID().toString());
        //第四个参数: 设置消息唯一id

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("zheaven123456");//id + 时间戳 （必须是全局唯一的）
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName,RabbitMQConfig.routing_key,cityPassBooking,messageId);


        return "ok. done";
    }

    /**
     * 单条短信发送,智能匹配短信模板
     *
     * @param apikey成功注册后登录云片官网,进入后台可查看
     * @param text需要使用已审核通过的模板或者默认模板
     * @param mobile接收的手机号,仅支持单号码发送
     * @return json格式字符串
     */

    public void singleSend(String text, String mobile) {

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

        try {
            ResponseEntity<SmsResp> responseEntity = restTemplate.postForEntity("https://sms.yunpian.com/v2/sms/single_send.json", requestEntity, SmsResp.class);


            System.out.println("getStatusCode" + responseEntity.getStatusCode());
            SmsResp buffer = responseEntity.getBody();

            System.out.println(buffer);



        } catch (HttpStatusCodeException e) {
            String errorpayload = e.getResponseBodyAsString();

            SmsResp buffer = GSON.fromJson(errorpayload,SmsResp.class);
            logger.error("短信发送错误 {}",e.toString());

            throw new BookNotFoundException(Enumfailures.resource_not_found, buffer.getMsg());

            //do whatever you want
        } catch (RestClientException e) {

            logger.error("短信发送错误 {}",e.toString());
            //no response payload, tell the user sth else
            throw new BookNotFoundException(Enumfailures.resource_not_found, "短信发送网络问题");

        }


    }



    public static void main(String[] text) {

        String greetings = String.format(
                "你的验证码是%s",
                123);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("apikey", "220dbf356cc27ef60b69d99d28c74f0e");
        params.add("text", greetings);
        params.add("mobile", "13468801683");



        HttpHeaders requestHeaders = new HttpHeaders();

        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        System.out.println("ddd我的祖国");
        // headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, requestHeaders);
        RestTemplate restTemplate = new RestTemplate();
        //  ResponseEntity<PhoneResp> responseEntity = restTemplate.getForEntity(url,PhoneResp.class);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        ResponseEntity<SmsResp> responseEntity = restTemplate.postForEntity("https://sms.yunpian.com/v2/sms/single_send.json",requestEntity,SmsResp.class);


        System.out.println("getStatusCode"+responseEntity.getStatusCode());
        SmsResp buffer = responseEntity.getBody();

        System.out.println(buffer);


    }
}

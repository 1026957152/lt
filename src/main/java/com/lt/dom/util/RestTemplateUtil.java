package com.lt.dom.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
public class RestTemplateUtil {
    /**
     * post请求
     * @param url
     * @param param
     * @return
     */
    public static JSONObject doPost(String url, MultiValueMap<String, Object> param){
        RestTemplate restTemplate=new RestTemplate();
        String s = restTemplate.postForObject(url, param, String.class);
        return JSONObject.parseObject(s);
    }
}
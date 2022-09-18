package com.lt.dom.thiirdAli.idfaceIdentity;

import com.alibaba.fastjson.JSON;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.thiirdAli.AliConfig;
import com.lt.dom.thiirdAli.idcard.HttpUtils;
import com.lt.dom.thiirdAli.ocr_business_license.BusinessLicenseVo;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;


/**
 * 使用APPCODE进行云市场ocr服务接口调用
 */
@Service
public class IdfaceIdentityOcrService {



    @Autowired
    private AliConfig aliConfig;


    public  IdfaceIdentityVo main(String name, String number,MultipartFile multipartFile) {
        String host = "https://zfah.market.alicloudapi.com";
        String path = "/efficient/idfaceIdentity";
        String method = "POST";
        String appcode = aliConfig.getIdfaceIdentity_appCode();


        // 对图像进行base64编码
        String imgBase64 = "";
        try {
            imgBase64 = new String(encodeBase64(multipartFile.getBytes()));

        } catch (IOException e) {
            e.printStackTrace();
            throw new BookNotFoundException(Enumfailures.voucher_disabled,"上传文件 转化 basse64 出现粗错误");
        }

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("Threshold", "0.33");
        bodys.put("base64Str", imgBase64);
        bodys.put("liveChk", "0");
        bodys.put("name", name);
        bodys.put("number", number);


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));

            String res = EntityUtils.toString(response.getEntity());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            System.out.println("识别了，看看返回接口"+res);
            IdfaceIdentityVo res_obj = JSON.parseObject(res,   IdfaceIdentityVo.class);
            System.out.println(res_obj.toString());

            return res_obj;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("阿里云ocr 识别错误，返回非200"+e.getMessage());

        }
    }

}
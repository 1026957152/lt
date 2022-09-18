package com.lt.dom.thiirdAli.ocr_business_license;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lt.dom.config.LtConfig;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.thiirdAli.AliConfig;
import com.lt.dom.thiirdAli.idcard.HttpUtils;
import com.lt.dom.thiirdAli.idcard.IdcardFaceVo;
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
public class BussinessLicenseOcrService {



    @Autowired
    private AliConfig aliConfig;


    public  BusinessLicenseVo main(MultipartFile multipartFile) {
        String host = "https://bizlicense.market.alicloudapi.com";
        String path = "/rest/160601/ocr/ocr_business_license.json";
        String method = "POST";
        String appcode = aliConfig.getBussiness_license_appCode();
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/json; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();



        // 对图像进行base64编码
        String imgBase64 = "";
        try {
            imgBase64 = new String(encodeBase64(multipartFile.getBytes()));

        } catch (IOException e) {
            e.printStackTrace();
            throw new BookNotFoundException(Enumfailures.voucher_disabled,"上传文件 转化 basse64 出现粗错误");
        }

        String bodys = "{\"image\":\""+imgBase64+"\"}";


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
            String res = EntityUtils.toString(response.getEntity());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
            System.out.println("识别了，看看返回接口"+res);
            BusinessLicenseVo res_obj = JSON.parseObject(res,   BusinessLicenseVo.class);
            System.out.println(res_obj.toString());
            return res_obj;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("阿里云ocr 识别错误，返回非200"+e.getMessage());


        }
    }
}
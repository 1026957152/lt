package com.lt.dom.thiirdAli.idcard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.otcenum.Enumfailures;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.commons.codec.binary.Base64.encodeBase64;


/**
 * 使用APPCODE进行云市场ocr服务接口调用
 */
@Service
public class IdCardOcrService {

    /*
     * 获取参数的json对象
     */
    public static JSONObject getParam(int type, String dataValue) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("dataType", type);
            obj.put("dataValue", dataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public IdcardFaceVo  main(MultipartFile multipartFile){

        String host = "http://dm-51.data.aliyun.com";
        String path = "/rest/160601/ocr/ocr_idcard.json";
        String appcode = "e5cccb072eb043d59995645e845eb972";

        //请根据线上文档修改configure字段
        JSONObject configObj = new JSONObject();
        configObj.put("side", "face");
        String config_str = configObj.toString();
        //            configObj.put("min_size", 5);
        //            String config_str = "";

        String method = "POST";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359d73e9498385570ec139105
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();




        // 对图像进行base64编码
        String imgBase64 = "";
        try {
            imgBase64 = new String(encodeBase64(multipartFile.getBytes()));

        } catch (IOException e) {
            e.printStackTrace();
            throw new BookNotFoundException(Enumfailures.voucher_disabled,"上传文件 转化 basse64 出现粗错误");
        }
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("image", imgBase64);
            if(config_str.length() > 0) {
                requestObj.put("configure", config_str);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String bodys = requestObj.toString();

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
            int stat = response.getStatusLine().getStatusCode();
            if(stat != 200){
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: "+ response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                throw new BookNotFoundException(Enumfailures.not_found,"阿里云ocr 识别错误，返回非200");

            }

            String res = EntityUtils.toString(response.getEntity());


            System.out.println("识别了，看看返回接口"+res);
            IdcardFaceVo res_obj = JSON.parseObject(res,   IdcardFaceVo.class);
            System.out.println(res_obj.toString());
            return res_obj;

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("阿里云ocr 识别错误，返回非200"+e.getMessage());

        }
    }
}
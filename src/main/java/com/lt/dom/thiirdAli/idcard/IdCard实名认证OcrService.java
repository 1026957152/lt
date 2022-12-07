package com.lt.dom.thiirdAli.idcard;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lt.dom.error.BookNotFoundException;
import com.lt.dom.otcenum.Enumfailures;
import com.lt.dom.serviceOtc.AgentServiceImpl;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class IdCard实名认证OcrService {
    Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

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

    public IdcardFaceVo main(MultipartFile multipartFile) {

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
            throw new BookNotFoundException(Enumfailures.voucher_disabled, "上传文件 转化 basse64 出现粗错误");
        }
        // 拼装请求body的json字符串
        JSONObject requestObj = new JSONObject();
        try {
            requestObj.put("image", imgBase64);
            if (config_str.length() > 0) {
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
            if (stat != 200) {
                System.out.println("Http code: " + stat);
                System.out.println("http header error msg: " + response.getFirstHeader("X-Ca-Error-Message"));
                System.out.println("Http body error msg:" + EntityUtils.toString(response.getEntity()));
                throw new BookNotFoundException(Enumfailures.not_found, "阿里云ocr 识别错误，返回非200");

            }

            String res = EntityUtils.toString(response.getEntity());


            System.out.println("识别了，看看返回接口" + res);
            IdcardFaceVo res_obj = JSON.parseObject(res, IdcardFaceVo.class);
            System.out.println(res_obj.toString());
            return res_obj;

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("阿里云ocr 识别错误，返回非200" + e.getMessage());

        }
    }


    public  boolean main(String name, String idcard) {

        logger.info("调用阿里身份证认证服务");
        String host = "http://smrz.qianshutong.com";
        String path = "/web/interface/grsfyz";
        String method = "POST";
        String appcode = "e5cccb072eb043d59995645e845eb972";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("idcard", idcard);
        bodys.put("name", name);



            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
        HttpResponse response = null;
        try {
            response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
        } catch (Exception e) {

            logger.error("实名认证接口调用 网络错误");
            throw new RuntimeException(e);
        }
        System.out.println(response.toString());

        String res = null;
        try {
            res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } catch (IOException e) {
            logger.error("实名认证接口调用 网络错误");
            throw new RuntimeException(e);
        }
        JsonObject convertedObject = new Gson().fromJson(res, JsonObject.class);


            Integer code = convertedObject.get("code").getAsInt();
            if(code != null){
                String data = convertedObject.get("data").getAsString();

                JsonObject convertedObject_data = new Gson().fromJson(data, JsonObject.class);


                Integer status = convertedObject_data.get("status").getAsInt();
                if(status ==0){


                    JsonObject convertedObject_data_data_last = convertedObject_data.get("data").getAsJsonObject();
                    Integer result = convertedObject_data_data_last.get("result").getAsInt();
                    if(result == 1){
                        logger.info("{},{} 实名认证通过",name,idcard);

                        return true;
                    }else{
                        logger.warn("{},{} 实名认证失败",name,idcard);
                        return false;
                    }

                }else{
                    logger.info("{},{} 实名认证未通过",name,idcard);
                    throw new BookNotFoundException(Enumfailures.resource_not_found,"接口调用错误"+data);

                }

            }else{
                Integer status = convertedObject.get("status").getAsInt();

                if(status == 0){
                    return true;
                }
                throw new BookNotFoundException(Enumfailures.resource_not_found,"接口调用错误");

            }



    }



    public static class Success {

        @JsonProperty("code")
        private Integer code;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        @JsonProperty("status")
        private Integer status;
        @JsonProperty("message")
        private String message;
        @JsonProperty("seqNum")
        private String seqNum;
        @JsonProperty("data")
        private DataDTO data;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSeqNum() {
            return seqNum;
        }

        public void setSeqNum(String seqNum) {
            this.seqNum = seqNum;
        }

        public DataDTO getData() {
            return data;
        }

        public void setData(DataDTO data) {
            this.data = data;
        }


        public static class DataDTO {
            @JsonProperty("result")
            private Integer result;
            @JsonProperty("resultMsg")
            private String resultMsg;

            public Integer getResult() {
                return result;
            }

            public void setResult(Integer result) {
                this.result = result;
            }

            public String getResultMsg() {
                return resultMsg;
            }

            public void setResultMsg(String resultMsg) {
                this.resultMsg = resultMsg;
            }
        }
    }
}
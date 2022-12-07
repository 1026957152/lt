package com.lt.dom.minapp;



import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 通用工具类
 *
 * @author liufeng
 * @date 2013-10-17
 */
public class CommonUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    // 凭证获取（GET）
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    /**
     * 发送https请求
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            System.out.println("http 请求：{}"+buffer.toString());
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }

    /**
     * 获取接口访问凭证
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static Token getToken(String appid, String appsecret) {
        Token token = null;
        String requestUrl = token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                token = new Token();

                token.setAppId(appid);


                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInteger("expires_in"));
                token.setReveiveTime(LocalDateTime.now());
            } catch (Exception e) {
                token = null;
                System.out.println("获取token失败 errcode:{} errmsg:{}"+jsonObject.getInteger("errcode")+"     "+ jsonObject.getString("errmsg"));

                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInteger("errcode"), jsonObject.getString("errmsg"));

            }
        }
        return token;
    }




    /**
     * URL编码（utf-8）
     *
     * @param source
     * @return
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据内容类型判断文件扩展名
     *
     * @param contentType 内容类型
     * @return
     */
    public static String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType))
            fileExt = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileExt = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileExt = ".amr";
        else if ("video/mp4".equals(contentType))
            fileExt = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileExt = ".mp4";
        return fileExt;
    }

/*    AppId:wx0febfe2abdc27049
    AppSecret:0518344d786659d64ad9737b4e5c4771*/
    public static void main(String[] args) {
        // 第三方用户唯一凭证
        String appId = "wxada5ab1a8c131d85";
        // 第三方用户唯一凭证密钥
        String appSecret ="bdde4cd0c7eff399037992a53dba9826";// "APPSECRET";

        // 调用接口获取凭证
        Token accessToken =new Token();// CommonUtil.getToken(appId, appSecret);
        accessToken.setAccessToken("59_q6_6Yo3s9hQqVLr0QZaoEruckWbu5O_rb9WyYJlvA-X-z26fVFEIUCDq15XFL9LixW0mEQ6MaCYOYFBfX7uZ9tUwjPP0-cfVL0aqmd2RdKrd0FufSeBPj14nn9zyy8-azzsAQG32aZXOvs1MXNDfAFAKAQ");
        System.out.println("ddddddddddddddd"+accessToken);
       // WeixinQRCode ticket = AdvancedUtil.createPermanentQRCode(accessToken.getAccessToken(), Constants.WX_COMPANY_SCENEID);

        //AdvancedUtil.getQRCode(ticket.getTicket(), "d:/");


        //文档地址 https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html#%E8%AF%B7%E6%B1%82%E5%8F%82%E6%95%B0


        if(accessToken != null){
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken.getAccessToken();

            // 发送请求参数
            Map<String,Object> paramJson = new HashedMap();
            paramJson.put("page", "pages/index/index");
            paramJson.put("check_path", false);
            paramJson.put("width", 280);
            paramJson.put("scene", "a=1");
            //设置小程序码版本
            //paramJson.set("env_version","release"); 默认正式
            //        paramJson.put("env_version","trial"); //体验版
       paramJson.put("env_version","develop"); //开发版

            RestTemplate restTemplate = new RestTemplate();


            ResponseEntity<byte[]> responseEntity = restTemplate.postForEntity(url,paramJson,byte[].class);

            byte[] buffer = responseEntity.getBody();
            assert buffer != null;

            File file = new File("D://qrcode2.jpg");
            try {

                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                bos.write(buffer);
            }catch (IOException io){
                io.printStackTrace();

            }

/*            InputStream inputStream = HttpUtils.postInputStream(url, paramJson);
            //将流写出到response
            IoUtil.copy(inputStream, response.getOutputStream());*/
        }


    }






}
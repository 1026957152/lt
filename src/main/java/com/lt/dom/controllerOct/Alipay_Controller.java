package com.lt.dom.controllerOct;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class Alipay_Controller {

/*    @Autowired
    DonaItemService donaItemService;
    @Autowired
    DonaService donaService;*/

    @Value("${alipay.APPID}")
    private final String APP_ID ="2021000119625133";
    //应用私钥
    private final String APP_PRIVATE_KEY ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCAN0KDGnPDiany6PpGAf9/FQJVNy7kylSJ1w7PBUqQtikMC1qcsQnQkR4I1GfGWxijAhfHGcVBj7rPnfvWArz7NZYtxni/GGEI/kA7OgMorQ+uZ3+Lilbo9Z2qSoKTVy7pm1OOAewCyDs4a08XWPkorqBTIOQTcLCMeSbdN592BokDxEegR2ATXsZ0xJH+CipEoXjyFzmRpueQSQOTg0nAYo0XKch9d0v75euDtztTbVzVY+fXb6uWGiBjo5XikSXOZVrj+HNKtdYiPWgf1PvehCdRtxDKyN4ik6Pz+mwHqsi9ivQJXO6xz+BuRH1R40b2LR5Zxd+YghR9Ya+IqPZDAgMBAAECggEAJZcN6v4AXp2ns83WQlwnmgyAyJcLKoyfSGJCtzMn71FYq21QvYuyAvHoylHrst3WVUQx0G+fw17uOBxrTw0ydrv5MaZ0lCIDoO1Zy4NwlaZXaJxzame+n0ITXW/G5Ie0+0xMrN+nYBFwO1RKtd+h/Ollm7mS5JgvSp/iYw0BtUo+nAPV8xflemxRjbUBD3r74Xp5TLDbuJtLicR/GoM22tdZUspIU78AiSUyehhQ1SkeNjldkCiWQg1XYSzCMza1SM736nJfZAQSsrESAP7fS40m7I3Sq6NlhB7+f9Z2M6hNq+GPqrSUV3WsJrEgWfkgzr8XyrI4XB8n/roO0ZMJAQKBgQDE2fT6vnfh1SZqPUAJ55/IfGdeor62U/TxxJwK0yxiUTCAQFQGHNdoxk9OoPKzm9xNQ5mkNR1jWo80ov2k27isFumMGiVnezkMR94b5JHZoBYcHlhchbbpAdv3HlsaimQqFEgDj+RwaSd0eiV/G/NQd/3TIPllRMkR5UiCcDHbwQKBgQCmvcaj8+7E9eb62PdDrXpnzFpBcHCELkeOSJjysF+gSRZVt7CLe9cfndzOgyxqVQacMhsCFpTQk/apyzLcRNd0yx6IZOhQE1alk3C+EU7NJ1IccgH0mnlA5G9ELf+YIS0glzRzBAh0UTYjCrpAp3z4OgW+Xg0py2bhvVm4yQgjAwKBgBtauks5uoj8SKlMt6BounBusSKadOv7QLdz/ccXTzyeUaIQzlxHF7vsisq/XNqOlw95jFkgNGRBClNjLuIA0mm/iVa5r2bw3Z2SQxAXNBlMwKXJpbe9sQwyAPIXGsvWXVLnnJY6IaFpS11JN/qw1J/i/LehBmcHJvXAqdHOa29BAoGBAJU2z0R5TvtDc2iYMO6HmDq7TSmyjX48TBywKu9Dls/BmBMtRvgL4v+QLcFA8cZWQIwW6sR7oMDnKJ3aEbG8iXMjMjes1ZiTNb8GqqXspzBAN+Lsw4M5mbjQtCG3aDujtNateWpT+Zzd8Cx7tSGWwOcCRZGOaQk11OO+p0QN4xIxAoGBAK+9vcx0CellpCOLrgda+7ZvRLvSRRxXMshmXy6NK7N5VPfHlVtkbvQlrS8sW6TSmTLsTyIvcRBrZEh6ADqMzch+37oZX3GXRrO35pcc+amDuppJk9j/8MSp29NMTMgxD1AabTcqvdj0szJm2QCs99KpOHMeh5YFIHMJLjb0joA5";

    private final String CHARSET ="UTF-8";
    // 支付宝公钥
  //  private final String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgDdCgxpzw4mp8uj6RgH/fxUCVTcu5MpUidcOzwVKkLYpDAtanLEJ0JEeCNRnxlsYowIXxxnFQY+6z5371gK8+zWWLcZ4vxhhCP5AOzoDKK0Prmd/i4pW6PWdqkqCk1cu6ZtTjgHsAsg7OGtPF1j5KK6gUyDkE3CwjHkm3TefdgaJA8RHoEdgE17GdMSR/goqRKF48hc5kabnkEkDk4NJwGKNFynIfXdL++Xrg7c7U21c1WPn12+rlhogY6OV4pElzmVa4/hzSrXWIj1oH9T73oQnUbcQysjeIpOj8/psB6rIvYr0CVzusc/gbkR9UeNG9i0eWcXfmIIUfWGviKj2QwIDAQAB";

    private final String ALIPAY_PUBLIC_KEY ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgS7siBDwCsBzdl1bBpv14mRVapoabshXFJ2aiS39+u8/x6ni0rd4P95Ixt+k3pKcB9s3FDb0sTYFMtam+DhMRlIA6vV0QKUkCfiR6+i5iuzKkFHFtxTryIhrNIgm3fpMdHPAIQfMO3sNSBszi7iG9JTCPSU69spxnoz+yoU9H3os9madu3x+sJ4J7v+ftBenPC/XT0bbusJDU0lOxnY9A6ZdKqHabFTr2eupaEBFhkrTLAu1QaGzPfNjxmc9nkwWap+CQ0g4cOkkG8lc3beey+r/hiRBeuy7IQVoRM39B+0R50V3gFrziP/uLTaEfVVe3u61lJk4FMGgKjHvRCzr+wIDAQAB";

    //这是沙箱接口路径,正式路径为https://openapi.alipay.com/gateway.do
    private final String GATEWAY_URL ="https://openapi.alipaydev.com/gateway.do";
    private final String FORMAT ="JSON";
    //签名方式
    private final String SIGN_TYPE ="RSA2";
    //支付宝异步通知路径,付款完毕后会异步调用本项目的方法,必须为公网地址
    private final String NOTIFY_URL ="http://127.0.0.1/notifyUrl";
    //支付宝同步通知路径,也就是当付款完毕后跳转本项目的页面,可以不是公网地址
    private final String RETURN_URL ="http://localhost:8080/returnUrl";




    //必须加ResponseBody注解，否则spring会寻找thymeleaf页面

    @RequestMapping("/pay/alipay")
    public String alipay(HttpSession session,
                         @RequestParam("dona_money") float dona_money,
                         @RequestParam("dona_id") int dona_id) throws AlipayApiException {
        //把dona_id项目id 放在session中
        session.setAttribute("dona_id",dona_id);

        //生成订单号（支付宝的要求？）
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String user = UUID.randomUUID().toString().replace("-","").toUpperCase();

        String OrderNum = time+user;

        //调用封装好的方法（给支付宝接口发送请求）
        return sendRequestToAlipay(OrderNum,dona_money,"冬天的第一杯奶茶");
    }
    /*
参数1：订单号
参数2：订单金额
参数3：订单名称
 */
    //支付宝官方提供的接口
    private String sendRequestToAlipay(String outTradeNo,Float totalAmount,String subject) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL,APP_ID,APP_PRIVATE_KEY,FORMAT,CHARSET,ALIPAY_PUBLIC_KEY,SIGN_TYPE);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(RETURN_URL);
        alipayRequest.setNotifyUrl(NOTIFY_URL);




        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = UUID.randomUUID().toString().substring(0,13);
        //付款金额，必填
        String total_amount = new String("88.88");
        //订单名称，必填
       // String subject=subject
        //商品描述，可空
        String body = new String("我的你的什么？你是我的优乐美");


        //商品描述（可空）
       // String body="";
/*        alipayRequest.setBizContent("{"out_trade_no":"" + outTradeNo + "","
                + ""total_amount":"" + totalAmount + "","
                + ""subject":"" + subject + "","
                + ""body":"" + body + "","
                + ""product_code":"FAST_INSTANT_TRADE_PAY"}");*/



        String bizContent="{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}";

        alipayRequest.setBizContent(bizContent);



        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();


        System.out.println("result"+result);
        return result;
    }



    @RequestMapping("/returnUrl")
    public String returnUrlMethod(HttpServletRequest request, HttpSession session, Model model) throws AlipayApiException, UnsupportedEncodingException {
        System.out.println("=同步回调=====");
        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        System.out.println(params);//查看参数都有哪些
        //验证签名（支付宝公钥）
        boolean signVerified = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE); // 调用SDK验证签名
        //验证签名通过
        if(signVerified){
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易流水号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            float money = Float.parseFloat(new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8"));

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+money);

            //在这里编写自己的业务代码（对数据库的操作）
		/*
		################################
		*/
            //跳转到提示页面（成功或者失败的提示页面）
            model.addAttribute("flag",1);
            model.addAttribute("msg","支持");
            return "common/payTips";
        }else{
            //跳转到提示页面（成功或者失败的提示页面）
            model.addAttribute("flag",0);
            model.addAttribute("msg","支持");
            return "common/payTips";
        }
    }

}
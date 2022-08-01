package com.lt.dom.controllerOct;

import com.alipay.api.internal.util.AlipaySignature;
import com.lt.dom.domain.CouponTemplatePojo;
import com.lt.dom.oct.Invitation;
import com.lt.dom.oct.Reservation;
import com.lt.dom.repository.InvitationsRepository;
import com.lt.dom.serviceOtc.SettleAccountServiceImpl;
import com.lt.dom.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
@RequestMapping("/invitations")
public class PayController {




    @Autowired
    private SettleAccountServiceImpl settleAccountService;


    /**
     * 支付回调函数（当用户支付成功之后，支付宝会自动调用该方法）
     * 此接口需要可以被外网访问而且必须是POST请求，并且注意拦截器是否拦截（如果被被您的登录拦截器拦截了，支付宝就无法访问此方法了）
     * @param request
     * @param response
     */
    @RequestMapping("/ZFBcallback")
    public void ZFBcallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            CommonUtils commonUtils=new CommonUtils();
            //支付宝公钥
            String alipay_public_key=commonUtils.getZFBinfoValue("alipay_public_key");
            PrintWriter out;
            out = response.getWriter();
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap<String, String>();
            Map requestParams = request.getParameterMap();
            //循环遍历支付宝请求过来的参数存入到params中
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //异步验签：切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            boolean flag = AlipaySignature.rsaCheckV1(params, alipay_public_key, "utf-8","RSA2");
            if (flag){
                //说明是支付宝调用的本接口
                if (params.get("trade_status").equals("TRADE_SUCCESS") || params.get("trade_status").equals("TRADE_FINISHED")) {
                    System.out.println("收到回调结果，用户已经完成支付");

                    settleAccountService.paySuccess(new Reservation());
                    /***
                     * 这里写您的业务逻辑代码
                     */
                    out.write("success");
                }
            }else {
                //验签失败该接口被别人调用
                out.write("支付宝异步回调验签失败，请留意");
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
————————————————
    版权声明：本文为CSDN博主「蓝胖子哇」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/weixin_44004020/article/details/111472797
*/

}
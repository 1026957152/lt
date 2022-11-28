package com.lt.dom.Scheduled;


import com.google.gson.Gson;
import com.lt.dom.controllerOct.Axh.XhToYxdService;
import com.lt.dom.controllerOct.Axh.model.CreditWaitConfirmVO;
import com.lt.dom.controllerOct.Axh.model.InsReject;
import com.lt.dom.controllerOct.Axh.model.YxdToXHResponse;
import com.lt.dom.oct.Axh.XydToXhPushRequest;
import com.lt.dom.oct.Axh.XydToXhPushRequestJsonFit;
import com.lt.dom.oct.RightRedemptionEntry;
import com.lt.dom.otcenum.enum_.EnumClinchPayWay还款方式;
import com.lt.dom.otcenum.enum_.EnumGuarantyWay担保方式;
import com.lt.dom.otcenum.enum_.EnumXhPushRequestStatus;
import com.lt.dom.repository.Axh.XydToXhPushRequestRepository;
import com.lt.dom.repository.RightRedemptionEntryRepository;
import com.lt.dom.repository.UsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class XhScheduled {
    private static final Logger logger = LoggerFactory.getLogger(XhScheduled.class);


    @Autowired
    UsageRepository usageRepository;



    @Autowired
    RightRedemptionEntryRepository rightRedemptionEntryRepository;

    @Autowired
    XydToXhPushRequestRepository xydToXhPushRequestRepository;


    @Autowired
    XhToYxdService xhToYxdService;

    @Scheduled(fixedDelay = 60000)
    // @Scheduled(cron = "${erwin.cron:0/2 * * * * ?}")
    public void cronTaskYmlDemoError() {
        System.out.println("这里是错误的处理");
        List<XydToXhPushRequest> subscriptions = xydToXhPushRequestRepository.findAll();
        List<XydToXhPushRequest> error = subscriptions.stream()
                .filter(e->e.getStatus_xh().equals(EnumXhPushRequestStatus.Error)).collect(toList());
        cronTaskYmlDemo(error);
    }
    @Scheduled(fixedDelay = 60000)
    // @Scheduled(cron = "${erwin.cron:0/2 * * * * ?}")
    public void cronTaskYmlDemoCreate() {

        System.out.println("这里是新建的处理");

        List<XydToXhPushRequest> subscriptions = xydToXhPushRequestRepository.findAll();


        List<XydToXhPushRequest> xydToXhPushRequests = subscriptions.stream()
                .filter(e->e.getStatus_xh().equals(EnumXhPushRequestStatus.Created))

                .filter(e->e.getCreatedDate().isAfter(LocalDateTime.now().minusMinutes(5))).collect(toList());

        cronTaskYmlDemo(xydToXhPushRequests);


    }

    public void cronTaskYmlDemo(List<XydToXhPushRequest> xydToXhPushRequests) {





       xydToXhPushRequests.stream().forEach(e->{

           XydToXhPushRequestJsonFit xydToXhPushRequestJsonFit = new Gson().fromJson(e.getJson(),XydToXhPushRequestJsonFit.class);

           System.out.println("测试报告："+xydToXhPushRequestJsonFit.getProductInfo().getProductName());
           if(!xydToXhPushRequestJsonFit.getProductInfo().getProductName().equals("小V税贷")){
               return;
           }
           if(xydToXhPushRequestJsonFit.getCrAmount()!=0f){
               try{
                   YxdToXHResponse yxdToXHResponse = main(xydToXhPushRequestJsonFit);
                   e.setStatus_xh(EnumXhPushRequestStatus.Approved);
                   xydToXhPushRequestRepository.save(e);
               }catch (Exception eee){
                   e.setStatus_xh(EnumXhPushRequestStatus.Error);
                   xydToXhPushRequestRepository.save(e);
               }

           }else{
               try{
                   YxdToXHResponse yxdToXHResponse = reject(xydToXhPushRequestJsonFit);
                   e.setStatus_xh(EnumXhPushRequestStatus.Rejected);
                   xydToXhPushRequestRepository.save(e);
               }catch (Exception eee){
                   e.setStatus_xh(EnumXhPushRequestStatus.Error);
                   xydToXhPushRequestRepository.save(e);
               }
           }

        });





        System.out.println("cron yml demo");
    }


    private String encodeValue(String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private YxdToXHResponse main(XydToXhPushRequestJsonFit amount){

        String url_website = "https://wbank.96262.com/icap-wbank/page/03/0315/031501/031501.html?t=1662004370329?code=011Cvj100knSnK1L88400OdfCv2Cvj1T&state=1&t=1662004370330";


        String url_qr = "http://111.20.184.51:81/upload-files/1/1268859028401577986/cadddeba-22da-4bbe-b59b-8f3359ef613ansh_xwsd_qrcod.png";
        CreditWaitConfirmVO code = new CreditWaitConfirmVO();
        //   code.setQrcode(url_qr);
        //  code.setAttachment_上传返款证明返回信息(url);

        code.setQrcode(encodeValue(url_qr));
        code.setAttachment_上传返款证明返回信息(encodeValue(url_website));
        code.setCreditAmount_授信额度(amount.getCrAmount());


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            code.setEndTime_授信额度有效期至(LocalDateTime.now().plusYears(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            code.setOrderId_申请id(amount.getOrderId());
            code.setInstId_机构id(9);


            code.setLoanLimit_贷款期限(12);
            code.setLoanRate_贷款年化利率(3.85f);


            code.setPayWay_还款方式(EnumClinchPayWay还款方式.等额本息.getId());
            code.setGuarantyWay_担保方式(EnumGuarantyWay担保方式.信用.getId());



            System.out.println("=====ddd===="+code.toString());

            System.out.println("请求信易贷："+new Gson().toJson(code));
            YxdToXHResponse yxdToXHResponse =  xhToYxdService.creditWaitConfirmVO(code, XhToYxdService.older_token.getAccessToken());


            return yxdToXHResponse;


    }



    private YxdToXHResponse reject(XydToXhPushRequestJsonFit amount){

        InsReject code = new InsReject();

        code.setOrderId_申请id(amount.getOrderId());

        code.setMsg("您的不符合授信条件");

        System.out.println("我的请求参数是"+(new Gson().toJson(code).toString()));

        System.out.println("请求信易贷："+new Gson().toJson(code));
        YxdToXHResponse yxdToXHResponse =  xhToYxdService.insReject(code);
        return yxdToXHResponse;


    }
}



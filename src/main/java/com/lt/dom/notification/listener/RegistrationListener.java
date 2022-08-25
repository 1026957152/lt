package com.lt.dom.notification.listener;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.lt.dom.notification.event.OnRegistrationCompleteEvent;
import com.lt.dom.oct.User;
import com.lt.dom.serviceOtc.IUserService;
import com.lt.dom.serviceOtc.SMSServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {
 
    @Autowired
    private IUserService service;
 
    @Autowired
    private MessageSource messages;
    @Autowired
    private SMSServiceImpl smsService;
/*    @Autowired
    private JavaMailSender mailSender;*/

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();

        //使用Hutool插件定义图形验证码的长和宽,验证码个数，干扰线条数

        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100,4,20);
        String imageBase64 = lineCaptcha.getImageBase64();


        String token =lineCaptcha.getCode();// UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl 
          = event.getAppUrl() + "/regitrationConfirm?token=" + token;
       // String message = messages.getMessage("message.regSucc", null, event.getLocale());
        //你的验证码是#code#






        //把验证码存到session
        //redisUtil.set("code", lineCaptcha.getCode(), 60);

        String greetings = String.format(
                "你的验证码是%s",
                lineCaptcha.getCode());

       smsService.singleSend(greetings,"13468801683");
/*        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);*/
    }
}
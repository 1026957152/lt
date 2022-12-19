package com.lt.dom.controllerOct.websocket;

import com.lt.dom.controllerOct.IndexController;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
public class SubscribeAndSendController {
    private static final Logger log = LoggerFactory.getLogger(SubscribeAndSendController.class);

    /**
     * 1.普通的订阅与推送
     * @return
     */
    @SubscribeMapping("/topic/subscribe/first")
    public ResponseEntity subscribe1(){
        log.info("订阅1----------");
        return ResponseEntity.ok("订阅1成功");
    }
    @MessageMapping("/topic/send/first")
    @SendTo("/topic/subscribe/first")
    public ResponseEntity recieve1(String msg){
        log.info("发送1：{}", msg);
        return ResponseEntity.ok(msg);
    }

    /**
     * 2.url传参的订阅与推送（Message适用,Subscribe不适用, 因为@SendTo识别不到）
     * @return
     */
    @SubscribeMapping("/topic/subscribe/second")
    public ResponseEntity subscribe2(){
        return ResponseEntity.ok("订阅2成功");
    }
    @MessageMapping("/topic/send/second/{name}/{gender}")
    @SendTo("/topic/subscribe/second")
    public ResponseEntity recieve2(String msg,@DestinationVariable("name") String name, @DestinationVariable("gender") String gender){
        log.info("发送2----------:{},{},{}",msg, name, gender);
        return ResponseEntity.ok(msg);
    }

    /**
     * 3.headers传参（Subscribe、Message都适用）
     * 可以通过@Headers取得所有参数，也可以通过@Header单独取出需要的数据
     * @param name
     * @param gender
     * @param headers
     * @return
     */
    @SubscribeMapping("/topic/subscribe/third")
    public ResponseEntity subscribe3(@Header("name")String name, @Header("gender")String gender, @Headers Map<String, Object> headers){
        log.info("订阅3----------:{},{},{}",name, gender, headers);
        return ResponseEntity.ok("订阅3成功");
    }
    @MessageMapping("/topic/send/third")
    @SendTo("/topic/subscribe/third")
    public ResponseEntity recieve3(String msg) {
        log.info("发送3：{}", msg);

        return ResponseEntity.ok("dd");

    }
    /**
     * 3.1
     * @return
     */
    @SubscribeMapping("/topic/subscribe/third1")
    public ResponseEntity subscribe31(){
        log.info("订阅3.1");
        return ResponseEntity.ok("订阅3.1成功");
    }
    @MessageMapping("/topic/send/third1")
    @SendTo("/topic/subscribe/third1")
    public ResponseEntity recieve31(String msg, @Header(value = "name")String name, @Header(value = "gender", required = false)String gender, @Headers Map<String, Object> headers){
        log.info("发送3.1----------:{},{},{},{}", msg, name, gender, headers);
        return ResponseEntity.ok(msg);
    }

    /**
     * 点对点的订阅与推送
     * @return
     */
    @SubscribeMapping("/user/topic/order")
    public ResponseEntity subscribe4(){
        log.info("订阅4成功");
        return ResponseEntity.ok("订阅4成功");
    }
    @MessageMapping("/user/topic/message")
    @SendToUser("/topic/order")
    public ResponseEntity recieve4(String msg){
        log.info("发送4----------:{}", msg);
        return ResponseEntity.ok(msg);
    }

}
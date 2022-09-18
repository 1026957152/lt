package com.lt.dom.rabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class QueueConsumer {



    @RabbitListener(queues = {"${queue.name}"})
    public void receive(@Payload String fileBody) {
        System.out.println("Message " + fileBody);
    }
    @RabbitListener(queues = {"testee"})
    public void receiveTrestee(@Payload String fileBody,Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        System.out.println("Message " + fileBody);

        try {

            /**
             * 无异常就确认消息
             * basicAck(long deliveryTag, boolean multiple)
             * deliveryTag:取出来当前消息在队列中的的索引;
             * multiple:为true的话就是批量确认,如果当前deliveryTag为5,那么就会确认
             * deliveryTag为5及其以下的消息;一般设置为false
             */
            channel.basicAck(tag, false);
        }catch (Exception e){
            /**
             * 有异常就绝收消息
             * basicNack(long deliveryTag, boolean multiple, boolean requeue)
             * requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;
             *         false:将消息丢弃
             */
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
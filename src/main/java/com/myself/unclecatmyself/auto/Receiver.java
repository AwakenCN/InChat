package com.myself.unclecatmyself.auto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by MySelf on 2018/12/3.
 */
@Slf4j
@Component
public class Receiver{


    @RabbitListener(queues = RabbitMQConfig.queueName)
    public void receiveMessage(String message){
        log.info("Received < " + message + " >");
    }

}

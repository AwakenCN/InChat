package com.myself.unclecatmyself.auto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by MySelf on 2018/12/3.
 */
@Slf4j
@Component
public class Sender {

    private final RabbitTemplate rabbitTemplate;

    public Sender(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Map<String,Object> maps){
        rabbitTemplate.convertAndSend(RabbitMQConfig.topicExchangeName,"inchat.",maps);
    }

}

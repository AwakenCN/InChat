package com.myself.unclecatmyself.auto;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MySelf on 2018/12/3.
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {

    static final String topicExchangeName = "inchat-exchange";

    static final String queueName = "inchatQueue";

    @Bean
    Queue queue(){
        return new Queue(queueName,false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue queue,TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("inchat.#");
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }

}

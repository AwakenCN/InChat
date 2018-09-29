package com.myself.nettychat.common.mqtts;

import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.timeout.IdleStateEvent;


/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 抽象出客户端的事件
 **/
public abstract class ClientMqttHandlerService implements MqttHandlerIntf {

    @Override
    public void doTimeOut(Channel channel, IdleStateEvent evt) {
        heart(channel,evt);
    }

    public abstract void  heart(Channel channel, IdleStateEvent evt);

    public abstract void suback(Channel channel,MqttSubAckMessage mqttMessage) ;

    public abstract void pubBackMessage(Channel channel, int i);

    public abstract void unsubBack(Channel channel, MqttMessage mqttMessage);
}

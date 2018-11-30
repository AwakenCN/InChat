package com.myself.unclecatmyself.bootstrap.scan;

import com.myself.unclecatmyself.bootstrap.bean.SendMqttMessage;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 扫描消息确认
 **/
@Slf4j
public class SacnScheduled extends ScanRunnable {

    private final long time;

    public SacnScheduled(long time) {
        this.time = time;
    }

    private boolean checkTime(long time) {
        return System.currentTimeMillis()-time>=10*1000;
    }

    @Override
    public void doInfo(SendMqttMessage poll) {
        if(checkTime(poll.getTime()) && poll.getChannel().isActive()){
            poll.setTime(System.currentTimeMillis());
            switch (poll.getConfirmStatus()){
                case PUB:
                    pubMessage(poll.getChannel(),poll);
                    break;
                case PUBREL:
                    sendAck(MqttMessageType.PUBREL,poll);
                    break;
                case PUBREC:
                    sendAck(MqttMessageType.PUBREC,poll);
                    break;
            }
        }
    }

    private   void pubMessage(Channel channel, SendMqttMessage mqttMessage){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH,true, mqttMessage.getQos(),mqttMessage.isRetain(),0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader(mqttMessage.getTopic(),mqttMessage.getMessageId());
        MqttPublishMessage mqttPublishMessage = new MqttPublishMessage(mqttFixedHeader,mqttPublishVariableHeader, Unpooled.wrappedBuffer(mqttMessage.getByteBuf()));
        channel.writeAndFlush(mqttPublishMessage);
    }

    protected void  sendAck(MqttMessageType type,SendMqttMessage mqttMessage){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(type,true, MqttQoS.AT_LEAST_ONCE,false,0x02);
        MqttMessageIdVariableHeader from = MqttMessageIdVariableHeader.from(mqttMessage.getMessageId());
        MqttPubAckMessage mqttPubAckMessage = new MqttPubAckMessage(mqttFixedHeader,from);
        mqttMessage.getChannel().writeAndFlush(mqttPubAckMessage);
    }

}

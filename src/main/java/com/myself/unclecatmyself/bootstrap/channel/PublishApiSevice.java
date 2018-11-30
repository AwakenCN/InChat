package com.myself.unclecatmyself.bootstrap.channel;

import com.myself.unclecatmyself.bootstrap.bean.MqttChannel;
import com.myself.unclecatmyself.bootstrap.bean.SendMqttMessage;
import com.myself.unclecatmyself.bootstrap.bean.WillMeaasge;
import com.myself.unclecatmyself.bootstrap.scan.ScanRunnable;
import com.myself.unclecatmyself.common.utils.MessageId;
import com.myself.unclecatmyself.common.enums.ConfirmStatus;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 发送消息以及确认
 **/
@Slf4j
public class PublishApiSevice {

    private final ScanRunnable scanRunnable;

    public PublishApiSevice(ScanRunnable scanRunnable) {
        this.scanRunnable = scanRunnable;
    }


    /**
     * 写入遗嘱消息
     */
    protected void writeWillMsg(MqttChannel mqttChannel, WillMeaasge willMeaasge) {
//        dup保证消息可靠传输，默认为0，只占用一个字节，表示第一次发送。不能用于检测消息重复发送等
        switch (willMeaasge.getQos()){
            case 0: // qos0
                sendQos0Msg(mqttChannel.getChannel(),willMeaasge.getWillTopic(),willMeaasge.getWillMessage().getBytes());
                break;
            case 1: // qos1
                sendQosConfirmMsg(MqttQoS.AT_LEAST_ONCE,mqttChannel,willMeaasge.getWillTopic(),willMeaasge.getWillMessage().getBytes());
                break;
            case 2: // qos2
                sendQosConfirmMsg(MqttQoS.EXACTLY_ONCE,mqttChannel,willMeaasge.getWillTopic(),willMeaasge.getWillMessage().getBytes());
                break;
        }


    }

    protected void sendQosConfirmMsg(MqttQoS qos, MqttChannel mqttChannel, String topic, byte[] bytes) {
        if(mqttChannel.isLogin()){
            int messageId = MessageId.messageId();
            switch (qos){
                case AT_LEAST_ONCE:
                    mqttChannel.addSendMqttMessage(messageId,sendQos1Msg(mqttChannel.getChannel(),topic,false,bytes,messageId));
                    break;
                case EXACTLY_ONCE:
                    mqttChannel.addSendMqttMessage(messageId,sendQos2Msg(mqttChannel.getChannel(),topic,false,bytes,messageId));
                    break;
            }
        }

    }


    /**
     * 发送 qos1 类的消息
     */
    private SendMqttMessage sendQos1Msg(Channel channel, String topic, boolean isDup, byte[] byteBuf, int messageId){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH,isDup, MqttQoS.AT_LEAST_ONCE,false,0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader(topic,messageId );
        MqttPublishMessage mqttPublishMessage = new MqttPublishMessage(mqttFixedHeader,mqttPublishVariableHeader, Unpooled.wrappedBuffer(byteBuf));
        channel.writeAndFlush(mqttPublishMessage);
        return addQueue(channel,messageId,topic,byteBuf,MqttQoS.AT_LEAST_ONCE, ConfirmStatus.PUB);
    }



    /**
     * 发送 qos0 类的消息  byte
     */
    protected   void  sendQos0Msg(Channel channel, String topic, byte[] byteBuf){
        if(channel!=null){
            sendQos0Msg(channel,topic,byteBuf,0);
        }
    }
    private    void  sendQos0Msg(Channel channel, String topic, byte[] byteBuf,int messageId){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH,false, MqttQoS.AT_MOST_ONCE,false,0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader(topic,messageId );
        MqttPublishMessage mqttPublishMessage = new MqttPublishMessage(mqttFixedHeader,mqttPublishVariableHeader,Unpooled.wrappedBuffer(byteBuf));
        channel.writeAndFlush(mqttPublishMessage);
    }




    private SendMqttMessage sendQos2Msg(Channel channel, String topic,boolean isDup, byte[] byteBuf, int messageId) {
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH,isDup, MqttQoS.EXACTLY_ONCE,false,0);
        MqttPublishVariableHeader mqttPublishVariableHeader = new MqttPublishVariableHeader(topic,messageId );
        MqttPublishMessage mqttPublishMessage = new MqttPublishMessage(mqttFixedHeader,mqttPublishVariableHeader, Unpooled.wrappedBuffer(byteBuf));
        channel.writeAndFlush(mqttPublishMessage);
        return addQueue(channel,messageId,topic,byteBuf,MqttQoS.EXACTLY_ONCE,ConfirmStatus.PUB);
    }


    /**
     * 发送qos1 publish  确认消息
     */
    protected   void  sendPubBack(Channel channel,int messageId){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBACK,false, MqttQoS.AT_MOST_ONCE,false,0x02);
        MqttMessageIdVariableHeader from = MqttMessageIdVariableHeader.from(messageId);
        MqttPubAckMessage mqttPubAckMessage = new MqttPubAckMessage(mqttFixedHeader,from);
        channel.writeAndFlush(mqttPubAckMessage);
    }


    /**
     * 发送qos2 publish  确认消息 第一步
     */
    protected   void  sendPubRec( MqttChannel mqttChannel,int messageId){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBREC,false, MqttQoS.AT_LEAST_ONCE,false,0x02);
        MqttMessageIdVariableHeader from = MqttMessageIdVariableHeader.from(messageId);
        MqttPubAckMessage mqttPubAckMessage = new MqttPubAckMessage(mqttFixedHeader,from);
        Channel channel = mqttChannel.getChannel();
        channel.writeAndFlush(mqttPubAckMessage);
        SendMqttMessage sendMqttMessage = addQueue(channel, messageId, null, null, null, ConfirmStatus.PUBREC);
        mqttChannel.addSendMqttMessage(messageId,sendMqttMessage);
    }

    /**
     * 发送qos2 publish  确认消息 第二步
     */
    protected   void  sendPubRel(Channel channel,boolean isDup,int messageId){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBREL,isDup, MqttQoS.AT_LEAST_ONCE,false,0x02);
        MqttMessageIdVariableHeader from = MqttMessageIdVariableHeader.from(messageId);
        MqttPubAckMessage mqttPubAckMessage = new MqttPubAckMessage(mqttFixedHeader,from);
        channel.writeAndFlush(mqttPubAckMessage);
    }

    /**
     * 发送qos2 publish  确认消息 第三步
     */
    protected   void  sendToPubComp(Channel channel,int messageId){
        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.PUBCOMP,false, MqttQoS.AT_MOST_ONCE,false,0x02);
        MqttMessageIdVariableHeader from = MqttMessageIdVariableHeader.from(messageId);
        MqttPubAckMessage mqttPubAckMessage = new MqttPubAckMessage(mqttFixedHeader,from);
        channel.writeAndFlush(mqttPubAckMessage);
    }

    private SendMqttMessage  addQueue(Channel channel,int messageId,String topic,byte[] datas,MqttQoS mqttQoS,ConfirmStatus confirmStatus){
        SendMqttMessage build = SendMqttMessage.builder().
                channel(channel).
                confirmStatus(confirmStatus).
                messageId(messageId)
                .topic(topic)
                .qos(mqttQoS)
                .byteBuf(datas)
                .time(System.currentTimeMillis()).build();
        scanRunnable.addQueue(build);
        return build;
    }

}

package com.myself.nettychat.bootstrap.bean;


import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.Builder;
import lombok.Data;

import com.myself.nettychat.common.enums.ConfirmStatus;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc mqtts 消息
 **/
@Builder
@Data
public class SendMqttMessage {


    private int messageId;

    private Channel channel;

    private volatile ConfirmStatus confirmStatus;

    private long time;

    private byte[]  byteBuf;

    private boolean isRetain;

    private MqttQoS qos;

    private String topic;

}

package com.myself.nettychat.bootstrap.bean;


import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.Builder;
import lombok.Data;


/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc Session会话数据保存
 **/
@Builder
@Data
public class SessionMessage {

    private byte[]  byteBuf;

    private MqttQoS qoS;

    private  String topic;


    public String getString(){
        return new String(byteBuf);
    }

}

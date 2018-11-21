package com.myself.nettychat.bootstrap;

import com.myself.nettychat.bootstrap.bean.MqttChannel;
import com.myself.nettychat.bootstrap.bean.WillMeaasge;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;

import java.util.List;
import java.util.Set;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 消息处理
 **/
public interface ChannelService {

    MqttChannel getMqttChannel(String deviceId);

    boolean connectSuccess(String s, MqttChannel build);

    void suscribeSuccess(String deviceId, Set<String> topics);

    void loginSuccess(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage);

    void publishSuccess(Channel channel, MqttPublishMessage mqttPublishMessage);

    void closeSuccess(String deviceId,boolean isDisconnect);

    void sendWillMsg(WillMeaasge willMeaasge);

    String  getDeviceId(Channel channel);

    void unsubscribe(String deviceId, List<String> topics1);

    void  doPubrel(Channel channel, int mqttMessage);

    void  doPubrec(Channel channel, int mqttMessage);

}

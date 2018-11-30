package com.myself.unclecatmyself.bootstrap.channel;

import com.myself.unclecatmyself.bootstrap.bean.*;
import com.myself.unclecatmyself.bootstrap.scan.ScanRunnable;
import com.myself.unclecatmyself.common.enums.ConfirmStatus;
import com.myself.unclecatmyself.common.enums.SessionStatus;
import com.myself.unclecatmyself.common.enums.SubStatus;
import com.myself.unclecatmyself.common.exception.ConnectionException;
import com.myself.unclecatmyself.common.utils.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc Channel事件处理service
 **/
@Slf4j
//@Component
public class MqttChannelService extends AbstractChannelService {

    @Autowired
    private ClientSessionService clientSessionService;

    @Autowired
    private WillService willService;

    private final ScanRunnable scanRunnable;

    public MqttChannelService(ScanRunnable scanRunnable) {
        super(scanRunnable);
        this.scanRunnable = scanRunnable;
    }


    /**
     * 取消订阅
     */
    @Override
    public void unsubscribe(String deviceId, List<String> topics1) {
        Optional.ofNullable(mqttChannels.get(deviceId)).ifPresent(mqttChannel -> {
            topics1.forEach(topic -> {
                deleteChannel(topic,mqttChannel);
            });
        });
    }

    /**
     * 登录成功后 回复
     */
    private void replyLogin(Channel channel, MqttConnectMessage mqttConnectMessage) {
        MqttFixedHeader mqttFixedHeader1 = mqttConnectMessage.fixedHeader();
        MqttConnectVariableHeader mqttConnectVariableHeader = mqttConnectMessage.variableHeader();
        final MqttConnectPayload payload = mqttConnectMessage.payload();
        String deviceId = getDeviceId(channel);
        MqttChannel build = MqttChannel.builder().channel(channel).cleanSession(mqttConnectVariableHeader.isCleanSession())
                .deviceId(payload.clientIdentifier())
                .sessionStatus(SessionStatus.OPEN)
                .isWill(mqttConnectVariableHeader.isWillFlag())
                .subStatus(SubStatus.NO)
                .topic(new CopyOnWriteArraySet<>())
                .message(new ConcurrentHashMap<>())
                .receive(new CopyOnWriteArraySet<>())
                .build();
        if (connectSuccess(deviceId, build)) { // 初始化存储mqttchannel
            if (mqttConnectVariableHeader.isWillFlag()) { // 遗嘱消息标志
                boolean b = doIf(mqttConnectVariableHeader, mqttConnectVariableHeader1 -> (payload.willMessage() != null)
                        , mqttConnectVariableHeader1 -> (payload.willTopic() != null));
                if (!b) {
                    throw new ConnectionException("will message and will topic is not null");
                }
                // 处理遗嘱消息
                final WillMeaasge buildWill = WillMeaasge.builder().
                        qos(mqttConnectVariableHeader.willQos())
                        .willMessage(deviceId)
                        .willTopic(payload.willTopic())
                        .isRetain(mqttConnectVariableHeader.isWillRetain())
                        .build();
                willService.save(payload.clientIdentifier(), buildWill);
            } else {
                willService.del(payload.clientIdentifier());
                boolean b = doIf(mqttConnectVariableHeader, mqttConnectVariableHeader1 -> (!mqttConnectVariableHeader1.isWillRetain()),
                        mqttConnectVariableHeader1 -> (mqttConnectVariableHeader1.willQos() == 0));
                if (!b) {
                    throw new ConnectionException("will retain should be  null and will QOS equal 0");
                }
            }
            doIfElse(mqttConnectVariableHeader, mqttConnectVariableHeader1 -> (mqttConnectVariableHeader1.isCleanSession()), mqttConnectVariableHeader1 -> {
                MqttConnectReturnCode connectReturnCode = MqttConnectReturnCode.CONNECTION_ACCEPTED;
                MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(connectReturnCode, false);
                MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(
                        MqttMessageType.CONNACK, mqttFixedHeader1.isDup(), MqttQoS.AT_MOST_ONCE, mqttFixedHeader1.isRetain(), 0x02);
                MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
                channel.writeAndFlush(connAck);// 清理会话
            }, mqttConnectVariableHeader1 -> {
                MqttConnectReturnCode connectReturnCode = MqttConnectReturnCode.CONNECTION_ACCEPTED;
                MqttConnAckVariableHeader mqttConnAckVariableHeader = new MqttConnAckVariableHeader(connectReturnCode, true);
                MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(
                        MqttMessageType.CONNACK, mqttFixedHeader1.isDup(), MqttQoS.AT_MOST_ONCE, mqttFixedHeader1.isRetain(), 0x02);
                MqttConnAckMessage connAck = new MqttConnAckMessage(mqttFixedHeader, mqttConnAckVariableHeader);
                channel.writeAndFlush(connAck);// 非清理会话

            });         //发送 session  数据
            ConcurrentLinkedQueue<SessionMessage> sessionMessages = clientSessionService.getByteBuf(payload.clientIdentifier());
            doIfElse(sessionMessages, messages -> messages != null && !messages.isEmpty(), byteBufs -> {
                SessionMessage sessionMessage;
                while ((sessionMessage = byteBufs.poll()) != null) {
                    switch (sessionMessage.getQoS()) {
                        case EXACTLY_ONCE:
                            sendQosConfirmMsg(MqttQoS.EXACTLY_ONCE,getMqttChannel(deviceId), sessionMessage.getTopic(), sessionMessage.getByteBuf());
                            break;
                        case AT_MOST_ONCE:
                            sendQos0Msg(channel, sessionMessage.getTopic(), sessionMessage.getByteBuf());
                            break;
                        case AT_LEAST_ONCE:
                            sendQosConfirmMsg(MqttQoS.AT_LEAST_ONCE,getMqttChannel(deviceId), sessionMessage.getTopic(), sessionMessage.getByteBuf());
                            break;
                    }
                }

            });
        }
    }



    /**
     * qos2 第二步
     */
    @Override
    public void doPubrel(Channel channel, int messageId) {
        MqttChannel mqttChannel = getMqttChannel(getDeviceId(channel));
        doIfElse(mqttChannel,mqttChannel1 ->mqttChannel1.isLogin(),mqttChannel1 -> {
            mqttChannel1.removeRecevice(messageId);
            sendToPubComp(channel,messageId);
        });
    }



    /**
     * qos2 第三步
     */
    @Override
    public void doPubrec(Channel channel, int mqttMessage) {
        sendPubRel(channel,false,mqttMessage);
    }

    /**
     * 连接成功后
     * @param deviceId
     * @param build
     */
    @Override
    public boolean connectSuccess(String deviceId, MqttChannel build) {
        return  Optional.ofNullable(mqttChannels.get(deviceId))
                .map(mqttChannel -> {
                    switch (mqttChannel.getSessionStatus()){
                        case OPEN:
                            return false;
                        case CLOSE:
                            switch (mqttChannel.getSubStatus()){
                                case YES: // 清除订阅  topic
                                    deleteSubTopic(mqttChannel).stream()
                                            .forEach(s -> cacheMap.putData(getTopic(s),build));
                                    break;
                            }
                    }
                    mqttChannels.put(deviceId,build);
                    return true;
                }).orElseGet(() -> {
                    mqttChannels.put(deviceId,build);
                    return  true;
                });
    }


    /**
     * 订阅成功后 (发送保留消息)
     */
    public void suscribeSuccess(String deviceId, Set<String> topics){
        doIfElse(topics,topics1->!CollectionUtils.isEmpty(topics1),strings -> {
            MqttChannel mqttChannel = mqttChannels.get(deviceId);
            mqttChannel.setSubStatus(SubStatus.YES); // 设置订阅主题标识
            mqttChannel.addTopic(strings);
            executorService.execute(() -> {
                Optional.ofNullable(mqttChannel).ifPresent(mqttChannel1 -> {
                    if(mqttChannel1.isLogin()){
                        strings.parallelStream().forEach(topic -> {
                            addChannel(topic,mqttChannel);
                            sendRetain(topic,mqttChannel); // 发送保留消息
                        });
                    }
                });
            });
        });
    }


    /**
     *成功登陆 (发送会话消息)
     * @param channel
     * @param deviceId
     * @param mqttConnectMessage
     */
    @Override
    public void loginSuccess(Channel channel, String deviceId, MqttConnectMessage mqttConnectMessage) {
        channel.attr(_login).set(true);
        channel.attr(_deviceId).set(deviceId);
        replyLogin(channel, mqttConnectMessage);
    }


    /**
     * 发布消息成功 ()
     * @param channel
     * @param mqttPublishMessage
     */
    @Override
    public void publishSuccess(Channel channel, MqttPublishMessage mqttPublishMessage) {
        MqttFixedHeader mqttFixedHeader = mqttPublishMessage.fixedHeader();
        MqttPublishVariableHeader mqttPublishVariableHeader = mqttPublishMessage.variableHeader();
        MqttChannel mqttChannel = getMqttChannel(getDeviceId(channel));
        ByteBuf payload = mqttPublishMessage.payload();
        byte[] bytes = ByteBufUtil.copyByteBuf(payload); //
        int messageId = mqttPublishVariableHeader.messageId();
        executorService.execute(() -> {
            if (channel.hasAttr(_login) && mqttChannel != null) {
                boolean isRetain;
                switch (mqttFixedHeader.qosLevel()) {
                    case AT_MOST_ONCE: // 至多一次
                        break;
                    case AT_LEAST_ONCE:
                        sendPubBack(channel, messageId);
                        break;
                    case EXACTLY_ONCE:
                        sendPubRec(mqttChannel, messageId);
                        break;
                }
                if ((isRetain=mqttFixedHeader.isRetain()) && mqttFixedHeader.qosLevel() != MqttQoS.AT_MOST_ONCE) { //是保留消息  qos >0
                    saveRetain(mqttPublishVariableHeader.topicName(),
                            RetainMessage.builder()
                                    .byteBuf(bytes)
                                    .qoS(mqttFixedHeader.qosLevel())
                                    .build(), false);
                } else if (mqttFixedHeader.isRetain() && mqttFixedHeader.qosLevel() == MqttQoS.AT_MOST_ONCE) { // 是保留消息 qos=0  清除之前保留消息 保留现在
                    saveRetain(mqttPublishVariableHeader.topicName(),
                            RetainMessage.builder()
                                    .byteBuf(bytes)
                                    .qoS(mqttFixedHeader.qosLevel())
                                    .build(), true);
                }
                if (!mqttChannel.checkRecevice(messageId)) {
                    push(mqttPublishVariableHeader.topicName(), mqttFixedHeader.qosLevel(), bytes,isRetain);
                    mqttChannel.addRecevice(messageId);
                }
            }
        });

    }
    /**
     * 推送消息给订阅者
     */
    private  void push(String topic, MqttQoS qos, byte[] bytes, boolean isRetain){
        Collection<MqttChannel> subChannels = getChannels(topic, topic1 -> cacheMap.getData(getTopic(topic1)));
        if(!CollectionUtils.isEmpty(subChannels)){
            subChannels.parallelStream().forEach(subChannel -> {
                switch (subChannel.getSessionStatus()){
                    case OPEN: // 在线
                        if(subChannel.isActive()){ // 防止channel失效  但是离线状态没更改
                            switch (qos){
                                case AT_LEAST_ONCE:
                                    sendQosConfirmMsg(MqttQoS.AT_LEAST_ONCE,subChannel,topic,bytes);
                                    break;
                                case AT_MOST_ONCE:
                                    sendQos0Msg(subChannel.getChannel(),topic,bytes);
                                    break;
                                case EXACTLY_ONCE:
                                    sendQosConfirmMsg(MqttQoS.EXACTLY_ONCE,subChannel,topic,bytes);
                                    break;
                            }
                        }
                        else{
                            if(!subChannel.isCleanSession() & !isRetain){
                                clientSessionService.saveSessionMsg(subChannel.getDeviceId(),
                                        SessionMessage.builder().byteBuf(bytes).qoS(qos).topic(topic).build() );
                                break;
                            }
                        }
                        break;
                    case CLOSE: // 连接 设置了 clean session =false
                        clientSessionService.saveSessionMsg(subChannel.getDeviceId(),
                                SessionMessage.builder().byteBuf(bytes).qoS(qos).topic(topic).build() );
                        break;
                }
            });
        }
    }

    /**
     * 关闭channel 操作
     * @param deviceId
     */
    @Override
    public void closeSuccess(String deviceId,boolean isDisconnect) {
        if(StringUtils.isNotBlank(deviceId)){
            executorService.execute(() -> {
                MqttChannel mqttChannel = mqttChannels.get(deviceId);
                Optional.ofNullable(mqttChannel).ifPresent(mqttChannel1 -> {
                    mqttChannel1.setSessionStatus(SessionStatus.CLOSE); // 设置关闭
                    mqttChannel1.close(); // 关闭channel
                    mqttChannel1.setChannel(null);
                    if(!mqttChannel1.isCleanSession()){ // 保持会话
                        // 处理 qos1 未确认数据
                        ConcurrentHashMap<Integer, SendMqttMessage> message = mqttChannel1.getMessage();
                        Optional.ofNullable(message).ifPresent(integerConfirmMessageConcurrentHashMap -> {
                            integerConfirmMessageConcurrentHashMap.forEach((integer, confirmMessage) -> doIfElse(confirmMessage, sendMqttMessage ->sendMqttMessage.getConfirmStatus()== ConfirmStatus.PUB, sendMqttMessage ->{
                                        clientSessionService.saveSessionMsg(mqttChannel.getDeviceId(), SessionMessage.builder()
                                                .byteBuf(sendMqttMessage.getByteBuf())
                                                .qoS(sendMqttMessage.getQos())
                                                .topic(sendMqttMessage.getTopic())
                                                .build()); // 把待确认数据转入session中
                                    }
                            ));

                        });
                    }
                    else{  // 删除sub topic-消息
                        mqttChannels.remove(deviceId); // 移除channelId  不保持会话 直接删除  保持会话 旧的在重新connect时替换
                        switch (mqttChannel1.getSubStatus()){
                            case YES:
                                deleteSubTopic(mqttChannel1);
                                break;
                        }
                    }
                    if(mqttChannel1.isWill()){     // 发送遗言
                        if(!isDisconnect){ // 不是disconnection操作
                            willService.doSend(deviceId);
                        }
                    }
                });
            });
        }
    }

    /**
     * 清除channel 订阅主题
     * @param mqttChannel
     */
    public Set<String>  deleteSubTopic(MqttChannel mqttChannel){
        Set<String> topics = mqttChannel.getTopic();
        topics.parallelStream().forEach(topic -> cacheMap.delete(getTopic(topic),mqttChannel));
        return topics;
    }

    /**
     * 发送 遗嘱消息(有的channel 已经关闭 但是保持了 session  此时加入session 数据中 )
     * @param willMeaasge 遗嘱消息
     */
    public void sendWillMsg(WillMeaasge willMeaasge){
        Collection<MqttChannel> mqttChannels = getChannels(willMeaasge.getWillTopic(), topic -> cacheMap.getData(getTopic(topic)));
        if(!CollectionUtils.isEmpty(mqttChannels)){
            mqttChannels.forEach(mqttChannel -> {
                switch (mqttChannel.getSessionStatus()){
                    case CLOSE:
                        clientSessionService.saveSessionMsg(mqttChannel.getDeviceId(),
                                SessionMessage.builder()
                                        .topic(willMeaasge.getWillTopic())
                                        .qoS(MqttQoS.valueOf(willMeaasge.getQos()))
                                        .byteBuf(willMeaasge.getWillMessage().getBytes()).build());
                        break;
                    case OPEN:
                        writeWillMsg(mqttChannel,willMeaasge);
                        break;
                }
            });
        }
    }

    /**
     * 保存保留消息
     * @param topic 主题
     * @param retainMessage 信息
     */
    private void saveRetain(String topic, RetainMessage retainMessage, boolean isClean){
        ConcurrentLinkedQueue<RetainMessage> retainMessages = retain.getOrDefault(topic, new ConcurrentLinkedQueue<>());
        if(!retainMessages.isEmpty() && isClean){
            retainMessages.clear();
        }
        boolean flag;
        do{
            flag = retainMessages.add(retainMessage);
        }
        while (!flag);
        retain.put(topic, retainMessages);
    }

    /**
     * 发送保留消息
     */
    public  void sendRetain(String topic,MqttChannel mqttChannel){
        retain.forEach((_topic, retainMessages) -> {
            if(StringUtils.startsWith(_topic,topic)){
                Optional.ofNullable(retainMessages).ifPresent(pubMessages1 -> {
                    retainMessages.parallelStream().forEach(retainMessage -> {
                        log.info("【发送保留消息】"+mqttChannel.getChannel().remoteAddress()+":"+retainMessage.getString()+"【成功】");
                        switch (retainMessage.getQoS()){
                            case AT_MOST_ONCE:
                                sendQos0Msg(mqttChannel.getChannel(),_topic,retainMessage.getByteBuf());
                                break;
                            case AT_LEAST_ONCE:
                                sendQosConfirmMsg(MqttQoS.AT_LEAST_ONCE,mqttChannel,_topic,retainMessage.getByteBuf());
                                break;
                            case EXACTLY_ONCE:
                                sendQosConfirmMsg(MqttQoS.EXACTLY_ONCE,mqttChannel,_topic,retainMessage.getByteBuf());
                                break;
                        }
                    });
                });
            }
        });

    }

}

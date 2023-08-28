package com.github.unclecatmyself.core.thread;

import com.github.unclecatmyself.core.bean.InChatMessage;
import com.github.unclecatmyself.core.constant.Constants;
import com.github.unclecatmyself.core.constant.SessionKey;
import com.github.unclecatmyself.core.utils.DateUtil;
import com.github.unclecatmyself.support.HandlerService;
import com.github.unclecatmyself.support.MessageFactory;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author noseparte
 * @implSpec 消息线程
 * @since 2023/8/26 - 11:52
 * @version 1.0
 */
public class MessageTask implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(MessageTask.class);

    private HandlerService handlerService;
    private final InChatMessage message;
    private final Channel channel;

    public MessageTask(InChatMessage message, Channel channel) {
        this.message = message;
        this.channel = channel;
    }

    public MessageTask(HandlerService handlerService, InChatMessage message, Channel channel) {
        this.handlerService = handlerService;
        this.message = message;
        this.channel = channel;
    }

    @Override
    public void run() {
        doTask();
    }


    /**
     * 改用线程池，异步调用
     */
    private void doTask() {
        int now = DateUtil.getSecond();
        message.setTime(now);
        channel.attr(SessionKey.CHANNEL_CREATE_TIME).set(now);
        Method method = MessageFactory.getInstance().getMethod(message.getType());
        if (null == method) {
            logger.warn("message#{} unregistered!", message.getType());
            return;
        }
        if(!Constants.LOGIN.equals(message.getType())) {
            handlerService.verify(channel, message);
        }
        //设置token
        channel.attr(SessionKey.TOKEN).set(message.getToken());
        try {
            method.invoke(handlerService, channel, message);
        } catch (Exception e) {
            logger.error("readTextMessage invoke method error, {}", e.getMessage(), e);
        }
    }
}

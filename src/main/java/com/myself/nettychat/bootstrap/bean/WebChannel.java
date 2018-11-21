package com.myself.nettychat.bootstrap.bean;

import com.myself.nettychat.common.enums.SessionStatus;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.swagger.models.auth.In;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 聊天消息channel 封装类
 * Created by MySelf on 2018/11/21.
 */
@Data
@Builder
public class WebChannel {

    private transient volatile Channel channel;

    private String msgId;

    private boolean isWill;

    private volatile SessionStatus sessionStatus; //在线 - 离线

    private volatile boolean cleanSession; //当为 true 时channel close 时 从缓存中删除 此channel

    private ConcurrentHashMap<Integer,SendWebMessage> message;// messageId - message(toid) //待确认消息

    private Set<Integer> receive;

    /**
     * 判断当前channel是否登录过
     * @return
     */
    public boolean isLogin(){
        return Optional.ofNullable(this.channel).map(channel1 -> {
            AttributeKey<Boolean> _login = AttributeKey.valueOf("login");
            return channel1.isActive() && channel1.hasAttr(_login);
        }).orElse(false);
    }



}

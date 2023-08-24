package com.github.unclecatmyself.support;

import com.github.unclecatmyself.bootstrap.handler.Handler;
import com.github.unclecatmyself.core.bean.InChatMessage;
import com.github.unclecatmyself.core.bean.vo.SendServerVO;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpMessage;

import java.util.Map;

/**
 * 业务层伪接口
 * Created by MySelf on 2018/11/21.
 */
public abstract class HandlerService implements Handler {

    /**
     * HTTP获取在线用户标签列表
     * @param channel {@link Channel} 链接实例
     */
    public abstract void getList(Channel channel);

    /**
     * HTTP获取在线用户数
     * @param channel {@link Channel} 链接实例
     */
    public abstract void getSize(Channel channel);

    /**
     * HTTP获取用户状态
     * @param channel {@link Channel} 链接实例
     * @param sendServerVO {@link SendServerVO} 用户标识
     */
    public abstract void getState(Channel channel,SendServerVO sendServerVO);

    /**
     * HTTP以服务端向指定用户发送通知
     * @param channel {@link Channel} 链接实例
     * @param sendServerVO {@link SendServerVO} 用户标识
     */
    public abstract void sendFromServer(Channel channel,SendServerVO sendServerVO);

    /**
     * HTTP以服务端处理发送
     * @param channel
     */
    public abstract void sendInChat(Channel channel, FullHttpMessage msg);

    /**
     * 用户未找到匹配Uri
     * @param channel {@link Channel} 链接实例
     */
    public abstract void notFindUri(Channel channel);

    /**
     * 登录类型
     * @param channel {@link Channel} 链接实例
     * @param message {@link InChatMessage} 数据信息
     * @return {@link Boolean} 成功失败
     */
    public abstract boolean login(Channel channel, InChatMessage message);

    /**
     * 发送给自己
     * @param channel {@link Channel} 链接实例
     * @param message {@link InChatMessage} 数据信息
     */
    public abstract void sendMeText(Channel channel, InChatMessage message);

    /**
     * 发送给某人
     * @param channel {@link Channel} 链接实例
     * @param message {@link InChatMessage} 数据信息
     */
    public abstract void sendToText(Channel channel, InChatMessage message);

    /**
     * 发送给群聊
     * @param channel {@link Channel} 链接实例
     * @param message {@link InChatMessage} 数据信息
     */
    public abstract void sendGroupText(Channel channel, InChatMessage message);

    /**
     * 登录校验
     * @param channel {@link Channel} 链接实例
     * @param message {@link InChatMessage} 数据信息
     */
    public abstract void verify(Channel channel, InChatMessage message);

    /**
     * 发送图片给个人
     * @param channel {@link Channel} 链接实例
     * @param message {@link InChatMessage} 数据信息
     */
    public abstract void sendPhotoToMe(Channel channel, InChatMessage message);
}

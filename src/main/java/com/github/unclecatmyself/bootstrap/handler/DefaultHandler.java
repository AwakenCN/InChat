package com.github.unclecatmyself.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import com.github.unclecatmyself.common.base.Handler;
import com.github.unclecatmyself.common.base.HandlerApi;
import com.github.unclecatmyself.common.base.HandlerService;
import com.github.unclecatmyself.common.exception.NoFindHandlerException;
import com.github.unclecatmyself.common.utils.ConstansUtil;
import com.github.unclecatmyself.common.utils.HttpConstantUtil;
import com.github.unclecatmyself.common.utils.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
@ChannelHandler.Sharable
public class DefaultHandler extends Handler {

    private final Logger log = LoggerFactory.getLogger(DefaultHandler.class);

    private final HandlerApi handlerApi;

    public DefaultHandler(HandlerApi handlerApi) {
        super(handlerApi);
        this.handlerApi = handlerApi;
    }

    @Override
    protected void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //暂未实现
        log.info("[webdoMessage]--暂未实现");
    }

    @Override
    protected void httpdoMessage(ChannelHandlerContext ctx, FullHttpRequest msg) {
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handlerApi instanceof HandlerService){
            httpHandlerService = (HandlerService)handlerApi;
        }else {
            throw new NoFindHandlerException("Server Handler 不匹配");
        }
        switch (HttpUtil.checkType(msg)){
            case HttpConstantUtil.GETSIZE:
                log.info("[DefaultWebSocketHandler.httpdoMessage.GETSIZE]");
                httpHandlerService.getSize(channel);
                break;
            case HttpConstantUtil.SENDFROMSERVER:
                log.info("[DefaultWebSocketHandler.httpdoMessage.SENDFROMSERVER]");
                String token = HttpUtil.getToken(msg);
                httpHandlerService.sendFromServer(null,null);
                break;
            case HttpConstantUtil.NOTFINDURI:
                log.info("[DefaultWebSocketHandler.httpdoMessage.NOTFINDURI]");
                httpHandlerService.notFindUri(channel);
                break;
            case HttpConstantUtil.GETLIST:
                log.info("[DefaultWebSocketHandler.httpdoMessage.GETLIST]");
                httpHandlerService.getList(channel);
            default:
                break;
        }
    }

    @Override
    protected void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel channel = ctx.channel();
        HandlerService handlerService;
        if (handlerApi instanceof HandlerService){
            handlerService = (HandlerService)handlerApi;
        }else{
            throw new NoFindHandlerException("Server Handler 不匹配");
        }
        Map<String,Object> maps = (Map) JSON.parse(msg.text());
        maps.put("time", new Date());
        switch ((String)maps.get(ConstansUtil.TYPE)){
            case ConstansUtil.LOGIN:
                log.info("[DefaultWebSocketHandler.textdoMessage.LOGIN]");
                handlerService.login(channel,maps);
                break;
            //针对个人，发送给自己
            case ConstansUtil.SENDME:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDME]");
                handlerService.verify(channel,maps);
                handlerService.sendMeText(channel,maps);
                break;
            //针对个人，发送给某人
            case ConstansUtil.SENDTO:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDTO]");
                handlerService.verify(channel,maps);
                handlerService.sendToText(channel,maps);
                break;
            //发送给群组
            case ConstansUtil.SENDGROUP:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDGROUP]");
                handlerService.verify(channel,maps);
                handlerService.sendGroupText(channel,maps);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("[DefaultWebSocketHandler.channelActive]"+ctx.channel().remoteAddress().toString()+"链接成功");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        log.error("exception",cause);
        log.info("[DefaultWebSocketHandler.exceptionCaught]"+ctx.channel().remoteAddress().toString()+"异常断开");
        ctx.close();
    }
}

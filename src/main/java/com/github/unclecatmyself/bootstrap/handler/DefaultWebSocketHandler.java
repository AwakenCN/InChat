package com.github.unclecatmyself.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import com.github.unclecatmyself.common.base.Handler;
import com.github.unclecatmyself.common.base.HandlerApi;
import com.github.unclecatmyself.common.base.WebSocketHandlerService;
import com.github.unclecatmyself.common.exception.NoFindHandlerException;
import com.github.unclecatmyself.common.utils.ConstansUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
@ChannelHandler.Sharable
public class DefaultWebSocketHandler extends Handler {

    private final Logger log = LoggerFactory.getLogger(DefaultWebSocketHandler.class);

    private final HandlerApi handlerApi;

    public DefaultWebSocketHandler(HandlerApi handlerApi) {
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
        //暂未实现
        log.info("[httpdoMessage]--暂未实现");
        msg.retain();
        log.info(msg.uri());
        log.info(msg.toString());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        response.headers().set("Content-Type","text/html;charset=UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("【InChat】-HTTP通道返回成功",CharsetUtil.UTF_8);
        response.content().writeBytes(buf);
        ctx.writeAndFlush(response);
        ctx.close();
    }

    @Override
    protected void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel channel = ctx.channel();
        WebSocketHandlerService webSocketHandlerService;
        if (handlerApi instanceof WebSocketHandlerService){
            webSocketHandlerService = (WebSocketHandlerService)handlerApi;
        }else{
            throw new NoFindHandlerException("Server Handler 不匹配");
        }
        Map<String,Object> maps = (Map) JSON.parse(msg.text());
        maps.put("time", new Date());
        switch ((String)maps.get(ConstansUtil.TYPE)){
            case ConstansUtil.LOGIN:
                log.info("[DefaultWebSocketHandler.textdoMessage.LOGIN]");
                webSocketHandlerService.login(channel,maps);
                break;
            //针对个人，发送给自己
            case ConstansUtil.SENDME:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDME]");
                webSocketHandlerService.verify(channel,maps);
                webSocketHandlerService.sendMeText(channel,maps);
                break;
            //针对个人，发送给某人
            case ConstansUtil.SENDTO:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDTO]");
                webSocketHandlerService.verify(channel,maps);
                webSocketHandlerService.sendToText(channel,maps);
                break;
            //发送给群组
            case ConstansUtil.SENDGROUP:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDGROUP]");
                webSocketHandlerService.verify(channel,maps);
                webSocketHandlerService.sendGroupText(channel,maps);
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
        handlerApi.close(ctx.channel());
    }
}

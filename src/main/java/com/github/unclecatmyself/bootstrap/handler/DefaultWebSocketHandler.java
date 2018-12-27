package com.github.unclecatmyself.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import com.github.unclecatmyself.common.exception.NoFindHandlerException;
import com.github.unclecatmyself.common.websockets.ServerWebSocketHandlerService;
import com.github.unclecatmyself.common.websockets.WebSocketHandler;
import com.github.unclecatmyself.common.utils.ConstansUtil;
import com.github.unclecatmyself.common.websockets.WebSocketHandlerApi;
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
public class DefaultWebSocketHandler extends WebSocketHandler {

    private final Logger log = LoggerFactory.getLogger(DefaultWebSocketHandler.class);

    private final WebSocketHandlerApi webSocketHandlerApi;

    public DefaultWebSocketHandler(WebSocketHandlerApi webSocketHandlerApi) {
        super(webSocketHandlerApi);
        this.webSocketHandlerApi = webSocketHandlerApi;
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
        ServerWebSocketHandlerService serverWebSocketHandlerService;
        if (webSocketHandlerApi instanceof ServerWebSocketHandlerService){
            serverWebSocketHandlerService = (ServerWebSocketHandlerService)webSocketHandlerApi;
        }else{
            throw new NoFindHandlerException("Server Handler 不匹配");
        }
        Map<String,Object> maps = (Map) JSON.parse(msg.text());
        maps.put("time", new Date());
        switch ((String)maps.get(ConstansUtil.TYPE)){
            case ConstansUtil.LOGIN:
                log.info("[DefaultWebSocketHandler.textdoMessage.LOGIN]");
                serverWebSocketHandlerService.login(channel,maps);
                break;
            //针对个人，发送给自己
            case ConstansUtil.SENDME:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDME]");
                serverWebSocketHandlerService.verify(channel,maps);
                serverWebSocketHandlerService.sendMeText(channel,maps);
                break;
            //针对个人，发送给某人
            case ConstansUtil.SENDTO:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDTO]");
                serverWebSocketHandlerService.verify(channel,maps);
                serverWebSocketHandlerService.sendToText(channel,maps);
                break;
            //发送给群组
            case ConstansUtil.SENDGROUP:
                log.info("[DefaultWebSocketHandler.textdoMessage.SENDGROUP]");
                serverWebSocketHandlerService.verify(channel,maps);
                serverWebSocketHandlerService.sendGroupText(channel,maps);
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
        webSocketHandlerApi.close(ctx.channel());
    }
}

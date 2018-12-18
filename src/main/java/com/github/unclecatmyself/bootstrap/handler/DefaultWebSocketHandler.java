package com.github.unclecatmyself.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import com.github.unclecatmyself.common.exception.NoFindHandlerException;
import com.github.unclecatmyself.common.utils.TimeUtil;
import com.github.unclecatmyself.common.websockets.ServerWebSocketHandlerService;
import com.github.unclecatmyself.common.websockets.WebSocketHandler;
import com.github.unclecatmyself.common.utils.ConstansUtil;
import com.github.unclecatmyself.common.websockets.WebSocketHandlerApi;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
@Slf4j
@ChannelHandler.Sharable
public class DefaultWebSocketHandler extends WebSocketHandler {

    private final WebSocketHandlerApi webSocketHandlerApi;

    public DefaultWebSocketHandler(WebSocketHandlerApi webSocketHandlerApi) {
        super(webSocketHandlerApi);
        this.webSocketHandlerApi = webSocketHandlerApi;
    }

    @Override
    protected void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        //暂未实现
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
        maps.put("time", TimeUtil.getTime());
        switch ((String)maps.get(ConstansUtil.TYPE)){
            case ConstansUtil.LOGIN:
                log.info("【登录校验】");
                serverWebSocketHandlerService.login(channel,maps);
                break;
            //针对个人，发送给自己
            case ConstansUtil.SENDME:
                log.info("【发送给自己】");
                serverWebSocketHandlerService.verify(channel,maps);
                serverWebSocketHandlerService.sendMeText(channel,maps);
                break;
            //针对个人，发送给某人
            case ConstansUtil.SENDTO:
                log.info("【发送给某人】");
                serverWebSocketHandlerService.verify(channel,maps);
                serverWebSocketHandlerService.sendToText(channel,maps);
                break;
            //发送给群组
            case ConstansUtil.SENDGROUP:
                log.info("发送到群组");
                serverWebSocketHandlerService.verify(channel,maps);
                serverWebSocketHandlerService.sendGroupText(channel,maps);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("【DefaultWebSocketHandler：channelActive】"+ctx.channel().remoteAddress().toString()+"链接成功");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
        log.error("exception",cause);
        webSocketHandlerApi.close(ctx.channel());
    }
}

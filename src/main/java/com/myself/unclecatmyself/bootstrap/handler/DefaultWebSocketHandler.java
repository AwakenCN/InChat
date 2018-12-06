package com.myself.unclecatmyself.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import com.myself.unclecatmyself.common.exception.NoFindHandlerException;
import com.myself.unclecatmyself.common.utils.ConstansUtil;
import com.myself.unclecatmyself.common.websockets.ServerWebSocketHandlerService;
import com.myself.unclecatmyself.common.websockets.WebSocketHandler;
import com.myself.unclecatmyself.common.websockets.WebSocketHandlerApi;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
@Component
@ChannelHandler.Sharable
public class DefaultWebSocketHandler extends WebSocketHandler {

    private final WebSocketHandlerApi webSocketHandlerApi;

    public DefaultWebSocketHandler(WebSocketHandlerApi webSocketHandlerApi) {
        super(webSocketHandlerApi);
        this.webSocketHandlerApi = webSocketHandlerApi;
    }

    @Override
    protected void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {

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
        switch ((String)maps.get(ConstansUtil.TYPE)){
            case ConstansUtil.LOGIN:
                serverWebSocketHandlerService.login(channel,maps);
                break;
            //针对个人，发送给自己
            case ConstansUtil.SENDME:
                serverWebSocketHandlerService.sendMeText(channel,maps);
                break;
            //针对个人，发送给某人
            case ConstansUtil.SENDTO:
                serverWebSocketHandlerService.sendToText(channel,maps);
                break;
            //发送给群组
            case ConstansUtil.SENDGROUP:
                serverWebSocketHandlerService.sendGroupText(channel,maps);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.info("【DefaultWebSocketHandler：channelActive】"+ctx.channel().remoteAddress().toString()+"链接成功");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
//        log.error("exception",cause);
        webSocketHandlerApi.close(ctx.channel());
    }
}

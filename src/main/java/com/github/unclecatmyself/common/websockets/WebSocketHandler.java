package com.github.unclecatmyself.common.websockets;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * Create by UncleCatMySelf in 2018/12/06
 */
public abstract class WebSocketHandler extends SimpleChannelInboundHandler<Object> {

    WebSocketHandlerApi webSocketHandlerApi;

    public WebSocketHandler(WebSocketHandlerApi webSocketHandlerApi){
        this.webSocketHandlerApi = webSocketHandlerApi;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TextWebSocketFrame){
            textdoMessage(ctx,(TextWebSocketFrame)msg);
        }else if (msg instanceof WebSocketFrame){
            webdoMessage(ctx,(WebSocketFrame)msg);
        }
    }

    protected abstract void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg);

    protected abstract void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        log.info("【DefaultWebSocketHandler：channelInactive】"+ctx.channel().localAddress().toString()+"关闭成功");
        webSocketHandlerApi.close(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if(evt instanceof IdleStateEvent){
//            webSocketHandlerApi.doTimeOut(ctx.channel(),(IdleStateEvent)evt);
//        }
        super.userEventTriggered(ctx, evt);
    }
}

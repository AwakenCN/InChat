package com.github.unclecatmyself.common.base;

import com.github.unclecatmyself.common.constant.LogConstant;
import com.github.unclecatmyself.common.exception.NotFindLoginChannlException;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty实现初始层
 * Create by UncleCatMySelf in 2018/12/06
 */
public abstract class Handler extends SimpleChannelInboundHandler<Object> {

    private static final Logger log = LoggerFactory.getLogger(Handler.class);

    HandlerApi handlerApi;

    public Handler(HandlerApi handlerApi){
        this.handlerApi = handlerApi;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TextWebSocketFrame){
            System.out.println("TextWebSocketFrame"+msg);
            textdoMessage(ctx,(TextWebSocketFrame)msg);
        }else if (msg instanceof WebSocketFrame){
            System.out.println("WebSocketFrame"+msg);
            webdoMessage(ctx,(WebSocketFrame)msg);
        }else if (msg instanceof FullHttpRequest){
            System.out.println("FullHttpRequest"+msg);
            httpdoMessage(ctx,(FullHttpRequest)msg);
        }
    }

    protected abstract void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg);

    protected abstract void textdoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    protected abstract void httpdoMessage(ChannelHandlerContext ctx, FullHttpRequest msg);

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(LogConstant.CHANNELINACTIVE+ctx.channel().localAddress().toString()+LogConstant.CLOSE_SUCCESS);
        try {
            handlerApi.close(ctx.channel());
        }catch (NotFindLoginChannlException e){
            log.error(LogConstant.NOTFINDLOGINCHANNLEXCEPTION);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//        if(evt instanceof IdleStateEvent){
//            webSocketHandlerApi.doTimeOut(ctx.channel(),(IdleStateEvent)evt);
//        }
        super.userEventTriggered(ctx, evt);
    }
}

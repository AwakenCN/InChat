package com.myself.nettychat.bootstrap.handler;

import com.myself.nettychat.common.exception.NoFindHandlerException;
import com.myself.nettychat.common.websockets.ServerWebSocketHandlerService;
import com.myself.nettychat.common.websockets.WebSocketHandler;
import com.myself.nettychat.common.websockets.WebSocketHandlerApi;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:15 2018\11\16 0016
 */
@Slf4j
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
        System.out.println("textdoMessage--"+msg.text());
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

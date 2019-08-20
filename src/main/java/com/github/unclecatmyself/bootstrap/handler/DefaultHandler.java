package com.github.unclecatmyself.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import com.github.unclecatmyself.common.base.Handler;
import com.github.unclecatmyself.common.base.HandlerApi;
import com.github.unclecatmyself.common.base.HandlerService;
import com.github.unclecatmyself.common.bean.vo.SendServerVO;
import com.github.unclecatmyself.common.constant.Constans;
import com.github.unclecatmyself.common.constant.HttpConstant;
import com.github.unclecatmyself.common.constant.LogConstant;
import com.github.unclecatmyself.common.constant.NotInChatConstant;
import com.github.unclecatmyself.common.exception.NoFindHandlerException;
import com.github.unclecatmyself.common.utils.HttpUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.UnsupportedEncodingException;
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
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handlerApi instanceof HandlerService){
            httpHandlerService = (HandlerService)handlerApi;
        }else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        if (msg instanceof BinaryWebSocketFrame){
            //TODO 实现图片处理
        }
    }

    @Override
    protected void httpdoMessage(ChannelHandlerContext ctx, FullHttpRequest msg) {
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handlerApi instanceof HandlerService){
            httpHandlerService = (HandlerService)handlerApi;
        }else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        switch (HttpUtil.checkType(msg)){
            case HttpConstant.GETSIZE:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETSIZE);
                httpHandlerService.getSize(channel);
                break;
            case HttpConstant.SENDFROMSERVER:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDFROMSERVER);
                SendServerVO serverVO = null;
                try {
                    serverVO = HttpUtil.getToken(msg);
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
                httpHandlerService.sendFromServer(channel,serverVO);
                break;
            case HttpConstant.GETLIST:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETLIST);
                httpHandlerService.getList(channel);
                break;
            case HttpConstant.SENDINCHAT:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDINCHAT);
                httpHandlerService.sendInChat(channel,msg);
                break;
            case HttpConstant.NOTFINDURI:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_NOTFINDURI);
                httpHandlerService.notFindUri(channel);
                break;
            default:
                System.out.println("未匹配"+msg);
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
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }
        Map<String,Object> maps = (Map) JSON.parse(msg.text());
        maps.put(Constans.TIME, new Date());
        switch ((String)maps.get(Constans.TYPE)){
            case Constans.LOGIN:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_LOGIN);
                handlerService.login(channel,maps);
                break;
            //针对个人，发送给自己
            case Constans.SENDME:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDME);
                handlerService.verify(channel,maps);
                handlerService.sendMeText(channel,maps);
                break;
            //针对个人，发送给某人
            case Constans.SENDTO:
                log.info(LogConstant.DefaultWebSocketHandler_SENDTO);
                handlerService.verify(channel,maps);
                handlerService.sendToText(channel,maps);
                break;
            //发送给群组
            case Constans.SENDGROUP:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDGROUP);
                handlerService.verify(channel,maps);
                handlerService.sendGroupText(channel,maps);
                break;
            //发送图片，发送给自己
            case Constans.SENDPHOTOTOME:
                log.info("图片到个人");
                handlerService.verify(channel,maps);
                handlerService.sendPhotoToMe(channel,maps);
                break;
            default:
                break;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(LogConstant.CHANNELACTIVE+ctx.channel().remoteAddress().toString()+LogConstant.CHANNEL_SUCCESS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception{
//        log.error("exception",cause);
        log.info(LogConstant.EXCEPTIONCAUGHT+ctx.channel().remoteAddress().toString()+LogConstant.DISCONNECT);
        ctx.close();
    }
}

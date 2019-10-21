package com.github.unclecatmyself.bootstrap.handler;

import com.alibaba.fastjson.JSON;
import com.github.unclecatmyself.support.HandlerService;
import com.github.unclecatmyself.core.bean.vo.SendServerVO;
import com.github.unclecatmyself.core.constant.Constants;
import com.github.unclecatmyself.core.constant.HttpConstant;
import com.github.unclecatmyself.core.constant.LogConstant;
import com.github.unclecatmyself.core.constant.UndefinedInChatConstant;
import com.github.unclecatmyself.core.exception.HandlerNotFoundException;
import com.github.unclecatmyself.core.utils.HttpUtil;
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
public class DefaultAbstractHandler extends AbstractHandler {

    private final Logger log = LoggerFactory.getLogger(DefaultAbstractHandler.class);

    private final Handler handler;

    public DefaultAbstractHandler(Handler handler) {
        super(handler);
        this.handler = handler;
    }

    @Override
    protected void webdoMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handler instanceof HandlerService){
            httpHandlerService = (HandlerService) handler;
        }else {
            throw new HandlerNotFoundException(UndefinedInChatConstant.NOT_HANDLER);
        }
        if (msg instanceof BinaryWebSocketFrame){
            //TODO 实现图片处理
        }
    }

    @Override
    protected void httpdoMessage(ChannelHandlerContext ctx, FullHttpRequest msg) {
        Channel channel = ctx.channel();
        HandlerService httpHandlerService;
        if (handler instanceof HandlerService){
            httpHandlerService = (HandlerService) handler;
        }else {
            throw new HandlerNotFoundException(UndefinedInChatConstant.NOT_HANDLER);
        }
        switch (HttpUtil.checkType(msg)){
            /** 获取在线用户数 */
            case HttpConstant.GET_SIZE:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETSIZE);
                httpHandlerService.getSize(channel);
                break;
            /** 以服务端形式发送出去 */
            case HttpConstant.SEND_FROM_SERVER:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDFROMSERVER);
                SendServerVO serverVO = null;
                try {
                    serverVO = HttpUtil.getToken(msg);
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
                httpHandlerService.sendFromServer(channel,serverVO);
                break;
            /** 获取在线用户列表 */
            case HttpConstant.GET_LIST:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETLIST);
                httpHandlerService.getList(channel);
                break;
            /** 获取用户在线状态 */
            case HttpConstant.GET_STATE:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETSTATE);
                SendServerVO token = null;
                try {
                    token = HttpUtil.getToken(msg);
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                }
                httpHandlerService.getState(channel,token);
                break;
            /** 分布式通讯转接 */
            case HttpConstant.SEND_IN_CHAT:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDINCHAT);
                httpHandlerService.sendInChat(channel,msg);
                break;
            /** 未匹配到uri */
            case HttpConstant.NOT_FIND_URI:
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
        if (handler instanceof HandlerService){
            handlerService = (HandlerService) handler;
        }else{
            throw new HandlerNotFoundException(UndefinedInChatConstant.NOT_HANDLER);
        }
        System.out.println(msg.text());
        Map<String,Object> maps = (Map) JSON.parse(msg.text());
        maps.put(Constants.TIME, new Date());
        switch ((String)maps.get(Constants.TYPE)){
            case Constants.LOGIN:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_LOGIN);
                handlerService.login(channel,maps);
                break;
            case "jmeter":
                //Jmeter高并发测试
                break;
            //针对个人，发送给自己
            case Constants.SEND_ME:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDME);
                handlerService.verify(channel,maps);
                handlerService.sendMeText(channel,maps);
                break;
            //针对个人，发送给某人
            case Constants.SEND_TO:
                log.info(LogConstant.DefaultWebSocketHandler_SENDTO);
                handlerService.verify(channel,maps);
                handlerService.sendToText(channel,maps);
                break;
            //发送给群组
            case Constants.SEND_GROUP:
                log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDGROUP);
                handlerService.verify(channel,maps);
                handlerService.sendGroupText(channel,maps);
                break;
            //发送图片，发送给自己
            case Constants.SEND_PHOTO_TO_ME:
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

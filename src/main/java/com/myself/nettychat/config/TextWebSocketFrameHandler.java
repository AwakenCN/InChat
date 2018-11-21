package com.myself.nettychat.config;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.myself.nettychat.common.properties.CacheTemplate;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:01 2018\8\14 0014
 */
@Component
@Qualifier("textWebSocketFrameHandler")
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private CacheTemplate cacheTemplate;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame frame) throws Exception {
        System.out.println(frame.text());
        Channel inchannel = ctx.channel();
        String result = frame.text();
        Gson gson = new Gson();
        Map<String,String> maps = (Map<String,String>) JSON.parse(result);
        Map<String,String> backs = new HashMap<String,String>();
        switch (maps.get("type")){
            case "login":
                backs.put("type","login");
                backs.put("success","true");
                cacheTemplate.add(maps.get("name"),inchannel);
                ctx.writeAndFlush(new TextWebSocketFrame(gson.toJson(backs)));
                break;
            case "offer":
                backs.put("type","offer");
                backs.put("offer",maps.get("offer"));
                backs.put("name","bbb");
                Channel other = cacheTemplate.get("bbb");
                other.writeAndFlush(new TextWebSocketFrame(gson.toJson(backs)));
                break;
            case "answer":
                backs.put("type","answer");
                backs.put("answer",maps.get("answer"));
                break;
            case "candidate":
                backs.put("type","candidate");
                backs.put("candidate",maps.get("candidate"));
                ctx.writeAndFlush(new TextWebSocketFrame(gson.toJson(backs)));
                break;
            case "leave":
                backs.put("type","leave");
                break;
            default:
                backs.put("type","error");
                backs.put("message","Command not found:"+maps.get("type"));
                break;
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //在线
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        //异常
        cause.printStackTrace();
        ctx.close();
    }
}

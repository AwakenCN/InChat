package com.myself.nettychat.config;

import com.myself.nettychat.async.MsgAsyncTesk;
import com.myself.nettychat.constont.LikeRedisTemplate;
import com.myself.nettychat.constont.LikeSomeCacheTemplate;
import com.myself.nettychat.utils.RandomNameUtil;
import com.myself.nettychat.utils.StringUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;


/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:01 2018\8\14 0014
 */
@Component
@Qualifier("textWebSocketFrameHandler")
@ChannelHandler.Sharable
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<Object>{

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private LikeRedisTemplate redisTemplate;
    @Autowired
    private LikeSomeCacheTemplate cacheTemplate;
    @Autowired
    private MsgAsyncTesk msgAsyncTesk;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                Object msg) throws Exception {
        if(msg instanceof TextWebSocketFrame){
            textWebSocketFrame(ctx, (TextWebSocketFrame) msg);
        }else if(msg instanceof WebSocketFrame){ //websocket帧类型 已连接
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        if(frame instanceof BinaryWebSocketFrame){
            //返回客户端
            BinaryWebSocketFrame imgBack= (BinaryWebSocketFrame) frame.copy();
            for (Channel channel : channels){
                channel.writeAndFlush(imgBack.retain());
            }
            //保存服务器
            BinaryWebSocketFrame img= (BinaryWebSocketFrame) frame;
            ByteBuf byteBuf=img.content();
            try {
                FileOutputStream outputStream=new FileOutputStream("D:\\a.jpg");
                byteBuf.readBytes(outputStream,byteBuf.capacity());
                byteBuf.clear();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void textWebSocketFrame(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel incoming = ctx.channel();
        String rName = StringUtil.getName(msg.text());
        String rMsg = StringUtil.getMsg(msg.text());
        if (rMsg.equals("")){
            return;
        }
        if (redisTemplate.check(incoming.id(),rName)){
            cacheTemplate.save(rName,rMsg);
            redisTemplate.save(incoming.id(),rName);
        }else{
            incoming.writeAndFlush(new TextWebSocketFrame("存在二次登陆，系统已为你自动断开本次链接"));
            channels.remove(ctx.channel());
            ctx.close();
            return;
        }
        for (Channel channel : channels) {
            //将当前每个聊天内容进行存储
            if (channel != incoming){
                channel.writeAndFlush(new TextWebSocketFrame( "[" + rName + "]" + rMsg));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame(rMsg + "[" + rName + "]" ));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        redisTemplate.delete(ctx.channel().id());
        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //在线
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //掉线
        msgAsyncTesk.saveChatMsgTask();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        //异常
        cause.printStackTrace();
        ctx.close();
    }
}

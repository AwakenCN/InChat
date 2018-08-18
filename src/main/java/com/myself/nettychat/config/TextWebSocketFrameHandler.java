package com.myself.nettychat.config;

import com.myself.nettychat.async.MsgAsyncTesk;
import com.myself.nettychat.constont.LikeRedisTemplate;
import com.myself.nettychat.constont.LikeSomeCacheTemplate;
import com.myself.nettychat.utils.RandomNameUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


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
    private LikeRedisTemplate redisTemplate;
    @Autowired
    private LikeSomeCacheTemplate cacheTemplate;
    @Autowired
    private MsgAsyncTesk msgAsyncTesk;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {
        Channel incoming = ctx.channel();
        String uName = String.valueOf(redisTemplate.get(incoming.id()));
        cacheTemplate.save(uName,msg.text());
        System.out.println("存储数据："+uName+"-"+msg.text());
        for (Channel channel : channels) {
            //将当前每个聊天内容进行存储
            if (channel != incoming){
                channel.writeAndFlush(new TextWebSocketFrame( msg.text() + "[" + uName + "]"));
            } else {
                channel.writeAndFlush(new TextWebSocketFrame("[you]" + msg.text() ));
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        String uName = String.valueOf(RandomNameUtil.getName());  //用来获取一个随机的用户名，可以用其他方式代替

        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[新用户] - " + uName + " 加入"));
        }
        redisTemplate.save(incoming.id(),uName);   //存储用户
        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        String uName = String.valueOf(redisTemplate.get(incoming.id()));
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame("[用户] - " + uName + " 离开"));
        }
        redisTemplate.delete(incoming.id());   //删除用户
        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:"+redisTemplate.get(incoming.id())+"在线");
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:"+redisTemplate.get(incoming.id())+"掉线");
        msgAsyncTesk.saveChatMsgTask();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("用户:" + redisTemplate.get(incoming.id()) + "异常");
        cause.printStackTrace();
        ctx.close();
    }
}

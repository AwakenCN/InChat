package com.github.unclecatmyself.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName DiscardServerHandler
 * @Description TODO
 * @Author MySelf
 * @Date 2019/8/19 20:39
 * @Version 1.0
 **/
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        // discard
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 引发异常时关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}

package com.myself.nettychat.bootstrap.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 转换
 **/
public class WebSocketFrameToByteBufDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame> {


    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame wsFrame, List<Object> out) throws Exception {
        ByteBuf buf = wsFrame.content();
        //避免计数器为0，报错
        buf.retain();
        out.add(buf);
    }
}

package com.myself.nettychat.common.utils;


import io.netty.buffer.ByteBuf;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 跨线程情况下 byteBuf 需要转换成byte[]
 **/
public class ByteBufUtil {

    public  static byte[]  copyByteBuf(ByteBuf byteBuf){
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }

}

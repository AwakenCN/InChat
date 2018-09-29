package com.myself.nettychat.common.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:30 2018\9\20 0020
 */
public class CallBackMessage {

    public static final ByteBuf SUCCESS = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("success", Charset.forName("UTF-8")));

    public static final ByteBuf ERROR = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("error", Charset.forName("UTF-8")));

    public static final ByteBuf Check1_test = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("Check1\r", Charset.forName("UTF-8")));

    public static final ByteBuf Check2 = Unpooled.unreleasableBuffer(
            Unpooled.copiedBuffer("Check2", Charset.forName("UTF-8")));


    public static ByteBuf sendString(String send){
        return Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer(send, Charset.forName("UTF-8")));
    }


}

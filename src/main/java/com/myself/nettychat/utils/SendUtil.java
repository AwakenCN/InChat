package com.myself.nettychat.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.nio.charset.Charset;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:32 2018\9\20 0020
 */
public class SendUtil {

    public boolean send(Integer item, Channel channel, String channelID, String type){
        try {
            if (item != null && channel != null){
                String items = IntegerToString(item);
                String result = CRC16MySelf.getAllString(channelID,type,items);
                System.out.println("send:" + result);
                ByteBuf msg = Unpooled.unreleasableBuffer(
                        Unpooled.copiedBuffer(result, Charset.forName("UTF-8")));
                channel.writeAndFlush(msg.duplicate());
                return true;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
        return false;
    }

    /**
     * 广播发送事件
     * @param items
     * @param channel
     * @param channelID
     * @param type
     * @return
     */
    public void sendAll(String items, Channel channel, String channelID, String type){
        try {
            if (items != null && channel != null){
                String result = CRC16MySelf.getAllString(channelID,type,items);
                ByteBuf msg = Unpooled.unreleasableBuffer(
                        Unpooled.copiedBuffer(result, Charset.forName("UTF-8")));
                channel.writeAndFlush(msg.duplicate());
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public String IntegerToString(Integer item){
        char[] locks_char = Const.LOCKS.toCharArray();
        for (int i=0;i<locks_char.length;i++){
            if (i==item){
                locks_char[i]=Const.OPEN;
            }
        }
        String result = String.valueOf(locks_char) ;
        return result;
    }

}

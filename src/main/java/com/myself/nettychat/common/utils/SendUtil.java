package com.myself.nettychat.common.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

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
     * 消息测试
     * @param channel
     * @param result
     * @return
     */
    public boolean send(Channel channel, String result){
        try {
            if (channel != null){
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
     * 向websocket端发送信息
     * @param channel
     * @param result
     * @return
     */
    public boolean sendWebSocket(Channel channel, String result){
        try {
            if (channel != null){
                System.out.println("send:" + result);
                channel.writeAndFlush(new TextWebSocketFrame(result));
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

    /**
     * 想指定链接发送数据
     * @param msg 消息
     * @param channel 指定链接
     * @return {@link String}
     */
    public static String sendTest(String msg,Channel channel) {
        try {
            channel.writeAndFlush(new TextWebSocketFrame( "[系统API]" + msg));
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
}

package com.myself.nettychat.config;

import com.myself.nettychat.common.utils.*;
import com.myself.nettychat.dto.CacheDTO;
import com.myself.nettychat.template.CacheTemplate;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:29 2018\9\20 0020
 */
@Component
@Qualifier("tcpServerHandler")
@ChannelHandler.Sharable
public class TCPServerHandler extends ChannelInboundHandlerAdapter {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Autowired
    private CacheTemplate cacheTemplate;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String ChannelID = null;
        try {
            String data = (String)msg;
            //切换真实ID
            String realChannelID = Const.isChannel(ctx.channel());
            ChannelID = data.substring(0,4);
            String type = data.substring(4,5);
            System.out.println(ChannelID);
            Const.ChangeClientId(realChannelID,ChannelID);
            if (type.equals("c")){
                String tip = data.substring(data.length()-1,data.length());
                System.out.println("tip="+tip);
                if (cacheTemplate.hasCacheDTO(ChannelID)){
                    System.out.println("true");
                    List<CacheDTO> cacheDTOList = cacheTemplate.getDTO(ChannelID);
                    System.out.println(cacheDTOList.size());
                    for (CacheDTO item: cacheDTOList){
                        if (tip.equals(item.getMsg())){
                            SendUtil sendUtil = new SendUtil();
                            sendUtil.sendWebSocket((Channel) cacheTemplate.getChannel(item.getToken()),"开锁成功！");
                            System.out.println("通知成功");
                        }
                    }
                }
            }
            ctx.writeAndFlush(CallBackMessage.Check1_test.duplicate());
        }finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
        System.out.println(cause.getMessage());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Const.add(String.valueOf(UUID.randomUUID()),ctx.channel());
        channels.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Disconnected client " + ctx.channel().remoteAddress());
        Const.remove(ctx.channel());
    }
}


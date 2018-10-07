package com.myself.nettychat.controller;

import com.myself.nettychat.common.utils.ResultVOUtil;
import com.myself.nettychat.common.utils.SendUtil;
import com.myself.nettychat.constont.LikeRedisTemplate;
import com.myself.nettychat.vo.ResultVo;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 20:55 2018\10\7 0007
 */
@RestController
@RequestMapping("/back")
public class NCBackController {

    @Autowired
    private LikeRedisTemplate redisTemplate;

    /**
     * 获取在线用户数
     * @return {@link ResultVo}
     */
    @GetMapping("/size")
    public ResultVo getSize(){
        return ResultVOUtil.success(redisTemplate.getSize());
    }

    /**
     * 获取在线用户列表
     * @return {@link ResultVo}
     */
    @GetMapping("/online")
    public ResultVo getOnline(){
        return ResultVOUtil.success(redisTemplate.getOnline());
    }

    /**
     * API调用向在线用户发送消息
     * @param name 用户名
     * @param msg 消息
     * @return {@link ResultVo}
     */
    @PostMapping("/send")
    public ResultVo send(@RequestParam String name,@RequestParam String msg){
        Channel channel = (Channel) redisTemplate.getChannel(name);
        if (channel == null){
            return ResultVOUtil.error(555,"当前用户连接已断开");
        }
        String result = SendUtil.sendTest(msg,channel);
        return ResultVOUtil.success(result);
    }

}

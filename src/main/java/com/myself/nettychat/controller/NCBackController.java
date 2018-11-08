package com.myself.nettychat.controller;

import com.myself.nettychat.common.utils.Const;
import com.myself.nettychat.common.utils.ResultVOUtil;
import com.myself.nettychat.common.utils.SendUtil;
import com.myself.nettychat.dto.CacheDTO;
import com.myself.nettychat.template.CacheTemplate;
import com.myself.nettychat.vo.ResultVo;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    private CacheTemplate cacheTemplate;

//    /**
//     * 获取当前连接数
//     * @return
//     */
//    @GetMapping(value = "/get_channel_size")
//    public ResultVo getChannelSize(){
//        return ResultVOUtil.success(Const.getSzie());
//    }
//
//    /**
//     * 获取连接Id数组
//     * @return
//     */
//    @GetMapping(value = "/get_channel_id_list")
//    public ResultVo getChannelIDList(){
//        return ResultVOUtil.success(Const.getIdList());
//    }

//    /**
//     * 向存在链接发送指定的端口
//     * @param channelId 连接Id
//     * @param lock 打开第几把锁
//     * @return
//     */
//    @PostMapping(value = "/send_to_channel")
//    public ResultVo SendToChannel(String channelId,Integer lock){
//        SendUtil sendUtil = new SendUtil();
//        Channel channel = Const.get(channelId);
//        if (channel != null){
//            if (sendUtil.send(lock,channel,channelId,Const.CONTROL_TYPE)){
//                return ResultVOUtil.success("【发送成功】");
//            }
//        }
//        return ResultVOUtil.error(888,"【发送失败】");
//    }

    /**
     * 单业务模拟-开锁
     * @param channelId 柜子ID--与tcp通信ID一致
     * @param msg 打开第几把锁
     * @param token 用户标识
     * @return
     */
    @PostMapping(value = "/send_test")
    public ResultVo SendTest(String channelId,String msg,String token){
        SendUtil sendUtil = new SendUtil();
        Channel channel = Const.get(channelId);
        if (channel != null){
            if (sendUtil.send(channel,msg)){
                String tip = msg.substring(msg.length()-1,msg.length());
                System.out.println(tip);
                CacheDTO cacheDTO = new CacheDTO(token,tip);
                List<CacheDTO> cacheDTOList = new ArrayList<>();
                cacheDTOList.add(cacheDTO);
                //存储等待二次通知判断
                cacheTemplate.saveDTO(channelId,cacheDTOList);
                return ResultVOUtil.success("【发送成功】");
            }
        }
        return ResultVOUtil.error(888,"【发送失败】");
    }

}

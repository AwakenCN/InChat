package com.myself.unclecatmyself.bootstrap.channel;

import com.myself.unclecatmyself.bootstrap.BaseApi;
import com.myself.unclecatmyself.bootstrap.ChannelService;
import com.myself.unclecatmyself.bootstrap.bean.WillMeaasge;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
//@Component
@Data
@NoArgsConstructor
public class WillService implements BaseApi {

    @Autowired
    ChannelService channelService;

    private static ConcurrentHashMap<String, WillMeaasge> willMeaasges = new ConcurrentHashMap<>(); // deviceid -WillMeaasge



    /**
     * 保存遗嘱消息
     */
    public void save(String deviceid, WillMeaasge build) {
        willMeaasges.put(deviceid,build); // 替换旧的
    }


    public void doSend( String deviceId) {  // 客户端断开连接后 开启遗嘱消息发送
        if(StringUtils.isNotBlank(deviceId)&&(willMeaasges.get(deviceId))!=null){
            WillMeaasge willMeaasge = willMeaasges.get(deviceId);
            channelService.sendWillMsg(willMeaasge); // 发送遗嘱消息
            if(!willMeaasge.isRetain()){ // 移除
                willMeaasges.remove(deviceId);
                log.info("deviceId will message["+willMeaasge.getWillMessage()+"] is removed");
            }
        }
    }

    /**
     * 删除遗嘱消息
     */
    public void del(String deviceid ) {willMeaasges.remove(deviceid);}

}

package com.github.unclecatmyself.common.utils;

import com.github.unclecatmyself.common.bean.InChatMessage;

import java.util.Date;
import java.util.Map;

/**
 * Created by MySelf on 2018/12/19.
 */
public class MessageChangeUtil {

    public static InChatMessage Change(Map<String,Object> maps){
        InChatMessage message = new InChatMessage();
        if (maps.containsKey(ConstansUtil.TOKEN)){
            message.setToken((String) maps.get(ConstansUtil.TOKEN));
        }
        if (maps.containsKey(ConstansUtil.TIME)){
            message.setTime((Date) maps.get(ConstansUtil.TIME));
        }
        if (maps.containsKey(ConstansUtil.VALUE)){
            message.setValue((String)maps.get(ConstansUtil.VALUE));
        }
        if (maps.containsKey(ConstansUtil.TYPE)){
            message.setType((String)maps.get(ConstansUtil.TYPE));
        }
        if (maps.containsKey(ConstansUtil.ONE)){
            message.setOne((String)maps.get(ConstansUtil.ONE));
        }
        if (maps.containsKey(ConstansUtil.GROUPID)){
            message.setGroudId((String)maps.get(ConstansUtil.GROUPID));
        }
        if (maps.containsKey(ConstansUtil.ON_ONLINE)){
            message.setOnline((String)maps.get(ConstansUtil.ON_ONLINE));
        }
        return message;
    }

}

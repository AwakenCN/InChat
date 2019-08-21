package com.github.unclecatmyself.common.utils;

import com.github.unclecatmyself.common.bean.InChatMessage;
import com.github.unclecatmyself.common.constant.Constans;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * Created by MySelf on 2018/12/19.
 */
public class MessageChangeUtil {

    public static InChatMessage Change(Map<String,Object> maps){
        InChatMessage message = new InChatMessage();
        if (maps.containsKey(Constans.TOKEN)){
            message.setToken((String) maps.get(Constans.TOKEN));
        }
        if (maps.containsKey(Constans.TIME)){
            message.setTime((Date) maps.get(Constans.TIME));
        }
        if (maps.containsKey(Constans.VALUE)){
            message.setValue((String)maps.get(Constans.VALUE));
        }
        if (maps.containsKey(Constans.TYPE)){
            message.setType((String)maps.get(Constans.TYPE));
        }
        if (maps.containsKey(Constans.ONE)){
            message.setOne((String)maps.get(Constans.ONE));
        }
        if (maps.containsKey(Constans.GROUPID)){
            message.setGroudId((String)maps.get(Constans.GROUPID));
        }
        if (maps.containsKey(Constans.ON_ONLINE)){
            message.setOnline((String)maps.get(Constans.ON_ONLINE));
        }
        if (maps.containsKey(Constans.ONLINE_GROUP)){
            message.setOnlineGroup((String)maps.get(Constans.ONLINE_GROUP));
        }
        return message;
    }

}

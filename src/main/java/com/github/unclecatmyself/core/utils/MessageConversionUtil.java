package com.github.unclecatmyself.core.utils;

import com.github.unclecatmyself.core.bean.InChatMessage;
import com.github.unclecatmyself.core.constant.Constants;

import java.util.Date;
import java.util.Map;

/**
 * Created by MySelf on 2018/12/19.
 */
public class MessageConversionUtil {

    public static InChatMessage convert(Map<String,Object> maps){
        InChatMessage message = new InChatMessage();
        if (maps.containsKey(Constants.TOKEN)){
            message.setToken((String) maps.get(Constants.TOKEN));
        }
        if (maps.containsKey(Constants.TIME)){
            message.setTime((Date) maps.get(Constants.TIME));
        }
        if (maps.containsKey(Constants.VALUE)){
            message.setValue((String)maps.get(Constants.VALUE));
        }
        if (maps.containsKey(Constants.TYPE)){
            message.setType((String)maps.get(Constants.TYPE));
        }
        if (maps.containsKey(Constants.ONE)){
            message.setOne((String)maps.get(Constants.ONE));
        }
        if (maps.containsKey(Constants.GROUP_ID)){
            message.setGroupId((String)maps.get(Constants.GROUP_ID));
        }
        if (maps.containsKey(Constants.ONLINE)){
            message.setOnline((String)maps.get(Constants.ONLINE));
        }
        if (maps.containsKey(Constants.ONLINE_GROUP)){
            message.setOnlineGroup((String)maps.get(Constants.ONLINE_GROUP));
        }
        return message;
    }

}

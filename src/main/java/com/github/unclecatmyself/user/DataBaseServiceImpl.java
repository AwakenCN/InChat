package com.github.unclecatmyself.user;

import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import com.github.unclecatmyself.common.bean.InChatMessage;

import java.util.Map;

/**
 * Created by MySelf on 2018/12/19.
 */
public class DataBaseServiceImpl implements InChatToDataBaseService{

    @Override
    public Boolean writeMapToDB(InChatMessage message) {
        System.out.println(message.toString());
        return true;
    }
}

package com.github.unclecatmyself.user;

import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import com.github.unclecatmyself.common.bean.InChatMessage;

/**
 * Created by MySelf on 2018/12/14.
 */
public class ToDataBaseServiceImpl implements InChatToDataBaseService {


    @Override
    public Boolean writeMessage(InChatMessage message) {
        System.out.println(message.toString());
        return true;
    }
}

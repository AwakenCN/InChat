package com.github.unclecatmyself.user;

import com.github.unclecatmyself.common.bean.InChatMessage;
import com.github.unclecatmyself.common.utils.MessageChangeUtil;
import com.github.unclecatmyself.task.ListenAsynData;

import java.util.Map;

/**
 * Created by MySelf on 2019/8/26.
 */
public class UserListenAsynData extends ListenAsynData {

    @Override
    public void asynData(Map<String, Object> maps) {
        InChatMessage inChatMessage = MessageChangeUtil.Change(maps);
        System.out.println(inChatMessage.toString());
    }

}

package com.github.unclecatmyself.users;

import com.github.unclecatmyself.common.bean.InChatMessage;
import com.github.unclecatmyself.common.utils.MessageChangeUtil;
import com.github.unclecatmyself.common.utils.SpringContextUtils;
import com.github.unclecatmyself.task.ListenAsynData;
import com.github.unclecatmyself.users.pojo.Message;
import com.github.unclecatmyself.users.repository.MessageRepository;

import java.util.Map;

/**
 * Created by MySelf on 2019/8/20.
 */
public class UserTextData extends ListenAsynData {

    private MessageRepository repository = (MessageRepository) SpringContextUtils.getBean(MessageRepository.class);

    @Override
    public void asynData(Map<String, Object> maps) {

        InChatMessage inChatMessage = MessageChangeUtil.Change(maps);

        Message message = new Message();
        message.setOne(inChatMessage.getOne());
        message.setGroudId(inChatMessage.getGroudId());
        message.setOnline(inChatMessage.getOnline());
        message.setOnlineGroup(inChatMessage.getOnlineGroup());
        message.setToken(inChatMessage.getToken());
        message.setType(inChatMessage.getType());
        message.setValue(inChatMessage.getValue());
        message.setTime(inChatMessage.getTime());
        repository.save(message);
    }
}

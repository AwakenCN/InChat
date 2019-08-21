package com.github.unclecatmyself.users;

import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import com.github.unclecatmyself.common.bean.InChatMessage;
import com.github.unclecatmyself.common.utils.SpringContextUtils;
import com.github.unclecatmyself.users.pojo.Message;
import com.github.unclecatmyself.users.pojo.Test;
import com.github.unclecatmyself.users.repository.MessageRepository;
import com.github.unclecatmyself.users.repository.TestRepository;

/**
 * Created by MySelf on 2019/1/3.
 */
public class DataBaseServiceImpl implements InChatToDataBaseService {

    private MessageRepository repository = (MessageRepository) SpringContextUtils.getBean(MessageRepository.class);

    //获取消息
    public Boolean writeMessage(InChatMessage inChatMessage) {
        Message message = new Message();
        message.setOne(inChatMessage.getOne());
        message.setGroudId(inChatMessage.getGroudId());
        message.setOnline(inChatMessage.getOnline());
        message.setOnlineGroup(inChatMessage.getOnlineGroup().toString());
        message.setToken(inChatMessage.getToken());
        message.setType(inChatMessage.getType());
        message.setValue(inChatMessage.getValue());
        message.setTime(inChatMessage.getTime());
        repository.save(message);
        return true;
    }
}

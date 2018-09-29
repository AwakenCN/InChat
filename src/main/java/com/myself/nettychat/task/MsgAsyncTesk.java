package com.myself.nettychat.task;

import com.myself.nettychat.constont.LikeSomeCacheTemplate;
import com.myself.nettychat.dataobject.User;
import com.myself.nettychat.dataobject.UserMsg;
import com.myself.nettychat.repository.UserMsgRepository;
import com.myself.nettychat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:50 2018\8\14 0014
 */
@Component
public class MsgAsyncTesk {

    @Autowired
    private LikeSomeCacheTemplate cacheTemplate;

    @Autowired
    private UserMsgRepository userMsgRepository;

    @Autowired
    private UserRepository userRepository;

    @Async
    public Future<Boolean> saveChatMsgTask() throws Exception{

        List<UserMsg> userMsgList = cacheTemplate.cloneCacheMap();
        for (UserMsg item:userMsgList){
            //保护措施
            User user = userRepository.findByUserName(item.getName());
            if (user != null){
                userMsgRepository.save(item);
            }
        }
        //清空临时缓存
        cacheTemplate.clearCacheMap();
        return new AsyncResult<>(true);
    }

}

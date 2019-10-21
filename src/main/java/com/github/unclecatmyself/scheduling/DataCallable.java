package com.github.unclecatmyself.scheduling;

import com.github.unclecatmyself.core.utils.MessageConversionUtil;
import com.github.unclecatmyself.support.InChatDBManager;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * TODO 会发生线程异常
 * 获取结果的异步线程任务
 * Create by UncleCatMySelf in 11:25 2018\12\30 0030
 */
public class DataCallable implements Callable<Boolean>{

    /** 用户读数据接口伪实现 */
    private final InChatDBManager inChatDBManager;
    /** 消息数据 */
    private final Map<String,Object> maps;

    DataCallable(InChatDBManager inChatToDataBaseService,Map<String,Object> maps) {
        this.inChatDBManager = inChatToDataBaseService;
        this.maps = maps;
    }

    @Override
    public Boolean call() throws Exception {
        inChatDBManager.writeMessage(MessageConversionUtil.convert(maps));
        return true;
    }
}

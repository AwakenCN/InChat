package com.github.unclecatmyself.task;

import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import com.github.unclecatmyself.common.utils.MessageChangeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 数据异步转移方法
 * Created by MySelf on 2018/12/3.
 */
public class DataAsynchronousTask {

    private final Logger log = LoggerFactory.getLogger(DataAsynchronousTask.class);

    /** 用户读数据接口伪实现 */
    private final InChatToDataBaseService inChatToDataBaseService;


    public DataAsynchronousTask(InChatToDataBaseService inChatToDataBaseService){
        this.inChatToDataBaseService = inChatToDataBaseService;
    }

    /**
     * 将Netty数据消息借助这个方法已新线程发送给用户实现读方法
     * @param maps {@link Map} 传递消息
     */
    public void writeData(Map<String,Object> maps){
        log.info("【异步写入数据】");
        new Thread(new Runnable() {
            @Override
            public void run() {
                inChatToDataBaseService.writeMapToDB(MessageChangeUtil.Change(maps));
            }
        }).start();
    }

}

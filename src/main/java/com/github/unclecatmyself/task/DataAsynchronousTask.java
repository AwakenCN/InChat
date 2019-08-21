package com.github.unclecatmyself.task;

import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import com.github.unclecatmyself.common.constant.LogConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * TODO 会发生线程异常
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
        Boolean result = false;
        ExecutorService service = Executors.newCachedThreadPool();
        final FutureTask<Boolean> future = new FutureTask<Boolean>(new DataCallable(inChatToDataBaseService,maps));
        service.execute(future);
        try {
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(LogConstant.DATAASYNCHRONOUSTASK_01);
        } catch (ExecutionException e) {
            log.error(LogConstant.DATAASYNCHRONOUSTASK_02);
        }
        if (!result){
            log.error(LogConstant.DATAASYNCHRONOUSTASK_03);
        }
    }

}

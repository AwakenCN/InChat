package com.myself.unclecatmyself.task;

import com.myself.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by MySelf on 2018/12/3.
 */
@Slf4j
@Component
public class DataAsynchronousTask {

    @Autowired
    InChatToDataBaseService inChatToDataBaseService;

    @Async
    public Future<Boolean> writeData(Map<String,Object> maps) throws Exception {
        log.info("异步写入数据："+maps.toString());
        return new AsyncResult<>(inChatToDataBaseService.writeMapToDB(maps));
    }

}

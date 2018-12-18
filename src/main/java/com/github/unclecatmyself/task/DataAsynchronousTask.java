package com.github.unclecatmyself.task;

import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.Future;

/**
 * Created by MySelf on 2018/12/3.
 */
@Slf4j
public class DataAsynchronousTask {


    InChatToDataBaseService inChatToDataBaseService;


    public Future<Boolean> writeData(Map<String,Object> maps) throws Exception {
        log.info("【异步写入数据】");
//        return new AsyncResult<>(inChatToDataBaseService.writeMapToDB(maps));
        return null;
    }

}

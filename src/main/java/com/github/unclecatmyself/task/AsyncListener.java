package com.github.unclecatmyself.task;

import java.util.Map;

/**
 * 异步监听
 * Created by MySelf on 2019/8/20.
 */
public abstract class AsyncListener {

    public abstract void asyncData(Map<String,Object> asyncData);

    public abstract void asybcState(String state,String token);
}


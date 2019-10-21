package com.github.unclecatmyself.scheduling;

import java.util.Map;

/**
 * 异步监听
 * Created by MySelf on 2019/8/20.
 */
public abstract class AsyncListener {

    public abstract void asyncData(Map<String,Object> asyncData);

    public abstract void asyncState(String state, String token);
}


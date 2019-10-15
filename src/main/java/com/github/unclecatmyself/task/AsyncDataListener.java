package com.github.unclecatmyself.task;

import java.util.Map;

/**
 * 监听异步数据
 * Created by MySelf on 2019/8/20.
 */
public abstract class AsyncDataListener {

    public abstract void asyncData(Map<String,Object> asyncData);

}


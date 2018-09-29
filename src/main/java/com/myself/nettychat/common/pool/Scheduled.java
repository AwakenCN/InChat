package com.myself.nettychat.common.pool;

import java.util.concurrent.ScheduledFuture;

/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 接口
 **/
@FunctionalInterface
public interface Scheduled {

    ScheduledFuture<?> submit(Runnable runnable);

}

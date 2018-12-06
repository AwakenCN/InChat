package com.myself.unclecatmyself.common.pool;

import java.util.concurrent.ScheduledFuture;

/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
@FunctionalInterface
public interface Scheduled {

    ScheduledFuture<?> submit(Runnable runnable);

}

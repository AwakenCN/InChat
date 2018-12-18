package com.github.unclecatmyself.task;

import com.github.unclecatmyself.common.pool.Scheduled;
import com.github.unclecatmyself.common.properties.InitNetty;

import java.util.concurrent.*;


/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
public class ScheduledPool implements Scheduled {

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(20);

    private final InitNetty serverBean;

    public ScheduledPool(InitNetty serverBean) {
        this.serverBean = serverBean;
    }

    @Override
    public ScheduledFuture<?> submit(Runnable runnable) {
        int initalDelay = serverBean.getInitalDelay();
        int period = serverBean.getPeriod();
        return scheduledExecutorService.scheduleAtFixedRate(runnable, initalDelay, period, TimeUnit.SECONDS);
    }

}

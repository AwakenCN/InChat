package com.myself.unclecatmyself.task;

import com.myself.unclecatmyself.common.properties.InitNetty;
import com.myself.unclecatmyself.common.pool.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;


/**
 * Create by UncleCatMySelf in 2018/12/06
 **/
@Service
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

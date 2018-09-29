package com.myself.nettychat.task;

import com.myself.nettychat.common.properties.InitNetty;
import com.myself.nettychat.common.pool.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;


/**
 * @author  MySelf
 * @create  2018/9/22
 * @desc 定时任务
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

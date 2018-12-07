package com.github.unclecatmyself.common.pool;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  Create by UncleCatMySelf in 2018/12/06
 *   tomcat: org.apache.catalina.core.StandardThreadExecutor
 *
 *   java.util.concurrent.threadPoolExecutor execute执行策略:
 *   优先offer到queue，queue满后再扩充线程到maxThread，如果已经到了maxThread就reject
 *   比较适合于CPU密集型应用（比如runnable内部执行的操作都在JVM内部，memory copy, or compute等等）
 *
 *   StandardThreadExecutor execute执行策略：
 *   优先扩充线程到maxThread，再offer到queue，如果满了就reject
 *   比较适合于业务处理需要远程资源的场景
 **/
public class StandardThreadExecutor extends ThreadPoolExecutor {

    public static final int DEFAULT_MIN_THREADS = 20;
    public static final int DEFAULT_MAX_THREADS = 200;
    public static final int DEFAULT_MAX_IDLE_TIME = 60000; // 1 minutes

    protected AtomicInteger submittedTasksCount;	// 正在处理的任务数
    private int maxSubmittedTaskCount;				// 最大允许同时处理的任务数

    public StandardThreadExecutor() {
        this(DEFAULT_MIN_THREADS, DEFAULT_MAX_THREADS);
    }

    public StandardThreadExecutor(int coreThread, int maxThreads) {
        this(coreThread, maxThreads, maxThreads);
    }

    public StandardThreadExecutor(int coreThread, int maxThreads, long keepAliveTime, TimeUnit unit) {
        this(coreThread, maxThreads, keepAliveTime, unit, maxThreads);
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, int queueCapacity) {
        this(coreThreads, maxThreads, queueCapacity, Executors.defaultThreadFactory());
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, int queueCapacity, ThreadFactory threadFactory) {
        this(coreThreads, maxThreads, DEFAULT_MAX_IDLE_TIME, TimeUnit.MILLISECONDS, queueCapacity, threadFactory);
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, long keepAliveTime, TimeUnit unit, int queueCapacity) {
        this(coreThreads, maxThreads, keepAliveTime, unit, queueCapacity, Executors.defaultThreadFactory());
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, long keepAliveTime, TimeUnit unit,
                                  int queueCapacity, ThreadFactory threadFactory) {
        this(coreThreads, maxThreads, keepAliveTime, unit, queueCapacity, threadFactory, new AbortPolicy());
    }

    public StandardThreadExecutor(int coreThreads, int maxThreads, long keepAliveTime, TimeUnit unit,
                                  int queueCapacity, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(coreThreads, maxThreads, keepAliveTime, unit, new ExecutorQueue(), threadFactory, handler);
        ((ExecutorQueue) getQueue()).setStandardThreadExecutor(this);

        submittedTasksCount = new AtomicInteger(0);

        // 最大并发任务限制： 队列buffer数 + 最大线程数
        maxSubmittedTaskCount = queueCapacity + maxThreads;
    }

    @Override
    public void execute(Runnable command) {
        int count = submittedTasksCount.incrementAndGet();

        // 超过最大的并发任务限制，进行 reject
        // 依赖的LinkedTransferQueue没有长度限制，因此这里进行控制
        if (count > maxSubmittedTaskCount) {
            submittedTasksCount.decrementAndGet();
            getRejectedExecutionHandler().rejectedExecution(command, this);
        }

        try {
            super.execute(command);
        } catch (RejectedExecutionException rx) {
            // there could have been contention around the queue
            if (!((ExecutorQueue) getQueue()).force(command)) {
                submittedTasksCount.decrementAndGet();
                getRejectedExecutionHandler().rejectedExecution(command, this);
            }
            rx.printStackTrace();
        }
    }

    public int getSubmittedTasksCount() {
        return this.submittedTasksCount.get();
    }


    protected void afterExecute(Runnable r, Throwable t) {
        submittedTasksCount.decrementAndGet();
    }
}

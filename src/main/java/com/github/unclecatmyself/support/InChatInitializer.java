package com.github.unclecatmyself.support;

import com.github.unclecatmyself.core.bean.InitNetty;
import com.github.unclecatmyself.core.thread.StandardThreadExecutor;

/**
 * Created by MySelf on 2019/8/26.
 */
public class InChatInitializer extends InitNetty {

    public static final StandardThreadExecutor optionExecutor = new StandardThreadExecutor();//操作池

    @Override
    public int getWebPort() {
        return 8090;
    }

    //分布式
    @Override
    public Boolean getDistributed() {
        return false;
    }

    //加密
    @Override
    public boolean isSsl() {
        return false;
    }

}

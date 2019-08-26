package com.github.unclecatmyself.user;

import com.github.unclecatmyself.common.bean.InitNetty;

/**
 * Created by MySelf on 2019/8/26.
 */
public class MyInit extends InitNetty {

    @Override
    public int getWebport() {
        return 8070;
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

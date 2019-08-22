package com.github.unclecatmyself.users;

import com.github.unclecatmyself.common.bean.InitNetty;

/**
 * Created by MySelf on 2019/1/3.
 */
public class MyInit extends InitNetty {

    @Override
    public int getWebport() {
        return 8070;
    }

    @Override
    public Boolean getDistributed() {
        return false;
    }

    @Override
    public boolean isSsl() {
        return false;
    }

}

package com.github.unclecatmyself.user;

import com.github.unclecatmyself.common.bean.InitNetty;

/**
 * Created by MySelf on 2018/12/27.
 */
public class MyInit extends InitNetty{

    @Override
    public int getWebport() {
        return 8070;
    }
}

package com.github.unclecatmyself.service;

import com.github.unclecatmyself.common.bean.InChatMessage;
import com.github.unclecatmyself.common.utils.MessageConversionUtil;
import com.github.unclecatmyself.task.AsyncListener;

import java.util.Map;

/**
 * Created by MySelf on 2019/8/26.
 */
public class UserAsyncDataListener extends AsyncListener {

    @Override
    public void asyncData(Map<String, Object> asyncData) {
        InChatMessage inChatMessage = MessageConversionUtil.convert(asyncData);
        System.out.println(asyncData);
    }

    @Override
    public void asybcState(String state, String token) {
        System.out.println("用户："+token+"，更新状态："+state);
    }

}

package com.github.unclecatmyself.service;

import com.github.unclecatmyself.common.bean.InChatMessage;
import com.github.unclecatmyself.common.utils.MessageConversionUtil;
import com.github.unclecatmyself.task.AsyncDataListener;

import java.util.Map;

/**
 * Created by MySelf on 2019/8/26.
 */
public class UserAsyncDataListener extends AsyncDataListener {

    @Override
    public void asyncData(Map<String, Object> asyncData) {
        InChatMessage inChatMessage = MessageConversionUtil.convert(asyncData);
        System.out.println(inChatMessage.toString());
    }

}

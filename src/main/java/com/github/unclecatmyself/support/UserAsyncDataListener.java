package com.github.unclecatmyself.support;

import com.github.unclecatmyself.core.bean.InChatMessage;
import com.github.unclecatmyself.core.utils.MessageConversionUtil;
import com.github.unclecatmyself.scheduling.AsyncListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by MySelf on 2019/8/26.
 */
public class UserAsyncDataListener extends AsyncListener {

    private final static Logger logger = LoggerFactory.getLogger(UserAsyncDataListener.class);

    @Override
    public void asyncData(Map<String, Object> asyncData) {
        InChatMessage inChatMessage = MessageConversionUtil.convert(asyncData);
        if(logger.isInfoEnabled()){
            logger.info(
                    "原始数据： {}", asyncData,
                    "序列化后的数据为： {}", inChatMessage
            );
        }
    }

    @Override
    public void asyncState(String state, String token) {
        if (logger.isInfoEnabled()) {
            logger.info("用户 {}, 更新状态：{} ", token, state);
        }
    }

}

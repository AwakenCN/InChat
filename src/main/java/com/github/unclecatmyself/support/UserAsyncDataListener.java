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
    public void asyncData(InChatMessage message) {
        if(logger.isInfoEnabled()){
            logger.info(
                    "序列化后的数据为： {}", message
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

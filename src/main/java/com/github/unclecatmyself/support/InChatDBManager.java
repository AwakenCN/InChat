package com.github.unclecatmyself.support;

import com.github.unclecatmyself.core.bean.InChatMessage;

/**
 * Created by MySelf on 2018/12/3.
 */
public interface InChatDBManager {

    Boolean writeMessage(InChatMessage message);

}


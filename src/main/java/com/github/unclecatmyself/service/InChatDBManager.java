package com.github.unclecatmyself.service;

import com.github.unclecatmyself.common.bean.InChatMessage;

import java.util.Map;

/**
 * Created by MySelf on 2018/12/3.
 */
public interface InChatDBManager {

    Boolean writeMessage(InChatMessage message);

}


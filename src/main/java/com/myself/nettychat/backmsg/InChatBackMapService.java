package com.myself.nettychat.backmsg;

import java.util.Map;

/**
 * 消息返回
 * Created by MySelf on 2018/11/23.
 */
public interface InChatBackMapService {

    Map<String,String> loginSuccess();

    Map<String,String> loginError();

}

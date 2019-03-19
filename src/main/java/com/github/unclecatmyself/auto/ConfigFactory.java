package com.github.unclecatmyself.auto;

import com.github.unclecatmyself.bootstrap.channel.http.FromServerService;
import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;
import com.github.unclecatmyself.common.bean.InitNetty;

/**
 * 默认配置工厂
 * Created by MySelf on 2018/12/21.
 */
public class ConfigFactory {

    /** Redis的ip地址，这你不会不知道吧？ */
    public static String RedisIP;

    /** 用户校验伪接口 */
    public static InChatVerifyService inChatVerifyService;

    /** 用户获取数据伪接口 */
    public static InChatToDataBaseService inChatToDataBaseService;

    /** 系统信息枚举服务接口 */
    public static FromServerService fromServerService;

    /** InChat项目配置 */
    public static InitNetty initNetty;

}

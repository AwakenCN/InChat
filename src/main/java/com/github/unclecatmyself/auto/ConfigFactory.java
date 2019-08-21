package com.github.unclecatmyself.auto;

import com.github.unclecatmyself.bootstrap.channel.http.FromServerService;
import com.github.unclecatmyself.bootstrap.data.InChatToDataBaseService;
import com.github.unclecatmyself.bootstrap.verify.InChatVerifyService;
import com.github.unclecatmyself.common.bean.InitNetty;
import com.github.unclecatmyself.task.ListenAsynData;

/**
 * 默认配置工厂
 * Created by MySelf on 2018/12/21.
 */
public class ConfigFactory {

    /** Redis的ip地址 */
    public static String RedisIP;

    /** 用户校验伪接口 */
    public static InChatVerifyService inChatVerifyService;

    /** 系统信息枚举服务接口 */
    public static FromServerService fromServerService;

    /** InChat项目配置 */
    public static InitNetty initNetty;

    /** 用户监听异步数据伪接口 */
    public static ListenAsynData listenAsynData;

}

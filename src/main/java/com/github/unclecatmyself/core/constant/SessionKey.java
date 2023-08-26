package com.github.unclecatmyself.core.constant;

import io.netty.util.AttributeKey;

/**
 * @author haoyitao
 * @implSpec channel属性定义
 * @since 2023/8/24 - 17:14
 * @version 1.0
 */
public class SessionKey {

    /**
     * 角色username
     */
    public static final AttributeKey<String> PLAYER_USERNAME = AttributeKey.valueOf("player_username");
    /**
     * token
     */
    public static final AttributeKey<String> TOKEN = AttributeKey.valueOf("token");
    /**
     * 上次的CREATE_ROLE
     */
    public static final AttributeKey<String> CREATE_ROLE = AttributeKey.valueOf("create_role");
    /**
     * 玩家Session状态
     */
    public static final AttributeKey<Integer> PLAYER_STATE = AttributeKey.valueOf("player_state");
    /**
     * 玩家IP
     */
    public static final AttributeKey<String> USER_IP =  AttributeKey.valueOf("user_ip");
    /**
     * 玩家地址
     */
    public static final AttributeKey<String> USER_ADDRESS = AttributeKey.valueOf("user_address");
    public static final AttributeKey<String> USER_ADDRESS_FULL  = AttributeKey.valueOf("user_address_full");
    /**
     * 通道创建时间
     */
    public static final AttributeKey<Integer> CHANNEL_CREATE_TIME = AttributeKey.valueOf("channel_create_time");
    /**
     * 是否http协议
     **/
    public static final AttributeKey<Boolean> IS_HTTP= AttributeKey.valueOf("is_http");

    public static final AttributeKey<Boolean> IS_KEEP_ALIVE= AttributeKey.valueOf("isKeepAlive");


}

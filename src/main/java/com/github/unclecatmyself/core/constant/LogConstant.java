package com.github.unclecatmyself.core.constant;

/**
 * Created by MySelf on 2019/1/3.
 */
public class LogConstant {

    public static final String HTTPCHANNELSERVICEIMPL_NOTFINDLOGIN = "[HttpChannelImpl.sendFromServer] 未找到用户在线标识";

    public static final String HTTPCHANNELSERVICEIMPL_CLOSE = "[HttpChannelImpl.close] 关闭HTTP通道连接";

    public static final String HTTPCHANNELSERVICEIMPL_SEND_EXCEPTION = "[HttpChannelImpl.sendFromServer] 发送通知异常";

    public static final String DEFAULTWEBSOCKETHANDLER_GETSIZE = "[DefaultWebSocketHandler.httpdoMessage.GET_SIZE]";

    public static final String DEFAULTWEBSOCKETHANDLER_SENDFROMSERVER = "[DefaultWebSocketHandler.httpdoMessage.SEND_FROM_SERVER]";

    public static final String DEFAULTWEBSOCKETHANDLER_NOTFINDURI = "[DefaultWebSocketHandler.httpdoMessage.NOT_FIND_URI]";

    public static final String DEFAULTWEBSOCKETHANDLER_GETLIST = "[DefaultWebSocketHandler.httpdoMessage.GET_LIST]";

    public static final String DEFAULTWEBSOCKETHANDLER_GETSTATE = "[DefaultWebSocketHandler.httpdoMessage.GET_STATE]";

    public static final String DEFAULTWEBSOCKETHANDLER_SENDINCHAT = "[DefaultWebSocketHandler.httpdoMessage.SEND_IN_CHAT]";

    public static final String DEFAULTWEBSOCKETHANDLER_LOGIN = "[DefaultWebSocketHandler.textdoMessage.LOGIN]";

    public static final String DEFAULTWEBSOCKETHANDLER_SENDME = "[DefaultWebSocketHandler.textdoMessage.SEND_ME]";

    public static final String DefaultWebSocketHandler_SENDTO = "[DefaultWebSocketHandler.textdoMessage.SEND_TO]";

    public static final String DEFAULTWEBSOCKETHANDLER_SENDGROUP = "[DefaultWebSocketHandler.textdoMessage.SEND_GROUP]";

    public static final String CHANNELACTIVE = "[DefaultWebSocketHandler.channelActive]";

    public static final String CHANNEL_SUCCESS = "链接成功";

    public static final String DISCONNECT = "异常断开";

    public static final String EXCEPTIONCAUGHT = "[DefaultWebSocketHandler.exceptionCaught]";

    public static final String CHANNELINACTIVE = "[AbstractHandler：channelInactive]";

    public static final String CLOSE_SUCCESS = "关闭成功";

    public static final String NOTFINDLOGINCHANNLEXCEPTION = "[捕获异常：LoginChannelNotFoundException]-[AbstractHandler：channelInactive] 关闭未正常注册链接！";

    public static final String DATAASYNCHRONOUSTASK_01 = "[DataAsynchronousTask.writeData]:数据外抛异常";

    public static final String DATAASYNCHRONOUSTASK_02 = "[DataAsynchronousTask.writeData]:数据外抛异常";

    public static final String DATAASYNCHRONOUSTASK_03 = "[DataAsynchronousTask.writeData]:线程任务执行异常";

    public static final String REDIS_START = "[RedisConfig.getJedis]:连接成功，测试连接PING->";
}

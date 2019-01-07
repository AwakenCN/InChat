package com.github.unclecatmyself.common.constant;

/**
 * Created by MySelf on 2019/1/3.
 */
public class LogConstant {

    public static final String HTTPCHANNELSERVICEIMPL_NOTFINDLOGIN = "[HttpChannelServiceImpl.sendFromServer] 未找到用户在线标识";

    public static final String HTTPCHANNELSERVICEIMPL_CLOSE = "[HttpChannelServiceImpl.close] 关闭HTTP通道连接";

    public static final String HTTPCHANNELSERVICEIMPL_SEND_EXCEPTION = "[HttpChannelServiceImpl.sendFromServer] 发送通知异常";

    public static final String DEFAULTWEBSOCKETHANDLER_GETSIZE = "[DefaultWebSocketHandler.httpdoMessage.GETSIZE]";

    public static final String DEFAULTWEBSOCKETHANDLER_SENDFROMSERVER = "[DefaultWebSocketHandler.httpdoMessage.SENDFROMSERVER]";

    public static final String DEFAULTWEBSOCKETHANDLER_NOTFINDURI = "[DefaultWebSocketHandler.httpdoMessage.NOTFINDURI]";

    public static final String DEFAULTWEBSOCKETHANDLER_GETLIST = "[DefaultWebSocketHandler.httpdoMessage.GETLIST]";

    public static final String DEFAULTWEBSOCKETHANDLER_SENDINCHAT = "[DefaultWebSocketHandler.httpdoMessage.SENDINCHAT]";

    public static final String DEFAULTWEBSOCKETHANDLER_LOGIN = "[DefaultWebSocketHandler.textdoMessage.LOGIN]";

    public static final String DEFAULTWEBSOCKETHANDLER_SENDME = "[DefaultWebSocketHandler.textdoMessage.SENDME]";

    public static final String DefaultWebSocketHandler_SENDTO = "[DefaultWebSocketHandler.textdoMessage.SENDTO]";

    public static final String DEFAULTWEBSOCKETHANDLER_SENDGROUP = "[DefaultWebSocketHandler.textdoMessage.SENDGROUP]";

    public static final String CHANNELACTIVE = "[DefaultWebSocketHandler.channelActive]";

    public static final String CHANNEL_SUCCESS = "链接成功";

    public static final String DISCONNECT = "异常断开";

    public static final String EXCEPTIONCAUGHT = "[DefaultWebSocketHandler.exceptionCaught]";

    public static final String CHANNELINACTIVE = "[Handler：channelInactive]";

    public static final String CLOSE_SUCCESS = "关闭成功";

    public static final String NOTFINDLOGINCHANNLEXCEPTION = "[捕获异常：NotFindLoginChannlException]-[Handler：channelInactive] 关闭未正常注册链接！";

    public static final String DATAASYNCHRONOUSTASK_01 = "[DataAsynchronousTask.writeData]:数据外抛异常";

    public static final String DATAASYNCHRONOUSTASK_02 = "[DataAsynchronousTask.writeData]:数据外抛异常";

    public static final String DATAASYNCHRONOUSTASK_03 = "[DataAsynchronousTask.writeData]:线程任务执行异常";

    public static final String REDIS_START = "[RedisConfig.getJedis]:连接成功，测试连接PING->";
}

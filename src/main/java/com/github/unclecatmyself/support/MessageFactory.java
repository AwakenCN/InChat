package com.github.unclecatmyself.support;

import com.github.unclecatmyself.bootstrap.handler.InChatMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 命令工厂
 */
public class MessageFactory {


    private static final Logger logger = LoggerFactory
            .getLogger(MessageFactory.class);

    public static final MessageFactory INSTANCE = new MessageFactory();

    private final Map<String, Method> msg2Method = new ConcurrentHashMap<>();

    private MessageFactory() {
    }

    public static MessageFactory getInstance() {
        return INSTANCE;
    }

    /**
     * 注册服务
     * @param serviceClass 服务类
     * @throws Exception
     */
    public void registerService(HandlerService serviceClass) throws Exception {
        if (serviceClass == null) {
            throw new IllegalArgumentException("service can`t be null!");
        }
        /**
         * 根据Annotation抽取方法
         */
        Method[] methods = serviceClass.getClass().getMethods();
        for (Method method : methods) {
            if (!method.isAnnotationPresent(InChatMessageHandler.class)) {
                continue;
            }
            Class<?>[] params = method.getParameterTypes();
            if (params.length != 2 && params.length != 3) {
                throw new IllegalArgumentException("方法 ["+method.getName()+"] 注册失败，参数数量必须为2或3");
            }

            msg2Method.put(method.getName().toLowerCase(), method);

        }
        logger.warn("[注册协议处理子系统] [" + serviceClass.getClass().getName() + "]");
    }

    public Map<String, Method> getMsg2Method() {
        return msg2Method;
    }

    /**
     * 获取指定消息的方法
     */
    public synchronized Method getMethod(String msgID) {
        return msg2Method.get(msgID);
    }

    /**
     *
     * @param methodName
     * @return
     */
    public synchronized Method getMessageInstance(String methodName) {
        Method method = msg2Method.get(methodName);
        if (method == null) {
            logger.error("{} 获取消息实体不存在", methodName);
            return null;
        } else {
            logger.info("{} 获取指定消息号的消息实体，该实体是初始化时产生，仅供属性读取。{}", methodName, method);
        }
        return method;
    }
}

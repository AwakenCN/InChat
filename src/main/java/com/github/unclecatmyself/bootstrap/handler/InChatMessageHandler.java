package com.github.unclecatmyself.bootstrap.handler;

import java.lang.annotation.*;

/**
 * @author haoyitao
 * @implSpec 自定义消息标识
 * @since 2023/8/25 - 17:24
 * @version 1.0
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InChatMessageHandler {
}

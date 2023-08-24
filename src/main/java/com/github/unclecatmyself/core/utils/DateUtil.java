package com.github.unclecatmyself.core.utils;

import java.time.Instant;

/**
 * @author haoyitao
 * @implSpec 时间工具类
 * @since 2023/8/24 - 17:23
 * @version 1.0
 */
public class DateUtil {

    public static int getSecond() {
        return (int) Instant.now().getEpochSecond();
    }

    public static void main(String[] args) {
        System.out.println(getSecond());
    }
}

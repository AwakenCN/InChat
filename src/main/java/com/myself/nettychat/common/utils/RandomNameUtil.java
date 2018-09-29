package com.myself.nettychat.common.utils;

import java.util.Random;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:48 2018\8\14 0014
 */
public class RandomNameUtil {

    private static Random ran = new Random();
    private final static int delta = 0x9fa5 - 0x4e00 + 1;

    public static char getName(){
        return (char)(0x4e00 + ran.nextInt(delta));
    }

}

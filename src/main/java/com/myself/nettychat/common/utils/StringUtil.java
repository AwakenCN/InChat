package com.myself.nettychat.common.utils;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:55 2018\8\20 0020
 */
public class StringUtil {

    public static String getName(String str){
        int nameIndex = str.indexOf("-");
        return str.substring(0,nameIndex);
    }


    public static String getMsg(String str){
        int nameIndex = str.indexOf("-");
        return str.substring(nameIndex + 1,str.length());
    }
}

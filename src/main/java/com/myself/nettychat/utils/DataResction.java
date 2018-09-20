package com.myself.nettychat.utils;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:32 2018\9\20 0020
 */
public class DataResction {

    /**
     * 切除符合协议的帧头帧尾
     * @param data 原始通信数据
     * @return
     */
    public static String ResctionHeadAndFeet(String data){
        return data.substring(2,data.length()-2);
    }

    /**
     * 切除符合协议的帧头帧尾————本地测试方法
     * @param data
     * @return
     */
    public static String ResctionHeadAndFeet_test(String data){
        return data.substring(2,data.length()-4);
    }

    /**
     * 返回通信CRC校验码
     * @param data
     * @return
     */
    public static String ResctionCRCCode(String data){
        return data.substring(39,data.length());
    }

    /**
     * 返回通信内容，除去CRC校验码
     * @param data
     * @return
     */
    public static String ResctionData(String data){
        return data.substring(0,39);
    }

    public static String ResctionDataNoID(String data){
        return data.substring(14);
    }

    /**
     * 返回设备唯一ID
     * @param data
     * @return
     */
    public static String ResctionID(String data){
        return data.substring(0,14);
    }

    /**
     * 返回数据类型
     * @param data
     * @return
     */
    public static String ResctionType(String data){
        return data.substring(0,1);
    }

    /**
     * 返回实际内容值
     * @param data
     * @return
     */
    public static String ResctionRealData(String data){
        return data.substring(1);
    }

    /**
     * 截取电量信息，并返回
     * @param data
     * @return
     */
    public static String ResctionPower(String data){
        return data.substring(0,4);
    }

    /**
     * 截取纬度信息，并返回
     * @param data
     * @return
     */
    public static String ResctionLatitude(String data){
        return data.substring(3,11);
    }

    /**
     * 截取经度信息，并返回
     * @param data
     * @return
     */
    public static String ResctionLongitude(String data){
        return data.substring(14,data.length());
    }


}

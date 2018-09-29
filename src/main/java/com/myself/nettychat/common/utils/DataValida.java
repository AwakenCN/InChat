package com.myself.nettychat.common.utils;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 19:32 2018\9\20 0020
 */
public class DataValida {

    /**
     * 校验数据帧头帧尾是否符合协议规范
     * @param data 通信原始数据
     * @return
     */
    public static boolean ValidateHeadAndFeet(String data){
        boolean state = false;
        if (Const.HEAD.equals(data.substring(0,2)) && Const.TAIL.equals(data.substring(data.length()-2,data.length()))){
            return true;
        }
        return state;
    }

    /**
     * 校验数据帧头是否符合协议数据规范————本地测试
     * @param data
     * @return
     */
    public static boolean ValidateHeadAndFeet_test(String data){
        boolean state = false;
        if (Const.HEAD.equals(data.substring(0,2)) && Const.TAIL.equals(data.substring(data.length()-4,data.length()-2))){
            return true;
        }
        return state;
    }

    /**
     * CRC16校验
     * @param data 内容数据
     * @param crcCode 附带CRC校验码
     * @return
     */
    public static boolean ValidateCRCCode(String data,String crcCode){
        boolean state = false;
        if (crcCode.equals(CRC16MySelf.getCRC(data.getBytes()))){
            return true;
        }
        return state;
    }

}

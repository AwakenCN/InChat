package com.github.unclecatmyself.common.utils;

import java.security.MessageDigest;

/**
 * Create by UncleCatMySelf in 15:13 2019\1\5 0005
 */
public class RedisUtil {

    private static final String STAL = "InChat";

    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static String convertMD5(String address,String token){
        String inStr = address+"&"+token+"%"+STAL;
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    public static String convertMD5(String inStr){
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++){
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    public static String getAddress(String s) {
        String[] stars = s.split("&");
        return stars[0];
    }

//    public static void main(String[] args) {
//        String s = new String("inchat");
//        System.out.println("原始：" + s);
//        System.out.println("MD5后：" + string2MD5(s));
////        System.out.println("原始：" + s);
////        System.out.println("加密后：" + convertMD5(s));
////        System.out.println("原始：" + s);
////        System.out.println("解密后：" + convertMD5(convertMD5(s)));
//    }

}

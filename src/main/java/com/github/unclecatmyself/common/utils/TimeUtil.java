package com.github.unclecatmyself.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MySelf on 2018/12/7.
 */
public class TimeUtil {

    private final static String format = "yyyy-MM-dd HH:mm:ss";

    public static String getTime(){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

}

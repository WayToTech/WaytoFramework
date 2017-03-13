package com.wayto.android.utils;

import java.text.SimpleDateFormat;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.gas.utils
 * @Description:日期时间工具类
 * @date 2016/8/11 8:45
 */
public class IDateTimeUtils {
    /**
     * 时间计数器，最多只能到99小时，如需要更大小时数需要改改方法
     *
     * @param time
     * @return
     */
    public static String showTimeCount(long time) {
        if (time >= 360000000) {
            return "00:00:00";
        }
        String timeCount = "";
        long hourc = time / 3600000;
        String hour = "0" + hourc;
        hour = hour.substring(hour.length() - 2, hour.length());

        long minuec = (time - hourc * 3600000) / (60000);
        String minue = "0" + minuec;
        minue = minue.substring(minue.length() - 2, minue.length());

        long secc = (time - hourc * 3600000 - minuec * 60000) / 1000;
        String sec = "0" + secc;
        sec = sec.substring(sec.length() - 2, sec.length());
        timeCount = hour + ":" + minue + ":" + sec;
        return timeCount;
    }

    /**
     * 时间戳格式化
     *
     * @param time
     * @param str
     * @return
     */
    public static String formatDate(long time, String str) {
        return new SimpleDateFormat(str).format(time);
    }

    /**
     * 获取当前时间
     *
     * @param str
     * @return
     */
    public static String getCurrentDate(String str) {
        return formatDate(System.currentTimeMillis(), str);
    }
}

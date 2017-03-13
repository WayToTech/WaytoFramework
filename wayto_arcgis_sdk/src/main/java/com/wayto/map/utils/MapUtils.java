package com.wayto.map.utils;

import android.location.Location;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.map.utils
 * @Description: 地图上常用工具
 * @date 2016/8/4 15:57
 */
public class MapUtils {

    /**
     * 计算两点间距离(单位M)
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public static double distanceBetween(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
    }

    /**
     * 两点的速度
     *
     * @param distance 距离(单位M)
     * @param time     时间（ms）
     * @return
     */
    public static float speedBetween(double distance, long time) {
        double dis = distance * 1000;
        long t = 1000 * 60 * 60;
        float speed = (float) dis / t;
        return speed;
    }

    /**
     * 距离换算
     *
     * @param mapScale
     * @param distance
     * @return
     */
    public static int distance2Px(double mapScale, int distance) {
        if (mapScale > distance) {
            return (int) (2000000 / mapScale);
        } else {
            return distance;
        }
    }
}

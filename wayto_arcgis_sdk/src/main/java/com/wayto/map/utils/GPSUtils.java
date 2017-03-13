package com.wayto.map.utils;

import android.content.Context;
import android.location.LocationManager;

/**
 * Describe:
 * Author: hezhiWu
 * Date: 2016-08-08
 * Time: 21:46
 * Version:1.0
 */
public class GPSUtils {
    /**
     * 判断GPS打开状态
     *
     * @param context
     * @return
     */
    public static boolean isOpenGps(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean on = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return on;
    }
}

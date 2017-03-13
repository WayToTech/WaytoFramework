package com.wayto.android.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.utils
 * @Description:权限请求类
 * @date 2017/1/9 14:15
 */

public class PermissionHelper {
    /**
     * 权限检测
     *
     * @param activity
     * @param permission
     * @return
     */
    public static boolean checkPermission(Activity activity, String permission,int requestCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            int permissionName = ContextCompat.checkSelfPermission(activity, permission);
            if (permissionName != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            } else {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}

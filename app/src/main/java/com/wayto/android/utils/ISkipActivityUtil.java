package com.wayto.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * @Package: com.jinggan.dear.utils
 * @Description: Activity跳转工具类
 * @author: Aaron
 * @date: 2015-12-10
 * @Time: 14:07
 * @version: V1.0
 */
public class ISkipActivityUtil {

    /**
     * 不带参数跳转
     *
     * @param context
     * @param cls
     */
    public static void startIntent(Context context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }

    /**
     * 带Bundle跳转
     *
     * @param context
     * @param cls
     * @param extras
     */
    public static void startIntent(Context context, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(context, cls);
        context.startActivity(intent);
    }

    /**
     * 回调跳转
     *
     * @param activity
     * @param cls
     * @param requestCode
     */
    public static void startIntentForResult(Activity activity, Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 回调跳转
     *
     * @param activity
     * @param cls
     * @param extras
     * @param requestCode
     */
    public static void startIntentForResult(Activity activity, Class<?> cls, Bundle extras, int requestCode) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(activity, cls);
        activity.startActivityForResult(intent, requestCode);
    }

    public static boolean startAPK(Context context, String packageName) {
        boolean isGo = true;
        try {
            Intent intent = context.getPackageManager()
                    .getLaunchIntentForPackage(packageName);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            isGo = false;
        }
        return isGo;
    }
}

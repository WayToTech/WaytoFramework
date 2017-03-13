package com.wayto.android.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.wayto.android.base.DataApplication;

import java.io.File;
import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.utils
 * @Description:系统工具类
 * @date 2016/11/28 10:44
 */

public class ISystemUtil {

    /**
     * 获取版本名
     *
     * @return 当前应用的版本名
     */
    public static String getVersionName() {
        String versionName = "";
        try {
            PackageManager manager = DataApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(DataApplication.getInstance().getPackageName(), 0);
            versionName = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        int versionCode = 0;
        try {
            PackageManager manager = DataApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(DataApplication.getInstance().getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：com.baidu.trace.LBSTraceService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(80);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    /**
     * 安装
     *
     * @param context
     */
    public static void installAPK(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setDataAndType(Uri.fromFile(new File(url)), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}

package com.wayto.android.common.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.wayto.android.utils.ILog;

/**
 * @Package: com.yunwei.zaina.common.dialog
 * @Description:Toast 消息提示
 * @author: Aaron
 * @date: 2016-06-08
 * @Time: 09:22
 * @version: V1.0
 */
public class ToastUtil {

    private static final String TAG = "IToastUtils";

    public static void showToast(Context context, int resId) {
        if (context == null || ((Activity) context).isFinishing()) {
            return;
        }
        showToast(context, context.getString(resId));
    }

    public static void showToast(Context context, String msg) {
        Toast mToast = null;
        try {
            if (context == null) {
                return;
            }
            if (mToast == null) {
                synchronized (ToastUtil.class) {
                    if (mToast == null) {
                        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                    } else {
                        mToast.setText(msg);
                        mToast.setDuration(Toast.LENGTH_SHORT);
                    }
                }
            } else {
                mToast.setText(msg);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
            ILog.e(TAG, e.getMessage());
        }
    }
}

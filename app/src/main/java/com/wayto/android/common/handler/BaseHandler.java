package com.wayto.android.common.handler;

import android.app.Activity;
import android.app.Service;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * @Package: com.yunwei.zaina.common.mServiceHandler
 * @Description:handler基类，防止handler引用Activity,造成内存溢出
 * @author: Aaron
 * @date: 2016-06-05
 * @Time: 15:12
 * @version: V1.0
 */
public class BaseHandler extends Handler {
    WeakReference<Activity> weakReference;
    public WeakReference<Service> mW;

    public BaseHandler(Activity activity) {
        weakReference = new WeakReference<Activity>(activity);
    }

    public BaseHandler(Service service) {
        mW = new WeakReference<Service>(service);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (weakReference != null && weakReference.get() == null) {
            throw new NullPointerException();
        }
        if (mW != null && mW.get() == null) {
            throw new NullPointerException();
        }
    }
}

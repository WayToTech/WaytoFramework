package com.wayto.android.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.wayto.android.R;
import com.wayto.android.base.DataApplication;
import com.wayto.android.function.mainFuncations.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.utils
 * @Description:Notification 工具类
 * @date 2016/12/5 15:44
 */

public class NotificationUtil {

    /**
     * 发起通知
     *
     * @return
     */
    public static Notification sendNotification(String contentTitle, String contentText, Class<MainActivity> activity) {
        Notification.Builder builder = new Notification.Builder(DataApplication.getInstance().getApplicationContext());
        Intent intent = new Intent(DataApplication.getInstance().getApplicationContext(), activity);
        builder.setContentIntent(PendingIntent.getActivity(DataApplication.getInstance(), 0, intent, 0)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(DataApplication.getInstance().getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle(contentTitle) // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText(contentText) // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        return builder.build();
    }

    /**
     * 发送提示消息
     */
    public static void sendShowMessageNotification(String context,String ticker) {
        NotificationManager mNotificationManager = (NotificationManager) DataApplication.getInstance().getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DataApplication.getInstance());
        mBuilder.setContentTitle(IUtil.getStrToRes(R.string.app_name))
                .setContentText(context)
                .setTicker(ticker) //通知首次出现在通知栏，带上升动画效果的
                .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                .setLargeIcon(BitmapFactory.decodeResource(DataApplication.getInstance().getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify((int) System.currentTimeMillis(), notification);
    }

}

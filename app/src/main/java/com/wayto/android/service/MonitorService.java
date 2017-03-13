package com.wayto.android.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.wayto.android.BuildConfig;
import com.wayto.android.R;
import com.wayto.android.base.DataApplication;
import com.wayto.android.common.Constant;
import com.wayto.android.common.handler.HandlerValue;
import com.wayto.android.function.mainFuncations.MainActivity;
import com.wayto.android.function.refreshLocationFuncation.UploadLocationPresenter;
import com.wayto.android.function.refreshLocationFuncation.data.UploadLocationEntity;
import com.wayto.android.utils.IDateTimeUtils;
import com.wayto.android.utils.ISpfUtil;
import com.wayto.android.utils.IStringUtils;
import com.wayto.android.utils.NotificationUtil;
import com.wayto.android.utils.PollingUtils;
import com.wayto.map.entity.Gps;
import com.wayto.map.utils.IPositionUtil;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.service
 * @Description:监控服务 高德定位回调 设置前台服务提高进程的级别
 * @date 2016/12/1 20:54
 */

public class MonitorService extends Service implements AMapLocationListener {
    private final String TAG = getClass().getSimpleName();
    public static final int ACTVITY_SEND_MESSENGER = 0x101;
    /*记录最后一次位置上传时间*/
    private long lastUploadTime;

    private final IBinder binder = new MonitorBinder();

    private AMapLocation location;

    private Messenger mClientMessenger;

    private Messenger serviceMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ACTVITY_SEND_MESSENGER:
                    mClientMessenger = msg.replyTo;
                    break;
            }
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();
        /*开启前台服务*/
        startForeground(17, NotificationUtil.sendNotification(getString(R.string.app_name), getString(R.string.foregroun_content_text), MainActivity.class));
        /*开始轮询*/
        PollingUtils.startPollingService(1);
        /*启动百度鹰眼服务*/
        if (BuildConfig.TRACK_SWITCH) {
            DataApplication.getInstance().startBaiduTrack();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class MonitorBinder extends Binder {
        public MonitorService getService() {
            return MonitorService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*停止前台服务*/
        stopForeground(true);
        /*停止轮询*/
        PollingUtils.stopPollingService();
        /*停止百度鹰眼服务*/
        DataApplication.getInstance().stopBaiduTrack();
        DataApplication.getInstance().destroyBaiduTrack();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                this.location = aMapLocation;
                Message message = new Message();
                message.what = HandlerValue.LOCATION_SUCCESS_KEY;
                message.obj = aMapLocation;
                try {
                    if (mClientMessenger != null) {
                        mClientMessenger.send(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                uploadLocationAction(aMapLocation);
            }
        }
    }

    /**
     * 返回定位信息
     *
     * @return
     */
    public AMapLocation getLocation() {
        return location;
    }

    /**
     * 返回Messenger
     *
     * @return
     */
    public Messenger getServiceMessenger() {
        return serviceMessenger;
    }

    /**
     * 位置上报
     *
     * @param location
     */
    private void uploadLocationAction(AMapLocation location) {
         /*坐标转换*/
        Gps gps = IPositionUtil.gcj_To_Gps84(location.getLatitude(), location.getLongitude());
        /*构建实体类*/
        UploadLocationEntity entity = new UploadLocationEntity();
        entity.setLng(gps.getWgLon());
        entity.setLat(gps.getWgLat());
        entity.setAltitude(location.getAltitude());
        entity.setBearing(location.getBearing());
        entity.setSpeed(location.getSpeed());
        entity.setCreateTime(IDateTimeUtils.getCurrentDate("yyyy-MM-dd hh:mm:dd"));

        int mode = IStringUtils.toInt(ISpfUtil.getValue(Constant.TRACK_RECORD_MODE_KEY, Constant.TRACK_RECORD_MODE.DRIVE.getValue()).toString());
        if (mode == Constant.TRACK_RECORD_MODE.WALK.getValue() && System.currentTimeMillis() - lastUploadTime > Constant.FIVE_MINUTES_TO_MILLSECONDS) {
            UploadLocationPresenter.getInstance().refreshLocation(entity);
            lastUploadTime = System.currentTimeMillis();
        } else if (mode == Constant.TRACK_RECORD_MODE.RIDING.getValue() && System.currentTimeMillis() - lastUploadTime > Constant.THREE_MINUTES_TO_MILISECONDS) {
            UploadLocationPresenter.getInstance().refreshLocation(entity);
            lastUploadTime = System.currentTimeMillis();
        } else if (mode == Constant.TRACK_RECORD_MODE.DRIVE.getValue() && System.currentTimeMillis() - lastUploadTime > Constant.ONE_MINUTE) {
            UploadLocationPresenter.getInstance().refreshLocation(entity);
            lastUploadTime = System.currentTimeMillis();
        }
    }
}

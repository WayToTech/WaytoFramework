package com.wayto.android.base;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.google.gson.Gson;
import com.tencent.bugly.crashreport.CrashReport;
import com.wayto.android.BuildConfig;
import com.wayto.android.common.Constant;
import com.wayto.android.function.accountFuncation.data.UserInfoEntity;
import com.wayto.android.db.DaoMaster;
import com.wayto.android.db.DaoSession;
import com.wayto.android.service.MonitorService;
import com.wayto.android.utils.ISpfUtil;
import com.wayto.android.vendor.baiduTrack.BaiduTrack;
import com.wayto.android.vendor.qiniu.QiNiuConfig;

import cn.jpush.android.api.JPushInterface;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.android.base
 * @Description:Application类
 * @date 2016/11/22 14:59
 */

public class DataApplication extends Application {
    private String TAG = getClass().getSimpleName();

    private static DataApplication instance;

    /*定位Client*/
    private AMapLocationClient mLocationClient;
    /*定位option*/
    private AMapLocationClientOption locationClientOption;
    /*监控服务*/
    private MonitorService monitorService;

    private DaoSession daoSession;

    /*百度Track*/
    protected BaiduTrack baiduTrack;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        /*初始化七牛配制*/
        QiNiuConfig.iniConfig(BuildConfig.QINIU_DOMAIN, BuildConfig.QINIU_FILENAME);
        /*Bugly初始化*/
        CrashReport.initCrashReport(getApplicationContext(), BuildConfig.BUGLY_ID, true);
        /*JPush初始化*/
        JPushInterface.init(getApplicationContext());
        JPushInterface.setDebugMode(true);
    }

    public static DataApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        /*分包处理 DEX*/
        MultiDex.install(this);
    }

    /**
     * 初始化定位参数
     */
    private void initLocationClient(MonitorService service) {
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getApplicationContext());

            locationClientOption = new AMapLocationClientOption();
            locationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度
            locationClientOption.setNeedAddress(true);
            locationClientOption.setInterval(4 * 1000);

            mLocationClient.setLocationOption(locationClientOption);
            mLocationClient.setLocationListener(service);
        }
    }

    /**
     * 启动定位
     */
    public void startLocation(MonitorService service) {
        initLocationClient(service);
        mLocationClient.startLocation();
    }

    /**
     * 停止定位服务
     */
    public void stopLocationService() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    /**
     * 销毁定位服务
     */
    public void destoryLocation() {
        mLocationClient = null;
        locationClientOption = null;
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    /**
     * 初始化百度鹰眼服务
     */
    private void initBaiduTrack() {
        if (baiduTrack == null) {
            baiduTrack = BaiduTrack.getInstance();
            baiduTrack.setSERVICE_ID(BuildConfig.BAIDUTRACK_SERVICE_ID);
            baiduTrack.setENTITY_NAME(BuildConfig.BAIDUTRACK_ENTITY_NAME + ISpfUtil.getValue(Constant.ACCESS_TOKEN_KEY, "").toString());
        }
    }

    /**
     * 启动百度Track服务
     */
    public void startBaiduTrack() {
        initBaiduTrack();
        baiduTrack.startTrace(getApplicationContext());
    }

    /**
     * 停止百度Track服务
     */
    public void stopBaiduTrack() {
        if (baiduTrack != null) {
            baiduTrack.stopTrace();
        }
    }

    /**
     * 注销百度Track
     */
    public void destroyBaiduTrack() {
        if (baiduTrack != null) {
            baiduTrack.destroy();
        }
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public UserInfoEntity getUserInfoEntity() {
        String string = ISpfUtil.getValue(Constant.USERINFO_KEY, "").toString();
        UserInfoEntity entity = new Gson().fromJson(string, UserInfoEntity.class);
        return entity;
    }

    /**
     * 获取DAO对象
     *
     * @return
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, Constant.DATEBASE_NAME);
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


    public MonitorService getMonitorService() {
        return monitorService;
    }

    public void setMonitorService(MonitorService monitorService) {
        this.monitorService = monitorService;
    }
}

package com.wayto.android.function.mainFuncations;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.amap.api.location.AMapLocation;
import com.esri.core.geometry.Point;
import com.wayto.android.BuildConfig;
import com.wayto.android.R;
import com.wayto.android.common.Constant;
import com.wayto.android.common.dialog.DialogFactory;
import com.wayto.android.common.eventbus.EventConstant;
import com.wayto.android.common.eventbus.NoticeEvent;
import com.wayto.android.common.handler.HandlerValue;
import com.wayto.android.base.BaseActivity;
import com.wayto.android.base.DataApplication;
import com.wayto.android.function.pictureFuncation.SelectPictureActivity;
import com.wayto.android.function.pictureFuncation.data.PictureEntity;
import com.wayto.android.function.mainFuncations.data.soure.MainRemoteRepo;
import com.wayto.android.function.mainFuncations.homeModule.HomeFragment;
import com.wayto.android.function.mainFuncations.mineModule.CheckAppVersion.CheckAppVersionContract;
import com.wayto.android.function.mainFuncations.mineModule.CheckAppVersion.CheckAppVersionPresenter;
import com.wayto.android.function.mainFuncations.mineModule.MineFragment;
import com.wayto.android.function.mainFuncations.mineModule.data.source.CheckAppVersionRemoteRepo;
import com.wayto.android.function.mainFuncations.missionModule.MissionFragment;
import com.wayto.android.function.mainFuncations.recordModule.RecordFragment;
import com.wayto.android.function.mainFuncations.trackModule.TrackFragment;
import com.wayto.android.receiver.PollingReceiver;
import com.wayto.android.service.MonitorService;
import com.wayto.android.utils.IActivityManage;
import com.wayto.android.utils.PermissionHelper;
import com.wayto.android.view.MainBottomNavigationBar;
import com.wayto.map.MapView;
import com.wayto.map.entity.MPointEntity;
import com.wayto.map.utils.ILngLatMercator;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations
 * @Description:主界面
 * @date 2016/11/22 15:40
 */

public class MainActivity extends BaseActivity implements MainBottomNavigationBar.BottomTabSelectedListener, MainContract.MainView, CheckAppVersionContract.View {
    private final String TAG = getClass().getSimpleName();

    /*模块标记*/
    private final int TAB_HOME = 0;
    private final int TAB_MISSION = 1;
    private final int TAB_TRACK = 2;
    private final int TAB_RECORD = 3;
    private final int TAB_MINE = 4;

    @BindView(R.id.main_container_FrameLayout)
    FrameLayout mainContainerFl;
    @BindView(R.id.main_bottom_navigationBar)
    MainBottomNavigationBar mainBottomNavigationBar;
    @BindView(R.id.main_mapView)
    MapView mapView;

    private MainPresenter mMainPresenter;
    private CheckAppVersionPresenter versionPresenter;

    /*Service*/
    private MonitorService monitorService;
    private ServiceConnection monitorConnection;
    private Messenger mClientMessenger;

    /*电量监听Receiver*/
    private PollingReceiver batteryReceiver;

    @Override
    protected void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            /*定位回调处理*/
            case HandlerValue.LOCATION_SUCCESS_KEY:
                AMapLocation location = (AMapLocation) msg.obj;
                if (location == null) {
                    return;
                }
                /*转成墨卡托坐标*/
                MPointEntity point = ILngLatMercator.lonLat2WebMercator(location.getLongitude(), location.getLatitude());
                /*定位点刷新*/
                mapView.updateCurrentLocation(new Point(point.getX(), point.getY()));
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        setToolbarCenterTitle(R.string.main_home_tab);
        setSwipeEnabled(false);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.unpause();
//        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.pause();
//        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.recycle();
        unbindMonitorService();
        unregisterReceiver(batteryReceiver);
    }

    /**
     * 初始化
     */
    private void init() {
        mClientMessenger = new Messenger(mHandler);
        batteryReceiver = new PollingReceiver();

        initBottomNavigationBar();
        initPresenter();

        addMapLayer();
        bindMonitorServer();
        registerBatteryReceiver();
    }

    /**
     * 初始化BottomNavigationBar
     */
    private void initBottomNavigationBar() {
        mainBottomNavigationBar.initConfig(this, R.id.main_container_FrameLayout);
        mainBottomNavigationBar.addTabItem(R.mipmap.icon_main_tab_home_pr, R.string.main_home_tab).addTabItem(R.mipmap.icon_main_tab_mission_pr, R.string.main_mission_tab).addTabItem(R.mipmap.icon_main_tab_loc_pr, R.string.main_track_tab).addTabItem(R.mipmap.icon_main_tab_record_pr, R.string.main_record_tab).addTabItem(R.mipmap.icon_main_tab_mine_pr, R.string.main_mine_tab);
        mainBottomNavigationBar.addFragment(HomeFragment.newInstance()).addFragment(MissionFragment.newInstance()).addFragment(TrackFragment.newInstance()).addFragment(RecordFragment.newInstance()).addFragment(MineFragment.newInstance());
        mainBottomNavigationBar.setTabSelectedListener(this);
        mainBottomNavigationBar.setFirstSelectedTab(TAB_TRACK);
    }

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        mMainPresenter = new MainPresenter(MainRemoteRepo.newInstance(), this);
        mMainPresenter.reqQiNiuToken();

        versionPresenter = new CheckAppVersionPresenter(CheckAppVersionRemoteRepo.newInstance(), this);
        versionPresenter.checkAppVersion();
    }

    /**
     * 注册电量监控Receiver
     */
    private void registerBatteryReceiver() {
        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, batteryFilter);
    }

    /**
     * 添加图层
     */
    public void addMapLayer() {
        /*设置图层*/
        mapView.addFeatureLayer(BuildConfig.DEVICE_MAP_LAYER);
    }

    /**
     * 启动定位服务
     */
    private void bindMonitorServer() {
        Intent intent = new Intent(this, MonitorService.class);
        monitorConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                monitorService = ((MonitorService.MonitorBinder) service).getService();
                /*Client与Service数据交互*/
                Messenger messenger = monitorService.getServiceMessenger();
                Message msg = Message.obtain();
                msg.what = MonitorService.ACTVITY_SEND_MESSENGER;
                msg.replyTo = mClientMessenger;
                try {
                    messenger.send(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                DataApplication.getInstance().setMonitorService(monitorService);
                /*定位权限检测*/
                if (PermissionHelper.checkPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION, Constant.REQUEST_ACCESS_COARSE_LOCATION)) {
                    /*服务启动*/
                    DataApplication.getInstance().startLocation(monitorService);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                monitorService = null;
            }
        };
        bindService(intent, monitorConnection, BIND_AUTO_CREATE);
    }

    /**
     * 停止定位服务
     */
    private void unbindMonitorService() {
        DataApplication.getInstance().stopLocationService();
        DataApplication.getInstance().destoryLocation();
        unbindService(monitorConnection);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
          /*将这一行注释掉，阻止activity保存fragment的状态*/
//        super.onSaveInstanceState(outState);
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case TAB_HOME:
                setToolbarCenterTitle(R.string.main_home_tab);
                mapView.setVisibility(View.VISIBLE);
                break;
            case TAB_MISSION:
                setToolbarCenterTitle(R.string.main_mission_tab);
                mapView.setVisibility(View.GONE);
                break;
            case TAB_TRACK:
                setToolbarCenterTitle(R.string.main_track_tab);
                mapView.setVisibility(View.VISIBLE);
                break;
            case TAB_RECORD:
                setToolbarCenterTitle(R.string.main_record_tab);
                mapView.setVisibility(View.GONE);
                break;
            case TAB_MINE:
                setToolbarCenterTitle(R.string.title_user_info);
                mapView.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showRequestFail() {

    }

    @Override
    public void showIsLatestVersion() {

    }

    @Override
    public void showCheckingDialog() {

    }

    @Override
    public void dismissCheckingDialog() {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constant.REQUEST_ACCESS_COARSE_LOCATION:/*定位权限启动成功*/
                 /*服务启动*/
                DataApplication.getInstance().startLocation(monitorService);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case SelectPictureActivity.SELECT_PICTURE_RESULT_CODE:/*修改头像回调*/
                List<PictureEntity> entities = (List<PictureEntity>) data.getExtras().getSerializable("result");
                if (entities == null || entities.size() == 0) {
                    return;
                }
                String headPath = entities.get(0).getUrl();
                NoticeEvent event = new NoticeEvent();
                event.setFlag(EventConstant.UPDATE_USER_HEAD_KEY);
                event.setObj(headPath);
                EventBus.getDefault().post(event);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showExitDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 退出Dialog
     */
    private void showExitDialog() {
        DialogFactory.showMsgDialog(this, getString(R.string.dialog_title_exit), getString(R.string.exit_msg) + getString(R.string.app_name) + "?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                IActivityManage.getInstance().exit();
                System.exit(0);
            }
        });
    }
}

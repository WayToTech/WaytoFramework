package com.wayto.map;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.android.runtime.ArcGISRuntime;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Point;
import com.wayto.android.map.greedao.DaoMaster;
import com.wayto.android.map.greedao.DaoSession;
import com.wayto.map.view.CompassView;
import com.wayto.map.common.Constant;
import com.wayto.map.google.GoogleMapLayer;
import com.wayto.map.google.GoogleMapLayerTypes;

/**
 * @Package: com.yunwei.map.widget
 * @Description:ArcGis 基础图层
 * @author: Aaron
 * @date: 2016-05-31
 * @Time: 20:43
 * @version: V1.0
 */
public class ArcGisBaseMapView extends MapView implements OnStatusChangedListener {
    private final String TAG = getClass().getSimpleName();

    private final String CLIENT_ID="1eFHW78avlnRUPHm";

    protected ImageView zoomoutIv, zoominIv, locationIv;
    protected TextView switchLayerBtn;
    private LinearLayout zoomoutLayout, zoominLayout;
    protected LinearLayout btmLayout;
    protected ImageView layerIV;
    protected RelativeLayout proLayout;
    protected RelativeLayout locationLayout;

    public boolean isFlow = false;
    private boolean isImageLayer = true;

    protected Point point;
    protected GoogleMapLayer terrainLayer;
    protected GoogleMapLayer imageLayer;
    protected GoogleMapLayer annotationLayer;

    private static DaoSession daoSession;

    // 指南针
    private CompassView mPointerSmall;// 指南针view
    private final float MAX_ROATE_DEGREE = 1.0f;// 最多旋转一周，即360°
    private SensorManager mSensorManager;// 传感器管理对象
    private Sensor mOrientationSensor;// 传感器对象
    private float mDirection;// 当前浮点方向
    private float mTargetDirection;// 目标浮点方向
    private AccelerateInterpolator mInterpolator;// 动画从开始到结束，变化率是一个加速的过程,就是一个动画速率
    private boolean mStopDrawing;// 是否停止指南针旋转的标志位

    protected double x = 1.2697781415967792E7;
    protected double y = 2579888.187931014;

    protected MapViewClickListener listener;

    public ArcGisBaseMapView(Context context) {
        super(context);
        initDaoSession(context);
        initView();
        initDefaultConfig();
    }

    public ArcGisBaseMapView(Context context, AttributeSet attri) {
        super(context, attri);
        initDaoSession(context);
        initView();
        initDefaultConfig();
    }

    /**
     * 初始化地图的默认配制
     */
    private void initDefaultConfig() {
        setMapBackground(0xf5f5f5f5, Color.TRANSPARENT, 0, 0);
        ArcGISRuntime.setClientId(Constant.ARCGIS_KEY);
        terrainLayer = new GoogleMapLayer(GoogleMapLayerTypes.TERRAIN_GOOGLE_MAP, getContext());
        imageLayer = new GoogleMapLayer(GoogleMapLayerTypes.IMAGE_GOOGLE_MAP, getContext());
        annotationLayer = new GoogleMapLayer(GoogleMapLayerTypes.ANNOTATION_GOOGLE_MAP, getContext());

        addLayer(imageLayer);
        addLayer(annotationLayer);
        addLayer(terrainLayer);

        //设置图层显示
        terrainLayer.setVisible(true);
        imageLayer.setVisible(false);
        annotationLayer.setVisible(false);

        //设置手势旋转地图
//        setAllowRotationByPinch(true);

        setOnStatusChangedListener(this);
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.map_base_layout, null);
        zoominLayout = (LinearLayout) view.findViewById(R.id.map_zoomin_layout);
        zoomoutLayout = (LinearLayout) view.findViewById(R.id.map_zoomout_layout);
        btmLayout = (LinearLayout) view.findViewById(R.id.map_btm_layout);
        locationIv = (ImageView) view.findViewById(R.id.map_loc_iv);
        proLayout = (RelativeLayout) view.findViewById(R.id.map_cop_layout);
        switchLayerBtn = (TextView) view.findViewById(R.id.map_outside_switch_iv);

        zoominIv = (ImageView) view.findViewById(R.id.map_zoomin_iv);
        zoomoutIv = (ImageView) view.findViewById(R.id.map_zoomout_iv);

        layerIV = (ImageView) view.findViewById(R.id.map_layer_iv);
        mPointerSmall = (CompassView) view.findViewById(R.id.map_compass_iv);
        locationLayout = (RelativeLayout) view.findViewById(R.id.map_loc_layout);
        //图层切换
        layerIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageLayer) {
                    layerIV.setImageResource(R.mipmap.homeicon_image_toggle0);
                    terrainLayer.setVisible(false);
                    imageLayer.setVisible(true);
                    annotationLayer.setVisible(true);
                    isImageLayer = false;
                } else {
                    layerIV.setImageResource(R.mipmap.homeicon_image_toggle1);
                    terrainLayer.setVisible(true);
                    imageLayer.setVisible(false);
                    annotationLayer.setVisible(false);
                    isImageLayer = true;
                }
            }
        });
        //放大
        zoominLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomin();
                zoominLayout.setClickable(true);
                zoomoutLayout.setClickable(true);

            }
        });
        //缩小
        zoomoutLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomout();
                zoominLayout.setClickable(true);
                zoomoutLayout.setClickable(true);
            }
        });

        locationIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFlow) {
                    setExtent(point);
                    setScale(GoogleMapLayer.scales[16]);
                    isFlow = true;
                    locationIv.setImageResource(R.mipmap.custom_follow);
                } else {
                    isFlow = false;
                    locationIv.setImageResource(R.mipmap.custom_loc);
                }
                if (listener!=null){
                    listener.onClickFlow();
                }
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        addView(view);
        //去掉水印
        ArcGISRuntime.setClientId(CLIENT_ID);
        //初始化传感器
        initSensor();
    }

    // 初始化方向传感器
    private void initSensor() {
        // sensor manager
        mSensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        mOrientationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mSensorManager.registerListener(mOrientationSensorEventListener, mOrientationSensor, SensorManager.SENSOR_DELAY_GAME);
        mDirection = 0.0f;// 初始化起始方向
        mTargetDirection = 0.0f;// 初始化目标方向
        mInterpolator = new AccelerateInterpolator();// 实例化加速动画对象
        mStopDrawing = true;
        mStopDrawing = false;
        new Handler().postDelayed(mCompassViewUpdater, 20);// 20毫秒执行一次更新指南针图片旋转
    }

    /**
     * 指南针
     * 这个是更新指南针旋转的线程，handler的灵活使用，每20毫秒检测方向变化值，对应更新指南针旋转
     */
    protected Runnable mCompassViewUpdater = new Runnable() {
        @Override
        public void run() {
            if (!mStopDrawing) {
                if (mDirection != mTargetDirection) {

                    // calculate the short routine
                    float to = mTargetDirection;
                    if (to - mDirection > 180) {
                        to -= 360;
                    } else if (to - mDirection < -180) {
                        to += 360;
                    }

                    // limit the max speed to MAX_ROTATE_DEGREE
                    float distance = to - mDirection;
                    if (Math.abs(distance) > MAX_ROATE_DEGREE) {
                        distance = distance > 0 ? MAX_ROATE_DEGREE
                                : (-1.0f * MAX_ROATE_DEGREE);
                    }
                    // need to slow down if the distance is short
                    mDirection = normalizeDegree(mDirection
                            + ((to - mDirection) * mInterpolator
                            .getInterpolation(Math.abs(distance) > MAX_ROATE_DEGREE ? 0.4f
                                    : 0.3f)));// 用了一个加速动画去旋转图片，很细致
                    // mPointer.updateDirection(mDirection);// 更新指南针旋转
                    if (mPointerSmall != null && mDirection > 0) {
                        mPointerSmall.updateDirection(mDirection);// 更新指南针旋转
                    }
                }
                new Handler().postDelayed(mCompassViewUpdater, 20);// 20毫米后重新执行自己，比定时器好
            }
        }
    };

    // 调整方向传感器获取的值
    private float normalizeDegree(float degree) {
        return (degree + 720) % 360;
    }

    // 方向传感器变化监听
    private SensorEventListener mOrientationSensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float direction = event.values[0] * -1.0f;
            mTargetDirection = normalizeDegree(direction);// 赋值给全局变量，让指南针旋转
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    public void onStatusChanged(Object o, STATUS status) {
        if (status == STATUS.LAYER_LOADED) {
            setExtent(new Point(x, y));
            setScale(GoogleMapLayer.scales[16]);
        }
    }


    /**
     * DAO对象
     *
     * @return
     */
    private static DaoSession initDaoSession(Context context) {
        if (daoSession == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), "MapLayer_db", null);
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public interface MapViewClickListener{
        void onClickFlow();
    }
}

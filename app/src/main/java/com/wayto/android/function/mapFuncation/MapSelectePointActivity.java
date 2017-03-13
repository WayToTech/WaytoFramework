package com.wayto.android.function.mapFuncation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.esri.android.map.event.OnPanListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Point;
import com.wayto.android.R;
import com.wayto.android.base.BaseActivity;
import com.wayto.android.base.DataApplication;
import com.wayto.android.function.mapFuncation.data.RegeoEntity;
import com.wayto.android.utils.ISkipActivityUtil;
import com.wayto.map.ArcGisBaseMapView;
import com.wayto.map.MapView;
import com.wayto.map.entity.LngLatEntity;
import com.wayto.map.entity.MPointEntity;
import com.wayto.map.google.GoogleMapLayer;
import com.wayto.map.utils.ILngLatMercator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mapFuncation
 * @Description:地图选点
 * @date 2017/2/20 16:53
 */

public class MapSelectePointActivity extends BaseActivity implements OnStatusChangedListener, OnPanListener, RegeoContract.View, ArcGisBaseMapView.MapViewClickListener {
    public static final int REQUEST_CODE_VALUE = 0x908;
    public static final int RESULT_CODE_VALUE = 0x909;
    public static final String RESULT_FLAG_KEY = "result";

    @BindView(R.id.SelectePointToMap_mapView)
    MapView mMapView;
    @BindView(R.id.SelectePointToMap_address_textView)
    TextView mAddressTextView;
    @BindView(R.id.SelectePointToMap_selecte_point_imageView)
    ImageView mSelectePointImageView;

    private double currentLng;
    private double currentLat;
    private double lng;
    private double lat;
    private MPointEntity point;

    private RegeoPresenter regeoPresenter;

    public static void startInternt(Activity activity) {
        ISkipActivityUtil.startIntentForResult(activity, MapSelectePointActivity.class, REQUEST_CODE_VALUE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_selecte_point);
        setToolbarTitle(R.string.title_selecte_point);
        setToolbarRightText(R.string.title_comfir_point);
        ButterKnife.bind(this);
        currentLng = lng = DataApplication.getInstance().getMonitorService().getLocation().getLongitude();
        currentLat = lat = DataApplication.getInstance().getMonitorService().getLocation().getLatitude();
        point = ILngLatMercator.lonLat2WebMercator(lng, lat);
        regeoPresenter = new RegeoPresenter(this);
        initUI();
        mMapView.setOnStatusChangedListener(this);
        mMapView.setOnPanListener(this);
        mMapView.setClickListener(this);
        regeoPresenter.regeoLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.unpause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.recycle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.pause();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
         /*设置当前位置*/
        mMapView.updateCurrentLocation(new Point(point.getX(), point.getY()));
    }

    @Override
    public void onStatusChanged(Object o, STATUS status) {
        if (status == STATUS.LAYER_LOADED) {
            /*设置地图上中心点及Scale*/
            Point point = new Point(this.point.getX(), this.point.getY());
            mMapView.setExtent(point);
            mMapView.setScale(GoogleMapLayer.scales[18]);
        }
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        Intent intent = new Intent();
        intent.putExtra(RESULT_FLAG_KEY, mAddressTextView.getText().toString());
        setResult(RESULT_CODE_VALUE, intent);
        finish();
    }

    @Override
    public void prePointerMove(float v, float v1, float v2, float v3) {

    }

    @Override
    public void postPointerMove(float v, float v1, float v2, float v3) {

    }

    @Override
    public void prePointerUp(float v, float v1, float v2, float v3) {

    }

    @Override
    public void postPointerUp(float v, float v1, float v2, float v3) {
        Point point = mMapView.getCenter();
        LngLatEntity entity = ILngLatMercator.WebMercator2lonLat(point.getX(), point.getY());
        lng = entity.getLNG();
        lat = entity.getLAT();
        startRegeo();
    }

    @Override
    public void onClickFlow() {
        mMapView.setLocationBtnBag(com.wayto.map.R.mipmap.custom_loc);
        mMapView.setFlow(false);
        lng = currentLng;
        lat = currentLat;
        startRegeo();
    }

    /**
     * 开始执行
     */
    private void startRegeo() {
        TranslateAnimation alphaAnimation2 = new TranslateAnimation(0F, 0F, 0F, -50F);
        alphaAnimation2.setDuration(200);
        alphaAnimation2.setRepeatCount(0);  //为重复执行的次数。如果设置为n，则动画将执行n+1次。INFINITE为无限制播放
//        alphaAnimation2.setRepeatMode(Animation.RESTART);
//        alphaAnimation2.setInterpolator(new LinearInterpolator());//动画结束的时候弹起
        mSelectePointImageView.setAnimation(alphaAnimation2);
        alphaAnimation2.start();

        regeoPresenter.regeoLocation();
    }

    @Override
    public void onRegeoStart() {

    }

    @Override
    public void onRegeoEnd() {

    }

    @Override
    public void onRegeoSuccess(RegeoEntity entity) {
        if (entity != null) {
            mAddressTextView.setText(entity.getRegeocode().getFormatted_address());
        }
    }

    @Override
    public void onRegeoFailure(String error) {

    }

    @Override
    public double getLng() {
        return lng;
    }

    @Override
    public double getLat() {
        return lat;
    }
}

package com.wayto.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.esri.android.map.GraphicsLayer;
import com.esri.core.geometry.Point;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.wayto.map.common.Constant;
import com.wayto.map.google.GoogleMapLayer;
import com.wayto.map.utils.MSpfUtil;

import java.util.Map;

/**
 * @Package: com.yunwei.map.widget
 * @Description:定位图层、设置点线图层
 * @author: Aaron
 * @date: 2016-06-01
 * @Time: 19:38
 * @version: V1.0
 */
public class ArcGisLocationLayer extends ArcGisBaseMapView {

    private final String TAG = getClass().getSimpleName();
    /*定位图块*/
    private GraphicsLayer mGraphicsLayerLoc = null;
    /*足迹采集图层*/
    private GraphicsLayer mGraphicsLayerTrack = null;
    /*点图层*/
    private GraphicsLayer mGraphicsLayerPoint = null;

    private int graphicId = -1;
    //记录是否开启足迹
    public boolean isTrackRecording = false;

    private boolean isAddLocIcon = false;

    public ArcGisLocationLayer(Context context) {
        super(context, null);
    }

    public ArcGisLocationLayer(Context context, AttributeSet attr) {
        super(context, attr);
        initLocationLayer();
    }

    /**
     * 初始化图层
     */
    private void initLocationLayer() {
        mGraphicsLayerLoc = new GraphicsLayer();
        mGraphicsLayerTrack = new GraphicsLayer();
        mGraphicsLayerPoint = new GraphicsLayer();

        addLayer(mGraphicsLayerTrack);
        addLayer(mGraphicsLayerPoint);
    }

    /**
     * 更新当前位置
     *
     * @param point
     */
    public GraphicsLayer updateCurrentLocation(Point point) {
        this.point = point;
        if (!isAddLocIcon) {
            addLayer(mGraphicsLayerLoc);
            isAddLocIcon = true;
        }
        if (isFlow || isTrackRecording) {
            setExtent(point);
            setScale(getScale());
            MSpfUtil.setValue(getContext(), Constant.LNG_FLAG, point.getX());
            MSpfUtil.setValue(getContext(), Constant.LAT_FLAG, point.getY());
        }
        addLocation(point, mGraphicsLayerLoc, R.mipmap.main_icon_follow);

        return mGraphicsLayerLoc;
    }

    /**
     * 更新当前位置
     *
     * @param point
     * @param resId 定位图标
     */
    public GraphicsLayer updateCurrentLocation(Point point, int resId) {
        this.point = point;
        if (!isAddLocIcon) {
            addLayer(mGraphicsLayerLoc);
            isAddLocIcon = true;
        }
        if (isFlow || isTrackRecording) {
            setExtent(point);
            setScale(getScale());
            MSpfUtil.setValue(getContext(), Constant.LNG_FLAG, point.getX());
            MSpfUtil.setValue(getContext(), Constant.LAT_FLAG, point.getY());
        }
        addLocation(point, mGraphicsLayerLoc, resId);

        return mGraphicsLayerLoc;
    }

    /**
     * 足迹采集定位点
     *
     * @param point
     */
    public GraphicsLayer updateTrackRecordLocation(Point point) {
        setExtent(point);
        setScale(GoogleMapLayer.scales[16]);
        addLocation(point, mGraphicsLayerTrack, R.mipmap.main_icon_follow);

        return mGraphicsLayerTrack;
    }

    public void addLocation(Point point, GraphicsLayer graphicsLayer, int resId) {
        Bitmap bmpStart = BitmapFactory.decodeResource(getResources(), resId);
        Drawable drawable = new BitmapDrawable(getResources(), bmpStart);
        addPictureMarkerSimple(point, graphicsLayer, drawable, null);
    }

    /**
     * @param mPoint
     * @param graphicsLayer
     * @param drawable
     * @param attributes    附带属性
     * @return
     * @author duyang
     * @version V1.1
     */
    public Graphic addPictureMarkerSimple(Point mPoint, GraphicsLayer graphicsLayer, Drawable drawable, Map<String, Object> attributes) {
        PictureMarkerSymbol picSys = new PictureMarkerSymbol(drawable);
        Graphic graphic = null;
        if (graphicsLayer != null && mPoint != null) {
            graphic = new Graphic(mPoint, picSys, attributes, 0);
            if (graphicId != -1) {
                mGraphicsLayerLoc.updateGraphic(graphicId, graphic);
            } else {
                graphicId = graphicsLayer.addGraphic(graphic);
                setScale(GoogleMapLayer.scales[16]);
            }
        }
        return graphic;
    }

    /**
     * 是否足迹跟随
     *
     * @param TrackRecording
     */
    public void setTrackRecording(boolean TrackRecording) {
        this.isFlow = TrackRecording;
    }
}

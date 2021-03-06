package com.wayto.map.google;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.esri.android.map.TiledServiceLayer;
import com.esri.core.geometry.Envelope;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.internal.io.handler.a;
import com.wayto.android.map.greedao.MapLayer1;
import com.wayto.android.map.greedao.MapLayer10;
import com.wayto.android.map.greedao.MapLayer10Dao;
import com.wayto.android.map.greedao.MapLayer11;
import com.wayto.android.map.greedao.MapLayer11Dao;
import com.wayto.android.map.greedao.MapLayer12;
import com.wayto.android.map.greedao.MapLayer12Dao;
import com.wayto.android.map.greedao.MapLayer13;
import com.wayto.android.map.greedao.MapLayer13Dao;
import com.wayto.android.map.greedao.MapLayer14;
import com.wayto.android.map.greedao.MapLayer14Dao;
import com.wayto.android.map.greedao.MapLayer15;
import com.wayto.android.map.greedao.MapLayer15Dao;
import com.wayto.android.map.greedao.MapLayer16;
import com.wayto.android.map.greedao.MapLayer16Dao;
import com.wayto.android.map.greedao.MapLayer17;
import com.wayto.android.map.greedao.MapLayer17Dao;
import com.wayto.android.map.greedao.MapLayer18;
import com.wayto.android.map.greedao.MapLayer18Dao;
import com.wayto.android.map.greedao.MapLayer19;
import com.wayto.android.map.greedao.MapLayer19Dao;
import com.wayto.android.map.greedao.MapLayer1Dao;
import com.wayto.android.map.greedao.MapLayer2;
import com.wayto.android.map.greedao.MapLayer2Dao;
import com.wayto.android.map.greedao.MapLayer3;
import com.wayto.android.map.greedao.MapLayer3Dao;
import com.wayto.android.map.greedao.MapLayer4;
import com.wayto.android.map.greedao.MapLayer4Dao;
import com.wayto.android.map.greedao.MapLayer5;
import com.wayto.android.map.greedao.MapLayer5Dao;
import com.wayto.android.map.greedao.MapLayer6;
import com.wayto.android.map.greedao.MapLayer6Dao;
import com.wayto.android.map.greedao.MapLayer7;
import com.wayto.android.map.greedao.MapLayer7Dao;
import com.wayto.android.map.greedao.MapLayer8;
import com.wayto.android.map.greedao.MapLayer8Dao;
import com.wayto.android.map.greedao.MapLayer9;
import com.wayto.android.map.greedao.MapLayer9Dao;
import com.wayto.map.MapView;
import com.wayto.map.R;
import com.wayto.map.utils.MLog;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @Package: com.yunwei.map.tiled.google
 * @Description:google地图图层下载Service
 * @author: Aaron
 * @date: 2016-06-01
 * @Time: 14:31
 * @version: V1.0
 */
public class GoogleMapLayer extends TiledServiceLayer {
    private final String TAG = getClass().getSimpleName();

    private Context context;
    private int mGoogleMapLayerType;
    private int dpi = 96;
    private int maxLevel = 19;
    private int minLevel = 0;
    private int tileHeight = 256;
    private int tileWidth = 256;

    private Point origin = new Point(-20037508.342787, 20037508.342787);
    private double[] resolutions = {156543.03392800014D, 78271.516963999937D,
            39135.758482000092D, 19567.879240999919D, 9783.9396204999593D,
            4891.9698102499797D, 2445.9849051249898D, 1222.9924525624949D,
            611.49622628138002D, 305.74811314055802D, 152.874056570411D,
            76.437028285073197D, 38.218514142536598D, 19.109257071268299D,
            9.55462853563415D, 4.77731426794937D, 2.388657133974685D,
            1.19432856685505D, 0.5971642835598172D, 0.2985821416476167D};

    public static double[] scales = {591657527.591555D, 295828763.79577702D,
            147914381.89788899D, 73957190.948944002D, 36978595.474472001D,
            18489297.737236001D, 9244648.8686180003D, 4622324.4343090001D,
            2311162.2171550002D, 1155581.108577D, 577790.55428899999D,
            288895.27714399999D, 144447.638572D, 72223.819285999998D,
            36111.909642999999D, 18055.954822D, 9027.9774109999998D,
            4513.9887049999998D, 2256.994353D, 1128.4971760000001D};

    public GoogleMapLayer(int paramInt, Context context) {
        super(true);
        this.context = context;
        this.mGoogleMapLayerType = paramInt;
        initLayer();
    }

    @Override
    protected void initLayer() {
        if (getID() == 0) {
            this.nativeHandle = create();
            changeStatus(com.esri.android.map.event.OnStatusChangedListener.STATUS.fromInt(-1000));
        }
        setDefaultSpatialReference(SpatialReference.create(102113));
        setFullExtent(new Envelope(-22041257.773878001D, -32673939.672751699D, 22041257.773878001D, 20851350.0432886D));
        //TileInfo
        setTileInfo(new TileInfo(this.origin, this.scales, this.resolutions, this.scales.length, this.dpi, this.tileWidth, this.tileHeight));
        super.initLayer();
    }

    @Override
    protected byte[] getTile(int level, int col, int row) throws Exception {
        if (level > maxLevel || level < minLevel) {
            return new byte[0];
        }
        String s = "Galileo".substring(0, ((3 * col + row) % 8));
        String url = "";
        switch (mGoogleMapLayerType) {
            case GoogleMapLayerTypes.IMAGE_GOOGLE_MAP: //影像
                url = "http://mt" + (col % 4) + ".google.cn/vt/lyrs=s&hl=zh-CN&gl=cn&" + "x=" + col + "&" + "y=" + row + "&" + "z=" + level + "&" + "s=" + s;
                break;
            case GoogleMapLayerTypes.VECTOR_GOOGLE_MAP:
                url = "http://mt" + (col % 4) + ".google.cn/vt/lyrs=m@158000000&hl=zh-CN&gl=cn&" + "x=" + col + "&" + "y=" + row + "&" + "z=" + level + "&" + "s=" + s;
                break;
            case GoogleMapLayerTypes.TERRAIN_GOOGLE_MAP: //.com获取不到切片，要改成.cn
                url = "http://mt" + (col % 4) + ".google.cn/vt/lyrs=t@131,r@227000000&hl=zh-CN&gl=cn&" + "x=" + col + "&" + "y=" + row + "&" + "z=" + level + "&" + "s=" + s;
                break;
            case GoogleMapLayerTypes.ANNOTATION_GOOGLE_MAP: //道路,POI
                url = "http://mt" + (col % 4) + ".google.cn/vt/imgtp=png32&lyrs=h@169000000&hl=zh-CN&gl=cn&" + "x=" + col + "&" + "y=" + row + "&" + "z=" + level + "&" + "s=" + s;
                break;
        }
        byte[] bytes = queryTileToLocal(mGoogleMapLayerType, level, col, row);
        if (bytes == null) {
            MLog.i(TAG, "0本地无缓存，通过网络获取下载！");
            Map<String, String> map = null;
            bytes = a.a(url, map);

            // 切片写入本地缓存，考虑是否开启子线程,并且对图片进行适当压缩
            saveTileToLocal(mGoogleMapLayerType, level, col, row, bytes);

        } else {
            MLog.d(TAG, "1本地有缓存！");
        }

        if (bytes == null) {
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.no_cache);
            MLog.e(TAG, "no_cache无缓存");
            return bitmap2Bytes(bmp);
        }
        return bytes;
    }

    /**
     * 将Bitmap转成Byte[]
     *
     * @param bmp
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bmp) {
        if (bmp != null) {
            ByteArrayOutputStream baosArrayOutputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, baosArrayOutputStream);
            return baosArrayOutputStream.toByteArray();
        }
        return null;
    }

    /**
     * 保存
     *
     * @param layerType
     * @param level
     * @param col
     * @param row
     * @param bytes
     */
    private void saveTileToLocal(int layerType, int level, int col, int row, byte[] bytes) {
        switch (level) {
            case 1:
                MapLayer1 layer1 = new MapLayer1(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer1Dao().insert(layer1);
                break;
            case 2:
                MapLayer2 layer2 = new MapLayer2(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer2Dao().insert(layer2);
                break;
            case 3:
                MapLayer3 layer3 = new MapLayer3(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer3Dao().insert(layer3);
                break;
            case 4:
                MapLayer4 layer4 = new MapLayer4(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer4Dao().insert(layer4);
                break;
            case 5:
                MapLayer5 layer5 = new MapLayer5(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer5Dao().insert(layer5);
                break;
            case 6:
                MapLayer6 layer6 = new MapLayer6(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer6Dao().insert(layer6);
                break;
            case 7:
                MapLayer7 layer7 = new MapLayer7(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer7Dao().insert(layer7);
                break;
            case 8:
                MapLayer8 layer8 = new MapLayer8(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer8Dao().insert(layer8);
                break;
            case 9:
                MapLayer9 layer9 = new MapLayer9(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer9Dao().insert(layer9);
                break;
            case 10:
                MapLayer10 layer10 = new MapLayer10(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer10Dao().insert(layer10);
                break;
            case 11:
                MapLayer11 layer11 = new MapLayer11(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer11Dao().insert(layer11);
                break;
            case 12:
                MapLayer12 layer12 = new MapLayer12(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer12Dao().insert(layer12);
                break;
            case 13:
                MapLayer13 layer13 = new MapLayer13(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer13Dao().insert(layer13);
                break;
            case 14:
                MapLayer14 layer14 = new MapLayer14(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer14Dao().insert(layer14);
                break;
            case 15:
                MapLayer15 layer15 = new MapLayer15(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer15Dao().insert(layer15);
                break;
            case 16:
                MapLayer16 layer16 = new MapLayer16(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer16Dao().insert(layer16);
                break;
            case 17:
                MapLayer17 layer17 = new MapLayer17(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer17Dao().insert(layer17);
                break;
            case 18:
                MapLayer18 layer18 = new MapLayer18(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer18Dao().insert(layer18);
                break;
            case 19:
                MapLayer19 layer19 = new MapLayer19(layerType, level, col, row, bytes);
                MapView.getDaoSession().getMapLayer19Dao().insert(layer19);
                break;
        }
    }

    /**
     * 查询
     *
     * @param layerType
     * @param level
     * @param col
     * @param row
     * @return
     */
    private byte[] queryTileToLocal(int layerType, int level, int col, int row) {
        switch (level) {
            case 1:
                List<MapLayer1> mLists1 = MapView.getDaoSession().getMapLayer1Dao().queryBuilder()
                        .where(MapLayer1Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer1Dao.Properties.Level.eq(level))
                        .where(MapLayer1Dao.Properties.Col.eq(col))
                        .where(MapLayer1Dao.Properties.Row.eq(row)).list();
                if (mLists1 != null && mLists1.size() > 0) {
                    for (MapLayer1 entity : mLists1) {
                        return entity.getLayer();
                    }
                }
            case 2:
                List<MapLayer2> mLists2 = MapView.getDaoSession().getMapLayer2Dao().queryBuilder()
                        .where(MapLayer2Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer2Dao.Properties.Level.eq(level))
                        .where(MapLayer2Dao.Properties.Col.eq(col))
                        .where(MapLayer2Dao.Properties.Row.eq(row)).list();
                if (mLists2 != null && mLists2.size() > 0) {
                    for (MapLayer2 entity : mLists2) {
                        return entity.getLayer();
                    }
                }
            case 3:
                List<MapLayer3> mLists3 = MapView.getDaoSession().getMapLayer3Dao().queryBuilder()
                        .where(MapLayer3Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer3Dao.Properties.Level.eq(level))
                        .where(MapLayer3Dao.Properties.Col.eq(col))
                        .where(MapLayer3Dao.Properties.Row.eq(row)).list();
                if (mLists3 != null && mLists3.size() > 0) {
                    for (MapLayer3 entity : mLists3) {
                        return entity.getLayer();
                    }
                }
            case 4:
                List<MapLayer4> mLists4 = MapView.getDaoSession().getMapLayer4Dao().queryBuilder()
                        .where(MapLayer4Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer4Dao.Properties.Level.eq(level))
                        .where(MapLayer4Dao.Properties.Col.eq(col))
                        .where(MapLayer4Dao.Properties.Row.eq(row)).list();
                if (mLists4 != null && mLists4.size() > 0) {
                    for (MapLayer4 entity : mLists4) {
                        return entity.getLayer();
                    }
                }
            case 5:
                List<MapLayer5> mLists5 = MapView.getDaoSession().getMapLayer5Dao().queryBuilder()
                        .where(MapLayer5Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer5Dao.Properties.Level.eq(level))
                        .where(MapLayer5Dao.Properties.Col.eq(col))
                        .where(MapLayer5Dao.Properties.Row.eq(row)).list();
                if (mLists5 != null && mLists5.size() > 0) {
                    for (MapLayer5 entity : mLists5) {
                        return entity.getLayer();
                    }
                }
            case 6:
                List<MapLayer6> mLists6 = MapView.getDaoSession().getMapLayer6Dao().queryBuilder()
                        .where(MapLayer6Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer6Dao.Properties.Level.eq(level))
                        .where(MapLayer6Dao.Properties.Col.eq(col))
                        .where(MapLayer6Dao.Properties.Row.eq(row)).list();
                if (mLists6 != null && mLists6.size() > 0) {
                    for (MapLayer6 entity : mLists6) {
                        return entity.getLayer();
                    }
                }
            case 7:
                List<MapLayer7> mLists7 = MapView.getDaoSession().getMapLayer7Dao().queryBuilder()
                        .where(MapLayer7Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer7Dao.Properties.Level.eq(level))
                        .where(MapLayer7Dao.Properties.Col.eq(col))
                        .where(MapLayer7Dao.Properties.Row.eq(row)).list();
                if (mLists7 != null && mLists7.size() > 0) {
                    for (MapLayer7 entity : mLists7) {
                        return entity.getLayer();
                    }
                }
            case 8:
                List<MapLayer8> mLists8 = MapView.getDaoSession().getMapLayer8Dao().queryBuilder()
                        .where(MapLayer8Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer8Dao.Properties.Level.eq(level))
                        .where(MapLayer8Dao.Properties.Col.eq(col))
                        .where(MapLayer8Dao.Properties.Row.eq(row)).list();
                if (mLists8 != null && mLists8.size() > 0) {
                    for (MapLayer8 entity : mLists8) {
                        return entity.getLayer();
                    }
                }
            case 9:
                List<MapLayer9> mLists9 = MapView.getDaoSession().getMapLayer9Dao().queryBuilder()
                        .where(MapLayer9Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer9Dao.Properties.Level.eq(level))
                        .where(MapLayer9Dao.Properties.Col.eq(col))
                        .where(MapLayer9Dao.Properties.Row.eq(row)).list();
                if (mLists9 != null && mLists9.size() > 0) {
                    for (MapLayer9 entity : mLists9) {
                        return entity.getLayer();
                    }
                }
            case 10:
                List<MapLayer10> mLists10 = MapView.getDaoSession().getMapLayer10Dao().queryBuilder()
                        .where(MapLayer10Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer10Dao.Properties.Level.eq(level))
                        .where(MapLayer10Dao.Properties.Col.eq(col))
                        .where(MapLayer10Dao.Properties.Row.eq(row)).list();
                if (mLists10 != null && mLists10.size() > 0) {
                    for (MapLayer10 entity : mLists10) {
                        return entity.getLayer();
                    }
                }
            case 11:
                List<MapLayer11> mLists11 = MapView.getDaoSession().getMapLayer11Dao().queryBuilder()
                        .where(MapLayer11Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer11Dao.Properties.Level.eq(level))
                        .where(MapLayer11Dao.Properties.Col.eq(col))
                        .where(MapLayer11Dao.Properties.Row.eq(row)).list();
                if (mLists11 != null && mLists11.size() > 0) {
                    for (MapLayer11 entity : mLists11) {
                        return entity.getLayer();
                    }
                }
            case 12:
                List<MapLayer12> mLists12 = MapView.getDaoSession().getMapLayer12Dao().queryBuilder()
                        .where(MapLayer12Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer12Dao.Properties.Level.eq(level))
                        .where(MapLayer12Dao.Properties.Col.eq(col))
                        .where(MapLayer12Dao.Properties.Row.eq(row)).list();
                if (mLists12 != null && mLists12.size() > 0) {
                    for (MapLayer12 entity : mLists12) {
                        return entity.getLayer();
                    }
                }
            case 13:
                List<MapLayer13> mLists13 = MapView.getDaoSession().getMapLayer13Dao().queryBuilder()
                        .where(MapLayer13Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer13Dao.Properties.Level.eq(level))
                        .where(MapLayer13Dao.Properties.Col.eq(col))
                        .where(MapLayer13Dao.Properties.Row.eq(row)).list();
                if (mLists13 != null && mLists13.size() > 0) {
                    for (MapLayer13 entity : mLists13) {
                        return entity.getLayer();
                    }
                }
            case 14:
                List<MapLayer14> mLists14 = MapView.getDaoSession().getMapLayer14Dao().queryBuilder()
                        .where(MapLayer14Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer14Dao.Properties.Level.eq(level))
                        .where(MapLayer14Dao.Properties.Col.eq(col))
                        .where(MapLayer14Dao.Properties.Row.eq(row)).list();
                if (mLists14 != null && mLists14.size() > 0) {
                    for (MapLayer14 entity : mLists14) {
                        return entity.getLayer();
                    }
                }
            case 15:
                List<MapLayer15> mLists15 = MapView.getDaoSession().getMapLayer15Dao().queryBuilder()
                        .where(MapLayer15Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer15Dao.Properties.Level.eq(level))
                        .where(MapLayer15Dao.Properties.Col.eq(col))
                        .where(MapLayer15Dao.Properties.Row.eq(row)).list();
                if (mLists15 != null && mLists15.size() > 0) {
                    for (MapLayer15 entity : mLists15) {
                        return entity.getLayer();
                    }
                }
            case 16:
                List<MapLayer16> mLists16 = MapView.getDaoSession().getMapLayer16Dao().queryBuilder()
                        .where(MapLayer16Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer16Dao.Properties.Level.eq(level))
                        .where(MapLayer16Dao.Properties.Col.eq(col))
                        .where(MapLayer16Dao.Properties.Row.eq(row)).list();
                if (mLists16 != null && mLists16.size() > 0) {
                    for (MapLayer16 entity : mLists16) {
                        return entity.getLayer();
                    }
                }
            case 17:
                List<MapLayer17> mLists17 = MapView.getDaoSession().getMapLayer17Dao().queryBuilder()
                        .where(MapLayer17Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer17Dao.Properties.Level.eq(level))
                        .where(MapLayer17Dao.Properties.Col.eq(col))
                        .where(MapLayer17Dao.Properties.Row.eq(row)).list();
                if (mLists17 != null && mLists17.size() > 0) {
                    for (MapLayer17 entity : mLists17) {
                        return entity.getLayer();
                    }
                }
            case 18:
                List<MapLayer18> mLists18 = MapView.getDaoSession().getMapLayer18Dao().queryBuilder()
                        .where(MapLayer18Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer18Dao.Properties.Level.eq(level))
                        .where(MapLayer18Dao.Properties.Col.eq(col))
                        .where(MapLayer18Dao.Properties.Row.eq(row)).list();
                if (mLists18 != null && mLists18.size() > 0) {
                    for (MapLayer18 entity : mLists18) {
                        return entity.getLayer();
                    }
                }
            case 19:
                List<MapLayer19> mLists19 = MapView.getDaoSession().getMapLayer19Dao().queryBuilder()
                        .where(MapLayer19Dao.Properties.LayerType.eq(layerType))
                        .where(MapLayer19Dao.Properties.Level.eq(level))
                        .where(MapLayer19Dao.Properties.Col.eq(col))
                        .where(MapLayer19Dao.Properties.Row.eq(row)).list();
                if (mLists19 != null && mLists19.size() > 0) {
                    for (MapLayer19 entity : mLists19) {
                        return entity.getLayer();
                    }
                }
        }
        return null;
    }
}

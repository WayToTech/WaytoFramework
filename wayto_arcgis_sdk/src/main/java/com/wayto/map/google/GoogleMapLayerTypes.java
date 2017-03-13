package com.wayto.map.google;

/**
 * @Package: com.yunwei.map.tiled.google
 * @Description:google地图图层类型
 * @author: Aaron
 * @date: 2016-06-01
 * @Time: 14:35
 * @version: V1.0
 */
public interface GoogleMapLayerTypes {
    /** * 谷歌矢量地图服务 ======市政图*/
    static final int VECTOR_GOOGLE_MAP = 1;
    /** * 谷歌影像地图服务 ====== 卫星图 */
    static final int IMAGE_GOOGLE_MAP = 2;
    /** * 谷歌地形地图服务=======地形图 */
    static final int TERRAIN_GOOGLE_MAP = 3;
    /** * 谷歌道路等POI地图服务 ====纯道路图 */
    static final int ANNOTATION_GOOGLE_MAP = 4;
}

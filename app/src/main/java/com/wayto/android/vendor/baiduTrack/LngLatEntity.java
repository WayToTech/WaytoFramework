package com.wayto.android.vendor.baiduTrack;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.baidu
 * @Description:
 * @date 2016/9/23 10:38
 */
public class LngLatEntity {

    private double lng;
    private double lat;

    public LngLatEntity(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}

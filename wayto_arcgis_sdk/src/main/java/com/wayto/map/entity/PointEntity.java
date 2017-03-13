package com.wayto.map.entity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.map.entity
 * @Description:
 * @date 2016/8/10 10:22
 */
public class PointEntity {

    private double lat;
    private double lng;
    private long time;

    public PointEntity() {

    }

    public PointEntity(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public PointEntity(double lat, double lng, long time) {
        this.lat = lat;
        this.lng = lng;
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}

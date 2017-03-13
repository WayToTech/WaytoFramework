package com.wayto.android.function.refreshLocationFuncation.data;

import java.io.Serializable;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.refreshLocationFuncation.data
 * @Description:
 * @date 2017/2/8 11:48
 */

public class UploadLocationEntity implements Serializable{
    private double Lng;
    private double Lat;
    private double Altitude;
    private double Bearing;
    private double Speed;
    private String Provider;
    private String CreateTime;

    public double getLng() {
        return Lng;
    }

    public void setLng(double lng) {
        Lng = lng;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public double getAltitude() {
        return Altitude;
    }

    public void setAltitude(double altitude) {
        Altitude = altitude;
    }

    public double getBearing() {
        return Bearing;
    }

    public void setBearing(double bearing) {
        Bearing = bearing;
    }

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    public String getProvider() {
        return Provider;
    }

    public void setProvider(String provider) {
        Provider = provider;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
}

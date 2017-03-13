package com.wayto.map.entity;


/**
 * 原始GPS经过点阵纠偏后的经纬度
 */
public class LngLatEntity {
    private double LNG;
    private double LAT;
    private String REMARK;

    public LngLatEntity() {
    }

    public LngLatEntity(double lng, double lat) {
        this.LNG = lng;
        this.LAT = lat;
    }

    public double getLNG() {
        return LNG;
    }

    public void setLNG(double lNG) {
        LNG = lNG;
    }

    public double getLAT() {
        return LAT;
    }

    public void setLAT(double lAT) {
        LAT = lAT;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String rEMARK) {
        REMARK = rEMARK;
    }
}

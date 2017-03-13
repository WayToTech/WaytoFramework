package com.wayto.android.vendor.baiduTrack;


import com.baidu.trace.TraceLocation;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.baidu
 * @Description:
 * @date 2016/8/16 10:44
 */
public interface BaiduTrackLocationListener {

    void onReceiveLocation(TraceLocation traceLocation);
}

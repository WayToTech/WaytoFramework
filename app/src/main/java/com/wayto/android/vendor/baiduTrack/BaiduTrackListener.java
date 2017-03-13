package com.wayto.android.vendor.baiduTrack;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.baidu
 * @Description:历史记录查询回调接口
 * @date 2016/9/23 10:32
 */
public interface BaiduTrackListener {

    void onQueryHistoryTrackSuccess(HistoryTrackData entity);

    void onQueryHistoryTrackFailure(String str);

    void onQueryDistanceCallback(String str);

}

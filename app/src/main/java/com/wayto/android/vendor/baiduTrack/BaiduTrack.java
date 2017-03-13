package com.wayto.android.vendor.baiduTrack;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.baidu.trace.LBSTraceClient;
import com.baidu.trace.LocationMode;
import com.baidu.trace.OnStartTraceListener;
import com.baidu.trace.OnStopTraceListener;
import com.baidu.trace.OnTrackListener;
import com.baidu.trace.Trace;

import java.util.Map;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.baidu
 * @Description:百度鹰眼
 * @date 2016/8/15 12:49
 */
public class BaiduTrack {
    private final String TAG = "BaiduTrack";

    private static long SERVICE_ID;
    private static String ENTITY_NAME;

    private static LBSTraceClient client;
    private static Trace trace;
    /**
     * 轨迹服务类型（0 : 不上传位置数据，也不接收报警信息； 1 : 不上传位置数据，但接收报警信息；2 : 上传位置数据，且接收报警信息）
     */
    static int traceType = 2;
    /**
     * 采集周期（单位 : 秒）
     */
    private static int gatherInterval = 5;

    /**
     * 打包周期（单位 : 秒）
     */
    private static int packInterval = 30;

    private static BaiduTrack instance;

    public static BaiduTrack getInstance() {
        if (instance == null) {
            instance = new BaiduTrack();

        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    private static void initTrack(Context context) {
//        if (client == null) {
            client = new LBSTraceClient(context);
            // 设置定位模式
            client.setLocationMode(LocationMode.High_Accuracy);
            client.setInterval(gatherInterval, packInterval);
//        }
//        if (trace == null) {
            trace = new Trace(context, SERVICE_ID, ENTITY_NAME, traceType);
//        }
    }

    /**
     * 开启轨迹服务
     */
    public void startTrace(final Context context) {
        initTrack(context);
        //实例化开启轨迹服务回调接口
        final OnStartTraceListener startTraceListener = new OnStartTraceListener() {
            //开启轨迹服务回调接口（arg0 : 消息编码，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onTraceCallback(int arg0, String arg1) {
                Log.d(TAG, "startTrace onTraceCallBack arg0=" + arg0 + ", argl===" + arg1);
                //开启服务失败，重新开启
                if (0 == arg0 || 10006 == arg0 || 10008 == arg0 || 10009 == arg0) {
                   Log.d(TAG,"服务开户成功!");
                }else {
                    startTrace(context);
                }
            }

            //轨迹服务推送接口（用于接收服务端推送消息，arg0 : 消息类型，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onTracePushCallback(byte arg0, String arg1) {
                Log.d(TAG, "startTrace onTracePushCallback arg1==" + arg1);

            }
        };
        if (client != null) {
            client.startTrace(trace, startTraceListener);
        }
    }

    /**
     * 停止轨迹服务
     */
    public void stopTrace() {
        //实例化停止轨迹服务回调接口
        OnStopTraceListener stopTraceListener = new OnStopTraceListener() {
            // 轨迹服务停止成功
            @Override
            public void onStopTraceSuccess() {
                Log.i(TAG, "stopTrace onStopTraceSuccess()");
            }

            // 轨迹服务停止失败（arg0 : 错误编码，arg1 : 消息内容，详情查看类参考）
            @Override
            public void onStopTraceFailed(int arg0, String arg1) {
                Log.i(TAG, "stopTrace onStopTraceFailed arg0=" + arg0 + ", arg1=" + arg1);
                //停止服务失败
                if (arg0 == 11001) {
                    stopTrace();
                }
            }
        };
        if (client != null) {
            client.stopTrace(trace, stopTraceListener);
        }
    }

    /**
     * 历史足迹查询
     *
     * @param serverId      服务Id
     * @param entityName    entity标识
     * @param simpleReturn  是否返回精简的结果（0 : 否，1 : 是）
     * @param isProcessed   是否返回纠偏后轨迹（0 : 否，1 : 是）
     * @param processOption 纠偏选项，仅在is_processed=1时生效，通过为纠偏选项赋1（需要）或0（不需要）来设置是否需要该项数据处理，多选项设置时用英文逗号","分割，若不设置某选项则按默认值处理。
     *                      [ need_denoise：去噪，默认为1;
     *                      need_vacuate：抽稀，默认为1;
     *                      need_mapmatch：绑路（仅适用于驾车），默认为0（但之前已开通绑路的service，默认为1）;
     *                      ]
     * @param startTime     开始时间 Unix时间戳
     * @param endTime       结束时间 Unix时间戳
     * @param pageSize      分页大小
     * @param pageIndex     分页索引
     * @param listener      回调监听
     */
    public void queryTrackHistory(Context context, int serverId, String entityName, int simpleReturn, int isProcessed, String processOption, int startTime, int endTime, int pageSize, int pageIndex, final BaiduTrackListener listener) {
        initTrack(context);
        client.queryHistoryTrack(serverId, entityName, simpleReturn, isProcessed, processOption, startTime, endTime, pageSize, pageIndex, new OnTrackListener() {
            @Override
            public void onRequestFailedCallback(String s) {
                Log.d(TAG, "onRequestFailedCallback s===" + s);
                listener.onQueryHistoryTrackFailure(s);
            }

            @Override
            public void onQueryHistoryTrackCallback(String s) {
                super.onQueryHistoryTrackCallback(s);
                Log.d(TAG, "onQueryHistoryTrackCallback s===" + s);
                HistoryTrackData historyTrackData = GsonService.parseJson(s,
                        HistoryTrackData.class);
                listener.onQueryHistoryTrackSuccess(historyTrackData);
            }

            @Override
            public void onQueryDistanceCallback(String s) {
                super.onQueryDistanceCallback(s);
                Log.d(TAG, "onQueryDistanceCallback s==" + s);
                listener.onQueryDistanceCallback(s);
            }

            @Override
            public Map onTrackAttrCallback() {
                return super.onTrackAttrCallback();
            }
        });
    }

    /**
     * 查询里程
     * @param serviceId 轨迹服务标识
     * @param entityName entity标识
     * @param isProcessed 是否返回纠偏后轨迹，轨迹纠偏功能包括去噪、抽稀、绑路三个步骤，可在process_option字段中设置使用哪几个步骤
    0 : 否
    1 : 是
     * @param processOption  纠偏选项，仅在is_processed=1时生效，通过为纠偏选项赋1（需要）或0（不需要）来设置是否需要该项数据处理，多选项设置时用英文逗号","分割，若不设置某选项则按默认值处理。
     * @param supplementMode 里程补充
     * @param startTime
     * @param endTime
     * @param listener
     */
    public void queryDistance(Activity activity,long serviceId, String entityName, int isProcessed, String processOption, String supplementMode, int startTime, int endTime, OnTrackListener listener){
        initTrack(activity);
        client.queryDistance(serviceId,entityName,isProcessed,processOption,supplementMode,startTime,endTime,listener);
    }

    public void destroy() {
        client.onDestroy();
        instance = null;
        client = null;
        trace = null;
    }

    public long getSERVICE_ID() {
        return SERVICE_ID;
    }

    public void setSERVICE_ID(long SERVICE_ID) {
        this.SERVICE_ID = SERVICE_ID;
    }

    public String getENTITY_NAME() {
        return ENTITY_NAME;
    }

    public void setENTITY_NAME(String ENTITY_NAME) {
        this.ENTITY_NAME = ENTITY_NAME;
    }
}

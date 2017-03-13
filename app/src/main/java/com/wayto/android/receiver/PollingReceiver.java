package com.wayto.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.wayto.android.R;
import com.wayto.android.function.batteryFuncation.BatteryPresenter;
import com.wayto.android.service.PollingService;
import com.wayto.android.utils.IUtil;
import com.wayto.android.vendor.baiduTTS.BaiduTTSUtils;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.receiver
 * @Description:轮询监听
 * @date 2017/1/13 14:00
 */

public class PollingReceiver extends BroadcastReceiver {
    private final String TAG = getClass().getSimpleName();

    /*自定义轮询广播*/
    public static final String ACTION = "com.yunwei.base.polling";
    /*百度鹰眼服务*/
    public static final String BAIDU_TRACK_SERVICE = "com.baidu.trace.LBSTraceService";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {/*电量检测*/
            /*获取电量的等级信息（百分比）*/
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            int battery = (int) (((double) level / scale) * 100);
            /*语音播报手机电量*/
            switch (status) {
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    if (battery == 5) {
                        BaiduTTSUtils.speak(context, IUtil.getStrToRes(R.string.tts_battery_five));
                    } else if (battery == 9) {
                        BaiduTTSUtils.speak(context, IUtil.getStrToRes(R.string.tts_battery_ten));
                    } else if (battery == 19) {
                        BaiduTTSUtils.speak(context, IUtil.getStrToRes(R.string.tts_baatery_twenty));
                    }
                    break;
            }
             /*电量上传*/
            BatteryPresenter.getInstance().uploadBatteryAction(level, scale);
        } else if (ACTION.equals(intent.getAction())) {/*自定义轮询广播检测*/
            PollingService.startPollingService(context);
        }
    }
}

package com.wayto.android.function.batteryFuncation;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.water.ui.activity
 * @Description:
 * @date 2016/12/27 9:21
 */

public interface BatteryContract {

    interface Presenter{
        void uploadBatteryAction(int level, int scale);
    }
}

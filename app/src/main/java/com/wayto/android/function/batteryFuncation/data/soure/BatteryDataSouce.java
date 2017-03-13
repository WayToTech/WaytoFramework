package com.wayto.android.function.batteryFuncation.data.soure;

import com.wayto.android.base.BaseDataSourse;
import com.wayto.android.function.batteryFuncation.data.BatteryEntity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.water.ui.activity.data.soure
 * @Description:
 * @date 2016/12/27 9:12
 */

public interface BatteryDataSouce extends BaseDataSourse {

    void uploadBattery(BatteryEntity body);
}

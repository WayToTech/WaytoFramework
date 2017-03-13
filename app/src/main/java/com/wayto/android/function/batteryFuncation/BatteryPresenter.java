package com.wayto.android.function.batteryFuncation;

import com.wayto.android.R;
import com.wayto.android.base.DataApplication;
import com.wayto.android.function.batteryFuncation.data.BatteryEntity;
import com.wayto.android.function.batteryFuncation.data.soure.BatteryRemoteRepo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.water.ui.activity
 * @Description:电量上传Presenter
 * @date 2016/12/27 9:19
 */

public class BatteryPresenter implements BatteryContract.Presenter {

    private static BatteryPresenter instance;

    private BatteryRemoteRepo remoteRepo;

    /*记录第一次上传*/
    private boolean isFirst = true;
    /*记录电量临时变量*/
    private int batteryTem;
    /*有效的电量集合*/
    private List<String> validBatteryLists = new ArrayList<>();

    public BatteryPresenter() {
        this.remoteRepo = new BatteryRemoteRepo();
        validBatteryLists = Arrays.asList(DataApplication.getInstance().getResources().getStringArray(R.array.battery_list));
    }

    public static BatteryPresenter getInstance() {
        if (instance == null) {
            instance = new BatteryPresenter();
        }

        return instance;
    }

    @Override
    public void uploadBatteryAction(int level, int scale) {
        int battery = (int) (((double) level / scale) * 100);
        if (isFirst || validBatteryLists.contains(String.valueOf(battery))) {
             /*重复上传同一个电量处理*/
            if (batteryTem != 0 && battery == batteryTem) {
                return;
            }
            isFirst = false;
            batteryTem = battery;

            /*构建实体*/
            BatteryEntity entity = new BatteryEntity();
            entity.setId(level);
            entity.setValue(battery);
            entity.setNote("battery change");

            remoteRepo.uploadBattery(entity);
        }
    }
}

package com.wayto.android.function.batteryFuncation.data.soure;

import com.wayto.android.entity.ResponseModel;
import com.wayto.android.function.batteryFuncation.data.BatteryEntity;
import com.wayto.android.utils.ILog;
import com.wayto.android.vendor.retrofit.RetrofitManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.water.ui.activity.data.soure
 * @Description:电量上传Remote
 * @date 2016/12/27 9:15
 */

public class BatteryRemoteRepo implements BatteryDataSouce {
    private final String TAG = getClass().getSimpleName();

    Call<ResponseModel> batteryCall;

    @Override
    public void uploadBattery(BatteryEntity body) {
        batteryCall = RetrofitManager.getInstance().getService().uploadBattery(body);
        batteryCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ILog.d(TAG, "response==" + response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ILog.d(TAG, "onFailure==" + t.getMessage());
            }
        });
    }

    @Override
    public void cancelRequest() {
        if (batteryCall != null && !batteryCall.isCanceled()) {
            batteryCall.cancel();
        }
    }
}

package com.wayto.android.function.mainFuncations.data.soure;

import com.wayto.android.common.Constant;
import com.wayto.android.vendor.retrofit.RetrofitManager;
import com.wayto.android.entity.ResponseModel;
import com.wayto.android.function.mainFuncations.data.QiNiuTokenEntity;
import com.wayto.android.utils.ILog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.data.soure
 * @Description:
 * @date 2016/11/30 13:59
 */

public class MainRemoteRepo implements MainDataSource {
    private final String TAG = getClass().getSimpleName();

    private static MainRemoteRepo instance;

    public static MainRemoteRepo newInstance() {
        if (instance == null) {
            instance = new MainRemoteRepo();
        }
        return instance;
    }

    @Override
    public void reqQiNiuToken(final RequestQiNiuTokenCallBack callBack) {
        Call<ResponseModel<QiNiuTokenEntity>> call = RetrofitManager.getInstance().getService().reqQiniuToken();
        call.enqueue(new Callback<ResponseModel<QiNiuTokenEntity>>() {
            @Override
            public void onResponse(Call<ResponseModel<QiNiuTokenEntity>> call, Response<ResponseModel<QiNiuTokenEntity>> response) {
                if (response.code() == Constant.HTTP_SUCESS_CODE) {
                    ILog.d(TAG,response.body().getData().getToken());
                    callBack.getQiNiuTokenSuccess(response.body().getData().getToken());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<QiNiuTokenEntity>> call, Throwable t) {

            }
        });
    }
}

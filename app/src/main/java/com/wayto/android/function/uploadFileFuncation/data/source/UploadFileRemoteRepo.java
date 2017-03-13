package com.wayto.android.function.uploadFileFuncation.data.source;

import com.wayto.android.entity.ResponseModel;
import com.wayto.android.utils.ILog;
import com.wayto.android.vendor.retrofit.RetrofitManager;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.uploadFileFuncation.data.source
 * @Description:
 * @date 2017/2/8 15:11
 */

public class UploadFileRemoteRepo implements UploadFileDataSource {
    private final String TAG = getClass().getSimpleName();

    private static UploadFileRemoteRepo remoteRepo;

    private Call<ResponseModel> call;

    public static UploadFileRemoteRepo newInstance() {
        if (remoteRepo == null) {
            remoteRepo = new UploadFileRemoteRepo();
        }
        return remoteRepo;
    }

    @Override
    public void uploadFile(File file, final UploadFileCallBack callBack) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        call = RetrofitManager.getInstance().getService().uploadFile(body);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ILog.d(TAG, "code==" + response.code());
                if (response.isSuccessful() && response.body().isSuccess()) {
                    callBack.getUploadFileSuccess(response.body().getData().toString());
                } else {
                    callBack.getUploadFileFialure(response.code(), "文件上传失败!");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ILog.e(TAG, "code==" + t.getMessage());
            }
        });
    }
}

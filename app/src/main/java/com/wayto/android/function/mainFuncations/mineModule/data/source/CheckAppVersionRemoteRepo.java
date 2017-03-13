package com.wayto.android.function.mainFuncations.mineModule.data.source;

import com.wayto.android.BuildConfig;
import com.wayto.android.entity.ResponseModel;
import com.wayto.android.function.mainFuncations.mineModule.data.AppVersionEntity;
import com.wayto.android.utils.IFileUtils;
import com.wayto.android.utils.ILog;
import com.wayto.android.vendor.retrofit.APIService;
import com.wayto.android.vendor.retrofit.RetrofitManager;
import com.wayto.android.vendor.retrofit.download.DownloadProgressHandler;
import com.wayto.android.vendor.retrofit.download.ProgressHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.cmcc.ui.mainFunctions.data.source
 * @Description:App版本检测
 * @date 2016/11/17 10:34
 */

public class CheckAppVersionRemoteRepo implements CheckAppVersionDataSource {
    private final String TAG = getClass().getSimpleName();

    private static CheckAppVersionRemoteRepo checkAppVersionRepo;

    private Call<ResponseModel<AppVersionEntity>> appVersionCall;
    private Call<ResponseBody> downloadCall;

    public static CheckAppVersionRemoteRepo newInstance() {
        if (checkAppVersionRepo == null) {
            checkAppVersionRepo = new CheckAppVersionRemoteRepo();
        }
        return checkAppVersionRepo;
    }

    @Override
    public void checkAppVersion(final CheckAppVersionCallBack callBack) {
        appVersionCall = RetrofitManager.getInstance().getService().checkAppVersion();
        appVersionCall.enqueue(new Callback<ResponseModel<AppVersionEntity>>() {
            @Override
            public void onResponse(Call<ResponseModel<AppVersionEntity>> call, Response<ResponseModel<AppVersionEntity>> response) {
                if (response.code() == 200 && response.isSuccessful()) {
                    callBack.onCheckAppSuccess(response.body().getData());
                } else {
                    callBack.onCheckAppDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<AppVersionEntity>> call, Throwable t) {
                callBack.onCheckAppDataNotAvailable();
            }
        });
    }

    @Override
    public void downloadApk(final DownloadCallBack downloadCallBack) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.DOMAI);
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        APIService retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(APIService.class);

        downloadCall = retrofit.downloadFile(downloadCallBack.getDownloadURL());
        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                downloadCallBack.onDownloadProgress(bytesRead, contentLength, done);
                ILog.e(TAG, String.format("%d%% done\n", (100 * bytesRead) / contentLength));
                downloadCallBack.onDownloadProgress((int) ((100 * bytesRead) / contentLength));
            }
        });
        downloadCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                InputStream is = response.body().byteStream();
                                File file = new File(IFileUtils.getDownloadDir());
                                if (!file.exists()) {
                                    file.mkdirs();
                                }
                                FileOutputStream fos = new FileOutputStream(file + "/YunweiBase.apk");
                                BufferedInputStream bis = new BufferedInputStream(is);
                                byte[] buffer = new byte[1024];
                                int len;
                                while ((len = bis.read(buffer)) != -1) {
                                    fos.write(buffer, 0, len);
                                    fos.flush();
                                }
                                fos.close();
                                bis.close();
                                is.close();
                                downloadCallBack.onDownloadComplete(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                                downloadCallBack.onDownloadDataNotAvailable();
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                downloadCallBack.onDownloadDataNotAvailable();
            }
        });
    }

    @Override
    public void cancelRequest() {
        if (appVersionCall != null && !appVersionCall.isCanceled()) {
            appVersionCall.cancel();
        }
        if (downloadCall != null && !downloadCall.isCanceled()) {
            downloadCall.cancel();
        }
    }
}

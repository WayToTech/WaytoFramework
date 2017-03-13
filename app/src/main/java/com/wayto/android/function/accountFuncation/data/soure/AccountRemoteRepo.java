package com.wayto.android.function.accountFuncation.data.soure;

import android.text.TextUtils;

import com.wayto.android.R;
import com.wayto.android.base.DataApplication;
import com.wayto.android.common.Constant;
import com.wayto.android.entity.ResponseModel;
import com.wayto.android.function.accountFuncation.data.ModifyHeadEntity;
import com.wayto.android.function.accountFuncation.data.UserInfoEntity;
import com.wayto.android.utils.BitmapUtil;
import com.wayto.android.utils.IFileUtils;
import com.wayto.android.utils.INetWorkUtil;
import com.wayto.android.utils.ISpfUtil;
import com.wayto.android.utils.ISystemUtil;
import com.wayto.android.utils.IUtil;
import com.wayto.android.vendor.qiniu.QiNiuImageUploadManager;
import com.wayto.android.vendor.qiniu.UploadCallBackListener;
import com.wayto.android.vendor.retrofit.RetrofitManager;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.account.data.soure
 * @Description:登录业务请求
 * @date 2016/11/29 15:19
 */

public class AccountRemoteRepo implements AccountDataSoure {
    private final String TAG = getClass().getSimpleName();

    private static AccountRemoteRepo remoteRepo;
    private Call<UserInfoEntity> LoginCall;
    private Call<ResponseModel> modifyHeadCall;
    private Call<ResponseModel> modifyPwdCall;

    public static AccountRemoteRepo newInstance() {
        if (remoteRepo == null) {
            remoteRepo = new AccountRemoteRepo();
        }
        return remoteRepo;
    }

    @Override
    public void login(String account, String password, final LoginCallBack callBack) {
        if (!INetWorkUtil.isNetworkAvailable(DataApplication.getInstance())) {
            callBack.onLoginFailure(IUtil.getStrToRes(R.string.invalid_network));
            return;
        }
        String body = "grant_type=password&client_id=wt&client_secret=123456&username=" + account + "&password=" + password + "&ver=" + ISystemUtil.getVersionName();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), body);
        LoginCall = RetrofitManager.getInstance().getService().loginRepo(requestBody);
        LoginCall.enqueue(new Callback<UserInfoEntity>() {
            @Override
            public void onResponse(Call<UserInfoEntity> call, Response<UserInfoEntity> response) {
                if (response.code() == Constant.HTTP_SUCESS_CODE) {
                    callBack.onLoginSuccess(response.body());
                } else if (response.code() == Constant.HTTP_PASSWORD_ERROR_CODE) {
                    callBack.onLoginFailure(IUtil.getStrToRes(R.string.account_pwd_error));
                } else {
                    callBack.onLoginFailure(IUtil.getStrToRes(R.string.login_failure));
                }
            }

            @Override
            public void onFailure(Call<UserInfoEntity> call, Throwable t) {
                callBack.onLoginFailure(IUtil.getStrToRes(R.string.login_failure));
            }
        });
    }

    @Override
    public void modifyHead(final String filePath, final ModifyHeadCallBack callBack) {
        if (!INetWorkUtil.isNetworkAvailable(DataApplication.getInstance())) {
            callBack.onModifyHeadFailure(IUtil.getStrToRes(R.string.invalid_network));
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String cachePath = BitmapUtil.compressBitmap(filePath, IFileUtils.getImageCatchDir());
                if (!TextUtils.isEmpty(cachePath)) {
                    QiNiuImageUploadManager.uploadImage(ISpfUtil.getValue(Constant.QINIU_TOKEN_KEY, "").toString(), cachePath, new UploadCallBackListener() {
                        @Override
                        public void onUploadStart() {

                        }

                        @Override
                        public void onUploadEnd() {

                        }

                        @Override
                        public void onProgess(double percent) {

                        }

                        @Override
                        public void onUploadComplete(List<String> path) {
                            if (path != null && path.size() > 0) {
                                IFileUtils.delete(filePath);
                                updateUserInfo(callBack.getHeadBody(path.get(0)), callBack);
                            }
                        }

                        @Override
                        public void onUploadFailure() {
                            callBack.onModifyHeadFailure(IUtil.getStrToRes(R.string.toast_modify_head_failure));
                        }
                    });
                } else {
                    callBack.onModifyHeadFailure(IUtil.getStrToRes(R.string.toast_modify_head_failure));
                }
            }
        }).start();
    }

    /**
     * 更新用户信息
     *
     * @param entity
     * @param callBack
     */
    private void updateUserInfo(final ModifyHeadEntity entity, final ModifyHeadCallBack callBack) {
        modifyHeadCall = RetrofitManager.getInstance().getService().modifyUserInfo(entity, DataApplication.getInstance().getUserInfoEntity().getId());
        modifyHeadCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    callBack.onModifyHeadSuccess(entity.getIcon());
                } else {
                    callBack.onModifyHeadFailure(IUtil.getStrToRes(R.string.toast_modify_head_failure));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callBack.onModifyHeadFailure(IUtil.getStrToRes(R.string.toast_modify_head_failure));
            }
        });
    }

    @Override
    public void modifyPassword(final ModifyPasswordCallBack callBack) {
        modifyPwdCall = RetrofitManager.getInstance().getService().modifyUserPassword(callBack.getPasswordBody(), DataApplication.getInstance().getUserInfoEntity().getId());
        modifyPwdCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.code() == 200 && response.isSuccessful()) {
                    callBack.onModifyPasswordSuccess();
                } else {
                    callBack.onModifyPasswordFailure(IUtil.getStrToRes(R.string.toast_modify_pwd_faliure));
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                callBack.onModifyPasswordFailure(IUtil.getStrToRes(R.string.toast_modify_pwd_faliure));
            }
        });
    }

    /**
     * 取消请求
     */
    @Override
    public void cancelRequest() {
        if (LoginCall != null && !LoginCall.isCanceled()) {
            LoginCall.cancel();
        }
        if (modifyHeadCall != null && modifyHeadCall.isCanceled()) {
            modifyHeadCall.cancel();
        }
        if (modifyPwdCall != null && modifyPwdCall.isCanceled()) {
            modifyPwdCall.cancel();
        }
    }
}

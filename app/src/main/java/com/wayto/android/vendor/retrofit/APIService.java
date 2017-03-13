package com.wayto.android.vendor.retrofit;

import com.wayto.android.BuildConfig;
import com.wayto.android.entity.ResponseModel;
import com.wayto.android.function.accountFuncation.data.ModifyHeadEntity;
import com.wayto.android.function.accountFuncation.data.ModifyPasswordEntity;
import com.wayto.android.function.accountFuncation.data.UserInfoEntity;
import com.wayto.android.function.batteryFuncation.data.BatteryEntity;
import com.wayto.android.function.mainFuncations.data.QiNiuTokenEntity;
import com.wayto.android.function.mainFuncations.mineModule.data.AppVersionEntity;
import com.wayto.android.function.mapFuncation.data.RegeoEntity;
import com.wayto.android.function.refreshLocationFuncation.data.UploadLocationEntity;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.common.retrofit
 * @Description:请求API接口配制
 * @date 2016/11/29 15:50
 */

public interface APIService {
    /**
     * 登录
     *
     * @param entity
     * @return
     */
    @POST(BuildConfig.LOGIN_URL)
    Call<UserInfoEntity> loginRepo(@Body RequestBody entity);

    /**
     * 修改用户信息
     *
     * @param entity
     * @return
     */
    @POST(BuildConfig.MODIFY_USER_HEAD_URL + "{id}")
    Call<ResponseModel> modifyUserInfo(@Body ModifyHeadEntity entity, @Path("id") String id);

    /**
     * 修改用户密码
     *
     * @param entity
     * @return
     */
    @PUT(BuildConfig.MODIFY_USER_PWD_URL + "{id}")
    Call<ResponseModel> modifyUserPassword(@Body ModifyPasswordEntity entity, @Path("id") String id);

    /**
     * 请求七牛Token
     *
     * @return
     */
    @GET(BuildConfig.QINIU_TOKEN_URL)
    Call<ResponseModel<QiNiuTokenEntity>> reqQiniuToken();

    /**
     * 版本检测
     *
     * @return
     */
    @GET(BuildConfig.CHECK_APP_VERSION_URL)
    Call<ResponseModel<AppVersionEntity>> checkAppVersion();

    /**
     * 电量上报
     *
     * @param entity
     * @return
     */
    @POST(BuildConfig.BATTERY_UPLOAD_URL)
    Call<ResponseModel> uploadBattery(@Body BatteryEntity entity);

    /**
     * 位置信息点上传
     *
     * @param entity
     * @return
     */
    @POST(BuildConfig.REFRESH_LOCATION_URL)
    Call<ResponseModel> uploadLocation(@Body UploadLocationEntity entity);

    /**
     * 文件下载
     *
     * @param url
     * @return
     */
    @GET
    Call<ResponseBody> downloadFile(@Url String url);

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @Multipart
    @POST("")
    Call<ResponseModel> uploadFile(@Part MultipartBody.Part file);

    /**
     * 轮询资源上传
     *
     * @param body
     * @return
     */
    @POST
    Call<ResponseModel> uploadPollingData(@Url String url, @Body RequestBody body);

    /**
     * 逆地理编码
     *
     * @param url
     * @return
     */
    @GET
    Call<RegeoEntity> geocodinLocation(@Url String url);

}

package com.wayto.android.function.pollingFuncation.data.source;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.wayto.android.base.DataApplication;
import com.wayto.android.common.Constant;
import com.wayto.android.entity.Image;
import com.wayto.android.entity.ResponseModel;
import com.wayto.android.entity.Video;
import com.wayto.android.function.pollingFuncation.PollingManage;
import com.wayto.android.function.uploadFileFuncation.UploadFileManager;
import com.wayto.android.db.UploadingTable;
import com.wayto.android.db.WorkRecordTable;
import com.wayto.android.db.WorkRecordTableDao;
import com.wayto.android.utils.ILog;
import com.wayto.android.utils.IUtil;
import com.wayto.android.vendor.retrofit.RetrofitManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.pollingFuncation.data.source
 * @Description:轮询上传业务类
 * @date 2017/1/20 17:24
 */

public class PollingRemoteRepo implements PollingDataSource {
    public final String TAG = getClass().getSimpleName();

    private static PollingRemoteRepo remoteRepo;
    /*附件标志*/
    private final String IMAGE_FLAG = "image";
    private final String VIDEO_FLAG = "video";
    private final String AUDIO_FLAG = "audio";

    public static PollingRemoteRepo newInstance() {
        if (remoteRepo == null) {
            remoteRepo = new PollingRemoteRepo();
        }
        return remoteRepo;
    }

    /**
     * 插入工作记录表【WorkRecordTable】
     * @param table
     * @param callBack
     */
    @Override
    public void insertWorkRecord(WorkRecordTable table, InsertWorkRecordCallBack callBack) {
        try {
            /*判断是否是第一次保存,如果不是直接更新*/
            if (table.getId() != Constant.DEFUALT_WORKRECORD_ID) {
                DataApplication.getInstance().getDaoSession().getWorkRecordTableDao().update(table);
                callBack.insertWorkRecordSuccess(table);
            } else {
                table.setId(null);
                long id = DataApplication.getInstance().getDaoSession().getWorkRecordTableDao().insert(table);
                if (id != 0) {
                    table.setId(id);
                    callBack.insertWorkRecordSuccess(table);
                } else {
                    callBack.insertWorkRecordFailure();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            callBack.insertWorkRecordFailure();
        }
    }

    /**
     * 查询工作记录表【WorkRecordTable】
     * @param callBack
     */
    @Override
    public void queryWorkRecordTab(QueryWorkRecordCallBack callBack) {
        try {
            List<WorkRecordTable> tables = DataApplication.getInstance().getDaoSession().getWorkRecordTableDao().queryBuilder()
                    .where(WorkRecordTableDao.Properties.WorkType.eq(callBack.getWorkType()))
                    .where(WorkRecordTableDao.Properties.LocalTag.eq(callBack.getlocalTag()))
                    .orderDesc(WorkRecordTableDao.Properties.Id).list();
            callBack.queryWorkRecordSuccess(tables);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.queryWorkRecordFailure();
        }
    }

    /**
     * 查询上传表【UploadingTable】
     * @param callBack
     */
    @Override
    public void queryUploadTaskList(QueryUploadTaskListCallBack callBack) {
        try {
            List<UploadingTable> lists = DataApplication.getInstance().getDaoSession().getUploadingTableDao().loadAll();
            callBack.getUploadTaskListSuccess(lists);
        } catch (Exception e) {
            e.printStackTrace();
            callBack.getUploadTaskListFailure();
        }
    }

    /**
     * 上传
     * @param entity
     * @param callBack
     */
    @Override
    public void uploadTask(UploadingTable entity, UploadTaskCallBack callBack) {
        if (TextUtils.isEmpty(entity.getUrl())) {
            return;
        }
        if (!TextUtils.isEmpty(entity.getImageUrl())) { /*判断是否有图片要上传*/
            List<String> images = Arrays.asList(entity.getImageUrl());
            uploadFileData(images, entity, IMAGE_FLAG, callBack);
        } else if (!TextUtils.isEmpty(entity.getVideoUrl())) {/*判断是否有视频要上传*/
            List<String> videos = Arrays.asList(entity.getVideoUrl().split(","));
            uploadFileData(videos, entity, VIDEO_FLAG, callBack);
        } else if (!TextUtils.isEmpty(entity.getAudioUrl())) {/*判断是否有语音要上传*/
            List<String> audios = Arrays.asList(entity.getAudioUrl().split(","));
            uploadFileData(audios, entity, AUDIO_FLAG, callBack);
        } else {
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), entity.getBody());
            uploadData(entity.getUrl(), entity, requestBody, callBack);
        }
    }

    /**
     * 删除上传记录
     * @param entity
     */
    @Override
    public void deleteUploadTask(UploadingTable entity) {
        DataApplication.getInstance().getDaoSession().getUploadingTableDao().delete(entity);
    }

    /**
     * 更新工作记录表【WorkRecordTable】
     * @param table
     */
    @Override
    public void updateWorkRecordTableStatus(WorkRecordTable table) {
        DataApplication.getInstance().getDaoSession().getWorkRecordTableDao().update(table);
    }

    /**
     * 插入上传表【WorkRecordTable】
     * @param table
     */
    @Override
    public void insertUploadTask(UploadingTable table) {
        DataApplication.getInstance().getDaoSession().getUploadingTableDao().insert(table);
    }

    /**
     * 修改任务状态【UploadingTable】
     * @param missionId
     * @param missionPointId
     */
    @Override
    public void updateMissionStatus(int missionId, int missionPointId) {
        //TODO 处理修改任务状态
        if (missionId == 0 || missionPointId == 0) {
            return;
        }
    }

    /**
     * 文件上传
     *
     * @param lists
     * @return
     */
    private void uploadFileData(List<String> lists, final UploadingTable entity, final String flag, final UploadTaskCallBack callBack) {
        List<File> files = new ArrayList<>();
        for (String string : lists) {
            files.add(new File(string));
        }
        UploadFileManager.uploadFile(files, new UploadFileManager.UploadFileCallBack() {
            @Override
            public void onUploadFileStart() {

            }

            @Override
            public void onUploadFileEnd() {

            }

            @Override
            public void onUploadFileSuccess(List<String> urls) {
                String body = entity.getBody();
                if (IMAGE_FLAG.equals(flag)) {
                    entity.setImageUrl("");
                    List<Image> imags = IUtil.convertStringListToImgList(urls);
                    body = body.replaceAll("\"" + PollingManage.DEFULT_IMAGE_FORMAT + "\"", new Gson().toJson(imags));
                } else if (VIDEO_FLAG.equals(flag)) {
                    entity.setVideoUrl("");
                    List<Video> videos = IUtil.convertStringListToVideoList(urls);
                    body = body.replaceAll("\"" + PollingManage.DEFULT_VIDEO_FORMAT + "\"", new Gson().toJson(videos));
                } else if (AUDIO_FLAG.equals(flag)) {
                    entity.setAudioUrl("");
                    body = body.replaceAll("\"" + PollingManage.DEFULT_AUDEIO_FORMAT + "\"", new Gson().toJson(urls));
                }
                entity.setBody(body);
                /*判断是否上传完成，否则递归上传(多个附件资源)*/
                if (TextUtils.isEmpty(entity.getImageUrl()) && TextUtils.isEmpty(entity.getVideoUrl()) && TextUtils.isEmpty(entity.getAudioUrl())) {
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json, text/json"), body);
                    uploadData(entity.getUrl(), entity, requestBody, callBack);
                } else {
                    uploadTask(entity, callBack);
                }
            }

            @Override
            public void onUploadFileFailure() {

            }
        });
    }

    /**
     * 资源上传
     *
     * @param url
     * @param body
     */
    private void uploadData(String url, final UploadingTable entity, RequestBody body, final UploadTaskCallBack callBack) {
        Call<ResponseModel> call = RetrofitManager.getInstance().getService().uploadPollingData(url, body);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ILog.d(TAG, "轮询上传 code==" + response.code());
                if (response.isSuccessful() && response.body().isSuccess()) {
                    callBack.onUploadTaskSuccess(entity);
                } else {
                    callBack.onUploadTaskFailure(entity);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                ILog.e(TAG, "轮询上传 error==" + t.getMessage());
                callBack.onUploadTaskFailure(entity);
            }
        });
    }
}

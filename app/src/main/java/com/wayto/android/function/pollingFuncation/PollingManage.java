package com.wayto.android.function.pollingFuncation;

import android.content.Context;

import com.wayto.android.function.pollingFuncation.data.source.PollingRemoteRepo;
import com.wayto.android.db.UploadingTable;
import com.wayto.android.db.WorkRecordTable;
import com.wayto.android.service.PollingService;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.pollingFuncation
 * @Description:轮询管理工具类
 * @date 2017/2/6 14:19
 */

public class PollingManage {
    /*默认图片格式*/
    public static final String DEFULT_IMAGE_FORMAT = "DEFULT_IMAGE_FORMAT";
    /*默认视频格式*/
    public static final String DEFULT_VIDEO_FORMAT = "DEFULT_VIDEO_FORMAT";
    /*默认音频格式*/
    public static final String DEFULT_AUDEIO_FORMAT = "DEFULT_AUDEIO_FORMAT";

    private static PollingManage instance;

    public static PollingManage getInstance() {
        if (instance == null) {
            instance = new PollingManage();
        }
        return instance;
    }

    /**
     * 插入上传表
     *
     * @param table
     */
    public void addWorkRecord(final Context context, WorkRecordTable table) {
        new PollingPresent(PollingRemoteRepo.newInstance(), new PollingContract.InsertWorkRecordView() {
            @Override
            public void insertWordRecordSuccess(WorkRecordTable table) {
                PollingService.startPollingService(context);
            }

            @Override
            public void insertWorkRecordFailure() {

            }
        }).insertWorkRecord(table,true);
    }

    /**
     * 保存草稿
     *
     * @param context
     * @param table
     * @param insertWorkRecordView
     */
    public void saveWorkRecord(Context context, WorkRecordTable table,PollingContract.InsertWorkRecordView insertWorkRecordView) {
        new PollingPresent(PollingRemoteRepo.newInstance(), insertWorkRecordView).insertWorkRecord(table, false);
    }

    /**
     * 删除任务列表
     *
     * @param table
     */
    public void deleteUploadTask(UploadingTable table) {
        new PollingPresent(PollingRemoteRepo.newInstance()).deleteUploadTask(table);
    }

    /**
     * 更新工作记录状态
     *
     * @param table
     */
    public void updateWorkRecordStatus(WorkRecordTable table) {
        new PollingPresent(PollingRemoteRepo.newInstance()).updateWorkRecordTaskStatus(table);
    }
}

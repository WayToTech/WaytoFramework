package com.wayto.android.function.pollingFuncation;

import com.wayto.android.R;
import com.wayto.android.common.Constant;
import com.wayto.android.function.pollingFuncation.data.source.PollingDataSource;
import com.wayto.android.function.pollingFuncation.data.source.PollingRemoteRepo;
import com.wayto.android.db.UploadingTable;
import com.wayto.android.db.WorkRecordTable;
import com.wayto.android.utils.IUtil;
import com.wayto.android.utils.NotificationUtil;

import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.pollingFuncation
 * @Description:轮询Present
 * @date 2017/1/20 16:32
 */

public class PollingPresent implements PollingContract.Presenter, PollingDataSource.InsertWorkRecordCallBack, PollingDataSource.QueryUploadTaskListCallBack, PollingDataSource.UploadTaskCallBack, PollingDataSource.QueryWorkRecordCallBack {

    private PollingRemoteRepo remoteRepo;
    private PollingContract.InsertWorkRecordView insertView;
    private PollingContract.QueryView queryView;
    private PollingContract.UploadView uploadView;
    private PollingContract.QueryWorkRecordView queryWorkRecordView;

    private boolean isUpload = true;
    private int workType, localTag;

    public PollingPresent(PollingRemoteRepo remoteRepo) {
        this.remoteRepo = remoteRepo;
    }

    public PollingPresent(PollingRemoteRepo remoteRepo, PollingContract.InsertWorkRecordView insertView) {
        this.remoteRepo = remoteRepo;
        this.insertView = insertView;
    }

    public PollingPresent(PollingRemoteRepo remoteRepo, PollingContract.QueryView queryView) {
        this.remoteRepo = remoteRepo;
        this.queryView = queryView;
    }

    public PollingPresent(PollingRemoteRepo remoteRepo, PollingContract.UploadView uploadView) {
        this.remoteRepo = remoteRepo;
        this.uploadView = uploadView;
    }

    public PollingPresent(PollingRemoteRepo remoteRepo, PollingContract.QueryWorkRecordView queryWorkRecordView) {
        this.remoteRepo = remoteRepo;
        this.queryWorkRecordView = queryWorkRecordView;
    }

    @Override
    public void insertWorkRecord(WorkRecordTable table, boolean isUpload) {
        this.isUpload = isUpload;
        /*标记上传中状态,启用后台自动上传isUpload=true,保存草稿isUpload=flase*/
        if (isUpload) {
            table.setLocalTag(Constant.WORKRECORD_LOCATION_STATUS.COMMITING.getValue());
        } else {
            table.setLocalTag(Constant.WORKRECORD_LOCATION_STATUS.UNMCOMMITTED.getValue());
        }
        remoteRepo.insertWorkRecord(table, this);
    }

    @Override
    public void queryWorkRecord(int workType, int localTag) {
        this.workType = workType;
        this.localTag = localTag;
        remoteRepo.queryWorkRecordTab(this);
    }

    @Override
    public void insertUploadTask(UploadingTable table) {
        remoteRepo.insertUploadTask(table);
    }

    @Override
    public void deleteUploadTask(UploadingTable entity) {
        remoteRepo.deleteUploadTask(entity);
    }

    @Override
    public void queryUploadTaskList() {
          remoteRepo.queryUploadTaskList(this);
    }

    @Override
    public void uploadTaskAction(UploadingTable entity) {
        remoteRepo.uploadTask(entity, this);
    }

    @Override
    public void updateWorkRecordTaskStatus(WorkRecordTable table) {
        /*标记上传完成状态*/
        table.setLocalTag(Constant.WORKRECORD_LOCATION_STATUS.SUBMITTED.getValue());
        remoteRepo.updateWorkRecordTableStatus(table);
    }

    @Override
    public void insertWorkRecordSuccess(WorkRecordTable table) {
        if (insertView != null) {
            insertView.insertWordRecordSuccess(table);
        }
        /*插入工作记录表成功，判断是否需要启用后台自动上传*/
        if (isUpload && table != null) {
            /*创建要上传的Task实体*/
            UploadingTable uploadingTable = new UploadingTable();
            uploadingTable.setWorkRecordID(table.getId());
            uploadingTable.setUrl(table.getUploadUrl());
            uploadingTable.setBody(table.getAttributes());
            uploadingTable.setImageUrl(table.getImagesUrl());
            uploadingTable.setVideoUrl(table.getVideoUrl());
            uploadingTable.setAudioUrl(table.getAudioUrl());

            insertUploadTask(uploadingTable);
        }
    }

    @Override
    public void insertWorkRecordFailure() {
        if (insertView != null) {
            insertView.insertWorkRecordFailure();
        }
    }

    @Override
    public void getUploadTaskListSuccess(List<UploadingTable> lists) {
        if (queryView != null) {
            queryView.queryUploadTaskSuccess(lists);
        }
    }

    @Override
    public void getUploadTaskListFailure() {
        if (queryView != null) {
            queryView.queryUploadTaskFailure();
        }
    }

    @Override
    public void onUploadTaskSuccess(UploadingTable entity) {
        if (uploadView != null) {
            /*修改任务工作状态*/
            WorkRecordTable table = entity.getWorkRecordTable();
            remoteRepo.updateMissionStatus(table.getMissionId(), table.getMissionPointId());
            /*发送通过*/
            NotificationUtil.sendShowMessageNotification(IUtil.getStrToRes(R.string.notification_show_msg), IUtil.getStrToRes(R.string.notification_show_msg));
            uploadView.uploadTaskSuccess(entity);
        }
    }

    @Override
    public void onUploadTaskFailure(UploadingTable entity) {
        if (uploadView != null) {
            uploadView.uploadTaskFailure(entity);
        }
    }

    @Override
    public void queryWorkRecordSuccess(List<WorkRecordTable> tables) {
        queryWorkRecordView.onQueryWorkRecordSuccess(tables);
    }

    @Override
    public void queryWorkRecordFailure() {
        queryWorkRecordView.onQueryWorkRecordFailure();
    }

    @Override
    public int getWorkType() {
        return workType;
    }

    @Override
    public int getlocalTag() {
        return localTag;
    }
}

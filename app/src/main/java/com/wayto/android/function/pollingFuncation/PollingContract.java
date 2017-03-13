package com.wayto.android.function.pollingFuncation;

import com.wayto.android.db.UploadingTable;
import com.wayto.android.db.WorkRecordTable;

import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.pollingFuncation
 * @Description:
 * @date 2017/1/20 16:34
 */

public interface PollingContract {


    interface InsertWorkRecordView {
        void insertWordRecordSuccess(WorkRecordTable table);

        void insertWorkRecordFailure();
    }

    interface QueryWorkRecordView {
        void onQueryWorkRecordSuccess(List<WorkRecordTable> tables);

        void onQueryWorkRecordFailure();
    }

    interface QueryView {
        void queryUploadTaskSuccess(List<UploadingTable> lists);

        void queryUploadTaskFailure();
    }

    interface UploadView {
        void uploadTaskSuccess(UploadingTable entity);

        void uploadTaskFailure(UploadingTable entity);
    }


    interface Presenter {
        /*插入工作记录*/
        void insertWorkRecord(WorkRecordTable table, boolean isUpload);

        /*查询工作记录*/
        void queryWorkRecord(int workType, int localTag);

        /*插入要上传的任务*/
        void insertUploadTask(UploadingTable table);

        /*删除上传成功的任务*/
        void deleteUploadTask(UploadingTable table);

        /*查询需要上传的任务列表*/
        void queryUploadTaskList();

        /*执行上传任务*/
        void uploadTaskAction(UploadingTable table);

        /*更新工作记录表状态*/
        void updateWorkRecordTaskStatus(WorkRecordTable table);
    }
}

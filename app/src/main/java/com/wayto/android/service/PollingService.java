package com.wayto.android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wayto.android.function.pollingFuncation.PollingContract;
import com.wayto.android.function.pollingFuncation.PollingManage;
import com.wayto.android.function.pollingFuncation.PollingPresent;
import com.wayto.android.function.pollingFuncation.data.source.PollingRemoteRepo;
import com.wayto.android.db.UploadingTable;
import com.wayto.android.utils.ILog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.service
 * @Description:轮询上传Service
 * @date 2017/2/6 14:48
 */

public class PollingService extends Service {
    private final String TAG = getClass().getSimpleName();

    private PollingPresent queryPollingPresent;
    private PollingPresent uploadPollingPresent;
    private List<UploadingTable> uploadingTables;

    public static void startPollingService(Context context) {
        Intent intent = new Intent(context, PollingService.class);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        queryPollingPresent = new PollingPresent(PollingRemoteRepo.newInstance(), new QueryTaskView());
        uploadPollingPresent = new PollingPresent(PollingRemoteRepo.newInstance(), new UploadTaskView());
        uploadingTables = new ArrayList<>();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        queryPollingPresent.queryUploadTaskList();
        return START_STICKY;
    }

    /**
     * 查询任务回调
     */
    class QueryTaskView implements PollingContract.QueryView {
        @Override
        public void queryUploadTaskFailure() {

        }

        @Override
        public void queryUploadTaskSuccess(List<UploadingTable> lists) {
            if (lists == null || lists.size() == 0) {
                ILog.d(TAG, "没有要上传的消息队列");
                stopSelf();
                return;
            }
            if (uploadingTables.size() == 0) {
                uploadingTables = lists;
            } else {
             /*判断要上传的消息队列是否包含有*/
                for (int i = 0; i < lists.size(); i++) {
                    boolean isAdd = true;
                    loob:
                    for (int j = 0; j < uploadingTables.size(); j++) {
                        if (lists.get(i).getId() == uploadingTables.get(j).getId()) {
                            isAdd = false;
                            break loob;
                        }
                    }
                    if (isAdd) {
                        uploadingTables.add(lists.get(i));
                    }
                }
            }
            for (UploadingTable table : uploadingTables) {
                uploadPollingPresent.uploadTaskAction(table);
            }
        }
    }

    /**
     * 上传任务回调
     */
    class UploadTaskView implements PollingContract.UploadView {
        @Override
        public void uploadTaskSuccess(UploadingTable entity) {
            if (entity == null) {
                return;
            }
           /*移除轮询队列*/
            uploadingTables.remove(entity);
            /*更新工作记录状态*/
            PollingManage.getInstance().updateWorkRecordStatus(entity.getWorkRecordTable());
            /*删除轮询数据库数据 */
            PollingManage.getInstance().deleteUploadTask(entity);
            if (uploadingTables.size() == 0) {
                stopSelf();
            }
        }

        @Override
        public void uploadTaskFailure(UploadingTable entity) {

        }
    }
}

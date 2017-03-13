package com.wayto.android.function.mainFuncations.mineModule.data.source;


import com.wayto.android.base.BaseDataSourse;
import com.wayto.android.function.mainFuncations.mineModule.data.AppVersionEntity;

import java.io.File;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.cmcc.ui.mainFunctions.mineModule.data.source
 * @Description:
 * @date 2016/11/17 11:01
 */

public interface CheckAppVersionDataSource extends BaseDataSourse{

    interface CheckAppVersionCallBack  {
        void onCheckAppSuccess(AppVersionEntity entity);

        void onCheckAppDataNotAvailable();
    }

    void checkAppVersion(CheckAppVersionCallBack callBack);

    interface DownloadCallBack {
        String getDownloadURL();

        void onDownloadProgress(int percent);

        void onDownloadComplete(File file);

        void onDownloadDataNotAvailable();

        void onDownloadProgress(long bytesRead, long contentLength, boolean done);
    }

    void downloadApk(DownloadCallBack downloadCallBack);
}

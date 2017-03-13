package com.wayto.android.function.uploadFileFuncation.data.source;

import java.io.File;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.uploadFileFuncation.data.source
 * @Description:
 * @date 2017/2/8 15:09
 */

public interface UploadFileDataSource {

    interface UploadFileCallBack {
        void getUploadFileSuccess(String url);

        void getUploadFileFialure(int code, String msg);
    }

    void uploadFile(File file, UploadFileCallBack callBack);
}

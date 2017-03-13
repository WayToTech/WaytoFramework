package com.wayto.android.function.uploadFileFuncation;

import java.io.File;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.uploadFileFuncation
 * @Description:
 * @date 2017/2/8 15:06
 */

public interface UploadFileContract {

    interface View {
        void onUploadFileStart();

        void onUploadFileEnd();

        void onUploadSuccess(String url);

        void onUploadFialure(int code, String msg);
    }

    interface Presenter{
        void uploadFileAction(File file);
    }
}

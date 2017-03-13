package com.wayto.android.function.refreshLocationFuncation;

import com.wayto.android.function.refreshLocationFuncation.data.UploadLocationEntity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.refreshLocationFuncation
 * @Description:
 * @date 2017/2/8 11:46
 */

public interface UploadLocationContract {

    interface Presenter{
        void refreshLocation(UploadLocationEntity entity);
    }
}

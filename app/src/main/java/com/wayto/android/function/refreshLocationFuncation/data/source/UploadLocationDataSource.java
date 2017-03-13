package com.wayto.android.function.refreshLocationFuncation.data.source;

import com.wayto.android.function.refreshLocationFuncation.data.UploadLocationEntity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.refreshLocationFuncation.data.source
 * @Description:
 * @date 2017/2/8 11:50
 */

public interface UploadLocationDataSource {

    void refreshLocation(UploadLocationEntity entity);
}

package com.wayto.android.function.mapFuncation.data.source;

import com.wayto.android.function.mapFuncation.data.RegeoEntity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mapFuncation.data.source
 * @Description:
 * @date 2017/2/20 17:53
 */

public interface RegeoDataSource {

    interface RegeoCallBack {
        void getRegeoSuccess(RegeoEntity entity);

        void getRegeoFailure(String faliure);

        double getLng();

        double getLat();
    }

    void startRegeo(RegeoCallBack callBack);
}

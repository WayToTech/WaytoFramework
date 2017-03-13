package com.wayto.android.function.mapFuncation;

import com.wayto.android.function.mapFuncation.data.RegeoEntity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mapFuncation
 * @Description:
 * @date 2017/2/20 17:57
 */

public interface RegeoContract {

    interface View {
        void onRegeoStart();

        void onRegeoEnd();

        void onRegeoSuccess(RegeoEntity entity);

        void onRegeoFailure(String error);

        double getLng();

        double getLat();
    }

    interface Presenter {
        void regeoLocation();
    }
}

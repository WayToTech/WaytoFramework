package com.wayto.android.function.mainFuncations.mineModule.CheckAppVersion;

import android.app.Activity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.cmcc.ui.mainFunctions.mineModule
 * @Description:
 * @date 2016/11/17 11:09
 */

public interface CheckAppVersionContract {

    interface View {
        void showRequestFail();

        void showIsLatestVersion();

        void showCheckingDialog();

        void dismissCheckingDialog();

        Activity getActivity();
    }

    interface Presenter {
        void checkAppVersion();
    }
}

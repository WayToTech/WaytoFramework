package com.wayto.android.function.mainFuncations;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.cmcc.ui.mainFunctions
 * @Description:
 * @date 2016/11/16 16:58
 */

public interface MainContract {

    interface MainView {

    }

    interface Presenter {
        /*获取七牛Token*/
        void reqQiNiuToken();
    }
}

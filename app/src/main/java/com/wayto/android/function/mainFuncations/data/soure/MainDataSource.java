package com.wayto.android.function.mainFuncations.data.soure;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.cmcc.ui.mainFunctions.data.source
 * @Description:
 * @date 2016/11/16 17:09
 */

public interface MainDataSource {
    /**
     * 七牛Token
     */
    interface RequestQiNiuTokenCallBack {
        void getQiNiuTokenSuccess(String token);
    }

    void reqQiNiuToken(RequestQiNiuTokenCallBack callBack);
}

package com.wayto.android.function.mainFuncations;

import com.wayto.android.common.Constant;
import com.wayto.android.function.mainFuncations.data.soure.MainDataSource;
import com.wayto.android.function.mainFuncations.data.soure.MainRemoteRepo;
import com.wayto.android.utils.ISpfUtil;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.trackFuncation
 * @Description:
 * @date 2016/11/30 14:04
 */

public class MainPresenter implements MainContract.Presenter, MainDataSource.RequestQiNiuTokenCallBack {

    private MainDataSource mRemoteRepo;
    private MainContract.MainView mainView;

    public MainPresenter(MainRemoteRepo remoteRepo, MainContract.MainView mainView) {
        this.mRemoteRepo = remoteRepo;
        this.mainView = mainView;
    }

    @Override
    public void reqQiNiuToken() {
        mRemoteRepo.reqQiNiuToken(this);
    }

    @Override
    public void getQiNiuTokenSuccess(String token) {
        ISpfUtil.setValue(Constant.QINIU_TOKEN_KEY, token);
    }
}

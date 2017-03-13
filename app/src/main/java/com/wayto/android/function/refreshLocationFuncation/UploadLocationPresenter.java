package com.wayto.android.function.refreshLocationFuncation;

import com.wayto.android.function.refreshLocationFuncation.data.UploadLocationEntity;
import com.wayto.android.function.refreshLocationFuncation.data.source.UploadLocationRemoteRepo;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.refreshLocationFuncation
 * @Description:
 * @date 2017/2/8 11:51
 */

public class UploadLocationPresenter implements UploadLocationContract.Presenter {
    private static UploadLocationPresenter presenter;
    private UploadLocationRemoteRepo remoteRepo;

    public static UploadLocationPresenter getInstance() {
        if (presenter == null) {
            presenter = new UploadLocationPresenter();
        }
        return presenter;
    }

    public UploadLocationPresenter() {
        remoteRepo = UploadLocationRemoteRepo.newInstance();
    }

    @Override
    public void refreshLocation(UploadLocationEntity entity) {
        remoteRepo.refreshLocation(entity);
    }
}

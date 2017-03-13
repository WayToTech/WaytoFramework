package com.wayto.android.function.uploadFileFuncation;

import com.wayto.android.function.uploadFileFuncation.data.source.UploadFileDataSource;
import com.wayto.android.function.uploadFileFuncation.data.source.UploadFileRemoteRepo;

import java.io.File;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.uploadFileFuncation
 * @Description:
 * @date 2017/2/8 15:12
 */

public class UploadFilePresenter implements UploadFileDataSource.UploadFileCallBack, UploadFileContract.Presenter {

    private UploadFileContract.View mView;
    private UploadFileRemoteRepo remoteRepo;

    public UploadFilePresenter(UploadFileContract.View view) {
        this.mView = view;
        this.remoteRepo = UploadFileRemoteRepo.newInstance();
    }

    @Override
    public void uploadFileAction(File file) {
        mView.onUploadFileStart();
        remoteRepo.uploadFile(file, this);
    }


    @Override
    public void getUploadFileSuccess(String url) {
        mView.onUploadSuccess(url);
        mView.onUploadFileEnd();
    }

    @Override
    public void getUploadFileFialure(int code, String msg) {
        mView.onUploadFialure(code, msg);
        mView.onUploadFileEnd();
    }
}

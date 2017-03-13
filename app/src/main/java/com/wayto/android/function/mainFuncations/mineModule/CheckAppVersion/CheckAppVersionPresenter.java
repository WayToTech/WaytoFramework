package com.wayto.android.function.mainFuncations.mineModule.CheckAppVersion;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.wayto.android.base.DataApplication;
import com.wayto.android.common.dialog.DialogFactory;
import com.wayto.android.common.dialog.LoadingDialog;
import com.wayto.android.common.dialog.ToastUtil;
import com.wayto.android.function.mainFuncations.mineModule.data.AppVersionEntity;
import com.wayto.android.function.mainFuncations.mineModule.data.source.CheckAppVersionDataSource;
import com.wayto.android.utils.ISystemUtil;

import java.io.File;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.cmcc.ui.mainFunctions.mineModule
 * @Description:App版本检测Presenter
 * @date 2016/11/17 11:08
 */

public class CheckAppVersionPresenter implements CheckAppVersionDataSource.CheckAppVersionCallBack, CheckAppVersionDataSource.DownloadCallBack, CheckAppVersionContract.Presenter {

    private CheckAppVersionDataSource mRepo;
    private CheckAppVersionContract.View mView;
    private AppVersionEntity mEntity;
    private LoadingDialog mLoadingDialog;

    public CheckAppVersionPresenter(CheckAppVersionDataSource checkAppVersionDataSource, CheckAppVersionContract.View view) {
        this.mRepo = checkAppVersionDataSource;
        this.mView = view;
    }

    @Override
    public void checkAppVersion() {
        mView.showCheckingDialog();
        mRepo.checkAppVersion(this);
    }

    @Override
    public void onCheckAppSuccess(AppVersionEntity entity) {
        mView.dismissCheckingDialog();
        this.mEntity = entity;
        if (entity.getVer() > ISystemUtil.getVersionCode()) {
            final Activity activity = mView.getActivity();
            String[] split = entity.getNote().split("\\|");
            String result = "";
            for (String str : split) {
                result += str + "\n";
            }

            switch (entity.getLevel()) {
                case 1:
                    /*普通更新*/
                    DialogFactory.showMsgDialog(activity, "检测到新版本", result, "现在更新", "暂不更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mLoadingDialog = (LoadingDialog) DialogFactory.createLoadingDialog(activity, "开始下载", true);
                            mRepo.downloadApk(CheckAppVersionPresenter.this);
                        }
                    }, null);
                    break;
                case 2:
                    /*强制更新*/
                    Dialog dialog = DialogFactory.warningDialog(activity, "检测到新版本", result, "立即更新", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mLoadingDialog = (LoadingDialog) DialogFactory.createLoadingDialog(activity, "开始下载", true);
                            mRepo.downloadApk(CheckAppVersionPresenter.this);
                        }
                    });
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            return true;
                        }
                    });
                    break;
            }
        } else {
            mView.showIsLatestVersion();
        }
    }

    @Override
    public void onCheckAppDataNotAvailable() {
        mView.dismissCheckingDialog();
        mView.showRequestFail();
    }

    @Override
    public String getDownloadURL() {
        return mEntity.getUrl();
    }

    @Override
    public void onDownloadProgress(int percent) {
        mLoadingDialog.setTipText("下载中.." + percent + "%");
        Log.e("CheckAppVersion", percent + "");
    }

    @Override
    public void onDownloadComplete(final File file) {
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingDialog.dismiss();
                ISystemUtil.installAPK(DataApplication.getInstance(), file.getAbsolutePath());
            }
        });
    }

    @Override
    public void onDownloadDataNotAvailable() {
        mView.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingDialog.dismiss();
                ToastUtil.showToast(DataApplication.getInstance(), "下载失败");
            }
        });
    }

    @Override
    public void onDownloadProgress(long bytesRead, long contentLength, boolean done) {

    }
}

package com.wayto.android.function.pictureFuncation;

import com.wayto.android.function.pictureFuncation.data.PictureEntity;
import com.wayto.android.function.pictureFuncation.data.soure.PictureDateSource;
import com.wayto.android.function.pictureFuncation.data.soure.PictureRemoteRepo;

import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.deviceFuncations.picture
 * @Description:
 * @date 2016/12/29 11:41
 */

public class PicturePresenter implements PictureDateSource.QueryAlbumCallBack, PictureContract.Presenter {

    private PictureContract.View mView;
    private PictureRemoteRepo mRemoteRepo;

    public PicturePresenter(PictureContract.View view) {
        this.mView = view;
        this.mRemoteRepo = PictureRemoteRepo.newInstance();
    }

    @Override
    public void queryAlbum() {
        mView.onQueryAlbumStart();
        mRemoteRepo.queryAlbumAction(this);
    }

    @Override
    public void getAlbumSuccess(List<PictureEntity> lists) {
        mView.queryAlbumSuccess(lists);
        mView.onQueryAlbumEnd();
    }

    @Override
    public void getAlbumFailure() {
        mView.queryAlbumFailure();
        mView.onQueryAlbumEnd();
    }
}

package com.wayto.android.function.pictureFuncation;

import com.wayto.android.function.pictureFuncation.data.PictureEntity;

import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.deviceFuncations.picture
 * @Description:
 * @date 2016/12/29 11:30
 */

public interface PictureContract {

    interface View {
        void onQueryAlbumStart();

        void queryAlbumSuccess(List<PictureEntity> lists);

        void queryAlbumFailure();

        void onQueryAlbumEnd();
    }

    interface Presenter {
        void queryAlbum();
    }
}

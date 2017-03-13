package com.wayto.android.function.pictureFuncation;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.wayto.android.function.pictureFuncation.data.PictureEntity;
import com.wayto.android.utils.BitmapUtil;
import com.wayto.android.utils.IFileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.pictureFuncation
 * @Description:图片压缩处理管理类
 * @date 2017/2/20 15:53
 */

public class PictureCompressManager {

    private static PictureCompressManager pictureCompressManager;

//    private Dialog dialog;

    public static PictureCompressManager getInstance() {
        if (pictureCompressManager == null) {
            pictureCompressManager = new PictureCompressManager();
        }
        return pictureCompressManager;
    }

    /**
     * 图片压缩
     *
     * @param activity
     * @param list
     * @param compressCallBack
     */
    public void startCompressPictureToEntity(Activity activity, List<PictureEntity> list, PictureCompressCallBack compressCallBack) {
        photoCut(activity, list, null, 0, compressCallBack);
    }

    /**
     * 图片压缩
     *
     * @param activity
     * @param entity
     * @param compressCallBack
     */
    public void startCompressPictureToEntity(Activity activity, PictureEntity entity, ImageView imageView, int position, PictureCompressCallBack compressCallBack) {
        List<PictureEntity> entities = new ArrayList<>();
        entities.add(entity);
        photoCut(activity, entities, imageView, position, compressCallBack);
    }


    /**
     * 图片压缩
     *
     * @param activity
     * @param list
     * @param compressCallBack
     */
    private void photoCut(final Activity activity, final List<PictureEntity> list, final ImageView imageView, final int position, final PictureCompressCallBack compressCallBack) {
        if (list == null && list.size() == 0) {
            return;
        }
//        dialog = DialogFactory.createLoadingDialog(activity, R.string.img_cro);
        if (compressCallBack != null) {
            compressCallBack.onPictureCompressStart();
        }
        new Thread() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < list.size(); i++) {
                        final String path = list.get(i).getUrl();
                        final String savePath = IFileUtils.getImageCatchDir();
                        final String photoPath = BitmapUtil.compressBitmap(path, savePath, list.get(i).getDate());
                        if (!TextUtils.isEmpty(photoPath)) {
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (compressCallBack != null) {
                                        compressCallBack.onPictureCompressResult(position, imageView, path, savePath);
                                    }
                                }
                            });
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            DialogFactory.dimissDialog(dialog);
                            compressCallBack.onPictureCompressEnd();
                        }
                    });
                }
            }
        }.start();
    }


    public interface PictureCompressCallBack {
        void onPictureCompressStart();

        void onPictureCompressEnd();

        void onPictureCompressResult(int position, ImageView imageView, String oldPath, String path);
    }
}

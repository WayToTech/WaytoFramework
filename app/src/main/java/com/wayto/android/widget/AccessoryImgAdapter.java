package com.wayto.android.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.wayto.android.R;
import com.wayto.android.base.ArrayListAdapter;
import com.wayto.android.function.pictureFuncation.PictureCompressManager;
import com.wayto.android.function.pictureFuncation.data.PictureEntity;
import com.wayto.android.utils.IDensityUtil;
import com.wayto.android.utils.ILog;
import com.wayto.android.utils.IUtil;

/**
 * @Package: com.yunwei.zaina.ui.adapter
 * @Description:附件图片适配器
 * @author: Aaron
 * @date: 2016-06-14
 * @Time: 19:00
 * @version: V1.0
 */
public class AccessoryImgAdapter extends ArrayListAdapter<PictureEntity> {
    private final String TAG = getClass().getSimpleName();

    public static final String ADD_IMG_FLAG = "add:";
    private boolean isShowCloseIcon = true;

    public AccessoryImgAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHodler hodler;
        if (convertView == null) {
            hodler = new ViewHodler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.accessory_gridview_item, null);
            hodler.imageView = (ImageView) convertView.findViewById(R.id.accessory_imageview);
            hodler.closeIv = (ImageView) convertView.findViewById(R.id.close_iv);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(IDensityUtil.getScreenW(mContext) / 5, IDensityUtil.getScreenW(mContext) / 5);
            params.rightMargin = 5;
            params.topMargin = 5;
            hodler.imageView.setLayoutParams(params);

            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }

        if (mList.get(position).getUrl().startsWith(ADD_IMG_FLAG)) {
            Glide.with(mContext).load(R.mipmap.icon_add_btn).into(hodler.imageView);
            hodler.closeIv.setVisibility(View.GONE);
        } else {
            if (!TextUtils.isEmpty(mList.get(position).getNewUrl())) {
                Glide.with(mContext).load(IUtil.fitterUrl(mList.get(position).getNewUrl())).error(R.mipmap.main_img_defaultpic_small).into(hodler.imageView);
            } else {
                if (mList.get(position).isSelecte()) {
                    mList.get(position).setSelecte(false);
                    ILog.d(TAG, "position==" + position);
                    PictureCompressManager.getInstance().startCompressPictureToEntity(mContext, mList.get(position), hodler.imageView, position, new PictureCompressManager.PictureCompressCallBack() {

                        @Override
                        public void onPictureCompressStart() {

                        }

                        @Override
                        public void onPictureCompressResult(int position, ImageView imageView, String oldPath, String path) {
                            mList.get(position).setNewUrl(path);
                            Glide.with(mContext).load(IUtil.fitterUrl(path)).error(R.mipmap.main_img_defaultpic_small).into(imageView);
//                    hodler.closeIv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onPictureCompressEnd() {

                        }
                    });
                }
            }
        }

        if (isShowCloseIcon && !mList.get(position).getUrl().startsWith(ADD_IMG_FLAG)) {
            hodler.closeIv.setVisibility(View.VISIBLE);
        } else {
            hodler.closeIv.setVisibility(View.GONE);
        }


        hodler.closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removePos(position);
                if (mList.size() == AccessoryView.DEFAULT_PICTURE_NUMBER && !mList.contains(ADD_IMG_FLAG)) {
                    PictureEntity entity = new PictureEntity();
                    entity.setUrl(ADD_IMG_FLAG);
                    mList.add(entity);
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private class ViewHodler {
        private ImageView imageView;
        private ImageView closeIv;
    }

    public void setShowCloseIcon(boolean showCloseIcon) {
        this.isShowCloseIcon = showCloseIcon;
    }
}

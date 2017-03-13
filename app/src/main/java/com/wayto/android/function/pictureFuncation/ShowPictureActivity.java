package com.wayto.android.function.pictureFuncation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.wayto.android.R;
import com.wayto.android.base.BaseActivity;
import com.wayto.android.function.pictureFuncation.data.PictureEntity;
import com.wayto.android.utils.ISkipActivityUtil;
import com.wayto.android.utils.IUtil;
import com.wayto.android.vendor.photoView.PhotoView;
import com.wayto.android.vendor.photoView.PhotoViewAttacher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.deviceFuncations.picture
 * @Description:图片查看类
 * @date 2017/1/4 16:23
 */

public class ShowPictureActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.ShowImage_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.ShowImage_index_textView)
    TextView mIndexTextView;

    private static List<PictureEntity> images;
    private static int position;

    /**
     * 跳转查看图片
     *
     * @param context
     * @param url
     */
    public static void startIntent(Context context, String url) {
        List<PictureEntity> list = new ArrayList<>();
        PictureEntity entity = new PictureEntity();
        entity.setUrl(url);
        list.add(entity);
        startIntent(context, list, 0);
    }

    /**
     * 图片查看
     *
     * @param content
     * @param paths
     */
    public static void startIntent(Context content, ArrayList<String> paths) {
        List<PictureEntity> list = new ArrayList<>();
        if (paths != null) {
            for (String path : paths) {
                PictureEntity entity = new PictureEntity();
                entity.setUrl(path);
                list.add(entity);
            }
            startIntent(content, list, 0);
        }
    }

    /**
     * 图片查看
     *
     * @param content
     * @param paths
     */
    public static void startIntent(Context content, ArrayList<String> paths, int currentPosion) {
        List<PictureEntity> list = new ArrayList<>();
        if (paths != null) {
            for (String path : paths) {
                PictureEntity entity = new PictureEntity();
                entity.setUrl(path);
                list.add(entity);
            }
            startIntent(content, list, currentPosion);
        }
    }

    /**
     * 跳转查看图片
     *
     * @param context
     * @param entity
     */
    public static void startIntent(Context context, PictureEntity entity) {
        List<PictureEntity> list = new ArrayList<>();
        list.add(entity);
        startIntent(context, list, 0);
    }

    /**
     * 跳转查看图片
     *
     * @param context
     * @param list
     */
    public static void startIntent(Context context, List<PictureEntity> list, int currentPosion) {
        images = list;
        position = currentPosion;
        ISkipActivityUtil.startIntent(context, ShowPictureActivity.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_show_picture);
        setSwipeEnabled(false);
        setToolbarVisibility(View.GONE);
        ButterKnife.bind(this);
        initViewPager();
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        mViewPager.setAdapter(new ShowImagePagerAdapter(images));
        mViewPager.addOnPageChangeListener(this);
        mIndexTextView.setText((position + 1) + "/" + images.size());
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mIndexTextView.setText((position + 1) + "/" + images.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class ShowImagePagerAdapter extends PagerAdapter {

        private List<PictureEntity> list;

        public ShowImagePagerAdapter(List<PictureEntity> list) {
            if (list == null) {
                this.list = new ArrayList<>();
            } else
                this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View imageLayout = getLayoutInflater().inflate(R.layout.item_show_picture, container, false);
            PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.item_showPicture_imageView);
            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.item_showPicture_progressBar);
            imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    ShowPictureActivity.this.finish();
                }

                @Override
                public void onOutsidePhotoTap() {
                    ShowPictureActivity.this.finish();
                }
            });
            Glide.with(ShowPictureActivity.this).load(IUtil.fitterUrl(list.get(position).getUrl())).placeholder(R.mipmap.main_img_defaultpic_small).error(R.mipmap.main_img_defaultpic_small).into(new GlideDrawableImageViewTarget(imageView) {
                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    super.onLoadFailed(e, errorDrawable);
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                    super.onResourceReady(resource, animation);
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadStarted(Drawable placeholder) {
                    super.onLoadStarted(placeholder);
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onStop() {
                    super.onStop();
                    spinner.setVisibility(View.GONE);
                }
            });
            PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
            attacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    ShowPictureActivity.this.finish();
                }
            });
            container.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}

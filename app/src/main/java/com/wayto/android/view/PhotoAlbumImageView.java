package com.wayto.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 图片尺寸设置
 */
public class PhotoAlbumImageView extends ImageView {
    private OnMeasureListener onMeasureListener;

    public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
        this.onMeasureListener = onMeasureListener;
    }

    public PhotoAlbumImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotoAlbumImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (onMeasureListener != null) {
            onMeasureListener.onMeasureSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public interface OnMeasureListener {
        void onMeasureSize(int width, int height);
    }

}

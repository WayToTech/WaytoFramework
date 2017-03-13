package com.wayto.android.view;

import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.wayto.android.base.DataApplication;

/**
 * @author huangyue
 * @version V1.0
 * @Package com.yunwei.cmcc.view
 * @Description: 将bitmap切割成圆形的Target,适用于Glide框架中的into()
 * @date 2016/10/31 10:42
 */

public class RoundedBitmapImageViewTarget extends BitmapImageViewTarget {

    public RoundedBitmapImageViewTarget(ImageView view){
        super(view);
    }

    @Override
    protected void setResource(Bitmap resource) {
        RoundedBitmapDrawable circularBitmapDrawable =
                RoundedBitmapDrawableFactory.create(DataApplication.getInstance().getResources(), resource);
        circularBitmapDrawable.setCircular(true);
        view.setImageDrawable(circularBitmapDrawable);
    }
}

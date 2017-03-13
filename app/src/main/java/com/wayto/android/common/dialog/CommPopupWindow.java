package com.wayto.android.common.dialog;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Created by SEEBOK on 2016/8/11.
 */
public class CommPopupWindow extends PopupWindow {

    public CommPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height,true);

        /**
         * 设置 PopupWindow 可点击内部操作
         * 设置透明背景，让PopupWindow点击外部消失
         * 设置 PopupWindow's contentView 的可点击操作
         */

        setTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0x55000000));
        getContentView().setFocusableInTouchMode(true);
        getContentView().setFocusable(true);
        setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);

    }

    public void showAtButton(View parent){
        showAtLocation(parent, Gravity.BOTTOM,0,0);
    }
}

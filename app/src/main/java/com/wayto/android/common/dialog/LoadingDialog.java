package com.wayto.android.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wayto.android.R;


/**
 * @Package: com.yunwei.zaina.common.dialog
 * @Description:加载对话框
 * @author: Aaron
 * @date: 2016-06-06
 * @Time: 09:20
 * @version: V1.0
 */
public class LoadingDialog extends Dialog {
    /**
     * 提示内容控件
     */
    private TextView tipText;
    private LinearLayout barView;

    private boolean backPressCancel;

    public LoadingDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.getContext().setTheme(R.style.dialog);
        super.setContentView(R.layout.dialog_progress_layout);

        tipText = (TextView) findViewById(R.id.loading_tv);
        barView = (LinearLayout) findViewById(R.id.loading_bar);

        Window window = getWindow();
        WindowManager.LayoutParams attributesParams = window.getAttributes();
        attributesParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        attributesParams.dimAmount = 0.6f;
        int width = (int) (window.getWindowManager().getDefaultDisplay()
                .getWidth() * 0.8f);
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 设置内容
     *
     * @param msg
     */
    public void setTipText(String msg) {
        if (TextUtils.isEmpty(msg))
            return;

        tipText.setText(msg);
    }

    /**
     * 设置内容
     *
     * @param resid
     */
    public void setTipText(int resid) {
        if (resid <= 0) {
            return;
        }
        tipText.setText(resid);
    }

    public void setBackPressCancel(boolean backPressCancel) {
        this.backPressCancel = backPressCancel;
    }

    public void setProgressVisibility(int visibility) {
        barView.setVisibility(visibility);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                || keyCode == KeyEvent.KEYCODE_SEARCH) {
            if (backPressCancel) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}

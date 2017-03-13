package com.wayto.android.base;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.wayto.android.common.dialog.ToastUtil;
import com.wayto.android.common.eventbus.NoticeEvent;
import com.wayto.android.common.handler.BaseHandler;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.base
 * @Description:基类Fragment
 * @date 2016/11/22 14:57
 */

public class BaseFragment extends Fragment {

    /**
     * 消息处理Handler
     */
    protected BaseHandler mHandler;
    /*定义加载Dialog*/
    protected Dialog loadDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initHandler();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化Handler
     */
    private void initHandler() {
        mHandler = new BaseHandler(getActivity()) {
            @Override
            public void handleMessage(Message msg) {
                BaseFragment.this.dispatchMessage(msg);
            }
        };
    }

    /**
     * Handler事件分发处理
     *
     * @param msg
     */
    protected void dispatchMessage(Message msg) {
    }

    /**
     * Toast
     *
     * @param resid
     */
    public void showToast(int resid) {
        ToastUtil.showToast(getActivity(), resid);
    }

    /**
     * Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtil.showToast(getActivity(), msg);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackGroundUserEvent(NoticeEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainUserEvent(NoticeEvent event) {

    }
}

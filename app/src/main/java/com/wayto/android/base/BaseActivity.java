package com.wayto.android.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wayto.android.R;
import com.wayto.android.common.dialog.ToastUtil;
import com.wayto.android.common.eventbus.NoticeEvent;
import com.wayto.android.common.handler.BaseHandler;
import com.wayto.android.utils.IActivityManage;
import com.wayto.android.widget.SwipeBackLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.base
 * @Description:基类Activity
 * @date 2016/11/22 14:57
 */

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    /**
     * Toolbar
     */
    private Toolbar mToolbar;
    /**
     * ActionBar
     */
    private ActionBar mActionBar;
    /**
     * ToolBar中间标题
     */
    private TextView mTitleCenterTextView;
    /**
     * ToolBar右边按钮Layout
     */
    private LinearLayout mToolbarMoreLayout;
    /**
     * ToolBar右边TextView
     */
    private TextView mToolbarRightText;
    /**
     * ToolBar右边ImageView
     */
    private ImageView mToolbarRightIV;
    /**
     * 布局实例器
     */
    protected LayoutInflater mLayoutInflater;
    /**
     * 核心内容
     */
    private FrameLayout mLinearLayoutContent;
    /**
     * 侧滑finish
     */
    private SwipeBackLayout swipeBackLayout;

    /**
     * 软件盘管理类
     */
    protected InputMethodManager mInput;
    /**
     * 消息处理Handler
     */
    protected BaseHandler mHandler;

    protected Dialog loadDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_base);
        EventBus.getDefault().register(this);
        IActivityManage.getInstance().addActivity(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void init() {
        //实例化Activity侧滑Finish()
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.replaceLayer(this);

        mInput = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);

        findViewById();
        initHandler();
        initListener();
    }

    /**
     * 初始控件
     */
    private void findViewById() {
        mLayoutInflater = LayoutInflater.from(this);
        mLinearLayoutContent = (FrameLayout) findViewById(R.id.base_activity_container);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_title);
        mToolbarMoreLayout = (LinearLayout) findViewById(R.id.toolbar_more_layout);
        mToolbarRightIV = (ImageView) findViewById(R.id.toolbar_more_add_icon);
        mToolbarRightText = (TextView) findViewById(R.id.toolbar_more_text);
        mTitleCenterTextView = (TextView) findViewById(R.id.toolbar_center_title_tv);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, android.R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        if (swipeBackLayout.isSwipeFinished()) {
            super.finish();
            overridePendingTransition(0, 0);
        } else {
            swipeBackLayout.cancelPotentialAnimation();
            super.finish();
            overridePendingTransition(0, R.anim.slide_out_right);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = mLayoutInflater.inflate(layoutResID, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLinearLayoutContent.removeAllViews();
        mLinearLayoutContent.addView(view, lp);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mLinearLayoutContent.removeAllViews();
        mLinearLayoutContent.addView(view, params);
    }

    @Override
    public void setContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLinearLayoutContent.removeAllViews();
        mLinearLayoutContent.addView(view, lp);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onBackGroundUserEvent(NoticeEvent event) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMainUserEvent(NoticeEvent event) {

    }

    /**
     * 设置监听器
     */
    private void initListener() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToolbarLeftButton();
            }
        });
        mToolbarMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickToolbarRightLayout();
            }
        });
    }

    /**
     * 初始化Handler
     */
    private void initHandler() {
        mHandler = new BaseHandler(BaseActivity.this) {
            @Override
            public void handleMessage(Message msg) {
                BaseActivity.this.dispatchMessage(msg);
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
     * 设置Toolbar返回Btn监听
     */
    public void onClickToolbarLeftButton() {
        this.finish();
    }

    /**
     * 设置Toolbar右边Layout监听
     */
    public void onClickToolbarRightLayout() {
    }

    /**
     * 设置Toolbar标题
     *
     * @param resid
     */
    public void setToolbarTitle(int resid) {
        mToolbar.setTitle(resid);
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar标题
     *
     * @param str
     */
    public void setToolbarTitle(String str) {
        mToolbar.setTitle(str);
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置中间标题
     *
     * @param resId
     */
    public void setToolbarCenterTitle(int resId) {
        mToolbar.setTitle("");
        mTitleCenterTextView.setText(resId);
        mTitleCenterTextView.setVisibility(View.VISIBLE);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 设置中间标题
     *
     * @param title
     */
    public void setToolbarCenterTitle(String title) {
        mToolbar.setTitle("");
        mTitleCenterTextView.setText(title);
        mTitleCenterTextView.setVisibility(View.VISIBLE);
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 设置Toolbar显示状态
     *
     * @param visibility
     */
    public void setToolbarVisibility(int visibility) {
        mToolbar.setVisibility(visibility);
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar右边Layout文字
     *
     * @param resid
     */
    public void setToolbarRightText(int resid) {
        mToolbarRightText.setText(resid);
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar右边Layout文字
     *
     * @param str
     */
    public void setToolbarRightText(String str) {
        mToolbarRightText.setText(str);
        mToolbarRightText.setVisibility(View.VISIBLE);
        mToolbarRightIV.setVisibility(View.GONE);
    }

    /**
     * 设置Toolbar右边Layout图片资源
     *
     * @param resid
     */
    public void setToolbarRightImage(int resid) {
        mToolbarRightIV.setImageResource(resid);
        mToolbarRightText.setVisibility(View.GONE);
    }

    /**
     * 设置Activity是否支持滑动Finsh
     *
     * @param enabled
     */
    public void setSwipeEnabled(boolean enabled) {
        swipeBackLayout.setSwipeEnabled(enabled);
    }

    /**
     * 显示软键盘
     *
     * @param edittext
     */
    public void showSoftInput(final EditText edittext) {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                edittext.requestFocus();
                if (mInput != null) mInput.showSoftInput(edittext, 0);
            }
        }, 700);
    }

    /**
     * 收起软键盘
     *
     * @param et
     */
    public void hideSoftInput(EditText et) {
        if (null != mInput && mInput.isActive()) {
            mInput.hideSoftInputFromWindow(et.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * Toast
     *
     * @param resid
     */
    public void showToast(int resid) {
        ToastUtil.showToast(this, resid);
    }

    /**
     * Toast
     *
     * @param msg
     */
    public void showToast(String msg) {
        ToastUtil.showToast(this, msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

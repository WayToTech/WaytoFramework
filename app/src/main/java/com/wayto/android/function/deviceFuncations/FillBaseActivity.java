package com.wayto.android.function.deviceFuncations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.wayto.android.R;
import com.wayto.android.base.BaseActivity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.deviceFuncations
 * @Description:上报的BaseActivity
 * @date 2017/2/20 14:31
 */

public abstract class FillBaseActivity extends BaseActivity {
    public static final String TITLE_FLAG="title_flage";

    private FragmentManager manager;
    private FragmentTransaction transaction;
    protected FillBaseFragment mFillFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_base);
        setSwipeEnabled(false);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
         /* 获取数据包，并初始化UI */
        Bundle bundle = getIntent().getExtras();
        mFillFragment = getFillingFragment(bundle);
        initUI(bundle);
    }

    /**
     * 初始化UI
     */
    private void initUI(Bundle bundle) {
        setToolbarTitle(getHeadTitle());
        transaction.replace(R.id.fill_content_fl, mFillFragment);
        transaction.commit();
    }

    /**
     * 获取填充的Fragment
     *
     * @return
     */
    public abstract FillBaseFragment getFillingFragment(Bundle bundle);

    /**
     * CBOK:子类设置标题值
     *
     * @return
     */
    public abstract String getHeadTitle();

    /**
     * 图片处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFillFragment.onActivityResult(requestCode, resultCode, data);
    }
}

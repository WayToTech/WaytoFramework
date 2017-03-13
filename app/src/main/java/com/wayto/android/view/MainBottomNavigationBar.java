package com.wayto.android.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import com.wayto.android.R;
import com.wayto.android.base.BaseFragment;
import com.wayto.android.widget.bottomnavigation.BadgeItem;
import com.wayto.android.widget.bottomnavigation.BottomNavigationBar;
import com.wayto.android.widget.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.view
 * @Description:主界面Bottom导航栏
 * @date 2016/11/22 18:06
 */

public class MainBottomNavigationBar extends BottomNavigationBar implements BottomNavigationBar.OnTabSelectedListener {
    /**
     * Fragment集合
     */
    private List<BaseFragment> fragments;
    /**
     * Activity
     */
    private AppCompatActivity activity;
    /**
     * 容器Id
     */
    private int containerId;

    private List<Integer> mIconResources;
    private List<Integer> mTitleResources;

    private BottomTabSelectedListener tabSelectedListener;

    public MainBottomNavigationBar(Context context) {
        super(context);
        init();
    }

    public MainBottomNavigationBar(Context context, AttributeSet attri) {
        super(context, attri);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        this.mIconResources = new ArrayList<>();
        this.mTitleResources = new ArrayList<>();
        setDefaultConfig();
        setTabSelectedListener(this);
    }

    /**
     * 初始配置
     */
    private void setDefaultConfig() {
        setMode(BottomNavigationBar.MODE_FIXED);
        setBackgroundResource(R.color.white);
        setActiveColor(R.color.white);
    }

    /**
     * 初始化配置
     *
     * @param activity
     * @param containerId
     */
    public void initConfig(AppCompatActivity activity, int containerId) {
        this.activity = activity;
        this.containerId = containerId;
        this.fragments = new ArrayList<>();
    }

    /**
     * 添加Fragment
     *
     * @param fragment
     * @return
     */
    public MainBottomNavigationBar addFragment(BaseFragment fragment) {
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.add(fragment);

        FragmentManager fm = activity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(containerId, fragment);
        transaction.commit();
        return this;
    }


    /**
     * 添加TabItem
     *
     * @param iconResId
     * @param nameResId
     * @return
     */
    public MainBottomNavigationBar addTabItem(int iconResId, int nameResId) {
        addItem(new BottomNavigationItem(iconResId, nameResId)).setActiveColor(R.color.tab_active).initialise();

        mIconResources.add(iconResId);
        mTitleResources.add(nameResId);
        return this;
    }

    /**
     * set Tab sign
     *
     * @param tabPosition
     * @param number
     */
    public void addTabSign(int tabPosition, int number) {
        int iconResource = mIconResources.get(tabPosition);
        int titleResource = mTitleResources.get(tabPosition);
        if (number > 0) {
            BadgeItem item = new BadgeItem();
            item.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            if (number > 99) {
                item.setText("99+");
            } else {
                item.setText(String.valueOf(number));
            }
            removeItem(tabPosition).addItem(tabPosition, new BottomNavigationItem(iconResource, titleResource).setBadgeItem(item)).setActiveColor(R.color.tab_active).initialise();
        } else {
            removeItem(tabPosition).addItem(tabPosition, new BottomNavigationItem(iconResource, titleResource)).setActiveColor(R.color.tab_active).initialise();
        }
    }

    /**
     * remove Tab sign
     *
     * @param tabPosition
     */
    public void removeTabSign(int tabPosition) {
        int iconResource = mIconResources.get(tabPosition);
        int titleResource = mTitleResources.get(tabPosition);
        removeItem(tabPosition).addItem(tabPosition, new BottomNavigationItem(iconResource, titleResource)).setActiveColor(R.color.tab_active).initialise();
    }

    /**
     * 设置默认选中的Tab
     * @param position
     */
    public void setFirstSelectedTab(int position) {
        switchTab(position);
        setFirstSelectedPosition(position).initialise();
        onTabSelected(position);
    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabSelected(int position) {
        switchTab(position);
        if (tabSelectedListener != null) {
            tabSelectedListener.onTabSelected(position);
        }
    }

    /**
     * Tab切换
     *
     * @param position
     */
    private void switchTab(int position) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        transaction.show(fragments.get(position));
        transaction.commit();
    }

    /**
     * hide Fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        for (BaseFragment fragment : fragments) {
            transaction.hide(fragment);
        }
    }

    /**
     * set listener
     *
     * @param listener
     */
    public void setTabSelectedListener(BottomTabSelectedListener listener) {
        this.tabSelectedListener = listener;
    }

    public interface BottomTabSelectedListener {
        void onTabSelected(int position);
    }
}

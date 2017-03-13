package com.wayto.android.function.mainFuncations.recordModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wayto.android.R;
import com.wayto.android.base.BaseFragment;
import com.wayto.android.function.mainFuncations.recordModule.adapter.RecordViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.homeFuncation
 * @Description:历史记录主界面
 * @date 2016/11/22 18:12
 */

public class RecordFragment extends BaseFragment {

    private static RecordFragment fragment;

    @BindView(R.id.record_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.record_viewPager)
    ViewPager mViewPager;

    private String[] tabNames;
    private int[] tabIcons;

    public static RecordFragment newInstance() {
        if (fragment == null) {
            fragment = new RecordFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tabNames = getResources().getStringArray(R.array.record_tab);
        tabIcons = new int[]{R.mipmap.icon_record_tab_task, R.mipmap.icon_record_tab_device, R.mipmap.icon_record_tab_hidden, R.mipmap.icon_record_tab_maintain};
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment_record, null);
        ButterKnife.bind(this, rootView);
        initTabLayout();
        return rootView;
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        mViewPager.setAdapter(new RecordViewPagerAdapter(getChildFragmentManager(), tabIcons.length));
        mViewPager.setOffscreenPageLimit(tabIcons.length);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        initTabView(mTabLayout);
    }

    /**
     * 初始化TabView
     *
     * @param layout
     */
    private void initTabView(TabLayout layout) {
        for (int i = 0; i < layout.getTabCount(); i++) {
            TabLayout.Tab tab = layout.getTabAt(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.record_top_tab_layout, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.record_top_tab_icon);
            TextView textView = (TextView) view.findViewById(R.id.record_top_tab_name);
            textView.setText(tabNames[i]);
            imageView.setImageResource(tabIcons[i]);
            tab.setCustomView(view);
        }
    }
}

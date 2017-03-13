package com.wayto.android.function.mainFuncations.missionModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wayto.android.R;
import com.wayto.android.base.BaseFragment;
import com.wayto.android.function.mainFuncations.missionModule.adapter.MissionViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.homeFuncation
 * @Description:任务主界面
 * @date 2016/11/22 18:12
 */

public class MissionFragment extends BaseFragment {

    private static MissionFragment fragment;
    @BindView(R.id.FragmentMission_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.FragmentMission_ViewPager)
    ViewPager mViewPager;

    private String missionModuleArray[];

    public static MissionFragment newInstance() {
        if (fragment == null) {
            fragment = new MissionFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        missionModuleArray = getResources().getStringArray(R.array.mission_module);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment_mission, null);
        ButterKnife.bind(this, rootView);
        initTabLayout();
        return rootView;
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        mViewPager.setAdapter(new MissionViewPagerAdapter(getChildFragmentManager(), missionModuleArray.length));
        mViewPager.setOffscreenPageLimit(missionModuleArray.length);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        initTabView(mTabLayout);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.colorPrimary));
                ((TextView) tab.getCustomView()).setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView()).setTextColor(getResources().getColor(R.color.gray));
                ((TextView) tab.getCustomView()).setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 初始化TabView
     *
     * @param layout
     */
    private void initTabView(TabLayout layout) {
        for (int i = 0; i < layout.getTabCount(); i++) {
            TabLayout.Tab tab = layout.getTabAt(i);
            View view = LayoutInflater.from(getContext()).inflate(R.layout.tab_mission_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.TabMission_textView);
            textView.setText(missionModuleArray[i]);
             /*设置默认选择*/
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.colorPrimary));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            }
            tab.setCustomView(textView);
        }
    }
}

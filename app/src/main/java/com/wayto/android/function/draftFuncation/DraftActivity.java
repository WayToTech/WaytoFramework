package com.wayto.android.function.draftFuncation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wayto.android.R;
import com.wayto.android.base.BaseActivity;
import com.wayto.android.function.draftFuncation.adater.DraftViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.draftFuncation
 * @Description:我的草稿
 * @date 2017/1/16 15:30
 */

public class DraftActivity extends BaseActivity {

    @BindView(R.id.DraftActivity_tabLayout)
    TabLayout DraftActivityTabLayout;
    @BindView(R.id.DraftActivity_ViewPager)
    ViewPager DraftActivityViewPager;

    private String missionModuleArray[];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_draft);
        setToolbarTitle(R.string.title_draft);
        ButterKnife.bind(this);
        missionModuleArray = getResources().getStringArray(R.array.draft_tab);
        initTabLayout();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        DraftActivityViewPager.setAdapter(new DraftViewPagerAdapter(getSupportFragmentManager(), missionModuleArray.length));
        DraftActivityViewPager.setOffscreenPageLimit(missionModuleArray.length);
        DraftActivityTabLayout.setupWithViewPager(DraftActivityViewPager);
        DraftActivityTabLayout.setTabMode(TabLayout.MODE_FIXED);
        initTabView(DraftActivityTabLayout);
        DraftActivityTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
            View view = LayoutInflater.from(this).inflate(R.layout.tab_mission_layout, null);
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

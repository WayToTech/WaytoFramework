package com.wayto.android.function.mainFuncations.recordModule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wayto.android.function.mainFuncations.recordModule.fragment.DeviceRecordFragment;
import com.wayto.android.function.mainFuncations.recordModule.fragment.HiddenRecordFragment;
import com.wayto.android.function.mainFuncations.recordModule.fragment.MaintainRecordFragment;
import com.wayto.android.function.mainFuncations.recordModule.fragment.MissionRecordFragment;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.recordFuncation
 * @Description:历史记录ViewPager adapter
 * @date 2016/12/7 13:55
 */

public class RecordViewPagerAdapter extends FragmentPagerAdapter {
    private final int TAB_MISSION=0;
    private final int TAB_DEVICE=1;
    private final int TAB_HIDDEN=2;
    private final int TAB_MAINTAIN=3;

    private int tabNumbers;

    public RecordViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public RecordViewPagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabNumbers = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case TAB_MISSION:
                fragment=new MissionRecordFragment();
                break;
            case TAB_DEVICE:
                fragment=new DeviceRecordFragment();
                break;
            case TAB_HIDDEN:
                fragment=new HiddenRecordFragment();
                break;
            case TAB_MAINTAIN:
                fragment=new MaintainRecordFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabNumbers;
    }
}

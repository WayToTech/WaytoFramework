package com.wayto.android.function.mainFuncations.missionModule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.wayto.android.function.mainFuncations.missionModule.MissionUnauditedFragment;
import com.wayto.android.function.mainFuncations.missionModule.MissionUntreatedFragment;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.missionModule.adapter
 * @Description:
 * @date 2017/3/7 20:06
 */

public class MissionViewPagerAdapter extends FragmentPagerAdapter {
    public static final int MISSION_UNTREATED = 0;/*待处理*/
    public static final int MISSION_UNAUDITED = 1;/*待审核*/

    private int tabNumbers;

    public MissionViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MissionViewPagerAdapter(FragmentManager fm, int tabNumbers) {
        super(fm);
        this.tabNumbers = tabNumbers;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MISSION_UNTREATED:
                fragment = MissionUntreatedFragment.newInstance();
                break;
            case MISSION_UNAUDITED:
                fragment = MissionUnauditedFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabNumbers;
    }
}

package com.wayto.android.function.mainFuncations.missionModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wayto.android.base.BaseFragment;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.missionModule
 * @Description:待审核
 * @date 2017/3/7 20:12
 */

public class MissionUnauditedFragment extends BaseFragment {

    private static MissionUnauditedFragment fragment;

    public static MissionUnauditedFragment newInstance() {
        if (fragment == null) {
            fragment = new MissionUnauditedFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView=new TextView(getContext());
        textView.setText("待审核");
        return textView;
    }
}

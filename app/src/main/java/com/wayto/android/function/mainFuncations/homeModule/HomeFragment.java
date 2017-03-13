package com.wayto.android.function.mainFuncations.homeModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wayto.android.R;
import com.wayto.android.base.BaseFragment;
import com.wayto.android.function.deviceFuncations.deviceFuncation.addDevice.AddDeviceActivity;
import com.wayto.android.utils.ILog;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.homeFuncation
 * @Description:主页
 * @date 2016/11/22 18:12
 */

public class HomeFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    private static HomeFragment fragment;

    public static HomeFragment newInstance() {
        if (fragment == null) {
            fragment = new HomeFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ILog.d(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment_home, null);
        ButterKnife.bind(this, rootView);
        ILog.d(TAG, "onCreateView");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ILog.d(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        ILog.d(TAG, "onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ILog.d(TAG, "onDestroy");
    }

    @OnClick({R.id.HomeFragment_report_Btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.HomeFragment_report_Btn:
                AddDeviceActivity.startAddDeviceIntent(getActivity(), "新增设施");
                break;
        }
    }
}

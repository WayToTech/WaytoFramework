package com.wayto.android.function.mainFuncations.mineModule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.wayto.android.R;
import com.wayto.android.base.BaseFragment;
import com.wayto.android.common.Constant;
import com.wayto.android.utils.ISpfUtil;
import com.wayto.android.utils.IStringUtils;
import com.wayto.android.view.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.mineFuncation.fragment
 * @Description:足迹记录设置界面
 * @date 2016/11/28 10:22
 */

public class TrackSetingFragment extends BaseFragment {

    public final static String FRAGMENT_FLAG = "trackFragment";

    private static TrackSetingFragment instance;

    @BindView(R.id.messageSetingFragment_walk_switchButton)
    SwitchButton messageSetingFragmentWalkSwitchButton;
    @BindView(R.id.messageSetingFragment_riding_switchButton)
    SwitchButton messageSetingFragmentRidingSwitchButton;
    @BindView(R.id.messageSetingFragment_drive_switchButton)
    SwitchButton messageSetingFragmentDriveSwitchButton;

    public static TrackSetingFragment newInstance() {
        if (instance == null) {
            instance = new TrackSetingFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mine_track_seting_fragment, null);
        ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    private void init() {
        initUI();
        setListener();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        int mode = IStringUtils.toInt(ISpfUtil.getValue(Constant.TRACK_RECORD_MODE_KEY, Constant.TRACK_RECORD_MODE.DRIVE.getValue()).toString());
        if (mode == Constant.TRACK_RECORD_MODE.WALK.getValue()) {
            messageSetingFragmentWalkSwitchButton.setChecked(true);
        } else if (mode == Constant.TRACK_RECORD_MODE.RIDING.getValue()) {
            messageSetingFragmentRidingSwitchButton.setChecked(true);
        } else if (mode == Constant.TRACK_RECORD_MODE.DRIVE.getValue()) {
            messageSetingFragmentDriveSwitchButton.setChecked(true);
        }
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        messageSetingFragmentWalkSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                messageSetingFragmentRidingSwitchButton.setChecked(false);
                messageSetingFragmentDriveSwitchButton.setChecked(false);
                messageSetingFragmentWalkSwitchButton.setChecked(isChecked);
                if (isChecked) {
                    ISpfUtil.setValue(Constant.TRACK_RECORD_MODE_KEY, Constant.TRACK_RECORD_MODE.WALK.getValue());
                }
            }
        });
        messageSetingFragmentRidingSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                messageSetingFragmentWalkSwitchButton.setChecked(false);
                messageSetingFragmentDriveSwitchButton.setChecked(false);
                messageSetingFragmentRidingSwitchButton.setChecked(isChecked);
                if (isChecked) {
                    ISpfUtil.setValue(Constant.TRACK_RECORD_MODE_KEY, Constant.TRACK_RECORD_MODE.RIDING.getValue());
                }
            }
        });
        messageSetingFragmentDriveSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                messageSetingFragmentWalkSwitchButton.setChecked(false);
                messageSetingFragmentRidingSwitchButton.setChecked(false);
                messageSetingFragmentDriveSwitchButton.setChecked(isChecked);
                if (isChecked) {
                    ISpfUtil.setValue(Constant.TRACK_RECORD_MODE_KEY, Constant.TRACK_RECORD_MODE.DRIVE.getValue());
                }
            }
        });
    }
}

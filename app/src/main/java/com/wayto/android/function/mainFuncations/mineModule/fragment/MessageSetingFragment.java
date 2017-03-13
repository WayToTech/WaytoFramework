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
 * @Description:消息提醒设置界面
 * @date 2016/11/28 10:22
 */

public class MessageSetingFragment extends BaseFragment {

    public final static String FRAGMENT_FLAG = "MessageFragment";

    @BindView(R.id.messageSetingFragment_voice_switchButton)
    SwitchButton messageSetingFragmentVoiceSwitchButton;
    @BindView(R.id.messageSetingFragment_notice_switchButton)
    SwitchButton messageSetingFragmentNoticeSwitchButton;

    private static MessageSetingFragment instance;

    public static MessageSetingFragment newInstance() {
        if (instance == null) {
            instance = new MessageSetingFragment();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mine_message_seting_fragment, null);
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
        messageSetingFragmentNoticeSwitchButton.setChecked(IStringUtils.toBool(ISpfUtil.getValue(Constant.MESSAGE_NOTICE_SIGN, false).toString()));
        messageSetingFragmentVoiceSwitchButton.setChecked(IStringUtils.toBool(ISpfUtil.getValue(Constant.MESSAGE_VOICE_SIGN, false).toString()));
    }

    /**
     * 设置监听器
     */
    private void setListener() {
        messageSetingFragmentVoiceSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ISpfUtil.setValue(Constant.MESSAGE_VOICE_SIGN, isChecked);
            }
        });

        messageSetingFragmentNoticeSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ISpfUtil.setValue(Constant.MESSAGE_NOTICE_SIGN, isChecked);
            }
        });
    }
}

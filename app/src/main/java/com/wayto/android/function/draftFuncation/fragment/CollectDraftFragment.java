package com.wayto.android.function.draftFuncation.fragment;

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
 * @Package com.yunwei.frame.function.draftFuncation
 * @Description:
 * @date 2017/3/9 13:47
 */

public class CollectDraftFragment extends BaseFragment {

    private static CollectDraftFragment fragment;

    public static CollectDraftFragment newInstance() {
        if (fragment == null) {
            fragment = new CollectDraftFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getContext());
        textView.setText("采集草稿");
        return textView;
    }
}

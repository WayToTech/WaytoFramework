package com.wayto.android.function.mainFuncations.recordModule.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;

import com.wayto.android.function.accountFuncation.data.UserInfoEntity;
import com.wayto.android.base.BaseRecyclerViewAdapter;
import com.wayto.android.function.mainFuncations.recordModule.RecordBaseFragment;
import com.wayto.android.function.mainFuncations.recordModule.adapter.MissionRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.recordFuncation
 * @Description:任务上报历史记录
 * @date 2016/12/7 14:14
 */

public class MissionRecordFragment extends RecordBaseFragment {

    private MissionRecyclerViewAdapter adapter;

    @Override
    protected void dispatchMessage(Message msg) {
        super.dispatchMessage(msg);
        switch (msg.what) {
            case 0x401:
                List<UserInfoEntity> entities = (List<UserInfoEntity>) msg.obj;
                if (adapter.getLoadState() == BaseRecyclerViewAdapter.LOADING_MORE) {
                    adapter.addItems(entities, adapter.getItemCount() - 1);
                    adapter.setLoadState(BaseRecyclerViewAdapter.LOADING_MORE);
                    loadMoreFinish();
                } else {
                    adapter.clearList();
                    adapter.addItems(entities);
                    closeRefresh();
                }
                loadComplete();
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MissionRecyclerViewAdapter(getContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAdapter(adapter);
        startUpRefresh();
    }

    @Override
    public void onPullRefresh() {
        List<UserInfoEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfoEntity entity = new UserInfoEntity();
            entity.setName("pullRefresh Item" + (i + 1));
            list.add(entity);
        }
        Message message = new Message();
        message.what = 0x401;
        message.obj = list;
        mHandler.sendMessageDelayed(message, 5000);
    }

    @Override
    public void onDownRefresh() {
        List<UserInfoEntity> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UserInfoEntity entity = new UserInfoEntity();
            entity.setName("downRefresh Item" + (i + 1));
            list.add(entity);
        }
        Message message = new Message();
        message.what = 0x401;
        message.obj = list;
        mHandler.sendMessageDelayed(message, 5000);
    }
}

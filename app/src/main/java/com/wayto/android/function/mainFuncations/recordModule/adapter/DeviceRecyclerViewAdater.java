package com.wayto.android.function.mainFuncations.recordModule.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wayto.android.R;
import com.wayto.android.function.accountFuncation.data.UserInfoEntity;
import com.wayto.android.base.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.recordModule.adapter
 * @Description:设置历史界面
 * @date 2016/12/8 15:14
 */

public class DeviceRecyclerViewAdater extends BaseRecyclerViewAdapter<UserInfoEntity> {


    public DeviceRecyclerViewAdater(Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.item_device_recyclerview, parent, false));
    }

    @Override
    public void onBindBaseViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder holderView = (ItemViewHolder) holder;
        holderView.itemDeviceRecyclerViewTextView.setText(mLists.get(position).getName());
    }

    protected class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_device_recyclerView_textView)
        TextView itemDeviceRecyclerViewTextView;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

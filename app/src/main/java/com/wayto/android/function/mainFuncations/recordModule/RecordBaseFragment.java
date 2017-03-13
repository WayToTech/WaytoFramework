package com.wayto.android.function.mainFuncations.recordModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wayto.android.R;
import com.wayto.android.base.BaseFragment;
import com.wayto.android.base.BaseRecyclerViewAdapter;
import com.wayto.android.view.PullToRefreshRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.recordFuncation
 * @Description:历史模块基类
 * @date 2016/12/7 14:15
 */

public class RecordBaseFragment extends BaseFragment implements PullToRefreshRecyclerView.PullToRefreshRecyclerViewListener, BaseRecyclerViewAdapter.OnRecyclerViewItemLongClickListener, BaseRecyclerViewAdapter.OnRecyclerViewItemClickListener {

    @BindView(R.id.record_recyclerView)
    public PullToRefreshRecyclerView recordRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mission_record, null);
        ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        recordRecyclerView.setPullToRefreshListener(this);
    }

    /**
     * 启动刷新
     */
    public void startUpRefresh() {
        recordRecyclerView.startUpRefresh();
    }

    /**
     * 关闭刷新
     */
    public void closeRefresh() {
        recordRecyclerView.closeDownRefresh();
    }

    /**
     * 加载完成
     */
    public void loadComplete() {
        recordRecyclerView.setLoading(false);
    }

    /**
     * 加载更多完成
     */
    public void loadMoreFinish(){
        recordRecyclerView.onLoadMoreFinish();
    }

    /**
     * 设置监听器
     *
     * @param adapter
     */
    public void setAdapter(BaseRecyclerViewAdapter adapter) {
        recordRecyclerView.setRecyclerViewAdapter(adapter);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        recordRecyclerView.setMode(PullToRefreshRecyclerView.Mode.BOTH);
    }

    @Override
    public void onItemClick(View view, Object data, int position) {

    }

    @Override
    public void onLongItemClick(View view, Object data, int position) {

    }

    @Override
    public void onDownRefresh() {

    }

    @Override
    public void onPullRefresh() {

    }
}

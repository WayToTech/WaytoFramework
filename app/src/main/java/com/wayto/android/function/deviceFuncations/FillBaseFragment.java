package com.wayto.android.function.deviceFuncations;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wayto.android.R;
import com.wayto.android.base.BaseFragment;
import com.wayto.android.common.Constant;
import com.wayto.android.function.pictureFuncation.SelectPictureActivity;
import com.wayto.android.function.pictureFuncation.data.PictureEntity;
import com.wayto.android.db.WorkRecordTable;
import com.wayto.android.widget.AccessoryView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.deviceFuncations
 * @Description:填报BaseFragment
 * @date 2017/2/20 14:32
 */

public abstract class FillBaseFragment<T> extends BaseFragment {
    /*默认workRecordTable 表ID,处理多次保存的问题*/
    protected long DEFULT_WORKRECORDTABLE_ID = Constant.DEFUALT_WORKRECORD_ID;

    private FrameLayout imageFrameLayout;
    protected FrameLayout mContentLayout;

    /*图片控件View*/
    protected AccessoryView mAccessoryView;
    /*图片回调*/
    private List<PictureEntity> entities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fill_base, null);
        findViewById(rootView);
        initUI();
        addContentView(inflater);
        return rootView;
    }

    /**
     * 初始化控件
     *
     * @param view
     */
    private void findViewById(View view) {
        imageFrameLayout = ButterKnife.findById(view, R.id.fillBaseFragment_add_img_layout);
        mContentLayout = ButterKnife.findById(view, R.id.fillBaseFragment_content_fl);
        ButterKnife.findById(view,R.id.fillBaseFragment_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDraftAction();
            }
        });
        ButterKnife.findById(view,R.id.fillBaseFragment_submit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAction();
            }
        });
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        mAccessoryView = new AccessoryView(getActivity());
        imageFrameLayout.addView(mAccessoryView);
    }

    /**
     * 添加布局文件
     */
    private void addContentView(LayoutInflater inflater) {
        mContentLayout.removeAllViews();
        mContentLayout.addView(getContentView(inflater));
    }

    /**
     * 获取表单布局view
     *
     * @return
     */
    protected abstract View getContentView(LayoutInflater inflater);

    /**
     * 保存草稿
     */
    protected abstract void saveDraftAction();

    /**
     * 提交
     */
    protected abstract void submitAction();

    /**
     * 获取工作记录表
     *
     * @return
     */
    protected abstract WorkRecordTable getWorkTable();

    /**
     * 获取界面实体
     *
     * @return
     */
    protected abstract T getEntity();

    /**
     * 获取图片附件集合
     *
     * @return
     */
    public List<String> getImageAccessoryList() {
        return mAccessoryView.getImageLists();
    }

    /**
     * 获取图片附件String 多张图片以","隔开
     *
     * @return
     */
    public String getImageAccessoryString() {
        StringBuffer sb = new StringBuffer();
        if (mAccessoryView.getImageLists() != null && mAccessoryView.getImageLists().size() > 0) {
            for (String str : mAccessoryView.getImageLists()) {
                sb.append(str).append(",");
            }
            return sb.substring(0, sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 选择、照片回调
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case SelectPictureActivity.SELECT_PICTURE_RESULT_CODE:
                entities = (List<PictureEntity>) data.getExtras().getSerializable("result");
                mAccessoryView.clearImages();
                if (entities == null || entities.size() == 0) {
                    return;
                }
                mAccessoryView.setImageListToEntity(entities);
        }
    }

}

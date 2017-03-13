package com.wayto.android.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.wayto.android.R;
import com.wayto.android.function.pictureFuncation.SelectPictureActivity;
import com.wayto.android.function.pictureFuncation.ShowPictureActivity;
import com.wayto.android.function.pictureFuncation.data.PictureEntity;
import com.wayto.android.view.AccessoryImageGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.yunwei.zaina.widget
 * @Description:附件控件
 * @author: Aaron
 * @date: 2016-06-14
 * @Time: 18:50
 * @version: V1.0
 */
public class AccessoryView extends LinearLayout implements AdapterView.OnItemClickListener {
    /*默认图片数量*/
    public final static int DEFAULT_PICTURE_NUMBER = 10;

    private AccessoryImageGridView gridView;
    private AccessoryImgAdapter adapter;

    private Activity activity;

    private String photoPath;

    public AccessoryView(Activity context) {
        super(context);
        this.activity = context;
        initView(context);
    }

    public AccessoryView(Activity context, AttributeSet attri) {
        super(context, attri);
        this.activity = context;
        initView(context);
    }

    private void initView(Activity context) {
        View view = LayoutInflater.from(context).inflate(R.layout.accessory_img_layout, null);
        gridView = (AccessoryImageGridView) view.findViewById(R.id.accessory_gridview);

        adapter = new AccessoryImgAdapter(context);
        gridView.setAdapter(adapter);

        initGridView();
        addView(view);
    }

    private void initGridView() {
        PictureEntity entity = new PictureEntity();
        entity.setUrl(AccessoryImgAdapter.ADD_IMG_FLAG);
        adapter.appendToList(entity);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PictureEntity entity = (PictureEntity) adapter.getItem(position);
        if (TextUtils.isEmpty(entity.getUrl())) {
            return;
        }
        /*添加图片*/
        if (AccessoryImgAdapter.ADD_IMG_FLAG.equals(entity.getUrl())) {
            List<String> list = new ArrayList<>();
            for (PictureEntity pictureEntity : adapter.getList()) {
                list.add(pictureEntity.getUrl());
            }
            SelectPictureActivity.startIntent(activity, list, DEFAULT_PICTURE_NUMBER);
        } else {
            /*查看大图*/
            ArrayList<String> list = new ArrayList<>();
            for (PictureEntity entity1 : adapter.getList()) {
                if (!AccessoryImgAdapter.ADD_IMG_FLAG.equals(entity1.getUrl()))
                    list.add(entity1.getUrl());
            }
            ShowPictureActivity.startIntent(getContext(), list, position);
        }
    }


    /**
     * 添加相册图片
     *
     * @param list
     */
    public void setImageListToStr(List<String> list) {
        if (list == null && list.size() == 0) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            if (adapter.getList().size() > DEFAULT_PICTURE_NUMBER) {
                adapter.removePos(adapter.getList().size() - 1);
                break;
            } else {
                setImageList(list.get(i));
            }
        }
    }

    /**
     * 添加拍照图片
     *
     * @param path
     */
    public void setImageList(String path) {
        PictureEntity entity = new PictureEntity();
        entity.setUrl(path);
        if ("photo".equals(path) && !TextUtils.isEmpty(photoPath)) {
            adapter.appendPositionToList(0, entity);
        } else {
            adapter.appendPositionToList(0, entity);
        }
        if (adapter.getList().size() > DEFAULT_PICTURE_NUMBER) {
            adapter.removePos(adapter.getList().size() - 1);
            return;
        }
    }

    /**
     * @param entities
     */
    public void setImageListToEntity(List<PictureEntity> entities) {
        for (PictureEntity entity : entities) {
            if (adapter.getList().size() > DEFAULT_PICTURE_NUMBER) {
                adapter.removePos(adapter.getList().size() - 1);
                return;
            } else {
                adapter.appendPositionToList(0, entity);
            }
        }


    }

    /**
     * 设置显示图片
     *
     * @param strs
     */
    public void setShowImageList(String[] strs) {
        List<PictureEntity> entities = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            PictureEntity entity = new PictureEntity();
            entity.setUrl(strs[i]);
            entities.add(entity);
        }
        setShowImage();
        if (adapter.getList().get(adapter.getCount() - 1).equals(AccessoryImgAdapter.ADD_IMG_FLAG)) {
            adapter.removePos(adapter.getCount() - 1);
            adapter.appendToList(entities);
        }
    }

    /**
     * 设置显示图片方法2，保留+号按钮
     */
    @Deprecated
    public void setShowImageListWithAddButton(String[] strs) {
        List<PictureEntity> entities = new ArrayList<>();
        for (int i = 0; i < strs.length; i++) {
            PictureEntity entity = new PictureEntity();
            entity.setUrl(strs[i]);
            entities.add(entity);
        }
        setShowImage();
        if (adapter.getList().get(adapter.getCount() - 1).equals(AccessoryImgAdapter.ADD_IMG_FLAG)) {
            for (int i = 0; i < strs.length; i++) {
                adapter.appendPositionToList(0, entities);
            }
        }
    }

    /**
     * 设置显示图片
     *
     * @param strs
     */
    public void setShowImageList(ArrayList<String> strs) {
        List<PictureEntity> entities = new ArrayList<>();
        for (int i = 0; i < strs.size(); i++) {
            PictureEntity entity = new PictureEntity();
            entity.setUrl(strs.get(i));
            entities.add(entity);
        }
        setShowImage();
        if (adapter.getList().get(adapter.getCount() - 1).equals(AccessoryImgAdapter.ADD_IMG_FLAG)) {
            adapter.removePos(adapter.getCount() - 1);
            adapter.appendToList(entities);
        }
    }

    /**
     * 设置显示图片
     *
     * @param url
     */
    public void setShowImage(String url) {
        PictureEntity entity = new PictureEntity();
        entity.setUrl(url);
        adapter.appendToList(entity);
    }

    public void setShowImage() {
        adapter.setShowCloseIcon(false);
        if (adapter.getList().get(adapter.getCount() - 1).equals(AccessoryImgAdapter.ADD_IMG_FLAG)) {
            adapter.removePos(adapter.getCount() - 1);
        }
    }

    /**
     * 获取图片资源
     *
     * @return
     */
    public List<String> getImageLists() {
        List<String> list = new ArrayList<>();
        for (PictureEntity entity : adapter.getList()) {
            if (!adapter.getList().get(adapter.getCount() - 1).getUrl().equals(AccessoryImgAdapter.ADD_IMG_FLAG)) {
                list.add(entity.getUrl());
            }
        }

        return list;
    }

    /**
     * 清空列表图片
     */
    public void clearImages() {
        while ((adapter.getCount()) > 1) {
            adapter.removePos(0);
        }
    }
}

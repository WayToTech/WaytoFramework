package com.wayto.android.function.pictureFuncation;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wayto.android.R;
import com.wayto.android.base.BaseActivity;
import com.wayto.android.common.Constant;
import com.wayto.android.function.pictureFuncation.data.PictureEntity;
import com.wayto.android.utils.IDateTimeUtils;
import com.wayto.android.utils.ISkipActivityUtil;
import com.wayto.android.utils.IUtil;
import com.wayto.android.utils.PermissionHelper;
import com.wayto.android.view.SpaceItemDecoration;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.deviceFuncations.picture
 * @Description:选择图片Activity
 * @date 2016/12/29 11:27
 */

public class SelectPictureActivity extends BaseActivity implements PictureContract.View, SwipeRefreshLayout.OnRefreshListener, SelectPictrueAdapter.OnClickSelectePictureListener {
    private final String TAG = getClass().getSimpleName();

    private final int TACK_PICTURE_VALUE = 490;
    public static final int SELECT_PICTURE_RESULT_CODE = 491;
    public static final int SELECT_PICTURE_REQUEST_CODE = 492;

    @BindView(R.id.SelectPiture_RecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.SelectPiture_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private SelectPictrueAdapter adapter;

    private PicturePresenter mPresenter;

    private List<PictureEntity> images;

    private String path;

    private static int totalImage = 10;

    private static List<String> selectePicLists;

    /**
     * 启动图片选择Activity
     *
     * @param activity
     * @param selectTotal 默认选择几张图片
     */
    public static void startIntent(Activity activity, int selectTotal) {
        if (selectTotal > 0) {
            totalImage = selectTotal;
        }
        ISkipActivityUtil.startIntentForResult(activity, SelectPictureActivity.class, SELECT_PICTURE_REQUEST_CODE);
    }

    /**
     * @param activity
     * @param selectePics 已经选择中的图片集合
     * @param selectTotal
     */
    public static void startIntent(Activity activity, List<String> selectePics, int selectTotal) {
        if (selectePics.size() > 0) {
            totalImage = selectTotal - selectePics.size();
            selectePicLists = selectePics;
        }else {
            selectePicLists=new ArrayList<>();
        }
        ISkipActivityUtil.startIntentForResult(activity, SelectPictureActivity.class, SELECT_PICTURE_REQUEST_CODE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_picture);
        setToolbarTitle(R.string.title_select_pictrue);
        ButterKnife.bind(this);
        if (PermissionHelper.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, Constant.REQUEST_READ_EXTERNAL_STORAGE) && PermissionHelper.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Constant.REQUEST_WRITE_EXTERNAL_STORAGE)) {
            init();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        images = new ArrayList<>();
        initSwipeRefreshLayout();
        initRecyclerView();
        initPresenter();
        startRefresh();
    }

    /**
     * 初始化SwipeRefreshLayout
     */
    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecyclerView() {
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        adapter = new SelectPictrueAdapter(this, totalImage);
        adapter.setListener(this);
        adapter.setSelecteData(selectePicLists);
        int spacingInPixels = getResources().getDimensionPixelOffset(R.dimen.recycler_item_space);
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(manager);
    }

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        mPresenter = new PicturePresenter(this);
    }


    /**
     * 启动刷新
     */
    private void startRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefresh();
    }

    @Override
    public void onClickToolbarRightLayout() {
        super.onClickToolbarRightLayout();
        sendAction();
    }

    @Override
    public void onClickPicture(boolean isSelecte, PictureEntity entity) {
        if (isSelecte) {
            images.add(entity);
        } else {
            images.remove(entity);
        }
        if (images.size() > 0) {
            setToolbarRightText(IUtil.getStrToRes(R.string.dialog_defalut_confirm_text) + "  [ " + images.size() + " ]");
        } else {
            if (selectePicLists != null && selectePicLists.size() > 0) {
                setToolbarRightText(R.string.dialog_defalut_confirm_text);
            } else
                setToolbarRightText("");
        }
    }

    @Override
    public void onTackPicture() {
        path = getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + "/IMG_" + System.currentTimeMillis() + ".png";
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, TACK_PICTURE_VALUE);
    }

    @Override
    public void onSelectePicture(PictureEntity entity) {
        images.add(entity);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TACK_PICTURE_VALUE:
                    PictureEntity entity = new PictureEntity();
                    entity.setUrl(path);
                    entity.setDate(IDateTimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
                    images.add(entity);
                    sendAction();
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.REQUEST_READ_EXTERNAL_STORAGE || requestCode == Constant.REQUEST_WRITE_EXTERNAL_STORAGE) {
            init();
        }
    }

    /**
     * 确定返回
     */
    private void sendAction() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("result", (Serializable) images);
        intent.putExtras(bundle);
        setResult(SELECT_PICTURE_RESULT_CODE, intent);
        this.finish();
    }

    @Override
    public void onRefresh() {
        mPresenter.queryAlbum();
    }

    @Override
    public void onQueryAlbumStart() {

    }

    @Override
    public void queryAlbumSuccess(final List<PictureEntity> lists) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                PictureEntity entity = new PictureEntity();
                entity.setUrl(SelectPictrueAdapter.KEY_TAKE_PICTURE);
                lists.add(0, entity);
                adapter.setData(lists);
            }
        });
    }

    @Override
    public void queryAlbumFailure() {

    }

    @Override
    public void onQueryAlbumEnd() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                mSwipeRefreshLayout.setEnabled(false);
            }
        });
    }
}

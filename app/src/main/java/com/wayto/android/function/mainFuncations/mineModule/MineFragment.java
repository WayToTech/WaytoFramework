package com.wayto.android.function.mainFuncations.mineModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wayto.android.R;
import com.wayto.android.base.BaseFragment;
import com.wayto.android.base.DataApplication;
import com.wayto.android.common.Constant;
import com.wayto.android.common.dialog.CommPopupWindow;
import com.wayto.android.common.dialog.DialogFactory;
import com.wayto.android.common.eventbus.EventConstant;
import com.wayto.android.common.eventbus.NoticeEvent;
import com.wayto.android.function.accountFuncation.AccountContract;
import com.wayto.android.function.accountFuncation.AccountPresenter;
import com.wayto.android.function.accountFuncation.LoginActivity;
import com.wayto.android.function.accountFuncation.ModifyPasswordActivity;
import com.wayto.android.function.accountFuncation.data.UserInfoEntity;
import com.wayto.android.function.accountFuncation.data.soure.AccountRemoteRepo;
import com.wayto.android.function.draftFuncation.DraftActivity;
import com.wayto.android.function.mainFuncations.mineModule.CheckAppVersion.CheckAppVersionContract;
import com.wayto.android.function.mainFuncations.mineModule.CheckAppVersion.CheckAppVersionPresenter;
import com.wayto.android.function.mainFuncations.mineModule.data.source.CheckAppVersionRemoteRepo;
import com.wayto.android.function.mainFuncations.mineModule.fragment.MessageSetingFragment;
import com.wayto.android.function.mainFuncations.mineModule.fragment.TrackSetingFragment;
import com.wayto.android.function.pictureFuncation.SelectPictureActivity;
import com.wayto.android.function.pictureFuncation.ShowPictureActivity;
import com.wayto.android.utils.IActivityManage;
import com.wayto.android.utils.ISkipActivityUtil;
import com.wayto.android.utils.ISpfUtil;
import com.wayto.android.utils.ISystemUtil;
import com.wayto.android.utils.IUtil;
import com.wayto.android.view.RoundedBitmapImageViewTarget;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.homeFuncation
 * @Description:我的主界面
 * @date 2016/11/22 18:12
 */

public class MineFragment extends BaseFragment implements CheckAppVersionContract.View, AccountContract.ModifyUearHeadView {

    private static MineFragment fragment;
    @BindView(R.id.userInfo_headView_iv)
    ImageView userInfoHeadViewIv;
    @BindView(R.id.userInfo_name_textView)
    TextView userInfoNameTextView;
    @BindView(R.id.userInfo_dev_textView)
    TextView userInfoDevTextView;
    @BindView(R.id.MineFragment_about_textView)
    TextView versionNameText;

    private CheckAppVersionPresenter versionPresenter;
    private AccountPresenter accountPresenter;

    private CommPopupWindow popupWindow;

    private String headPath;

    public static MineFragment newInstance() {
        if (fragment == null) {
            fragment = new MineFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        versionPresenter = new CheckAppVersionPresenter(CheckAppVersionRemoteRepo.newInstance(), this);
        accountPresenter = new AccountPresenter(AccountRemoteRepo.newInstance(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment_mine, null);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(getActivity()).load(IUtil.fitterUrl(DataApplication.getInstance().getUserInfoEntity().getIcon())).asBitmap().centerCrop().into(new RoundedBitmapImageViewTarget(userInfoHeadViewIv));
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        versionNameText.setText(IUtil.getStrToRes(R.string.set_about) + " " + ISystemUtil.getVersionName());
        UserInfoEntity userInfoEntity = DataApplication.getInstance().getUserInfoEntity();
        if (userInfoEntity == null) {
            return;
        }
        userInfoNameTextView.setText(userInfoEntity.getName());
        userInfoDevTextView.setText(userInfoEntity.getDept());
    }

    @OnClick({R.id.userInfo_headView_iv, R.id.MineFragment_history_seting_textView, R.id.MineFragment_modify_pwd_textView, R.id.MineFragment_Message_seting_textView, R.id.MineFragment_Track_seting_textView, R.id.MineFragment_about_textView, R.id.MineFragment_switch_user_textView, R.id.MineFragment_exit_textView})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.userInfo_headView_iv:/*头像*/
                showHeadDialog();
                break;
            case R.id.MineFragment_history_seting_textView:/*我的草稿*/
                ISkipActivityUtil.startIntent(getActivity(), DraftActivity.class);
                break;
            case R.id.MineFragment_modify_pwd_textView:/*密码修改*/
                ISkipActivityUtil.startIntent(getActivity(), ModifyPasswordActivity.class);
                break;
            case R.id.MineFragment_Message_seting_textView:/*消息提示*/
                bundle.putString(SetingInfoActivity.SHOW_FRAGMENT_FLAG, MessageSetingFragment.FRAGMENT_FLAG);
                bundle.putString(SetingInfoActivity.HEAD_TITLE_FLAG, getString(R.string.set_msg_info));
                ISkipActivityUtil.startIntent(getActivity(), SetingInfoActivity.class, bundle);
                break;
            case R.id.MineFragment_Track_seting_textView:/*轨迹记录*/
                bundle.putString(SetingInfoActivity.SHOW_FRAGMENT_FLAG, TrackSetingFragment.FRAGMENT_FLAG);
                bundle.putString(SetingInfoActivity.HEAD_TITLE_FLAG, getString(R.string.set_track_info));
                ISkipActivityUtil.startIntent(getActivity(), SetingInfoActivity.class, bundle);
                break;
            case R.id.MineFragment_about_textView:/*版本更新*/
                versionPresenter.checkAppVersion();
                break;
            case R.id.MineFragment_switch_user_textView:/*切换用户*/
                showSwitchUserDialog();
                break;
            case R.id.MineFragment_exit_textView:/*退出*/
                showExitAppDialog();
                break;
        }
    }

    @Override
    public void showRequestFail() {
        showToast(R.string.taost_version_failure);
    }

    @Override
    public void showIsLatestVersion() {
        showToast(R.string.toast_version_later);
    }

    @Override
    public void showCheckingDialog() {
        loadDialog = DialogFactory.createLoadingDialog(getActivity(), R.string.dialog_msg_check_app_ver);
    }

    @Override
    public void dismissCheckingDialog() {
        DialogFactory.dimissDialog(loadDialog);
    }

    /**
     * 切換用戶Dialog
     */
    private void showSwitchUserDialog() {
        DialogFactory.showMsgDialog(getActivity(), getString(R.string.dialog_title_switch_user), getString(R.string.switch_user_msg), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ISpfUtil.setValue(Constant.PSSWORD_KEY, "");
                IActivityManage.getInstance().exit();
                ISkipActivityUtil.startIntent(getActivity(), LoginActivity.class);
            }
        });
    }

    /**
     * 退出Dialog
     */
    private void showExitAppDialog() {
        DialogFactory.showMsgDialog(getActivity(), getString(R.string.dialog_title_exit), getString(R.string.exit_msg) + getString(R.string.app_name) + "?", getString(R.string.exit), getString(R.string.dialog_defalut_cancel_text), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.gc();
                IActivityManage.getInstance().exit();
                System.exit(0);
            }
        }, null);
    }

    private void showHeadDialog() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_show_head_layout, null);
        view.findViewById(R.id.dialog_show_head_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPictureActivity.startIntent(getActivity(), DataApplication.getInstance().getUserInfoEntity().getIcon());
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.dialog_edit_head_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectPictureActivity.startIntent(getActivity(), 1);
                popupWindow.dismiss();
            }
        });
        view.findViewById(R.id.dialog_cancle_head_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow = new CommPopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtButton(getView());
    }

    @Override
    public void onMainUserEvent(NoticeEvent event) {
        super.onMainUserEvent(event);
        switch (event.getFlag()) {
            case EventConstant.UPDATE_USER_HEAD_KEY:
                headPath = event.getObj().toString();
                accountPresenter.modifyUserHead();
                break;
        }
    }

    @Override
    public void showModifyHearDialog() {
        loadDialog = DialogFactory.createLoadingDialog(getActivity(), R.string.dialog_msg_modify_head);
    }

    @Override
    public void modifyHeadFailure(String error) {
        showToast(error);
    }

    @Override
    public void modifyHeadSuccess(String headNewPath) {
        Glide.with(this).load(IUtil.fitterUrl(headNewPath)).asBitmap().centerCrop().into(new RoundedBitmapImageViewTarget(userInfoHeadViewIv));
        showToast(R.string.toast_modify_head_success);
    }

    @Override
    public String getHeadPath() {
        return headPath;
    }

    @Override
    public void dimissModifyHearDialog() {
        DialogFactory.dimissDialog(loadDialog);
    }

}

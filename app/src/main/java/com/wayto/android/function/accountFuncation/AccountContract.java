package com.wayto.android.function.accountFuncation;

import android.app.Activity;

import com.wayto.android.function.accountFuncation.data.UserInfoEntity;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.android.function.account
 * @Description:定义协议
 * @date 2016/11/29 14:54
 */

public interface AccountContract {
    /**
     * 登录
     */
    interface LoginView {
        void showDialog();

        void dismissDialog();

        void loginSuccess(UserInfoEntity entity);

        void loginFailure(String error);

        String getAccount();

        String getPassword();
    }

    /**
     * 修改用户头像
     */
    interface ModifyUearHeadView {
        void showModifyHearDialog();

        void dimissModifyHearDialog();

        String getHeadPath();

        Activity getActivity();

        void modifyHeadSuccess(String headNewPath);

        void modifyHeadFailure(String error);
    }

    /**
     * 修改密码
     */
    interface ModifyUserPasswordView {
        void showModifyPwdDialog();

        void dimissModifyPwdDialog();

        void modifyPwdSuccess();

        void modifyPwdFailure(String error);

        String getNewPassword();

        String getOldPassword();
    }

    interface Presenter {
        void login();

        void modifyUserHead();

        void modifyUserPwd();
    }
}

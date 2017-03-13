package com.wayto.android.function.accountFuncation.data.soure;

import com.wayto.android.function.accountFuncation.data.ModifyHeadEntity;
import com.wayto.android.function.accountFuncation.data.ModifyPasswordEntity;
import com.wayto.android.function.accountFuncation.data.UserInfoEntity;
import com.wayto.android.base.BaseDataSourse;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.account.data.soure
 * @Description:
 * @date 2016/11/29 15:02
 */

public interface AccountDataSoure extends BaseDataSourse {

    interface LoginCallBack {

        void onLoginSuccess(UserInfoEntity entity);

        void onLoginFailure(String error);

    }

    interface ModifyHeadCallBack {
        void onModifyHeadSuccess(String headNewPath);

        void onModifyHeadFailure(String error);

        ModifyHeadEntity getHeadBody(String path);
    }

    interface ModifyPasswordCallBack {
        void onModifyPasswordSuccess();

        void onModifyPasswordFailure(String error);

        ModifyPasswordEntity getPasswordBody();
    }

    void login(String account, String password, LoginCallBack callBack);

    void modifyHead(String path, ModifyHeadCallBack callBack);

    void modifyPassword(ModifyPasswordCallBack callBack);
}

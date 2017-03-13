package com.wayto.android.function.accountFuncation.data;

import java.io.Serializable;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.account.data
 * @Description:
 * @date 2017/1/10 16:58
 */

public class ModifyPasswordEntity implements Serializable{

    private String OldPassword;

    private String NewPassword;

    public String getOldPassword() {
        return OldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.OldPassword = oldPassword;
    }

    public String getNewPassword() {
        return NewPassword;
    }

    public void setNewPassword(String newPassword) {
        this.NewPassword = newPassword;
    }
}

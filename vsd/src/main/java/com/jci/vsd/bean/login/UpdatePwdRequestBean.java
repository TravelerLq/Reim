package com.jci.vsd.bean.login;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class UpdatePwdRequestBean extends BaseBean {
    private String userId;
    private String oldPassword;
    private String newPassword;

    public UpdatePwdRequestBean(String userId, String oldPassword, String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public UpdatePwdRequestBean() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}

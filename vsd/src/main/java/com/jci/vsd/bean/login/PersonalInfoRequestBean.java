package com.jci.vsd.bean.login;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class PersonalInfoRequestBean extends BaseBean {
    private String userId;

    public PersonalInfoRequestBean() {
    }

    public PersonalInfoRequestBean(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

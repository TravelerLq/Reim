package com.jci.vsd.bean.login;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by Yso on 2017/11/13.
 */

public class LoginRequestbean extends BaseBean {
    private String userName;
    private String password;
    private String type;

    public LoginRequestbean(String userName, String password, String type) {
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    public LoginRequestbean() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

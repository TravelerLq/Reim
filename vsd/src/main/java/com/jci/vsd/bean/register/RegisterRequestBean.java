package com.jci.vsd.bean.register;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/13.
 * 注册请求Bean
 */

public class RegisterRequestBean extends BaseBean {

    /**
     * phone : 15252466554
     * password : 123456
     * idNumber : 320113199310156418
     * realname : 屠正松
     * email : 916468631@qq.com
     */

    private String phone;
    private String password;
    private String idNumber;
    private String realname;
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

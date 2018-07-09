package com.jci.vsd.bean.token;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class TokenBean extends BaseBean {
    private String accessToken;
    private String status;
    private String expiredTime;
    private String message;

    public TokenBean() {
    }

    public TokenBean(String accessToken, String status, String expiredTime, String message) {
        this.accessToken = accessToken;
        this.status = status;
        this.expiredTime = expiredTime;
        this.message = message;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

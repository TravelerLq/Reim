package com.jci.vsd.bean.login;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class BindCardRequestBean extends BaseBean {
    private String userId;
    private String cardNo;

    public BindCardRequestBean() {
    }

    public BindCardRequestBean(String userId, String cardNo) {
        this.userId = userId;
        this.cardNo = cardNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}

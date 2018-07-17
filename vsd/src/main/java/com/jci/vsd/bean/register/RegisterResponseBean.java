package com.jci.vsd.bean.register;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/11.
 */

public class RegisterResponseBean extends BaseBean {
  private  String status;
  private  String uid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

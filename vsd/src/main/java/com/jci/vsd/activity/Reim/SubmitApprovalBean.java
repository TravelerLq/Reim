package com.jci.vsd.activity.Reim;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/26.
 */

public class SubmitApprovalBean extends BaseBean {

    /**
     * id : 44
     * flag : true
     * sign : signtest
     * cer : certest
     */

    private int id;
    private String flag;
    private String sign;
    private String cer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCer() {
        return cer;
    }

    public void setCer(String cer) {
        this.cer = cer;
    }
}

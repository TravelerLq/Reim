package com.jci.vsd.bean.reim;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/26.
 */

public class ReimDocSubmitBean extends BaseBean {

    /**
     * id : 41
     * sign : sign
     * cer : cer
     */

    private int id;
    private String sign;
    private String cer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

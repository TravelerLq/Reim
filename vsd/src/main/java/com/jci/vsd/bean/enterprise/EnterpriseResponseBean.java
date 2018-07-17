package com.jci.vsd.bean.enterprise;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/11.
 */

public class EnterpriseResponseBean extends BaseBean {
    private String status;
    private int cid;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnterpriseResponseBean that = (EnterpriseResponseBean) o;

        return status != null ? status.equals(that.status) : that.status == null;
    }

    @Override
    public int hashCode() {
        return status != null ? status.hashCode() : 0;
    }
}

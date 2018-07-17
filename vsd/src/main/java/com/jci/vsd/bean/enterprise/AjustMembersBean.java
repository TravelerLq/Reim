package com.jci.vsd.bean.enterprise;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/12.
 */

public class AjustMembersBean extends BaseBean {
    int id;
    int departId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartId() {
        return departId;
    }

    public void setDepartId(int departId) {
        this.departId = departId;
    }
}

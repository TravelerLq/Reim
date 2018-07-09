package com.jci.vsd.bean.differReport;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 *  Created by liqing on 17/11/21.
 * 存料差异报告详情－请求bean
 */

public class DifferReportDetailRequestBean extends BaseBean {
private String ID;

    public DifferReportDetailRequestBean(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}

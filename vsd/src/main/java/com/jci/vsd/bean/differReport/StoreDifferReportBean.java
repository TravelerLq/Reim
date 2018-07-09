package com.jci.vsd.bean.differReport;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by liqing on 17/11/13.
 * 存料差异报告的Bean
 */

public class StoreDifferReportBean extends BaseBean {
    private String materialNo;

    public StoreDifferReportBean(String materialNo) {
        this.materialNo = materialNo;
    }

    public String getMaterialNo() {
        return materialNo;
    }

    public void setMaterialNo(String materialNo) {
        this.materialNo = materialNo;
    }
}

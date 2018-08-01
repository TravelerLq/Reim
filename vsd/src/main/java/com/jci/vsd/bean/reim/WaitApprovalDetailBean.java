package com.jci.vsd.bean.reim;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/26.
 * 待审批列表详情Bean
 */

public class WaitApprovalDetailBean extends BaseBean {

    /**
     * amount : 100
     * name : 住宿费
     * remark : 测试
     * id : 131
     * detail : 开始日期 : 2018-07-23
     */

    private String amount;
    private String name;
    private String remark;
    private int id;
    private String detail;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

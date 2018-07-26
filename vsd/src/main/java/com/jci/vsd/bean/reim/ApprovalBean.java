package com.jci.vsd.bean.reim;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/6/28.
 */

public class ApprovalBean extends BaseBean {


    /**
     * date : 2018-07-25
     * total : 1730
     * appl : 加图索
     * id : 1
     * annex : 3
     */

    private String date;
    private String total;
    private String appl;
    private int id;
    private String annex;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAppl() {
        return appl;
    }

    public void setAppl(String appl) {
        this.appl = appl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnnex() {
        return annex;
    }

    public void setAnnex(String annex) {
        this.annex = annex;
    }
}

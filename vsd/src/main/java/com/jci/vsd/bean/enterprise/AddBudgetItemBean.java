package com.jci.vsd.bean.enterprise;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/19.
 */

public class AddBudgetItemBean extends BaseBean {

    /**
     * item : 8
     * cat : 8
     * cquota : 10000
     * iquota : 1800
     * dpt : 118
     * dquota : 20000
     */
    private String status;
    private String type;
    private int item;
    private int cat;
    private double cquota;
    private double iquota;
    private int dpt;
    private double dquota;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    //"1"部门 ； "2" ：二级科目 ；"3"三级科目

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }

    public double getCquota() {
        return cquota;
    }

    public void setCquota(double cquota) {
        this.cquota = cquota;
    }

    public double getIquota() {
        return iquota;
    }

    public void setIquota(double iquota) {
        this.iquota = iquota;
    }

    public int getDpt() {
        return dpt;
    }

    public void setDpt(int dpt) {
        this.dpt = dpt;
    }

    public double getDquota() {
        return dquota;
    }

    public void setDquota(double dquota) {
        this.dquota = dquota;
    }
}

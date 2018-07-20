package com.jci.vsd.bean.enterprise;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/2.
 */

public class BudgetBean extends BaseBean {


    /**
     * quota : 10000.0
     * name : 研发部
     * dpt : 118
     * id : 3
     * used : 344.33
     * item : 1
     * cat : 1
     */

    private double quota;
    private String name;
    private int dpt;
    private int id;
    private double used;
    private int item;
    private int cat;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getQuota() {
        return quota;
    }

    public void setQuota(double quota) {
        this.quota = quota;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDpt() {
        return dpt;
    }

    public void setDpt(int dpt) {
        this.dpt = dpt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getUsed() {
        return used;
    }

    public void setUsed(double used) {
        this.used = used;
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
}

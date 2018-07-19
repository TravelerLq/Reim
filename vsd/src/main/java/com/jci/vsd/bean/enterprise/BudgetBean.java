package com.jci.vsd.bean.enterprise;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/2.
 */

public class BudgetBean extends BaseBean {
    private String departmentName;
    private int budget;



    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }
}

package com.jci.vsd.bean.enterprise;

import com.jci.vsd.bean.BaseBean;

import java.util.List;

/**
 * Created by liqing on 18/7/23.
 */

public class ProducerAddBean extends BaseBean {


    /**
     * name : 部门审批
     * checker : 28
     * sort : 2
     * fath : 2
     * dpts : [118,119]
     */

    private String name;
    private int checker;
    private int sort;
    private int fath;
    private List<Integer> dpts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getChecker() {
        return checker;
    }

    public void setChecker(int checker) {
        this.checker = checker;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getFath() {
        return fath;
    }

    public void setFath(int fath) {
        this.fath = fath;
    }

    public List<Integer> getDpts() {
        return dpts;
    }

    public void setDpts(List<Integer> dpts) {
        this.dpts = dpts;
    }
}

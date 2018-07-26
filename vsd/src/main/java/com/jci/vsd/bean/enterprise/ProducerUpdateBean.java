package com.jci.vsd.bean.enterprise;

import com.jci.vsd.bean.BaseBean;

import java.util.List;

/**
 * Created by liqing on 18/7/25.
 */

public class ProducerUpdateBean extends BaseBean {

    /**
     * id : 3
     * name : 更新测试
     * checker : 24
     * fath : 5
     * dpts : [120]
     */

    private int id;
    private String name;
    private int checker;

    private List<Integer> dpts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public List<Integer> getDpts() {
        return dpts;
    }

    public void setDpts(List<Integer> dpts) {
        this.dpts = dpts;
    }
}

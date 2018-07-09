package com.jci.vsd.bean.wait.out;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class ValueKeyBean extends BaseBean {
    private String name;
    private String key;

    public ValueKeyBean() {
    }

    public ValueKeyBean(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

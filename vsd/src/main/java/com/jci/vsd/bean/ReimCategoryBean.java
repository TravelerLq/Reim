package com.jci.vsd.bean;

/**
 * Created by liqing on 18/7/17.
 */

public class ReimCategoryBean extends BaseBean {
    private int key;
    private String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ReimCategoryBean(int key, String value) {
        this.key = key;
        this.value = value;
    }
}

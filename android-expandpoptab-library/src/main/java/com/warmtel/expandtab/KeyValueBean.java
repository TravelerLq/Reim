package com.warmtel.expandtab;

public class KeyValueBean  {
    private String key;
    private String value;
   private  Boolean isCheck;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public KeyValueBean(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public KeyValueBean(String key, String value, Boolean isCheck) {
        this.key = key;
        this.value = value;
        this.isCheck = isCheck;
    }

    public KeyValueBean() {
        super();
    }

    public Boolean getCheck() {
        return isCheck;
    }

    public void setCheck(Boolean check) {
        isCheck = check;
    }
}

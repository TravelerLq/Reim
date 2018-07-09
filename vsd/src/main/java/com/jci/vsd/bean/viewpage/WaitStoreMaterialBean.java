package com.jci.vsd.bean.viewpage;


import com.jci.vsd.bean.BaseBean;

/**
 * 待存料对象
 * Created by Victor on 11/6/2017.
 */

public class WaitStoreMaterialBean extends BaseBean {

    private String sapNo;
    private String description;
    private String number;
    private String unit;
    private String areaSeat;

    public WaitStoreMaterialBean() {
    }

    public WaitStoreMaterialBean(String sapNo, String description, String number, String unit, String areaSeat) {
        this.sapNo = sapNo;
        this.description = description;
        this.number = number;
        this.unit = unit;
        this.areaSeat = areaSeat;
    }

    public String getSapNo() {
        return sapNo;
    }

    public void setSapNo(String sapNo) {
        this.sapNo = sapNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getAreaSeat() {
        return areaSeat;
    }

    public void setAreaSeat(String areaSeat) {
        this.areaSeat = areaSeat;
    }
}

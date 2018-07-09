package com.jci.vsd.bean.differReport;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by liqing on 17/11/21.
 * 存料差异报告－responsebean
 */

public class DifferReportResponseBean extends BaseBean {


    /**
     * MaterialCode : 5162101
     * StockNum : 1
     * ReseriveNum : 1
     * DifferentNum : 0
     * ReseriveDate : 2017-11-23
     * StockId : d4bb2fc6-7950-48a9-96a6-314f3d15715d
     */
    private String delveryCode;
    private String MaterialCode;
    private String StockNum;
    private String ReseriveNum;
    private String DifferentNum;
    private String ReseriveDate;
    private String StockId;
    private String Unit;

    public String getDelveryCode() {
        return delveryCode;
    }

    public void setDelveryCode(String delveryCode) {
        this.delveryCode = delveryCode;
    }

    public String getMaterialCode() {
        return MaterialCode;
    }

    public void setMaterialCode(String MaterialCode) {
        this.MaterialCode = MaterialCode;
    }

    public String getStockNum() {
        return StockNum;
    }

    public void setStockNum(String StockNum) {
        this.StockNum = StockNum;
    }

    public String getReseriveNum() {
        return ReseriveNum;
    }

    public void setReseriveNum(String ReseriveNum) {
        this.ReseriveNum = ReseriveNum;
    }

    public String getDifferentNum() {
        return DifferentNum;
    }

    public void setDifferentNum(String DifferentNum) {
        this.DifferentNum = DifferentNum;
    }

    public String getReseriveDate() {
        return ReseriveDate;
    }

    public void setReseriveDate(String ReseriveDate) {
        this.ReseriveDate = ReseriveDate;
    }

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String StockId) {
        this.StockId = StockId;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}

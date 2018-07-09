package com.jci.vsd.bean.differReport;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 *  Created by liqing on 17/11/21.
 * 存料差异报告详情－返回bean
 */

public class DifferReportDetailResponseBean extends BaseBean {


    /**
     * MaterialCode : 5162101
     * StoreArea : 800000
     * StoreNum : 1
     * reserveNum : 1
     * diffNum : 0
     * describe : 器械零件
     * reserveCode : R201711210063
     * DealerName : 联德机械（杭州）有限公司
     * ReserveDate : /Date(1511404445890)/
     * RealReserveDate : 2017-11-23
     */

    private String MaterialCode;
    private String StoreArea;
    private String StoreNum;
    private String reserveNum;
    private String diffNum;
    private String describe;
    private String reserveCode;
    private String DealerName;
    private String ReserveDate;
    private String RealReserveDate;
    private String Unit="";

    public String getMaterialCode() {
        return MaterialCode;
    }

    public void setMaterialCode(String MaterialCode) {
        this.MaterialCode = MaterialCode;
    }

    public String getStoreArea() {
        return StoreArea;
    }

    public void setStoreArea(String StoreArea) {
        this.StoreArea = StoreArea;
    }

    public String getStoreNum() {
        return StoreNum;
    }

    public void setStoreNum(String StoreNum) {
        this.StoreNum = StoreNum;
    }

    public String getReserveNum() {
        return reserveNum;
    }

    public void setReserveNum(String reserveNum) {
        this.reserveNum = reserveNum;
    }

    public String getDiffNum() {
        return diffNum;
    }

    public void setDiffNum(String diffNum) {
        this.diffNum = diffNum;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getReserveCode() {
        return reserveCode;
    }

    public void setReserveCode(String reserveCode) {
        this.reserveCode = reserveCode;
    }

    public String getDealerName() {
        return DealerName;
    }

    public void setDealerName(String DealerName) {
        this.DealerName = DealerName;
    }

    public String getReserveDate() {
        return ReserveDate;
    }

    public void setReserveDate(String ReserveDate) {
        this.ReserveDate = ReserveDate;
    }

    public String getRealReserveDate() {
        return RealReserveDate;
    }

    public void setRealReserveDate(String RealReserveDate) {
        this.RealReserveDate = RealReserveDate;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }
}

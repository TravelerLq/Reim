package com.jci.vsd.bean.differReport;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 *  Created by liqing on 17/11/21.
 * 存料差异报告－请求bean
 */

public class DifferReportRequestBean extends BaseBean {


    /**
     * StartTime : 2017-1-1
     * EndTime : 2017-12-30
     * PageSize : 1
     * PageNum : 20
     */

    private String StartTime;
    private String EndTime;
    private int PageSize;
    private int PageNum;

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public int getPageSize() {
        return PageSize;
    }

    public void setPageSize(int PageSize) {
        this.PageSize = PageSize;
    }

    public int getPageNum() {
        return PageNum;
    }

    public void setPageNum(int PageNum) {
        this.PageNum = PageNum;
    }

    public DifferReportRequestBean(String startTime, String endTime, int pageSize, int pageNum) {
        StartTime = startTime;
        EndTime = endTime;
        PageSize = pageSize;
        PageNum = pageNum;
    }
}

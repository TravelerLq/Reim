package com.jci.vsd.bean.notice;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by liqing on 17/12/6.
 */

public class NoticeRequestBean extends BaseBean {
    String Receiver;
    String PageSize;
    String PageIndex;
    private String status = "";

    public NoticeRequestBean(String receiver, String status) {
        Receiver = receiver;
        this.status = status;
    }

    public NoticeRequestBean(String receiver, String pageSize, String pageIndex) {
        Receiver = receiver;
        PageSize = pageSize;
        PageIndex = pageIndex;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }

    public String getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(String pageIndex) {
        PageIndex = pageIndex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

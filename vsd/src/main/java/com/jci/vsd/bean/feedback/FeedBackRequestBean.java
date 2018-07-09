package com.jci.vsd.bean.feedback;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class FeedBackRequestBean extends BaseBean {

    public FeedBackRequestBean( String userId, String pageNum,String pageSize) {
        this.pageSize = pageSize;
        this.userId = userId;
        this.pageNum = pageNum;
    }

    private String pageSize;
    private String userId;
    private String pageNum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }
}

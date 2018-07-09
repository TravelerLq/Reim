package com.jci.vsd.bean.feedback;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by Administrator on 2017/11/14 0014.
 * 信息反馈详情
 */

public class FeedBackDetailRequestBean extends BaseBean {
    private String submitId;
    private String type;
    public FeedBackDetailRequestBean(String userId,String submitId,String type) {
        this.userId = userId;
        this.submitId = submitId;
        this.type = type;
    }

    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSubmitId() {
        return submitId;
    }

    public void setSubmitId(String submitId) {
        this.submitId = submitId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

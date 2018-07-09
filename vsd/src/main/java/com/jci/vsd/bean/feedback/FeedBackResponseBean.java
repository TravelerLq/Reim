package com.jci.vsd.bean.feedback;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class FeedBackResponseBean extends BaseBean {
    private String SubmitUserID;
    private String ID;
    private String SubmitTime;
    private String Content;
    private String Status;
    private String Title;

    public FeedBackResponseBean() {
    }

    public FeedBackResponseBean(String submitUserID, String ID, String submitTime, String content, String status) {
        SubmitUserID = submitUserID;
        this.ID = ID;
        SubmitTime = submitTime;
        Content = content;
        Status = status;
    }

    public String getSubmitUserID() {
        return SubmitUserID;
    }

    public void setSubmitUserID(String submitUserID) {
        SubmitUserID = submitUserID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(String submitTime) {
        SubmitTime = submitTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}

package com.jci.vsd.bean.feedback;

import com.jci.vsd.bean.dialog.BaseBean;

import java.io.StringReader;
import java.util.List;

/**
 * Created by Administrator on 2017/11/14 0014.
 * 信息反馈详情
 */

public class FeedBackDetailResponseBean extends BaseBean {
    private String SubmitUserID;
    private String SubmitContent;
    private String SubmitTime;
    private String BackContent;
    private String BackTime;
    private String SubmitTitle;
    private List<String> SubmitImg;
    private List<String> BackImg;
    private String BackId;

    public String getSubmitUserID() {
        return SubmitUserID;
    }

    public void setSubmitUserID(String submitUserID) {
        SubmitUserID = submitUserID;
    }

    public String getSubmitContent() {
        return SubmitContent;
    }

    public void setSubmitContent(String submitContent) {
        SubmitContent = submitContent;
    }

    public String getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(String submitTime) {
        SubmitTime = submitTime;
    }

    public String getBackContent() {
        return BackContent;
    }

    public void setBackContent(String backContent) {
        BackContent = backContent;
    }

    public String getBackTime() {
        return BackTime;
    }

    public void setBackTime(String backTime) {
        BackTime = backTime;
    }

    public String getSubmitTitle() {
        return SubmitTitle;
    }

    public void setSubmitTitle(String submitTitle) {
        SubmitTitle = submitTitle;
    }

    public List<String> getSubmitImg() {
        return SubmitImg;
    }

    public void setSubmitImg(List<String> submitImg) {
        SubmitImg = submitImg;
    }

    public List<String> getBackImg() {
        return BackImg;
    }

    public void setBackImg(List<String> backImg) {
        BackImg = backImg;
    }

    public String getBackId() {
        return BackId;
    }

    public void setBackId(String backId) {
        BackId = backId;
    }
}

package com.jci.vsd.bean.feedback;

import com.jci.vsd.bean.dialog.BaseBean;

import java.util.List;

/**
 * Created by liqing on 17/11/21.
 * 提交信息反馈-requestBean
 */

public class SubmitFeedBackRequestBean extends BaseBean {
    private String userId;
    private String content;
    private String title;
    private List<String> imgArray;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgArray() {
        return imgArray;
    }

    public void setImgArray(List<String> imgArray) {
        this.imgArray = imgArray;
    }
}

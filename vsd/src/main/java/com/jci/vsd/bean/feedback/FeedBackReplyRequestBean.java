package com.jci.vsd.bean.feedback;

import com.jci.vsd.bean.dialog.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class FeedBackReplyRequestBean extends BaseBean {
    private String userId;
    private List<String> imgArray;
    private String content;
    private String SubmitId;

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

    public String getSubmitId() {
        return SubmitId;
    }

    public void setSubmitId(String submitId) {
        SubmitId = submitId;
    }

    public List<String> getImgArray() {
        return imgArray;
    }

    public void setImgArray(List<String> imgArray) {
        this.imgArray = imgArray;
    }
}

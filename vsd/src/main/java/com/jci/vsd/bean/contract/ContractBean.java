package com.jci.vsd.bean.contract;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/4.
 * 合同管理bean
 */

public class ContractBean extends BaseBean {

    private String title;
    private String content;
    private String time;

    public ContractBean(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

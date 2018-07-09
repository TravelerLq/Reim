package com.jci.vsd.bean.download;

import com.jci.vsd.bean.dialog.BaseBean;


/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class CheckUpdateResponse extends BaseBean {
    private String Name;
    private String Type;
    private String Content;
    private String Url;

    public CheckUpdateResponse() {
    }

    public CheckUpdateResponse(String name, String type, String content, String url) {
        Name = name;
        Type = type;
        Content = content;
        Url = url;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}

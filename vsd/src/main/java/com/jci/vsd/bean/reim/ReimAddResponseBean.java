package com.jci.vsd.bean.reim;

import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/26.
 * 提交报销单成功后 返回的Bean
 */

public class ReimAddResponseBean extends BaseBean {

    /**  图片的二进制流数据
     * bytes : iVBORw0KGgoAAAANSUhEUgAAAxgAAAJkCAYAAACS6odqAAAAIGNIUk0AAHoIKDBERERERUWCIiIiIiMjk5/8BLoZjnMFpK0sAAAAASUVORK5CYII=
     * name : 9e0c9436ac7b4a059b7fcadde39e307f.png  png格式图片的名字
     * id : 35 报销单id
     */

    private String bytes;
    private String name;
    private int id;
    private String uri;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getBytes() {
        return bytes;
    }

    public void setBytes(String bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

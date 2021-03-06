package com.jci.vsd.bean.enterprise;


import com.jci.vsd.bean.BaseBean;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeID;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeManager;
import com.jci.vsd.view.widget.treelist.bean.TreeNodeName;
import com.jci.vsd.view.widget.treelist.bean.TreeNodePID;

/**
 * Created by liqing on 18/6/29.
 * 成员管理
 */

public class MembersBean extends BaseBean {

    @TreeNodeID
    private String id;
    @TreeNodePID
    private String pid;
    @TreeNodeName
    private String name;

    private String status;
    @TreeNodeManager
    Boolean leader;

    public Boolean getLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

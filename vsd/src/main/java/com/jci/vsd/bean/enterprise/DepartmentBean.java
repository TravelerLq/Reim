package com.jci.vsd.bean.enterprise;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/2.
 */

public class DepartmentBean extends BaseBean {


    /**
     * leader : 28
     * lname : 屠正松
     * perm : 1
     * name : 事业部
     * id : 111
     * permname : 管理类费用
     */

    private int leader;
    private String lname;
    private int perm;
    private String name;
    private int id;
    private String permname;

    public int getLeader() {
        return leader;
    }

    public void setLeader(int leader) {
        this.leader = leader;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public int getPerm() {
        return perm;
    }

    public void setPerm(int perm) {
        this.perm = perm;
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

    public String getPermname() {
        return permname;
    }

    public void setPermname(String permname) {
        this.permname = permname;
    }
}

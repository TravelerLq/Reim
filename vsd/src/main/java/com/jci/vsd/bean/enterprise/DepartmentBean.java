package com.jci.vsd.bean.enterprise;


import com.jci.vsd.bean.BaseBean;

/**
 * Created by liqing on 18/7/2.
 */

public class DepartmentBean extends BaseBean {

    /**
     * departmentId : 55
     * departmentName : 生产部
     * departmentQuota : 1000
     * deptLeaderId : 54
     * deptLeaderName : 王朕
     * numberOfEmployees : 0
     * reimbursementRightId : 3
     * reimbursementRightName : 制造类费用
     */

    private int departmentId;
    private String departmentName;
    private String departmentQuota;
    private int deptLeaderId;
    private String deptLeaderName;
    private int numberOfEmployees;
    private int reimbursementRightId;
    private String reimbursementRightName;

    public DepartmentBean(int departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentQuota() {
        return departmentQuota;
    }

    public void setDepartmentQuota(String departmentQuota) {
        this.departmentQuota = departmentQuota;
    }

    public int getDeptLeaderId() {
        return deptLeaderId;
    }

    public void setDeptLeaderId(int deptLeaderId) {
        this.deptLeaderId = deptLeaderId;
    }

    public String getDeptLeaderName() {
        return deptLeaderName;
    }

    public void setDeptLeaderName(String deptLeaderName) {
        this.deptLeaderName = deptLeaderName;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public int getReimbursementRightId() {
        return reimbursementRightId;
    }

    public void setReimbursementRightId(int reimbursementRightId) {
        this.reimbursementRightId = reimbursementRightId;
    }

    public String getReimbursementRightName() {
        return reimbursementRightName;
    }

    public void setReimbursementRightName(String reimbursementRightName) {
        this.reimbursementRightName = reimbursementRightName;
    }
}

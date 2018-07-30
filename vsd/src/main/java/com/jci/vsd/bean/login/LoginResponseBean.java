package com.jci.vsd.bean.login;



import com.jci.vsd.bean.BaseBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class LoginResponseBean extends BaseBean {
    private String Id;
    private String Code;
    private String Name;
    private String LogName;
    private String EmployeeId;
    private String CardId;
    private String Password;
    private String LastLogTime;
    private String CreateTime;
    private String UpdateTime;
    private String UpdateUser;
    private String Status;
    private String CreateUser;
    private String UserType;

    //1=boss 2=部门领导 3=员工
    private String role;


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private List<HomeAuthorityBean> list;


    public LoginResponseBean() {
    }

    public LoginResponseBean(String id, String code, String name, String logName, String employeeId, String cardId, String password, String lastLogTime, String createTime, String updateTime, String updateUser, String status, String createUser) {
        Id = id;
        Code = code;
        Name = name;
        LogName = logName;
        EmployeeId = employeeId;
        CardId = cardId;
        Password = password;
        LastLogTime = lastLogTime;
        CreateTime = createTime;
        UpdateTime = updateTime;
        UpdateUser = updateUser;
        Status = status;
        CreateUser = createUser;
    }
//   public void setList(List<HomeAuthorityBean>list){
//       this.list=list;
//   }


    public List<HomeAuthorityBean> getList() {
        return list;
    }

    public void setList(List<HomeAuthorityBean> list) {
        this.list = list;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLogName() {
        return LogName;
    }

    public void setLogName(String logName) {
        LogName = logName;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getLastLogTime() {
        return LastLogTime;
    }

    public void setLastLogTime(String lastLogTime) {
        LastLogTime = lastLogTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUpdateTime() {
        return UpdateTime;
    }

    public void setUpdateTime(String updateTime) {
        UpdateTime = updateTime;
    }

    public String getUpdateUser() {
        return UpdateUser;
    }

    public void setUpdateUser(String updateUser) {
        UpdateUser = updateUser;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}

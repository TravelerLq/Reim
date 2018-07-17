package com.jci.vsd.data;

import com.anupcowkur.reservoir.Reservoir;
import com.jci.vsd.bean.UserBean;
import com.jci.vsd.constant.AppConstant;


/**
 * Description：用户信息
 * Created by：Kyle
 * Date：2017/2/9
 */
public class UserData {
    public static UserBean getUserInfo(){
        UserBean userBean=null;
        try {
            if (Reservoir.contains(AppConstant.KEY_USER)) {
                userBean= Reservoir.get(AppConstant.KEY_USER, UserBean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userBean;
    }
    public static void saveUser(UserBean userBean){
        try {
            Reservoir.put(AppConstant.KEY_USER,userBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void removeUser(){
        try {
            Reservoir.delete(AppConstant.KEY_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

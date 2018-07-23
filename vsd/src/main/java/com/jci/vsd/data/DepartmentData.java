package com.jci.vsd.data;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import com.jci.vsd.bean.enterprise.DepartmentBean;
import com.jci.vsd.bean.enterprise.EnterpriseRequestBean;
import com.jci.vsd.bean.enterprise.GetEnterInfoBean;
import com.jci.vsd.constant.AppConstant;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liqing on 18/7/13.
 */

public class DepartmentData {


    public static DepartmentBean getBean() {
        DepartmentBean enterpriseBean = null;
        try {
            if (Reservoir.contains(AppConstant.KEY_DEPARTMENT)) {
                enterpriseBean = Reservoir.get(AppConstant.KEY_DEPARTMENT, DepartmentBean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enterpriseBean;
    }

    public static void saveEnterprise(DepartmentBean bean) {
        try {
            Reservoir.put(AppConstant.KEY_DEPARTMENT, bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeEnterprise() {
        try {
            Reservoir.delete(AppConstant.KEY_DEPARTMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //保存公司集合
    public static void saveDepartmentList(List<DepartmentBean> list) {
        try {
            Reservoir.put(AppConstant.KEY_DEPARTMENT_LIST, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //保存公司集合
    public static List<DepartmentBean> getDepartmentList() {
        List<DepartmentBean> list = null;
        try {
            Type resultType = new TypeToken<List<DepartmentBean>>() {

            }.getType();

            list = Reservoir.get(AppConstant.KEY_ENTERPRISE_LIST, resultType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

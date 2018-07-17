package com.jci.vsd.data;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import com.jci.vsd.bean.enterprise.EnterpriseBean;
import com.jci.vsd.bean.enterprise.EnterpriseRequestBean;
import com.jci.vsd.bean.enterprise.GetEnterInfoBean;
import com.jci.vsd.constant.AppConstant;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liqing on 18/7/13.
 */

public class EnterpriseData {


    public static EnterpriseRequestBean getEnterpriseBean() {
        EnterpriseRequestBean enterpriseBean = null;
        try {
            if (Reservoir.contains(AppConstant.KEY_ENTERPRISE)) {
                enterpriseBean = Reservoir.get(AppConstant.KEY_ENTERPRISE, EnterpriseRequestBean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enterpriseBean;
    }

    public static void saveEnterprise(EnterpriseRequestBean enterpriseBean) {
        try {
            Reservoir.put(AppConstant.KEY_ENTERPRISE, enterpriseBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeEnterprise() {
        try {
            Reservoir.delete(AppConstant.KEY_ENTERPRISE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //保存公司集合
    public static void saveEnterpriseList(List<GetEnterInfoBean.CosBean> list) {
        try {
            Reservoir.put(AppConstant.KEY_ENTERPRISE_LIST, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //保存公司集合
    public static List<GetEnterInfoBean.CosBean> getEnterpriseList() {
        List<GetEnterInfoBean.CosBean> list = null;
        try {
            Type resultType = new TypeToken<List<GetEnterInfoBean.CosBean>>() {

            }.getType();

            list = Reservoir.get(AppConstant.KEY_ENTERPRISE_LIST, resultType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}

package com.jci.vsd.data;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import com.jci.vsd.bean.CatBean;
import com.jci.vsd.bean.enterprise.AddBudgetItemBean;
import com.jci.vsd.bean.reim.ReimAddItemBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.utils.Loger;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liqing on 18/7/25.
 */

public class ReimAddData {

    public static ReimAddItemBean getReimAddItemBean() {
        ReimAddItemBean enterpriseBean = null;
        try {
            if (Reservoir.contains(AppConstant.KEY_REIM_ITEM)) {
                enterpriseBean = Reservoir.get(AppConstant.KEY_REIM_ITEM, ReimAddItemBean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enterpriseBean;
    }

    public static void saveReimAddItemBean(ReimAddItemBean enterpriseBean) {
        try {
            Reservoir.put(AppConstant.KEY_REIM_ITEM, enterpriseBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveReimItemList(List<ReimAddItemBean> list) {
        try {
            Reservoir.put(AppConstant.KEY_REIM_ITEM_LIST, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<ReimAddItemBean> getReimItemList() {
        List<ReimAddItemBean> list = null;
        Type resultType = new TypeToken<List<ReimAddItemBean>>() {

        }.getType();

        try {
            list = Reservoir.get(AppConstant.KEY_REIM_ITEM_LIST, resultType);
            for (int i = 0; i < list.size() - 1; i++) {
                Loger.e("list-reim--" + list.get(i).getRemark());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void removeReimList() {
        try {
            Reservoir.delete(AppConstant.KEY_REIM_ITEM_LIST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void updateReimList(int pos) {
//        try {
//            Reservoir.delete(AppConstant.KEY_REIM_ITEM_LIST);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}

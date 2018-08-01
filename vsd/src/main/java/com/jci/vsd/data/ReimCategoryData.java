package com.jci.vsd.data;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import com.jci.vsd.bean.CatBean;
import com.jci.vsd.bean.ReimCategoryBean;
import com.jci.vsd.constant.AppConstant;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liqing on 18/7/25.
 */

public class ReimCategoryData {


    public static void saveCategoryList(List<CatBean> list) {
        try {
            Reservoir.put(AppConstant.KEY_CATEGORY_LIST, list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static List<CatBean> getCategoryList() {
        List<CatBean> list = null;
        Type resultType = new TypeToken<List<CatBean>>() {

        }.getType();

        try {
            list = Reservoir.get(AppConstant.KEY_CATEGORY_LIST, resultType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

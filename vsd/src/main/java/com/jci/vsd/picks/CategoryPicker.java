package com.jci.vsd.picks;

import android.app.Activity;

import com.jci.vsd.bean.CatBean;

import java.util.ArrayList;

import cn.addapp.pickers.picker.LinkagePicker;

/**
 * Created by liqing on 18/7/20.
 */

public class CategoryPicker extends LinkagePicker {

    //省市区数据
    private ArrayList<CatBean> catBeans = new ArrayList<CatBean>();
    public CategoryPicker(Activity activity) {
        super(activity);
    }

    public CategoryPicker(Activity activity,ArrayList<CatBean> catBeans) {
        super(activity);
        this.catBeans = catBeans;
    }
}

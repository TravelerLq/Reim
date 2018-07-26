package com.jci.vsd.activity;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.jci.vsd.R;
import com.jci.vsd.bean.TestBean;
import com.jci.vsd.bean.reim.ReimAddItemBean;
import com.jci.vsd.data.ReimAddData;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 18/7/26.
 */

public class ReserviorTestActivity extends BaseActivity {
    List<ReimAddItemBean> mDatas = new ArrayList<>();
    List<Integer> integers = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        integers.add(87);
        integers.add(88);
        TestBean testBean = new TestBean();
        testBean.setIds(integers);
        String ids = JSON.toJSONString(testBean);
        Loger.e("===ids --"+ids);
        ReimAddItemBean bean;
        for (int i = 0; i < 3; i++) {
            bean = new ReimAddItemBean();
            bean.setRemark("reserviorTestRemark" + i);
            mDatas.add(bean);
        }
        ReimAddData.saveReimItemList(mDatas);
        List<ReimAddItemBean> list = ReimAddData.getReimItemList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Loger.e("--getReimTest-" + list.get(i).getRemark());
            }

        }
        ReimAddItemBean updateBean = new ReimAddItemBean();
        updateBean.setRemark("updateBean2");
        mDatas.set(2, updateBean);
        ReimAddData.removeReimList();
//        ReimAddData.saveReimItemList(mDatas);

        List<ReimAddItemBean> listUpdate = ReimAddData.getReimItemList();
        if (listUpdate != null) {
            for (int i = 0; i < listUpdate.size(); i++) {
                Loger.e("--getReimTestlistUpdate-" + listUpdate.get(i).getRemark());
            }

        }


//        List<ReimAddItemBean> list1 = ReimAddData.getReimItemList();
//        if (list1 != null) {
//            for (int i = 0; i < list1.size(); i++) {
//                Loger.e("--getReimTest-" + list1.get(i).getRemark());
//            }
//            mDatas.addAll(list);
//        }


    }
}

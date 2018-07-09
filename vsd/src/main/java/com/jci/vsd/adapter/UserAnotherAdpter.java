package com.jci.vsd.adapter;

import android.view.View;

import com.jci.vsd.bean.UserBean;

import java.util.List;

/**
 * Created by liqing on 18/6/15.
 *
 * 测试类baseAdapter
 */

public class UserAnotherAdpter extends DefaultAdapter<UserBean> {

    public UserAnotherAdpter(List<UserBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<UserBean> getHolder(View v, int viewType) {
        return null;
    }

    @Override
    public int getLayoutId(int viewType) {
        return 0;
    }
}

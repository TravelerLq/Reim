package com.jci.vsd.adapter;

import android.content.Context;
import android.view.View;

import com.jci.vsd.R;
import com.jci.vsd.bean.UserBean;

import java.util.List;

/**
 * Created by liqing on 18/6/15.
 */

public class UserAdapter extends BaseAdapter<UserBean> {

    private List<UserBean> mData;

    public UserAdapter(List<UserBean> mData, Context context) {
        super(mData, context);
        this.mData = mData;
    }

    @Override
    public int getLayoutId(int viewTYpe) {
        return R.layout.item_user;
    }

    @Override
    public BaseHolder getHolder(View view) {
        return new UserItemHolder(view);
    }

    public void add(List<UserBean> items) {
        int previousDataSize = this.mData.size();
        this.mData.addAll(items);
       // notifyItemRangeInserted(previousDataSize, items.size());
       notifyDataSetChanged();
    }

}

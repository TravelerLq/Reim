package com.jci.vsd.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by liqing on 17/11/24.
 */

public abstract class CustomAdapter<T> extends BaseAdapter {
    private List<T> listData;

    public CustomAdapter(List<T> listData) {
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return listData.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}

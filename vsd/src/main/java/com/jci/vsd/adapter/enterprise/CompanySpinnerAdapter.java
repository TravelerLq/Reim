package com.jci.vsd.adapter.enterprise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jci.vsd.R;
import com.warmtel.expandtab.KeyValueBean;

import java.util.List;

/**
 * Created by liqing on 18/7/12.
 */

public class CompanySpinnerAdapter extends BaseAdapter {

    private List<KeyValueBean> mData;
    private Context context;

    public CompanySpinnerAdapter(List<KeyValueBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    public void setSelect(int pos) {
        for (int i = 0; i < mData.size(); i++) {
            if (i == pos) {
                mData.get(pos).setCheck(true);
            } else {
                mData.get(pos).setCheck(false);
            }

        }

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater = LayoutInflater.from(context);
        convertView = _LayoutInflater.inflate(R.layout.spinner_item, null);
        if (convertView != null) {
            TextView textView = (TextView) convertView.findViewById(R.id.tv_spinner);
            textView.setText(mData.get(position).getValue());

        }
        return convertView;

    }
}

package com.jci.vsd.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.bean.UserBean;
import com.jci.vsd.utils.Loger;

/**
 * Created by liqing on 18/6/15.
 */

public class UserItemHolder extends BaseHolder<UserBean> implements View.OnClickListener {

    TextView tvName;
    ImageView imageView;

    public UserItemHolder(View itemView) {

        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        imageView = (ImageView) itemView.findViewById(R.id.iv_avatar);


    }

    @Override
    public void setData(UserBean data, int pos) {

        tvName.setText(data.getPhone());
        tvName.setOnClickListener(this);
        imageView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        //tvDelete -->  listener.delet    tvAdd-->listener.add

//        if(listener!=null)
//        if (v.getId() == R.id.tv_name) {
//
//            Loger.e("tv_name--");
//        } else if (v.getId() == R.id.iv_avatar) {
//            Loger.e("---iv_avatar");
//        }
    }

    //setClickListener();
}

package com.jci.vsd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jci.vsd.R;
import com.jci.vsd.bean.PicBean;

import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class PicAdapater extends BaseAdapter {
    private Context context;
    private List<PicBean> mList;
    private LayoutInflater layoutInflater;

    public PicAdapater(Context context,List<PicBean> list){
        this.context = context;
        this.mList = list;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        if(mList == null)
            return 0;
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.item_pic_layout,null);
            viewHolder = new ViewHolder();
            viewHolder.picItemImg = (ImageView) view.findViewById(R.id.picItemImg);
            viewHolder.deleteBtn = (ImageButton) view.findViewById(R.id.deleteBtn);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        PicBean picBean = mList.get(position);
        if(picBean.getStatus() == 0) {
            viewHolder.picItemImg.setImageResource(R.drawable.add_feed_back);
            viewHolder.deleteBtn.setVisibility(View.GONE);
        }else{
            viewHolder.picItemImg.setImageBitmap(picBean.getBitmap());
            viewHolder.deleteBtn.setVisibility(View.VISIBLE);
        }
        viewHolder.deleteBtn.setOnClickListener(new MyOnClick(position));
        return view;
    }

    private static class ViewHolder{
        private ImageView picItemImg;
        private ImageButton deleteBtn;
    }

    private class MyOnClick implements View.OnClickListener{
        private int position;

        public MyOnClick(int position){
            this.position = position;
        }
        @Override
        public void onClick(View view) {
            mList.remove(position);
            notifyDataSetChanged();
        }
    }
}

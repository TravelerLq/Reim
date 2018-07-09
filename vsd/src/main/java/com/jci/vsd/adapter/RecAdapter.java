package com.jci.vsd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.bean.UserBean;
import com.jci.vsd.utils.Loger;

import java.util.List;

/**
 * Created by liqing on 18/6/15.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {
    private List<UserBean> mData;

    private Context context;
    private LayoutInflater inflater;
    private ItemClickListener listener;

    public RecAdapter(List<UserBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
        this.inflater = LayoutInflater.from(this.context);
    }

    public interface ItemClickListener {

        void onItemclick(View view, int pos);

        void onImageClick(View view, int pos);

    }


    public void add(List<UserBean> items) {
        int previousDataSize = this.mData.size();
        this.mData.addAll(items);
         notifyItemRangeInserted(previousDataSize, items.size());
       // notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        View view = inflater.inflate(R.layout.item_user, parent, false);
        if (myViewHolder == null) {
            myViewHolder = new MyViewHolder(view);
        }

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(mData.get(position).getLogin());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    Loger.e("---RecAdapter--click");
                    listener.onItemclick(v, position);
                }
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    Loger.e("---RecAdapter--click");
                    listener.onImageClick(v, position);
                }
            }
        });




    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView imageView;

        MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_name);

            imageView = (ImageView) itemView.findViewById(R.id.iv_avatar);
        }
    }
}

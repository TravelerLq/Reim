package com.jci.vsd.adapter.reim;

/**
 * Created by liqing on 18/6/28.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.bean.reim.MyReimDetailBean;
import com.jci.vsd.bean.reim.WaitApprovalDetailBean;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 18/3/20.
 *
 */

public class ApprovalDetailRecycleAdapter extends RecyclerView.Adapter<ApprovalDetailRecycleAdapter.MyViewHolder> {

    private Context context;
    private List<MyReimDetailBean.CostsBean> list;
    private LayoutInflater inflater;
    private List<Bitmap> listBitmap = new ArrayList<>();

    public ApprovalDetailRecycleAdapter(Context context, List<MyReimDetailBean.CostsBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(this.context);
    }

    public void setListBitmap(List<Bitmap> list) {
        this.listBitmap = list;

    }

    public static interface OnItemClickListener {
          void OnItemClick(View view, int pos);
        void onPicCLick(View view, int pos);

    }

    //    public static interface OnItemClickListener {
//        void OnItemClick(View view, int pos);
//    }
    public ApprovalDetailRecycleAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(ApprovalDetailRecycleAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        if (myViewHolder == null) {
            myViewHolder = new MyViewHolder(inflater.inflate(R.layout.item_approval_detail, parent, false));

        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MyReimDetailBean.CostsBean bean = list.get(position);
        if (bean != null) {
            if (holder.tvExplain == null) {
                Log.e("holder.tvTitle", "null");
            } else {
                holder.tvFee.setText(bean.getAmount()+"");
                holder.tvExplain.setText(bean.getRemark());
                holder.tvType.setText(bean.getName());
                holder.rlItem.setTag(position);
                holder.ivPic.setTag(position);
                if (listBitmap.size() == 0) {
                    Loger.e("listmap.size()==000");
                } else {
                    holder.ivPic.setImageBitmap(listBitmap.get(position));
                }
                holder.rlItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.OnItemClick(view, (int) view.getTag());
                        }
                    }
                });


                holder.ivPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.onPicCLick(view, (int) view.getTag());
                        }
                        //bean.setBitmap(listBitmap.get(position));

//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("itemBean", bean);
//                        Intent intent = new Intent(context, ViewImageActivity.class);
//                        intent.putExtras(bundle);
////                        Intent intent = new Intent(context, ViewImageActivity.class);
////                        intent.putExtra("data", bean.getFileData());
//                        context.startActivity(intent);

                    }
                });

            }

        }

    }

    @Override
    public int getItemCount() {

        if (list == null)
            return 0;
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvFee;
        TextView tvDate;
        TextView tvType;
        TextView tvExplain;
        RelativeLayout rlItem;
        ImageView ivPic;
        View view;

        public MyViewHolder(View view) {
            super(view);
            tvFee = (TextView) view.findViewById(R.id.tv_money);
            tvDate = (TextView) view.findViewById(R.id.tv_expense_time);
            tvType = (TextView) view.findViewById(R.id.tv_expense_category);
            tvExplain = (TextView) view.findViewById(R.id.tv_explain);
            rlItem = (RelativeLayout) view.findViewById(R.id.rl_expense_item);
            ivPic = (ImageView) view.findViewById(R.id.iv_expense_item_pic);
            this.view = view;
        }
    }
}

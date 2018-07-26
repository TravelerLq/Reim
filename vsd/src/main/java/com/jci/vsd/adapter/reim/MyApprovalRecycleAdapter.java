package com.jci.vsd.adapter.reim;

/**
 * Created by liqing on 18/6/28.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.bean.reim.ApprovalBean;

import java.util.List;

/**
 * Created by liqing on 18/3/20.
 * 待审批列表Adapter
 */

public class MyApprovalRecycleAdapter extends RecyclerView.Adapter<MyApprovalRecycleAdapter.MyViewHolder> {

    private Context context;
    private List<ApprovalBean> list;
    private LayoutInflater inflater;

    public MyApprovalRecycleAdapter(Context context, List<ApprovalBean> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(this.context);
    }

    public static interface OnItemClickListener {
        void OnItemClick(View view, int pos);
    }

    public MyApprovalRecycleAdapter.OnItemClickListener listener;

    public void setOnItemClickListener(MyApprovalRecycleAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = null;
        if (myViewHolder == null) {
            myViewHolder = new MyViewHolder(inflater.inflate(R.layout.item_wait_approval, parent, false));

        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ApprovalBean bean = list.get(position);
        if (bean != null) {
            if (holder.tvFee == null) {
                Log.e("holder.tvTitle", "null");
            } else {
                Log.e("holder.tvTitle", "not null");
                holder.tvFee.setText(bean.getTotal());
                //  holder.tvExplain.setText(bean.getApprovalName());
                holder.tvDate.setText(bean.getDate());
                holder.tvNum.setText(String.valueOf(bean.getAnnex()));
//                String process = bean.getProcess();
//                if (process.length() >= 10) {
//                    process = process.substring(0, 9) + "...";
//                }

                holder.tvName.setText(bean.getAppl());
                holder.rlItem.setTag(position);
                holder.rlItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (listener != null) {
                            listener.OnItemClick(view, (int) view.getTag());
                        }
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
        TextView tvName;
        TextView tvDate;
        TextView tvNum;
        TextView tvExplain;
        TextView tvDepartment;
        LinearLayout rlItem;
        View view;

        public MyViewHolder(View view) {
            super(view);
            tvFee = (TextView) view.findViewById(R.id.tv_money);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvDate = (TextView) view.findViewById(R.id.tv_expense_time);
            tvNum = (TextView) view.findViewById(R.id.tv_expense_num);
            //   tvExplain = view.findViewById(R.id.tv_explain);
            rlItem = (LinearLayout) view.findViewById(R.id.rl_expense_item);
            tvDepartment = (TextView) view.findViewById(R.id.tv_department);
            this.view = view;
        }
    }
}

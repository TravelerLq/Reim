package com.jci.vsd.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.jci.vsd.utils.Loger;

/**
 * Created by liqing on 18/6/14
 * 只是封装一个BaseHolder  setdata （bindViewHolder）
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    public OnHolderClickListener listener;

    private SparseArray<View> mViews;

    public interface OnHolderClickListener {
        void onHolderClick(View view, int pos);
    }

    public void setOnHolderClickListener(OnHolderClickListener listener) {
        this.listener = listener;

    }

    /**
     * 根据viewId 获取view 双向过程 凭什么他
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getViewId(int viewId) {
        View view = mViews.get(viewId);
        return (T)view;
    }

    public BaseHolder(View itemView) {
        super(itemView);
        // itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            Loger.e("---layoutPos=" + this.getLayoutPosition() + " oldPos="
                    + this.getOldPosition() + "getpos=" + this.getPosition());
            listener.onHolderClick(v, this.getPosition());

        }
    }

    public abstract void setData(T data, int pos);

}

package com.jci.vsd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jci.vsd.R;

import java.util.List;

/**
 * Created by liqing on 18/6/15.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {
    private static final String TAG = BaseAdapter.class.getName();
    protected BaseHolder mHolder;
    protected List<T> mData;
    protected Context context;
    protected int viewType;

    protected OnRecycleviewItemClickListener listener;

    public BaseAdapter(List<T> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }


    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(this.context).inflate(getLayoutId(viewType), parent, false);
        mHolder = getHolder(view);

        this.viewType = viewType;


//        mHolder.setOnItemClickListener(new BaseHolder.OnViewClickListener() {
//            //设置Item点击事件
//            @Override
//            public void onViewClick(View view, int position) {
//                if (mOnItemClickListener != null && mInfos.size() > 0) {
//                    Log.e(TAG, "onViewClick:--- " );
//                    mOnItemClickListener.onItemClick(view, viewType, mInfos.get(position), position);
//                }else{
//                    Log.e(TAG, "onViewClickListener:---null " );
//                }
//            }
//        });

        return mHolder;
    }


//    @Override
//    public void onBindViewHolder(BaseHolder<T> holder, int position, List<Object> payloads) {
//        mHolder.setData(mData.get(position), position);
//        super.onBindViewHolder(holder, position, payloads);
//    }


    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position) {
        mHolder.setData(mData.get(position), position);


        mHolder.setOnHolderClickListener(new BaseHolder.OnHolderClickListener() {
            @Override
            public void onHolderClick(View view, int pos) {
                if (listener != null) {
                    Log.e("baseAdaper item--", "--pos=" + pos);
                    listener.onItemClick(view, viewType, mData, pos);
                } else {
                    Log.e("listener--", "--null--");
                }
//                switch (view.getId()) {
//                    case R.id.tv_name:
//                        Log.e("tvname click--", "--pos=" + pos);
//                    default:
//                        break;
//
//                }


            }
        });

    }

    public List<T> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * @param viewTYpe
     * @return
     */

    public abstract int getLayoutId(int viewTYpe);


    /**
     * @param view
     * @return
     */

    public abstract BaseHolder getHolder(View view);

    public interface OnRecycleviewItemClickListener<T> {
        void onItemClick(View view, int viewType, T data, int pos);

    }


    public void setOnItemClickListener(OnRecycleviewItemClickListener listener) {
        this.listener = listener;
    }

}

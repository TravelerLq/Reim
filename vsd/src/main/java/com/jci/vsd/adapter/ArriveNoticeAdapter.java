package com.jci.vsd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.bean.ArriveNoticeBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 17/11/14.
 * 到料提醒listAdapter
 */

public class ArriveNoticeAdapter extends BaseAdapter {
   private  Context context;
    private List<ArriveNoticeBean> beanList = new ArrayList<>();
    private int pageNo;
    OnItemClickListener listener;

    public ArriveNoticeAdapter(Context context, List<ArriveNoticeBean> beanlist) {
        this.context = context;
        this.beanList = beanlist;
        if(this.context==null){
            Loger.e("---ReportAdapter-context null");
        }
    }

    public interface OnItemClickListener {
        void onBtnDetailClick(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }


    @Override
    public int getCount() {
        return beanList == null ? 0 : beanList.size();
    }

    @Override
    public Object getItem(int pos) {
        return pos;
    }

    @Override
    public long getItemId(int pos) {
        return pos + ArriveNoticeViewPageAdapter.HZ_VEWPAGE_EVERY_PAGE_NO * (pageNo);
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent) {
        final HolderView holder;
        if(context==null){
            Loger.e("----context=null");
        }
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_arrive_notice, null);
            holder = new HolderView();
            holder.tvArriveDate = (TextView) convertView.findViewById(R.id.tv_arrive_date);
            holder.tvContentInfo = (TextView) convertView.findViewById(R.id.tv_content_info);
            holder.tvArriveDate.setText(beanList.get(pos).getTime());
//            holder.btnDetail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //这里的pos 不再是list.get(pos)对应，要加上页码＊每页数量＋pos 才是对应的list里的数据
//                    if (listener != null)
//                        listener.onBtnDetailClick(pos);
//                }
//            });
            convertView.setTag(holder);

        } else {
            holder = (HolderView) convertView.getTag();
        }

        if (pos % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.select_btn_item_click_1_xml);
        } else {
            convertView.setBackgroundResource(R.drawable.select_btn_item_click_2_xml);
        }
        ArriveNoticeBean bean = beanList.get(pos);

        return convertView;
    }

    static class HolderView {
        TextView tvContentInfo;
        TextView tvArriveDate;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}

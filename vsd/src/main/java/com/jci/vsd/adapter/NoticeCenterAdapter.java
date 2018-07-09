package com.jci.vsd.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.bean.notice.UnreadNoticeBean;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.TimeUtils;

import java.util.List;

/**
 * Created by liqing on 17/12/5.
 */

public class NoticeCenterAdapter extends CustomAdapter<UnreadNoticeBean> {

    private Context mContext;
    private OnItemClickListener listener;
    private List<UnreadNoticeBean> beanList;

    public NoticeCenterAdapter(Context mContext, List<UnreadNoticeBean> listData) {
        super(listData);
        this.mContext = mContext;
        this.beanList = listData;
    }

    public interface OnItemClickListener {
        void detail(int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_notice_center, null);
            holder = new HolderView();
            holder.tvUnredCircle = (TextView) convertView.findViewById(R.id.tv_unread_circle);
            holder.tvUnreadText = (TextView) convertView.findViewById(R.id.tv_unread_text);
            holder.tvUnreadTitle = (TextView) convertView.findViewById(R.id.tv_unread_title);
            holder.llUnRead = (LinearLayout) convertView.findViewById(R.id.ll_item);
            holder.tvUnreadContent = (TextView) convertView.findViewById(R.id.tv_unread_content);
            holder.tvUnreadTime = (TextView) convertView.findViewById(R.id.tv_unread_time);
            holder.rlItem = (RelativeLayout) convertView.findViewById(R.id.rl_item);

            holder.rlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.detail(position);
                        Loger.e("detail_notice---pos=" + position);
                    }
                }
            });
            convertView.setTag(holder);

        } else {
            holder = (HolderView) convertView.getTag();
        }

        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.select_btn_item_click_1_xml);
        } else {
            convertView.setBackgroundResource(R.drawable.select_btn_item_click_2_xml);
        }
        UnreadNoticeBean bean = beanList.get(position);
        if(bean!=null&&beanList.size()>0){
            holder.rlItem.setVisibility(View.VISIBLE);
            holder.tvUnreadTitle.setText(bean.getMsgTitle());
            holder.tvUnreadTime.setText(TimeUtils.formartToMMDD(bean.getCreateDate()));
            if(bean.getMsgContent().length()>15){
                String content=bean.getMsgContent().substring(0,14)+"...";
                //bg_tab_select_blue
                holder.tvUnreadContent.setText(content);

            }else{
                holder.tvUnreadContent.setText(bean.getMsgContent());

            }

            // holder.tvUnreadContent.setText(bean.);
            //已读未读设置 0-未读
            if(!TextUtils.isEmpty(bean.getStatus())){
                if (bean.getStatus().equals("0")) {
                    holder.tvUnredCircle.setBackgroundResource(R.drawable.circle_blue);
                    holder.tvUnreadText.setText("未读");
                    holder.tvUnreadText.setTextColor(mContext.getResources().getColor(R.color.bg_tab_select_blue));
                }else{
                    holder.tvUnredCircle.setBackgroundResource(R.drawable.circle_grey);
                    holder.tvUnreadText.setText("已读");
                    holder.tvUnreadText.setTextColor(mContext.getResources().getColor(R.color.gray_B3B3B3));
                }
            }

        } else{
            holder.rlItem.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class HolderView {
        RelativeLayout rlItem;
        LinearLayout llUnRead;
        TextView tvUnredCircle;
        TextView tvUnreadText;
        TextView tvUnreadTitle;
        TextView tvUnreadContent;
        TextView tvUnreadTime;

    }


}

package com.jci.vsd.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.HomeBean;
import com.jci.vsd.bean.login.HomeAuthorityBean;
import com.jci.vsd.utils.DisplayUtils;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqing on 17/7/24.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.myViewHolder> {
    private static final String TAG = HomeAdapter.class.getSimpleName();
    private List<HomeBean> homeBeanList;
    private List<HomeAuthorityBean> authorityList = new ArrayList<>();
    private Context mContext;
    private OnItemClickListener mOnItemClickListener = null;
    private HomeAuthorityBean bean;


    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public HomeAdapter(List<HomeBean> homeBeanList, Context context) {
        this.mContext = context;


        //排序
        List<HomeBean> disableList = new ArrayList<>();
        List<HomeAuthorityBean> list = new ArrayList<>();
        authorityList = VsdApplication.getInstance().getLoginResponseBean().getList();
        int size = authorityList.size();
        for (int i = 0; i < size; i++) {
            if (authorityList.get(i).getHasAuthority().equals("false")) {
                disableList.add(homeBeanList.get(i));
                list.add(authorityList.get(i));
            }
        }
        homeBeanList.removeAll(disableList);
        authorityList.removeAll(list);
        homeBeanList.addAll(disableList);
        authorityList.addAll(list);

        this.homeBeanList = homeBeanList;

    }

    public void setAuthorityList(List<HomeAuthorityBean> authorityList) {

        this.authorityList = authorityList;
        //消息中心
//        bean=new HomeAuthorityBean();
//        bean.setHasAuthority("true");
//        bean.setName("notice");
//         this.authorityList.add(bean);
//        for (int i = 0; i < this.authorityList.size(); i++) {
//            Loger.e("pos=" + i + "homeAuthority--" + authorityList.get(i).getHasAuthority());
//        }
    }


    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 实例化展示的view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_view_home, parent, false);
        //将创建的View注册点击事件
        DisplayUtils.initNormal((Activity) mContext);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        //  params.height = (int) (DisplayUtils.nomalWidth / 6);
        params.width = (int) (DisplayUtils.nomalWidth / 3.272);
        params.height = params.width * 10 / 11;
//        Log.e(TAG, "DisplayUtils.nomalWidth " +DisplayUtils.nomalWidth  + " params.width = "
//                + params.width+ " params.height = " + params.height);
        view.setLayoutParams(params);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(myViewHolder viewHolder, int position) {

        // viewHolder.mTitle.setText(homeBeanList.get(position).getTitle());
        //viewHolder.mImagICon.setImageResource(homeBeanList.get(position).getImg());
        // viewHolder.llItem.setBackgroundResource(homeBeanList.get(position).getImg());
        if (authorityList.get(position).getHasAuthority().equals("true")) {
//            Loger.e("---hasAuthority--pos=" + position);
            viewHolder.llItem.setBackgroundResource(homeBeanList.get(position).getImg());
        } else {
            viewHolder.llItem.setBackgroundResource(homeBeanList.get(position).getImgFalse());
//            Loger.e("---nonoAuthority--pos=" + position);
            viewHolder.llItem.setFocusable(false);
        }

        if (homeBeanList.get(position).isNeedShowNotice()) {
            viewHolder.tv_badge_num.setVisibility(View.VISIBLE);
            viewHolder.tv_badge_num.setText(String.valueOf(homeBeanList.get(position).getUnreadCnt()));
        } else {
            viewHolder.tv_badge_num.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return homeBeanList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitle;
        private ImageView mImagICon;
        private RelativeLayout rlItem;
        private LinearLayout llItem;
        private TextView tv_badge_num;

        public myViewHolder(View itemView) {
            super(itemView);
            mImagICon = (ImageView) itemView.findViewById(R.id.iv_icon);
            mTitle = (TextView) itemView.findViewById(R.id.tv_icon_name);
            llItem = (LinearLayout) itemView.findViewById(R.id.ll_item);
            tv_badge_num = (TextView) itemView.findViewById(R.id.tv_badge_num);
            llItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
        }
    }


}

package com.jci.vsd.adapter.enterprise;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.ScreenUtil;
import com.jci.vsd.view.widget.treelist.TreeMenuBaseAdapter;
import com.jci.vsd.view.widget.treelist.TreeMenuUtils;
import com.jci.vsd.view.widget.treelist.bean.Node;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liqing on 18/6/29.
 * 成员管理 （tree view）
 */

public class MembersManagerAdapter<T> extends TreeMenuBaseAdapter<T> {
    private int mCurrentPos;
    boolean isExpand;
    private List<T> mData;


    public MembersManagerAdapter(ListView listView, Context context, List<T> data, int defaultLevel) throws IllegalAccessException {
        super(listView, context, data, defaultLevel);
        this.mData = data;
    }

    public void setCurrentPosition(int position, boolean isExpand) {
        this.mCurrentPos = position;
        this.isExpand = isExpand;
    }


    private LinearLayout.LayoutParams getParams(int margin) {
        int itemHeight = ScreenUtil.dip2px(40);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
        int itemMargin = ScreenUtil.dip2px(margin);
        params.setMargins(0, 0, itemMargin, 0);
        return params;
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_members_manage, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //left
        holder.tvMemberName.setPadding(node.getNodeLevel() * 50, 0, 0, 0);
        TreeMenuUtils.setNodeIcon(node);
//        Loger.e("node.name" + node.getNodeName());
//        Loger.e("node.leader" + node.getLeader());
//        Loger.e("node.pid" + node.getParentID());
        if (node.getLeader() != null && node.getLeader()) {
            holder.tvCompanyRole.setVisibility(View.VISIBLE);

        } else {
            holder.tvCompanyRole.setVisibility(View.GONE);
        }


        if (mCurrentPos == position) {
            //当前item被选中
            holder.rlMembers.setLayoutParams(getParams(0)); //让item 变长，出现悬浮效果
//            holder.rlMembers.setBackgroundResource(R.drawable.es_listview_sel);
//            holder.tvMemberName.setTextColor(mContext.getResources().getColor(R.color.white));

        } else {
            holder.rlMembers.setLayoutParams(getParams(8));//item short to hide the 悬浮效果失效
//            holder.rlMembers.setBackgroundResource(R.color.purple_deep);
//            holder.tvMemberName.setTextColor(mContext.getResources().getColor(R.color.purple_default_text));

        }


        holder.tvMemberName.setText(node.getNodeName());


        if (node.getIcon() == -1) {
            // 不是部门，有role
            holder.icon.setVisibility(View.GONE);

        } else {
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(node.getIcon());
        }

        return convertView;
    }


    class ViewHolder {
        @BindView(R.id.tv_member_name)
        TextView tvMemberName;

        @BindView(R.id.rl_members)
        RelativeLayout rlMembers;
        @BindView(R.id.iv_icon)
        ImageView icon;
        @BindView(R.id.tv_company_role)
        TextView tvCompanyRole;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.jci.vsd.activity.contract;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.SlideRecycleview.CommonAdapter;
import com.jci.vsd.SlideRecycleview.SwipeMenuLayout;
import com.jci.vsd.SlideRecycleview.ViewHolder;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.bean.contract.ContractBean;
import com.jci.vsd.bean.enterprise.DepartmentBean;
import com.jci.vsd.view.ItemDecor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 18/7/4.
 * 合约管理 （列表）
 */

public class ContractManageRecycleActivity extends BaseActivity {

    @BindView(R.id.recycle_contract_manage)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private List<ContractBean> mDatas;
    private CommonAdapter<ContractBean> mAdapter;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rightFucTxt)
    TextView tvRight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_manage_recycle);
        mDatas = new ArrayList<>();
        context = ContractManageRecycleActivity.this;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        int left = (int) getResources().getDimension(R.dimen.dimen_10);
        int right = (int) getResources().getDimension(R.dimen.dimen_10);
        int bottom = (int) getResources().getDimension(R.dimen.dimen_30);
        recyclerView.addItemDecoration(new ItemDecor(left, right, bottom));
        tvRight.setVisibility(View.VISIBLE);
        initData();
        initRecycleView();
        initViewEvent();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mDatas.add(new ContractBean("CA商务合同" + i, "合同内容的执行从" + i, "13:3" + i + "PM"));
        }
    }


    private void initRecycleView() {

        mAdapter = new CommonAdapter<ContractBean>(context, mDatas, R.layout.item_contract_swipe) {
            @Override
            public void convert(final ViewHolder holder, ContractBean swipeBean) {
                //设置是否开启左滑出菜单，设置false 为右滑出菜单
                // ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(mIndex == 0 ? false : true);// 并依次打开左滑右滑
                ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(true);
                //    holder.setText(R.id.content, swipeBean.name + (mIndex == 0 ? "我左青龙" : "我右白虎"));
                TextView tvContractTitle = holder.getView(R.id.tv_contract_title);
                tvContractTitle.setText(swipeBean.getTitle());

                TextView tvContractContent = holder.getView(R.id.tv_contract_content);
                tvContractContent.setText(swipeBean.getContent());

                TextView tvContractTime = holder.getView(R.id.tv_contract_time);
                tvContractTime.setText(swipeBean.getTime());
                //验证长按
                holder.setOnLongClickListener(R.id.content, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(mContext, "longclig", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
                        return false;
                    }
                });

                holder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        if (pos >= 0 && pos < mDatas.size()) {
                            Toast.makeText(context, "删除:" + pos, Toast.LENGTH_SHORT).show();
                            mDatas.remove(pos);
                            mAdapter.notifyItemRemoved(pos);//推荐用这个
                            //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                            //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                            //mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
                (holder).setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onClick() called with: v = [" + v + "]");
                        // toAtivityWithParams();
                    }
                });
                //置顶：
                holder.setOnClickListener(R.id.btnTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();

                        if (pos > 0 && pos < mDatas.size()) {
                            ContractBean swipeBean = mDatas.get(pos);
                            mDatas.remove(swipeBean);
                            mAdapter.notifyItemInserted(0);
                            mDatas.add(0, swipeBean);
                            mAdapter.notifyItemRemoved(pos + 1);
                            if (layoutManager.findFirstVisibleItemPosition() == 0) {
                                recyclerView.scrollToPosition(0);
                            }
                            //notifyItemRangeChanged(0,holder.getAdapterPosition()+1);
                        }

                    }
                });


            }
        };

        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initViewEvent() {
        tvRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rightFucTxt:
                toActivity(ContractAddActivtiy.class);
                break;
            default:
                break;
        }
    }
}

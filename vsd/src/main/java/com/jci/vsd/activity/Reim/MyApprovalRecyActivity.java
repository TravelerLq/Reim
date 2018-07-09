package com.jci.vsd.activity.Reim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.adapter.reim.MyApprovalRecycleAdapter;
import com.jci.vsd.bean.reim.ApprovalBean;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 18/6/28.
 * 我的审批－－进入的 但审批列表
 */

public class MyApprovalRecyActivity extends BaseActivity {
    @BindView(R.id.rl_approval)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private MyApprovalRecycleAdapter adapter;
    private List<ApprovalBean> beanList;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_approval_recy);
        context = MyApprovalRecyActivity.this;
        beanList = new ArrayList<>();
        initTestData();
        initRecycleView();
    }

    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyApprovalRecycleAdapter(context, beanList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyApprovalRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                String itemApprovalId = String.valueOf(beanList.get(pos).getApprovalId());
                String itemFormId = String.valueOf(beanList.get(pos).getFormId());
                Loger.e("---approval click---");
                Intent intent = new Intent(context, MyApprovalProcessActivity.class);
                intent.putExtra("formId", itemFormId);
                intent.putExtra("approvalId", itemApprovalId);
                startActivity(intent);
//                toActivityWithData(context, ApprovalProcessDetailActvity.class, "approvalId", itemApprovalId);
            }
        });


    }


    // 初始化显示的数据
    public void initTestData() {
        for (int i = 0; i < 10; i++) {
            ApprovalBean bean = new ApprovalBean();
            bean.setDate("2018-01-07");
            bean.setMoney("200." + i);
//            bean.setFormId(i);
//            bean.setApprovalName("remark" + i);
            bean.setUserName("张三");
            bean.setProcess("审批中");
            bean.setCount(1);
            beanList.add(i, bean);
        }


    }

    @Override
    protected void initViewEvent() {

    }
}

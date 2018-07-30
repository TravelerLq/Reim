package com.jci.vsd.activity.Reim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.adapter.reim.MyApprovalRecycleAdapter;
import com.jci.vsd.bean.reim.ApprovalBean;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/6/28.
 * 我的审批－－进入待审批列表
 */

public class MyApprovalRecyActivity extends BaseActivity {
    @BindView(R.id.rl_approval)
    RecyclerView recyclerView;
    @BindView(R.id.textview_title)
    TextView tvTitle;
    @BindView(R.id.button_back)
    ImageButton backBtn;
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
        tvTitle.setText("待审批事项");
        // initTestData();
        getData();
        initRecycleView();
    }

    private void getData() {
        Observable<List<ApprovalBean>> observable = new ReimControl().getWaitApprovalData();
        CommonDialogObserver<List<ApprovalBean>> observer = new CommonDialogObserver<List<ApprovalBean>>(this) {
            @Override
            public void onNext(List<ApprovalBean> list) {
                super.onNext(list);
                SimpleToast.toastMessage("数据获取成功", Toast.LENGTH_SHORT);
                if (list != null) {
                    beanList.clear();
                    beanList.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MyApprovalRecyActivity.this);

    }

    private void initRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyApprovalRecycleAdapter(context, beanList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MyApprovalRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                String itemApprovalId = String.valueOf(beanList.get(pos).getId());
                // String itemFormId = String.valueOf(beanList.get(pos).getFormId());
                Loger.e("---approval click---");
                Intent intent = new Intent(context, MyApprovalProcessActivity.class);
                // intent.putExtra("formId", itemFormId);
                intent.putExtra("id", itemApprovalId);
                startActivity(intent);
//                toActivityWithData(context, ApprovalProcessDetailActvity.class, "approvalId", itemApprovalId);
            }
        });


    }


    // 初始化显示的数据
    public void initTestData() {
        for (int i = 0; i < 10; i++) {
            ApprovalBean bean = new ApprovalBean();
//            DateUtils.addDay()
//            bean.setDate("2018-01-07");
//            bean.setMoney("200." + i);
////            bean.setFormId(i);
////            bean.setApprovalName("remark" + i);
//            bean.setUserName("张三");
//            bean.setProcess("审批中");
//            bean.setCount(1);
            bean.setAppl("Jesscy");
            beanList.add(i, bean);
        }


    }

    @Override
    protected void initViewEvent() {
        backBtn.setOnClickListener(this);
    }
}

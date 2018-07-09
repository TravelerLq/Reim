package com.jci.vsd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.activity.FoundationActivity;
import com.jci.vsd.activity.enterprise.BudgetManageActivity;
import com.jci.vsd.activity.enterprise.DepartmentManageActivity;
import com.jci.vsd.activity.enterprise.MembsersManageActivity;
import com.jci.vsd.activity.enterprise.ProducerManageActivity;
import com.jci.vsd.bean.enterprise.EnterpriseBean;
import com.jci.vsd.fragment.main.BaseFragment;
import com.jci.vsd.observer.DialogObserverHolder;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * Created by liqing on 18/6/25.
 */

public class EnterpriseHomeFragment extends BaseFragment implements View.OnClickListener, DialogObserverHolder {

    private LinearLayout llMembersManage;
    private LinearLayout llDepartmentManage;
    private LinearLayout llBudgetManage, llProducerManage, llEnterpriseUpdate, llEnterpeiseAdd;

    private TextView tvCode;
    private TextView tvGet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enterprise_home, container, false);
        llMembersManage = (LinearLayout) view.findViewById(R.id.ll_members_manage);
        llDepartmentManage = (LinearLayout) view.findViewById(R.id.ll_department_manage);
        llBudgetManage = (LinearLayout) view.findViewById(R.id.ll_budget_manage);
        llProducerManage = (LinearLayout) view.findViewById(R.id.ll_producer_manage);
        llEnterpriseUpdate = (LinearLayout) view.findViewById(R.id.ll_enterprise_update);
        llEnterpeiseAdd = (LinearLayout) view.findViewById(R.id.ll_add_enterprise);
        tvGet = (TextView) view.findViewById(R.id.tv_get_code);
        tvCode = (TextView) view.findViewById(R.id.tv_get_code_content);

        llMembersManage.setOnClickListener(this);
        llDepartmentManage.setOnClickListener(this);
        llBudgetManage.setOnClickListener(this);
        llProducerManage.setOnClickListener(this);
        llEnterpriseUpdate.setOnClickListener(this);
        llEnterpeiseAdd.setOnClickListener(this);
        tvGet.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_members_manage:
                toActivity(EnterpriseHomeFragment.this, MembsersManageActivity.class);
                break;
            case R.id.ll_department_manage:
                toActivity(EnterpriseHomeFragment.this, DepartmentManageActivity.class);
                break;
            case R.id.ll_budget_manage:
                toActivity(EnterpriseHomeFragment.this, BudgetManageActivity.class);
                break;
            case R.id.ll_producer_manage:
                toActivity(EnterpriseHomeFragment.this, ProducerManageActivity.class);
                break;

            case R.id.ll_enterprise_update:
                EnterpriseBean bean = new EnterpriseBean();
                bean.setIntentType("edit");
                bean.setCompanyName("Apple");
                toAtivityWithParams(EnterpriseHomeFragment.this, FoundationActivity.class, bean);
                break;
            case R.id.ll_add_enterprise:
                EnterpriseBean beanNew = new EnterpriseBean();
                beanNew.setIntentType("new");
                toAtivityWithParams(EnterpriseHomeFragment.this, FoundationActivity.class, beanNew);
                break;
            case R.id.tv_get_code:
                getCode();
                break;
            default:
                break;
        }

    }

    private void getCode() {
        //
        tvCode.setText("1234");
    }

    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void addSubscription(Subscription subscription) {

    }

    @Override
    public void removeDisposable(Disposable disposable) {

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return null;
    }
}

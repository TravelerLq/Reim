package com.jci.vsd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jci.vsd.R;
import com.jci.vsd.activity.Reim.ReimAddActivity;
import com.jci.vsd.activity.Reim.ReimAddTypeActivity;
import com.jci.vsd.activity.Reim.ReimHomeActivity;
import com.jci.vsd.activity.Reim.ReimRecycActivity;
import com.jci.vsd.activity.contract.ContractManageRecycleActivity;
import com.jci.vsd.activity.contract.RemotelyBillingRecycleActivity;
import com.jci.vsd.fragment.main.BaseFragment;
import com.jci.vsd.observer.DialogObserverHolder;

import org.reactivestreams.Subscription;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 * Created by liqing on 18/6/25.
 * <p>
 * 主页fragment
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, DialogObserverHolder {

    private RelativeLayout rlReim;

    private RelativeLayout rlContract;
    private RelativeLayout rlInvoicing;
    private RelativeLayout rlBudget;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rlReim = (RelativeLayout) view.findViewById(R.id.rl_reim);
        rlContract = (RelativeLayout) view.findViewById(R.id.rl_contract);
        rlInvoicing = (RelativeLayout) view.findViewById(R.id.rl_invoicing);
        rlBudget = (RelativeLayout) view.findViewById(R.id.rl_budget);
        initEvent();
        return view;
    }

    private void initEvent() {
        rlReim.setOnClickListener(this);
        rlContract.setOnClickListener(this);
        rlInvoicing.setOnClickListener(this);
        rlBudget.setOnClickListener(this);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_reim:
                toActivity(HomeFragment.this, ReimHomeActivity.class);
                // toActivity(HomeFragment.this, ReimAddActivity.class);

                break;
            case R.id.rl_contract:
                toActivity(HomeFragment.this, ContractManageRecycleActivity.class);
                break;
            case R.id.rl_invoicing:
                //远程开票：
                toActivity(HomeFragment.this, RemotelyBillingRecycleActivity.class);
                break;
            case R.id.rl_budget:
                toActivity(HomeFragment.this,ReimAddTypeActivity.class);
                break;
            default:
                break;
        }

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

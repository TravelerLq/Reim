package com.jci.vsd.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.FoundationActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.activity.enterprise.BudgetChooseActivity;
import com.jci.vsd.activity.enterprise.BudgetManageActivity;
import com.jci.vsd.activity.enterprise.DepartmentManageActivity;
import com.jci.vsd.activity.enterprise.MembsersManageActivity;
import com.jci.vsd.activity.enterprise.ProducerManageActivity;
import com.jci.vsd.adapter.enterprise.CompanySpinnerAdapter;
import com.jci.vsd.bean.enterprise.EnterpriseBean;
import com.jci.vsd.bean.enterprise.EnterpriseRequestBean;
import com.jci.vsd.bean.enterprise.GetEnterInfoBean;
import com.jci.vsd.bean.enterprise.RequestCodeBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.data.EnterpriseData;
import com.jci.vsd.fragment.main.BaseFragment;
import com.jci.vsd.network.control.EnterpriseControl;
import com.jci.vsd.network.control.FillcodeApiControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.DialogObserverHolder;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;
import com.warmtel.expandtab.KeyValueBean;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by liqing on 18/6/25.
 * 公司管理
 */

public class EnterpriseHomeFragment extends BaseFragment implements View.OnClickListener, DialogObserverHolder {

    private LinearLayout llMembersManage;
    private LinearLayout llDepartmentManage;
    private LinearLayout llBudgetManage, llProducerManage, llEnterpriseUpdate, llEnterpeiseAdd;

    private TextView tvCode;
    private TextView tvGet;
    private Spinner spinnerCompanyName;

    private CompanySpinnerAdapter spinnerAdapter;
    private List<KeyValueBean> spinnerList;
    private Context mContext;
    private GetEnterInfoBean.CosBean SelectCosBean;

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
        spinnerCompanyName = (Spinner) view.findViewById(R.id.sp_company_name);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = EnterpriseHomeFragment.this.getActivity();
        spinnerList = new ArrayList<>();

//        spinnerList.add(0, new KeyValueBean("1,", "company1"));
//        spinnerList.add(1, new KeyValueBean("2,", "company2"));
//        spinnerList.add(2, new KeyValueBean("3,", "company3"));


        initSpinner(spinnerCompanyName, spinnerList, 0);
        //  getEnterpriseinfo();


    }

    private void getEnterpriseinfo() {
        EnterpriseRequestBean enterpriseRequestBean = EnterpriseData.getEnterpriseBean();
        Observable<GetEnterInfoBean> observable = new EnterpriseControl().getEnterpeiseInfo();
        CommonDialogObserver<GetEnterInfoBean> observer = new CommonDialogObserver<GetEnterInfoBean>(this) {

            @Override
            public void onNext(GetEnterInfoBean getEnterInfoBean) {
                super.onNext(getEnterInfoBean);
                String status = getEnterInfoBean.getStatus();
                if (status.equals("200")) {
                    SimpleToast.toastMessage("成功", Toast.LENGTH_LONG);
                    List<GetEnterInfoBean.CosBean> listCompany = getEnterInfoBean.getCos();
                    EnterpriseData.saveEnterpriseList(listCompany);
                    //保存公司信息
                    List<GetEnterInfoBean.CosBean> reservior = EnterpriseData.getEnterpriseList();
                    Loger.e("--reservoir--" + reservior.get(0).getName());
                    KeyValueBean keyValueBean;
                    spinnerList.clear();
                    for (int i = 0; i < listCompany.size(); i++) {
                        String key = String.valueOf(listCompany.get(i).getId());
                        String value = listCompany.get(i).getName();
                        keyValueBean = new KeyValueBean(key, value);
                        spinnerList.add(keyValueBean);
                    }


                }

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401"))
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_LONG);
                toActivity(EnterpriseHomeFragment.this, LoginActivity.class);
            }
        };

        RxHelper.bindOnUI(observable, observer);
    }

    private void initSpinner(Spinner spinner, List<KeyValueBean> listData, int select) {


//        //1.为下拉列表定义一个数组适配器，这个数组适配器就用到里前面定义的list。装的都是list所添加的内容
//        spinnerAdapter = new ArrayAdapter<String>(EnterpriseHomeFragment.this.getActivity(), android.R.layout.simple_spinner_item, listData);//样式为原安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装list内容。
//        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
//        spinnerAdapter =new CompanySpinnerAdapter(spinnerList,EnterpriseHomeFragment.this.getActivity())
        spinnerAdapter = new CompanySpinnerAdapter(listData, mContext);
        spinner.setAdapter(spinnerAdapter);
        //4.为下拉列表设置各种点击事件，以响应菜单中的文本item被选中了，用setOnItemSelectedListener
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            private int spinnerSelectPos;

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int pos, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
                Loger.e("---spiner-select-pos=" + pos);
                spinnerSelectPos = pos;
                SelectCosBean = EnterpriseData.getEnterpriseList().get(pos);

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //myTextView.setText("Nothing");
            }
        });
        spinner.setSelection(select);
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
                // toActivity(EnterpriseHomeFragment.this, BudgetManageActivity.class);
                toActivity(EnterpriseHomeFragment.this, BudgetChooseActivity.class);
                break;
            case R.id.ll_producer_manage:
                toActivity(EnterpriseHomeFragment.this, ProducerManageActivity.class);
                break;

            case R.id.ll_enterprise_update:
                //更新公司信息
                EnterpriseBean bean = new EnterpriseBean();
                bean.setIntentType(AppConstant.UPDATE);
                toAtivityWithParams(EnterpriseHomeFragment.this, FoundationActivity.class, bean);
                break;
            case R.id.ll_add_enterprise:
                //新建公司信息
                EnterpriseBean beanNew = new EnterpriseBean();
                beanNew.setIntentType(AppConstant.NEW);
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
        RequestCodeBean requestBean = new RequestCodeBean();
        Observable<String> observable = new FillcodeApiControl().getCode(requestBean);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (!TextUtils.isEmpty(s)) {
                    tvCode.setText(s);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(t.getMessage(), Toast.LENGTH_SHORT);
            }
        };
//        RxHelper.bindOnUIFragmentLifeCycle(observable, observer, EnterpriseHomeFragment.this);
        RxHelper.bindOnUI(observable, observer);
        // tvCode.setText("1234");

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
        return getActivity().getSupportFragmentManager();
    }
}

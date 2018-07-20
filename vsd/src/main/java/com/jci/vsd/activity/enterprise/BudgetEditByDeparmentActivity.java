package com.jci.vsd.activity.enterprise;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.BudgetManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.SinglePicker;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/7/3.
 * 预算管理（按照部门划分）的编辑
 * 在buggetBean新增一个字段，
 */

public class BudgetEditByDeparmentActivity extends BaseActivity {
    @BindView(R.id.edt_name)
    TextView edtName;
    @BindView(R.id.edt_budget_input)
    TextView edtBudgetInput;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    List<BudgetBean> budgetBeanList = new ArrayList<>();
    private List<String> singlePickList = new ArrayList<>();
    private int selectDepartId;
    private BudgetBean intentBean;
    private int id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_edit_department);
        intentBean = (BudgetBean) getIntent().getSerializableExtra(AppConstant.SERIAL_KEY);
        if (intentBean != null) {
            id = intentBean.getId();
            Loger.e("intentBean!=null--id＝" + id);
            edtName.setText(intentBean.getName());
            edtBudgetInput.setText(intentBean.getQuota() + "");
        }
        initTestData();
        initViewEvent();
        getBudgetDeparment();
    }

    private void initTestData() {
        singlePickList.add("Department-1");
        singlePickList.add("Department-2");

    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_sure:
                checkData();

                break;
            case R.id.edt_name:

                onOptionPicker(edtName, singlePickList);

                break;

            default:
                break;
        }
    }


    private void getBudgetDeparment() {
        Observable<List<BudgetBean>> observable = new BudgetManageControl().getBudgetCategory();
        CommonDialogObserver observer = new CommonDialogObserver<List<BudgetBean>>(this) {
            @Override
            public void onNext(List<BudgetBean> budgetBeans) {
                super.onNext(budgetBeans);
                budgetBeanList.clear();
                budgetBeanList.addAll(budgetBeans);
                singlePickList.clear();
                for (int i = 0; i < budgetBeanList.size(); i++) {
                    singlePickList.add(budgetBeanList.get(i).getName());
                }


            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_SHORT);
                    exit();
                    toActivity(LoginActivity.class);
                }
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, BudgetEditByDeparmentActivity.this);
    }


    //单选
    public void onOptionPicker(View view, List<String> list) {
        SinglePicker<String> picker = new SinglePicker<>(this, list);
        picker.setCanLoop(false);//不禁用循环
        picker.setLineVisible(true);
        picker.setTextSize(18);
        picker.setSelectedIndex(8);
        picker.setWheelModeEnable(false);
        //启用权重 setWeightWidth 才起作用
        picker.setLabel("分");
        picker.setWeightEnable(true);
        picker.setWeightWidth(1);
        picker.setSelectedTextColor(Color.GREEN);//前四位值是透明度
        picker.setUnSelectedTextColor(Color.RED);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int index, String item) {
                Loger.e("index=" + index + ", item=" + item);
            }
        });
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int index, String item) {
                Loger.e("index=" + index + ", item=" + item);
                // 获取部门ID
                edtName.setText(item);
                selectDepartId = budgetBeanList.get(index).getDpt();
            }
        });

        picker.show();
    }

    private void checkData() {
        String name = edtName.getText().toString();
        String budget = edtBudgetInput.getText().toString();
        if (TextUtils.isEmpty(name)) {
            SimpleToast.toastMessage("部门名称不可位空", Toast.LENGTH_LONG);
            edtName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(budget)) {
            SimpleToast.toastMessage("预算不可为空", Toast.LENGTH_LONG);
            edtBudgetInput.requestFocus();
            return;
        }
        //test
        selectDepartId = 120;
        BudgetBean budgetBean = new BudgetBean();
        budgetBean.setId(id);
        budgetBean.setDpt(selectDepartId);
        budgetBean.setQuota(Double.valueOf(budget));
        updateBudgetItemByDepartment(budgetBean);
//        toActivity(BudgetManageActivity.class);
//        this.finish();
    }

    private void updateBudgetItemByDepartment(BudgetBean budgetBean) {

        Observable<Boolean> observable = new BudgetManageControl().updateBudget(budgetBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("更改成功", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_LONG);
                    exit();
                    toActivity(LoginActivity.class);
                }
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, BudgetEditByDeparmentActivity.this);
    }


}



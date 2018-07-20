package com.jci.vsd.activity.enterprise;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/7/3.
 * 预算管理的编辑
 * 在buggetBean新增一个字段？
 * 用来判断是更新科目？还是部门？
 */

public class EditBudgetItemActivity extends BaseActivity {
    @BindView(R.id.edt_name)
    TextView edtName;
    @BindView(R.id.edt_budget_input)
    TextView edtBudgetInput;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_budget_category)
    TextView tvBudgetCategory;
    @BindView(R.id.ll_budget_department)
    LinearLayout llBudgetDepart;

    @BindView(R.id.ll_budget_category)
    LinearLayout llBudgetCategory;

    private int id;
    private String intentType;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_edit);
        BudgetBean bean = (BudgetBean) getIntent().getSerializableExtra(AppConstant.SERIAL_KEY);
        //判断是科目？还是部门哪个？
        Loger.e("editBudget Id=" + bean.getId());

        Loger.e("editBudget getItem=" + bean.getItem());
        Loger.e("editBudget getCat=" + bean.getCat());
        Loger.e("editBudget type=" + bean.getType());

        intentType = bean.getType();
        if (intentType.equals(AppConstant.VALUE_BUDGET_DEPART)) {
            //department
            llBudgetCategory.setVisibility(View.GONE);
            llBudgetDepart.setVisibility(View.VISIBLE);

        } else {
            llBudgetCategory.setVisibility(View.VISIBLE);
            llBudgetDepart.setVisibility(View.GONE);
        }
        if (bean != null) {
            edtName.setText(bean.getName());
            edtBudgetInput.setText(bean.getQuota() + "");
        }


//        if (bean != null) {
//            edtName.setText(bean.getBudget());
//            edtBudgetInput.setText(bean.getBudget());
//        }

        initViewEvent();
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
            default:
                break;
        }
    }

    private void checkData() {
//        String name = edtName.getText().toString();
        String budget = edtBudgetInput.getText().toString();


        if (TextUtils.isEmpty(budget)) {
            SimpleToast.toastMessage("预算金额不可为空", Toast.LENGTH_LONG);
            edtBudgetInput.requestFocus();
            return;
        }
        updateBudget(id, Double.valueOf(budget));

//        toActivity(BudgetManageActivity.class);
//        this.finish();
    }

    private void updateBudget(int id, double budget) {

        Observable<Boolean> observable = new BudgetManageControl().updateBudget(id, budget);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("更新预算成功", Toast.LENGTH_SHORT);
                    if (intentType.equals(AppConstant.VALUE_BUDGET_DEPART)) {
                        toActivity(BudgetByDepartManageActivity.class);
                    } else {
                        toActivity(BudgetByCategoryManageActivity.class);
                    }

                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401"))

                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_LONG);
                exit();
                toActivity(LoginActivity.class);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, EditBudgetItemActivity.this);
    }


}



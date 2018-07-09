package com.jci.vsd.activity.enterprise;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.view.widget.SimpleToast;

import butterknife.BindView;

/**
 * Created by liqing on 18/7/3.
 * 预算管理的编辑
 */

public class EditBudgetItemActivity extends BaseActivity {
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_budget_input)
    EditText edtBudgetInput;
    @BindView(R.id.tv_sure)
    TextView tvSure;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_edit);
        BudgetBean bean = (BudgetBean) getIntent().getSerializableExtra(AppConstant.SERIAL_KEY);
        if (bean != null) {
            edtName.setText(bean.getBudget());
            edtBudgetInput.setText(bean.getBudget());
        }

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
        toActivity(BudgetManageActivity.class);
        this.finish();
    }


}



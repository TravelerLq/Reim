package com.jci.vsd.activity.enterprise;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;

import butterknife.BindView;

/**
 * Created by liqing on 18/7/16.
 * 预算管理 按科目 or 部门划分
 */

public class BudgetChooseActivity extends BaseActivity {
    @BindView(R.id.tv_depart_type)
    TextView tvChooseDepart;

    @BindView(R.id.tv_subject_type)
    TextView tvChooseSubject;

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_choose);
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        tvChooseDepart.setOnClickListener(this);
        tvChooseSubject.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.budget_choose));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_depart_type:

                toActivity(BudgetByDepartManageActivity.class);
                break;
            case R.id.tv_subject_type:
                //toActivity(BudgetManageActivity.class);
                toActivity(BudgetByCategoryManageActivity.class);
                break;
            default:
                break;
        }
    }
}

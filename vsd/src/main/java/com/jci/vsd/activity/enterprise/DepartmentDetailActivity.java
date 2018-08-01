package com.jci.vsd.activity.enterprise;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.bean.ReimCategoryBean;
import com.jci.vsd.bean.enterprise.DepartmentBean;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.DepartmentManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.TimePickerUtils;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.SinglePicker;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/7/2.
 * 部门详情
 */

public class DepartmentDetailActivity extends BaseActivity {
    private static final int REQUEST_PERSON = 1111;

    List<String> allStatus;
    @BindView(R.id.edt_depart_name)
    EditText edtDepartName;
    @BindView(R.id.edt_limit)
    EditText edtLimit;

    @BindView(R.id.tv_reim_authority)
    TextView tvReimAuthority;

    @BindView(R.id.tv_depart_leader)
    TextView tvDepartLeader;

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    private DepartmentBean bean;
    private List<ReimCategoryBean> listReimCategory;
    private Context mContext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);
        mContext = DepartmentDetailActivity.this;
        allStatus = new ArrayList<>();
        listReimCategory = new ArrayList<>();
        //  getAuthority();
        initTestData();
        initViewEvent();
        bean = (DepartmentBean) getIntent().getSerializableExtra(AppConstant.SERIAL_KEY);
        if (bean != null) {
            edtDepartName.setText(bean.getName());
            tvReimAuthority.setText(bean.getPermname());
            tvDepartLeader.setText(bean.getLname());
        }

    }

    private void initTestData() {
//        listReimCategory.add(new ReimCategoryBean(1, "管理"));
//        listReimCategory.add(new ReimCategoryBean(2, "管理2"));
//        listReimCategory.add(new ReimCategoryBean(3, "管理3"));
        for (int i = 0; i < listReimCategory.size(); i++) {
            allStatus.add(i, listReimCategory.get(i).getPerm());
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);

    }




    @Override
    protected void initViewEvent() {
        tvDepartLeader.setOnClickListener(this);
        tvReimAuthority.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.department_detail));
    }


}

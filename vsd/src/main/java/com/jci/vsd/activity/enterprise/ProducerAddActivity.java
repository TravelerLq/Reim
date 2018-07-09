package com.jci.vsd.activity.enterprise;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.bean.enterprise.ProducerBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.TimePickerUtils;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 18/7/3.
 * 审批流程新增
 */

public class ProducerAddActivity extends BaseActivity {

    private static final int REQUEST_PERSON = 1100;
    @BindView(R.id.edt_producer_name)
    EditText edtProducerName;

    @BindView(R.id.tv_producer_department)
    TextView tvProducerDepart;

    @BindView(R.id.tv_producer_order)
    TextView tvProducerOrder;


    @BindView(R.id.tv_producer_person)
    TextView tvProducerPerson;
    @BindView(R.id.tv_sure)
    TextView tvSure;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_edit);
        initViewEvent();
        ProducerBean bean = (ProducerBean) getIntent().getSerializableExtra(AppConstant.SERIAL_KEY);
        if (bean != null) {
            /**
             * approveNumId : 24
             * approveNumName : 部门审批
             * approverId : 54
             * approverName : 王朕
             * approverOrder : 1
             */
            edtProducerName.setText(bean.getApproveNumName());
            tvProducerDepart.setText(bean.getDname());
            //tvProducerOrder.setText("wwe");
            tvProducerOrder.setText(bean.getApproverOrder() + "");
            tvProducerPerson.setText(bean.getApproverName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_PERSON) {
            Bundle bundle = data.getExtras();
            MembersBean userBean = (MembersBean) bundle.getSerializable("user");
            tvProducerPerson.setText(userBean.getName());

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_sure:
                checkData();
                break;
            case R.id.tv_producer_department:
                //deparment
                break;
            case R.id.tv_producer_order:
                Loger.e("click--tv_producer_level");
                selectLevel();
                break;
            case R.id.tv_producer_person:
                Loger.e("click--tv_producer_person");
                selectPerson();
                //deparment
                break;
            default:
                break;
        }
    }

    private void selectPerson() {

        startActivityForResult(new Intent(this, MembsersSelectActivity.class), REQUEST_PERSON);
    }

    private void selectLevel() {

        Resources res = getResources();
        String[] status = res.getStringArray(R.array.approval_no);
        List<String> allStatus = Arrays.asList(status);
        TimePickerUtils.getInstance().onListDataPicker(this, allStatus, tvProducerOrder);

    }

    private void checkData() {
        String name = edtProducerName.getText().toString();
        String level = tvProducerOrder.getText().toString();

        String depart = tvProducerDepart.getText().toString();
        String person = tvProducerPerson.getText().toString();

        if (TextUtils.isEmpty(name)) {
            SimpleToast.toastMessage("审批名称不可为空", Toast.LENGTH_LONG);
            edtProducerName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(level)) {
            SimpleToast.toastMessage("审批流程不可为空", Toast.LENGTH_LONG);

            return;
        }
        if (TextUtils.isEmpty(depart)) {
            SimpleToast.toastMessage("审批部门不可为空", Toast.LENGTH_LONG);

            return;
        }
        if (TextUtils.isEmpty(person)) {
            SimpleToast.toastMessage("审批人不可为空", Toast.LENGTH_LONG);

            return;
        }
        toActivity(ProducerManageActivity.class);
        this.finish();
    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
        tvProducerOrder.setOnClickListener(this);
        tvProducerDepart.setOnClickListener(this);
        tvProducerPerson.setOnClickListener(this);
    }


}

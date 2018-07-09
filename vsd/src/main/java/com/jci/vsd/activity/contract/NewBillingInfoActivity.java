package com.jci.vsd.activity.contract;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.view.widget.SimpleToast;

import butterknife.BindInt;
import butterknife.BindView;

/**
 * Created by liqing on 18/7/5.
 * 新增开票信息
 */

public class NewBillingInfoActivity extends BaseActivity {

    @BindView(R.id.edt_company_name)
    EditText edtCompName;

    @BindView(R.id.edt_comany_tax_no)
    EditText edtTaxNo;


    @BindView(R.id.edt_comany_bank_no)
    EditText edtBankNo;

    @BindView(R.id.edt_company_address)
    EditText edtCompanyAddress;

    @BindView(R.id.edt_company_tel)
    EditText edtCompanyTel;
    //开票名称
    @BindView(R.id.edt_billing_name)
    EditText edtBillingName;

    //开票金额
    @BindView(R.id.edt_billing_moeny)
    EditText edtBillingMoney;

    //商品名称
    @BindView(R.id.edt_product_name)
    EditText edtProductName;

    @BindView(R.id.tv_sure)
    TextView tvSure;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_billing_info);
        edtCompName.setText("000");
        initTestData();
        initViewEvent();
    }

    private void initTestData() {
        edtCompName.setText("XXX物联网科技公司");
        edtTaxNo.setText("24958350x89");
        edtBankNo.setText("6220345723900");
        edtCompanyTel.setText("234109984");
        edtCompanyAddress.setText("南京秦淮区");
        edtBillingName.setText("合同开票");
        edtBillingMoney.setText("239005");
        edtProductName.setText("税控盘");
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
        String notEmpty = "不可以为空！";
        String companyName = edtCompName.getText().toString().trim();
        String taxNo = edtTaxNo.getText().toString().trim();

        String bankNo = edtBankNo.getText().toString().trim();
        String companyAddress = edtCompanyAddress.getText().toString().trim();
        String companyTel = edtCompanyTel.getText().toString().trim();
        String billingName = edtBillingName.getText().toString().trim();
        String billingMoney = edtBillingMoney.getText().toString().trim();
        String productName = edtProductName.getText().toString().trim();

        if (TextUtils.isEmpty(companyName)) {
            SimpleToast.toastMessage("公司名称" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }


        if (TextUtils.isEmpty(taxNo)) {
            SimpleToast.toastMessage("公司税号" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(bankNo)) {
            SimpleToast.toastMessage("公司银行帐号" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }


        if (TextUtils.isEmpty(companyAddress)) {
            SimpleToast.toastMessage("公司地址" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }


        if (TextUtils.isEmpty(companyTel)) {
            SimpleToast.toastMessage("公司电话" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }


        if (TextUtils.isEmpty(billingName)) {
            SimpleToast.toastMessage("开票名称" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(billingMoney)) {
            SimpleToast.toastMessage("开票金额" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(productName)) {
            SimpleToast.toastMessage("商品名称" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }
        //提交
        SimpleToast.toastMessage("开票成功", Toast.LENGTH_SHORT);
        finish();
        toActivity(RemotelyBillingRecycleActivity.class);

    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
    }
}

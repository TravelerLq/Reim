package com.jci.vsd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.bean.enterprise.EnterpriseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 18/6/26.
 * 创建公司 (EnterpriseUpdateActivity)
 */

public class FoundationActivity extends BaseActivity {
    @BindView(R.id.edt_company_name)
    EditText edtCompanyName;
    @BindView(R.id.edt_tax_no)
    EditText edtTaxtNo;

    @BindView(R.id.sp_comany_type)
    Spinner spCompanyType;
    @BindView(R.id.sp_vat_way)
    Spinner spVatWay;

    @BindView(R.id.sp_income_tax_way)
    Spinner spIncomeTaxWay;

    @BindView(R.id.sp_invoicing_way)
    Spinner spInvoicingWay;

    @BindView(R.id.edt_open_bank)
    EditText edtOpenBank;

    @BindView(R.id.edt_bank_account)
    EditText edtBankAccount;

    @BindView(R.id.edt_company_address)
    EditText edtCompanyAddress;

    @BindView(R.id.edt_company_tel)
    EditText edtCompanyTel;

    @BindView(R.id.edt_founder_id)
    EditText edtFounderId;

    @BindView(R.id.edt_reim)
    EditText edtReim;

    @BindView(R.id.ll_sure)
    LinearLayout llSure;

    @BindView(R.id.ll_frame)
    LinearLayout llFrame;

    //公司性质
    private List<String> companyTypeList = new ArrayList<>();
    private ArrayAdapter<String> adapter_company_type;

    //增值税征收方式
    private List<String> vatWayList = new ArrayList<>();
    private ArrayAdapter<String> adapter_vat_way;

    //所得税征收方式
    private List<String> incomeTaxList = new ArrayList<>();
    private ArrayAdapter<String> adapter_income_tax;

    //开票方式
    private List<String> invoicingList = new ArrayList<>();
    private ArrayAdapter<String> adapter_invoicing;
    private int companySelect = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foundation);
        initViewEvent();

        initSpinnerData();
        //公司性质
        initSpCompanyType();
        //增值税征收方式
        initSpinner(adapter_vat_way, spVatWay, vatWayList, 0);
        //所得税征收方式
        initSpinner(adapter_income_tax, spIncomeTaxWay, incomeTaxList, 0);
        //开票方式
        initSpinner(adapter_invoicing, spInvoicingWay, invoicingList, 0);
        EnterpriseBean bean = (EnterpriseBean) getIntent().getSerializableExtra(AppConstant.SERIAL_KEY);

        if (bean != null) {
            //更新信息的 可以编辑
            llFrame.setVisibility(View.GONE);
            if (bean.getIntentType().equals("new")) {
                //
                Loger.e("editabel--new");
            } else {
                //
                Loger.e("editabel--new");
                initTestData();
            }
        } else {
//信息查看 不可编辑
//            edtCompanyName.setClickable(false);
//            edtCompanyName.setFocusable(false);
//            edtCompanyName.setFocusableInTouchMode(false);
            Loger.e("not --editabel-");
            llFrame.setVisibility(View.VISIBLE);
            llFrame.setFocusable(true);
            llFrame.setFocusableInTouchMode(true);


        }

    }

    private void initSpinnerData() {
        //公司性质
        companyTypeList.add("--请选择--");
        companyTypeList.add("有限责任公司");
        companyTypeList.add("个人商户");

        //增值税征收方式
        vatWayList.add("--请选择--");
        vatWayList.add("小规模纳税人");
        vatWayList.add("一般纳税人");

        //所得税征收方式
        incomeTaxList.add("--请选择--");
        incomeTaxList.add("查账征收");
        incomeTaxList.add("核定征收");

        //开票方式
        invoicingList.add("--请选择--");
        invoicingList.add("增值税普通发票");
        invoicingList.add("增值税专用发票");
        invoicingList.add("增值税电子发票");


    }

    private void initSpCompanyType() {

        //1.为下拉列表定义一个数组适配器，这个数组适配器就用到里前面定义的list。装的都是list所添加的内容
        adapter_company_type = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, companyTypeList);//样式为原安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装list内容。
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        adapter_company_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
        spCompanyType.setAdapter(adapter_company_type);
        //4.为下拉列表设置各种点击事件，以响应菜单中的文本item被选中了，用setOnItemSelectedListener
        spCompanyType.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
                //myTextView.setText("您选择的是：" + xflxadapter.getItem(arg2));//文本说明
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //myTextView.setText("Nothing");
            }
        });
        spCompanyType.setSelection(companySelect);

    }

    private void initSpinner(ArrayAdapter<String> spinnerAdapter, Spinner spinner, List<String> listData, int select) {


        //1.为下拉列表定义一个数组适配器，这个数组适配器就用到里前面定义的list。装的都是list所添加的内容
        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listData);//样式为原安卓里面有的android.R.layout.simple_spinner_item，让这个数组适配器装list内容。
        //2.为适配器设置下拉菜单样式。adapter.setDropDownViewResource
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //3.以上声明完毕后，建立适配器,有关于sipnner这个控件的建立。用到myspinner
        spinner.setAdapter(spinnerAdapter);
        //4.为下拉列表设置各种点击事件，以响应菜单中的文本item被选中了，用setOnItemSelectedListener
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {//选择item的选择点击监听事件
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                // 将所选mySpinner 的值带入myTextView 中
                //myTextView.setText("您选择的是：" + xflxadapter.getItem(arg2));//文本说明
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
                //myTextView.setText("Nothing");
            }
        });
        spinner.setSelection(select);
    }


    @Override
    protected void initViewEvent() {
        llSure.setOnClickListener(this);
    }

    private void initTestData() {
        edtCompanyName.setText("御安神科技");
        edtTaxtNo.setText("234958710cidle");
        edtOpenBank.setText("中国建设银行");
        edtBankAccount.setText("32039483956");
        edtCompanyTel.setText("051203945");
        edtFounderId.setText("32047859098723");
        edtReim.setText("334");
        edtCompanyAddress.setText("江苏南京玄武区");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.ll_sure) {
            checkData();
        }
    }

    private void checkData() {

        if (edtCompanyName.getText().toString().trim().equals("")) {
            SimpleToast.toastMessage("请填写公司名称", Toast.LENGTH_LONG);
            return;
        } else if (edtTaxtNo.getText().toString().trim().equals("")) {
            SimpleToast.toastMessage("请填写税号", Toast.LENGTH_LONG);
            return;
        } else if (spCompanyType.getSelectedItem().toString().trim().equals("--请选择--")) {
            SimpleToast.toastMessage("请选择公司性质", Toast.LENGTH_LONG);
            //   ToastUtil.showToast(EnterpriseUpdateActivity.this, "请选择公司性质", Toast.LENGTH_LONG);
            return;
        } else if (spVatWay.getSelectedItem().toString().trim().equals("--请选择--")) {
            // ToastUtil.showToast(EnterpriseUpdateActivity.this, "请选择增值税征收方式", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请选择增值税征收方式", Toast.LENGTH_LONG);
            return;
        } else if (spIncomeTaxWay.getSelectedItem().toString().trim().equals("--请选择--")) {
            //  ToastUtil.showToast(EnterpriseUpdateActivity.this, "请选择所得税增收方式", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请选择所得税增收方式", Toast.LENGTH_LONG);
            return;
        } else if (spInvoicingWay.getSelectedItem().toString().trim().equals("--请选择--")) {
            // ToastUtil.showToast(EnterpriseUpdateActivity.this, "请选择开票方式", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请选择开票方式", Toast.LENGTH_LONG);
            return;
        } else if (edtOpenBank.getText().toString().trim().equals("")) {
            //  ToastUtil.showToast(EnterpriseUpdateActivity.this, "请填写开户银行名称", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请填写开户银行名称", Toast.LENGTH_LONG);
            return;
        } else if (edtBankAccount.getText().toString().trim().equals("")) {
            //ToastUtil.showToast(EnterpriseUpdateActivity.this, "请填写开户银行账号", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请填写开户银行账号", Toast.LENGTH_LONG);
            return;
        } else if (edtCompanyAddress.getText().toString().trim().equals("")) {

            SimpleToast.toastMessage("请填写公司地址", Toast.LENGTH_LONG);
            return;
        } else if (edtCompanyTel.getText().toString().trim().equals("")) {

            SimpleToast.toastMessage("请填写公司电话", Toast.LENGTH_LONG);
            return;
        } else if (edtFounderId.getText().toString().trim().equals("")) {

            SimpleToast.toastMessage("请填写证件号码", Toast.LENGTH_LONG);
            return;
        } else if (edtReim.getText().toString().trim().equals("")) {

            SimpleToast.toastMessage("请填写报销限额", Toast.LENGTH_LONG);
            return;
        }
//        } else if (spu.getUidNum() == -1) {
//           // ToastUtil.showToast(EnterpriseUpdateActivity.this, "userId不可以为空！", Toast.LENGTH_LONG);
//            SimpleToast.toastMessage( "请选择所得税增收方式", Toast.LENGTH_LONG);
//            return;
//        }

//        edie = new EnterpriseDetailInfoEntity();
//        edie.setCname(cname.getText().toString().trim());
//        edie.setTaxnum(taxnum.getText().toString().trim());
//        edie.setGsxz(gsxz_span.getSelectedItem().toString().trim());
//        edie.setZztax(zztax_span.getSelectedItem().toString().trim());
//        edie.setSdtax(sdtax_span.getSelectedItem().toString().trim());
//        edie.setKpfs(kpfs_span.getSelectedItem().toString().trim());
//        edie.setKhbankname(khbankname.getText().toString().trim());
//        edie.setKhbankid(khbankid.getText().toString().trim());
//        edie.setCaddr(caddr.getText().toString().trim());
//        edie.setCtel(ctel.getText().toString().trim());
//        edie.setCcid(ccid.getText().toString().trim());
//        edie.setClimit(climit.getText().toString().trim());
//
//        System.out.println("aaaaaaaa:" + new SharedPreferencesUtil(EnterpriseUpdateActivity.this).getCid());

        toActivity(RegisterActivity.class);
    }


}

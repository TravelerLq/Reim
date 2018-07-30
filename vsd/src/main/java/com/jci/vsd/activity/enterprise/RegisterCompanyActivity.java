package com.jci.vsd.activity.enterprise;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.activity.MainActivity;
import com.jci.vsd.bean.enterprise.EnterpriseBean;
import com.jci.vsd.bean.enterprise.EnterpriseRequestBean;
import com.jci.vsd.bean.enterprise.EnterpriseResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.data.EnterpriseData;
import com.jci.vsd.network.control.EnterpriseControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;
import com.squareup.haha.perflib.Main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/6/26.
 * 注册公司 (EnterpriseUpdateActivity)
 */

public class RegisterCompanyActivity extends BaseActivity {
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

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
    @BindView(R.id.edt_founder)
    EditText edtFounder;
    @BindView(R.id.edt_founder_id)
    EditText edtFounderId;

    @BindView(R.id.edt_reim)
    EditText edtReim;

    @BindView(R.id.ll_sure)
    LinearLayout llSure;


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
    private int companySelect = 1;
    private EnterpriseBean enterpriseBean;
    private int companyId;
    private MySpEdit prefs;
    private String userId;
    private EnterpriseRequestBean requestBean;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_company);
        prefs = MySpEdit.getInstance();


        initViewEvent();

        initSpinnerData();
        //公司性质
        initSpCompanyType();
        //增值税征收方式
        initSpinner(adapter_vat_way, spVatWay, vatWayList, 1);
        //所得税征收方式
        initSpinner(adapter_income_tax, spIncomeTaxWay, incomeTaxList, 1);
        //开票方式
        initSpinner(adapter_invoicing, spInvoicingWay, invoicingList, 1);
        EnterpriseBean bean = (EnterpriseBean) getIntent().getSerializableExtra(AppConstant.SERIAL_KEY);
        initTestData();
    }


    private void arrayToList(int resId, List<String> list) {
        Resources res = getResources();
        //  String[] status = res.getStringArray(R.array.approval_no);
        String[] status = res.getStringArray(resId);
        list = Arrays.asList(status);
    }


    private void initSpinnerData() {
        //公司性质
//        companyTypeList.add("--请选择--");
//        companyTypeList.add("有限责任公司");
//        companyTypeList.add("个人商户");
        arrayToList(R.array.company_type, companyTypeList);

        Resources res = getResources();
        //  String[] status = res.getStringArray(R.array.approval_no);
        String[] status = getResources().getStringArray(R.array.company_type);
        companyTypeList.clear();
        companyTypeList.addAll(Arrays.asList(status));
        Loger.e("--company().size--" + companyTypeList.size());
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

        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.register_company));
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
        edtFounder.setText("张三");
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.ll_sure) {
            checkData();
        }
    }

    private void checkData() {
        String companyName = edtCompanyName.getText().toString().trim();
        String companyType = spCompanyType.getSelectedItem().toString().trim();
        String vatWay = spVatWay.getSelectedItem().toString().trim();
        String incomeTaxWay = spIncomeTaxWay.getSelectedItem().toString().trim();
        String taxNo = edtTaxtNo.getText().toString().trim();
        String openBank = edtOpenBank.getText().toString().trim();
        String bankAccount = edtBankAccount.getText().toString().trim();
        String address = edtCompanyAddress.getText().toString().trim();
        String tel = edtCompanyTel.getText().toString().trim();
        String invoicingWay = spInvoicingWay.getSelectedItem().toString().trim();
        String legal = edtFounder.getText().toString().trim();
        String legalId = edtFounderId.getText().toString().trim();
        String reimLimit = edtReim.getText().toString().trim();


        if (TextUtils.isEmpty(companyName)) {
            SimpleToast.toastMessage("请填写公司名称", Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(taxNo)) {
            SimpleToast.toastMessage("请填写税号", Toast.LENGTH_LONG);
            return;
        } else if (companyType.equals("--请选择--")) {
            SimpleToast.toastMessage("请选择公司性质", Toast.LENGTH_LONG);
            //   ToastUtil.showToast(EnterpriseUpdateActivity.this, "请选择公司性质", Toast.LENGTH_LONG);
            return;
        } else if (vatWay.equals("--请选择--")) {
            // ToastUtil.showToast(EnterpriseUpdateActivity.this, "请选择增值税征收方式", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请选择增值税征收方式", Toast.LENGTH_LONG);
            return;
        } else if (incomeTaxWay.equals("--请选择--")) {
            //  ToastUtil.showToast(EnterpriseUpdateActivity.this, "请选择所得税增收方式", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请选择所得税增收方式", Toast.LENGTH_LONG);
            return;
        } else if (invoicingWay.equals("--请选择--")) {
            // ToastUtil.showToast(EnterpriseUpdateActivity.this, "请选择开票方式", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请选择开票方式", Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(openBank)) {
            //  ToastUtil.showToast(EnterpriseUpdateActivity.this, "请填写开户银行名称", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请填写开户银行名称", Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(bankAccount)) {
            //ToastUtil.showToast(EnterpriseUpdateActivity.this, "请填写开户银行账号", Toast.LENGTH_LONG);
            SimpleToast.toastMessage("请填写开户银行账号", Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(address)) {

            SimpleToast.toastMessage("请填写公司地址", Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(tel)) {

            SimpleToast.toastMessage("请填写公司电话", Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(legal)) {
            SimpleToast.toastMessage("请填写法人姓名", Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(legalId)) {

            SimpleToast.toastMessage("请填写法人证件号码", Toast.LENGTH_LONG);
            return;
        } else if (TextUtils.isEmpty(reimLimit)) {

            SimpleToast.toastMessage("请填写报销限额", Toast.LENGTH_LONG);
            return;
        }
//        {"name":"南京御安神物联网科技有限公司",
//                "nature":"有限责任公司",
//                "vatColl":"一般纳税人",      －－－ 增值税增收方式
//                "incomeTaxColl":"查账征收",
//                "taxId":"9132010530261362XN",
//                "bank":"农行华荣支行",
//                "bankAcct":"10100101040010592",
//                "address":"南京玄武区玄武大道699-10号5幢",
//                "phone":"025-83377373",
//                "invoice":"自开",
//                "legal":"时辉",
//                "legalIdNumber":"321028197505222615",
//                "quota":1000000.00,
//                "creator":2
//        }
        requestBean = new
                EnterpriseRequestBean();
        requestBean.setName(companyName);
        requestBean.setNature(companyType);
        requestBean.setVatColl(vatWay);
        requestBean.setAddress(address);
        requestBean.setBank(openBank);
        requestBean.setBankAcct(bankAccount);
//        requestBean.setCreator(2);
        requestBean.setIncomeTaxColl(incomeTaxWay);
        requestBean.setInvoice(invoicingWay);
        requestBean.setLegal(legal);
        requestBean.setLegalIdNumber(legalId);
        requestBean.setPhone(tel);
        requestBean.setQuota(Double.valueOf(reimLimit));
        requestBean.setTaxId(taxNo);


//        } else if (spu.getUidNum() == -1) {
//           // ToastUtil.showToast(EnterpriseUpdateActivity.this, "userId不可以为空！", Toast.LENGTH_LONG);
//            SimpleToast.toastMessage( "请选择所得税增收方式", Toast.LENGTH_LONG);
//            return;
//        }

        toRegisterCompany(requestBean);

    }

    private void toRegisterCompany(final EnterpriseRequestBean enterpriseRequestBean) {


        Observable<EnterpriseResponseBean> observable = new EnterpriseControl().registerEnterprise(enterpriseRequestBean);
        CommonDialogObserver<EnterpriseResponseBean> observer = new CommonDialogObserver<EnterpriseResponseBean>(this) {
            @Override
            public void onNext(EnterpriseResponseBean responseBean) {
                super.onNext(responseBean);
                //保存 公司信息(wc 这里后台只返回了 id)
                //EnterpriseData.saveEnterprise(requestBean);
                String status = responseBean.getStatus();
                if (status.equals("200")) {
                    SimpleToast.toastMessage("公司创建成功", Toast.LENGTH_LONG);

                    companyId = responseBean.getCid();
                    enterpriseRequestBean.setId(companyId);
                    EnterpriseData.saveEnterprise(enterpriseRequestBean);

                }

                if (status.equals("20005")) {
                    SimpleToast.toastMessage("公司已经存在", Toast.LENGTH_LONG);

                }
                toActivity(MainActivity.class);
                finish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401")){
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_LONG);
                    exit();
                    toActivity(LoginActivity.class);

                }


            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, RegisterCompanyActivity.this);

    }


}

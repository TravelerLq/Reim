package com.jci.vsd.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jci.vsd.R;
import com.jci.vsd.activity.enterprise.RegisterCompanyActivity;
import com.jci.vsd.adapter.RecAdapter;
import com.jci.vsd.bean.UserBean;
import com.jci.vsd.bean.register.RegisterRequestBean;
import com.jci.vsd.bean.register.RegisterResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.data.UserData;
import com.jci.vsd.network.control.RegisterControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.CertServiceUrl;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Map;

import cn.com.syan.jcee.common.sdk.utils.CertificateConverter;
import cn.com.syan.online.sdk.OnlineApplyResponse;
import cn.com.syan.online.sdk.OnlineClient;
import cn.com.syan.online.sdk.OnlineIssueResponse;
import cn.com.syan.spark.client.sdk.SparkApplication;
import cn.unitid.spark.cm.sdk.business.*;


import butterknife.BindView;
import cn.unitid.spark.cm.sdk.exception.CmSdkException;
import cn.unitid.spark.cm.sdk.listener.ProcessListener;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/6/25.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.edt_acount)
    EditText edtAccount;
    @BindView(R.id.edt_real_name)
    EditText edtName;
    @BindView(R.id.edt_id)
    EditText edtID;
    @BindView(R.id.edt_tel)
    EditText edtTel;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.edt_psw)
    EditText edtPsw;
    @BindView(R.id.check_agree)
    CheckBox checkAgree;
    @BindView(R.id.edt_emil)
    EditText edtEmail;

    //back title
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    private boolean agree = false;
    private OnlineClient onlineClient;
    private String vcode;
    private String account;
    private String psw;
    private String realName;
    private String id;
    private String tel;
    private CBSCertificateStore store = null;
    private String email;
    private MySpEdit prefs;
    private String intentType;
    //创建公司
    private int type=2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        prefs = MySpEdit.getInstance();
        initViewEvent();
        intentType = getIntent().getStringExtra(AppConstant.KEY_TYPE);
        Loger.e("--registertype---" + intentType);
        if (intentType.equals("boss")) {
            type=2;
        }else if(intentType.equals("3")){
            //填写邀请码进来的员工；
            type=3;
        }

        initTestData1();
        onlineClient = new OnlineClient(CertServiceUrl.baseUrl, CertServiceUrl.appKey, CertServiceUrl.appSecret);
        //  sharedPreferencesUtil = new SharedPreferencesUtil(RegisterActivity.this);
        SparkApplication.init(getApplication());
        store = CBSCertificateStore.getInstance();
        if (store.getAllCertificateList().size() > 0) {
            //
            //  store.deleteCertificate(store.getAllCertificateList().get(0).getId());

            Log.e("certificate--size=", "" + store.getAllCertificateList().size());
            Log.e("certificate--id=", "" + store.getAllCertificateList().get(0).getId());

        }

        checkAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                agree = isChecked;

            }
        });
    }

    @Override
    protected void initViewEvent() {
        btnRegister.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.register));

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.btn_register) {
            //去注册
            if (agree) {
                // toActivity(MainActivity.class);
                Loger.e("--register--click");
                //initTestData1();
                checkData();
            } else {
                Toast.makeText(RegisterActivity.this,
                        " 请先同意协议条款", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void initTestData() {
        edtAccount.setText("lq");
        edtPsw.setText("123456");
        edtName.setText("liq");
        edtID.setText("320322199007171428");
        edtTel.setText("15951882547");
        edtEmail.setText("2319345023@qq.com");

    }

    private void initTestData1() {
        edtAccount.setText("屠正松");
        edtPsw.setText("123456");
        edtName.setText("屠正松");
        edtID.setText("320113199310156418");
        edtTel.setText("15951882547");
        edtEmail.setText("2319345023@qq.com");

    }


    private void initTestData2() {
        //  edtAccount.setText("屠正松");
        edtPsw.setText("123456");
        edtName.setText("张睿");
        edtID.setText("321027199409053017");
        edtTel.setText("15951882511");
        edtEmail.setText("2319345023@qq.com");

    }

    private void initTestData3() {
        //  edtAccount.setText("屠正松");
        edtPsw.setText("123456");
        edtName.setText("周先康");
        edtID.setText("342601199406205015");
        edtTel.setText("15951883214");
        edtEmail.setText("2319345023@qq.com");

    }

    private void checkData() {

        account = edtAccount.getText().toString().trim();
        psw = edtPsw.getText().toString().trim();
        realName = edtName.getText().toString().trim();
        id = edtID.getText().toString();
        tel = edtTel.getText().toString();
        email = edtEmail.getText().toString();

//        if (TextUtils.isEmpty(account)) {
//            Toast.makeText(RegisterActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
//            edtAccount.requestFocus();
//            return;
//        }

        if (TextUtils.isEmpty(psw)) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            edtPsw.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(realName)) {
            Toast.makeText(RegisterActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
            edtName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(RegisterActivity.this, "请输入身份证号", Toast.LENGTH_SHORT).show();
            edtID.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(tel)) {
            Toast.makeText(RegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
            edtTel.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(RegisterActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
            return;
        }
        vcode = "1234";
//                if ("".equals(vcode)) {
//                    ToastUtil.showToast(RegFirstStepActivity.this, "请输入验证码", Toast.LENGTH_SHORT);
//                    yzmet.requestFocus();
//                    return;
//                }


        //toActivity(MainActivity.class);
        //   goRegisterCertify();
        //注册证书 －－》同步证书


        goRegister();
    }

    private void goRegisterCertify() {
        CertificateRegisterService certificateRegisterService = new CertificateRegisterService(RegisterActivity.this,
                onlineClient, new ProcessListener<OnlineApplyResponse>() {
            @Override
            public void doFinish(OnlineApplyResponse onlineApplyResponse, String cert) {

                CertificateIssueService certificateIssueService = new CertificateIssueService(RegisterActivity.this, onlineClient, new ProcessListener<OnlineIssueResponse>() {
                    @Override
                    public void doFinish(OnlineIssueResponse data, String cert) {
                        Log.e("CertificateIssueService", "onFinish");

                        String your_signature = data.getSignCert();
                        String your_encryption = data.getEncCert();

                        X509Certificate x509Certificate = null;
                        try {
                            x509Certificate = CertificateConverter.fromBase64(your_signature);

                        } catch (CertificateException e) {
                            e.printStackTrace();
                        }

                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String validate = formatter.format(x509Certificate.getNotBefore()) + " - " +
                                formatter.format(x509Certificate.getNotAfter());
                        //注册
                        goRegister();


                    }

                    @Override
                    public void doException(CmSdkException exception) {
//                        if (pdu.getMypDialog().isShowing()) {
//                            pdu.dismisspd();
//                        }
                        Log.e("CertificateIssueService", "doException");
                        Toast.makeText(RegisterActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("firstRegister", "showException" + exception.getMessage());
                    }
                });
                String exts = onlineApplyResponse.getExtensions();
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                Map<String, String> retMap = gson.fromJson(exts, new TypeToken<Map<String, String>>() {
                }.getType());

                certificateIssueService.issue(onlineApplyResponse.getApplyId(), onlineApplyResponse.getSubject(), retMap, onlineApplyResponse.getAlgorithm(), true);


            }


            @Override
            public void doException(CmSdkException e) {
//
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT);
                Log.e("RegisterActivity", "CmSdkException" + e.getMessage());

            }
        });

        try {

            certificateRegisterService.register(realName, id, tel, "1234", null);
        } catch (Exception e) {
            Toast.makeText(RegisterActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }


    }

    private void goRegister() {
        final RegisterRequestBean requestBean = new RegisterRequestBean();
        requestBean.setRealname(realName);
        requestBean.setIdNumber(id);
        requestBean.setPhone(tel);
        requestBean.setEmail(email);
        requestBean.setPassword(psw);
//        requestBean.setType(type);

        Observable<RegisterResponseBean> observable = new RegisterControl()
                .register(requestBean);
        CommonDialogObserver<RegisterResponseBean> observer = new CommonDialogObserver<RegisterResponseBean>(this) {
            @Override
            public void onNext(RegisterResponseBean registerResponseBean) {
                super.onNext(registerResponseBean);
                String status = registerResponseBean.getStatus();
                if (status.equals("200")) {
                    Loger.e("--uid=" + registerResponseBean.getUid());

                    SimpleToast.toastMessage(" 注册成功", Toast.LENGTH_LONG);

//                    UserBean userBean = new UserBean();
//                    userBean.setPassword(psw);
//                    userBean.setPhone(tel);
//                    UserData.saveUser(userBean);
                    Loger.e("--responseBean.getRole()" +type);



//                    if (intentType.equals("boss")) {
//
//                        toActivityWithType(LoginActivity.class, intentType);
//                    } else {
//                        toActivity(LoginActivity.class);
//                    }

                }
                //(code == 20005 -- 201
                if (status.equals("205")) {
                    SimpleToast.toastMessage(" 帐号已存在,去登录", Toast.LENGTH_LONG);

                }
                //
                if(status.equals("206")){
                    SimpleToast.toastMessage(" 手机号已注册,去登录", Toast.LENGTH_LONG);
                }

                prefs.setUser(tel);
                prefs.setPsw(psw);
                toActivityWithType(LoginActivity.class, intentType);
                finish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, this);

    }


}

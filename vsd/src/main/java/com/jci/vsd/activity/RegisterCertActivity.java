package com.jci.vsd.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jci.vsd.R;
import com.jci.vsd.fragment.dialog.RxStyleDialogFragment;
import com.jci.vsd.utils.CertServiceUrl;
import com.jci.vsd.view.widget.SimpleToast;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Map;

import butterknife.BindView;
import cn.com.syan.jcee.common.sdk.utils.CertificateConverter;
import cn.com.syan.online.sdk.OnlineApplyResponse;
import cn.com.syan.online.sdk.OnlineClient;
import cn.com.syan.online.sdk.OnlineIssueResponse;
import cn.com.syan.spark.client.sdk.SparkApplication;
import cn.unitid.spark.cm.sdk.business.CertificateIssueService;
import cn.unitid.spark.cm.sdk.business.CertificateRegisterService;
import cn.unitid.spark.cm.sdk.exception.CmSdkException;
import cn.unitid.spark.cm.sdk.listener.ProcessListener;

/**
 * Created by liqing on 18/7/10.
 * 申请个人证书 activity
 */

public class RegisterCertActivity extends BaseActivity {
    @BindView(R.id.edt_name)
    EditText edtName;

    @BindView(R.id.edt_id_no)
    EditText edtIdNo;
    @BindView(R.id.edt_tel)
    EditText edtTel;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.edt_pin)
    EditText edtPin;
    @BindView(R.id.ll_cert)
    LinearLayout llCert;
    private OnlineClient onlineClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cert);

        llCert.getBackground().setAlpha(100);//0~255透明度值
        initTestData();
        initRegistCert();
        initViewEvent();

    }

    private void initTestData() {
        edtName.setText("李青");
        edtIdNo.setText("320322199007171428");
        edtTel.setText("15951882547");
        edtPin.setText("1234");
    }

    private void initRegistCert() {
        onlineClient = new OnlineClient(CertServiceUrl.baseUrl,
                CertServiceUrl.appKey, CertServiceUrl.appSecret);

        SparkApplication.init(getApplication());
    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_sure) {
            showProgress();
            applyCert();
        }
    }

    private void applyCert() {
        String nameStr = edtName.getText().toString().trim();
        String idNoStr = edtIdNo.getText().toString().trim();
        String telStr = edtTel.getText().toString().trim();
        String pin =edtPin.getText().toString().trim();
        if (TextUtils.isEmpty(nameStr)) {
            edtName.requestFocus();
            SimpleToast.toastMessage("姓名不可以为空！", Toast.LENGTH_LONG);
            return;
        }

        if (TextUtils.isEmpty(idNoStr)) {
            edtIdNo.requestFocus();
            SimpleToast.toastMessage("身份证号不可以为空！", Toast.LENGTH_LONG);
            return;
        }

        if (TextUtils.isEmpty(telStr)) {
            edtTel.requestFocus();
            SimpleToast.toastMessage("电话号码不可以为空！", Toast.LENGTH_LONG);
            return;
        }

        if (TextUtils.isEmpty(pin)) {
            edtPin.requestFocus();
            SimpleToast.toastMessage("PIN码不可以为空！", Toast.LENGTH_LONG);
            return;
        }

        goRegisterCertify(nameStr, idNoStr, telStr);
    }


    private void goRegisterCertify(String realname, String id, String tel) {
        onlineClient = new OnlineClient(CertServiceUrl.baseUrl, CertServiceUrl.appKey, CertServiceUrl.appSecret);
        CertificateRegisterService certificateRegisterService = new CertificateRegisterService(RegisterCertActivity.this,
                onlineClient, new ProcessListener<OnlineApplyResponse>() {
            @Override
            public void doFinish(OnlineApplyResponse onlineApplyResponse, String cert) {

                CertificateIssueService certificateIssueService = new CertificateIssueService(RegisterCertActivity.this, onlineClient, new ProcessListener<OnlineIssueResponse>() {
                    @Override
                    public void doFinish(OnlineIssueResponse data, String cert) {
//                        if (pdu.getMypDialog().isShowing()) {
//                            pdu.dismisspd();
//                        }
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

//                        goToAddUser();
                        //注册成功，去加入公司 or注册公司
                        rxStyleDialogFragment.dismiss();
                        toActivity(LoginActivity.class);

                    }

                    @Override
                    public void doException(CmSdkException exception) {
//                        if (pdu.getMypDialog().isShowing()) {
//                            pdu.dismisspd();
//                        }
                        Log.e("CertificateIssueService", "doException");
                        SimpleToast.toastMessage(exception.getMessage(), Toast.LENGTH_SHORT);
                        Toast.makeText(RegisterCertActivity.this, "" + exception.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(RegisterCertActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT);
                SimpleToast.toastMessage(e.getMessage(), Toast.LENGTH_SHORT);
                Log.e("RegisterActivity", "CmSdkException" + e.getMessage());

            }
        });

        try {

            certificateRegisterService.register(realname, id, tel, "1234", null);
        } catch (Exception e) {
            // Toast.makeText(RegisterCertActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT);
            SimpleToast.toastMessage(e.getMessage(), Toast.LENGTH_SHORT);
            e.printStackTrace();
        }


    }
}

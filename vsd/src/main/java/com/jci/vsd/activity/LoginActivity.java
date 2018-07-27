package com.jci.vsd.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jci.vsd.R;
import com.jci.vsd.activity.enterprise.RegisterCompanyActivity;

import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.CheckUpdateResponse;
import com.jci.vsd.bean.enterprise.EnterpriseBean;
import com.jci.vsd.bean.login.LoginResponseBean;
import com.jci.vsd.bean.register.RegisterRequestBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.fragment.EnterpriseHomeFragment;
import com.jci.vsd.fragment.dialog.RxStyleDialogFragment;
import com.jci.vsd.fragment.dialog.UpdateAppDialog;

import com.jci.vsd.network.control.DownloadAppControl;
import com.jci.vsd.network.control.LoginApiControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.DefaultObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.Utils;
import com.jci.vsd.view.widget.SimpleToast;
import com.squareup.haha.perflib.Main;

import java.util.ArrayList;

import butterknife.BindView;
import cn.com.syan.spark.client.sdk.SparkApplication;
import cn.unitid.spark.cm.sdk.business.CBSCertificateStore;
import cn.unitid.spark.cm.sdk.data.entity.Certificate;
import io.reactivex.Observable;

/**
 * 登录
 * Created by Victor on 10/31/2017.
 */

public class LoginActivity extends BaseActivity {


    private MySpEdit prefs;


    private Button btnSure;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.edt_acount)
    EditText edtAccount;
    @BindView(R.id.edt_psw)
    EditText edtPsw;


    private Dialog dialog;
    private View inflate;
    private TextView cancel;
    private TextView tvSelectCompany;
    private TextView tvRegisterCompany;
    private Context context;

    private ArrayList<Certificate> certificateArrayList;//证书列表;
    private CBSCertificateStore store;
    private String intentType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //  checkCBScert();
        context = LoginActivity.this;
        btnSure = (Button) findViewById(R.id.btn_sure);
        MySpEdit.getInstance().setAppEmv(true);
        prefs = MySpEdit.getInstance();
        tvRegister.setText(Html.fromHtml(getResources().getString(R.string.str_register, 1, "", "去注册")));
        initViewEvent();
        initTestData();
        //检查更新
        checkUpdateApp();
        intentType = getIntent().getStringExtra("type");
        Loger.e("logih-getIntenttype" + intentType);
        edtAccount.setText(prefs.getUser());
        edtPsw.setText(prefs.getPsw());

//        RegisterRequestBean registerRequestBean = (RegisterRequestBean) getIntent().
//                getSerializableExtra(AppConstant.SERIAL_KEY);
//        if (registerRequestBean != null) {
//            edtAccount.setText(registerRequestBean.getPhone());
//            edtPsw.setText(registerRequestBean.getPassword());
//        } else {
//            initTestData();
//        }
    }

    private void initTestData() {
        edtAccount.setText("13770721767");
        edtPsw.setText("123456");
    }

    @Override
    protected void initViewEvent() {
        tvRegister.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initTestData();
    }

    /**
     * 用来测试RxJava的进度框的
     */

    private void login(final String userName, final String pwd, final String type) {

        //测试header
        // Observable<LoginResponseBean> observable = new LoginApiControl().loginNew(userName, pwd);

        //测试header response
        Observable<LoginResponseBean> observable = new LoginApiControl().loginResponse(userName, pwd);

        CommonDialogObserver<LoginResponseBean> observer = new CommonDialogObserver<LoginResponseBean>(this) {

            @Override
            public void onNext(LoginResponseBean responseBean) {
                super.onNext(responseBean);
                Loger.i("===login====" + new Gson().toJson(responseBean));
                VsdApplication.getInstance().setLoginResponseBean(responseBean);
                String status = responseBean.getStatus();
                if (status.equals("200")) {
                    SimpleToast.toastMessage("登录成功", Toast.LENGTH_LONG);
                    //
                    prefs.setUser(responseBean.getId());

                    if (!TextUtils.isEmpty(intentType) && intentType.equals("boss")) {
                        //
                        toActivity(RegisterCompanyActivity.class);
                    } else {
                        toActivity(MainActivity.class);
                        // toActivity(ReserviorTestActivity.class);
                    }
                } else if (status.equals("201")) {
                    SimpleToast.toastMessage("登录201", Toast.LENGTH_LONG);
                }

                finish();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(t.getMessage(), Toast.LENGTH_LONG);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, LoginActivity.this);
    }


    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onClick(View view) {
        //  super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_register:
//                userAccountEdit.setText("");
                //   toActivity(RegisterActivity.class);c

                showDialog();

                break;
            case R.id.btn_sure:
//                userPwdEdit.setText("");
                Loger.e("---btnclick");
                //checkCBScert();
                checkData();
                break;
            case R.id.tv_select_company:
                //加入公司
                Loger.e("-----take");
//            spu.setRoleType("1");
                toActivity(FillCodeActivity.class);
                finish();
                dialog.dismiss();
                break;
            case R.id.tv_register_company:
                //创建公司 -先去填写个人信息
                Loger.e("-----foundation");
                toActivityWithType(RegisterActivity.class, "boss");
                finish();

//                EnterpriseBean beanNew = new EnterpriseBean();
//                beanNew.setIntentType("new_login");
//                toAtivityWithParams(FoundationActivity.class, beanNew);
                //创建公司 －－0；加入公司 1 不是返回的roleID，只是判断是点击 注册公司？添加公司？
//                spu.setRoleType("0");
                // toActivity(context, RegCertActivity.class);
                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;

            default:
                break;

        }


    }

    private void checkCBScert() {

        store = CBSCertificateStore.getInstance();
        certificateArrayList = store.getAllCertificateList();
        if (certificateArrayList.size() == 0) {
            //先去申请证书
            SimpleToast.ToastMessage("请先申请个人证书！");
            toActivity(RegisterCertActivity.class);
        }
    }

    private void checkData() {
        String accountStr = edtAccount.getText().toString().trim();
        String pswStr = edtPsw.getText().toString().trim();
        if (TextUtils.isEmpty(accountStr)) {
            SimpleToast.toastMessage("帐号不可为空！", Toast.LENGTH_SHORT);
            edtAccount.requestFocus();
            return;

        }

        if (TextUtils.isEmpty(pswStr)) {
            SimpleToast.toastMessage("密码不可为空！", Toast.LENGTH_SHORT);
            edtPsw.requestFocus();
            return;
        }

        // login
        //  accountStr = "15252466554";

        //    login(accountStr, pswStr, "1");


        //  test
        toActivity(MainActivity.class);

    }


    public void showDialog() {
        dialog = new Dialog(this, R.style.BottomDialog);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_choose_role, null);
        tvRegisterCompany = (TextView) inflate.findViewById(R.id.tv_register_company);
        tvSelectCompany = (TextView) inflate.findViewById(R.id.tv_select_company);
        cancel = (TextView) inflate.findViewById(R.id.btn_cancel);
        tvRegisterCompany.setOnClickListener(this);
        tvSelectCompany.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        // WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //获得window窗口的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        dialogWindow.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(inflate);
        dialog.show();
    }

    private void initEditCheck(EditText eidtText, final ImageButton imageButton) {
        eidtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus) {
                    imageButton.setVisibility(View.VISIBLE);
                } else {
                    imageButton.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
        VsdApplication.getRefWatcher(this).watch(this);

    }

    private void checkUpdateApp() {
        Observable<CheckUpdateResponse> checkUpdateResponseObservable = new DownloadAppControl().getNewAppVersion();
        DefaultObserver<CheckUpdateResponse> checkUpdateResponseCommonDialogObserver = new DefaultObserver<CheckUpdateResponse>() {
            @Override
            protected void showProgress() {

            }

            @Override
            protected void dismissProgress() {

            }

            @Override
            public void onNext(CheckUpdateResponse checkUpdateResponse) {
                super.onNext(checkUpdateResponse);
                updateShowDialog(checkUpdateResponse.getUrl(), checkUpdateResponse.getLast());
                // downLoadApp(checkUpdateResponse.getUrl());
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(checkUpdateResponseObservable, checkUpdateResponseCommonDialogObserver, LoginActivity.this);
    }

    private void updateShowDialog(String url, String version) {
        try {
            UpdateAppDialog dialog = new UpdateAppDialog();
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.SERIAL_KEY, url);
            bundle.putString(AppConstant.INT_KEY, version);
            dialog.setArguments(bundle);
            dialog.show(getSupportFragmentManager(), "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //检查是否有证书


}

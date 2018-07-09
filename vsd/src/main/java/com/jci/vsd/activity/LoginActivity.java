package com.jci.vsd.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.gson.Gson;
import com.jci.vsd.R;
import com.jci.vsd.adapter.PageAdapter;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.CheckUpdateResponse;
import com.jci.vsd.bean.login.LoginResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.fragment.dialog.UpdateAppDialog;
import com.jci.vsd.fragment.login.LoginFragment;
import com.jci.vsd.fragment.login.RegisterFragment;
import com.jci.vsd.network.control.DownloadAppControl;
import com.jci.vsd.network.control.LoginApiControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.DefaultObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * 可以提炼出来
 * Created by Victor on 10/31/2017.
 */

public class LoginActivity extends BaseActivity {
//    private Button nextBtn;
//
//    private ImageButton nameDeleteBtn, pwdDeleteBtn;
//    private EditText userAccountEdit, userPwdEdit;
//    private EditText urlEdit;

    private MySpEdit prefs;

    private TabLayout mTabLayout;
    private ViewPager viewPager;
    private String[] titles;
    private Button btnSure;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    private Dialog dialog;
    private View inflate;
    private TextView cancel;
    private TextView tvSelectCompany;
    private TextView tvRegisterCompany;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = LoginActivity.this;

//        mTabLayout = (TabLayout) findViewById(R.id.tab_login);
//        viewPager = (ViewPager) findViewById(R.id.vp_login);
//        btnSure=(Button)findViewById(R.id.btn_sure);
        btnSure = (Button) findViewById(R.id.btn_sure);
        MySpEdit.getInstance().setAppEmv(true);
        titles = new String[]{"登录", "注册"};
        prefs = MySpEdit.getInstance();

        tvRegister.setText(Html.fromHtml(getResources().getString(R.string.str_register, 1, "", "去注册")));
        initViewEvent();

        //  mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                Log.e("pos=", "" + position);
//                switch (position) {
//                    case 0:
//                        //返回理化性能
////                        fragment = LoginFragment.newInstance();
////                        return fragment;
//                        return new LoginFragment();
//
//                    case 1:
//                        //详情
//                        return new RegisterFragment();
//
//                    default:
//                        // 默认
//
//                }
//                return null;
//            }
//
//            @Override
//            public int getCount() {
//                return titles.length;
//            }
//
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return titles[position];
//            }
//        });
//        mTabLayout.setupWithViewPager(viewPager);


        //环境选择
//        new EnvironmentDialog(this).show();

        // initViewEvent();

        //检查更新
        //  checkUpdateApp();
    }

    @Override
    protected void initViewEvent() {
        tvRegister.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

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
            public void onNext(LoginResponseBean result) {
                super.onNext(result);
                Loger.i("===login====" + new Gson().toJson(result));
                VsdApplication.getInstance().setLoginResponseBean(result);
                if (result != null && !TextUtils.isEmpty(result.getCardId())) {
                    if ("1".equals(type)) {
                        prefs.setUser(userName);
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, LoginActivity.this);
    }

    //response  header

    private void loginResponse(final String userName, final String pwd, final String type) {

        Observable<LoginResponseBean> observable = new LoginApiControl().loginResponse(userName, pwd);
        CommonDialogObserver<LoginResponseBean> observer = new CommonDialogObserver<LoginResponseBean>(this) {

            @Override
            public void onNext(LoginResponseBean result) {
                super.onNext(result);
                Loger.i("===login====" + new Gson().toJson(result));
                VsdApplication.getInstance().setLoginResponseBean(result);
                if (result != null && !TextUtils.isEmpty(result.getCardId())) {
                    if ("1".equals(type)) {
                        prefs.setUser(userName);
                    }
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
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
                //   toActivity(RegisterActivity.class);
                showDialog();

                break;
            case R.id.btn_sure:
//                userPwdEdit.setText("");
                Loger.e("---btnclick");
                toActivity(MainActivity.class);
                break;
            case R.id.tv_select_company:
                Loger.e("-----take");
//            spu.setRoleType("1");
                toActivity(FillCodeActivity.class);
                dialog.dismiss();
                break;
            case R.id.tv_register_company:

                Loger.e("-----foundation");
                toActivity(FoundationActivity.class);
                //创建公司 －－0；加入公司 1 不是返回的roleID，只是判断是点击 注册公司？添加公司？
//                spu.setRoleType("0");
                //  toActivity(context, RegCertActivity.class);

                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
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
                updateShowDialog(checkUpdateResponse.getUrl(), checkUpdateResponse.getContent());
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
}

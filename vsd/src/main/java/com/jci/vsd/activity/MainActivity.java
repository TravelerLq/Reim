package com.jci.vsd.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jci.vsd.R;

import com.jci.vsd.activity.Reim.ReimHomeActivity;
import com.jci.vsd.activity.UserInfo.UserInfoActivity;
import com.jci.vsd.adapter.PageAdapter;
import com.jci.vsd.bean.UserBean;
import com.jci.vsd.bean.download.CheckUpdateResponse;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.data.UserData;
import com.jci.vsd.fragment.EnterpriseHomeFragment;
import com.jci.vsd.fragment.HelpFragment;
import com.jci.vsd.fragment.HomeFragment;
import com.jci.vsd.fragment.dialog.UpdateAppDialog;
import com.jci.vsd.network.control.DownloadAppControl;
import com.jci.vsd.observer.DefaultObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.leibnik.wechatradiobar.WeChatRadioGroup;
import io.reactivex.Observable;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_home)
    WeChatRadioGroup radioGroupHome;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    private PageAdapter pageAdapter;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.textview_title)
    TextView tvTitle;
    private String type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserBean userBean = UserData.getUserInfo();
        if (userBean != null) {
            type = userBean.getType();
            Loger.e("---");
            if (type.equals("1") || type.equals("2")) {
                //boss or部门领导
                setContentView(R.layout.activity_main);
            } else {
                //员工
                setContentView(R.layout.activity_main_employee);
            }
        }


        ButterKnife.bind(this);
        tvTitle.setText(getResources().getString(R.string.home_title));
        //更新检测
        checkUpdateApp();
       // testDown();
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new HomeFragment());
        list.add(new HelpFragment());
        list.add(new EnterpriseHomeFragment());

        pageAdapter = new PageAdapter(getSupportFragmentManager(), list);
        vpHome.setAdapter(pageAdapter);
        radioGroupHome.setViewPager(vpHome);
        initViewEvent();
        buttonBack.setVisibility(View.GONE);
        tvHeader.setVisibility(View.VISIBLE);
        checkAuthority();

    }

    private void testDown() {
        String apkUrl = "http://download.fir.im/v2/app/install/5818acbcca87a836f50014af?download_token=a01301d7f6f8f4957643c3fcfe5ba6ff";
     // apkUrl = "http://192.168.31.109:8080/shuidao/notoken/downapk";
       //apkUrl ="http:\\/\\/www.pgyer.com\\/app\\/installUpdate\\/3becc83ec489185cd5a9259ffc9bef6c?sig=nkhiTi3LmGR8cArX7Ab%2BkeXfxSjZxTsZybjWdwhdwBySiqSohOfV07%2BX5h9Upx4Z";
        updateShowDialog(apkUrl, "1.3");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //getSupportFragmentManager().putFragment(outState, "mContent", mContent);
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

                if (checkUpdateResponse.isFlag()) {
                    // 需要更新
                    //  String apkUrl = AppConstant.BASE_URL+"shuidao/notoken/downapk";
                    String apkUrl = checkUpdateResponse.getUrl();
                    //apkUrl=  "http://download.fir.im/v2/app/install/5818acbcca87a836f50014af?download_token=a01301d7f6f8f4957643c3fcfe5ba6ff";
                    // apkUrl=   "http:\\/\\/www.pgyer.com\\/app\\/installUpdate\\/3becc83ec489185cd5a9259ffc9bef6c?sig=nkhiTi3LmGR8cArX7Ab%2BkeXfxSjZxTsZybjWdwhdwBxZPminY8nLlmO%2B48W%2FYFIA";
                    //  apkUrl= "http:\\/\\/www.pgyer.com\\/app\\/installUpdate\\/3becc83ec489185cd5a9259ffc9bef6c?sig=nkhiTi3LmGR8cArX7Ab%2BkeXfxSjZxTsZybjWdwhdwBwxwSphumXkpHvym1j7jCxc";
                    //  apkUrl="http:\\/\\/www.pgyer.com\\/app\\/installUpdate\\/3becc83ec489185cd5a9259ffc9bef6c?sig=nkhiTi3LmGR8cArX7Ab%2BkeXfxSjZxTsZybjWdwhdwByP7C554zZA33sLt2byZLnb";
                    updateShowDialog(apkUrl, checkUpdateResponse.getLast());
                }
//                updateShowDialog(checkUpdateResponse.getUrl(), checkUpdateResponse.getLast());
//                // updateShowDialog(apkUrl, checkUpdateResponse.getLast());
//                // downLoadApp(checkUpdateResponse.getUrl());
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(checkUpdateResponseObservable, checkUpdateResponseCommonDialogObserver, MainActivity.this);
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
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.topButton:
//                toggle();
//                break;
//            case R.id.scanBtn:
//              //  startActivity(new Intent(MainActivity.this,MaterialScanInputActivity.class));
//                break;
//            case R.id.recycleBtn:
//               // startActivity(new Intent(MainActivity.this,StoreMaterialActivity.class));
//                break;
            case R.id.tv_header:
                toActivity(UserInfoActivity.class);
//                toActivity(UserInfoCenterActivity.class);
                break;

            default:
                break;
        }
    }

    @Override
    protected void initViewEvent() {
        tvHeader.setOnClickListener(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    private void checkAuthority() {


        if (PermissionsUtil.hasPermission(MainActivity.this, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {


        } else {
            PermissionsUtil.requestPermission(MainActivity.this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    Log.e("--", "permissionGranted: 用户授予了访问外部存储的权限");

                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    Log.e("--", "permissionDenied: 用户拒绝了访问外部存储的申请");
                    // needPermissionTips();

                }
            }, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }
}

package com.jci.vsd.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;

import com.jci.vsd.bean.BaseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.fragment.dialog.RxStyleDialogFragment;
import com.jci.vsd.observer.DialogObserverHolder;
import com.jci.vsd.utils.Loger;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;
import com.umeng.analytics.MobclickAgent;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.syan.spark.client.sdk.SparkApplication;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Victor on 10/31/2017.
 * activity的基类
 */

public abstract class BaseActivity extends RxFragmentActivity implements DialogObserverHolder, View.OnClickListener {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private List<Subscription> compositeSubscription = new ArrayList<>();
    protected Unbinder unBinder;

    RxStyleDialogFragment rxStyleDialogFragment;

    protected final static List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VsdApplication.getInstance().addActivity(this);
        //apply for cert init
        SparkApplication.init(getApplication());

        synchronized (mActivities) {
            mActivities.add(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(BaseActivity.this);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unBinder = ButterKnife.bind(this);

    }

    @Override
    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void addSubscription(Subscription subscription) {
        if (compositeSubscription == null) {
            return;
        }
        compositeSubscription.add(subscription);
    }

    @Override
    public void removeDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            return;
        }
        compositeDisposable.remove(disposable);
    }

    @Override
    protected void onDestroy() {
        VsdApplication.getInstance().removeActivity(this);
        //移除view绑定
        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroy();
        clearWorkOnDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
    }

    protected void exit() {
        for (int i = 0; i < mActivities.size(); i++) {
            mActivities.get(i).finish();
        }
    }

    /**
     * onDestroy时调用此方法
     * 切断此Activity中的观察者容器中包含的观察者
     */
    private void clearWorkOnDestroy() {
        //disposable clear
        compositeDisposable.clear();
        //subscription clear
        for (Subscription subscription : compositeSubscription) {
            if (subscription != null)
                subscription.cancel();
        }
        compositeSubscription.clear();
    }

    public void addFragmentNotToStack(int layoutId, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId, fragment);
        fragmentTransaction.commit();
    }


    public void addFragmentByTagNotToStack(int layoutId, Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(layoutId, fragment, tag);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                Loger.e("---btnback-");
                finish();
                break;
        }
    }

    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivity(Class<T> clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }


    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivityWithType(Class<T> clz,String intentTye) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(AppConstant.KEY_TYPE,intentTye);


    }


    /**
     * 跳转页面
     *
     * @param clz 跳转到的类
     * @param <T>
     */
    protected <T> void toActivityWithType(Class<T> clz,String intentTye,int requestCode,boolean needCallBack) {
        Intent intent = new Intent(this, clz);
        intent.putExtra(AppConstant.KEY_TYPE,intentTye);
        if(needCallBack){
            startActivityForResult(intent,requestCode);
        }else {
            startActivity(intent);
        }


    }

//    /**
//     * 跳转页面
//     *
//     * @param clz 跳转到扫描的类
//     * @param <T>
//     */
//    protected <T> void toScanActivityWithParams(Class<T> clz,ScanTypeBean scanTypeBean,boolean needCallBack) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.SERIAL_KEY,scanTypeBean);
//        Intent intent = new Intent(this, clz);
//        intent.putExtras(bundle);
//        if(needCallBack){
//            startActivityForResult(intent,AppConstant.REQUEST_CODE);
//        }else {
//            startActivity(intent);
//        }
//    }
//
//    /**
//     * 跳转页面
//     *
//     * @param clz 跳转到扫描的类
//     * @param <T>
//     */
//    protected <T> void toScanActivityWithParams(Class<T> clz,ScanTypeBean scanTypeBean,int requestCode,boolean needCallBack) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.SERIAL_KEY,scanTypeBean);
//        Intent intent = new Intent(this, clz);
//        intent.putExtras(bundle);
//        if(needCallBack){
//            startActivityForResult(intent,requestCode);
//        }else {
//            startActivity(intent);
//        }
//    }
//
//
//    /**
//     * 跳转页面
//     *
//     * @param clz 跳转到扫描结果的类
//     * @param <T>
//     */
//    protected <T> void toScanResultActivityWithParams(Class<T> clz,ScanInfoResultBean scanTypeBean) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.SERIAL_KEY,scanTypeBean);
//        bundle.putString("deliverNo",VsdApplication.getInstance().getTokenBean().getAccessToken());
//        Intent intent = new Intent(this, clz);
//        intent.putExtras(bundle);
//        startActivity(intent);
//    }

    protected <T> void toAtivityWithParams(Class<T> clz, BaseBean baseBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.SERIAL_KEY, baseBean);
        Intent intent = new Intent(this, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * view 初始化及事件控制
     */
    protected abstract void initViewEvent();

    /**
     * 提示对话框
     *
     * @param message
     */
    protected void warningDialog(String message) {
        new AlertDialog.Builder(BaseActivity.this)
                .setTitle(getResources().getString(R.string.notice))
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //清空之前扫描的料单数据
                        VsdApplication.getInstance().getWaitStoreMaterialBeanList().clear();
                        finish();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(BaseActivity.this);
    }


    protected void showProgress() {


        try {
            rxStyleDialogFragment = new RxStyleDialogFragment();
            rxStyleDialogFragment.setCancelable(false);
            rxStyleDialogFragment.show(this.getSupportFragmentManager(), "");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    protected void dimissProgress(){
        if(rxStyleDialogFragment != null && rxStyleDialogFragment.getDialog() != null && rxStyleDialogFragment.getDialog().isShowing()){
            try {
                rxStyleDialogFragment.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        rxStyleDialogFragment = null;
    }

}

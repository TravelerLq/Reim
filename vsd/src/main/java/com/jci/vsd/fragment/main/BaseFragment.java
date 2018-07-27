package com.jci.vsd.fragment.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jci.vsd.bean.BaseBean;
import com.jci.vsd.constant.AppConstant;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Victor on 11/1/2017.
 */

public class BaseFragment extends RxFragment {
    protected FragmentActivity baseActivity;
    protected Unbinder unBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (FragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (container != null) {
            unBinder = ButterKnife.bind(this, container);
        }
        return container;
    }

    @Override
    public void onDestroyView() {
        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroyView();
    }

    ////    toScanActivityWithParams(MaterialScanInputActivity.class, scanTypeBean,false);
//    public void toScanActivityForResult(Fragment fragment,){
//        ScanTypeBean scanTypeBean = new ScanTypeBean();
//        scanTypeBean.setScanTypeEnum(ScanTypeEnum.TRANSFER);
//        //收料
//        scanTypeBean.setTitleMsg(getResources().getString(R.string.transfer_title));
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.SERIAL_KEY, scanTypeBean);
//        Intent intent = new Intent(getActivity(), MaterialScanInputActivity.class);
//        intent.putExtras(bundle);
//        this.startActivityForResult(intent, FRAGMNET_REQUEST_CODE_SCAN1);
//    }

    /**
     * 页面跳转
     *
     * @param fragment
     * @param clz
     * @param baseBean
     * @param <T>
     */
    protected <T> void toAtivityWithParams(Fragment fragment, Class<T> clz, BaseBean baseBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.SERIAL_KEY, baseBean);
        Intent intent = new Intent(getActivity(), clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    /**
     * 页面跳转带有结果
     *
     * @param fragment
     * @param clz
     * @param baseBean
     * @param <T>
     */
    protected <T> void toAtivityWithParamsForResult(Fragment fragment, Class<T> clz, BaseBean baseBean, int requestcode) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.SERIAL_KEY, baseBean);
        Intent intent = new Intent(getActivity(), clz);
        intent.putExtras(bundle);
        fragment.startActivityForResult(intent, requestcode);
    }
//
//    /**
//     * 跳转页面
//     *
//     * @param clz 跳转到类
//     * @param <T>
//     */
//    protected <T> void toScanActivityForResult(Fragment fragment,Class<T> clz,ScanTypeBean scanTypeBean,int requestcode,boolean needCallBack) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.SERIAL_KEY,scanTypeBean);
//        Intent intent = new Intent(getActivity(), clz);
//        intent.putExtras(bundle);
//        if(needCallBack){
//            fragment.startActivityForResult(intent,requestcode);
//        }else {
//            startActivity(intent);
//        }
//    }

    /**
     * 跳转页面
     *
     * @param clz 跳转到扫描的类
     * @param <T>
     */
    protected <T> void toActivity(Fragment fragment, Class<T> clz) {

        Intent intent = new Intent(getActivity(), clz);
        startActivity(intent);

    }




    /**
     * 跳转页面
     *
     * @param clz 跳转到扫描的类
     * @param <T>
     */
    protected <T> void toActivityWithId(Fragment fragment, Class<T> clz,String id) {

        Intent intent = new Intent(getActivity(), clz);
        intent.putExtra("id",id);
        startActivity(intent);

    }

    protected <T> void toActivityWithData(Context context, Class<T> tClass, String key, String data) {
        Intent intent = new Intent(context, tClass);
        intent.putExtra(key, data);
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        baseActivity = null;
    }
}

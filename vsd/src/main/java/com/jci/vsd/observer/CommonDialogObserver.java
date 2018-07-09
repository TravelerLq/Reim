package com.jci.vsd.observer;

import android.support.v4.app.FragmentManager;

import com.jci.vsd.bean.dialog.ActionsCountDlg;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.fragment.dialog.RxStyleDialogFragment;

/**
 * Created by Victor on 11/1/2017.
 */

public class CommonDialogObserver<T> extends DefaultObserver<T> implements RxStyleDialogFragment.CancelRxJava {
    private DialogObserverHolder mHolder;
    private RxStyleDialogFragment rxStyleDialogFragment;
    private FragmentManager manager;
    public CommonDialogObserver(DialogObserverHolder mHolder){
        this.mHolder = mHolder;
        this.manager = mHolder.getSupportFragmentManager();
    }

    @Override
    protected void showProgress() {
        dimissDialog();
        try {
            rxStyleDialogFragment = new RxStyleDialogFragment();
            rxStyleDialogFragment.setCancelRxJava(this);
            rxStyleDialogFragment.setCancelable(false);
            rxStyleDialogFragment.show(manager, "");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void dismissProgress() {
        dimissDialog();
    }


    /**
     * udpdate 进度对话框
     * @param update
     */
    public void updateProgressDailog(ActionsCountDlg update) {
        if(update != null){
            if(rxStyleDialogFragment != null){
                rxStyleDialogFragment.updateProgress(update);
            }
        }

    }

    /**
     * 取消对话框
     */

    private void dimissDialog(){
        if(rxStyleDialogFragment != null && rxStyleDialogFragment.getDialog() != null && rxStyleDialogFragment.getDialog().isShowing()){
            try {
                rxStyleDialogFragment.dismiss();
            }catch (Exception e){
                e.printStackTrace();
            }
       }
        rxStyleDialogFragment = null;
    }


    @Override
    public void canceRxJava() {
        onError(new IApiException("取消","取消数据加载"));
    }
}

package com.jci.vsd.fragment.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.FileCallBack;
import com.jci.vsd.bean.download.FileSubscriber;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.DownloadAppControl;
import com.jci.vsd.utils.AutoInstall;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.Utils;
import com.jci.vsd.view.widget.SimpleToast;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class UpdateAppDialog extends DialogFragment implements View.OnClickListener {
    private ProgressBar updateProgressBar;
    private Button sureBtn;
    private Button cancelBtn;
    private String downloadUrl = "";
    private String versionCode = "";
    private static final String fileName = "reim.apk";
    private static final String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    private TextView newVersionMsgTxt,nowVersionMsgTxt;
    Subscription subscription;
    private ImageButton nameDeleteBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //url + version
        downloadUrl = getArguments().getString(AppConstant.SERIAL_KEY);
        versionCode = getArguments().getString(AppConstant.INT_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//去掉这句话，背景会变暗
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        View result = inflater.inflate(R.layout.fragment_update_dialog_layout, container, false);
        updateProgressBar = (ProgressBar) result.findViewById(R.id.updateProgressBar);
        sureBtn = (Button) result.findViewById(R.id.sureBtn);
        cancelBtn = (Button) result.findViewById(R.id.cancelBtn);
        nameDeleteBtn = (ImageButton) result.findViewById(R.id.nameDeleteBtn);
        nowVersionMsgTxt = (TextView) result.findViewById(R.id.nowVersionMsgTxt);
        newVersionMsgTxt = (TextView) result.findViewById(R.id.newVersionMsgTxt);
        nowVersionMsgTxt.setText(Utils.getVersionCode());
        newVersionMsgTxt.setText(versionCode);
        nameDeleteBtn.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        updateProgressBar.setMax(100);
        return result;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sureBtn:
                downLoadApp(downloadUrl);
                break;
            case R.id.nameDeleteBtn:
            case R.id.cancelBtn: {
                dismiss();
            }
            break;
        }
    }


    public void downLoadApp(String url) {
        final FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(fileStoreDir, fileName) {

            @Override
            public void onSuccess(final ResponseBody responseBody) {
                sureBtn.setEnabled(true);
                cancelBtn.setEnabled(true);
                updateProgressBar.setVisibility(View.INVISIBLE);
                AutoInstall.setUrl(fileStoreDir
                        + "/" + fileName);
                AutoInstall.install(getActivity());
                dismiss();
            }

            @Override
            public void progress(long progress, long total) {
                int progressIndex = (int) (progress * 100 / total);
                Loger.i("progress = " + progress + "," + total + "," + Thread.currentThread().getId() + "," + progressIndex);
                updateProgressBar.setProgress(progressIndex);
            }

            @Override
            public void onStart() {
                updateProgressBar.setVisibility(View.VISIBLE);
                sureBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
            }

            @Override
            public void onCompleted() {
                updateProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                updateProgressBar.setVisibility(View.INVISIBLE);
                sureBtn.setEnabled(true);
                cancelBtn.setEnabled(true);
            }
        };
        rx.Observable<ResponseBody> obserable = new DownloadAppControl().downloadApp1(url);
        subscription = obserable.subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody body) {
                        try {
                            callBack.saveFile(body);
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.onError(e);
                            Utils.doException(e);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //指定线程保存文件
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Utils.doException(throwable);
                    }
                }).observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new FileSubscriber<ResponseBody>(VsdApplication.getInstance(), callBack));//在主线程中更新ui
        //new DownloadAppControl().downloadApp(getActivity(),url,callBack);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            Loger.i("onDestroy == ");
            subscription.unsubscribe();
        }
    }
}

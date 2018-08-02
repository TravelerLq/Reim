package com.jci.vsd.activity.Reim;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.FileCallBack;
import com.jci.vsd.bean.download.FileSubscriber;
import com.jci.vsd.network.control.DownloadAppControl;
import com.jci.vsd.network.control.DownloadFileControl;
import com.jci.vsd.utils.AutoInstall;
import com.jci.vsd.utils.FileUtils;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.Utils;

import java.io.File;

import butterknife.BindView;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by liqing on 18/8/2.
 */

public class TestDownPic extends BaseActivity {

    private String fileName = ".png";
    private static final String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    Subscription subscription;
    private String url;
    @BindView(R.id.iv_test)
    ImageView ivTest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);
        http://192.168.31.109:8080/shuidao/web/static/pngforms/0e0d12244f1c4f6dae6ae0bcdb33e187.png
        url = "http://192.168.31.109:8080/shuidao/notoken/downapk";
        url = "http://192.168.31.109:8080/shuidao/web/static/pngforms/ece2bea0934a45ffb6ffc965d7de3cae.png";
//        url="http://pic41.nipic.com/20140509/4746986_145156378323_2.jpg";
//        url="http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg";
        fileName = System.currentTimeMillis() + ".png";
        downLoadPic(fileName, url);

    }

    @Override
    protected void initViewEvent() {

    }


    public void downLoadPic(final String fileName, String url) {
        final FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(fileStoreDir, fileName) {

            @Override
            public void onSuccess(final ResponseBody responseBody) {

//                AutoInstall.setUrl(fileStoreDir
//                        + "/" + fileName);
//                AutoInstall.i
// nstall(getActivity());
//                dismiss();

                String path = fileStoreDir + "/" + fileName;
//                Uri uri = Uri.fromFile(new File(path));
//                Glide.with(TestDownPic.this)
//
//                        .load(uri)
//                        .thumbnail(0.1f)
//                        .into(ivTest);

                Glide.with(TestDownPic.this)
                        .load(path)
                        .into(ivTest);


                Loger.e("--downLoad--success +path=" + path);
                String hashFileReim = null;
                try {
                    hashFileReim = FileUtils.getMD5Checksum(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Loger.e("--hashReim" + hashFileReim);

            }

            @Override
            public void progress(long progress, long total) {
                int progressIndex = (int) (progress * 100 / total);
                Loger.e("progress = " + progress + "/" + total + "," + Thread.currentThread().getId() + "," + progressIndex);
            //    updateProgressBar.setProgress(progressIndex);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {
                //updateProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Loger.e("--hashReim--onError");
//                updateProgressBar.setVisibility(View.INVISIBLE);
//                sureBtn.setEnabled(true);
//                cancelBtn.setEnabled(true);
            }
        };
        rx.Observable<ResponseBody> obserable = new DownloadFileControl().downloadReimFile(url);
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

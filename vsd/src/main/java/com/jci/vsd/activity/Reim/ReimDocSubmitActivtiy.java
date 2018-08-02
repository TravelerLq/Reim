package com.jci.vsd.activity.Reim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.MainActivity;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.FileCallBack;
import com.jci.vsd.bean.download.FileSubscriber;
import com.jci.vsd.bean.reim.ReimAddResponseBean;
import com.jci.vsd.bean.reim.ReimDocSubmitBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.data.ReimAddData;
import com.jci.vsd.network.control.DownloadFileControl;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.BitmapUtil;
import com.jci.vsd.utils.FileUtils;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.StrTobaseUtil;
import com.jci.vsd.utils.Utils;
import com.jci.vsd.view.widget.SimpleToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.unitid.spark.cm.sdk.business.SignatureP1Service;
import cn.unitid.spark.cm.sdk.common.DataProcessType;
import cn.unitid.spark.cm.sdk.data.response.DataProcessResponse;
import cn.unitid.spark.cm.sdk.exception.CmSdkException;
import cn.unitid.spark.cm.sdk.listener.ProcessListener;
import io.reactivex.Observable;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.jci.vsd.constant.AppConstant.BASE__DOWN_URL;

/**
 * Created by liqing on 18/6/28.
 * 生成单据、并提交
 */

public class ReimDocSubmitActivtiy extends BaseActivity {

    @BindView(R.id.iv_reim_doc)
    ImageView ivReimDoc;
    @BindView(R.id.btn_reim_submit)
    Button btnReimDocSubmit;

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    private String base64Code;
    private String picPath;
    private String cert;
    private String sign;
    private List<String> selectPic;
    private int id;
    String hashFileReim = null;

    //下载图片

    private String fileName = ".png";
    private static final String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    Subscription subscription;
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reim_submit);
        initViewEvent();

        ReimAddResponseBean intentBean = (ReimAddResponseBean) getIntent().getExtras()
                .getSerializable(AppConstant.SERIAL_KEY);
        if (intentBean != null) {
            id = intentBean.getId();
            url = intentBean.getUri();
            url = url + AppConstant.BASE_URL;
            downLoadPic(fileName, url);
        }
//        String idStr = getIntent().getStringExtra(AppConstant.KEY_TYPE);
//        if (!TextUtils.isEmpty(idStr)) {
//            id = Integer.valueOf(idStr);
//
//            url = BASE__DOWN_URL + idStr;
//        }
//        Loger.e("formId" + id);
//        fileName = System.currentTimeMillis() + ".png";
//        url = AppConstant.BASE_URL;


    }

    @Override
    protected void initViewEvent() {
        ivReimDoc.setOnClickListener(this);
        btnReimDocSubmit.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.submit_reim_doc));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_reim_submit:

                try {

                    if (!TextUtils.isEmpty(hashFileReim)) {
                        //  signVerifyP1(hashFile);
                        signVerifyP1(hashFileReim);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Loger.e("--hashFileReim--" + hashFileReim);
//                signVerifyP1(hashFile);
                break;
            //提交单据 （先签名添加，成功后－再提交）
            case R.id.iv_reim_doc:

                selectPic = new ArrayList<>();
                selectPic.add(picPath);
                PhotoPreview.builder()
                        .setPhotos((ArrayList) selectPic)
                        .setShowDeleteButton(false)
                        .start(ReimDocSubmitActivtiy.this, PhotoPreview.REQUEST_CODE);
                //查看单据大图
                break;
            case R.id.button_back:
                warningDialog("确定退出？");
                break;
            default:
                break;


        }
    }


    //获取报销单图片
    public void downLoadPic(final String fileName, String url) {
        final FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(fileStoreDir, fileName) {

            @Override
            public void onSuccess(final ResponseBody responseBody) {
                String path = fileStoreDir + "/" + fileName;
                Glide.with(ReimDocSubmitActivtiy.this)
                        .load(path)
                        .into(ivReimDoc);
                Loger.e("--downLoad--success +path=" + path);
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
                Loger.i("progress = " + progress + "," + total + "," + Thread.currentThread().getId() + "," + progressIndex);
//                updateProgressBar.setProgress(progressIndex);
            }

            @Override
            public void onStart() {
                showProgress();
            }

            @Override
            public void onCompleted() {
                //updateProgressBar.setVisibility(View.INVISIBLE);
                dimissProgress();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                Loger.e("FileCallback--onError" + e.getMessage());
                dimissProgress();
                SimpleToast.toastMessage(e.getMessage(), Toast.LENGTH_SHORT);
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

    //签名验证
    private void signVerifyP1(final String base64Code1) {
//        pdu.showpd();

        String plantext = base64Code1;


        ReimDocSubmitActivtiy.this.getIntent().putExtra("data", plantext);
        ReimDocSubmitActivtiy.this.getIntent().putExtra("type", DataProcessType.SIGNATURE_P1.name());
        SignatureP1Service signatureP1Service = new SignatureP1Service(ReimDocSubmitActivtiy.this, "1234", new ProcessListener<DataProcessResponse>() {
            @Override
            public void doFinish(DataProcessResponse dataProcessResponse, String certificate) {

//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                if (dataProcessResponse.getRet() == 0) {
                    Log.e("密钥", "= " + dataProcessResponse.getResult());
                    // spu.setCertKey(dataProcessResponse.getResult());
                    //获得就是签名证书
                    Log.e("cert", "= " + certificate);
                    cert = certificate;
                    sign = dataProcessResponse.getResult();
                    // spu.setCert(certificate);
                    ReimDocSubmitBean bean = new ReimDocSubmitBean();
                    bean.setCer(cert);
                    bean.setSign(sign);
                    bean.setId(id);
                    Loger.e("---id" + id);
                    submitReimDoc(bean);
                    //去提交单据
                    // submitSignJsonstring();
                } else {
//                    if (pdu.getMypDialog().isShowing()) {
//                        pdu.dismisspd();
//                    }
                    Toast.makeText(ReimDocSubmitActivtiy.this, "文件签名失败" + dataProcessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void doException(CmSdkException e) {
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                Toast.makeText(ReimDocSubmitActivtiy.this, "文件签名失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void submitReimDoc(ReimDocSubmitBean bean) {
        Observable<ReimAddResponseBean> observable = new ReimControl().submitReimDoc(bean);
        CommonDialogObserver<ReimAddResponseBean> observer = new CommonDialogObserver<ReimAddResponseBean>(this) {
            @Override
            public void onNext(ReimAddResponseBean reimAddResponseBean) {
                super.onNext(reimAddResponseBean);
                SimpleToast.toastMessage("提交成功。", Toast.LENGTH_SHORT);
                ReimAddData.removeReimList();
                if (reimAddResponseBean != null) {
                    int formId = reimAddResponseBean.getId();
                    Loger.e("formIdFormNet--" + formId);
                    MySpEdit.getInstance().setFormId(formId);
                    Loger.e("formIdFormShare--" + MySpEdit.getInstance().getFormId());

//                    Thread closeActivity = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(3000);
//                                // Do some stuff
//                            } catch (Exception e) {
//                                e.getLocalizedMessage();
//                            }
//                        }
//                    });
//                    closeActivity.start();
                    finish();
                    toActivity(MainActivity.class);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ReimDocSubmitActivtiy.this);
    }


}

package com.jci.vsd.activity.Reim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.adapter.TimeLineAdapter;
import com.jci.vsd.adapter.reim.ApprovalDetailRecycleAdapter;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.FileCallBack;
import com.jci.vsd.bean.download.FileSubscriber;
import com.jci.vsd.bean.reim.ApprovalAllDetailBean;
import com.jci.vsd.bean.reim.MyReimDetailBean;
import com.jci.vsd.bean.reim.ReimPicBean;
import com.jci.vsd.bean.reim.SubmitApprovalBean;
import com.jci.vsd.bean.reim.WaitApprovalDetailBean;
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
import java.util.HashMap;
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

/**
 * Created by liqing on 18/6/28.
 * 我的审批 -审批详情
 */

public class MyApprovalProcessActivity extends BaseActivity {
    @BindView(R.id.rl_approval_detail)
    RecyclerView rlApprovalDetail;
    @BindView(R.id.rl_time_line)
    RecyclerView rlTimeLine;
    @BindView(R.id.iv_reim_pic)
    ImageView ivReimPic;

    @BindView(R.id.tv_approval_unpass)
    TextView tvUnpass;
    @BindView(R.id.tv_approval_pass)
    TextView tvPass;
    //    @BindView(R.id.button_back);
    @BindView(R.id.button_back)
    ImageButton backBtn;

    @BindView(R.id.textview_title)
    TextView titleTxt;


    private Context context;
    private ApprovalDetailRecycleAdapter adapter;
    private LinearLayoutManager layoutReimManager;


    private List<MyReimDetailBean.CostsBean> approvalReimbeanList;
    private String reason = "";
    //    private boolean approveResultId;
//    private int level = 0;
    private String picPath;
    private String cert = "";
    private String sign = "";
    private int id;
    //    private String reimBase64;
//    private String reimPicName;
//    private String hashFile;
    private String approvalResult;
    //    private Bitmap bitmap;
//    private File file;
    private List<String> selectPic;
    private String fileName = ".png";
    private static final String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    Subscription subscription;
    private String hashFileReim;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_approval_detail);
        context = MyApprovalProcessActivity.this;
        titleTxt.setText(getResources().getString(R.string.my_approval_detail));

        initViewEvent();
        layoutReimManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        // layoutTimeManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        approvalReimbeanList = new ArrayList<>();
        initRecyApproval();
        id = Integer.valueOf(getIntent().getStringExtra("id"));
        Loger.e("---approval id" + id);
        getData(id);

    }


    private void initRecyApproval() {
        rlApprovalDetail.setLayoutManager(layoutReimManager);
        adapter = new ApprovalDetailRecycleAdapter(context, approvalReimbeanList);
        rlApprovalDetail.setAdapter(adapter);
        adapter.setOnItemClickListener(new ApprovalDetailRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                toAtivityWithParams(WaitApprovalPicActivity.class, approvalReimbeanList.get(pos));
            }

            @Override
            public void onPicCLick(View view, int pos) {
                // 详情


                //选中的图片集合
//                String picPath = BitmapUtil.saveBitmapToSDCard(bitmaps.get(pos), System.currentTimeMillis() + ".jpg");
//                Loger.e("bitmap-- i=" + pos + " save to Pic" + picPath);
//                selectPic.clear();
//                selectPic.add(0, picPath);
//                PhotoPreview.builder()
//                        .setPhotos((ArrayList) selectPic)
//                        .setShowDeleteButton(false)
//                        .start(MyReimApprovalDetailActivity.this, PhotoPreview.REQUEST_CODE);

            }
        });
    }


//    private void initTimeLine() {
//        initTimeLineData();
//
//        rlTimeLine.setLayoutManager(layoutTimeManager);
//        rlTimeLine.setHasFixedSize(true);
//
//        //用自定义分割线类设置分割线
//        rlTimeLine.addItemDecoration(new DividerItemDecorationOld(this));
//        timeLineAdapter = new TimeLineAdapter(context, timeLineItem);
//        rlTimeLine.setAdapter(timeLineAdapter);
//
//        timeLineItem.clear();
//        if (approvalProcessbeanList.size() == 0) {
//            HashMap<String, Object> map = new HashMap<String, Object>();
//            map.put("ItemTitle", "暂无数据");
//            map.put("ItemText", "");
//            timeLineItem.add(0, map);
//
//        } else {
//            for (int i = 0; i < approvalProcessbeanList.size(); i++) {
//                Loger.e("----processList.size=" + approvalProcessbeanList.size());
//                String strDepart = approvalProcessbeanList.get(i).getApprovalName();
//                String strApproval = approvalProcessbeanList.get(i).getApprover();
//                String strResult = approvalProcessbeanList.get(i).getResult();
////                if (strResult.equals("true")) {
////                    strResult = "通过";
////                } else {
////                    strResult = "不通过";
////                }
//                String itemTitleStr = strDepart + " " + strApproval + " " + strResult;
//                String itemDate = approvalProcessbeanList.get(i).getDate();
//                HashMap<String, Object> map = new HashMap<String, Object>();
//                map.put("ItemTitle", itemTitleStr);
//                map.put("ItemText", itemDate);
//                timeLineItem.add(i, map);
//            }
//        }
//
//        Loger.e("----processList.size=" + timeLineItem.size());
//        timeLineAdapter.notifyDataSetChanged();
//    }
//
//    private void initTimeLineData() {
//
//        ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean bean =
//                new ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean();
//        bean.setApprovalName("部门审批");
//        bean.setApprover("张三");
//        bean.setResult("通过");
//        bean.setNumber(1);
//        bean.setDate("2018-4-17");
//
//        ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean bean1 =
//                new ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean();
//        bean.setApprovalName("财务审批");
//        bean.setApprover("王朕");
//        bean.setResult("通过");
//        bean.setNumber(2);
//        bean.setDate("2018-4-27");
//
//
//        approvalProcessbeanList.add(0, bean);
//        approvalProcessbeanList.add(1, bean1);
//    }

    @Override
    protected void initViewEvent() {
        ivReimPic.setOnClickListener(this);
        tvPass.setOnClickListener(this);
        tvUnpass.setOnClickListener(this);
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_reim_pic:
                //查看大图的报销单据照片
                selectPic = new ArrayList<>();
                selectPic.add(picPath);
                PhotoPreview.builder()
                        .setPhotos((ArrayList) selectPic)
                        .setShowDeleteButton(false)
                        .start(MyApprovalProcessActivity.this, PhotoPreview.REQUEST_CODE);
                break;
            case R.id.tv_approval_pass:
                //通过，先去签名数据
                approvalResult = "true";
                try {
                    if (!TextUtils.isEmpty(hashFileReim)) {
                        signVerifyP1(hashFileReim, approvalResult);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                Loger.e("--hashFileReim--" + hashFileReim);

                break;
            case R.id.tv_approval_unpass:
                //填写理由
                showReasonDialog();
                break;
            default:
                break;
        }
    }

//    private void submit() {
//        switch (level) {
////            case 0:
////                getPdfForm();
////                break;
////            case 1:
////                //
////                toDownLoadPdf();
////                break;
////            case 2:
////                // signVerifyP1();
////                signVerifyP1NoPin();
////                break;
////            case 3:
////                submitSignJsonstring();
////                break;
////            case 4:
////                submitApproval();
////                break;
//            default:
//                break;
//        }
//    }

    //填写reason Dialog

    private void showReasonDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请填写不通过理由");
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {
                reason = input.getText().toString();
                //pass=1 ;unpass =2
                approvalResult = "false";
                if (TextUtils.isEmpty(reason)) {
                    SimpleToast.toastMessage("请填写不通过理由", Toast.LENGTH_SHORT);
                    return;
                }

                try {
                    if (!TextUtils.isEmpty(hashFileReim)) {
                        signVerifyP1(hashFileReim, approvalResult);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
                Loger.e("--hashFileReim--" + hashFileReim);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    //签名验证
    private void signVerifyP1(final String base64Code1, final String isPass) {
        String plantext = base64Code1;
        MyApprovalProcessActivity.this.getIntent().putExtra("data", plantext);
        MyApprovalProcessActivity.this.getIntent().putExtra("type", DataProcessType.SIGNATURE_P1.name());
        SignatureP1Service signatureP1Service = new SignatureP1Service(MyApprovalProcessActivity.this, "1234", new ProcessListener<DataProcessResponse>() {
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
                    SubmitApprovalBean bean = new SubmitApprovalBean();
                    bean.setCer(cert);
                    bean.setSign(sign);
                    bean.setFlag(isPass);
//                    id = 48;
                    Loger.e("--pass Id" + id);
                    bean.setId(id);
                    bean.setReason(reason);
                    submitApproval(bean);

                } else {
//                    if (pdu.getMypDialog().isShowing()) {
//                        pdu.dismisspd();
//                    }
                    Toast.makeText(MyApprovalProcessActivity.this, "文件签名失败" + dataProcessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void doException(CmSdkException e) {
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                Toast.makeText(MyApprovalProcessActivity.this, "文件签名失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getData(int id) {
        Observable<MyReimDetailBean> observable = new ReimControl().getMyReimDetail(id);
        CommonDialogObserver<MyReimDetailBean> observer = new CommonDialogObserver<MyReimDetailBean>(this) {
            @Override
            public void onNext(MyReimDetailBean bean) {
                super.onNext(bean);

                if (bean != null) {

                    if (bean.getCosts().size() == 0) {
                        SimpleToast.toastMessage("暂无数据", Toast.LENGTH_SHORT);

                    } else {
                        SimpleToast.toastMessage("获取成功", Toast.LENGTH_SHORT);
                        String picUrl = bean.getUrl();
                        String fileName = System.currentTimeMillis() + ".png";

                        downLoadPic(fileName, picUrl);

//                        String base64Code = bean.getBytes();
//                        String reimPicName = bean.getName();
//                        bitmap = StrTobaseUtil.base64ToBitmap(base64Code);
//                        // file = BitmapUtil.saveBitmapToSDCardFile(bitmap, reimPicName + ".jpg");
//                        // file = BitmapUtil.saveBitmapToSDCardFile(bitmap, reimPicName );
//
//                        picPath = BitmapUtil.saveBitmapToSDCard(bitmap, System.currentTimeMillis() + ".jpg");
//                        Loger.e("---picPath" + picPath);
//                        //  picPath = BitmapUtil.saveBitmapToSDCard(bitmap, reimPicName + ".jpg");
//                        ivReimPic.setImageBitmap(bitmap);


//                        try {
//                            hashFile = FileUtils.getMD5Checksum(picPath);
//                            Loger.e("--hashFile--" + hashFile);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

                        approvalReimbeanList.clear();
                        approvalReimbeanList.addAll(bean.getCosts());
                        adapter.notifyDataSetChanged();


                    }

                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MyApprovalProcessActivity.this);


    }


    //获取报销单图片
    public void downLoadPic(final String fileName, String url) {
        final FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(fileStoreDir, fileName) {

            @Override
            public void onSuccess(final ResponseBody responseBody) {
                String path = fileStoreDir + "/" + fileName;
                picPath=path;
                Glide.with(MyApprovalProcessActivity.this)
                        .load(path)
                        .into(ivReimPic);
                Loger.e("--downLoad--success +path=" + path);
                try {
                    hashFileReim = FileUtils.getMD5Checksum(path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Loger.e("--hashFileReim" + hashFileReim);

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
        // rx.Observable<ResponseBody> obserable = new DownloadFileControl().downloadReimFile(url);
        rx.Observable<ResponseBody> obserable = new DownloadFileControl().downloadReimFileWithToken(url);
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


    private void submitApproval(SubmitApprovalBean bean) {

        Observable<Boolean> observable = new ReimControl().submitApproval(bean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean boo) {
                super.onNext(boo);
                SimpleToast.toastMessage("提交成功。", Toast.LENGTH_SHORT);
//                toActivity(MyApprovalRecyActivity.class);
                finish();

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MyApprovalProcessActivity.this);
    }

}

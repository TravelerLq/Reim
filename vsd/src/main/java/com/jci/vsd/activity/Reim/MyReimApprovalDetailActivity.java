package com.jci.vsd.activity.Reim;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.adapter.TimeLineAdapter;
import com.jci.vsd.adapter.reim.MyReimRecycleAdapter;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.FileCallBack;
import com.jci.vsd.bean.download.FileSubscriber;
import com.jci.vsd.bean.reim.ApprovalAllDetailBean;
import com.jci.vsd.bean.reim.MyReimDetailBean;
import com.jci.vsd.network.control.DownloadFileControl;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.BitmapUtil;
import com.jci.vsd.utils.FileUtils;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.StrTobaseUtil;
import com.jci.vsd.utils.Utils;
import com.jci.vsd.view.widget.DividerItemDecorationOld;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.ResponseBody;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by liqing on 18/6/28.
 * 我的报销 -审批详情
 */

public class MyReimApprovalDetailActivity extends BaseActivity {
    @BindView(R.id.rl_approval_detail)
    RecyclerView rlApprovalDetail;
    @BindView(R.id.rl_time_line)
    RecyclerView rlTimeLine;
    @BindView(R.id.iv_reim_pic)
    ImageView ivReimPic;

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    private Context context;
    private MyReimRecycleAdapter adapter;
    private LinearLayoutManager layoutReimManager;
    private LinearLayoutManager layoutTimeManager;

    private ArrayList<HashMap<String, Object>> timeLineItem;

    private List<MyReimDetailBean.CostsBean> mDatas;

    private List<MyReimDetailBean.SchedsBean> approvalProcessbeanList;
    private TimeLineAdapter timeLineAdapter;
    private int id;
    private String picPath;
    private List<String> selectPic;
    private String reason="";

    private String fileName = ".png";
    private static final String fileStoreDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    Subscription subscription;
    private String hashFileReim;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reim_approval_detail);
        context = MyReimApprovalDetailActivity.this;
        initViewEvent();
        layoutReimManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        layoutTimeManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        approvalProcessbeanList = new ArrayList<>();
        timeLineItem = new ArrayList<>();
        selectPic = new ArrayList<>();

        initRecyApproval();
        //    initTimeLine();
        if (getIntent() != null) {
            id = Integer.valueOf(getIntent().getStringExtra("id"));
        }

        getData(id);

    }


    private void initRecyApproval() {
        rlApprovalDetail.setLayoutManager(layoutReimManager);
        adapter = new MyReimRecycleAdapter(context, mDatas);
        rlApprovalDetail.setAdapter(adapter);
        adapter.setOnItemClickListener(new MyReimRecycleAdapter.OnItemClickListener() {
            @Override
            public void onPicCLick(View view, int pos) {
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


    private void initTimeLine() {


        rlTimeLine.setLayoutManager(layoutTimeManager);
        rlTimeLine.setHasFixedSize(true);

        //用自定义分割线类设置分割线
        rlTimeLine.addItemDecoration(new DividerItemDecorationOld(this));
        timeLineAdapter = new TimeLineAdapter(context, timeLineItem);
        rlTimeLine.setAdapter(timeLineAdapter);

        timeLineItem.clear();
        if (approvalProcessbeanList.size() == 0) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemTitle", "暂无数据");
            map.put("ItemText", "");
            timeLineItem.add(0, map);

        } else {
            for (int i = 0; i < approvalProcessbeanList.size(); i++) {
                Loger.e("----processList.size=" + approvalProcessbeanList.size());
                String strDepart = approvalProcessbeanList.get(i).getName();
                String strApproval = approvalProcessbeanList.get(i).getChecker();
                String strResult = approvalProcessbeanList.get(i).isResult();
//                if (strResult.equals("true")) {
//                    strResult = "通过";
//                } else {
//                    strResult = "不通过";
//                }
                String itemTitleStr = strDepart + " " + strApproval + " " + strResult;
                String itemDate = approvalProcessbeanList.get(i).getTime();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", itemTitleStr);
                map.put("ItemText", itemDate);
                timeLineItem.add(i, map);
            }
        }

        Loger.e("----processList.size=" + timeLineItem.size());
        timeLineAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initViewEvent() {
        ivReimPic.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.my_reim_approval_detail));


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_reim_pic:
                //查看大图的报销单据照片
                PhotoPreview.builder()
                        .setPhotos((ArrayList) selectPic)
                        .setShowDeleteButton(false)
                        .start(MyReimApprovalDetailActivity.this, PhotoPreview.REQUEST_CODE);
                //查看单据大图

                break;
//            case R.id.tv_approving:
//
//                break;
            default:
                break;
        }
    }


    private void getData(int id) {
        Observable<MyReimDetailBean> observable = new ReimControl().getMyReimDetail(id);
        CommonDialogObserver<MyReimDetailBean> observer = new CommonDialogObserver<MyReimDetailBean>(this) {
            @Override
            public void onNext(MyReimDetailBean bean) {
                super.onNext(bean);

                if (bean != null) {
                    if (bean.getCosts().size() > 0) {
                        //  SimpleToast.toastMessage("成功", Toast.LENGTH_SHORT);
                        mDatas.clear();
                        mDatas.addAll(bean.getCosts());
                        adapter.notifyDataSetChanged();
                    } else {
                        SimpleToast.toastMessage("暂无数据", Toast.LENGTH_SHORT);
                    }

                    if (bean.getScheds().size() > 0) {
                        approvalProcessbeanList.clear();
                        approvalProcessbeanList.addAll(bean.getScheds());
                        for (int i = 0; i < approvalProcessbeanList.size(); i++) {
                            Loger.e("proces--" + approvalProcessbeanList.get(i).getName());
                        }
                        initTimeLine();

                    } else {
                        SimpleToast.toastMessage("暂无审批进度", Toast.LENGTH_SHORT);
                    }

                    String picUrl=bean.getUrl();
                    String fileName= System.currentTimeMillis() + ".png";
                    downLoadPic(fileName, picUrl);

//                    String base64Code = bean.getBytes();
//                    Bitmap bitmap = StrTobaseUtil.base64ToBitmap(base64Code);
//                    //bitmap to png
//
//                    picPath = BitmapUtil.saveBitmapToSDCard(bitmap, System.currentTimeMillis() + ".jpg");
//                    ivReimPic.setImageBitmap(bitmap);


                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MyReimApprovalDetailActivity.this);


    }
//


    //获取报销单图片
    public void downLoadPic(final String fileName, String url) {
        final FileCallBack<ResponseBody> callBack = new FileCallBack<ResponseBody>(fileStoreDir, fileName) {

            @Override
            public void onSuccess(final ResponseBody responseBody) {
                String path = fileStoreDir + "/" + fileName;
                Loger.e("---picName--"+path);
                Glide.with(MyReimApprovalDetailActivity.this)
                        .load(path)
                        .into(ivReimPic);
                selectPic.add(path);
//                Loger.e("--downLoad--success +path=" + path);
//                try {
//                    hashFileReim = FileUtils.getMD5Checksum(path);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Loger.e("--hashFileReim" + hashFileReim);

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
        rx.Observable<ResponseBody> obserable =new DownloadFileControl().downloadReimFileWithToken(url);
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

}

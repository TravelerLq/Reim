package com.jci.vsd.activity.Reim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.adapter.TimeLineAdapter;
import com.jci.vsd.adapter.reim.ApprovalDetailRecycleAdapter;
import com.jci.vsd.bean.reim.ApprovalAllDetailBean;
import com.jci.vsd.bean.reim.WaitApprovalDetailBean;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.DividerItemDecorationOld;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/6/28.
 * 我的报销 -审批详情
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

    private Context context;
    private ApprovalDetailRecycleAdapter adapter;
    private LinearLayoutManager layoutReimManager;
    private LinearLayoutManager layoutTimeManager;

    private ArrayList<HashMap<String, Object>> timeLineItem;

    private List<WaitApprovalDetailBean> approvalReimbeanList;

    private List<ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean> approvalProcessbeanList;
    private TimeLineAdapter timeLineAdapter;
    private String reason;
    private boolean approveResultId;
    private int level = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_approval_detail);
        context = MyApprovalProcessActivity.this;
        initViewEvent();
        layoutReimManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        layoutTimeManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        approvalReimbeanList = new ArrayList<>();
        approvalProcessbeanList = new ArrayList<>();
        timeLineItem = new ArrayList<>();
        initRecyApproval();
        // initTimeLine();
        int id = Integer.valueOf(getIntent().getStringExtra("id"));

        getData(id);

    }


    private void initRecyApproval() {
        rlApprovalDetail.setLayoutManager(layoutReimManager);
        adapter = new ApprovalDetailRecycleAdapter(context, approvalReimbeanList);
        rlApprovalDetail.setAdapter(adapter);
        adapter.setOnItemClickListener(new ApprovalDetailRecycleAdapter.OnItemClickListener() {
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
        initTimeLineData();

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
                String strDepart = approvalProcessbeanList.get(i).getApprovalName();
                String strApproval = approvalProcessbeanList.get(i).getApprover();
                String strResult = approvalProcessbeanList.get(i).getResult();
//                if (strResult.equals("true")) {
//                    strResult = "通过";
//                } else {
//                    strResult = "不通过";
//                }
                String itemTitleStr = strDepart + " " + strApproval + " " + strResult;
                String itemDate = approvalProcessbeanList.get(i).getDate();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("ItemTitle", itemTitleStr);
                map.put("ItemText", itemDate);
                timeLineItem.add(i, map);
            }
        }

        Loger.e("----processList.size=" + timeLineItem.size());
        timeLineAdapter.notifyDataSetChanged();
    }

    private void initTimeLineData() {

        ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean bean =
                new ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean();
        bean.setApprovalName("部门审批");
        bean.setApprover("张三");
        bean.setResult("通过");
        bean.setNumber(1);
        bean.setDate("2018-4-17");

        ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean bean1 =
                new ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean();
        bean.setApprovalName("财务审批");
        bean.setApprover("王朕");
        bean.setResult("通过");
        bean.setNumber(2);
        bean.setDate("2018-4-27");


        approvalProcessbeanList.add(0, bean);
        approvalProcessbeanList.add(1, bean1);
    }

    @Override
    protected void initViewEvent() {
        ivReimPic.setOnClickListener(this);
        tvPass.setOnClickListener(this);
        tvUnpass.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_reim_pic:
                //查看大图的报销单据照片

                break;
            case R.id.tv_approval_pass:
                //通过，先去签名数据
                approveResultId = true;
                submit();
                break;
            case R.id.tv_approval_unpass:
                //填写理由
                showReasonDialog();
                break;
            default:
                break;
        }
    }

    private void submit() {
        switch (level) {
//            case 0:
//                getPdfForm();
//                break;
//            case 1:
//                //
//                toDownLoadPdf();
//                break;
//            case 2:
//                // signVerifyP1();
//                signVerifyP1NoPin();
//                break;
//            case 3:
//                submitSignJsonstring();
//                break;
//            case 4:
//                submitApproval();
//                break;
            default:
                break;
        }
    }

    //填写reason Dialog

    private void showReasonDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请填写不通过理由");

// Set up the input
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
                approveResultId = false;
                if (TextUtils.isEmpty(reason)) {

                }
                submit();

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


    private void getData(int id) {
        Observable<List<WaitApprovalDetailBean>> observable = new ReimControl().getWaitApprovalDetail(id);
        CommonDialogObserver<List<WaitApprovalDetailBean>> observer = new CommonDialogObserver<List<WaitApprovalDetailBean>>(this) {
            @Override
            public void onNext(List<WaitApprovalDetailBean> list) {
                super.onNext(list);
                SimpleToast.toastMessage("获取成功", Toast.LENGTH_SHORT);
                if (list != null) {
                    approvalReimbeanList.clear();
                    approvalReimbeanList.addAll(list);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MyApprovalProcessActivity.this);


    }


}

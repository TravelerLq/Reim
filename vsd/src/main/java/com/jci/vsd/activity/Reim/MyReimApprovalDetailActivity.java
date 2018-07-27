package com.jci.vsd.activity.Reim;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.adapter.TimeLineAdapter;
import com.jci.vsd.adapter.reim.ApprovalDetailRecycleAdapter;
import com.jci.vsd.adapter.reim.MyReimRecycleAdapter;
import com.jci.vsd.bean.reim.ApprovalAllDetailBean;
import com.jci.vsd.bean.reim.MyReimDetailBean;
import com.jci.vsd.bean.reim.WaitApprovalDetailAllBean;
import com.jci.vsd.fragment.reim.ApprovedFragment;
import com.jci.vsd.fragment.reim.ApprovingFragment;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.BitmapUtil;
import com.jci.vsd.utils.FileUtils;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.StrTobaseUtil;
import com.jci.vsd.view.widget.DividerItemDecorationOld;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import me.iwf.photopicker.PhotoPreview;

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
    private Context context;
    private MyReimRecycleAdapter adapter;
    private LinearLayoutManager layoutReimManager;
    private LinearLayoutManager layoutTimeManager;

    private ArrayList<HashMap<String, Object>> timeLineItem;

    private List<MyReimDetailBean> approvalReimbeanList;

    private List<ApprovalAllDetailBean.ApprovalProcessVoAppArrayListBean> approvalProcessbeanList;
    private TimeLineAdapter timeLineAdapter;
    private int id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reim_approval_detail);
        context = MyReimApprovalDetailActivity.this;
        initViewEvent();
        layoutReimManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        layoutTimeManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        approvalReimbeanList = new ArrayList<>();
        approvalProcessbeanList = new ArrayList<>();
        timeLineItem = new ArrayList<>();
        initRecyApproval();
        initTimeLine();
        id = Integer.valueOf(getIntent().getStringExtra("id"));
        getData(id);

    }


    private void initRecyApproval() {
        rlApprovalDetail.setLayoutManager(layoutReimManager);
        adapter = new MyReimRecycleAdapter(context, approvalReimbeanList);
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


    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_reim_pic:
                //查看大图的报销单据照片

                break;
//            case R.id.tv_approving:
//
//                break;
            default:
                break;
        }
    }


    private void getData(int id) {
        Observable<WaitApprovalDetailAllBean> observable = new ReimControl().getWaitApprovalDetail(id);
        CommonDialogObserver<WaitApprovalDetailAllBean> observer = new CommonDialogObserver<WaitApprovalDetailAllBean>(this) {
            @Override
            public void onNext(WaitApprovalDetailAllBean bean) {
                super.onNext(bean);

                if (bean != null) {
                    //   SimpleToast.toastMessage("成功");

                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MyReimApprovalDetailActivity.this);


    }


}

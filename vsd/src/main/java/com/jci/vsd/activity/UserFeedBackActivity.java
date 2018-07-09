package com.jci.vsd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.feedback.FeedBackRequestBean;
import com.jci.vsd.bean.feedback.FeedBackResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.FeedBackApiControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Administrator on 2017/11/15 0015.
 * 用户反馈
 */

public class UserFeedBackActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    @BindView(R.id.feedBackList)
    PullToRefreshListView listView;
    @BindView(R.id.rightFucBtn)
    ImageButton rightFucBtn;

    private String userId;
    private int index = 1;
    private List<FeedBackResponseBean> list = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_feed_back_layout);
        initViewEvent();
        // 传入参数userId，
        userId = null;
        userId = VsdApplication.getInstance().getLoginResponseBean().getId();
        Loger.e("userId=----" + userId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        index = 1;
        getFeedBackInfo(userId);
    }

    //获取feedbackInfo
    private void getFeedBackInfo(final String userId) {
        //TODO pageNum pageSize 需自动获取
        Observable<List<FeedBackResponseBean>> obervable = new FeedBackApiControl().getFeedBack(new FeedBackRequestBean(userId,index+"","10000"));
        Observer<List<FeedBackResponseBean>> observer = new CommonDialogObserver<List<FeedBackResponseBean>>(UserFeedBackActivity.this) {
            @Override
            public void onNext(List<FeedBackResponseBean> feedBackResponseBean) {
                super.onNext(feedBackResponseBean);
                if(feedBackResponseBean.size() >0){
                    index++;
                    list.addAll(feedBackResponseBean);

                }
                listView.onRefreshComplete();
            }
        };
       RxHelper.bindOnUI(obervable,observer);
    }

    @Override
    protected void initViewEvent() {
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                getFeedBackInfo(userId);
            }
        });
        backBtn.setOnClickListener(this);
        rightFucBtn.setImageResource(R.drawable.feed_back);
        rightFucBtn.setOnClickListener(this);
        rightFucBtn.setVisibility(View.VISIBLE);
        titleTxt.setText(getResources().getString(R.string.my_feed_back));



    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rightFucBtn: {
                //toActivity(MeFeedBackActivity.class);

            }
            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VsdApplication.getRefWatcher(this).watch(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//        FeedBackResponseBean backResponseBean = (FeedBackResponseBean) adapterView.getItemAtPosition(i);
//        Intent intent = new Intent(UserFeedBackActivity.this, FeedBackDetailActivity.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(AppConstant.SERIAL_KEY,backResponseBean);
//        intent.putExtras(bundle);
//        startActivity(intent);
    }
}

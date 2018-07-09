package com.jci.vsd.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jci.vsd.R;
import com.jci.vsd.adapter.NoticeCenterAdapter;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.notice.NoticeRequestBean;
import com.jci.vsd.bean.notice.UnreadNoticeBean;
import com.jci.vsd.network.api.NoticeCenterApi;
import com.jci.vsd.network.control.NoticeCenterControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by liqing on 17/12/5.
 * 消息中心页面
 */

public class NoticeCenterActivity extends BaseActivity {
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    @BindView(R.id.feedBackList)
    PullToRefreshListView listView;
    @BindView(R.id.rightFucBtn)
    ImageButton rightFucBtn;
    private NoticeCenterAdapter adapter;
    private List<UnreadNoticeBean> beanList;
    private Context mContext;
    String userId;
    String status = "0";
    private String msgId;
    private String pageSize = "10";
    private String pageIndex = "1";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_center);
        titleTxt.setText("信息中心");
        mContext = NoticeCenterActivity.this;
        beanList = new ArrayList<>();
       // initTestData();
        adapter = new NoticeCenterAdapter(mContext, beanList);
        listView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NoticeCenterAdapter.OnItemClickListener() {
            @Override
            public void detail(int pos) {
                Loger.e("---click--pos=" + pos);
                msgId = beanList.get(pos).getMsgId();
                Loger.e("---click--msgId" + msgId);
                //设置已读
                if (beanList.get(pos) != null && !TextUtils.isEmpty(msgId)) {
                    setNoticeRead(msgId);
                    //传入Bundle对象
                    Intent intent = new Intent(mContext, NoticeCenterDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("bean", beanList.get(pos));
                    intent.putExtras(bundle);
                    startActivity(intent);

                } else {
                    Toast.makeText(mContext, "暂无详情", Toast.LENGTH_SHORT).show();
                }

            }
        });
        initViewEvent();
        userId = VsdApplication.getInstance().getLoginResponseBean().getId();
        Loger.e("uerId---" + userId);
        if (!TextUtils.isEmpty(userId)) {
            getNotice(userId, pageSize, pageIndex);
        }

    }

    //获取数据
    private void getNotice(String userId, String pageSize, String pageIndex) {
        Observable<List<UnreadNoticeBean>> observable = new NoticeCenterControl().getNotice(new NoticeRequestBean(userId, pageSize, pageIndex));
        Observer<List<UnreadNoticeBean>> observer = new CommonDialogObserver<List<UnreadNoticeBean>>(NoticeCenterActivity.this) {
            @Override
            public void onNext(List<UnreadNoticeBean> unreadNoticeBeanList) {
                super.onNext(unreadNoticeBeanList);
                beanList.clear();
                beanList.addAll(unreadNoticeBeanList);
              //  initTestData();
                adapter.notifyDataSetChanged();
            }
        };

        RxHelper.bindOnUI(observable, observer);
    }


    //设置已读
    private void setNoticeRead(String msgId) {
        Observable<Boolean> observable = new NoticeCenterControl().setNoticeRead(msgId);
        Observer<Boolean> observer = new CommonDialogObserver<Boolean>(NoticeCenterActivity.this) {
            @Override
            public void onNext(Boolean result) {
                super.onNext(result);
                if (result) {
                    Loger.e("setReadSuccess---");
                }
            }
        };
        RxHelper.bindOnUI(observable, observer);
    }

    private void initTestData() {
        UnreadNoticeBean bean =new UnreadNoticeBean();
        bean.setCreateDate("2017/12/23");
        bean.setStatus("0");
        bean.setMsgTitle("TestTitle1");
        bean.setMsgId("0");
        bean.setMsgContent(getString(R.string.no_feed_back_msg));
        UnreadNoticeBean bean1 =new UnreadNoticeBean();
        bean1.setCreateDate("2017/12/24");
        bean1.setStatus("1");
        bean1.setMsgTitle("TestTitle2");
        bean1.setMsgContent(getString(R.string.notice_content));
        bean1.setMsgId("1");
        beanList.add(0,bean);
        beanList.add(1,bean1);

    }

    @Override
    protected void initViewEvent() {
        backBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //返回后 重新刷新数据
        Loger.e("uerId---" + userId);
        if (!TextUtils.isEmpty(userId)) {
            getNotice(userId, pageSize, pageIndex);
        }

    }
}

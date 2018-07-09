package com.jci.vsd.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.notice.UnreadNoticeBean;
import com.jci.vsd.network.control.NoticeCenterControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;

import org.w3c.dom.Text;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by liqing on 17/12/5.
 * 消息中心详情
 */

public class NoticeCenterDetailActivity extends BaseActivity {
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    @BindView(R.id.rightFucBtn)
    ImageButton rightFuncBtn;
    @BindView(R.id.tv_title)
    TextView tvNoticeTitle;
    @BindView(R.id.tv_time)
    TextView tvNoticeTime;
    @BindView(R.id.tv_content)
    TextView tvNoticeContent;
    @BindView(R.id.tv_name)
    TextView tvName;
    private UnreadNoticeBean bean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_center_detail);
        titleTxt.setText("信息详情");
        initViewEvent();
        //
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            bean = (UnreadNoticeBean) bundle.getSerializable("bean");
            tvNoticeTitle.setText(bean.getMsgTitle());
            tvNoticeContent.setText(bean.getMsgContent());
            tvNoticeTime.setText(bean.getCreateDate());
            tvName.setText(bean.getReceiver());
        }

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
}

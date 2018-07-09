package com.jci.vsd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.login.PersonalInfoRequestBean;
import com.jci.vsd.bean.login.PersonalInformationResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.LoginApiControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Utils;

import org.w3c.dom.Text;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class UserInfoCenterActivity extends BaseActivity {
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    @BindView(R.id.modifyLayout)
    RelativeLayout modifyLayout;
    @BindView(R.id.nowUserTxt)
    TextView nowUserTxt;
    @BindView(R.id.nowVersionTxt)
    TextView nowVersionTxt;
    @BindView(R.id.nowConnectWayTxt)
    TextView nowConnectWayTxt;
    @BindView(R.id.unregisterBtn)
    Button unregisterBtn;
    @BindView(R.id.modifyCardLayout)
    RelativeLayout modifyCardLayout;
    @BindView(R.id.cardIdTxt)
    TextView cardIdTxt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_center_layout);
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        titleTxt.setText(getResources().getString(R.string.person_info_center));
        backBtn.setOnClickListener(this);
        modifyLayout.setOnClickListener(this);
        nowUserTxt.setText(VsdApplication.getInstance().getLoginResponseBean().getName());
        nowVersionTxt.setText(Utils.getVersionName());
        unregisterBtn.setOnClickListener(this);
        if (!TextUtils.isEmpty(VsdApplication.getInstance().getLoginResponseBean().getCardId())) {
            cardIdTxt.setText(getResources().getString(R.string.repeat_bind_persion_to_card));
        } else {
            cardIdTxt.setText(getResources().getString(R.string.bind_person_to_card));
        }
        modifyCardLayout.setOnClickListener(this);
        getPersonalInfor(VsdApplication.getInstance().getLoginResponseBean().getId());
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.modifyLayout:
                toActivity(ModifyPwdActivity.class);
                break;
            case R.id.unregisterBtn:
                VsdApplication.getInstance().removeAllActivity();
                toActivity(LoginActivity.class);
                break;
//            case R.id.modifyCardLayout:{
//                Intent intent = new Intent(UserInfoCenterActivity.this,CardBindActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(AppConstant.SERIAL_KEY,VsdApplication.getInstance().getLoginResponseBean());
//                intent.putExtras(bundle);
//                startActivity(intent);
//                finish();
//            }

        }
    }

    private void getPersonalInfor(String userId) {
        Observable<PersonalInformationResponseBean> observable = new LoginApiControl().getPersionInformation(new PersonalInfoRequestBean(userId));
        Observer<PersonalInformationResponseBean> observer = new CommonDialogObserver<PersonalInformationResponseBean>(UserInfoCenterActivity.this) {
            @Override
            public void onNext(PersonalInformationResponseBean personalInformationResponseBean) {
                super.onNext(personalInformationResponseBean);
                nowConnectWayTxt.setText(personalInformationResponseBean.getEmail());
            }
        };
        RxHelper.bindOnUI(observable, observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VsdApplication.getRefWatcher(this).watch(this);
    }
}

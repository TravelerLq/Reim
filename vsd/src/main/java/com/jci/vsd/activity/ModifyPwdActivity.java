package com.jci.vsd.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.login.UpdatePwdRequestBean;
import com.jci.vsd.network.control.LoginApiControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.view.widget.SimpleToast;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class ModifyPwdActivity extends BaseActivity {
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    @BindView(R.id.rightFucTxt)
    TextView rightFucTxt;
    @BindView(R.id.oldPwdEdit)
    EditText oldPwdEdit;
    @BindView(R.id.newPwdEdit)
    EditText newPwdEdit;
    @BindView(R.id.newPwdAgainEdit)
    EditText newPwdAgainEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd_layout);
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.modify_pwd));
        rightFucTxt.setText(getResources().getString(R.string.save));
        rightFucTxt.setVisibility(View.VISIBLE);
        rightFucTxt.setOnClickListener(this);
        rightFucTxt.setBackgroundResource(R.drawable.select_btn_back_click_xml);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.rightFucTxt: {
                checkPwd();
            }
                break;
        }
    }

    private void checkPwd(){
        String oldPwd = oldPwdEdit.getText().toString().trim();
        String newPwd = newPwdEdit.getText().toString().trim();
        String newAgainPwd = newPwdAgainEdit.getText().toString().trim();
        if(TextUtils.isEmpty(oldPwd)){
            SimpleToast.ToastMessage(R.string.please_input_old_pwd);
            return;
        }
        if(TextUtils.isEmpty(newPwd)){
            SimpleToast.ToastMessage(R.string.please_input_new_pwd);
            return;
        }

        if(TextUtils.isEmpty(newAgainPwd)){
            SimpleToast.ToastMessage(R.string.please_input_new_pwd_again);
            return;
        }

        if(!newAgainPwd.equals(newPwd)){
            SimpleToast.ToastMessage(R.string.pwd_not_same);
            return;
        }
        updatePwd(VsdApplication.getInstance().getLoginResponseBean().getId(),oldPwd,newAgainPwd);
    }

    private void updatePwd(String userId,String oldPwd,String newPwd){
        Observable<Boolean> observable = new LoginApiControl().updatePersonlPwd(new UpdatePwdRequestBean(userId,oldPwd,newPwd));
        Observer<Boolean> observer = new CommonDialogObserver<Boolean>(ModifyPwdActivity.this){
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if(aBoolean){
                    SimpleToast.ToastMessage(R.string.update_pwd_success);
                    VsdApplication.getInstance().removeAllActivity();
                    toActivity(LoginActivity.class);
                    finish();
                }
            }
        };
        RxHelper.bindOnUI(observable,observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VsdApplication.getRefWatcher(this).watch(this);
    }
}

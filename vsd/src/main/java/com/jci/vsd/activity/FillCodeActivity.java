package com.jci.vsd.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.network.control.FillcodeApiControl;
import com.jci.vsd.network.control.LoginApiControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.view.widget.SimpleToast;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/6/26.
 * 填写邀请码页面
 */

public class FillCodeActivity extends BaseActivity {
    @BindView(R.id.edt_verify_code)
    EditText edtCode;
    @BindView(R.id.btn_sure_code)
    Button btnSure;
    private String codeStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_code);
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        btnSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.btn_sure_code) {
            Log.e("--btnsure--click", "--");
            codeStr = edtCode.getText().toString().trim();
            if (TextUtils.isEmpty(codeStr)) {
                SimpleToast.ToastMessage("请填写邀请码！");
            } else {
                //

                submitCode(codeStr);
                // toActivityWithType(RegisterActivity.class, "user");
            }
        }
    }

    private void submitCode(String codeStr) {
        Observable<String> observable = new FillcodeApiControl().submitCode(codeStr);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                if (s.equals("200")) {
                    SimpleToast.toastMessage("加入公司成功", Toast.LENGTH_LONG);
                    toActivityWithType(RegisterActivity.class,"3");
                    finish();
                    //去注册申请
                } else if (s.equals("50002")) {
                    SimpleToast.toastMessage("邀请码过期，请重新填写", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, FillCodeActivity.this);

    }


}

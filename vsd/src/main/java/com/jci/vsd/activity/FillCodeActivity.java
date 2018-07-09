package com.jci.vsd.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jci.vsd.R;
import com.jci.vsd.view.widget.SimpleToast;

import butterknife.BindView;

/**
 * Created by liqing on 18/6/26.
 */

public class FillCodeActivity extends BaseActivity {
    @BindView(R.id.edt_verify_code)
    EditText edtCode;
    @BindView(R.id.btn_sure_code)
    Button btnSure;

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
            String codeStr = edtCode.getText().toString().trim();
            if (TextUtils.isEmpty(codeStr)) {
                SimpleToast.ToastMessage("请填写邀请码！");
            } else {
                toActivity(RegisterActivity.class);
            }
        }
    }
}

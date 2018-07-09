package com.jci.vsd.activity.UserInfo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;

import org.w3c.dom.Text;

import butterknife.BindView;

/**
 * Created by liqing on 18/7/9.
 * 版本信息
 */

public class VersionInfoActivity extends BaseActivity {
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_current_version)
    TextView tvCurrentVersion;

    @BindView(R.id.tv_last_version)
    TextView tvLastVersion;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_info);
        getVersion();
    }

    private void getVersion() {
    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.tv_sure) {
            //更新

        }
    }
}

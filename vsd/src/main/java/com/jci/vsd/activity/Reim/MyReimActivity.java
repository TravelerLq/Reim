package com.jci.vsd.activity.Reim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.fragment.reim.ApprovedFragment;
import com.jci.vsd.fragment.reim.ApprovingFragment;

import butterknife.BindView;

/**
 * Created by liqing on 18/6/28.
 * 我的报销UI 有审批中＋已经审批 2个Tab (2 fragment)
 */

public class MyReimActivity extends BaseActivity {

    @BindView(R.id.tv_approving)
    TextView tvApproving;
    @BindView(R.id.tv_approved)
    TextView tvApproved;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    private ApprovingFragment approvingFragment;
    private ApprovedFragment approvedFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reim);
        initViewEvent();
        initApprovingFragment();

    }

    @Override
    protected void initViewEvent() {

        tvApproving.setOnClickListener(this);
        tvApproved.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_approved:
                initApprovedFragment();
                break;
            case R.id.tv_approving:
                initApprovingFragment();
                break;
            default:
                break;
        }
    }

    private void initApprovingFragment() {
        if (approvingFragment == null) {
            approvingFragment = new ApprovingFragment();
        }
        addFragmentNotToStack(R.id.frame_content, approvingFragment);
        //tvStoreCombine.setBackground(getResources().getDrawable(R.drawable.bg_combine_select));
        tvApproving.setBackgroundColor(getResources().getColor(R.color.red_rec_));
        tvApproved.setBackgroundColor(getResources().getColor(R.color.grey_b5b5));
        tvApproving.setTextColor(getResources().getColor(R.color.white));
        tvApproved.setTextColor(getResources().getColor(R.color.white));
    }

    private void initApprovedFragment() {
        if (approvedFragment == null) {
            approvedFragment = new ApprovedFragment();
        }
        addFragmentNotToStack(R.id.frame_content, approvedFragment);
        //tvStoreCombine.setBackground(getResources().getDrawable(R.drawable.bg_combine_select));
        tvApproved.setBackgroundColor(getResources().getColor(R.color.red_rec_));
        tvApproving.setBackgroundColor(getResources().getColor(R.color.grey_b5b5));
        tvApproved.setTextColor(getResources().getColor(R.color.white));
        tvApproving.setTextColor(getResources().getColor(R.color.white));
    }
}

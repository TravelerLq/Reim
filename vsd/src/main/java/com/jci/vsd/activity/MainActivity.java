package com.jci.vsd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jci.vsd.R;

import com.jci.vsd.activity.UserInfo.UserInfoActivity;
import com.jci.vsd.adapter.PageAdapter;
import com.jci.vsd.fragment.EnterpriseHomeFragment;
import com.jci.vsd.fragment.HelpFragment;
import com.jci.vsd.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.leibnik.wechatradiobar.WeChatRadioGroup;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_home)
    WeChatRadioGroup radioGroupHome;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    private PageAdapter pageAdapter;
    @BindView(R.id.button_back)
    ImageButton buttonBack;

    @BindView(R.id.tv_header)
    TextView tvHeader;
    @BindView(R.id.textview_title)
    TextView tvTitle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tvTitle.setText(getResources().getString(R.string.home_title));
        List<Fragment> list = new ArrayList<Fragment>();
        list.add(new HomeFragment());
        list.add(new HelpFragment());
        list.add(new EnterpriseHomeFragment());

        pageAdapter = new PageAdapter(getSupportFragmentManager(), list);
        vpHome.setAdapter(pageAdapter);
        radioGroupHome.setViewPager(vpHome);
        initViewEvent();
        buttonBack.setVisibility(View.GONE);
        tvHeader.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //getSupportFragmentManager().putFragment(outState, "mContent", mContent);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.topButton:
//                toggle();
//                break;
//            case R.id.scanBtn:
//              //  startActivity(new Intent(MainActivity.this,MaterialScanInputActivity.class));
//                break;
//            case R.id.recycleBtn:
//               // startActivity(new Intent(MainActivity.this,StoreMaterialActivity.class));
//                break;
            case R.id.tv_header:
                toActivity(UserInfoActivity.class);
//                toActivity(UserInfoCenterActivity.class);
                break;

            default:
                break;
        }
    }

    @Override
    protected void initViewEvent() {
        tvHeader.setOnClickListener(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }
}

package com.jci.vsd.activity.Reim;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.utils.ImageCaptureManager;
import com.jci.vsd.utils.Loger;

import org.spongycastle.jcajce.provider.symmetric.ARC4;

import java.io.IOException;

import butterknife.BindInt;
import butterknife.BindView;

/**
 * Created by liqing on 18/6/26.
 */

public class ReimHomeActivity extends BaseActivity {


    private static final int REQUEST_CODE_TAKE_PIC = 1111;
    @BindView(R.id.iv_home_pic)
    ImageView ivHomePic;
    @BindView(R.id.iv_invoicing_verify)
    ImageView ivInvocingVerify;
    @BindView(R.id.iv_add_expense)
    ImageView ivAddEpense;

    @BindView(R.id.rl_my_expense)
    RelativeLayout rlMyExpense;
    @BindView(R.id.rl_my_approval)
    RelativeLayout rlMyApproval;


    private ImageCaptureManager captureManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reim_home);
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        ivHomePic.setOnClickListener(this);
        ivInvocingVerify.setOnClickListener(this);
        ivAddEpense.setOnClickListener(this);

        rlMyExpense.setOnClickListener(this);
        rlMyApproval.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.iv_add_expense:
                Loger.e("----ivAdd--click");
                toActivity(ReimRecycActivity.class);
                // getFormId();
//                Intent it = new Intent(getActivity(), AddExpenseItemActivtity.class);
//                // it.putExtra("type", "home");
//                startActivity(it);

                break;
            case R.id.iv_home_pic:
//                PhotoPicker.builder()
//                        .setPhotoCount(1)
//                        .setShowCamera(true)
//                        .setPreviewEnabled(false)
//                        .setSelected(selectedPhotos)
//                        .start(HomeFragment.this.getActivity());
                //拍照
                takePic(REQUEST_CODE_TAKE_PIC, ReimHomeActivity.this);

                //   toActivity(HomeFragment.this.getActivity(), TestActivity.class);
                break;
            case R.id.rl_my_approval:
                //审批
                toActivity(MyApprovalRecyActivity.class);
                //Intent intentApproval = new Intent(HomeFragment.this.getActivity(), ApprovalProcessRecyActvity.class);
                // startActivity(intentApproval);
                break;

            case R.id.rl_my_expense:
                //我的报销
                toActivity(MyReimActivity.class);
//                Intent intent = new Intent(HomeFragment.this.getActivity(), MyExpenseProcessActivity.class);
//                startActivity(intent);
////                Intent intent = new Intent(HomeFragment.this.getActivity(), ExpenseItemListActivity.class);
//                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void takePic(final int requestCode, final Context context) {


        if (PermissionsUtil.hasPermission(context, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {

            openCamera(requestCode, context);
        } else {
            PermissionsUtil.requestPermission(ReimHomeActivity.this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permission) {
                    Log.e("--", "permissionGranted: 用户授予了访问外部存储的权限");
                    openCamera(requestCode, ReimHomeActivity.this);
                }

                @Override
                public void permissionDenied(@NonNull String[] permission) {
                    Log.e("--", "permissionDenied: 用户拒绝了访问外部存储的申请");
                    // needPermissionTips();

                }
            }, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE});
        }
    }


    private void openCamera(int requestCode, Context context) {
        captureManager = new ImageCaptureManager(context);
        try {
            Intent intent = captureManager.dispatchTakePictureIntent();
            startActivityForResult(intent, requestCode);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {

            e.printStackTrace();
        }
    }
}

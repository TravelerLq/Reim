package com.jci.vsd.activity.Reim;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.bean.reim.WaitApprovalDetailAllBean;
import com.jci.vsd.bean.reim.WaitApprovalDetailBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.BitmapUtil;
import com.jci.vsd.utils.StrTobaseUtil;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by liqing on 18/7/27.
 * 我的审批－审批详情—审批单的票据原件
 */

public class WaitApprovalPicActivity extends BaseActivity {
    @BindView(R.id.iv_view_big_pic)
    ImageView ivPic;
    private int id;
    private String picPath;
    private List<String> selectPic;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        selectPic = new ArrayList<>();
        WaitApprovalDetailBean bean = (WaitApprovalDetailBean) getIntent()
                .getExtras().getSerializable(AppConstant.SERIAL_KEY);
        if (bean != null) {
            id = bean.getId();
        }
        getpic(id);
        initViewEvent();
    }

    @Override
    protected void initViewEvent() {
        ivPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.iv_view_big_pic) {
            viewBigPic();
        }
    }

    private void viewBigPic() {

        selectPic.add(picPath);
        PhotoPreview.builder()
                .setPhotos((ArrayList) selectPic)
                .setShowDeleteButton(false)
                .start(WaitApprovalPicActivity.this, PhotoPreview.REQUEST_CODE);
    }

    private void getpic(int id) {

        Observable<ReimPicBean> observable = new ReimControl().getReimPic(id);
        CommonDialogObserver<ReimPicBean> observer = new CommonDialogObserver<ReimPicBean>(this) {
            @Override
            public void onNext(ReimPicBean bean) {
                super.onNext(bean);
                SimpleToast.toastMessage("获取成功", Toast.LENGTH_SHORT);
                if (bean != null) {
                    String base64Code = bean.getBytes();
                    String picName = bean.getName();
                    Bitmap bitmap = StrTobaseUtil.base64ToBitmap(base64Code);
                    //   picPath = BitmapUtil.saveBitmapToSDCard(bitmap, System.currentTimeMillis() + ".jpg");
                    picPath = BitmapUtil.saveBitmapToSDCard(bitmap, picName);
                    ivPic.setImageBitmap(bitmap);
                    selectPic.add(picPath);


                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, WaitApprovalPicActivity.this);


    }
}

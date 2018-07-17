package com.jci.vsd.activity.UserInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.activity.MeFeedBackActivity;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.network.control.LoginApiControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.CircleImageView;

import org.spongycastle.LICENSE;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by liqing on 18/7/3.
 */

public class UserInfoActivity extends BaseActivity {

    private ArrayList<String> selectedPhotos;

    @BindView(R.id.project_master_head)
    CircleImageView headerImage;

    @BindView(R.id.ll_help)
    LinearLayout llHelp;

    @BindView(R.id.ll_feedback)
    LinearLayout llFeedBack;

    @BindView(R.id.ll_version)
    LinearLayout llVersion;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    @BindView(R.id.ll_exit)
    LinearLayout llExit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        selectedPhotos = new ArrayList<>();

        initViewEvent();
        //
    }
/*
也需要从网络加载各种尺寸
If the thumbnail needs to load the same full-resolution image over the network, it might not be faster at all.
 */

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.project_master_head:
                chooseImage();

                break;
            case R.id.ll_help:
                break;

            case R.id.ll_feedback:
                toActivity(MeFeedBackActivity.class);
                break;
            case R.id.ll_version:
                toActivity(VersionInfoActivity.class);
                break;
            case R.id.ll_about:
                break;
            case R.id.ll_exit:
                //退出
                warningDialog("确认退出登录？");
                break;
            default:
                break;
        }
    }

    String thumbnailStr = "http://i.imgur.com/DvpvklR.png";

    public void loadThumbnailImageRequest() {
        //setup Glide request without the into()
        RequestBuilder<Drawable> thumbnailRequest = Glide.with(VsdApplication.getContext())
                .load(headerImage);

        //pass the request as  the parameter to the thumbnail request
        Glide.with(UserInfoActivity.this)
                .load(headerImage)
                .thumbnail(thumbnailRequest)
                .into(headerImage);
    }


    private void chooseImage() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setSelected(selectedPhotos)
                .start(UserInfoActivity.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                selectedPhotos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                String path = selectedPhotos.get(0);
                //
                Glide.with(UserInfoActivity.this)
                        .load(path)

                        .into(headerImage);
            }
        }
    }

    @Override
    protected void initViewEvent() {
        headerImage.setOnClickListener(this);
        llHelp.setOnClickListener(this);
        llVersion.setOnClickListener(this);
        llAbout.setOnClickListener(this);
        llFeedBack.setOnClickListener(this);
        llExit.setOnClickListener(this);
    }

    @Override
    protected void warningDialog(String message) {
        new AlertDialog.Builder(UserInfoActivity.this)
                .setTitle(getResources().getString(R.string.notice))
                .setMessage(message)
                .setPositiveButton(getResources().getString(R.string.sure), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginOut();
                        exit();
                       // toActivity(LoginActivity.class);

                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();
    }

    private void loginOut() {
        Observable<String> observable = new LoginApiControl().loginOut();
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.e("--s=" + s);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, UserInfoActivity.this);
    }

    /*
     RecycleBin机制 缓存机制
     所有继承AbListview子类（listview和 gridview）都是这种机制
     */
    int mFirstActivePosition;
    View[] mActiveViews;

    public View getActiveViews(int pos) {
        int index = pos - mFirstActivePosition;

        final View[] activeViews = mActiveViews;
        if (index >= 0 && index < activeViews.length) {
            final View match = activeViews[index];
            return match;
        }

        return null;
    }

//    View getActiveView(int position) {
//        int index = position - mFirstActivePosition;
//        final View[] activeViews = mActiveViews;
//        if (index >= 0 && index < activeViews.length) {
//            final View match = activeViews[index];
//            activeViews[index] = null;
//            return match;
//        }
//        return null;
//    }


}

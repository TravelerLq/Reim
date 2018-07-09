package com.jci.vsd.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.jci.vsd.utils.Loger;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.util.ArrayList;

/**
 * Created by liqing on 17/11/17.
 * 从相册多选照片
 */

public class MultiSelectActivity extends TakePhotoActivity {

    TakePhoto takePhoto;
    private int limit = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhoto = getTakePhoto();
        if (getIntent() != null) {
            Loger.e("----intent!=null");
            Bundle bundle = getIntent().getExtras();
        //    limit = bundle.getInt(ToFeedBackActivity.SELECT_PIC_NUM);
//            limit=Integer.parseInt(data);
            Loger.e("MultiSelectActivity---limit=" + limit);
        }
        takePhoto.onPickMultiple(limit);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        Loger.e("---showImg=size=" + images.size());
        Intent mIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", images);
        mIntent.putExtras(bundle);
        // 设置结果，并进行传送
        this.setResult(Activity.RESULT_OK, mIntent);
        this.finish();

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        finish();
    }
}

package com.jci.vsd.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.adapter.PicAdapater;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.PicBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.FeedBackApiControl;
import com.jci.vsd.network.control.UploadProgressListener;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.ImageUtils;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.TimeUtils;

import org.w3c.dom.Text;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class MeFeedBackActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    @BindView(R.id.picGridView)
    GridView picGridView;
    @BindView(R.id.submitBtn)
    Button submitBtn;
    @BindView(R.id.feedBackTimeTxt)
    TextView feedbackTime;
    private PicAdapater picAdapater;
    private static final int PICK_PHOTO = 10002;
    List<PicBean> picBeanList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_feed_back_layout);
        picBeanList.add(new PicBean());
        initViewEvent();
        feedbackTime.setText(TimeUtils.getCurrentTime());
    }

    @Override
    protected void initViewEvent() {
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.me_feed_back));
        picAdapater = new PicAdapater(MeFeedBackActivity.this, picBeanList);
        picGridView.setAdapter(picAdapater);
        picGridView.setOnItemClickListener(this);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subFile(picBeanList.get(0).getPhotoPath(), uploadProgressListener);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long l) {
        Loger.i("onItemClick === " + postion);
        if ((postion == picBeanList.size() - 1) && (picBeanList.size() <= 10) && (picBeanList.get(postion).getStatus() == 0)) {
            takePicFromPhoto();
        } else {
            Intent intent = new Intent(MeFeedBackActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(AppConstant.SERIAL_KEY, picBeanList.get(postion).getBitmap());
            intent.putExtras(bundle);
            startActivity(intent);
            // toAtivityWithParams(FeedBackBigPicActivity.class,picBeanList.get(postion));
        }
    }

    private void takePicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_PHOTO:
                    getPicPathArray(data);
                    break;
            }
        }
    }

    private List<PicBean> getPicPathArray(final Intent data) {
        Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                //data中自带有返回的uri
                Uri photoUri = data.getData();
                //获取照片路径
                String[] filePathColumn = {MediaStore.Audio.Media.DATA};
                Cursor cursor = getContentResolver().query(photoUri, filePathColumn, null, null, null);
                int index = 0;
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    String photoPath = cursor.getString(cursor.getColumnIndex(filePathColumn[index]));
                    Bitmap bitmap = ImageUtils.getImageThumbnail(photoPath, 200, 200);
                    PicBean picBean = new PicBean(1, bitmap);
                    picBean.setPhotoPath(photoPath);
                    picBeanList.add(picBeanList.size() - 1, picBean);
                }
                if (picBeanList.size() >= 10) {
                    picBeanList.remove(picBeanList.size() - 1);
                }
                cursor.close();
                e.onNext(true);
                e.onComplete();
            }
        });

        Observer<Boolean> observer = new CommonDialogObserver<Boolean>(MeFeedBackActivity.this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    picAdapater.notifyDataSetChanged();
                }
            }
        };
        RxHelper.bindOnUI(observable, observer);
        return picBeanList;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VsdApplication.getRefWatcher(this).watch(this);
    }

    @Override
    public void onClick(View view) {
        Loger.i("onclicl");
        super.onClick(view);
        switch (view.getId()) {
            case R.id.submitBtn:
                subFile(picBeanList.get(0).getPhotoPath(), uploadProgressListener);
                break;
        }
    }

    private void subFile(String path, UploadProgressListener uploadProgressListener) {
        File file = new File(path);
        Loger.i("subFile = " + path);
        Observable<String> observable = new FeedBackApiControl().submitPic(file, uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(MeFeedBackActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.i("subFile" + s);
            }
        };
        RxHelper.bindOnUI(observable, observer);
    }

    UploadProgressListener uploadProgressListener = new UploadProgressListener() {
        @Override
        public void onProgress(long currentBytesCount, long totalBytesCount) {
            Loger.i("uploadProgressListener = " + currentBytesCount);
        }
    };
}

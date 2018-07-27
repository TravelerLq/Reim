package com.jci.vsd.activity.Reim;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.MainActivity;
import com.jci.vsd.bean.reim.ReimAddResponseBean;
import com.jci.vsd.bean.reim.ReimDocSubmitBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.data.ReimAddData;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.BitmapUtil;
import com.jci.vsd.utils.FileUtils;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.StrTobaseUtil;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.unitid.spark.cm.sdk.business.SignatureP1Service;
import cn.unitid.spark.cm.sdk.common.DataProcessType;
import cn.unitid.spark.cm.sdk.data.response.DataProcessResponse;
import cn.unitid.spark.cm.sdk.exception.CmSdkException;
import cn.unitid.spark.cm.sdk.listener.ProcessListener;
import io.reactivex.Observable;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by liqing on 18/6/28.
 * 生成单据、并提交
 */

public class ReimDocSubmitActivtiy extends BaseActivity {

    @BindView(R.id.iv_reim_doc)
    ImageView ivReimDoc;
    @BindView(R.id.btn_reim_submit)
    Button btnReimDocSubmit;
    private String base64Code;
    private String picPath;
    private String cert;
    private String sign;
    private int id;
    private List<String> selectPic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reim_submit);
        initViewEvent();
        ReimAddResponseBean getIntentBean = (ReimAddResponseBean) getIntent().getExtras().getSerializable(AppConstant.SERIAL_KEY);
        if (getIntentBean != null) {
            id = getIntentBean.getId();
            base64Code = getIntentBean.getBytes();
            Bitmap bitmap = StrTobaseUtil.base64ToBitmap(base64Code);
            //bitmap to png

            picPath = BitmapUtil.saveBitmapToSDCard(bitmap, System.currentTimeMillis() + ".jpg");
            ivReimDoc.setImageBitmap(bitmap);

            Loger.e("bitmap--save to Pic" + picPath);
//            originalBoxPicList.clear();
//            originalBoxPicList.add(0, picPath);


//                    方式一.  Drawable drawable=new BitmapDrawable(Bitmap);
//                    Glide.with(mContext).load(drawable).into(ivImage);
            //2.
//                    byte[] decodedString = Base64.decode(person_object.getPhoto(),Base64.NO_WRAP);
//                    InputStream inputStream  = new ByteArrayInputStream(decodedString);
//                    Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
//                    user_image.setImageBitmap(bitmap);
            // ivPicTest.setImageBitmap(bitmap);
            // ivReimDoc.setImageBitmap(bitmap);
//                    Drawable drawable = new BitmapDrawable(
        }

    }

    @Override
    protected void initViewEvent() {
        ivReimDoc.setOnClickListener(this);
        btnReimDocSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.btn_reim_submit:

                String hashFile = null;
                try {
                    hashFile = FileUtils.getMD5Checksum(picPath);
                    signVerifyP1(hashFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Loger.e("--hashFile--" + hashFile);
//                signVerifyP1(hashFile);
                break;
            //提交单据 （先签名添加，成功后－再提交）
            case R.id.iv_reim_doc:

                selectPic = new ArrayList<>();
                selectPic.add(picPath);
                PhotoPreview.builder()
                        .setPhotos((ArrayList) selectPic)
                        .setShowDeleteButton(false)
                        .start(ReimDocSubmitActivtiy.this, PhotoPreview.REQUEST_CODE);
                //查看单据大图
                break;
            default:
                break;


        }
    }

    //签名验证
    private void signVerifyP1(final String base64Code1) {
//        pdu.showpd();

        String plantext = base64Code1;


        ReimDocSubmitActivtiy.this.getIntent().putExtra("data", plantext);
        ReimDocSubmitActivtiy.this.getIntent().putExtra("type", DataProcessType.SIGNATURE_P1.name());
        SignatureP1Service signatureP1Service = new SignatureP1Service(ReimDocSubmitActivtiy.this, "1234", new ProcessListener<DataProcessResponse>() {
            @Override
            public void doFinish(DataProcessResponse dataProcessResponse, String certificate) {

//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                if (dataProcessResponse.getRet() == 0) {
                    Log.e("密钥", "= " + dataProcessResponse.getResult());
                    // spu.setCertKey(dataProcessResponse.getResult());
                    //获得就是签名证书
                    Log.e("cert", "= " + certificate);
                    cert = certificate;
                    sign = dataProcessResponse.getResult();
                    // spu.setCert(certificate);
                    ReimDocSubmitBean bean = new ReimDocSubmitBean();
                    bean.setCer(cert);
                    bean.setSign(sign);
                    bean.setId(id);
                    submitReimDoc(bean);
                    //去提交单据
                    // submitSignJsonstring();
                } else {
//                    if (pdu.getMypDialog().isShowing()) {
//                        pdu.dismisspd();
//                    }
                    Toast.makeText(ReimDocSubmitActivtiy.this, "文件签名失败" + dataProcessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void doException(CmSdkException e) {
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                Toast.makeText(ReimDocSubmitActivtiy.this, "文件签名失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitReimDoc(ReimDocSubmitBean bean) {
        Observable<ReimAddResponseBean> observable = new ReimControl().submitReimDoc(bean);
        CommonDialogObserver<ReimAddResponseBean> observer = new CommonDialogObserver<ReimAddResponseBean>(this) {
            @Override
            public void onNext(ReimAddResponseBean reimAddResponseBean) {
                super.onNext(reimAddResponseBean);
                SimpleToast.toastMessage("提交成功。", Toast.LENGTH_SHORT);
                ReimAddData.removeReimList();
                if (reimAddResponseBean != null) {
                    int formId = reimAddResponseBean.getId();
                    Loger.e("formIdFormNet--" + formId);
                    MySpEdit.getInstance().setFormId(formId);
                    Loger.e("formIdFormShare--" + MySpEdit.getInstance().getFormId());

//                    Thread closeActivity = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Thread.sleep(3000);
//                                // Do some stuff
//                            } catch (Exception e) {
//                                e.getLocalizedMessage();
//                            }
//                        }
//                    });
//                    closeActivity.start();
                    finish();
                    toActivity(MainActivity.class);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ReimDocSubmitActivtiy.this);
    }

}

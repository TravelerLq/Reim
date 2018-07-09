package com.jci.vsd.activity.contract;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.Reim.ReimAddActivity;
import com.jci.vsd.activity.Reim.ReimHomeActivity;
import com.jci.vsd.adapter.GridContractAddImageAdapter;
import com.jci.vsd.utils.CompressUtil;
import com.jci.vsd.utils.DateUtils;
import com.jci.vsd.utils.FileToBase64Util;
import com.jci.vsd.utils.ImageCaptureManager;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.ScreenUtil;
import com.jci.vsd.utils.TimePickerUtils;
import com.jci.vsd.utils.TimeUtils;
import com.jci.vsd.view.widget.ScrollGridView;
import com.jci.vsd.view.widget.SimpleToast;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.unitid.spark.cm.sdk.business.SignatureP1Service;
import cn.unitid.spark.cm.sdk.common.DataProcessType;
import cn.unitid.spark.cm.sdk.data.response.DataProcessResponse;
import cn.unitid.spark.cm.sdk.exception.CmSdkException;
import cn.unitid.spark.cm.sdk.listener.ProcessListener;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

/**
 * Created by liqing on 18/7/4.
 * 合同添加
 */

public class ContractAddActivtiy extends BaseActivity {

    private static final int REQUEST_CODE_TAKE_PIC = 1121;
    private ImageCaptureManager captureManager;

    private ArrayList<String> selectedPhotos;
    private ArrayList<String> photos;
    private String path;
    private Uri uri;

    Boolean isValid;
    @BindView(R.id.tv_contract_type)
    TextView tvContractType;

    @BindView(R.id.tv_invocing_way)
    TextView tvInvocingWay;
    @BindView(R.id.tv_pay_way)
    TextView tvPayWay;

    @BindView(R.id.edt_pay_progress)
    EditText edtPayProgress;

    @BindView(R.id.tv_contract_start_time)
    TextView tvContractStartTime;

    @BindView(R.id.tv_contract_end_time)
    TextView tvContractEndTime;

    @BindView(R.id.scroll_grid)
    ScrollGridView gridView;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    private GridContractAddImageAdapter mAdapter;
    private List<String> picList;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract_add);
        context = ContractAddActivtiy.this;
        picList = new ArrayList<>();
        selectedPhotos = new ArrayList<>();
        try {
            Boolean result = TimeUtils.compare("2014-1-2", "2013-2-4");
            Loger.e("--result--" + result);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        initViewEvent();
        mAdapter = new GridContractAddImageAdapter(picList, context);
        gridView.setAdapter(mAdapter);
        mAdapter.setAddAndDeleteListener(new GridContractAddImageAdapter.AddAndDeleteListener() {

            @Override
            public void addImag() {
                takePics();
            }

            @Override
            public void viewImag(int pos) {
                PhotoPreview.builder()
                        .setPhotos((ArrayList) picList)
                        .setCurrentItem(pos)
                        .setShowDeleteButton(false)
                        .start(ContractAddActivtiy.this, PhotoPreview.REQUEST_CODE);

            }

            @Override
            public void deletImg(int pos) {
                picList.remove(pos);
                mAdapter.notifyDataSetChanged();

            }
        });

    }


    //收到照片后处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                //System.out.println("********"+photos.get(0)+"********");
            }

            selectedPhotos.clear();


            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            path = selectedPhotos.get(0);
            Loger.e("path--" + (new File(selectedPhotos.get(0)).length()));
            uri = Uri.fromFile(new File(selectedPhotos.get(0)));
//            //图片压缩：
//            Glide.with(ContractAddActivtiy.this)
//                    .load(uri)
//                    .thumbnail(0.1f)
//                    .into(ivReimPic);
//
            File outFile = CompressUtil.compressImage(path,
                    ScreenUtil.getInstance().getDisplayWidth(),
                    ScreenUtil.getInstance().getDisplayWidth());
            Loger.e("--outFile.length()--" + outFile.length());
            picList.add(path);
            mAdapter.notifyDataSetChanged();

            try {
//
                String base64CodePic = FileToBase64Util.encodeBase64File(outFile.getPath());


                //  signVerifyP1(base64CodePic);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //签名验证
    private void signVerifyP1(final String base64Code1) {
//        pdu.showpd();

        String plantext = base64Code1;
//        base64Code = base64Code1;

        ContractAddActivtiy.this.getIntent().putExtra("data", plantext);
        ContractAddActivtiy.this.getIntent().putExtra("type", DataProcessType.SIGNATURE_P1.name());
        SignatureP1Service signatureP1Service = new SignatureP1Service(ContractAddActivtiy.this, "1234", new ProcessListener<DataProcessResponse>() {
            @Override
            public void doFinish(DataProcessResponse dataProcessResponse, String certificate) {
                Log.e("doFinish---", "= ");
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                if (dataProcessResponse.getRet() == 0) {
                    Log.e("密钥", "= " + dataProcessResponse.getResult());
                    //  spu.setCertKey(dataProcessResponse.getResult());
                    //获得就是签名证书
                    Log.e("cert", "= " + certificate);
                    //  spu.setCert(certificate);

                    //此处添加 GridView照片
                    picList.add(path);
                    mAdapter.notifyDataSetChanged();
//                    Glide.with(ReimAddActivity.this)
//                            .load(uri)
//                            .thumbnail(0.1f)
//                            .into(ivReimPic);


                } else {
//                    if (pdu.getMypDialog().isShowing()) {
//                        pdu.dismisspd();
//                    }
                    Loger.e("dataProcessResponse.getRet() !=0");
                    Toast.makeText(ContractAddActivtiy.this, "图片签名失败" + dataProcessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void doException(CmSdkException e) {
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                SimpleToast.toastMessage("图片签名失败" + e.getMessage(), Toast.LENGTH_LONG);
                //Toast.makeText(AddExpenseItemActivtity.this, "图片签名失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void takePics() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setPreviewEnabled(false)
                .setSelected(selectedPhotos)
                .start(ContractAddActivtiy.this);
    }

    @Override
    protected void initViewEvent() {
        tvContractType.setOnClickListener(this);
        tvInvocingWay.setOnClickListener(this);
        tvPayWay.setOnClickListener(this);
        tvContractStartTime.setOnClickListener(this);
        tvContractEndTime.setOnClickListener(this);
        tvSure.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_contract_type:
                select(R.array.contract_type, tvContractType);
                break;
            case R.id.tv_invocing_way:
                select(R.array.invoicing_type, tvInvocingWay);
                break;
            case R.id.tv_pay_way:
                select(R.array.pay_way, tvPayWay);
                break;
            case R.id.tv_contract_start_time:
//              时间
                TimePickerUtils.getInstance().onYearMonthDayPickerText
                        (context, tvContractStartTime);
                break;
            case R.id.tv_contract_end_time:
//              时间
                TimePickerUtils.getInstance().onYearMonthDayPickerText
                        (context, tvContractEndTime);
                break;
            case R.id.tv_sure:
                //  checkData();
                checktime();
                break;
            default:
                break;
        }
    }


    private void checktime() {
        String notEmpty = "不可以为空";
        String strContractStartTime = tvContractStartTime.getText().toString();
        String strContractEndTime = tvContractEndTime.getText().toString();

        if (TextUtils.isEmpty(strContractStartTime)) {
            SimpleToast.toastMessage("合同签订日期" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(strContractEndTime)) {
            SimpleToast.toastMessage("合同到期日期" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }
//        Boolean isValid = TimeUtils.timeCompare(
//                strContractStartTime, strContractEndTime, TimeUtils.DEFULT_FORMATE_8);
        try {
            isValid = TimeUtils.compare(strContractStartTime, strContractEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!isValid) {
            SimpleToast.toastMessage("截止日期不合法！", Toast.LENGTH_SHORT);
            return;
        }


    }

    private void checkData() {
        String notEmpty = "不可以为空";
        String strContractType = tvContractType.getText().toString();
        String strInvoicingWay = tvInvocingWay.getText().toString();
        String strPayWay = tvPayWay.getText().toString();
        String strContractStartTime = tvContractStartTime.getText().toString();
        String strContractEndTime = tvContractStartTime.getText().toString();
        String strContractProgress = edtPayProgress.getText().toString();
        if (TextUtils.isEmpty(strContractType)) {
            SimpleToast.toastMessage("合同类型" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(strInvoicingWay)) {
            SimpleToast.toastMessage("开票方式" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(strPayWay)) {
            SimpleToast.toastMessage("支付方式" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }


        if (TextUtils.isEmpty(strContractStartTime)) {
            SimpleToast.toastMessage("合同签订日期" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(strContractEndTime)) {
            SimpleToast.toastMessage("合同到期日期" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }
//        Boolean isValid = TimeUtils.timeCompare(
//                strContractStartTime, strContractEndTime, TimeUtils.DEFULT_FORMATE_8);
        try {
            isValid = TimeUtils.compare(strContractStartTime, strContractEndTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!isValid) {
            SimpleToast.toastMessage("截止日期不合法！", Toast.LENGTH_SHORT);
            return;
        }

        if (TextUtils.isEmpty(strContractProgress)) {
            SimpleToast.toastMessage("合同进度" + notEmpty, Toast.LENGTH_SHORT);
            return;
        }
        SimpleToast.toastMessage("添加成功", Toast.LENGTH_SHORT);
        finish();
        toActivity(ContractManageRecycleActivity.class);

    }

    //select

    private void select(int resId, View view) {

        Resources res = getResources();
        //  String[] status = res.getStringArray(R.array.approval_no);
        String[] status = res.getStringArray(resId);
        List<String> allStatus = Arrays.asList(status);
        TimePickerUtils.getInstance().onListDataPicker(this, allStatus, view);

    }

}

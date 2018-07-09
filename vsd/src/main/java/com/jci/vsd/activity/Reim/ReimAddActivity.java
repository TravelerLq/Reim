package com.jci.vsd.activity.Reim;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.MainActivity;
import com.jci.vsd.utils.CompressUtil;
import com.jci.vsd.utils.DateUtils;
import com.jci.vsd.utils.DisplayUtils;
import com.jci.vsd.utils.FileToBase64Util;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.ScreenUtil;
import com.jci.vsd.utils.Utils;
import com.jci.vsd.view.widget.CountEditText;
import com.jci.vsd.view.widget.SimpleToast;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopTwoListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
 * Created by liqing on 18/6/26.
 * 添加报销项目
 */

public class ReimAddActivity extends BaseActivity {
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.ll_reim_type)
    LinearLayout llReimType;
    @BindView(R.id.expandable_list_view)
    ExpandPopTabView expandPopTabView;

    @BindView(R.id.edt_start_time)
    EditText edtStartTime;

    @BindView(R.id.edt_end_time)
    EditText edtEndTime;

    @BindView(R.id.edt_start_location)
    EditText edtStartLocation;
    @BindView(R.id.edt_end_location)
    EditText edtEndLocation;

    @BindView(R.id.iv_reim_pic)
    ImageView ivReimPic;

    @BindView(R.id.edt_count)
    CountEditText edtCount;

    @BindView(R.id.btn_add)
    Button btnAdd;
    //expandapoTab
    private PopTwoListView popTwoListView;
    private List<KeyValueBean> parentsList;
    List<ArrayList<KeyValueBean>> childList;
    private Context context;
    private ArrayList<String> selectedPhotos;
    private ArrayList<String> photos;
    private String path;
    private Uri uri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reim_add);
        context = ReimAddActivity.this;
        initViewEvent();
        initData();
    }

    private void initData() {
        parentsList = new ArrayList<>();
        childList = new ArrayList<>();
        selectedPhotos = new ArrayList<>();

        //二级菜单初始化
        initExpandaTabView();
    }


    private void initExpandaTabView() {

        //数据初始化
        setSecondMenuData();

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
            //图片压缩：
            Glide.with(ReimAddActivity.this)
                    .load(uri)
                    .thumbnail(0.1f)
                    .into(ivReimPic);
//
            File outFile = CompressUtil.compressImage(path,
                    ScreenUtil.getInstance().getDisplayWidth(),
                    ScreenUtil.getInstance().getDisplayWidth());
            Loger.e("--outFile.length()--" + outFile.length());

            try {
//
                String base64CodePic = FileToBase64Util.encodeBase64File(outFile.getPath());


                signVerifyP1(base64CodePic);

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

        ReimAddActivity.this.getIntent().putExtra("data", plantext);
        ReimAddActivity.this.getIntent().putExtra("type", DataProcessType.SIGNATURE_P1.name());
        SignatureP1Service signatureP1Service = new SignatureP1Service(ReimAddActivity.this, "1234", new ProcessListener<DataProcessResponse>() {
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
                    Glide.with(ReimAddActivity.this)
                            .load(uri)
                            .thumbnail(0.1f)
                            .into(ivReimPic);


                } else {
//                    if (pdu.getMypDialog().isShowing()) {
//                        pdu.dismisspd();
//                    }
                    Loger.e("dataProcessResponse.getRet() !=0");
                    Toast.makeText(ReimAddActivity.this, "图片签名失败" + dataProcessResponse.getMessage(), Toast.LENGTH_SHORT).show();
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

    //eepanddpop点击响应

    public void addItem(final ExpandPopTabView expandTabView,
                        final List<KeyValueBean> parentLists,
                        List<ArrayList<KeyValueBean>> childrenListLists,
                        String defaultParentkey, String defaultChildkey,
                        String defaultShowText) {
        popTwoListView = new PopTwoListView(this);
        //  popTwoListView.setDefaultSelectByValue(defaultParentSelect, defaultChildSelect);
        popTwoListView.setDefaultSelectByKey(defaultParentkey, defaultChildkey);

        //distanceView.setDefaultSelectByKey(defaultParent, defaultChild);
        popTwoListView.setCallBackAndData(expandTabView, parentLists, childrenListLists, new PopTwoListView.OnSelectListener() {

            @Override
            public void getValue(String showText, String parentKey, String childrenKey) {
                Log.e("----", "三级－－ :" + showText + " ,parentKey :" + parentKey + " ,childrenKey :" + childrenKey);

            }

            @Override
            public void getParentValue(int position, String showText, String key) {
                Log.e("－－－－", "二级－－－－ :" + showText + " ,二级key :" + key);

            }

        });
        expandTabView.addItemToExpandTab("", popTwoListView);

    }

    void setSecondMenuData() {

        KeyValueBean parentBean = new KeyValueBean();
        parentBean.setKey("1");
        parentBean.setValue("四川");
        parentsList.add(parentBean);

        parentBean = new KeyValueBean();
        parentBean.setKey("2");
        parentBean.setValue("重庆");
        parentsList.add(parentBean);

        parentBean = new KeyValueBean();
        parentBean.setKey("3");
        parentBean.setValue("云南");
        parentsList.add(parentBean);
//        //==================================================

        ArrayList<KeyValueBean> sclist = new ArrayList<>();
        KeyValueBean bean = new KeyValueBean();
        bean.setKey("11");
        bean.setValue("成都");
        sclist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("12");
        bean.setValue("绵阳");
        sclist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("13");
        bean.setValue("德阳");
        sclist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("14");
        bean.setValue("宜宾");
        sclist.add(bean);
        childList.add(sclist);


        ArrayList<KeyValueBean> cqlist = new ArrayList<>();
        bean = new KeyValueBean();
        bean.setKey("21");
        bean.setValue("渝北");
        cqlist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("22");
        bean.setValue("渝中");
        cqlist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("23");
        bean.setValue("江北");
        cqlist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("24");
        bean.setValue("沙坪坝");
        cqlist.add(bean);
        childList.add(cqlist);

        ArrayList<KeyValueBean> shlist = new ArrayList<>();
        bean = new KeyValueBean();
        bean.setKey("31");
        bean.setValue("昆明");
        shlist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("32");
        bean.setValue("丽江");
        shlist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("33");
        bean.setValue("香格里拉");
        shlist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("34");
        bean.setValue("凯里");
        shlist.add(bean);
        childList.add(shlist);

        //     childList.add(shlist);

        addItem(expandPopTabView, parentsList, childList, parentsList.get(0).getKey(), childList.get(0).get(0).getKey(), "");


    }


    @Override
    protected void initViewEvent() {
        // llReimType.setOnClickListener(this);
        ivReimPic.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.btn_add:
                checkData();
                //  toActivity(context, ExpenseItemListActivity.class);
                break;
            case R.id.ll_reim_type:
                Loger.e("---reim_type-click" + expandPopTabView.isShown());

                if (expandPopTabView.isShown()) {
                    Loger.e("--expandPop--");
                    expandPopTabView.onExpandPopView();
                }
                // toActivity(AddExpenseItemActivtity.this, ExpenseTypeActivity.class);
                break;
            case R.id.rl_pick_date:
                Toast.makeText(context, "为当前时间，不可修改！", Toast.LENGTH_SHORT).show();
                // TimePickerUtils.getInstance().onYearMonthDayPicker(AddExpenseItemActivtity.this, tvDate);
                break;
            case R.id.iv_reim_pic:

                takePics();
            default:
                break;
        }
    }


    private void checkData() {
        String edtMoneyStr = edtMoney.getText().toString();
//        expenseTypeStr = tv.getText().toString();
        String startTimeStr = edtStartTime.getText().toString().trim();
        String endTimeStr = edtEndTime.getText().toString().trim();
        String startLocationStr = edtStartLocation.getText().toString().trim();
        String endLocationStr = edtEndLocation.getText().toString().trim();
        String remarkStr = edtCount.getText().toString().trim();


        if (TextUtils.isEmpty(edtMoneyStr)) {
            Toast.makeText(this, "金额不可为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(remarkStr)) {
            Toast.makeText(this, "说明不可为空", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (TextUtils.isEmpty(expenseTypeStr)) {
//            Toast.makeText(this, "请选择类别", Toast.LENGTH_SHORT).show();
//            return;
//        }
        // 判断照片是否为空
        if (selectedPhotos.size() == 0) {
            Toast.makeText(this, "票据照片必选", Toast.LENGTH_SHORT).show();
            return;
        }
        // 判断日期是否为空
        if (TextUtils.isEmpty(startTimeStr)) {
            Toast.makeText(this, "开始时间不可为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 判断日期是否为空
        if (TextUtils.isEmpty(startLocationStr)) {
            Toast.makeText(this, "开始地点不可以为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 判断日期是否为空
        if (TextUtils.isEmpty(endLocationStr)) {
            Toast.makeText(this, "结束地点不可为空", Toast.LENGTH_SHORT).show();
            return;
        }

        Date startTime = DateUtils.strToDate(startTimeStr);
        Date endTime = DateUtils.strToDate(endTimeStr);
        toActivity(ReimRecycActivity.class);
        finish();
        //新增报销项目

//        amount = Double.parseDouble(edtFeeStr);
//
//        submitExpenseItem();
    }

    private void takePics() {
        PhotoPicker.builder()
                .setPhotoCount(1)
                .setShowCamera(true)
                .setPreviewEnabled(false)
                .setSelected(selectedPhotos)
                .start(ReimAddActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (expandPopTabView != null) {
            expandPopTabView.onExpandPopView();
        }
    }

}

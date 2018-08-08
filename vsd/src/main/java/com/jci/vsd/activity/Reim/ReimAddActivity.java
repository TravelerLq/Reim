package com.jci.vsd.activity.Reim;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.MainActivity;
import com.jci.vsd.activity.MeFeedBackActivity;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.CatBean;
import com.jci.vsd.bean.PicBean;
import com.jci.vsd.bean.reim.ReimAddItemBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.data.ReimAddData;
import com.jci.vsd.data.ReimCategoryData;
import com.jci.vsd.network.api.FeedBackApi;
import com.jci.vsd.network.control.FeedBackApiControl;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.network.control.UploadProgressListener;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.CompressUtil;
import com.jci.vsd.utils.DateUtils;
import com.jci.vsd.utils.DisplayUtils;
import com.jci.vsd.utils.FileToBase64Util;
import com.jci.vsd.utils.FileUtils;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.ScreenUtil;
import com.jci.vsd.utils.TimePickerUtils;
import com.jci.vsd.utils.Utils;
import com.jci.vsd.view.widget.CountEditText;
import com.jci.vsd.view.widget.SimpleToast;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopTwoListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.util.ConvertUtils;
import cn.unitid.spark.cm.sdk.business.SignatureP1Service;
import cn.unitid.spark.cm.sdk.common.DataProcessType;
import cn.unitid.spark.cm.sdk.data.response.DataProcessResponse;
import cn.unitid.spark.cm.sdk.exception.CmSdkException;
import cn.unitid.spark.cm.sdk.listener.ProcessListener;
import io.reactivex.Observable;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

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
    TextView edtStartTime;

    @BindView(R.id.edt_end_time)
    TextView edtEndTime;

    @BindView(R.id.tv_single_time)
    TextView tvSingleTime;

    @BindView(R.id.edt_start_location)
    EditText edtStartLocation;
    @BindView(R.id.edt_end_location)
    EditText edtEndLocation;
    @BindView(R.id.edt_single_location)
    EditText edtSingleLocation;
    //client

    @BindView(R.id.edt_treat_client)
    EditText edtClient;

    @BindView(R.id.iv_reim_pic)
    ImageView ivReimPic;

    @BindView(R.id.edt_count)
    CountEditText edtCount;

    @BindView(R.id.btn_add)
    Button btnAdd;

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    @BindView(R.id.tv_money)
    TextView tvReimType;

    @BindView(R.id.rl_start_end_time)
    LinearLayout rlStartAndEndTime;
    @BindView(R.id.rl_time)
    LinearLayout rlTime;


    @BindView(R.id.rl_start_and_end_location)
    LinearLayout rlStartEndLocation;
    @BindView(R.id.rl_location)
    LinearLayout rlLocation;
    @BindView(R.id.rl_client)
    LinearLayout rlClinet;

    @BindView(R.id.etContent)
    EditText edtContent;

    //餐费标准
    @BindView(R.id.rl_meal_standard)
    LinearLayout rlMealStandard;
    @BindView(R.id.edt_meal_standard)
    EditText edtMealStandard;

    //车牌号
    @BindView(R.id.rl_vehicle_no)
    LinearLayout rlVehicleNo;
    @BindView(R.id.edt_vehicle_no)
    EditText edtVehiclNo;

    //行驶公里数
    @BindView(R.id.rl_drive_mile)
    LinearLayout rlDriveMile;
    @BindView(R.id.edt_drive_mile)
    EditText edtDriveMile;


    //事由
    @BindView(R.id.rl_reason)
    LinearLayout rlReason;
    @BindView(R.id.edt_reason)
    EditText edtReason;


    //expandapoTab
    private PopTwoListView popTwoListView;
    private List<KeyValueBean> parentsList = new ArrayList<>();
    List<ArrayList<KeyValueBean>> childList = new ArrayList<>();
    private Context context;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private ArrayList<String> photos;
    private String path;
    private Uri uri;
    List<PicBean> picBeanList = new ArrayList<>();
    private List<CatBean> catBeanList = new ArrayList<>();
    //科目三
    private List<KeyValueBean> thirdList = new ArrayList<>();
    private int categoryId = 2;
    private int itemId;
    private String cert;
    private String signKey;
    private String categoryName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loger.e("---ReimAddONCreate---");
        setContentView(R.layout.activity_reim_add);
        context = ReimAddActivity.this;
        initViewEvent();
        initTesData();
        catBeanList = ReimCategoryData.getCategoryList();
        new Thread(MyThread).start();
//        if (catBeanList != null) {
//            //从保存的文件里获取
//            if (catBeanList.size() > 0) {
//                CatBean catBean = null;
//                parentsList.clear();
//                for (int i = 0; i < catBeanList.size(); i++) {
//                    catBean = catBeanList.get(i);
//
//                    //firstList.add(catBeanList.get(i).getCname());
//                    // parentsList.add(new KeyValueBean(object.getExpenseCategoryId(), object.getExpenseCategoryName()));
//                    parentsList.add(new KeyValueBean(String.valueOf(catBeanList.get(i).getCat()), catBean.getCname()));
//
//                }
//            }
//        } else {
//
//            new Thread(MyThread).start();
////            CatBean catBean = null;
////            parentsList.clear();
////            for (int i = 0; i < catBeanList.size(); i++) {
////                catBean = catBeanList.get(i);
////
////                //firstList.add(catBeanList.get(i).getCname());
////                // parentsList.add(new KeyValueBean(object.getExpenseCategoryId(), object.getExpenseCategoryName()));
////                parentsList.add(new KeyValueBean(String.valueOf(catBeanList.get(i).getCat()), catBean.getCname()));
////
////            }
////            initExpandaTabView();
//        }
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
            String hashFile = null;
            try {
                hashFile = FileUtils.getMD5Checksum(path);
                signVerifyP1(hashFile);
                Loger.e("--hashFile--" + hashFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Loger.e("path--" + (new File(selectedPhotos.get(0)).length()));

//            //图片压缩：
//            Glide.with(ReimAddActivity.this)
//                    .load(uri)
//                    .thumbnail(0.1f)
//                    .into(ivReimPic);


//
//            File outFile = CompressUtil.compressImage(path,
//                    ScreenUtil.getInstance().getDisplayWidth(),
//                    ScreenUtil.getInstance().getDisplayWidth());
//            Loger.e("--outFile.length()--" + outFile.length());

//            try {
////
//                String base64CodePic = FileToBase64Util.encodeBase64File(outFile.getPath());
//                String hashFilCompress = FileUtils.getMD5Checksum(outFile.getPath());
//                Loger.e("--hashFilCompress--" + hashFile);
//
//                signVerifyP1(hashFile);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

    }

    //签名验证
    private void signVerifyP1(final String base64Code1) {

        String plantext = base64Code1;

        ReimAddActivity.this.getIntent().putExtra("data", plantext);
        ReimAddActivity.this.getIntent().putExtra("type", DataProcessType.SIGNATURE_P1.name());
        SignatureP1Service signatureP1Service = new SignatureP1Service(ReimAddActivity.this, "1234", new ProcessListener<DataProcessResponse>() {
            @Override
            public void doFinish(DataProcessResponse dataProcessResponse, String certificate) {
                cert = certificate;
                Log.e("doFinish---", "= ");
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                if (dataProcessResponse.getRet() == 0) {
                    Log.e("密钥", "= " + dataProcessResponse.getResult());
                    signKey = dataProcessResponse.getResult();
                    //  spu.setCertKey(dataProcessResponse.getResult());
                    //获得就是签名证书
                    Log.e("cert", "= " + certificate);
                    //  spu.setCert(certificate);
                    uri = Uri.fromFile(new File(selectedPhotos.get(0)));
                    Glide.with(ReimAddActivity.this)
                            .load(uri)
                            .thumbnail(0.1f)
                            .into(ivReimPic);


                } else {
//                    if (pdu.getMypDialog().isShowing()) {
//                        pdu.dismisspd();
//                    }
                    Loger.e("dataProcessResponse.getRet() !=0");
                    SimpleToast.toastMessage("图片签名失败,请重试" + dataProcessResponse.getMessage(), Toast.LENGTH_SHORT);
                    // Toast.makeText(ReimAddActivity.this, "图片签名失败" + dataProcessResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void doException(CmSdkException e) {
//                if (pdu.getMypDialog().isShowing()) {
//                    pdu.dismisspd();
//                }
                SimpleToast.toastMessage("图片签名失败,请重试" + e.getMessage(), Toast.LENGTH_SHORT);
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
                Log.e("----", "三级－－ :" + showText +
                        " ,parentKey :" + parentKey + " ,childrenKey :" + childrenKey);

                //没有三级科目
                if (childrenKey.equals("0")) {
                    Loger.e("---integer.(-1)=" + Integer.valueOf("0"));
                    itemId = Integer.valueOf("0");
                    Loger.e("");

                } else {
                    itemId = Integer.valueOf(childrenKey);
                    tvReimType.setText(showText);
                }

                reimTypeLayout(categoryId, itemId);
            }

            @Override
            public void getParentValue(int position, String showText, String key) {
                Log.e("－－－－", "二级－－－－ :" + showText + " ,二级key :" + key);
                categoryId = Integer.valueOf(key);
                categoryName = showText;
                tvReimType.setText(showText);

                getItemdata(position);

            }

        });
        expandTabView.addItemToExpandTab("", popTwoListView);

    }

    void setSecondMenuData() {

//        KeyValueBean parentBean = new KeyValueBean();
//        parentBean.setKey("1");
//        parentBean.setValue("四川");
//        parentsList.add(parentBean);
//
//        parentBean = new KeyValueBean();
//        parentBean.setKey("2");
//        parentBean.setValue("重庆");
//        parentsList.add(parentBean);
//
//        parentBean = new KeyValueBean();
//        parentBean.setKey("3");
//        parentBean.setValue("云南");
//        parentsList.add(parentBean);
////        //==================================================
//
        ArrayList<KeyValueBean> sclist = new ArrayList<>();
        KeyValueBean bean = new KeyValueBean();

        bean.setKey("11");
        bean.setValue("住宿费");
        sclist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("12");
        bean.setValue("打车票");
        sclist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("13");
        bean.setValue("餐饮费");
        sclist.add(bean);

        bean = new KeyValueBean();
        bean.setKey("14");
        bean.setValue("食品票");
        sclist.add(bean);
        childList.add(sclist);

        addItem(expandPopTabView, parentsList, childList, parentsList.get(0).getKey(), childList.get(0).get(0).getKey(), "");
    }


    @Override
    protected void initViewEvent() {
        // llReimType.setOnClickListener(this);
        ivReimPic.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        // backBtn.setOnClickListener(this);
        llReimType.setOnClickListener(this);
        edtStartTime.setOnClickListener(this);
        edtEndTime.setOnClickListener(this);

        tvSingleTime.setOnClickListener(this);

        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.add_reim_item));

    }

    @Override
    public void onClick(View view) {


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


            case R.id.tv_single_time:

                // Toast.makeText(context, "为当前时间，不可修改！", Toast.LENGTH_SHORT).show();
                TimePickerUtils.getInstance().onYearMonthDayPickerText(ReimAddActivity.this, tvSingleTime);
                break;

            case R.id.edt_start_time:

                // Toast.makeText(context, "为当前时间，不可修改！", Toast.LENGTH_SHORT).show();
                TimePickerUtils.getInstance().onYearMonthDayPickerText(ReimAddActivity.this, edtStartTime);
                break;
            case R.id.edt_end_time:

                // Toast.makeText(context, "为当前时间，不可修改！", Toast.LENGTH_SHORT).show();
                TimePickerUtils.getInstance().onYearMonthDayPickerText(ReimAddActivity.this, edtEndTime);
                break;

//            case R.id.tv_single_time:
//
//                // Toast.makeText(context, "为当前时间，不可修改！", Toast.LENGTH_SHORT).show();
//                TimePickerUtils.getInstance().onYearMonthDayPickerText(ReimAddActivity.this, edtStartTime);
//                break;

            case R.id.iv_reim_pic:
                takePics();
                break;
            case R.id.button_back:
                warningDialog("确定退出当前编辑？");
                break;

            default:

                break;
        }
    }

    private void initTesData() {
        edtMoney.setText("110");
        edtStartTime.setText("2018-7-26");
        edtEndTime.setText("2018-7-29");
        edtStartLocation.setText("南京");
        edtEndLocation.setText("上海");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Loger.e("----reimAddActi--onresume---");
    }

    private void checkData() {
        ReimAddItemBean bean = new ReimAddItemBean();
        String edtMoneyStr = edtMoney.getText().toString();
//        expenseTypeStr = tv.getText().toString();
        String dateStr = tvSingleTime.getText().toString();
        String startTimeStr = edtStartTime.getText().toString().trim();
        String endTimeStr = edtEndTime.getText().toString().trim();
        String startLocationStr = edtStartLocation.getText().toString().trim();
        String endLocationStr = edtEndLocation.getText().toString().trim();
        String remarkStr = edtContent.getText().toString().trim();
        String loaction = edtSingleLocation.getText().toString().trim();

        String client = edtClient.getText().toString();


        if (TextUtils.isEmpty(edtMoneyStr)) {
            Toast.makeText(this, "金额不可为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(tvReimType.getText().toString())) {
            Toast.makeText(this, "请选择报销类别", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(remarkStr)) {
            Toast.makeText(this, "明细不可为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rlStartEndLocation.getVisibility() == VISIBLE) {
            if (TextUtils.isEmpty(startTimeStr)) {
                Toast.makeText(this, "开始时间不可为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bean.setStart(startTimeStr);
            }
        }
//        // 判断日期是否为空
//        if (edtStartTime.getVisibility() == View.VISIBLE && TextUtils.isEmpty(startTimeStr)) {
//            Toast.makeText(this, "开始时间不可为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (rlStartEndLocation.getVisibility() == VISIBLE) {
            if (TextUtils.isEmpty(endTimeStr)) {
                Toast.makeText(this, "结束时间不可为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bean.setStart(endTimeStr);
            }
        }

//        if (edtEndTime.getVisibility() == View.VISIBLE && TextUtils.isEmpty(startTimeStr)) {
//            Toast.makeText(this, "结束时间不可为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
        //只有时间时候
        if (rlTime.getVisibility() == VISIBLE) {
            if (TextUtils.isEmpty(dateStr)) {
                Toast.makeText(this, "日期不可以为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bean.setStart(dateStr);
            }
        }

        // 判断是否为空

        if (rlStartEndLocation.getVisibility() == VISIBLE) {
            if (TextUtils.isEmpty(startLocationStr)) {
                Toast.makeText(this, "开始地点不可以为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bean.setBp(startLocationStr);
            }
        }
//end location
        if (rlStartEndLocation.getVisibility() == VISIBLE) {
            if (TextUtils.isEmpty(endLocationStr)) {
                Toast.makeText(this, "结束地点不可为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bean.setDst(endLocationStr);
            }
        }
//        if (edtStartLocation.getVisibility() == View.VISIBLE && TextUtils.isEmpty(startLocationStr)) {
//            Toast.makeText(this, "开始地点不可以为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        // 判断是否为空
//        if (edtEndLocation.getVisibility() == View.VISIBLE && TextUtils.isEmpty(endLocationStr)) {
//            Toast.makeText(this, "结束地点不可为空", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // 判断是否为空
        if (rlLocation.getVisibility() == VISIBLE) {
            if (TextUtils.isEmpty(loaction)) {
                Toast.makeText(this, "地点不可为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bean.setBp(loaction);
            }
        }
//        if (edtSingleLocation.getVisibility() == View.VISIBLE && TextUtils.isEmpty(endLocationStr)) {
//            Toast.makeText(this, "地点不可为空", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // client
        if (rlClinet.getVisibility() == VISIBLE) {
            if (TextUtils.isEmpty(client)) {
                Toast.makeText(this, "招待客户不可以为空", Toast.LENGTH_SHORT).show();
                return;
            } else {
                bean.setClient(client);
            }
        }


        if (selectedPhotos.size() == 0) {
            Toast.makeText(this, "票据原件不可以为空", Toast.LENGTH_SHORT).show();
            return;
        }

        Date startTime = DateUtils.strToDate(startTimeStr);
        Date endTime = DateUtils.strToDate(endTimeStr);
        //新增报销项目

        bean.setAmount(edtMoneyStr);
        bean.setStart(startTimeStr);
        bean.setEnd(endTimeStr);
        bean.setBp(startLocationStr);
        bean.setDst(endLocationStr);
        bean.setRemark(remarkStr);
        bean.setCat(String.valueOf(categoryId));
        bean.setItem(String.valueOf(itemId));

        bean.setCer(cert);
        bean.setSign(signKey);

        subFile(bean, selectedPhotos.get(0), uploadProgressListener);
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

    //

    private void subFile(final ReimAddItemBean bean, String path, UploadProgressListener uploadProgressListener) {
        final File file = new File(path);
        Loger.i("subFile = " + path);
        Observable<String> observable = new ReimControl().submitReim_pic(bean, file, uploadProgressListener);
        CommonDialogObserver<String> observer = new CommonDialogObserver<String>(ReimAddActivity.this) {
            @Override
            public void onNext(String s) {
                super.onNext(s);
                Loger.e("subFile" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                int code =jsonObject.getIntValue(AppConstant.JSON_CODE) ;
                if (code == 200) {
                    SimpleToast.toastMessage("成功", Toast.LENGTH_SHORT);
                    JSONObject jsonData = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA));
                    int id = jsonData.getIntValue("id");
                    bean.setId(id);

                    ReimAddData.saveReimAddItemBean(bean);
                    //  toAcW(ReimRecycActivity.class);
                    toAtivityWithParamsAndBean(ReimRecycActivity.class, bean, AppConstant.KEY_REIM_ITEM);

                }else if(code==70003){
                    SimpleToast.toastMessage("验签失败，请重试", Toast.LENGTH_SHORT);
                }
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

    //获取报销科目

    private void getJsonData() {

        catBeanList = new ArrayList<>();
        // firstList = new ArrayList<>();
        //  showProgress();

        Thread thread = new Thread(new Runnable() {

            private String json;


            @Override
            public void run() {
                //线程执行内容
                try {
                    json = ConvertUtils.toString(getAssets().open("data.json"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catBeanList.addAll(JSON.parseArray(json, CatBean.class));
                ReimCategoryData.saveCategoryList(catBeanList);
                CatBean catBean = null;
                for (int i = 0; i < catBeanList.size(); i++) {
                    catBean = catBeanList.get(i);

                    //firstList.add(catBeanList.get(i).getCname());
                    // parentsList.add(new KeyValueBean(object.getExpenseCategoryId(), object.getExpenseCategoryName()));
                    parentsList.clear();
                    parentsList.add(new KeyValueBean(String.valueOf(catBeanList.get(i).getCat()), catBean.getCname()));
                }
            }
        });
        //开启线程
        thread.start();

        initExpandaTabView();
    }

    private void getJsonReimTypeData() {
        catBeanList = new ArrayList<>();
        String json = "";
        try {
            json = ConvertUtils.toString(getAssets().open("data.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        catBeanList.addAll(JSON.parseArray(json, CatBean.class));
        ReimCategoryData.saveCategoryList(catBeanList);
        CatBean catBean = null;
        parentsList.clear();
        for (int i = 0; i < catBeanList.size(); i++) {
            catBean = catBeanList.get(i);
            parentsList.add(new KeyValueBean(String.valueOf(catBeanList.get(i).getCat()), catBean.getCname()));
        }

    }

    private void getItemdata(int pos) {
        thirdList.clear();
        List<CatBean.ItemsBean> itemsBeanList = catBeanList.get(pos).getItems();
        CatBean.ItemsBean itemsBean = null;
        if (itemsBeanList != null && itemsBeanList.size() > 0) {
            for (int i = 0; i < itemsBeanList.size(); i++) {
                //   Log.e("thirdListNewGet==", "" + thirdList.get(i).getValue());
                itemsBean = itemsBeanList.get(i);
                KeyValueBean keyValueBean = new KeyValueBean
                        (String.valueOf(itemsBean.getItem()), itemsBean.getIname());
                thirdList.add(keyValueBean);
            }

        }

        if (thirdList.size() == 0) {
            thirdList.add(new KeyValueBean("0", "无三级科目"));
        }
        if (popTwoListView == null) {
            popTwoListView = new PopTwoListView(this);
        }

        popTwoListView.refreshChild(thirdList);
    }


    //去加载Reim报销科目类别

    Handler handle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                initExpandaTabView();
            }


        }

    };

    Runnable MyThread = new Runnable() {
        @Override
        public void run() {
            getJsonReimTypeData();
            handle.sendEmptyMessage(1);
        }
    };


    // 根据CatId +ItemId 来决定显示布局

    public void reimTypeLayout(int catId, int itemId) {

        Loger.e("reimTypeLayout--" + catId + "--itemID--" + itemId);
        //默认 1.日期 ＋始终点
        Boolean hasTime = true;
        Boolean hasLocation = true;
        Boolean singleTime = false, singleLocation = false, hasClinet = false;
        Boolean hasReason = false;


        if (catId == 2 && itemId == 2) {
            //招待费 －打车费 ：CatId =2,ItemId=2 招待客户：住宿时间、地点
            singleTime = false;
            singleLocation = false;
            hasClinet = true;
            hasReason = false;
        }

        if (catId == 8 && itemId == 2) {
            //交通费 －打车费 ：CatId =2,ItemId=2 时间、地点
            singleTime = false;
            singleLocation = false;
            hasClinet = false;
            hasReason = true;
        }

        if (catId == 4 && itemId == 21) {
            //办公费费  ：CatId =4,ItemId=21 时间、地点 明细

            singleLocation = true;
            singleTime = true;
            hasClinet = false;
            hasReason = false;
        }
        if (catId == 24 && itemId == -1) {
            //误餐费 时间
            hasTime = true;
            hasLocation = false;
            singleTime = true;
            hasReason = true;
            hasClinet = false;
        }
        if (catId == 11) {
            //
            rlVehicleNo.setVisibility(VISIBLE);

            if (itemId == 11) {
                //11 11 加油票
                hasTime = true;
                singleTime = true;
                hasLocation = false;
                hasReason = false;
                rlDriveMile.setVisibility(VISIBLE);
            }
            if (itemId == 15) {
                //停车费
                hasReason = true;
                hasLocation = true;
                singleLocation = true;
                hasTime = true;
                singleTime = true;
                hasClinet = false;
            }
            if (itemId == 16) {
                //车辆维修费
                hasReason = true;
                hasLocation = false;
                singleLocation = true;
                hasTime = true;
                singleTime = true;
                hasClinet = false;
                rlDriveMile.setVisibility(VISIBLE);
            }

        } else {
            rlVehicleNo.setVisibility(GONE);
        }

        if (hasTime) {
            rlTime.setVisibility(singleTime ? VISIBLE : GONE);
            rlStartAndEndTime.setVisibility((singleTime ? View.GONE : VISIBLE));

        } else {
            rlTime.setVisibility(GONE);
            rlStartAndEndTime.setVisibility(GONE);
        }
        if (hasLocation) {

            rlLocation.setVisibility(singleLocation ? VISIBLE : GONE);
            rlStartEndLocation.setVisibility((singleLocation ? View.GONE : VISIBLE));

        } else {
            rlLocation.setVisibility(GONE);
            rlStartEndLocation.setVisibility(GONE);
        }

        rlReason.setVisibility(hasReason ? VISIBLE : GONE);
        rlClinet.setVisibility(hasClinet ? VISIBLE : GONE);


    }


}

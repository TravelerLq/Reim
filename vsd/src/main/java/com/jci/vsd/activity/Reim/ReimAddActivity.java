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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.MainActivity;
import com.jci.vsd.activity.MeFeedBackActivity;
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
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.ScreenUtil;
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
    private int categoryId;
    private int itemId;
    private String cert;
    private String signKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reim_add);
        context = ReimAddActivity.this;
        initViewEvent();
        initTesData();

        catBeanList = ReimCategoryData.getCategoryList();
        if (catBeanList == null || catBeanList.size() == 0) {
            getJsonData();
        } else {
            CatBean catBean = null;
            parentsList.clear();
            for (int i = 0; i < catBeanList.size(); i++) {
                catBean = catBeanList.get(i);

                //firstList.add(catBeanList.get(i).getCname());
                // parentsList.add(new KeyValueBean(object.getExpenseCategoryId(), object.getExpenseCategoryName()));
                parentsList.add(new KeyValueBean(String.valueOf(catBeanList.get(i).getCat()), catBean.getCname()));


            }

            initExpandaTabView();
        }
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
//            //图片压缩：
//            Glide.with(ReimAddActivity.this)
//                    .load(uri)
//                    .thumbnail(0.1f)
//                    .into(ivReimPic);


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
                SimpleToast.toastMessage("图片签名失败,请重试" + e.getMessage(), Toast.LENGTH_LONG);
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
                itemId = Integer.valueOf(childrenKey);


            }

            @Override
            public void getParentValue(int position, String showText, String key) {
                Log.e("－－－－", "二级－－－－ :" + showText + " ,二级key :" + key);
                categoryId = Integer.valueOf(key);
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

    private void initTesData() {
        edtMoney.setText("110");
        edtStartTime.setText("2018-7-26");
        edtEndTime.setText("2018-7-29");
        edtStartLocation.setText("南京");
        edtEndLocation.setText("上海");

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
        //新增报销项目

//        amount = Double.parseDouble(edtFeeStr);
//
//        submitExpenseItem();

        ReimAddItemBean bean = new ReimAddItemBean();
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
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    SimpleToast.toastMessage("成功", Toast.LENGTH_SHORT);
                    JSONObject jsonData = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA));
                    int id = jsonData.getIntValue("id");
                    bean.setId(id);

                    ReimAddData.saveReimAddItemBean(bean);
                    //  toAcW(ReimRecycActivity.class);
                    toAtivityWithParamsAndBean(ReimRecycActivity.class, bean, AppConstant.KEY_REIM_ITEM);
                    finish();
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


    }

    private void getItemdata(int pos) {
//        for (int i = 0; i < thirdExpenseCategoryList.size(); i++) {
//            //   Loger.e("thirdExpenseCategoryList-name" + thirdExpenseCategoryList.get(i).getExpenseItemName());
//            if (thirdExpenseCategoryList.get(i).getExpenseItemId() != null) {
//                ExpenseThirdTypeBean bean = thirdExpenseCategoryList.get(i);
//                KeyValueBean keyValueBean = new KeyValueBean(bean.getExpenseItemId().toString(), bean.getExpenseItemName());
//                thirdList.add(keyValueBean);
//            }
//
//
//        }
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
        if (popTwoListView == null) {
            popTwoListView = new PopTwoListView(this);
        }

        popTwoListView.refreshChild(thirdList);
    }


    // pic load


}

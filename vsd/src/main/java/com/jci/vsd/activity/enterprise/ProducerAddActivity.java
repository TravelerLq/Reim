package com.jci.vsd.activity.enterprise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.bean.enterprise.ProducerAddBean;
import com.jci.vsd.bean.enterprise.ProducerBean;
import com.jci.vsd.bean.enterprise.ProducerSettingInfoBean;
import com.jci.vsd.network.control.ProducerManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.TimePickerUtils;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnMoreItemPickListener;
import cn.addapp.pickers.listeners.OnMoreWheelListener;
import cn.addapp.pickers.picker.LinkagePicker;
import cn.addapp.pickers.util.DateUtils;
import io.reactivex.Observable;
import okhttp3.Interceptor;

/**
 * Created by liqing on 18/7/3.
 * 审批流程新增
 */

public class
ProducerAddActivity extends BaseActivity {

    private static final int REQUEST_PERSON = 1100;
    @BindView(R.id.edt_producer_name)
    EditText edtProducerName;

    @BindView(R.id.tv_producer_department)
    TextView tvProducerDepart;

    @BindView(R.id.tv_producer_order)
    TextView tvProducerOrder;


    @BindView(R.id.tv_producer_person)
    TextView tvProducerPerson;
    @BindView(R.id.tv_sure)
    TextView tvSure;

    private int departId;
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    // 可配置的部门
    private List<ProducerSettingInfoBean.AvldptsBean> listDeparts = new ArrayList<>();
    private List<ProducerSettingInfoBean.AvlusersBean> listApprover = new ArrayList<>();
    List<Integer> selectDepsId = new ArrayList<>();

    String[] itemsDeparts = new String[]{""};
    String[] itemsApprovers = new String[]{""};
    private int selectApproverId;
    List<List<ProducerBean>> producersLevels = new ArrayList<>();
    List<ProducerBean> parentProducerList = new ArrayList<>();

    private List<String> secondList = new ArrayList<>();
    private int sort = 4;
    private List<String> firstList = new ArrayList<>();
    private int parentProducerId;
    private int selectProducerId;
    private List<ProducerSettingInfoBean.ChkpntsBean> producersList = new ArrayList<>();
    List<ProducerSettingInfoBean.ChkpntsBean.FathsBean> fathsBeans = new ArrayList<>();
    private String selectDepartName = "";
    private Boolean isSet;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_edit);
        initViewEvent();
        getProducerSettingInfo();
//        initTestData();
//        initDepartment();
    }

    private void initDepartment() {

        //调整部门，就直接用listDiaog ,带有选择的那种

        if (itemsDeparts.length == 0) {
            itemsDeparts = new String[]{"财务部", "销售部", "技术部"};
        }
    }

    private void initTestData() {


        itemsDeparts = new String[]{"财务部", "销售部", "技术部"};
//
//        listStr = new ArrayList<>();
//        listDeparts = new ArrayList<>();
//        listDeparts =
//                DepartmentData.getDepartmentList();
//        for (int i = 0; i < listDeparts.size(); i++) {
//            listStr.add(listDeparts.get(i).getName());
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_PERSON) {
            Bundle bundle = data.getExtras();
            MembersBean userBean = (MembersBean) bundle.getSerializable("user");
            tvProducerPerson.setText(userBean.getName());

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_sure:
                if (isSet) {
                    checkData();
                } else {
                    SimpleToast.toastMessage("暂不可配置流程", Toast.LENGTH_SHORT);
                }

                break;
            case R.id.tv_producer_department:
                //deparment
//             onOptionPicker(tvProducerDepart, listStr);
                if (listDeparts.size() == 0) {
                    SimpleToast.toastMessage("暂无可配置部门", Toast.LENGTH_SHORT);
                } else {
                    showMultiChoiceDialog();
                }

                break;
            case R.id.tv_producer_order:
                // sort =2: 最低可配置审批流程2
                if (sort == 2) {
                    List<String> list = new ArrayList<>();
                    list.add("2");
                    selectProducerId = 2;
                    parentProducerId = 1;
                    TimePickerUtils.getInstance().onListPicker(ProducerAddActivity.this, list, tvProducerOrder);
                } else {
                    onLinkagePicker(tvProducerOrder, 3);
                }

                break;
            case R.id.tv_producer_person:
                Loger.e("click--tv_producer_person");
                if (listApprover.size() == 0) {
                    SimpleToast.toastMessage("暂无可配置人员", Toast.LENGTH_SHORT);
                } else {
                    showSingleChoiceDialog();
                }

                //deparment
                break;
            default:
                break;
        }
    }

    public void onLinkagePicker(final TextView view, final int i) {

        final LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {

            @Override
            public boolean isOnlyTwo() {
                return true;
            }

            @Override
            public List<String> provideFirstData() {
                Loger.e("---provideFirstData--");
                firstList.clear();
                Loger.e("sort--" + sort);
                if (sort == 4) {
                    firstList.add("2");
                    firstList.add("3");
                    firstList.add("4");
                } else if (sort == 3) {
                    firstList.add("2");
                    firstList.add("3");

                } else {
                    firstList.add("2");
                }

                return firstList;
            }


            @Override
            public List<String> provideSecondData(int firstIndex) {
                Loger.e("---provideSecondData--");
                long start = System.currentTimeMillis();
                long end = 0;
//                //二级下面的KeyValueBean
                List<String> secondList = new ArrayList<>();
                //sort =3
                selectProducerId = Integer.valueOf(firstList.get(firstIndex));
                Loger.e("--sort-" + selectProducerId);
                if (firstIndex == 1) {
                    fathsBeans = producersList.get(0).getFaths();
                    for (int j = 0; j < fathsBeans.size(); j++) {
                        secondList.add(fathsBeans.get(j).getName());
                    }
                } else if (firstIndex == 0) {
                    //sort =2
                    fathsBeans = producersList.get(1).getFaths();
                    for (int j = 0; j < fathsBeans.size(); j++) {
                        secondList.add(fathsBeans.get(j).getName());
                    }
                }

                end = System.currentTimeMillis();
                Loger.e("provideSecondData--" + "end-start=" + (end - start));
                return secondList;

            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {

                Loger.e("---secondIndex--" + secondIndex);
                ArrayList<String> thirdList = new ArrayList<>();
                for (int i = 1; i <= (firstIndex == 0 ? 12 : 24); i++) {
                    String str = DateUtils.fillZero(i) + "third";
                    if (firstIndex == 0) {
                        str += "￥";
                    } else {
                        str += "$";
                    }
                    thirdList.add(str);
                }
                return thirdList;
            }

        };

        LinkagePicker picker = new LinkagePicker(this, provider);
        picker.setCanLoop(true);
        picker.setSelectedIndex(0, 0);
        //picker.setSelectedItem("12", "9");
        picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {

            @Override
            public void onItemPicked(String first, String second, String third) {
                Loger.e(first + "-" + second + "-" + third);
                for (int i = 0; i < fathsBeans.size(); i++) {
                    if (fathsBeans.get(i).getName().equals(second)) {
                        parentProducerId = fathsBeans.get(i).getId();
                        Loger.e("---fathId--" + parentProducerId);
                    }

                }
                view.setText("" + first);


            }

        });

        picker.show();
    }


    private void checkData() {
        String name = edtProducerName.getText().toString();
        String level = tvProducerOrder.getText().toString();

        String depart = tvProducerDepart.getText().toString();
        String person = tvProducerPerson.getText().toString();

        if (TextUtils.isEmpty(name)) {
            SimpleToast.toastMessage("审批名称不可为空", Toast.LENGTH_LONG);
            edtProducerName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(level)) {
            SimpleToast.toastMessage("审批流程不可为空", Toast.LENGTH_LONG);

            return;
        }
        if (TextUtils.isEmpty(depart)) {
            SimpleToast.toastMessage("审批部门不可为空", Toast.LENGTH_LONG);

            return;
        }
        if (TextUtils.isEmpty(person)) {
            SimpleToast.toastMessage("审批人不可为空", Toast.LENGTH_LONG);

            return;
        }
        //   selectApproverId = 28;

        ProducerAddBean producerBean = new ProducerAddBean();
//        producerBean.set
        producerBean.setName(name);
        producerBean.setDpts(selectDepsId);
        producerBean.setFath(parentProducerId);
        producerBean.setSort(selectProducerId);
        producerBean.setChecker(selectApproverId);
        producerBean.setDpts(selectDepsId);
        addProducerItem(producerBean);
    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
        tvProducerOrder.setOnClickListener(this);
        tvProducerDepart.setOnClickListener(this);
        tvProducerPerson.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.producer_add));
    }


    private void addProducerItem(ProducerAddBean requestBean) {
        Observable<Boolean> observable = new ProducerManageControl().addProducer(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("添加成功", Toast.LENGTH_SHORT);
                    toActivity(ProducerManageActivity.class);
                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401"))

                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_LONG);
                exit();
                toActivity(LoginActivity.class);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ProducerAddActivity.this);
    }

    private void getProducerSettingInfo() {

        Observable<ProducerSettingInfoBean> observable = new ProducerManageControl().getProducerSettingInfo();
        CommonDialogObserver<ProducerSettingInfoBean> observer = new CommonDialogObserver<ProducerSettingInfoBean>(this) {
            @Override
            public void onNext(ProducerSettingInfoBean bean) {
                super.onNext(bean);
                Boolean hasDpts = false;
                Boolean hasProducer = false;
                Boolean hasApprovals = false;
                // SimpleToast.toastMessage("流程可配置信息获取成功", Toast.LENGTH_SHORT);
                sort = bean.getSortavl();
                //sort =2，只要配置boss下面一级。

                //可选择的审核部门
                if (bean.getAvldpts() != null && bean.getAvldpts().size() > 0) {
                    hasDpts = true;
                    //setting department
                    listDeparts.clear();
                    listDeparts.addAll(bean.getAvldpts());

                    //赋值给 department items
                    itemsDeparts = new String[listDeparts.size()];

                    for (int i = 0; i < listDeparts.size(); i++) {
                        itemsDeparts[i] = listDeparts.get(i).getName();
                        Loger.e("depart-" + listDeparts.get(i).getName());
                    }

                }
                //可选择的审批流程
                if (bean.getChkpnts() != null && bean.getChkpnts().size() > 0) {
                    hasProducer = true;
                    producersList.clear();
                    producersList.addAll(bean.getChkpnts());
                }

                //可选择的审核人
                if (bean.getAvlusers() != null && bean.getAvlusers().size() > 0) {
                    hasApprovals = true;
                    listApprover.clear();
                    listApprover.addAll(bean.getAvlusers());
                    itemsApprovers = new String[listApprover.size()];
                    for (int i = 0; i < listApprover.size(); i++) {
                        itemsApprovers[i] = listApprover.get(i).getName();
                        Loger.e("approver-" + listApprover.get(i).getName());

                    }
                }

                if (hasDpts && hasProducer && hasApprovals) {
                    SimpleToast.toastMessage("流程可配置信息获取成功", Toast.LENGTH_SHORT);
                    isSet = true;
                } else {
                    SimpleToast.toastMessage("可配置信息不完整，暂不可添加", Toast.LENGTH_SHORT);
                    isSet = false;
                }

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_LONG);
                    exit();
                    toActivity(LoginActivity.class);
                }


            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ProducerAddActivity.this);
    }

    //获取parent producer
    private List<ProducerBean> getParentProducer(int sort) {
        Observable<List<ProducerBean>> observable = new ProducerManageControl().getParentProducer(sort);
        CommonDialogObserver<List<ProducerBean>> observer = new CommonDialogObserver<List<ProducerBean>>(this) {
            @Override
            public void onNext(List<ProducerBean> beanList) {
                super.onNext(beanList);
                SimpleToast.toastMessage("获取成功", Toast.LENGTH_SHORT);
                if (beanList.size() > 0) {
                    //setting department
                    parentProducerList.clear();
                    parentProducerList.addAll(beanList);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401"))

                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_LONG);
                exit();
                toActivity(LoginActivity.class);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ProducerAddActivity.this);
        return parentProducerList;
    }

    //多选
    private void showMultiChoiceDialog() {

        selectDepsId.clear();
        selectDepartName = "";
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("多选对话框")
                .setNegativeButton("取消", null).setPositiveButton("确定", null)
                .setMultiChoiceItems(itemsDeparts, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Loger.e("multi chocie--which" + which + "isCheck" + isChecked);
                        if (isChecked) {
                            selectDepartName = itemsDeparts[which] + "&" + selectDepartName;
                            tvProducerDepart.setText(selectDepartName);
                            selectDepsId.add(listDeparts.get(which).getDpt());

                        }


                    }


                }).create();
        dialog.show();

    }

    //singleChoice
    private void showSingleChoiceDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("单选对话框").setIcon(R.drawable.ic_image)
                .setSingleChoiceItems(itemsApprovers, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Loger.e("choose-whitch--" + which);
                        tvProducerPerson.setText(itemsApprovers[which]);
                        selectApproverId = listApprover.get(which).getUid();
                        dialog.dismiss();

                    }
                }).create();
        dialog.show();

    }


}

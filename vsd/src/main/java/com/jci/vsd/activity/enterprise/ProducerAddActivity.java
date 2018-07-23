package com.jci.vsd.activity.enterprise;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.bean.enterprise.DepartmentBean;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.bean.enterprise.ProducerAddBean;
import com.jci.vsd.bean.enterprise.ProducerBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.data.DepartmentData;
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
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnMoreItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.LinkagePicker;
import cn.addapp.pickers.picker.SinglePicker;
import cn.addapp.pickers.util.DateUtils;
import io.reactivex.Observable;

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

    private List<String> firstList;
    private List<String> secondList;
    private List<String> thirdList;
    private List<String> listStr;
    private int departId;
    private List<DepartmentBean> listDeparts = new ArrayList<>();
    List<Integer> selectDeps = new ArrayList<>();
    String[] items = new String[]{""};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_edit);
        initViewEvent();
        initTestData();
        //   initDepartment();
    }

    private void initDepartment() {

        //调整部门，就直接用listDiaog ,带有选择的那种
        listDeparts = DepartmentData.getDepartmentList();
        items = new String[listDeparts.size()];

        for (int i = 0; i < listDeparts.size(); i++) {
            items[i] = listDeparts.get(i).getName();
        }

        if (items.length == 0) {
            items = new String[]{"财务部", "销售部", "技术部"};
        }
    }

    private void initTestData() {


        items = new String[]{"财务部", "销售部", "技术部"};
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
                checkData();
                break;
            case R.id.tv_producer_department:
                //deparment
//                onOptionPicker(tvProducerDepart, listStr);
                showMultiChoiceDialog();

                break;
            case R.id.tv_producer_order:
                Loger.e("click--tv_producer_level");
//                SimpleToast.toastMessage("审批序号不可以修改", Toast.LENGTH_SHORT);
                //    onLinkagePicker(tvProducerOrder, 2);
                onLinkagePicker(tvProducerOrder, 3);
                break;
            case R.id.tv_producer_person:
                Loger.e("click--tv_producer_person");
                selectPerson();
                //deparment
                break;
            default:
                break;
        }
    }


    public void onOptionPicker(final TextView view, List<String> list) {

        SinglePicker<String> picker = new SinglePicker<>(this, list);
        picker.setCanLoop(false);//不禁用循环
        picker.setLineVisible(true);
        picker.setTextSize(18);
//        picker.setSelectedIndex(8);
//        picker.setWheelModeEnable(false);
        //启用权重 setWeightWidth 才起作用
//        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setSelectedIndex(0);
//        picker.setLabel("分");
//        picker.setWeightEnable(true);
//        picker.setWeightWidth(1);
//        picker.setCanLoop(false);
//        picker.setSelectedTextColor(Color.GREEN);//前四位值是透明度
//        picker.setUnSelectedTextColor(Color.RED);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int index, String item) {
                Loger.e("index=" + index + ", item=" + item);
            }
        });
        picker.setOnItemPickListener(new OnItemPickListener<String>() {

            @Override
            public void onItemPicked(int index, String item) {
                Loger.e("index=" + index + ", item=" + item);
                // 获取部门ID
                departId = listDeparts.get(index).getId();
                view.setText(item);

            }
        });
        picker.show();
    }


    public void onLinkagePicker(final TextView view, final int i) {
        LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {

//            @Override
//            public boolean isOnlyTwo() {
//                return true;
//            }

            @Override
            public boolean isOnlyTwo() {
                return i == 2 ? true : false;
            }

            @Override
            public List<String> provideFirstData() {
                ArrayList<String> firstList = new ArrayList<>();
                firstList.add("2");
                firstList.add("3");
                return firstList;
            }

            @Override
            public List<String> provideSecondData(int firstIndex) {
//                //二级下面的KeyValueBean
                ArrayList<String> secondList = new ArrayList<>();
//                CatBean catBean = catBeanList.get(firstIndex);
//                itemsBeanList.clear();
//                itemsBeanList.addAll(catBean.getItems());
//                secondList.clear();
////                secondList.addAll(catBean.getItems());
//                categoryId = catBean.getCat();
//                Loger.e("---categoryId" + categoryId);

//                for (int i = 0; i < itemsBeanList.size(); i++) {
//                    String itemName = itemsBeanList.get(i).getIname();
//                    Loger.e("--itemName--" + itemName);
//                    secondList.add(itemName);
//                }


                for (int i = 0; i < 3; i++) {

                    //  Loger.e("--itemName--" + itemName);
                    secondList.add("2-" + i);
                    secondList.add("3-" + i);
                }

                return secondList;
            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {

//                itemId = itemsBeanList.get(secondIndex).getId();
                //   Loger.e("---itemSelectId--" + itemId);
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
//        picker.setLabel("科目二", "科目三");
        picker.setSelectedIndex(0, 0);
        //picker.setSelectedItem("12", "9");
        picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {

            @Override
            public void onItemPicked(String first, String second, String third) {
                Loger.e(first + "-" + second + "-" + third);
                view.setText("" + first + second);


            }
        });
        picker.show();
    }

    private void selectPerson() {

        startActivityForResult(new Intent(this, MembsersSelectActivity.class), REQUEST_PERSON);
    }

    private void selectLevel() {

        Resources res = getResources();
        String[] status = res.getStringArray(R.array.approval_no);
        List<String> allStatus = Arrays.asList(status);
        TimePickerUtils.getInstance().onListDataPicker(this, allStatus, tvProducerOrder);

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
        //获取producer选择的ID

        ProducerAddBean producerBean = new ProducerAddBean();
//        producerBean.set
        producerBean.setDpts(selectDeps);

        addProducerItem(producerBean);
//        toActivity(ProducerManageActivity.class);
//        this.finish();

    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
        tvProducerOrder.setOnClickListener(this);
        tvProducerDepart.setOnClickListener(this);
        tvProducerPerson.setOnClickListener(this);
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


    private void showMultiChoiceDialog() {

        selectDeps.clear();
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("多选对话框")
                .setNegativeButton("取消", null).setPositiveButton("确定", null)
                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Loger.e("multi chocie--which" + which);
                        ProducerAddBean producerAddBean = new ProducerAddBean();
                        selectDeps.add(listDeparts.get(which).getId());

                    }
                }).create();
        dialog.show();

    }

}

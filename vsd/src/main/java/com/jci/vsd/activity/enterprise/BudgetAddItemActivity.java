package com.jci.vsd.activity.enterprise;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.bean.enterprise.AddBudgetItemBean;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.BudgetManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.TimePickerUtils;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
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
 * 预算管理的增加
 * 根据intentType会判断，
 * 如果是department :就增加部门的
 * category :就增加报销科目类别的
 */

public class BudgetAddItemActivity extends BaseActivity {
    @BindView(R.id.edt_name)
    TextView tvDepartmentName;
    @BindView(R.id.edt_budget_input)
    TextView edtBudgetInput;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.tv_budget_category)
    TextView tvBudgetCategory;

    @BindView(R.id.ll_budget_department)
    LinearLayout llBudgetDepartment;

    @BindView(R.id.ll_budget_category)
    LinearLayout llBudgetCategory;
    private String intentType;
    @BindView(R.id.textview_title)
    TextView tvTitle;

    List<String> singlePickList;
    List<BudgetBean> departmentList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_add);
        tvTitle.setText("新增预算");
        singlePickList = new ArrayList<>();
        departmentList = new ArrayList<>();
        intentType = getIntent().getStringExtra(AppConstant.KEY_TYPE);
        if (intentType.equals(AppConstant.VALUE_BUDGET_DEPART)) {
            //depart
            llBudgetCategory.setVisibility(View.GONE);
            llBudgetDepartment.setVisibility(View.VISIBLE);
            getBudgetDeparment();
        } else {
            llBudgetCategory.setVisibility(View.VISIBLE);
            llBudgetDepartment.setVisibility(View.GONE);
            getBudgetCategory();
        }

        initViewEvent();
    }


    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
        tvDepartmentName.setOnClickListener(this);
        tvBudgetCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_sure:
                checkData(intentType);
                break;
            case R.id.edt_name:
                //选择部门
                onOptionPicker(tvDepartmentName, singlePickList);

                break;
            case R.id.tv_budget_category:

                break;
            default:
                break;
        }
    }

    private void checkData(String intentType) {
        String name = tvDepartmentName.getText().toString();
        String budget = edtBudgetInput.getText().toString();

        String category = tvBudgetCategory.getText().toString();
        if (TextUtils.isEmpty(budget)) {
            SimpleToast.toastMessage("预算不可为空", Toast.LENGTH_LONG);
            edtBudgetInput.requestFocus();
            return;
        }

        if (intentType.equals(AppConstant.VALUE_BUDGET_DEPART)) {
            if (TextUtils.isEmpty(name)) {

                SimpleToast.toastMessage("部门名称不可位空", Toast.LENGTH_LONG);
                tvDepartmentName.requestFocus();
                return;
            }
            //addBudget Bydepartment
            AddBudgetItemBean addBudgetItemBean = new AddBudgetItemBean();
            addBudgetItem(addBudgetItemBean);

        } else {

            if (TextUtils.isEmpty(category)) {
                SimpleToast.toastMessage("科目类别不可位空", Toast.LENGTH_LONG);
                tvDepartmentName.requestFocus();
                return;
            }
            //addBudget Bydepartment
            AddBudgetItemBean addBudgetItemBean = new AddBudgetItemBean();
            addBudgetItem(addBudgetItemBean);

        }


    }

    private void getBudgetDeparment() {
        Observable<List<BudgetBean>> observable = new BudgetManageControl().getBudgetCategory();
        CommonDialogObserver observer = new CommonDialogObserver<List<BudgetBean>>(this) {
            @Override
            public void onNext(List<BudgetBean> budgetBeans) {
                super.onNext(budgetBeans);
                departmentList.clear();
                departmentList.addAll(budgetBeans);
                singlePickList.clear();
                for (int i = 0; i < departmentList.size(); i++) {
                    singlePickList.add(departmentList.get(i).getName());
                }


            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_SHORT);
                    exit();
                    toActivity(LoginActivity.class);
                }
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, BudgetAddItemActivity.this);
    }


    private void getBudgetCategory() {
        Observable<List<BudgetBean>> observable = new BudgetManageControl().getBudgetCategory();
        CommonDialogObserver observer = new CommonDialogObserver<List<BudgetBean>>(this) {
            @Override
            public void onNext(List<BudgetBean> budgetBeans) {
                super.onNext(budgetBeans);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_SHORT);
                    exit();
                    toActivity(LoginActivity.class);
                }
            }

        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, BudgetAddItemActivity.this);
    }

    private void addBudgetItem(AddBudgetItemBean requestBean) {
        Observable<Boolean> observable = new BudgetManageControl().addBudget(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("添加部门成功", Toast.LENGTH_SHORT);
                    toActivity(BudgetManageActivity.class);
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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, BudgetAddItemActivity.this);
    }


    //单选
    public void onOptionPicker(View view, List<String> list) {
        SinglePicker<String> picker = new SinglePicker<>(this, list);
        picker.setCanLoop(false);//不禁用循环
        picker.setLineVisible(true);
        picker.setTextSize(18);
        picker.setSelectedIndex(8);
        picker.setWheelModeEnable(false);
        //启用权重 setWeightWidth 才起作用
        picker.setLabel("分");
        picker.setWeightEnable(true);
        picker.setWeightWidth(1);
        picker.setSelectedTextColor(Color.GREEN);//前四位值是透明度
        picker.setUnSelectedTextColor(Color.RED);
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
                int selectDepartId = departmentList.get(index).getDpt();

            }
        });
        picker.show();
    }


    //两级 关联

    public void onLinkagePicker(View view) {
        LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {

//            @Override
//            public boolean isOnlyTwo() {
//                return true;
//            }

            @Override
            public boolean isOnlyTwo() {
                return true;
            }

            @Override
            public List<String> provideFirstData() {
                ArrayList<String> firstList = new ArrayList<>();
                firstList.add("管理费");
                firstList.add("");
                return firstList;
            }

            @Override
            public List<String> provideSecondData(int firstIndex) {
                //二级下面的KeyValueBean
                ArrayList<String> secondList = new ArrayList<>();
                for (int i = 1; i <= (firstIndex == 0 ? 12 : 24); i++) {
                    String str = DateUtils.fillZero(i);
                    if (firstIndex == 0) {
                        str += "￥";
                    } else {
                        str += "$";
                    }
                    secondList.add(str);
                }
                return secondList;
            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {

                ArrayList<String> thirdList = new ArrayList<>();
                for (int i = 1; i <= (firstIndex == 0 ? 12 : 24); i++) {
                    String str = DateUtils.fillZero(i) + "third";
//                    if (firstIndex == 0) {
//                        str += "￥";
//                    } else {
//                        str += "$";
//                    }
                    thirdList.add(str);
                }
                return thirdList;
            }

        };
        LinkagePicker picker = new LinkagePicker(this, provider);
        picker.setCanLoop(false);
        picker.setLabel("小时制", "点");
        picker.setSelectedIndex(0, 8);
        //picker.setSelectedItem("12", "9");
        picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {

            @Override
            public void onItemPicked(String first, String second, String third) {
                Loger.e(first + "-" + second + "-" + third);
            }
        });
        picker.show();
    }

}



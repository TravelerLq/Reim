package com.jci.vsd.activity.enterprise;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.bean.CatBean;
import com.jci.vsd.bean.ReimCategoryBean;
import com.jci.vsd.bean.enterprise.AddBudgetItemBean;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.data.ReimCategoryData;
import com.jci.vsd.network.control.BudgetManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.TimePickerUtils;
import com.jci.vsd.view.widget.SimpleToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.entity.Province;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnMoreItemPickListener;
import cn.addapp.pickers.listeners.OnMoreWheelListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.LinkagePicker;
import cn.addapp.pickers.picker.SinglePicker;
import cn.addapp.pickers.util.ConvertUtils;
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

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView tvTitle;

    List<String> singlePickList;
    List<BudgetBean> departmentList;
    List<CatBean.ItemsBean> itemsBeanList = new ArrayList<>();

    private List<CatBean> catBeanList = new ArrayList<>();
    private List<String> firstList;
    private int categoryId;
    private int itemId;
    private int departId;


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
            // getBudgetCategory();
            getJsonData();
        }

        initViewEvent();
    }


    private void getJsonData() {

        catBeanList = new ArrayList<>();
        firstList = new ArrayList<>();
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
                for (int i = 0; i < catBeanList.size(); i++) {

                    firstList.add(catBeanList.get(i).getCname());
                }
            }
        });
        //开启线程
        thread.start();


    }


    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
        tvDepartmentName.setOnClickListener(this);
        tvBudgetCategory.setOnClickListener(this);
        backBtn.setOnClickListener(this);
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
                //  TimePickerUtils.getInstance().onListDataPicker(BudgetAddItemActivity.this,singlePickList,edtBudgetInput);

                break;
            case R.id.tv_budget_category:
                onLinkagePicker(tvBudgetCategory);

                break;
            default:
                break;
        }
    }

    private void checkData(String intentType) {
        String name = tvDepartmentName.getText().toString();
        String budget = edtBudgetInput.getText().toString();
        Double budgetDouble = Double.valueOf(budget);
        Double parseDouble=Double.parseDouble(budget);
        Loger.e("--budDouble.valueOf"+budgetDouble);
        Loger.e("--budDouble.parseDou"+parseDouble);

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
            addBudgetItemBean.setType("1");
            addBudgetItemBean.setDpt(departId);

            addBudgetItemBean.setDquota(budgetDouble);
            addBudgetItem(addBudgetItemBean);

        } else {

            if (TextUtils.isEmpty(category)) {
                SimpleToast.toastMessage("科目类别不可位空", Toast.LENGTH_LONG);
                tvDepartmentName.requestFocus();
                return;
            }
            //addBudget Bydepartment
            AddBudgetItemBean addBudgetItemBean = new AddBudgetItemBean();

//itemId !=-1,代表有itemId(既有三级科目，又有二级科目)
            if (itemId !=-1) {
                //更新科目三
                addBudgetItemBean.setCat(itemId);
                addBudgetItemBean.setType("3");
                addBudgetItemBean.setIquota(parseDouble);
                addBudgetItem(addBudgetItemBean);
            } else {
                //itemId ==-1,代表没有itemId(没有三级科目，只有二级科目)
                // 更科目二
                addBudgetItemBean.setType("2");
                addBudgetItemBean.setCat(categoryId);
                addBudgetItemBean.setCquota(budgetDouble);
                addBudgetItem(addBudgetItemBean);
            }


        }


    }

    private void getBudgetDeparment() {
        Observable<List<BudgetBean>> observable = new BudgetManageControl().getBudgetDepart();
        CommonDialogObserver observer = new CommonDialogObserver<List<BudgetBean>>(this) {
            @Override
            public void onNext(List<BudgetBean> budgetBeans) {
                super.onNext(budgetBeans);
                departmentList.clear();
                departmentList.addAll(budgetBeans);
                singlePickList.clear();
                for (int i = 0; i < departmentList.size(); i++) {
                    singlePickList.add(departmentList.get(i).getName());
                    Loger.e("--pickDptName--" + singlePickList.get(i));
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
        final String addType = requestBean.getType();
        Loger.e("add-type=" + addType);
        Observable<Boolean> observable = new BudgetManageControl().addBudget(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("新增成功", Toast.LENGTH_SHORT);
//                    if (addType.equals("1")) {
//                        toActivity(BudgetByDepartManageActivity.class);
//                        finish();
//                    } else {
//                        toActivity(BudgetByCategoryManageActivity.class);
//                    }

                    finish();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_LONG);
                if (t.getMessage().equals("401")) {
                    exit();
                    toActivity(LoginActivity.class);
                }


            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, BudgetAddItemActivity.this);
    }


    //单选
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
                departId = departmentList.get(index).getId();
                view.setText(item);


            }
        });
        picker.show();
    }


    //两级 关联

    public void onLinkagePicker(final TextView view) {
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
                Loger.e("---provideFirstData" );
//                ArrayList<String> firstList = new ArrayList<>();
//                firstList.add("管理费1");
//                firstList.add("管理费2");
                return firstList;
            }

            @Override
            public List<String> provideSecondData(int firstIndex) {
                Loger.e("---provideSecondData" );
//                //二级下面的KeyValueBean
                ArrayList<String> secondList = new ArrayList<>();
                CatBean catBean = catBeanList.get(firstIndex);
                itemsBeanList.clear();
                itemsBeanList.addAll(catBean.getItems());
                secondList.clear();
//                secondList.addAll(catBean.getItems());
                categoryId = catBean.getCat();
                Loger.e("---categoryId" + categoryId);
                if (itemsBeanList.size() > 0) {
                    for (int i = 0; i < itemsBeanList.size(); i++) {
                        String itemName = itemsBeanList.get(i).getIname();
                        Loger.e("--itemName--" + itemName);
                        secondList.add(itemName);
                    }
                } else {
                    secondList.add("暂无数据");
                }

                return secondList;
            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {

                itemId = itemsBeanList.get(secondIndex).getId();
                Loger.e("---itemSelectId--" + itemId);
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
        picker.setCanLoop(true);
//        picker.setLabel("科目二", "科目三");
        picker.setSelectedIndex(0, 0);
        //picker.setSelectedItem("12", "9");
        picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {

            @Override
            public void onItemPicked(String first, String second, String third) {
                Loger.e(first + "-" + second + "-" + third);
                if (itemId == -1) {
                    //只有2级
                    view.setText("" + first);
                } else {
                    view.setText("" + second);
                }


            }
        })
        ;
        picker.setOnMoreWheelListener(new OnMoreWheelListener() {
            @Override
            public void onFirstWheeled(int i, String s) {
                Loger.e("--onFirstWheeled=" + i + " s=" + s);
            }

            @Override
            public void onSecondWheeled(int i, String s) {
                Loger.e("--onSecondWheeled=" + i + " s=" + s );
                if(itemsBeanList.size()>0){
                    itemId = itemsBeanList.get(i).getId();
                }else {
                    itemId = -1;
                }
                Loger.e("itemId="+itemId);


            }

            @Override
            public void onThirdWheeled(int i, String s) {

            }
        });
        picker.show();
    }


}



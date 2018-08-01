package com.jci.vsd.activity.enterprise;

import android.app.Activity;
import android.content.Context;
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
import com.jci.vsd.bean.ReimCategoryBean;
import com.jci.vsd.bean.enterprise.DepartmentBean;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.DepartmentManageControl;
import com.jci.vsd.network.control.MembersManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.TimePickerUtils;
import com.jci.vsd.view.widget.SimpleToast;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.SinglePicker;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/7/2.
 * 新建部门
 */

public class NewDeparmentActivity extends BaseActivity {
    private static final int REQUEST_PERSON = 1111;

    List<String> allStatus;
    @BindView(R.id.edt_depart_name)
    EditText edtDepartName;
    @BindView(R.id.edt_limit)
    EditText edtLimit;

    @BindView(R.id.tv_reim_authority)
    TextView tvReimAuthority;

    @BindView(R.id.tv_depart_leader)
    TextView tvDepartLeader;

    @BindView(R.id.tv_add)
    TextView tvAdd;
    private List<ReimCategoryBean> listReimCategory;
    private Context mContext;
    private int selectLeaderId;
    private int reimCatedgoryId;
    private DepartmentBean editBean;


    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_department);
        mContext = NewDeparmentActivity.this;
        allStatus = new ArrayList<>();
        listReimCategory = new ArrayList<>();
        getAuthority();
        //  initTestData();
        initViewEvent();
        //
        editBean = (DepartmentBean) getIntent().
                getSerializableExtra(AppConstant.SERIAL_KEY);
        if (editBean != null) {
            if (editBean.getType().equals("1")) {
                titleTxt.setText(getResources().getString(R.string.edit_depart));
            }
            edtDepartName.setText(editBean.getName());
            tvReimAuthority.setText(editBean.getPermname());
            tvDepartLeader.setText(editBean.getLname());
            tvReimAuthority.setTag(editBean.getPerm());
        }

    }

    private void initTestData() {
//        listReimCategory.add(new ReimCategoryBean(1, "管理"));
//        listReimCategory.add(new ReimCategoryBean(2, "管理2"));
//        listReimCategory.add(new ReimCategoryBean(3, "管理3"));
        for (int i = 0; i < listReimCategory.size(); i++) {
            allStatus.add(i, listReimCategory.get(i).getPerm());
        }
    }

    //获取报销权限
    private void getAuthority() {
        Observable<List<ReimCategoryBean>> observable = new DepartmentManageControl().getAuthority();
        CommonDialogObserver<List<ReimCategoryBean>> observer
                = new CommonDialogObserver<List<ReimCategoryBean>>(this) {

            @Override
            public void onNext(List<ReimCategoryBean> keyValueBeans) {
                super.onNext(keyValueBeans);
                SimpleToast.toastMessage("科目类别获取成功", Toast.LENGTH_SHORT);
                listReimCategory.clear();
                listReimCategory.addAll(keyValueBeans);
                for (int i = 0; i < listReimCategory.size(); i++) {
                    allStatus.add(i, listReimCategory.get(i).getPerm());
                }
            }

            @Override
            public void onError(Throwable t) {
                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_SHORT);
                } else {
                    SimpleToast.toastMessage(t.toString(), Toast.LENGTH_SHORT);
                }


                // toActivity(LoginActivity.class);
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, NewDeparmentActivity.this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_add:
                checkData();

                break;
            case R.id.tv_reim_authority:
                Loger.e("----authority--");

                selectAuthority();
                break;
            case R.id.tv_depart_leader:
                // toActivity(MembsersManageActivity.class);
                startActivityForResult(new Intent(this, MembsersSelectActivity.class), REQUEST_PERSON);
                break;
            default:
                break;
        }
    }

    private void selectAuthorityTest() {
        Resources res = getResources();
        String[] status = res.getStringArray(R.array.approval_no);
        for (int i = 0; i < status.length; i++) {
            allStatus.add(status[i]);
        }
        if (allStatus.size() > 0) {

            //  View view1 = TimePickerUtils.getInstance().
            // onListDataPicker(DepartmentsManageAddItemActivity.this, allStatus, tvAuthority);
            onListDataPicker(NewDeparmentActivity.this, allStatus, tvReimAuthority);

        }
    }


    private void selectAuthority() {
        if (listReimCategory.size() == 0) {
            SimpleToast.toastMessage("报销科目获取失败,不可进行此操作", Toast.LENGTH_SHORT);
            return;
        }

        TimePickerUtils.getInstance().onListBeanPicker(NewDeparmentActivity.this,
                listReimCategory, allStatus, tvReimAuthority);
    }

    private void checkData() {
        DepartmentBean requestBean = null;
        String notEmpty = "不可以为空！";
        String departmentNameStr = edtDepartName.getText().toString().trim();
        String limitStr = edtLimit.getText().toString().trim();
        String departmentLeaderStr = tvDepartLeader.getText().toString().trim();
        String authorityStr = tvReimAuthority.getText().toString().trim();

        reimCatedgoryId = (int) tvReimAuthority.getTag();
        Loger.e("reimtype" + authorityStr + " reimCategoryId=" + reimCatedgoryId);

        if (TextUtils.isEmpty(departmentNameStr)) {
            SimpleToast.toastMessage("部门名称" + notEmpty, Toast.LENGTH_LONG);
            return;
        }

//        if (TextUtils.isEmpty(limitStr)) {
//            SimpleToast.toastMessage("报销限额" + notEmpty, Toast.LENGTH_LONG);
//            return;
//        }
        if (TextUtils.isEmpty(authorityStr)) {
            SimpleToast.toastMessage("报销类别" + notEmpty, Toast.LENGTH_LONG);
            return;
        }
        if (TextUtils.isEmpty(departmentLeaderStr)) {
            SimpleToast.toastMessage("部门主管" + notEmpty, Toast.LENGTH_LONG);
            return;
        }
//        {"name":"研发部部",
//                "perm":1,
//                "leader":24
//        }


        requestBean = new DepartmentBean();
        requestBean.setName(departmentNameStr);
        requestBean.setPerm(reimCatedgoryId);
        requestBean.setLeader(selectLeaderId);
//        addDepartment(requestBean);


        if (editBean != null) {
            Loger.e("--update-manage");
            //去更新
            requestBean.setId(editBean.getId());
            updateDepartmentInfo(requestBean);
        } else {
            //去新增
            Loger.e("--add-manage");
            addDepartment(requestBean);
        }
    }

    private void addDepartment(DepartmentBean requestBean) {
        Observable<Boolean> observable = new DepartmentManageControl().addDepartment(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("成功", Toast.LENGTH_SHORT);
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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, NewDeparmentActivity.this);
    }


    private void updateDepartmentInfo(DepartmentBean requestBean) {

        Observable<Boolean> observable = new DepartmentManageControl().updateDepartment(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("成功", Toast.LENGTH_SHORT);
                    finish();

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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, NewDeparmentActivity.this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_PERSON) {
            Bundle bundle = data.getExtras();
            MembersBean userBean = (MembersBean) bundle.getSerializable("user");
            tvDepartLeader.setText(userBean.getName());
            selectLeaderId = Integer.valueOf(userBean.getId());


        }
    }

    @Override
    protected void initViewEvent() {
        tvDepartLeader.setOnClickListener(this);
        tvReimAuthority.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
        backBtn.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void onListDataPicker(Activity context, final List<String> allStatus, final View view) {
        final int[] pos = {0};
        final SinglePicker<String> picker = new SinglePicker<String>(context, allStatus);
        picker.setCanLoop(true);
        picker.setWheelModeEnable(false);
        picker.setItemWidth(200);
        picker.setTopPadding(15);
        picker.setTextSize(25);
        picker.setSelectedIndex(0);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int i, String s) {
                pos[0] = i;
                Loger.e("i," + i);
            }
        });

        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                Loger.e("viewName=" + (view instanceof TextView));
//                spu.setPos(String.valueOf(i));
//                selectId = deptRight.get(i).getKey();
//                reimbursementRightId = Byte.valueOf(selectId);
//                Loger.e("selectId==" + selectId);
                if (view instanceof TextView) {
                    Loger.e("view instanceof TextView---");
                    ((TextView) view).setText(allStatus.get(i));
                } else if (view instanceof EditText) {
                    ((EditText) view).setText(allStatus.get(i));
                }

                view.setTag(i);
            }
        });
        picker.setWeightEnable(true);

        if (view.getTag() == null) {
            view.setTag(0);
        }

        picker.show();
        Loger.e("view.selectPOs()" + view.getTag());

    }

}

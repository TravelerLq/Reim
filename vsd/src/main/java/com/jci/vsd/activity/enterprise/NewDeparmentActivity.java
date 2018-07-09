package com.jci.vsd.activity.enterprise;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.utils.Loger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.SinglePicker;

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_department);
        allStatus = new ArrayList<>();
        initViewEvent();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_add:
                checkData();
                finish();
                toActivity(DepartmentManageActivity.class);
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

    private void selectAuthority() {
        Resources res = getResources();
        String[] status = res.getStringArray(R.array.approval_no);
        for (int i = 0; i < status.length; i++) {
            allStatus.add(status[i]);
        }
        if (allStatus.size() > 0) {

            //  View view1 = TimePickerUtils.getInstance().
            // onListDataPicker(DepartmentsManageAddItemActivity.this, allStatus, tvAuthority);
            onListDataPicker(NewDeparmentActivity.this, allStatus, tvReimAuthority);

            // selectPos = (int)view1.getTag();

//                    selectId = deptRight.get(selectPos).getKey();
//                    Loger.e("--selectPos" + selectPos + "selectId--" + selectId);
        }
    }

    private void checkData() {
        String departmentNameStr = edtDepartName.getText().toString().trim();
        String limitStr = edtLimit.getText().toString().trim();
        String departmentLeaderStr = tvDepartLeader.getText().toString().trim();
        String authorityStr = tvReimAuthority.getText().toString().trim();
        if (TextUtils.isEmpty(departmentLeaderStr)) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_PERSON) {
            Bundle bundle = data.getExtras();
            MembersBean userBean = (MembersBean) bundle.getSerializable("user");
            tvDepartLeader.setText(userBean.getName());

        }
    }

    @Override
    protected void initViewEvent() {
        tvDepartLeader.setOnClickListener(this);
        tvReimAuthority.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
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

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
import com.jci.vsd.bean.enterprise.ProducerUpdateBean;
import com.jci.vsd.constant.AppConstant;
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
import io.reactivex.Observable;

/**
 * Created by liqing on 18/7/3.
 * 审批流程编辑
 */

public class ProducerEditActivity extends BaseActivity {

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
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    private List<ProducerSettingInfoBean.AvldptsBean> listDeparts = new ArrayList<>();
    private List<ProducerSettingInfoBean.AvlusersBean> listApprover = new ArrayList<>();
    List<Integer> selectDepsId = new ArrayList<>();

    String[] itemsDeparts = new String[]{""};
    String[] itemsApprovers = new String[]{""};
    private int selectApproverId;

    private List<Integer> depts = new ArrayList<>();
    private int approverId;
    private int id;
    private int fath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_edit);
        initViewEvent();
        getProducerSettingInfo();
        ProducerBean bean = (ProducerBean) getIntent().getSerializableExtra(AppConstant.SERIAL_KEY);
        if (bean != null) {
            /**
             * approveNumId : 24
             * approveNumName : 部门审批
             * approverId : 54
             * approverName : 王朕
             * approverOrder : 1
             */
            edtProducerName.setText(bean.getName());
            id = bean.getId();
            approverId = bean.getChecker();
            fath = bean.getFath();
            String dptName;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bean.getCoverdpts().size(); i++) {
                sb.append(bean.getCoverdpts().get(i).getName());
                depts.add(bean.getCoverdpts().get(i).getDpt());

            }
            String str = sb.toString();

            tvProducerDepart.setText(str);
            //tvProducerOrder.setText("wwe");
            tvProducerOrder.setText(bean.getSort() + "");
            tvProducerPerson.setText(bean.getCheckername());
        }
        stringAppend();

    }

    public void stringAppend() {
        String str = "";
        long start = System.currentTimeMillis();
        long end = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++)
            sb.append("a");
        str = sb.toString();
        end = System.currentTimeMillis();
        Loger.e("strAppend--" + str + "end-start=" + (end - start));
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
                if (listApprover.size() == 0) {
                    SimpleToast.toastMessage("暂无可配置部门", Toast.LENGTH_SHORT);
                } else {
                    showMultiChoiceDialog();
                }
                break;
            case R.id.tv_producer_order:
                Loger.e("click--tv_producer_level");
                SimpleToast.toastMessage("流程不可更改", Toast.LENGTH_SHORT);

                break;
            case R.id.tv_producer_person:
                Loger.e("click--tv_producer_person");
                if (listDeparts.size() == 0) {
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

        // updateBudgetItem()
        ProducerUpdateBean bean = new ProducerUpdateBean();
        bean.setName(name);
        bean.setId(id);
        bean.setChecker(approverId);
        bean.setDpts(depts);
        updateBudgetItem(bean);

//        toActivity(ProducerManageActivity.class);
//        this.finish();

    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
        tvProducerOrder.setOnClickListener(this);
        tvProducerDepart.setOnClickListener(this);
        tvProducerPerson.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.edit));
    }


    private void updateBudgetItem(ProducerUpdateBean requestBean) {
        Observable<Boolean> observable = new ProducerManageControl().updateProducer(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("更新成功", Toast.LENGTH_SHORT);
                    toActivity(ProducerManageActivity.class);
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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ProducerEditActivity.this);
    }

    private void getProducerSettingInfo() {
        Observable<ProducerSettingInfoBean> observable = new ProducerManageControl().getProducerSettingInfo();
        CommonDialogObserver<ProducerSettingInfoBean> observer = new CommonDialogObserver<ProducerSettingInfoBean>(this) {
            @Override
            public void onNext(ProducerSettingInfoBean bean) {
                super.onNext(bean);
                SimpleToast.toastMessage("流程可配置信息获取成功", Toast.LENGTH_SHORT);

                if (bean.getAvldpts() != null && bean.getAvldpts().size() > 0) {
                    //可选择的审核部门
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


                if (bean.getAvlusers() != null && bean.getAvlusers().size() > 0) {
                    //可选择的审核人

                    listApprover.clear();
                    listApprover.addAll(bean.getAvlusers());
                    //赋值给 approver items

                    itemsApprovers = new String[listApprover.size()];
                    for (int i = 0; i < listApprover.size(); i++) {
                        itemsApprovers[i] = listApprover.get(i).getName();
                        Loger.e("approver-" + listApprover.get(i).getName());

                    }
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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ProducerEditActivity.this);
    }

    //多选
    private void showMultiChoiceDialog() {

        selectDepsId.clear();
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("多选对话框")
                .setNegativeButton("取消", null).setPositiveButton("确定", null)
                .setMultiChoiceItems(itemsDeparts, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Loger.e("multi chocie--which" + which);
                        ProducerAddBean producerAddBean = new ProducerAddBean();
                        tvProducerDepart.setText(itemsDeparts[which]);
                        selectDepsId.add(listDeparts.get(which).getDpt());

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

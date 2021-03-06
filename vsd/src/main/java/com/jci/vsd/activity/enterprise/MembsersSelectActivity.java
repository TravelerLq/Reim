package com.jci.vsd.activity.enterprise;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.adapter.enterprise.MembersManagerAdapter;
import com.jci.vsd.bean.enterprise.AjustMembersBean;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.network.control.MembersManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;
import com.jci.vsd.view.widget.treelist.TreeMenuBaseAdapter;
import com.jci.vsd.view.widget.treelist.bean.Node;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/6/29.
 * 成员管理
 */

public class MembsersSelectActivity extends BaseActivity {


    private static final int REQUEST_CODE_DEPARTMENT = 1111;
    @BindView(R.id.lv_members)

    ListView lvMembers;
    private MembersManagerAdapter<MembersBean> adapter;
    private List<MembersBean> mData;

    private Dialog dialog;
    private View inflate;
    private TextView cancel;

    private Context context;
    private TextView tvAjust;
    private TextView tvDelete;
    private int deletePos;
    private MembersBean selectOperateBean;
    private List<MembersBean> departmentList;

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_manage);
        context = MembsersSelectActivity.this;
        mData = new ArrayList<>();
        departmentList = new ArrayList<>();
        // initTestData();
        initViewEvent();
        loadData();
//        lvMembers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                SimpleToast.toastMessage("长按－－", Toast.LENGTH_SHORT);
//                return true;
//            }
//        });
        //loadData();


    }


    public void showDialog() {
        dialog = new Dialog(this, R.style.BottomDialog);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_choose_role, null);
        tvDelete = (TextView) inflate.findViewById(R.id.tv_register_company);
        tvAjust = (TextView) inflate.findViewById(R.id.tv_select_company);
        cancel = (TextView) inflate.findViewById(R.id.btn_cancel);
        tvAjust.setText("调入其他部门");
        tvDelete.setText("从本公司删除");
        tvAjust.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        // WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //获得window窗口的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        dialogWindow.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(inflate);
        dialog.show();
    }


    private void initTreeMemberAdapter() {
        try {
            adapter = new MembersManagerAdapter<>(lvMembers, this, mData, 1);
            adapter.setListExpand(true);
            adapter.setTreeNodeClickListener(new TreeMenuBaseAdapter.OnTreeNodeClickListener() {
                @Override
                public void onNodeClick(Node node, int position) {
                    String pid = node.getParentID();
                    Loger.e("---nodeclickPos--" + position + "pid" + pid);
                    if (node.getChildrenNodes().size() == 0 && !pid.equals("0")) {

                        MembersBean bean = mData.get(position);
                        Intent mIntent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("user", mData.get(position));
                        Loger.e("--selectBean" + bean.getName() + "---id=" + bean.getId());
                        mIntent.putExtras(bundle);
                        // 设置结果，并进行传送
                        setResult(Activity.RESULT_OK, mIntent);
                        finish();
                    }
//                    if (!pid.equals("0")) {
//                        MembersBean bean = mData.get(position);
//                        Intent mIntent = new Intent();
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("user", mData.get(position));
//                        Loger.e("--selectBean" + bean.getName() + "---id=" + bean.getId());
//                        mIntent.putExtras(bundle);
//                        // 设置结果，并进行传送
////                        setResult(Activity.RESULT_OK, mIntent);
////                        finish();
////                        setResult(RESULT_OK, intent); //intent为A传来的带有Bundle的intent，当然也可以自己定义新的Bundle
////                        finish();//此处一定要调用finish()方法
//                    }

                    adapter.setCurrentPosition(position, node.isExpand());
                    adapter.notifyDataSetChanged();
                }

            });

            //

            adapter.setTreeNodeLongClickListener(new TreeMenuBaseAdapter.OnTreeNodeLongClickListener() {
                @Override
                public void onNodeLongClick(Node node, int position) {
                    //是个子节点
                    if (node.getChildrenNodes().size() == 0) {
                        selectOperateBean = mData.get(position);

                        Loger.e("--selectPersonName" + selectOperateBean.getName() +
                                "selectId" + selectOperateBean.getId());
                        showDialog();
                        //  SimpleToast.toastMessage("nodeLongClick－－", Toast.LENGTH_SHORT);

                        Loger.e("nodeLongClick--pos" + position);
                        deletePos = position;

//                        mData.remove(5);
//
//                        List<MembersBean> refreshData = new ArrayList<>();
//                        refreshData.addAll(mData);
//                        initTreeMemberAdapter();
//                         expandOrCollapse(position);//展开或关闭


                        for (int i = 0; i < mData.size(); i++) {
                            Loger.e(" mData.--" + mData.get(i).getName());
                        }

                    }

                    adapter.setCurrentPosition(position, node.isExpand());
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        lvMembers.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_DEPARTMENT) {

            }

        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_select_company:
//                //ajust 调整部门 用dialog
//                toActivityWithType(DepartmentManageActivity.class,
//                        AppConstant.VALUE_AJUST, REQUEST_CODE_DEPARTMENT, true);
                //调整部门，就直接用listDiaog ,带有选择的那种
                for (int i = 0; i < departmentList.size(); i++) {
                    if (departmentList.get(i).getId().equals(selectOperateBean.getPid())) {
                        departmentList.remove(i);
                    }
                }
                items = new String[departmentList.size()];

                for (int i = 0; i < departmentList.size(); i++) {
                    items[i] = departmentList.get(i).getName();
                }


                showSingleChoiceDialog();
//                TimePickerUtils.getInstance().onListBeanPicker();
                dialog.dismiss();
                break;
            case R.id.tv_register_company:
                //从本公司删除成员
                Loger.e("-----delete emplyee");

//                mData.remove(deletePos);
//                for (int i = 0; i < mData.size(); i++) {
//                    Loger.e("nodeName" + mData.get(i).getName());
//                }
//
//                adapter.notifyDataSetChanged();
                deleteMember(selectOperateBean);
                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    String[] items = new String[]{};

    private void selectDepartment() {

//        TimePickerUtils.getInstance().onListBeanPicker(MembsersManageActivity.this,
//                listReimCategory, allStatus, tvReimAuthority);

    }

    private void showSingleChoiceDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("单选对话框").setIcon(R.drawable.ic_image)
                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MembsersManageActivity.this, items[which], Toast.LENGTH_SHORT).show();
                        // SimpleToast.toastMessage("调入" + items[which] + "成功", Toast.LENGTH_SHORT);
                        //
                        Loger.e("choose-whitch--" + which);
                        AjustMembersBean ajustMembersBean = new AjustMembersBean();

                        ajustMembersBean.setDpt(Integer.valueOf(departmentList.get(which).getId()));
                        ajustMembersBean.setId(Integer.valueOf(selectOperateBean.getId()));
                        Loger.e("dptId=" + departmentList.get(which).getId());
                        Loger.e("ajustPersonid=" + selectOperateBean.getId());
                        ajust(ajustMembersBean);
                        dialog.dismiss();

                    }
                }).create();
        dialog.show();

    }

    @Override
    protected void initViewEvent() {
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.members_manage));
    }


    private void initTestData() {
        //parentItem
//        mData.add(new MembersBean("1", "0", "总经理办公室"));
//        mData.add(new MembersBean("2", "0", "销售部"));
//        //总经理下的childItem
//        mData.add(new MembersBean("3", "1", "张三"));
//        mData.add(new MembersBean("4", "1", "王一"));
//
//        //销售部下的
//
//        mData.add(new MembersBean("5", "2", "孙田"));
//        mData.add(new MembersBean("6", "2", "Jsec"));
//        mData.add(new MembersBean("7", "2", "Lotey"));

    }


    //加载MemberBean
    private void loadData() {
        Observable<List<MembersBean>> observable = new MembersManageControl().getMembers();
        CommonDialogObserver<List<MembersBean>> observer = new CommonDialogObserver<List<MembersBean>>(this) {
            @Override
            public void onNext(List<MembersBean> beanList) {
                super.onNext(beanList);
                mData.clear();
                mData.addAll(beanList);
                initTreeMemberAdapter();

                for (int i = 0; i < mData.size(); i++) {
                    if (mData.get(i).getPid().equals("0")) {
                        departmentList.add(mData.get(i));
                    }
                }


            }


            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MembsersSelectActivity.this);
    }

    //delete members

    private void deleteMember(MembersBean membersBean) {
        Observable<Boolean> observable = new MembersManageControl().deleteMember(membersBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("删除成功", Toast.LENGTH_LONG);
                    loadData();
                } else {
                    SimpleToast.toastMessage("删除失败，请重试！", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MembsersSelectActivity.this);
    }

    //调整到新部门 （ 新部门新增成员，就部门减一人）

    private void ajust(AjustMembersBean requestBean) {
        Observable<Boolean> observable = new MembersManageControl().ajustMember(requestBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    SimpleToast.toastMessage("操作成功", Toast.LENGTH_LONG);
                    loadData();
                } else {
                    SimpleToast.toastMessage("操作失败，请重试！", Toast.LENGTH_LONG);
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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, MembsersSelectActivity.this);
    }


}

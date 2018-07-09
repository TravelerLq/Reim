package com.jci.vsd.activity.enterprise;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.FillCodeActivity;
import com.jci.vsd.activity.FoundationActivity;
import com.jci.vsd.activity.MainActivity;
import com.jci.vsd.adapter.enterprise.MembersManagerAdapter;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;
import com.jci.vsd.view.widget.treelist.TreeMenuBaseAdapter;
import com.jci.vsd.view.widget.treelist.bean.Node;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 18/6/29.
 * 成员管理
 */

public class MembsersManageActivity extends BaseActivity {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_manage);
        context = MembsersManageActivity.this;
        mData = new ArrayList<>();
        initTestData();
        initLvMembers();
        initViewEvent();
//        lvMembers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                SimpleToast.toastMessage("长按－－", Toast.LENGTH_SHORT);
//                return true;
//            }
//        });

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


    private void initLvMembers() {
        try {
            adapter = new MembersManagerAdapter<>(lvMembers, this, mData, 0);
            //  adapter.setListExpand(false);
            adapter.setTreeNodeClickListener(new TreeMenuBaseAdapter.OnTreeNodeClickListener() {
                @Override
                public void onNodeClick(Node node, int position) {

//                    Loger.e("---node--");
                    if (node.getChildrenNodes().size() == 0) {
                        showDialog();
                        SimpleToast.toastMessage("node－－", Toast.LENGTH_SHORT);
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
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_select_company:
                //ajust to
                dialog.dismiss();
                break;
            case R.id.tv_register_company:
                //从本公司删除
                Loger.e("-----delete emplyee");
                toActivity(FoundationActivity.class);

                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initViewEvent() {

    }


    private void initTestData() {
        //parentItem
        mData.add(new MembersBean("1", "0", "总经理办公室"));
        mData.add(new MembersBean("2", "0", "销售部"));
        //总经理下的childItem
        mData.add(new MembersBean("3", "1", "张三"));
        mData.add(new MembersBean("4", "1", "王一"));

        //销售部下的

        mData.add(new MembersBean("5", "2", "孙田"));
        mData.add(new MembersBean("6", "2", "Jsec"));
        mData.add(new MembersBean("7", "2", "Lotey"));

    }
}

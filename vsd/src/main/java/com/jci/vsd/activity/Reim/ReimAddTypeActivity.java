package com.jci.vsd.activity.Reim;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.bean.CatBean;
import com.jci.vsd.data.ReimCategoryData;
import com.jci.vsd.fragment.reim.ReimBusinessTripFragment;
import com.jci.vsd.fragment.reim.ReimMeetingFragment;
import com.jci.vsd.fragment.reim.ReimTreatFragment;
import com.jci.vsd.utils.Loger;
import com.warmtel.expandtab.ExpandPopTabView;
import com.warmtel.expandtab.KeyValueBean;
import com.warmtel.expandtab.PopTwoListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 18/8/2.
 */

public class ReimAddTypeActivity extends BaseActivity {

    private String strReim;
    @BindView(R.id.ll_reim_type)
    LinearLayout llReimType;

    @BindView(R.id.tv_money)
    TextView tvReimType;

    @BindView(R.id.expandable_list_view)
    ExpandPopTabView expandPopTabView;

    @BindView(R.id.frame_reim_type)
    FrameLayout fragmentContent;

    //expandapoTab
    private PopTwoListView popTwoListView;
    private List<KeyValueBean> parentsList = new ArrayList<>();
    List<ArrayList<KeyValueBean>> childList = new ArrayList<>();
    private Context context;

    private List<CatBean> catBeanList = new ArrayList<>();
    //科目三
    private List<KeyValueBean> thirdList = new ArrayList<>();
    private int categoryId = 2;
    private int itemId;
    private CatBean catBean;

    private ReimMeetingFragment reimMeetingFragment;

    private ReimTreatFragment reimTreatFragment;

    private ReimBusinessTripFragment reimBusinessFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reim_add_type);
        initViewEvent();
        context = ReimAddTypeActivity.this;
        catBeanList = ReimCategoryData.getCategoryList();


        if (catBeanList != null && catBeanList.size() != 0) {
            parentsList.clear();
            for (int i = 0; i < catBeanList.size(); i++) {
                catBean = catBeanList.get(i);
                parentsList.add(new KeyValueBean(String.valueOf(catBeanList.get(i).getCat()), catBean.getCname()));
            }

            initExpandaTabView();
        } else {
            //开启线程加载科目类别

        }


        initTreatFragment(R.id.frame_reim_type, reimTreatFragment);


    }


    @Override
    protected void initViewEvent() {
        llReimType.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ll_reim_type:
                Loger.e("---reim_type-click" + expandPopTabView.isShown());

                if (expandPopTabView.isShown()) {
                    Loger.e("--expandPop--");
                    expandPopTabView.onExpandPopView();
                }
                break;
            default:
                break;
        }


    }

    //init expandableView data

    private void initExpandaTabView() {
        //数据初始化
        setSecondMenuData();
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

                //没有三级科目
                if (childrenKey.equals("0")) {
                    Loger.e("---integer.(-1)=" + Integer.valueOf("0"));
                    itemId = Integer.valueOf("0");
                    Loger.e("");

                } else {
                    itemId = Integer.valueOf(childrenKey);
                    tvReimType.setText(showText);
                }

                // reimTypeLayout(categoryId, itemId);

                // initFragment(categoryId, itemId);

                initFragment(categoryId);
            }

            @Override
            public void getParentValue(int position, String showText, String key) {
                Log.e("－－－－", "二级－－－－ :" + showText + " ,二级key :" + key);
                categoryId = Integer.valueOf(key);
                //  categoryName = showText;
                tvReimType.setText(showText);

                getItemdata(position);

            }

        });
        expandTabView.addItemToExpandTab("", popTwoListView);

    }

    private void getItemdata(int pos) {
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

        if (thirdList.size() == 0) {
            thirdList.add(new KeyValueBean("0", "无三级科目"));
        }
        if (popTwoListView == null) {
            popTwoListView = new PopTwoListView(this);
        }

        popTwoListView.refreshChild(thirdList);
    }

    private void initFragment(int categoryId) {
        switch (categoryId) {
            case 1:
                initMeetingFragment(R.id.frame_reim_type, reimMeetingFragment);
                break;
            case 2:
                initTreatFragment(R.id.frame_reim_type, reimTreatFragment);
                break;
            case 3:
                break;
            case 7:
                initBusinessTripFragment(R.id.frame_reim_type, reimBusinessFragment);
                break;
            default:
                break;
        }
    }


    //cat =1 会议费
    private void initMeetingFragment(int layoutId, ReimMeetingFragment fragment) {
        if (fragment == null) {
            fragment = new ReimMeetingFragment();
            Bundle bundle = new Bundle();
            bundle.putString("itemId", itemId + "");
            fragment.setArguments(bundle);

        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layoutId, fragment);
        transaction.commit();


    }


    //cat =2 招待类
    private void initTreatFragment(int layoutId, ReimTreatFragment fragment) {
        if (fragment == null) {
            fragment = new ReimTreatFragment();
            Bundle bundle = new Bundle();
            bundle.putString("itemId", itemId + "");
            fragment.setArguments(bundle);

        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layoutId, fragment);
        transaction.commit();


    }

    //cat =7 差旅费

    private void initBusinessTripFragment(int layoutId, ReimBusinessTripFragment fragment) {
        if (fragment == null) {
            fragment = new ReimBusinessTripFragment();
            Bundle bundle = new Bundle();
            bundle.putString("itemId", itemId + "");
            fragment.setArguments(bundle);

        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(layoutId, fragment);
        transaction.commit();


    }
}

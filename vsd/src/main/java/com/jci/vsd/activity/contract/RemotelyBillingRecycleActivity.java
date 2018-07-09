package com.jci.vsd.activity.contract;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.SlideRecycleview.CommonAdapter;
import com.jci.vsd.SlideRecycleview.SwipeMenuLayout;
import com.jci.vsd.SlideRecycleview.ViewHolder;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.bean.contract.ContractBean;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.utils.PhoneUtils;
import com.jci.vsd.utils.PopupWindowUtil;
import com.jci.vsd.utils.ScreenUtil;
import com.jci.vsd.view.ItemDecor;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 18/7/4.
 * 远程开票 （列表）
 */

public class RemotelyBillingRecycleActivity extends BaseActivity {

    @BindView(R.id.recycle_remotely_billing)
    RecyclerView recyclerView;

    //popwindown
    @BindView(R.id.tv_select)
    TextView tvSelect;

    //sppiner
    @BindView(R.id.sp_select)
    Spinner spinner;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    private LinearLayoutManager layoutManager;
    private Context context;
    private List<ContractBean> mDatas;
    private CommonAdapter<ContractBean> mAdapter;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rightFucTxt)
    TextView tvRight;

    private View popWindownView;
    private PopupWindow mPopupWindow;


    private int tvSelectWidth;
    private String strContract;
    private String strNoContract;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remotely_billing);
        mDatas = new ArrayList<>();
        context = RemotelyBillingRecycleActivity.this;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        int left = (int) getResources().getDimension(R.dimen.dimen_10);
        int right = (int) getResources().getDimension(R.dimen.dimen_10);
        int bottom = (int) getResources().getDimension(R.dimen.dimen_30);
        recyclerView.addItemDecoration(new ItemDecor(left, right, bottom));
        tvRight.setVisibility(View.VISIBLE);
        initData();
        initRecycleView();
        initViewEvent();
        //  initPopwindownView();
        // initSpinner();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });
    }


    private void initSpinner() {
        spinner.setDropDownHorizontalOffset(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mDatas.add(new ContractBean("CA商务合同" + i, "合同内容的执行从" + i, "13:3" + i + "PM"));
        }
    }


    private void initRecycleView() {

        mAdapter = new CommonAdapter<ContractBean>(context, mDatas, R.layout.item_contract_swipe) {
            @Override
            public void convert(final ViewHolder holder, ContractBean swipeBean) {
                //设置是否开启左滑出菜单，设置false 为右滑出菜单
                // ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(mIndex == 0 ? false : true);// 并依次打开左滑右滑
                ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(true);
                //    holder.setText(R.id.content, swipeBean.name + (mIndex == 0 ? "我左青龙" : "我右白虎"));
                TextView tvContractTitle = holder.getView(R.id.tv_contract_title);
                tvContractTitle.setText(swipeBean.getTitle());

                TextView tvContractContent = holder.getView(R.id.tv_contract_content);
                tvContractContent.setText(swipeBean.getContent());

                TextView tvContractTime = holder.getView(R.id.tv_contract_time);
                tvContractTime.setText(swipeBean.getTime());
                //验证长按
                holder.setOnLongClickListener(R.id.content, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(mContext, "longclig", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
                        return false;
                    }
                });

                holder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        if (pos >= 0 && pos < mDatas.size()) {
                            Toast.makeText(context, "删除:" + pos, Toast.LENGTH_SHORT).show();
                            mDatas.remove(pos);
                            mAdapter.notifyItemRemoved(pos);//推荐用这个
                            //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                            //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                            //mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
                (holder).setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).getTitle(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onClick() called with: v = [" + v + "]");
                        // toAtivityWithParams();
                        toActivity(NewBillingInfoActivity.class);
                    }
                });
                //置顶：
                holder.setOnClickListener(R.id.btnTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();

                        if (pos > 0 && pos < mDatas.size()) {
                            ContractBean swipeBean = mDatas.get(pos);
                            mDatas.remove(swipeBean);
                            mAdapter.notifyItemInserted(0);
                            mDatas.add(0, swipeBean);
                            mAdapter.notifyItemRemoved(pos + 1);
                            if (layoutManager.findFirstVisibleItemPosition() == 0) {
                                recyclerView.scrollToPosition(0);
                            }
                            //notifyItemRangeChanged(0,holder.getAdapterPosition()+1);
                        }

                    }
                });


            }
        };

        recyclerView.setAdapter(mAdapter);
    }

    //初始化popwindown
    private void initPopwindownView() {


        popWindownView = View.inflate(context, R.layout.popupwindow_selector, null);
        RadioGroup group = (RadioGroup) popWindownView.findViewById(R.id.rg_billing);

        final RadioButton rbAll = (RadioButton) popWindownView.findViewById(R.id.rb_all);
        final RadioButton rbContract = (RadioButton) popWindownView.findViewById(R.id.rb_contract);
        final RadioButton rbNoContract = (RadioButton) popWindownView.findViewById(R.id.rb_no_contract);


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_all:
                        rbAll.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_checked, 0, 0, 0);
                        rbContract.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        rbNoContract.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                    case R.id.rb_contract:
                        rbAll.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        rbContract.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_checked, 0, 0, 0);
                        rbNoContract.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        break;
                    case R.id.rb_no_contract:
                        rbAll.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        rbContract.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        rbNoContract.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_checked, 0, 0, 0);
                        break;
                    default:
                        break;

                }

                mPopupWindow.dismiss();
                //refrsh the list（）；


            }


        });

    }


    @Override
    protected void initViewEvent() {
        tvRight.setOnClickListener(this);
        tvSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rightFucTxt:
                toActivity(ContractAddActivtiy.class);
                break;

            case R.id.tv_select:
                // showSelect();
                //createPopWindown();
                showPopWindow(tvSelect, 0);

                break;
            default:
                break;
        }
    }

    private void showPopWindow(View anchorView, int pos) {
        View contentView = getPopupWindowContentView(pos);


        mPopupWindow = new PopupWindow(contentView, tvSelectWidth
                , ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        int windowPos[] = PopupWindowUtil.calculatePopWindowPos(anchorView, contentView);
        //  int xOff = 20; // 可以自己调整偏移
        int xOff = 0; // 可以自己调整偏移
        windowPos[0] -= xOff;
        //  mPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
        mPopupWindow.showAsDropDown(anchorView, 0, 2);

    }

    private View getPopupWindowContentView(final int pos) {

        final View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_menu_billing, null);
        View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (view.getId() == R.id.tv_contract_selection) {
                    tvSelect.setText(strContract);
                } else {
                    tvSelect.setText(strNoContract);
                }

                mPopupWindow.dismiss();
            }
        };

        TextView tvContractSelect = (TextView) contentView.findViewById(R.id.tv_contract_selection);
        strContract = tvContractSelect.getText().toString();
        tvContractSelect.setOnClickListener(menuItemOnClickListener);
        TextView tvNoContractSelect = (TextView) contentView.findViewById(R.id.tv_no_contract_selection);
        tvNoContractSelect.setOnClickListener(menuItemOnClickListener);
        strNoContract = tvNoContractSelect.getText().toString();
        return contentView;
    }


    private void showSelect() {

        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            return;
        }

        if (mPopupWindow == null)
            mPopupWindow = createPopWindown(popWindownView);

        mPopupWindow.setWidth(PhoneUtils.getInstance(this).getScreenW());
        mPopupWindow.setHeight(300);
        mPopupWindow.showAsDropDown(tvSelect, 0, 0);
    }

    //
    private PopupWindow createPopWindown(View view) {
        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(view);
        ColorDrawable colorDrawable = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(colorDrawable);
        return popupWindow;
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            tvSelectWidth = tvSelect.getMeasuredWidth();

            Loger.e("tvSelectWidth-=" + tvSelectWidth);
        }
    }
}

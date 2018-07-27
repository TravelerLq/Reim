package com.jci.vsd.activity.enterprise;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.SlideRecycleview.CommonAdapter;
import com.jci.vsd.SlideRecycleview.SwipeMenuLayout;
import com.jci.vsd.SlideRecycleview.ViewHolder;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.control.BudgetManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/7/2.
 * 预算管理(按照部门划分）
 */

public class BudgetByDepartManageActivity extends BaseActivity {
    private static final String TAG = BudgetByDepartManageActivity.class.getSimpleName();

    @BindView(R.id.recycle_budget)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private CommonAdapter<BudgetBean> mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<BudgetBean> mDatas;
    private Context context;
    private PopupWindow mPopupWindow;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.rightFucTxt)
    TextView rightFucTxt;
    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    private static int type = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_manage);
        initViewEvent();
        context = BudgetByDepartManageActivity.this;
        rightFucTxt.setVisibility(View.VISIBLE);
       //   tvTitle.setText("预算管理（按部门划分）");
        // type=1
        getData(type);
        intData();
        initRecycleView();
        initViewEvent();

    }


    private void intData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            // mDatas.add(new BudgetBean("部门" + i, i * 10 + i));
        }

    }

    @Override
    protected void initViewEvent() {
        rightFucTxt.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.budget_depart));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rightFucTxt:
                Loger.e("--add reim depart");
                //     toActivity(EditBudgetItemActivity.class);
                toActivityWithType(BudgetAddItemActivity.class, AppConstant.VALUE_BUDGET_DEPART);
                break;
            case R.id.btn_sure:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Loger.e(TAG + "-----onResume");
        //
        getData(type);
    }

    private void initRecycleView() {

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mAdapter = new CommonAdapter<BudgetBean>(context, mDatas, R.layout.item_buget_swipe) {
            @Override
            public void convert(final ViewHolder holder, BudgetBean swipeBean) {
                //设置是否开启左滑出菜单，设置false 为右滑出菜单
                // ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(mIndex == 0 ? false : true);// 并依次打开左滑右滑
                ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(true);
//
                TextView tvName = (TextView) holder.getView(R.id.edt_budget_deparment);
                TextView tvBudget = (TextView) holder.getView(R.id.edt_budget_account);
                tvName.setText(swipeBean.getName());
                tvBudget.setText(swipeBean.getUsed() + "" + "/" + swipeBean.getQuota() + "");
                //  tvBudget.setText(swipeBean.getBudget() + "");
//                tvName.setFocusable(false);
//                tvBudget.setFocusable(false);
//                tvName.setFocusable(false);
//
//                tvName.setFocusableInTouchMode(false);
//
//                tvBudget.setFocusable(false);
//                tvBudget.setFocusableInTouchMode(false);


                //验证长按
                holder.setOnLongClickListener(R.id.content, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int pos = holder.getLayoutPosition();
                        Toast.makeText(mContext, "longclig", Toast.LENGTH_SHORT).show();

                        // mAdapter.notifyDataSetChanged();
                        return true;
                    }
                });

                holder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        if (pos >= 0 && pos < mDatas.size()) {
                            //   Toast.makeText(context, "删除:" + pos, Toast.LENGTH_SHORT).show();
                            deleteBudgetItem(mDatas.get(pos).getId(), pos);
//                            mDatas.remove(pos);
//                            mAdapter.notifyItemRemoved(pos);//推荐用这个
                            //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                            //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                            //mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                //edit
                holder.setOnClickListener(R.id.btnEdit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mDatas.get(pos).setType(AppConstant.VALUE_BUDGET_DEPART);
//
                        toAtivityWithParams(EditBudgetItemActivity.class, mDatas.get(pos));


                    }
                });
                //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
                (holder).setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onClick() called with: v = [" + v + "]");
                    }
                });


            }
        };

        recyclerView.setAdapter(mAdapter);
        //mRecyclerView.setLayoutManager(mLayoutManager = new GridLayoutManager(getContext(), 2));
        recyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(this));

        //6 2016 10 21 add , 增加viewChache 的 get()方法，
        // 可以用在：当点击外部空白处时，关闭正在展开的侧滑菜单。我个人觉得意义不大，
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                    if (null != viewCache) {
                        viewCache.smoothClose();
                    }
                }
                return false;
            }
        });

    }

    private void getData(int type) {
        Observable<List<BudgetBean>> observable = new BudgetManageControl().getBudgetList(type);
        CommonDialogObserver<List<BudgetBean>> observer = new CommonDialogObserver<List<BudgetBean>>(this) {
            @Override
            public void onNext(List<BudgetBean> budgetBeans) {
                super.onNext(budgetBeans);
                mDatas.clear();
                mDatas.addAll(budgetBeans);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_SHORT);
                    exit();
                    toActivity(LoginActivity.class);
                } else {
                    // SimpleToast.toastMessage(t.toString(), Toast.LENGTH_LONG);
                }
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, BudgetByDepartManageActivity.this);

    }

    //删除
    private void deleteBudgetItem(int departId, final int pos) {
        BudgetBean budgetBean = new BudgetBean();
        budgetBean.setId(departId);
        Observable<Boolean> observable = new BudgetManageControl().deleteBudget(budgetBean);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean isSuccess) {
                super.onNext(isSuccess);
                SimpleToast.toastMessage("删除成功", Toast.LENGTH_SHORT);
                mDatas.remove(pos);
                mAdapter.notifyItemRemoved(pos);//推荐用这个
                getData(type);

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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, BudgetByDepartManageActivity.this);


    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        // activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }


}

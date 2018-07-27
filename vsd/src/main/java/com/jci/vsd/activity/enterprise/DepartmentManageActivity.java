package com.jci.vsd.activity.enterprise;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anupcowkur.reservoir.Reservoir;
import com.jci.vsd.R;
import com.jci.vsd.SlideRecycleview.CommonAdapter;
import com.jci.vsd.SlideRecycleview.SwipeMenuLayout;
import com.jci.vsd.SlideRecycleview.ViewHolder;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.activity.Reim.ReimRecycActivity;
import com.jci.vsd.bean.SwipeBean;
import com.jci.vsd.bean.enterprise.DepartmentBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.data.DepartmentData;
import com.jci.vsd.network.control.DepartmentManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.ItemDecor;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.unitid.spark.cm.sdk.data.entity.Item;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/7/2.
 * 部门管理
 */

public class DepartmentManageActivity extends BaseActivity {
    @BindView(R.id.recycle_department)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Context context;
    private List<DepartmentBean> mDatas;
    private CommonAdapter<DepartmentBean> mAdapter;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rightFucTxt)
    TextView tvRight;
    private String keyStr;

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_manage);
        mDatas = new ArrayList<>();
        context = DepartmentManageActivity.this;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new ItemDecor(40));
        tvRight.setVisibility(View.VISIBLE);
        //initData();
        initRecycleView();
        initViewEvent();
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
        if (getIntent() != null) {
            keyStr = getIntent().getStringExtra(AppConstant.KEY_TYPE);

        }

        getDepartment();
    }


    private void initData() {
        mDatas = new ArrayList<>();
        DepartmentBean bean;
        for (int i = 0; i < 5; i++) {
            bean = new DepartmentBean();
            bean.setId(i);
            bean.setName("name" + i);
            mDatas.add(bean);
        }

    }


    private void initRecycleView() {

        mAdapter = new CommonAdapter<DepartmentBean>(context, mDatas, R.layout.item_department_swipe) {
            @Override
            public void convert(final ViewHolder holder, DepartmentBean swipeBean) {
                //设置是否开启左滑出菜单，设置false 为右滑出菜单
                // ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(mIndex == 0 ? false : true);// 并依次打开左滑右滑
                ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(true);
                //    holder.setText(R.id.content, swipeBean.name + (mIndex == 0 ? "我左青龙" : "我右白虎"));
                TextView tvDepartmentName = holder.getView(R.id.tv_depart_name);
                tvDepartmentName.setText(swipeBean.getName());
                //验证长按
                holder.setOnLongClickListener(R.id.content, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(mContext, "longclig", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
                        return false;
                    }
                });

                //左滑删除
                holder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        if (pos >= 0 && pos < mDatas.size()) {
                            // Toast.makeText(context, "删除:" + pos, Toast.LENGTH_SHORT).show();
                            deleteDepartment(mDatas.get(pos).getId(), pos);
//                            mDatas.remove(pos);
//                            mAdapter.notifyItemRemoved(pos);//推荐用这个

                            //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                            //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                            //mAdapter.notifyDataSetChanged();
                        }
                    }
                });


                //编辑
                holder.setOnClickListener(R.id.btnEdit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        if (pos >= 0 && pos < mDatas.size()) {
                            Toast.makeText(context, "编辑:" + pos, Toast.LENGTH_SHORT).show();
                            DepartmentBean editBean = mDatas.get(pos);
                            toAtivityWithParams(NewDeparmentActivity.class, editBean);
                        }
                    }
                });

                //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
                (holder).setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).getName(), Toast.LENGTH_SHORT).show();
                        int pos = holder.getAdapterPosition();
                        DepartmentBean bean = mDatas.get(pos);
                        toAtivityWithParams(DepartmentDetailActivity.class, bean);
                        Log.d("TAG", "onClick() called with: v = [" + pos + "]");
                        if (!TextUtils.isEmpty(keyStr) && keyStr.equals(AppConstant.VALUE_AJUST)) {
                            Intent intent = new Intent(DepartmentManageActivity.this,
                                    DepartmentDetailActivity.class);
                            // intent.putExtra("departId", mDatas.get(holder.getAdapterPosition()).getDepartmentName()) )
//                            Bundle bundle = new Bundle();
//                            DepartmentBean bean = mDatas.get();
//                            bundle.putSerializable(AppConstant.SERIAL_KEY, bean);
//                            intent.putExtras(bundle);
//                            startActivity(intent);


                        }
                    }
                });
                //置顶：
                holder.setOnClickListener(R.id.btnTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        if (pos > 0 && pos < mDatas.size()) {
                            DepartmentBean swipeBean = mDatas.get(pos);
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

    // 获取部门
    private void getDepartment() {
        Observable<List<DepartmentBean>> observable = new DepartmentManageControl().getDepartment();
        CommonDialogObserver<List<DepartmentBean>> observer = new CommonDialogObserver<List<DepartmentBean>>(this) {
            @Override
            public void onNext(List<DepartmentBean> beanList) {
                super.onNext(beanList);
                SimpleToast.toastMessage("获取成功", Toast.LENGTH_SHORT);
                if (beanList != null) {
                    mDatas.clear();
                    mDatas.addAll(beanList);
                    DepartmentData.saveDepartmentList(mDatas);
                    mAdapter.notifyDataSetChanged();

                }

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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, DepartmentManageActivity.this);

    }

    //删除
    private void deleteDepartment(int departId, final int pos) {

        Observable<Boolean> observable = new DepartmentManageControl().deleteDepartment(departId);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean isSuccess) {
                super.onNext(isSuccess);
                SimpleToast.toastMessage("删除成功", Toast.LENGTH_SHORT);
                mDatas.remove(pos);
                mAdapter.notifyItemRemoved(pos);//推荐用这个

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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, DepartmentManageActivity.this);


    }

    @Override
    protected void initViewEvent() {
        tvRight.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.department_manage));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rightFucTxt:
                //  toActivity();
                finish();
                toActivity(NewDeparmentActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        Loger.e("--resume-departManage");
        super.onResume();
        getDepartment();
    }
}

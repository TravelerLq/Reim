package com.jci.vsd.activity.enterprise;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.SlideRecycleview.CommonAdapter;
import com.jci.vsd.SlideRecycleview.SwipeMenuLayout;
import com.jci.vsd.SlideRecycleview.ViewHolder;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.bean.enterprise.DepartmentBean;
import com.jci.vsd.bean.enterprise.ProducerBean;
import com.jci.vsd.network.control.DepartmentManageControl;
import com.jci.vsd.network.control.ProducerManageControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.view.widget.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/7/3.
 * 审批流程管理
 */

public class ProducerManageActivity extends BaseActivity {
    @BindView(R.id.recycle_producer)
    RecyclerView recyclerView;
    @BindView(R.id.rightFucTxt)
    TextView rightFucTxt;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private CommonAdapter<ProducerBean> mAdapter;
    private Context context;
    private List<ProducerBean> mDatas;
    private LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_manage);
        initViewEvent();
        context = ProducerManageActivity.this;
        rightFucTxt.setVisibility(View.VISIBLE);
        getData();
        //test
        intData();
        initRecycleView();
    }


    private void intData() {
        mDatas = new ArrayList<>();
        ProducerBean bean;
        for (int i = 0; i < 5; i++) {
            bean = new ProducerBean();
            //   bean.setApproveNumName("boss approval" + i);


//         bean.
            // mDatas.add(new ProducerBean("boss审批", "jess", 1, "技术部门"));


        }
    }


    private void initRecycleView() {

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mAdapter = new CommonAdapter<ProducerBean>(context, mDatas, R.layout.item_producer_swipe) {
            @Override
            public void convert(final ViewHolder holder, ProducerBean swipeBean) {
                //设置是否开启左滑出菜单，设置false 为右滑出菜单
                // ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(mIndex == 0 ? false : true);// 并依次打开左滑右滑
                ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(true);
//
                TextView tvName = (TextView) holder.getView(R.id.tv_producer_name);
                TextView tvLevel = (TextView) holder.getView(R.id.tv_producer_level);
                TextView tvProducerPerson = (TextView) holder.getView(R.id.tv_producer_person);
                tvName.setText(swipeBean.getName());
                tvLevel.setText(swipeBean.getSort() + "");
                tvProducerPerson.setText(swipeBean.getCheckername());
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

                            delete(mDatas.get(pos).getId(), pos);
                            //  Toast.makeText(context, "删除:" + pos, Toast.LENGTH_SHORT).show();
//                            mDatas.remove(pos);
//                            mAdapter.notifyItemRemoved(pos);//推荐用这个
                            //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                            //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                            //mAdapter.notifyDataSetChanged();
                        }
                    }
                });
                holder.setOnClickListener(R.id.btnEdit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
//
                        //   toAtivityWithParams(EditBudgetItemActivity.class, mDatas.get(pos));
                        toAtivityWithParams(ProducerEditActivity.class, mDatas.get(pos));

                    }
                });
                //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
                (holder).setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).getDepartmentName(), Toast.LENGTH_SHORT).show();
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


    @Override
    protected void initViewEvent() {
        rightFucTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rightFucTxt:
                toActivity(ProducerAddActivity.class);
                finish();
                break;
            case R.id.btn_sure:
                break;
            default:
                break;
        }
    }


    // 获取
    private void getData() {
        Observable<List<ProducerBean>> observable = new ProducerManageControl().getProducerList();
        CommonDialogObserver<List<ProducerBean>> observer = new CommonDialogObserver<List<ProducerBean>>(this) {
            @Override
            public void onNext(List<ProducerBean> beanList) {
                super.onNext(beanList);
                SimpleToast.toastMessage("获取成功", Toast.LENGTH_SHORT);
                if (beanList != null) {
                    mDatas.clear();
                    mDatas.addAll(beanList);
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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ProducerManageActivity.this);

    }


    //删除
    //删除
    private void delete(int departId, final int pos) {

        Observable<Boolean> observable = new ProducerManageControl().deleteProducer(departId);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean isSuccess) {
                super.onNext(isSuccess);
                SimpleToast.toastMessage("删除成功", Toast.LENGTH_SHORT);
                mDatas.remove(pos);
                mAdapter.notifyItemRemoved(pos);//推荐用这个
                getData();

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
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ProducerManageActivity.this);


    }

}

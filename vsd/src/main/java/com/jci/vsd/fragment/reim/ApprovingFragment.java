package com.jci.vsd.fragment.reim;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.SlideRecycleview.CommonAdapter;
import com.jci.vsd.SlideRecycleview.SwipeMenuLayout;
import com.jci.vsd.SlideRecycleview.ViewHolder;
import com.jci.vsd.activity.LoginActivity;
import com.jci.vsd.activity.Reim.MyReimApprovalDetailActivity;
import com.jci.vsd.bean.reim.ApprovalBean;
import com.jci.vsd.fragment.main.BaseFragment;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.DialogObserverHolder;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by liqing on 18/6/28.
 * 审批中fragment type = 1
 * type = 1 表示查询我的正在被审批的报销单
 * type = 2 表示查询我的已经审批完成（包括通过和驳回）的报销单
 */

public class ApprovingFragment extends BaseFragment implements DialogObserverHolder {

    private Context context;
    private List<ApprovalBean> mData;

    private CommonAdapter<ApprovalBean> mAdapter;
    private LinearLayoutManager mLayoutManager;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private int type = 1;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approving, container, false);
        context = ApprovingFragment.this.getActivity();
        mData = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rl_approving);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        getData(type);
        //  initDatas();
        initAdapter();


        mAdapter = new CommonAdapter<ApprovalBean>(context, mData, R.layout.item_approving_swipe) {
            @Override
            public void convert(final ViewHolder holder, ApprovalBean approvalBean) {
                ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(true);
                TextView tv = (TextView) holder.getView(R.id.tv_money);
                TextView tvName = (TextView) holder.getView(R.id.tv_name);
                TextView tvTime = (TextView) holder.getView(R.id.tv_expense_time);
                TextView tvNum = (TextView) holder.getView(R.id.tv_expense_num);
                TextView tvApprovalResult = (TextView) holder.getView(R.id.tv_department);
                tv.setText(approvalBean.getTotal());
                tvName.setText(approvalBean.getAppl());
                tvTime.setText(approvalBean.getDate());
                tvNum.setText(approvalBean.getAnnex());
                //  tvApprovalResult.setText(approvalBean.get);


                holder.setOnClickListener(R.id.btnDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        if (pos >= 0 && pos < mDatas.size()) {
                            Toast.makeText(getContext(), "删除:" + pos, Toast.LENGTH_SHORT).show();
                            mDatas.remove(pos);
                            mAdapter.notifyItemRemoved(pos);//推荐用这个
                            //如果删除时，不使用mAdapter.notifyItemRemoved(pos)，则删除没有动画效果，
                            //且如果想让侧滑菜单同时关闭，需要同时调用 ((SwipeMenuLayout) holder.itemView).quickClose();
                            //mAdapter.notifyDataSetChanged();
                            delete(mData.get(pos).getId(), pos);
                        }
                    }
                });

                //注意事项，设置item点击，不能对整个holder.itemView设置咯，只能对第一个子View，即原来的content设置，这算是局限性吧。
                (holder).setOnClickListener(R.id.content, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
//                       getActivity(). toActivity(MyReimApprovalDetailActivity.class);
                        Loger.e("--reim list-id" + mData.get(pos).getId());
                        toActivityWithId(ApprovingFragment.this, MyReimApprovalDetailActivity.class, String.valueOf(mData.get(pos).getId()));
                        //  Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).name, Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onClick() called with: v = [" + v + "]");
                        getActivity().finish();
                    }
                });

                //验证长按
                holder.setOnLongClickListener(R.id.content, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(mContext, "longclig", Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onLongClick() called with: v = [" + v + "]");
                        return false;
                    }
                });

            }
        };

        //setDapter

        mRecyclerView.setAdapter(mAdapter);
        //mRecyclerView.setLayoutManager(mLayoutManager = new GridLayoutManager(getContext(), 2));
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(getContext()));

        //6 2016 10 21 add , 增加viewChache 的 get()方法，
        // 可以用在：当点击外部空白处时，关闭正在展开的侧滑菜单。我个人觉得意义不大，
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
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
        return view;
    }

    private void initAdapter() {
    }


    private void getData(int type) {
        Observable<List<ApprovalBean>> observable = new ReimControl().getMyReimData(type);
        CommonDialogObserver<List<ApprovalBean>> observer = new CommonDialogObserver<List<ApprovalBean>>(this) {
            @Override
            public void onNext(List<ApprovalBean> list) {
                super.onNext(list);
                SimpleToast.toastMessage("数据获取成功", Toast.LENGTH_SHORT);
                if (list != null) {
                    mData.clear();
                    mData.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUI(observable, observer);

    }

    private void delete(int id, final int pos) {


        Observable<Boolean> observable = new ReimControl().deleteMyReim(id);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean isSuccess) {
                super.onNext(isSuccess);
                SimpleToast.toastMessage("删除成功", Toast.LENGTH_SHORT);
                mData.remove(pos);
                mAdapter.notifyItemRemoved(pos);//推荐用这个
                getData(type);

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                if (t.getMessage().equals("401")) {
                    SimpleToast.toastMessage("登录超时，请重新登录", Toast.LENGTH_SHORT);

                }


            }


        };
        RxHelper.bindOnUI(observable, observer);

    }


    private void initDatas() {
        for (int i = 0; i < 5; i++) {
            ApprovalBean bean = new ApprovalBean();

            mData.add(i, bean);
        }
    }


    @Override
    public void addDisposable(Disposable disposable) {

    }

    @Override
    public void addSubscription(Subscription subscription) {

    }

    @Override
    public void removeDisposable(Disposable disposable) {

    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }
}

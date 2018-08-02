package com.jci.vsd.activity.Reim;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jci.vsd.R;
import com.jci.vsd.SlideRecycleview.CommonAdapter;
import com.jci.vsd.SlideRecycleview.SwipeMenuLayout;
import com.jci.vsd.SlideRecycleview.ViewHolder;
import com.jci.vsd.activity.BaseActivity;
import com.jci.vsd.bean.SwipeBean;
import com.jci.vsd.bean.reim.Bean;
import com.jci.vsd.bean.reim.IdsBean;
import com.jci.vsd.bean.reim.ReimAddItemBean;
import com.jci.vsd.bean.reim.ReimAddResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.data.ReimAddData;
import com.jci.vsd.network.control.ReimControl;
import com.jci.vsd.observer.CommonDialogObserver;
import com.jci.vsd.observer.RxHelper;
import com.jci.vsd.utils.Loger;
import com.jci.vsd.view.widget.SimpleToast;
//import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 18/6/27.
 */

public class ReimRecycActivity extends BaseActivity {
    @BindView(R.id.recycle_reim)
    RecyclerView recyclerView;
    private List<Bean> dataList;
    @BindView(R.id.rightFucTxt)
    TextView rightFucTxt;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.btn_reim_generate)
    Button btnReimGenetate;

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    private CommonAdapter<ReimAddItemBean> mAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<ReimAddItemBean> mDatas = new ArrayList<>();
    private int mIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reim_recycle);
        rightFucTxt.setVisibility(View.VISIBLE);

        List<ReimAddItemBean> list = ReimAddData.getReimItemList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                Loger.e("--getReimFromReservior-" + list.get(i).getRemark());
            }
            mDatas.addAll(list);
        }


        //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String intentType = getIntent().getStringExtra(AppConstant.KEY_TYPE);
        if (intentType.equals(AppConstant.KEY_REIM_ITEM)) {
            Bundle bundle = getIntent().getExtras();
            ReimAddItemBean bean = (ReimAddItemBean) bundle.getSerializable(AppConstant.SERIAL_KEY);
            if (bean != null) {
                mDatas.add(bean);
                Loger.e("getBUndleSeri--" + bean.getAmount() + "--remark" + bean.getRemark());
                ReimAddData.saveReimItemList(mDatas);
            }
        }

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

    }

    @Override
    protected void initViewEvent() {
        rightFucTxt.setOnClickListener(this);
        btnReimGenetate.setOnClickListener(this);

        backBtn.setOnClickListener(this);
        titleTxt.setText(getResources().getString(R.string.add_reim_form));
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.rightFucTxt:
                toActivity(ReimAddActivity.class);

                break;
            case R.id.btn_reim_generate:
                //生成单据
                IdsBean idsBean = new IdsBean();
                List<Integer> integers = new ArrayList<>();
                for (int i = 0; i < mDatas.size(); i++) {
                    integers.add(mDatas.get(i).getId());
                }
                idsBean.setIds(integers);
                submitReim(idsBean);

        }
        if (view.getId() == R.id.rightFucTxt) {
            toActivity(ReimAddActivity.class);
            finish();
        }
    }

    private void submitReim(IdsBean idsBean) {
        Observable<ReimAddResponseBean> observable = new ReimControl().submitReim(idsBean);
        CommonDialogObserver<ReimAddResponseBean> observer = new CommonDialogObserver<ReimAddResponseBean>(this) {
            @Override
            public void onNext(ReimAddResponseBean reimAddResponseBean) {
                super.onNext(reimAddResponseBean);
                SimpleToast.toastMessage("提交成功，正在为您生成报销单。。。", Toast.LENGTH_SHORT);
                if (reimAddResponseBean != null) {
                    String id = reimAddResponseBean.getId() + "";

                    Thread closeActivity = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(3000);
                                Loger.e("---sleep 3000--");
                                // Do some stuff
                            } catch (Exception e) {
                                e.getLocalizedMessage();
                            }
                        }
                    });
                    closeActivity.start();
                    Loger.e("---reimRecycleToActivity--");
                    toAtivityWithParams(ReimDocSubmitActivtiy.class, reimAddResponseBean);
                    finish();
                    Loger.e("---finish --");
//                    toAtivityWithParams(ReimDocSubmitActivtiy.class, reimAddResponseBean);
//
//                    if(!TextUtils.isEmpty(id)){
//                        toActivityWithType(ReimDocSubmitActivtiy.class,id);
//                    }

                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, ReimRecycActivity.this);

    }

    private void initRecycleView() {

//        ReimAddItemBean bean = null;
//
//        for (int i = 0; i < 5; i++) {
//            bean = new ReimAddItemBean();
//            bean.setAmount(i * 20 + i + "");
//            bean.setRemark("说明" + i);
//            mDatas.add(bean);
//        }
//        ReimAddData.saveReimItemList(mDatas);
//        ReimAddData.getReimItemList();

//        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mAdapter = new CommonAdapter<ReimAddItemBean>(ReimRecycActivity.this, mDatas, R.layout.item_reim_item_swipe) {
            @Override
            public void convert(final ViewHolder holder, ReimAddItemBean swipeBean) {
                //设置是否开启左滑出菜单，设置false 为右滑出菜单
                // ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(mIndex == 0 ? false : true);// 并依次打开左滑右滑
                ((SwipeMenuLayout) holder.itemView).setIos(true).setLeftSwipe(true);
                TextView tvMoney = (TextView) holder.getView(R.id.tv_money);
                tvMoney.setText(swipeBean.getAmount());
                TextView tvCategoryName = (TextView) holder.getView(R.id.tv_expense_category);
                tvCategoryName.setText(swipeBean.getCategoryName());

                TextView tvExplain = holder.getView(R.id.tv_explain);
                tvExplain.setText(swipeBean.getRemark());


                //    holder.setText(R.id.content, swipeBean.name + (mIndex == 0 ? "我左青龙" : "我右白虎"));

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
                            Toast.makeText(ReimRecycActivity.this, "删除:" + pos, Toast.LENGTH_SHORT).show();
                            mDatas.remove(pos);
                            ReimAddData.removeReimList();
                            ReimAddData.saveReimItemList(mDatas);
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
                        Toast.makeText(mContext, "onClick:" + mDatas.get(holder.getAdapterPosition()).getAmount(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onClick() called with: v = [" + v + "]");
                    }
                });
                //置顶：
                holder.setOnClickListener(R.id.btnTop, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();

                        if (pos > 0 && pos < mDatas.size()) {
                            ReimAddItemBean swipeBean = mDatas.get(pos);
                            mDatas.remove(swipeBean);
                            mAdapter.notifyItemInserted(0);
                            mDatas.add(0, swipeBean);
                            mAdapter.notifyItemRemoved(pos + 1);
                            if (mLayoutManager.findFirstVisibleItemPosition() == 0) {
                                recyclerView.scrollToPosition(0);
                            }
                            //notifyItemRangeChanged(0,holder.getAdapterPosition()+1);
                        }

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
}

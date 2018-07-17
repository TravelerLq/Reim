package com.jci.vsd.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.adapter.BaseAdapter;
import com.jci.vsd.adapter.RecAdapter;
import com.jci.vsd.adapter.UserAdapter;
import com.jci.vsd.bean.UserBean;
import com.jci.vsd.utils.Loger;
import com.paginate.Paginate;
import com.paginate.recycler.LoadingListItemCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by liqing on 18/6/15.
 */

public class UsersActivity extends BaseActivity {


    @BindView(R.id.recycle_uers)
    RecyclerView recyclerView;

    @BindView(R.id.users_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private List<UserBean> mData = new ArrayList<>();
    private Context context;
    private UserAdapter adapter;
    private RecAdapter recAdapter;
    private Paginate mPaginate;

    private boolean isLoadingMore;
    private boolean isLoading;
    private int page;
    private int totalPages = 3;
    private int itemsPerPage = 8;
    private boolean loading;
    private Handler handler;
    private long networkDelay = 1000;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initTestData();
        page = 0;
        handler = new Handler();
        context = UsersActivity.this;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        adapter = new UserAdapter(mData, context);
//        recyclerView.setAdapter(adapter);


//
//        adapter.setOnItemClickListener(new BaseAdapter.OnRecycleviewItemClickListener() {
//            @Override
//            public void onItemClick(View view, int viewType, Object data, int pos) {
//
//
//                switch (view.getId()) {
//                    case  R.id.tv_name:
//                        Log.e("---activity--", "item click tvName--");
//                        break;
//                    default:
//                        break;
//                }
//
//            }
//        });
        recAdapter = new RecAdapter(mData, context);
        recyclerView.setAdapter(recAdapter);
        recAdapter.setOnItemClickListener(new RecAdapter.ItemClickListener() {
            @Override
            public void onItemclick(View view, int pos) {
                Loger.e("tvlivk---");
            }

            @Override
            public void onImageClick(View view, int pos) {
                Loger.e("imageClick---");
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("---refresh", "--");

                        mData.add(0, new UserBean(0, "refreshNew---", "hh"));
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        //onloadMore

        initPaginate();


    }


    private Runnable fakeCallback = new Runnable() {
        @Override
        public void run() {
            page++;
           // recAdapter.add(DataProvider.getRandomData(itemsPerPage));
            loading = false;
        }
    };


    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {

                @Override
                public void onLoadMore() {
                    loading = true;
                    Loger.e("---initPaginate.onLoadMore");
                    handler.postDelayed(fakeCallback, networkDelay);
                }

                @Override
                public boolean isLoading() {
                    return loading;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return page == totalPages; // If all pages are loaded return true;
                }
            };
            mPaginate = Paginate.with(recyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }


    public void initTestData() {
        UserBean userBean;
        for (int i = 0; i < 5; i++) {
            userBean = new UserBean(i, "name" + i, "urlIMag" + i);
            mData.add(i, userBean);
        }
    }


    @Override
    protected void initViewEvent() {

    }


    private class CustomLoadingListItemCreator implements LoadingListItemCreator {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.custom_loading_list_item, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            VH vh = (VH) holder;
            vh.tvLoading.setText(String.format("Total items loaded: %d.\nLoading more...", adapter.getItemCount()));

            // This is how you can make full span if you are using StaggeredGridLayoutManager
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) vh.itemView.getLayoutParams();
                params.setFullSpan(true);
            }
        }
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvLoading;

        public VH(View itemView) {
            super(itemView);
            tvLoading = (TextView) itemView.findViewById(R.id.tv_loading_text);
        }
    }


}

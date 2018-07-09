package com.jci.vsd.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.jci.vsd.R;
import com.jci.vsd.bean.dialog.BaseBean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yso on 2017/11/9.
 */

public abstract class BaseViewPageAdapter<T extends BaseBean> extends PagerAdapter {
    protected Context context;
    protected List<View> mListViewPager = new ArrayList<View>(); // ViewPager对象的内容
    protected List<T> mSourceList = new ArrayList<T>();
    protected List<List<T>> lcontant = null;
    protected int pageNum = 1;
    //每页显示商品数量
    protected int everyPageNo = 1;
    public BaseViewPageAdapter(final Context context, List<T> list,int everyPageNo) {
        int count = 0;  //循环次数
        int pos = 0;	//当前位置
        this.context = context;
        this.mSourceList = list;
        this.everyPageNo = everyPageNo;
        if(list == null){
            return;
        }
        //计算页数
        pageNum = (list.size() + everyPageNo -1)/everyPageNo;
        lcontant = new ArrayList<List<T>>();
        for (int i = 0; i < pageNum; i++) {
            List<T> item = new ArrayList<T>();
            for(int k = pos;k<list.size();k++){
                count++;
                pos = k;
                item.add(list.get(k));
                //每个List EVERYPAGENUM条记录，存满EVERYPAGENUM个跳出
                if(count == everyPageNo){
                    count = 0;
                    pos = pos+1;
                    break;
                }
            }
            lcontant.add(item);
        }

        for (int j = 0; j < pageNum; j++) {
            View viewPager = LayoutInflater.from(context).inflate(
                    R.layout.list, null);
            final ListView mList = (ListView) viewPager.findViewById(R.id.wifi_list);
            initAdapter(context,mList,j);
            mListViewPager.add(viewPager);
        }

    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        return mListViewPager.size();
    }

    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(mListViewPager.get(position));
        return mListViewPager.get(position);

    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void destroyItem(View container, int position, Object arg2) {
        ViewPager pViewPager = ((ViewPager) container);
        pViewPager.removeView(mListViewPager.get(position));
    }

    @Override
    public void finishUpdate(View arg0) {
        // TODO Auto-generated method stub

    }


    public abstract void initAdapter(Context context, ListView mList, int pageNum);

    public void refreshList( List<T> list){
        this.mSourceList = list;
        notifyDataSetChanged();
    }

}

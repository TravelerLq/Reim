package com.jci.vsd.adapter;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jci.vsd.bean.ArriveNoticeBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.utils.Loger;

import java.util.List;

/**
 * Created by liqing on 17/11/15.
 * 到料提醒ViewPagerAdapter
 */

public class ArriveNoticeViewPageAdapter extends BaseViewPageAdapter<ArriveNoticeBean> {
    public static final int HZ_VEWPAGE_EVERY_PAGE_NO = 9;
    public ArriveNoticeViewPageAdapter(Context context, List<ArriveNoticeBean> list) {
        super(context, list, HZ_VEWPAGE_EVERY_PAGE_NO);
        if(context==null){
            Loger.e("---PageAdapte1-context null");
        }
    }

    @Override
    public void initAdapter(Context context,ListView listView, int pageNum) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Loger.i("-----setOnItemClickListener-------");
                long positon = adapterView.getItemIdAtPosition(i);
//                Intent intent = new Intent(context, ReceiptNotDoneActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(AppConstant.SERIAL_KEY, (Serializable) mSourceList.get((int)positon));
//                intent.putExtras(bundle);
//                context.startActivity(intent);
            }
        });
        if(context==null){
            Loger.e("---PageAdapte2-context null");
        }
        ArriveNoticeAdapter myAdapter = new ArriveNoticeAdapter(context, (List<ArriveNoticeBean>) lcontant.get(pageNum));
        myAdapter.setPageNo(pageNum);
        listView.setAdapter(myAdapter);

    }
}

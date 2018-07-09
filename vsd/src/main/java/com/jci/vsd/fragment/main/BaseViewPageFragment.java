package com.jci.vsd.fragment.main;

import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.adapter.BaseViewPageAdapter;
import com.jci.vsd.utils.Loger;

/**ViewPage 的积累
 * Created by Administrator on 2017/11/30 0030.
 */

public class BaseViewPageFragment extends BaseFragment implements View.OnClickListener{
    protected int nowPageNo = 0;
    protected BaseViewPageAdapter pagerAdapter;
    protected ImageButton leftArraowBtn;
    protected ImageButton rightArraowBtn;
    protected ViewPager viewPager;
    protected TextView showIndexTxt;

    protected  void initViewPage(){

        viewPager.setAdapter(pagerAdapter);
        initPageNo(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                initPageBtn(position);
                intText(position);
                nowPageNo = position;
                Loger.i("onPageSelected = "+nowPageNo);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    protected void initPageNo(int pageNo){
        initPageBtn(pageNo);
        intText(pageNo);
        viewPager.setCurrentItem(pageNo);
    }

    protected void initPageBtn(int pageNo){
        if(pageNo == 0) {
            leftArraowBtn.setEnabled(false);
        }else{
            leftArraowBtn.setEnabled(true);
        }
        if(pageNo == (pagerAdapter.getCount() -1)){
            rightArraowBtn.setEnabled(false);
        }else{
            rightArraowBtn.setEnabled(true);
        }
    }

    protected void intText(int pageNo){
        String result = ( pagerAdapter.getCount() >0 ? (pageNo +1): 0)+" / "+ pagerAdapter.getCount();
        SpannableString spannedString = new SpannableString(result);
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.blue_1399CE));
        spannedString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        showIndexTxt.setText(spannedString);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.leftArraowBtn:{
                Loger.i("onPageSelected 1= "+nowPageNo);
                if(nowPageNo <= 0){
                    return;
                }else{
                    nowPageNo--;
                    initPageNo(nowPageNo);
                }
            }
            break;
            case R.id.rightArraowBtn:{
                Loger.i("onPageSelected 2= "+nowPageNo);
                if(pagerAdapter == null || pagerAdapter.getCount() - 1 <0){
                    return ;
                }
                if(nowPageNo >= (pagerAdapter.getCount() - 1)){
                    return;
                }else{
                    nowPageNo++;
                    initPageNo(nowPageNo);
                }
            }
            break;
        }
    }

    public void dealScanTxt(String result){}
}

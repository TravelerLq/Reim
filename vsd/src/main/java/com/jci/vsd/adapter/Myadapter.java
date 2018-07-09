package com.jci.vsd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.bean.viewpage.WaitStoreMaterialBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.utils.Utils;

import java.util.List;

public class Myadapter extends BaseAdapter {
	private Context con;
	private List<WaitStoreMaterialBean> materialBeanList = null;
	private int pageNo;//在ViewPage中所处的page的index
	public Myadapter(Context con, List<WaitStoreMaterialBean> mType) {
		this.con = con;
		this.materialBeanList = mType;
	
	}

	@Override
	public int getCount() {
		if(materialBeanList == null)
			return 0;
		return materialBeanList.size();
	}

	@Override
	public Object getItem(int pos) {
		return pos;
	}

	@Override
	public long getItemId(int pos) {
		return pos+ AppConstant.HZ_VEWPAGE_EVERY_PAGE_NO*(pageNo);
	}

	@Override
	public View getView(final int pos, View convertView, ViewGroup parent) {
		final HolderView holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(con).inflate(R.layout.list_item,null);
			holder = new HolderView();
			holder.materialSerailTxt = (TextView) convertView.findViewById(R.id.materialSerailTxt);
			holder.materialSapTxt = (TextView) convertView.findViewById(R.id.materialSapTxt);
			holder.materialDescpTxt = (TextView) convertView.findViewById(R.id.materialDescpTxt);
	
			holder.materialNumberTxt = (TextView) convertView.findViewById(R.id.materialNumberTxt);
	 		holder.materialUnitTxt = (TextView) convertView.findViewById(R.id.materialUnitTxt);
			holder.materialAreaSeatTxt = (TextView) convertView.findViewById(R.id.materialAreaSeatTxt);
			convertView.setTag(holder);

		} else {
			holder = (HolderView) convertView.getTag();
		}

		if(pos % 2 == 0){
			convertView.setBackgroundResource(R.drawable.select_btn_item_click_1_xml);
		}else{
			convertView.setBackgroundResource(R.drawable.select_btn_item_click_2_xml);
		}
		WaitStoreMaterialBean waitStoreMaterialBean = materialBeanList.get(pos);
		holder.materialSerailTxt.setText((pos+ AppConstant.HZ_VEWPAGE_EVERY_PAGE_NO*(pageNo)+1)+"");
		holder.materialSapTxt.setText(waitStoreMaterialBean.getSapNo());
		holder.materialDescpTxt.setText(waitStoreMaterialBean.getDescription());
		holder.materialNumberTxt.setText(waitStoreMaterialBean.getNumber());
		holder.materialUnitTxt.setText(Utils.getMaterialUnit(waitStoreMaterialBean.getUnit()));
		holder.materialAreaSeatTxt.setText(waitStoreMaterialBean.getAreaSeat());
		return convertView;
	}

	static class HolderView {
		TextView materialSerailTxt;
		TextView materialSapTxt;
		TextView materialDescpTxt;
		TextView materialNumberTxt;
		TextView materialUnitTxt;
		TextView materialAreaSeatTxt;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}

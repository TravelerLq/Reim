package com.jci.vsd.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jci.vsd.R;
import com.jci.vsd.utils.DisplayUtils;

import java.util.List;

/**
 * Created by liqing on 17/11/16.
 * GridView 的adapter
 */

public class GridViewAddImageAdapter extends BaseAdapter {

    //默认上传最大数值
    private String TAG = "GridViewAddImageAdapter";
    private int maxCount = 9;
    private List<String> datas;
    private Context context;
    private LayoutInflater inflater;
    AddAndDeleteListener listener;

    public GridViewAddImageAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setMaxImages(int maxImages) {
        this.maxCount = maxImages;
    }

    public interface AddAndDeleteListener {
        void addImag();

        void viewImag(int pos);

        void deletImg(int pos);

    }

    public void setAddAndDeleteListener(AddAndDeleteListener listener) {
        this.listener = listener;
    }


    @Override
    public int getCount() {
        int count = datas == null ? 1 : datas.size() + 1;
        if (count >= maxCount) {
            return maxCount;
        }
        Log.e(TAG, "getCount: －－－＝" + count);
        return count;
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview_image, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ViewGroup.LayoutParams params = convertView.getLayoutParams();
//        params.height = (int) (DisplayUtils.nomalWidth / 5);
//        params.width = (int) (DisplayUtils.nomalWidth / 5);
        convertView.setLayoutParams(params);
//        Log.e(TAG, "getView: ----height="+ params.height+ " width=" +params.width );
//        Log.e(TAG, "getView: pic datas.size()= " + datas.size() + "pos=" + position);
        if ((datas != null && position < datas.size()) || datas.size() == maxCount) {

            // Glide.with(this).load(new File(images.get(i).getCompressPath())).into(imageView1);
            Glide.with(context).load(datas.get(position))
                    .into(viewHolder.imageView);
            viewHolder.delete.setVisibility(View.VISIBLE);
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.deletImg(position);
                }
            });
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.viewImag(position);
                }
            });
        } else {
            viewHolder.delete.setVisibility(View.GONE);
            Glide.with(context)
                    .load(R.drawable.add_feed_back)
                    .into(viewHolder.imageView);
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.addImag();
                }
            });
        }
        return convertView;
    }

    class ViewHolder {

        ImageView imageView;
        TextView delete;

        public ViewHolder(View root) {
            imageView = (ImageView) root.findViewById(R.id.gridview_item_img);
            delete = (TextView) root.findViewById(R.id.tv_delete);
        }
    }


}
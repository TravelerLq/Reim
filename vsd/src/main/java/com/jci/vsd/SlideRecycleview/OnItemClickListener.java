package com.jci.vsd.SlideRecycleview;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liqing on 18/6/27.
 */

public interface OnItemClickListener<T> {
    void onItemClick(ViewGroup var1, View var2, T var3, int var4);

    boolean onItemLongClick(ViewGroup var1, View var2, T var3, int var4);
}

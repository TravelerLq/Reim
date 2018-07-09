package com.jci.vsd.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liqing on 18/7/2.
 */

public class ItemDecor extends RecyclerView.ItemDecoration {

    private int itemDecoBottom;
    private int itemDecoLeft;
    private int itemDecoRight;

    public ItemDecor(int itemDecoBottom) {
        this.itemDecoBottom = itemDecoBottom;
    }

    public ItemDecor(int itemDecoLeft, int itemDecoRight,int itemDecoBottom) {
        this.itemDecoBottom = itemDecoBottom;
        this.itemDecoLeft = itemDecoLeft;
        this.itemDecoRight = itemDecoRight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, itemDecoBottom);
    }

}

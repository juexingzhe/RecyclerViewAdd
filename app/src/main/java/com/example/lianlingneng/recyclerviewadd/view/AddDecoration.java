package com.example.lianlingneng.recyclerviewadd.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class AddDecoration extends RecyclerView.ItemDecoration {

    private int width;

    public AddDecoration(int width) {
        this.width = width;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if (position % 3 == 1) {
            outRect.right = width;
            outRect.left = width;
        }
        outRect.bottom = width;

    }
}

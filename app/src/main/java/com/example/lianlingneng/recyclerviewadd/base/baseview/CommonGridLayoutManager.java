package com.example.lianlingneng.recyclerviewadd.base.baseview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonAdapter;

public class CommonGridLayoutManager extends GridLayoutManager {
    private static final float SCALE = 1.33f;

    private int numColumns;
    private CommonAdapter commonAdapter;

    private int itemViewWidth;
    private int itemViewHeight;

    public CommonGridLayoutManager(Context context, int spanCount, CommonAdapter commonAdapter) {
        super(context, spanCount);
        this.numColumns = spanCount;
        this.commonAdapter = commonAdapter;
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0 || state.isPreLayout()) return;
        itemViewWidth = (int) (getHorizontalSpace() / numColumns + 0.5f);
        itemViewHeight = (int) (itemViewWidth * SCALE + 0.5f);
        commonAdapter.setItemWidthHeight(itemViewWidth, itemViewHeight);

        super.onLayoutChildren(recycler, state);
    }

    @Override
    public void onAttachedToWindow(final RecyclerView view) {
        super.onAttachedToWindow(view);
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            SpanSizeLookup spanSizeLookup = new SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == view.getAdapter().getItemCount() - 1) {
                        return gridLayoutManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            };
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        }
    }

    /**
     * 测量itemview的确切大小
     */
    private void measureChildWithExactlySize(View child) {
        final int widthSpec = View.MeasureSpec.makeMeasureSpec(itemViewWidth, View.MeasureSpec.EXACTLY);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(itemViewHeight, View.MeasureSpec.EXACTLY);
        child.measure(widthSpec, heightSpec);
    }

    /**
     * /**
     * 获取RecyclerView的显示高度
     */
    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * 获取RecyclerView的显示宽度
     */
    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }
}

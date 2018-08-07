package com.example.lianlingneng.recyclerviewadd.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lianlingneng.recyclerviewadd.base.basepresenter.BaseLoadAdapter;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonBuilder;


public class LoadMoreScrollListener extends RecyclerView.OnScrollListener {


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        BaseLoadAdapter adapter = (BaseLoadAdapter) recyclerView.getAdapter();

        if (null == layoutManager) return;

        if (layoutManager instanceof GridLayoutManager) {
            int lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();

            //最后一项说明已经可以看见最后一项了，需要加载loading框
            if (adapter.isHasMore() && adapter.getItemCount() > adapter.getPageCount() && (adapter.getItemCount() - CommonBuilder.SPANS) <= lastVisibleItemPosition ) {
                if (!adapter.isLoading()) {
                    adapter.loadMore();
                }
            }

        }

    }
}

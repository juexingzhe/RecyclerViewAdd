package com.example.lianlingneng.recyclerviewadd.base.basepresenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.example.lianlingneng.recyclerviewadd.base.LoadMoreScrollListener;
import com.example.lianlingneng.recyclerviewadd.base.basemodel.CommonModel;
import com.example.lianlingneng.recyclerviewadd.base.basemodel.BaseModel;
import com.example.lianlingneng.recyclerviewadd.base.baseview.CommonGridLayoutManager;
import com.example.lianlingneng.recyclerviewadd.presenter.AddAdapter;
import com.example.lianlingneng.recyclerviewadd.presenter.CollectAdapter;

/**
 * RecyclerView的构造过程封装
 */
public class CommonBuilder {
    public static final int SPANS = 3;

    private RecyclerView recyclerView;
    private Context context;
    private CommonAdapter adapter;
    private BaseModel baseModel;
    private CommonGridLayoutManager layoutManager;
    private CommonAdapter.OnItemClickListener clickLisenter;
    private RecyclerView.OnScrollListener onScrollListener;


    public CommonBuilder(Context context) {
        this.context = context;
    }

    public CommonBuilder recyclerView(RecyclerView view) {
        this.recyclerView = view;
        return this;
    }

    public CommonBuilder baseModel(BaseModel model) {
        baseModel = model;
        return this;
    }

    public CommonBuilder adapter(CommonAdapter adapter) {
        this.adapter = adapter;
        return this;
    }

    public CommonBuilder gridLayoutManager(CommonGridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
        return this;
    }

    public CommonBuilder setClickLisenter(CommonAdapter.OnItemClickListener lisenter) {
        this.clickLisenter = lisenter;
        return this;
    }

    public CommonBuilder setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
        return this;
    }

    public CommonBuilder build() {
        if (null == baseModel) {
            baseModel = new CommonModel(context);
        }

        if (null == adapter) {
            adapter = new CollectAdapter(context, baseModel.fetchData());
        }

        if (null == clickLisenter) {
            clickLisenter = new AddAdapter.OnItemClickListener() {
                @Override
                public void onNormalClick(View view, int position) {
                    Toast.makeText(context, "NormalClick position = " + position, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onAddClick(View view, int position) {
                    Toast.makeText(context, "AddClick position = " + position, Toast.LENGTH_LONG).show();
                }
            };
        }

        if (null == layoutManager) {
            layoutManager = new CommonGridLayoutManager(context, SPANS, adapter);
        }

        if (null == onScrollListener) {
            onScrollListener = new LoadMoreScrollListener();
        }

        adapter.setOnItemClickListener(clickLisenter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(onScrollListener);
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        adapter.notifyDataSetChanged();
                        recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                });

        return this;
    }
}

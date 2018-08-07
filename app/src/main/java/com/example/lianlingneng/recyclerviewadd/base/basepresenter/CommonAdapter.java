package com.example.lianlingneng.recyclerviewadd.base.basepresenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.lianlingneng.recyclerviewadd.presenter.AddAdapter;

import java.util.List;

/**
 * 所有<>RecyclerView</>的父Adapter
 * 主要提供设置Item宽高和Item点击事件
 *
 * @param <N>
 */
public abstract class CommonAdapter<N extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context context;
    protected int itemWidth;
    protected int itemHeight;
    protected OnItemClickListener onItemClickListener;

    public CommonAdapter(Context context) {
        this.context = context;
    }

    public void setItemWidthHeight(int width, int height) {
        this.itemWidth = width;
        this.itemHeight = height;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onNormalClick(View view, int position);

        void onAddClick(View view, int position);
    }

    public abstract void clearData();

    public abstract void addData(List list);
}

package com.example.lianlingneng.recyclerviewadd.base.basepresenter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lianlingneng.recyclerviewadd.R;

import java.util.List;

/**
 * 实现加载更多功能的<>RecyclerView</>需要继承该Adapter
 * <p>
 * 需要实现setViewHolder--->在onCreateViewHolder中调用
 * 需要实现bindItemViewHolder--->在onBindViewHolder中调用
 * 需要实现getItemViewType--->在getItemViewType中调用
 *
 * @param <T>
 */
public abstract class BaseLoadAdapter<T> extends CommonAdapter {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_BOTTOM = 2;

    public static final int STATE_LOADING = 0x012;
    public static final int STATE_NOMORE = 0x013;
    public static final int STATE_ERR = 0x014;

    private int pageCount = 5;//每一页和后台说定的条数

    private boolean hasMore = true;

    private boolean isLoading = false;

    protected List<T> list;

    private int state = STATE_LOADING;

    public BaseLoadAdapter(Context context, List<T> data) {
        super(context);
        this.list = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BOTTOM) {
            return new BottomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_layout, parent, false));
        } else {
            return setViewHolder(parent, viewType);
        }
    }

    public abstract RecyclerView.ViewHolder setViewHolder(ViewGroup parent, int viewTyp);

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_BOTTOM) {
            final ProgressBar progressBar = ((BottomViewHolder) holder).progressBar;
            final TextView bottomTextView = ((BottomViewHolder) holder).bottomTextView;
            final ImageView bottomIcon = ((BottomViewHolder) holder).bottomIcon;

            if (holder.itemView == null) return;

            if (holder.itemView.getVisibility() == View.GONE) {
                holder.itemView.setVisibility(View.VISIBLE);
            }

            switch (state) {
                case STATE_LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    bottomTextView.setText("加载中...");
                    bottomIcon.setVisibility(View.GONE);
                    holder.itemView.setOnClickListener(null);
                    hasMore = true;
                    hideBottomView(holder);
                    break;
                case STATE_NOMORE:
                    progressBar.setVisibility(View.GONE);
                    bottomTextView.setText("没有更多数据了");
                    bottomIcon.setImageResource(R.drawable.info_icon);
                    holder.itemView.setOnClickListener(null);
                    hasMore = false;
                    hideBottomView(holder);
                    break;
                case STATE_ERR:
                    progressBar.setVisibility(View.VISIBLE);
                    bottomTextView.setText("加载失败请点击重试");
                    bottomIcon.setImageResource(R.drawable.error_icon);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            progressBar.setVisibility(View.VISIBLE);
                            bottomTextView.setText("加载中...");
                            bottomIcon.setVisibility(View.GONE);
                            loadMore();
                        }
                    });
                    hasMore = true;
                    break;
                default:
                    break;
            }
        } else {
            bindItemViewHolder(holder, position);
        }
    }

    private void hideBottomView(final RecyclerView.ViewHolder holder) {
        if (holder.itemView == null || holder.itemView.getVisibility() == View.GONE) return;
        holder.itemView.postDelayed(new Runnable() {
            @Override
            public void run() {
                holder.itemView.setVisibility(View.GONE);
            }
        }, 2000);
    }

    public abstract void bindItemViewHolder(RecyclerView.ViewHolder holder, int position);

    public abstract int getNormalItemViewType(int position);


    @Override
    public int getItemViewType(int position) {
        if (list.size() < pageCount) {
            return getNormalItemViewType(position);
        } else {
            if (position == list.size()) {//最后一个数据
                return TYPE_BOTTOM;
            } else {
                return getNormalItemViewType(position);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (null == list) return 0;

        if (list.size() < pageCount) {//后台返回数据小于pageCount，没有更多数据
            hasMore = false;
            return list.size();
        } else {
            return list.size() + 1;
        }
    }

    /**
     * 子类实现需要调用super.loadMore()
     */
    public void loadMore() {
        if (isLoading()) return;
        setLoading(true);
        setHasMore(true);
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    class BottomViewHolder extends RecyclerView.ViewHolder {

        LinearLayout container;
        TextView bottomTextView;
        ImageView bottomIcon;
        ProgressBar progressBar;

        public BottomViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.bottom_container);
            bottomTextView = itemView.findViewById(R.id.bottom_title);
            bottomIcon = itemView.findViewById(R.id.bottom_icon);
            progressBar = itemView.findViewById(R.id.progress);
        }
    }

    public void setErrorStatus() {
        state = STATE_ERR;
        notifyItemChanged(list.size());
        setLoading(false);
    }

    public void setLastedStatus() {
        state = STATE_NOMORE;
        notifyItemChanged(list.size());
    }


    public void addList(List addList) {
        int count = this.list.size();
        this.list.addAll(addList);
        notifyItemRangeChanged(count, addList.size());
        setLoading(false);
    }

    public void clearData() {
        if (this.list != null) {
            this.list.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void addData(List list) {
        if (this.list != null) {
            addList(list);
        }
    }

    /**
     * 下拉刷新的时候重置状态
     */
    public void resetLoadStatus() {
        this.state = STATE_LOADING;
        this.isLoading = false;
        this.hasMore = true;
    }
}

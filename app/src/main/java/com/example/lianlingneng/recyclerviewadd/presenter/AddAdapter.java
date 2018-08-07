package com.example.lianlingneng.recyclerviewadd.presenter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lianlingneng.recyclerviewadd.R;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonAdapter;

import java.util.List;

public class AddAdapter extends CommonAdapter<RecyclerView.ViewHolder> {
    private static final int NORMAL = 0x0001;
    private static final int ADD = 0x0002;

    private List<Drawable> images;


    public AddAdapter(Context context, List<Drawable> images) {
        super(context);
        this.images = images;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if (viewType == NORMAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            holder = new NormalViewHolder(view);
            ImageView imgView = ((NormalViewHolder) holder).imageView;
            imgView.getLayoutParams().height = itemHeight;
            imgView.getLayoutParams().width = itemWidth;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add, parent, false);
            holder = new AddViewHolder(view);
            ((AddViewHolder) holder).textView.getLayoutParams().height = itemHeight;
            ((AddViewHolder) holder).textView.getLayoutParams().width = itemWidth;
        }

        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalViewHolder) {
            final ImageView imgView = ((NormalViewHolder) holder).imageView;
            imgView.setImageDrawable(images.get(position));
            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onNormalClick(imgView, position);
                    }
                }
            });
        } else {
            final TextView txtView = ((AddViewHolder) holder).textView;
            txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onAddClick(txtView, position);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return images == null ? 0 : images.size() + 1;
    }

    @Override
    public void clearData() {
        if (this.images != null) {
            this.images.clear();
        }
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        NormalViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo);
        }
    }

    class AddViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        AddViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position == images.size() ? ADD : NORMAL;
    }


    @Override
    public void addData(List list) {

    }

}

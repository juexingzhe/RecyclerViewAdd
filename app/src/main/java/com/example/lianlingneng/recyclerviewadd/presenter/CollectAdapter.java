package com.example.lianlingneng.recyclerviewadd.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lianlingneng.recyclerviewadd.R;
import com.example.lianlingneng.recyclerviewadd.base.CommonConfig;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.BaseLoadAdapter;
import com.example.lianlingneng.recyclerviewadd.model.DataBean;
import com.example.lianlingneng.recyclerviewadd.base.VideoPlayer;

import java.util.List;
import java.util.Random;

public class CollectAdapter extends BaseLoadAdapter<DataBean> {

    private LongClickListenr listenr;
    private LoadMoreListener loadMoreListener;

    public CollectAdapter(Context context, List<DataBean> images) {
        super(context, images);
    }

    @Override
    public RecyclerView.ViewHolder setViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder;
        if (viewType == CommonConfig.PNG_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            holder = new NormalViewHolder(view);
            ImageView imgView = ((NormalViewHolder) holder).imageView;
            imgView.getLayoutParams().height = itemHeight;
            imgView.getLayoutParams().width = itemWidth;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
            holder = new VideoViewHolder(view);
            VideoPlayer video = ((VideoViewHolder) holder).textureView;
            video.getLayoutParams().height = itemHeight;
            video.getLayoutParams().width = itemWidth;
        }


        return holder;
    }

    @Override
    public void bindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NormalViewHolder) {
            final ImageView imgView = ((NormalViewHolder) holder).imageView;
            Glide.with(context)
                    .load(list.get(position).getImage())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.i("LLN", (e == null ? "Null Error" : e.toString()) + "model = " + model);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .dontAnimate()
                    .error(R.drawable.ic_launcher_background)
                    .into(imgView);


            imgView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onNormalClick(imgView, position);
                    }
                }
            });

            imgView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, "Long Click = " + position, Toast.LENGTH_LONG).show();
                    if (null != listenr) {
                        listenr.onItemLongClick(v, position);
                    }
                    return true;
                }
            });
        } else {
            final VideoPlayer videoPlayer = ((VideoViewHolder) holder).textureView;
            videoPlayer.setUp(list.get(position), null);
            videoPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    videoPlayer.onClick(v);
                }
            });
        }
    }


    @Override
    public int getNormalItemViewType(int position) {
        switch (list.get(position).getType()) {
            case DataBean.PNG:
                return CommonConfig.PNG_TYPE;
            case DataBean.MP4:
                return CommonConfig.MP4_TYPE;
            default:
                return CommonConfig.PNG_TYPE;
        }
    }

    @Override
    public void loadMore() {
        super.loadMore();
        if (loadMoreListener == null) return;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //loadMoreListener.loadMoreData();
                int nextInt = new Random().nextInt(3);
                switch (nextInt) {
                    case 0:
                        setErrorStatus();
                        break;
                    case 1:
                        setLastedStatus();
                        break;
                    case 2:
                        loadMoreListener.loadMoreData();
                        break;
                    default:
                        break;
                }

            }
        }, 2000);
    }

    class NormalViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        NormalViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo);
        }
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        private VideoPlayer textureView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            textureView = itemView.findViewById(R.id.video);
        }
    }

    public void setListenr(LongClickListenr listenr) {
        this.listenr = listenr;
    }

    public interface LongClickListenr {
        void onItemLongClick(View view, int position);
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface LoadMoreListener {
        void loadMoreData();
    }
}

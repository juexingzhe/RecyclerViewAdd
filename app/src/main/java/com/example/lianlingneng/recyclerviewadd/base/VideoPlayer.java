package com.example.lianlingneng.recyclerviewadd.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lianlingneng.recyclerviewadd.R;
import com.example.lianlingneng.recyclerviewadd.model.DataBean;
import com.example.lianlingneng.recyclerviewadd.utils.Utils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VideoPlayer extends FrameLayout implements TextureView.SurfaceTextureListener, View.OnClickListener{

    private Context mContext;
    private FrameLayout mContainer;
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private ImageView imageView;

    private DataBean bean;
    private Map<String, String> headers;

    private boolean isComplete = false;
    private boolean loop = false;

    private ExecutorService executorService;

    public VideoPlayer(@NonNull Context context) {
        this(context, null);
    }

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public void setUp(DataBean bean, Map<String, String> headers) {
        this.bean = bean;
        this.headers = headers;
    }

    private void init(){
        mContainer = new FrameLayout(mContext);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.addView(mContainer, layoutParams);
        initWorker();
        initTextureView();
        initMediaPlayer();
        addTextureView();

    }

    private void initWorker() {
        if (null == executorService) {
            executorService = Executors.newFixedThreadPool(2);
        }
    }

    private void initImg() {
        if (null == imageView) {
            imageView = new ImageView(mContext);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mContainer.addView(imageView, layoutParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
    }

    private void initTextureView(){
        if (null == textureView) {
            textureView = new TextureView(mContext);
            textureView.setKeepScreenOn(true);
            textureView.setSurfaceTextureListener(this);
        }
    }

    private void initMediaPlayer() {
        if (null == mediaPlayer) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
    }

    private void addTextureView() {
        mContainer.removeView(textureView);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        mContainer.addView(textureView, layoutParams);
        initImg();
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.d("LLN", "onSurfaceTextureAvailable");
        if (null == imageView) {
            initImg();
        }
        Glide.with(mContext)
                .load(bean.getImage())
                .placeholder(R.drawable.ic_launcher_background)
                .crossFade()
                .into(imageView);
        executorService.execute(new OpenMediaPlayer(surface));
    }

    class OpenMediaPlayer implements Runnable {

        private SurfaceTexture surface;

        OpenMediaPlayer(SurfaceTexture surface) {
            this.surface = surface;
        }

        @Override
        public void run() {

            try {
                initMediaPlayer();
                mediaPlayer.setDataSource(bean.getVideo());
                mediaPlayer.setSurface(new Surface(surface));

                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        Log.d("LLN", "onPrepared");
                        long savedPlayPosition = Utils.getSavedPlayPosition(mContext, bean.getVideo());
                        mp.start();
                        mp.seekTo((int) savedPlayPosition);
                    }
                });

                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                    }
                });

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        isComplete = true;

                        if (mediaPlayer.isPlaying()) return;

                        if (loop) {
                            mediaPlayer.seekTo(0);

                            if (!mediaPlayer.isPlaying()) {
                                mediaPlayer.start();
                            }
                        }
                    }
                });

                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        Log.d("LLN", "onInfo");
                        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                            imageView.setVisibility(GONE);
                        }
                        return true;
                    }
                });


                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.d("LLN", "onSurfaceTextureDestroyed");
        if (mediaPlayer != null) {
            Utils.savePlayPosition(mContext, bean.getVideo(),isComplete ? 0 : mediaPlayer.getCurrentPosition());
            mediaPlayer.release();
            mediaPlayer = null;
        }

        if (imageView == null) {
            initImg();
        }
        imageView.setVisibility(VISIBLE);

        return true;

    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    @Override
    public void onClick(View v) {
        if (null == mediaPlayer) return;

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            return;
        }

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}

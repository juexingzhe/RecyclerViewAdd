package com.example.lianlingneng.recyclerviewadd.base.baseview;

import android.content.Context;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lianlingneng.recyclerviewadd.R;
import com.example.lianlingneng.recyclerviewadd.base.CommonConfig;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.BaseLoadAdapter;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonAdapter;
import com.example.lianlingneng.recyclerviewadd.base.baseview.BaseView;
import com.example.lianlingneng.recyclerviewadd.model.BeanModel;
import com.example.lianlingneng.recyclerviewadd.model.DataBean;
import com.example.lianlingneng.recyclerviewadd.view.AddDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * MVP中的View
 * 主要提供RecyclerView和它的容器，RecyclerView有下拉刷新和加载更多功能
 *
 * 默认布局是有下拉刷新布局
 */
public class CommonView extends BaseView {

    private static final int WIDTH = 14;

    private View view;
    private RecyclerView recyclerView;

    //布局文件id
    private int inflaterResource;

    private SwipeRefreshLayout refreshLayout;

    private boolean isRefreshEnable = false;

    public CommonView(Context context, ViewGroup container) {
        this(context, container, R.layout.common_recycler);
        setRefreshEnable(true);
    }

    public CommonView(Context context, ViewGroup container, int resource) {
        super(context);
        this.inflaterResource = resource;
        initView(container);
    }

    private Handler handler = new Handler();

    @Override
    public View getContainerView() {
        return view;
    }

    @Override
    public View getDelegateView() {
        return recyclerView;
    }


    public void setRefreshEnable(boolean refreshEnable) {
        isRefreshEnable = refreshEnable;
        if (isRefreshEnable) {
            initRefresh();
            refreshLayout.setEnabled(true);
        }
    }

    private void initRefresh() {
        if (refreshLayout == null) {
            refreshLayout = view.findViewById(R.id.refresh);
            if (refreshLayout == null) return;
            refreshLayout.setColorSchemeResources(R.color.colorPrimary);
            refreshLayout.setRefreshing(true);
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    ((CommonAdapter)recyclerView.getAdapter()).clearData();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((CommonAdapter) recyclerView.getAdapter()).addData(getData());
                            if (recyclerView.getAdapter() instanceof BaseLoadAdapter) {
                                ((BaseLoadAdapter)recyclerView.getAdapter()).resetLoadStatus();
                            }
                            refreshLayout.setRefreshing(false);
                        }
                    }, 2000);
                }
            });
        }

        refreshLayout.setEnabled(isRefreshEnable);
    }

    private List<DataBean> getData() {
        List<DataBean> beans = new ArrayList<>();

        beans.add(new DataBean(DataBean.PNG, "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4"));
        beans.add(new DataBean(DataBean.MP4, "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-09-58.jpg", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-10_10-20-26.mp4"));
        beans.add(new DataBean(DataBean.PNG, "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-03_12-52-08.jpg", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-03_13-02-41.mp4"));
        beans.add(new DataBean(DataBean.MP4, "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-28_18-18-22.jpg", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-28_18-20-56.mp4"));
        beans.add(new DataBean(DataBean.PNG, "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-26_10-00-28.jpg", "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/04/2017-04-26_10-06-25.mp4"));

        return beans;
    }


    private void initView(ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(inflaterResource, viewGroup, false);
        recyclerView = view.findViewById(R.id.recycler);
        if (recyclerView == null) {
            throw new RuntimeException("RecyclerView = Null");
        }
        recyclerView.addItemDecoration(new AddDecoration(WIDTH));
    }


    public void setRefreshing(boolean status) {
        if (refreshLayout == null) return;

        refreshLayout.setRefreshing(status);
    }
}

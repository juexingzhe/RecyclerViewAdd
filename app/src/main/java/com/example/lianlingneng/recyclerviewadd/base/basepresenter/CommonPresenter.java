package com.example.lianlingneng.recyclerviewadd.base.basepresenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.lianlingneng.recyclerviewadd.base.basemodel.BaseModel;
import com.example.lianlingneng.recyclerviewadd.base.baseview.CommonView;

/**
 * 所有<>RecyclerView</>的父Presenter
 * 主要封装<>RecyclerView</>的构造过程，包括设置Adapter/LayoutManager/刷新/加载更多
 *
 * 需要子类自行实现builder，但是不要调用build，否则RecyclerView是null，构造失败
 */
public abstract class CommonPresenter extends BasePresenter<CommonView> {

    protected BaseModel baseModel;
    protected Context context;

    public CommonPresenter(Context context) {
        this.context = context;
        initData();
    }

    @Override
    public void attachView(CommonView view) {
        super.attachView(view);
        //初始化View
        this.builder(context).recyclerView((RecyclerView) view.getDelegateView()).build();
    }

    public abstract CommonBuilder builder(Context context);
}

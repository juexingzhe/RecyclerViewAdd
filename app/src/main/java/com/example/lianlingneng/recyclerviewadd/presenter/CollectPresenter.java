package com.example.lianlingneng.recyclerviewadd.presenter;

import android.content.Context;

import com.example.lianlingneng.recyclerviewadd.base.basemodel.BaseModel;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonBuilder;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonPresenter;
import com.example.lianlingneng.recyclerviewadd.base.baseview.CommonView;
import com.example.lianlingneng.recyclerviewadd.model.BeanModel;

import java.util.List;

public class CollectPresenter extends CommonPresenter {

    private CollectAdapter adapter;

    public CollectPresenter(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        baseModel = new BeanModel(context);
        baseModel.setLoadDataListener(new BaseModel.LoadDataListener() {
            @Override
            public void onSuccess(List list) {
                if (isViewAttached()) {
                    view.setRefreshing(false);
                }
            }

            @Override
            public void onError() {

            }

            @Override
            public void onEnd() {

            }
        });
    }

    /**
     * Dont call build()
     *
     * @param context
     * @return
     */
    @Override
    public CommonBuilder builder(Context context) {
        adapter = new CollectAdapter(context, baseModel.fetchData());
        adapter.setLoadMoreListener(new CollectAdapter.LoadMoreListener() {
            @Override
            public void loadMoreData() {
                adapter.addList(baseModel.fetchData());
            }
        });
        return new CommonBuilder(context).
                baseModel(baseModel).
                adapter(adapter);
    }
}

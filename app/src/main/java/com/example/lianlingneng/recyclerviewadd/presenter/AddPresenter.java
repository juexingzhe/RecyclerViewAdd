package com.example.lianlingneng.recyclerviewadd.presenter;

import android.content.Context;

import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonBuilder;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonPresenter;
import com.example.lianlingneng.recyclerviewadd.base.basemodel.CommonModel;

public class AddPresenter extends CommonPresenter {

    public AddPresenter(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        baseModel = new CommonModel(context);
    }

    @Override
    public CommonBuilder builder(Context context) {
        return new CommonBuilder(context).
                baseModel(baseModel).
                adapter(new AddAdapter(context, baseModel.fetchData()));
    }
}

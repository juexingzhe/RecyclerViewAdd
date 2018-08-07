package com.example.lianlingneng.recyclerviewadd.base.basepresenter;

import com.example.lianlingneng.recyclerviewadd.contract.Contract;

/**
 * 所有MVP中Presenter的父Presenter
 * 主要提供和View的联系，包括引用，判空等
 *
 * @param <V>
 */
public abstract class BasePresenter<V extends Contract.View> implements Contract.Presenter {
    protected V view;

    protected BasePresenter() {
    }

    public void attachView(V view) {
        this.view = view;
    }

    public void destroyView() {
        this.view = null;
    }

    protected boolean isViewAttached() {
        return this.view != null;
    }
}

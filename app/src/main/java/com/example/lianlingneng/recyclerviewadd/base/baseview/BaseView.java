package com.example.lianlingneng.recyclerviewadd.base.baseview;

import android.content.Context;

import com.example.lianlingneng.recyclerviewadd.contract.Contract;

/**
 * 所有MVP中View的父View
 * 主要显示加载菊花，显示网络请求错误界面等
 */
public abstract class BaseView implements Contract.View {

    protected Context context;

    public BaseView(Context context) {
        this.context = context;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showData(String data) {

    }

    @Override
    public void showFailure(String msg) {

    }

    @Override
    public void showError(String msg) {

    }
}

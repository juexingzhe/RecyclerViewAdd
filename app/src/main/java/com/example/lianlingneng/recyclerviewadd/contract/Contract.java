package com.example.lianlingneng.recyclerviewadd.contract;

import android.view.ViewGroup;

public interface Contract {

    interface Presenter {
        void initData();
    }

    interface View {
        void showLoading();

        void hideLoading();

        void showData(String data);

        void showFailure(String msg);

        void showError(String msg);

        android.view.View getContainerView();

        android.view.View getDelegateView();
    }
}

package com.example.lianlingneng.recyclerviewadd.base.basemodel;

import java.util.List;

/**
 * 所有MVP中Model需要继承的父BaseModel
 * 需要实现fetchData逻辑，后台拉取或者本地读取
 *
 * @param <T>
 */
public abstract class BaseModel<T> {

    public abstract List<T> fetchData();

    protected LoadDataListener loadDataListener;

    public LoadDataListener getLoadDataListener() {
        return loadDataListener;
    }

    public void setLoadDataListener(LoadDataListener loadDataListener) {
        this.loadDataListener = loadDataListener;
    }

    public interface LoadDataListener{

        /**
         * 拉数据成功 先调用onEnd后onSuccess
         * @param list
         */
        void onSuccess(List list);

        /**
         * 错误
         */
        void onError();

        /**
         * 拉取过程结束
         */
        void onEnd();
    }
}

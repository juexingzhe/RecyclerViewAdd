package com.example.lianlingneng.recyclerviewadd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.lianlingneng.recyclerviewadd.presenter.CollectPresenter;
import com.example.lianlingneng.recyclerviewadd.base.basepresenter.CommonPresenter;
import com.example.lianlingneng.recyclerviewadd.base.baseview.CommonView;

public class MainActivity extends AppCompatActivity {

    private CommonView view;
    private CommonPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        FrameLayout container = findViewById(R.id.container);

        //建立View与Presenter关系
//        presenter = new AddPresenter(this);
//        view = new CommonView(this, container, R.layout.add_recycler);
//        presenter.attachView(view);

        presenter = new CollectPresenter(this);
        view = new CommonView(this, container);
        presenter.attachView(view);

        //添加View到容器
        container.addView(view.getContainerView());
    }
}

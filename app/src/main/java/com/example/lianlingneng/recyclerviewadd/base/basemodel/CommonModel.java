package com.example.lianlingneng.recyclerviewadd.base.basemodel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.example.lianlingneng.recyclerviewadd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有<>RecyclerView</>需要的CommonModel
 * 在每个Presenter中的initData中初始化
 */
public class CommonModel extends BaseModel<Drawable> {

    List<Drawable> images;
    private Context context;

    public CommonModel(Context context) {
        this.context = context;
    }

    @Override
    public List<Drawable> fetchData() {
        if (images == null) {
            images = new ArrayList<>();
        }

        Resources resources = context.getResources();

        images.add(resources.getDrawable(R.mipmap.bg_1));
        images.add(resources.getDrawable(R.mipmap.bg_2));
        images.add(resources.getDrawable(R.mipmap.bg_3));
        images.add(resources.getDrawable(R.mipmap.bg_4));
        images.add(resources.getDrawable(R.mipmap.bg_1));
        images.add(resources.getDrawable(R.mipmap.bg_2));
        images.add(resources.getDrawable(R.mipmap.bg_3));
        images.add(resources.getDrawable(R.mipmap.bg_4));
        images.add(resources.getDrawable(R.mipmap.bg_1));
        images.add(resources.getDrawable(R.mipmap.bg_2));
        images.add(resources.getDrawable(R.mipmap.bg_3));
        images.add(resources.getDrawable(R.mipmap.bg_4));
        images.add(resources.getDrawable(R.mipmap.bg_1));
        images.add(resources.getDrawable(R.mipmap.bg_2));
        images.add(resources.getDrawable(R.mipmap.bg_3));
        images.add(resources.getDrawable(R.mipmap.bg_4));

        return images;
    }

}

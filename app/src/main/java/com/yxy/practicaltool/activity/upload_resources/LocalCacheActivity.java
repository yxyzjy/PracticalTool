package com.yxy.practicaltool.activity.upload_resources;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.adapter.LocalCacheAdapter;
import com.yxy.practicaltool.bean.UseDemoBean;
import com.yxy.practicaltool.myview.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalCacheActivity extends BaseActivity {

    @Bind(R.id.rv_local_cache)
    CustomRecyclerView rvLocalCache;
    private LocalCacheAdapter cacheAdapter;
    private ArrayList<UseDemoBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_local_cache, "本地缓存");
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        rvLocalCache.setLayoutManager(new LinearLayoutManager(this));
        rvLocalCache.setItemAnimator(new DefaultItemAnimator());
        cacheAdapter = new LocalCacheAdapter(this, R.layout.item_local_cache, list);
        rvLocalCache.setAdapter(cacheAdapter);
    }

    @Override
    public void initData() {
        super.initData();

        for (int i = 0;i<10;i++){
            UseDemoBean useDemoBean = new UseDemoBean();
            useDemoBean.name = "名字"+i;
            list.add(useDemoBean);
        }
        cacheAdapter.notifyDataSetChanged();
    }
}

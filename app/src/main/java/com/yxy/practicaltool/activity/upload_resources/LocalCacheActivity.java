package com.yxy.practicaltool.activity.upload_resources;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.yxy.practicaltool.MyApplication;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.adapter.LocalCacheAdapter;
import com.yxy.practicaltool.common.L;
import com.yxy.practicaltool.dao.TestDao;
import com.yxy.practicaltool.dao.UploadResourcesDaoDao;
import com.yxy.practicaltool.gen.Test;
import com.yxy.practicaltool.gen.UploadResourcesDao;
import com.yxy.practicaltool.myview.CustomRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalCacheActivity extends BaseActivity {

    @Bind(R.id.rv_local_cache)
    CustomRecyclerView rvLocalCache;
    private LocalCacheAdapter cacheAdapter;
    private ArrayList<UploadResourcesDao> list = new ArrayList<>();
    private UploadResourcesDaoDao dao;
    private TestDao testDao;

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
        dao = MyApplication.getInstances().getDaoSession().getUploadResourcesDaoDao();

        testDao = MyApplication.getInstances().getDaoSession().getTestDao();

        List<Test> tests = testDao.loadAll();
        L.e(tests.size()+"=======");

        list= (ArrayList<UploadResourcesDao>) dao.loadAll();

        cacheAdapter.notifyDataSetChanged();
    }
}

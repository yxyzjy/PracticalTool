package com.yxy.practicaltool.activity.upload_resources;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yxy.practicaltool.MyApplication;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.adapter.LocalCacheAdapter;
import com.yxy.practicaltool.common.L;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.dao.TestDao;
import com.yxy.practicaltool.dao.UploadResourcesDaoDao;
import com.yxy.practicaltool.gen.Test;
import com.yxy.practicaltool.gen.UploadResourcesDao;
import com.yxy.practicaltool.myview.CustomRecyclerView;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

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

    }

    @Override
    public void initData() {
        super.initData();
        dao = MyApplication.getInstances().getDaoSession().getUploadResourcesDaoDao();
        list.addAll(dao.loadAll());
        L.e("=====list===" + list.size());
        cacheAdapter = new LocalCacheAdapter(this, R.layout.item_local_cache, list);
        rvLocalCache.setAdapter(cacheAdapter);
    }


    private void getData(){
        list.addAll(dao.loadAll());
        cacheAdapter.notifyDataSetChanged();
    }

    class LocalCacheAdapter extends CommonAdapter<UploadResourcesDao> {

        private Intent intent = new Intent();
        private Context context;

        public LocalCacheAdapter(Context context, int layoutId, List<UploadResourcesDao> datas) {
            super(context, layoutId, datas);
            this.context = context;
        }

        @Override
        public void convert(ViewHolder holder, final UploadResourcesDao infoBean) {
            ImageView iv_cache_pic = holder.getView(R.id.iv_cache_pic);
            TextView tv_delete = holder.getView(R.id.tv_delete);

            holder.setText(R.id.tv_cache_name, "品种：" + infoBean.getPinzhongName());
            holder.setText(R.id.tv_cache_danwei, "单位：" + infoBean.getUnitsName());
            holder.setText(R.id.tv_cache_tel, "电话：" + infoBean.getPhone());
            holder.setText(R.id.tv_cache_shuxing, "属性：" + infoBean.getAttributeName());
            holder.setText(R.id.tv_cache_time, "时间：" + infoBean.getCurrentTime());

            if (!TextUtils.isEmpty(infoBean.getPicInfos())) {
                int i = infoBean.getPicInfos().indexOf("|");
                String strPic = infoBean.getPicInfos().substring(0, i);
                Glide.with(context)
                        .load(strPic)
                        .into(iv_cache_pic);
            }

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dao.deleteByKey(infoBean.getCurrentTime());;
                    getData();
                    ToastUtils.showToast(mContext,"刪除成功！");
                }
            });
        }
    }

}

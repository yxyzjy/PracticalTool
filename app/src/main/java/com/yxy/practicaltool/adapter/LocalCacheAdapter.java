package com.yxy.practicaltool.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.bean.UseDemoBean;
import com.yxy.practicaltool.gen.UploadResourcesDao;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by yxy on 2016/7/26 0026.
 */
public class LocalCacheAdapter extends CommonAdapter<UploadResourcesDao> {

    private Intent intent = new Intent();
    private Context context;

    public LocalCacheAdapter(Context context, int layoutId, List<UploadResourcesDao> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, UploadResourcesDao infoBean) {
        ImageView iv_cache_pic = holder.getView(R.id.iv_cache_pic);
        holder.setText(R.id.tv_cache_name, "品种："+infoBean.getName2());
        holder.setText(R.id.tv_cache_danwei,"单位："+infoBean.getUnitsName());
        holder.setText(R.id.tv_cache_tel,"电话："+infoBean.getPhone());
        holder.setText(R.id.tv_cache_shuxing,"属性："+infoBean.getAttributeName());
        holder.setText(R.id.tv_cache_time,"时间："+infoBean.getPhone());



        Glide.with(context)
                .load(infoBean.getAttributeId())
//                .placeholder(R.mipmap.photo_200x200)
//                .error(R.mipmap.photo_200x200)
                .into(iv_cache_pic);
    }
}

package com.yxy.practicaltool.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        TextView tv_delete = holder.getView(R.id.tv_delete);

        holder.setText(R.id.tv_cache_name, "品种：" + infoBean.getPinzhongName());
        holder.setText(R.id.tv_cache_danwei, "单位：" + infoBean.getUnitsName());
        holder.setText(R.id.tv_cache_tel, "电话：" + infoBean.getPhone());
        holder.setText(R.id.tv_cache_shuxing, "属性：" + infoBean.getAttributeName());
        holder.setText(R.id.tv_cache_time, "时间：" + infoBean.getCurrentTime());

        if (!TextUtils.isEmpty(infoBean.getPicInfos())) {
            int i = infoBean.getPicInfos().indexOf("|");
            String strPic = infoBean.getPicInfos().substring(0,i);
            Glide.with(context)
                    .load(strPic)
                    .into(iv_cache_pic);
        }

        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
}

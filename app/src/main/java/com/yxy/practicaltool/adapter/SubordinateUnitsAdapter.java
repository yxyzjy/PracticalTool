package com.yxy.practicaltool.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.bean.UseDemoBean;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by yxy on 2016/7/26 0026.
 */
public class SubordinateUnitsAdapter extends CommonAdapter<UseDemoBean> {

    private Intent intent = new Intent();
    private Context context;

    public SubordinateUnitsAdapter(Context context, int layoutId, List<UseDemoBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder,  UseDemoBean infoBean) {
        ImageView iv_duihao = holder.getView(R.id.iv_duihao);
        holder.setText(R.id.tv_name,infoBean.name);

        /*Glide.with(context)
                .load(infoBean)
                .placeholder(R.mipmap.photo_200x200)
                .error(R.mipmap.photo_200x200)
                .bitmapTransform(new RoundedCornersTransformation(context, CommonUtil.dip2px(context, 8), 0))
                .into(iv_image);*/
    }
}

package com.yxy.practicaltool.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by yxy on 2016/7/26 0026.
 */
public class SubordinateUnitsAdapter extends CommonAdapter<CompanyListRes.DataBean> {

    private Intent intent = new Intent();
    private Context context;

    public SubordinateUnitsAdapter(Context context, int layoutId, List<CompanyListRes.DataBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, CompanyListRes.DataBean infoBean) {
        ImageView iv_duihao = holder.getView(R.id.iv_duihao);
        holder.setText(R.id.tv_name, infoBean.CName);

        /*Glide.with(context)
                .load(infoBean)
                .placeholder(R.mipmap.photo_200x200)
                .error(R.mipmap.photo_200x200)
                .bitmapTransform(new RoundedCornersTransformation(context, CommonUtil.dip2px(context, 8), 0))
                .into(iv_image);*/
    }
}

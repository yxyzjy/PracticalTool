package com.yxy.practicaltool.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by yxy on 2016/7/26 0026.
 */
public class AttributeAdapter extends CommonAdapter<AttributeListRes.DataBean> {

    private Intent intent = new Intent();
    private Context context;

    public AttributeAdapter(Context context, int layoutId, List<AttributeListRes.DataBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, AttributeListRes.DataBean infoBean) {
        ImageView iv_duihao = holder.getView(R.id.iv_duihao);
        holder.setText(R.id.tv_name, infoBean.Cname);
        if (infoBean.isSelect){
            iv_duihao.setVisibility(View.VISIBLE);
        }else {
            iv_duihao.setVisibility(View.GONE);
        }
    }
}

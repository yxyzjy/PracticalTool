package com.yxy.practicaltool.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.bean.CaseTypeSelectBean;
import com.yxy.practicaltool.bean.UseDemoBean;
import com.yxy.practicaltool.entity.resulte.CaseTypeRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.List;

/**
 * Created by yxy on 2016/7/26 0026.
 */
public class CaseTypeAdapter extends CommonAdapter<CaseTypeRes.DataBean> {

    private Intent intent = new Intent();
    private Context context;

    public CaseTypeAdapter(Context context, int layoutId, List<CaseTypeRes.DataBean> datas) {
        super(context, layoutId, datas);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, CaseTypeRes.DataBean infoBean) {
        ImageView iv_duihao = holder.getView(R.id.iv_duihao);
        if (TextUtils.isEmpty(infoBean.Title)){
            holder.setText(R.id.tv_name, "+添加分类");
        }else {
            holder.setText(R.id.tv_name, infoBean.Title);
        }
        if (infoBean.isSelect){
            iv_duihao.setVisibility(View.VISIBLE);
        }else {
            iv_duihao.setVisibility(View.INVISIBLE);
        }
    }
}

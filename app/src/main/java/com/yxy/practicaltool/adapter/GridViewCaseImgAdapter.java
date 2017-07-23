package com.yxy.practicaltool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.bean.PicInfo;

import java.util.ArrayList;


public class GridViewCaseImgAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PicInfo> list;
    private int lastPos=-1;
    View.OnClickListener clickListener;

    public GridViewCaseImgAdapter(Context context, ArrayList<PicInfo> list, View.OnClickListener clickListener) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return list.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.item_add_image_case, null);
        ImageView iv_release_pic = (ImageView) convertView.findViewById(R.id.iv_release_pic);
        TextView iv_delete_pic = (TextView) convertView.findViewById(R.id.iv_delete_pic);
        TextView tv_fengmian = (TextView) convertView.findViewById(R.id.tv_fengmian);
        TextView tv_case_des = (TextView) convertView.findViewById(R.id.tv_case_des);

        tv_fengmian.setVisibility(View.GONE);
        iv_delete_pic.setVisibility(View.GONE);
        if (position >= list.size()) {
            Glide.with(context)
                    .load(R.mipmap.icon_add)
                    .into(iv_release_pic);
            tv_case_des.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(list.get(position).pic)
                    .into(iv_release_pic);
            tv_case_des.setVisibility(View.VISIBLE);
            if (list.get(position).isFengmian){
                tv_fengmian.setVisibility(View.VISIBLE);
                iv_delete_pic.setVisibility(View.GONE);
            }else {
                tv_fengmian.setVisibility(View.GONE);
                iv_delete_pic.setVisibility(View.VISIBLE);
            }
        }

        iv_delete_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastPos != -1){
                    list.get(lastPos).isFengmian = false;
                }
                list.get(position).isFengmian =true;
                lastPos = position;
                notifyDataSetChanged();
            }
        });
        tv_case_des.setOnClickListener(clickListener);
        return convertView;
    }

}

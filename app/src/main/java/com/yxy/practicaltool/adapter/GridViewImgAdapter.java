package com.yxy.practicaltool.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.bean.PicInfo;

import java.util.ArrayList;


public class GridViewImgAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PicInfo> list;

    public GridViewImgAdapter(Context context, ArrayList<PicInfo> list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list = list;
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
        convertView = layoutInflater.inflate(R.layout.item_add_image, null);
        ImageView iv_release_pic = (ImageView) convertView.findViewById(R.id.iv_release_pic);
        ImageView iv_delete_pic = (ImageView) convertView.findViewById(R.id.iv_delete_pic);

        if (position >= list.size()) {
            Glide.with(context)
                    .load(R.mipmap.icon_add)
                    .into(iv_release_pic);
            iv_delete_pic.setVisibility(View.GONE);
        } else {
            Glide.with(context)
                    .load(list.get(position).pic)
                    .into(iv_release_pic);
            iv_delete_pic.setVisibility(View.VISIBLE);
        }
        iv_delete_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

}

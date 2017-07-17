package com.yxy.practicaltool.activity.upload_resources;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.adapter.SubordinateUnitsTestAdapter;
import com.yxy.practicaltool.bean.UseDemoBean;
import com.yxy.practicaltool.myview.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubordinateAttributeActivity extends BaseActivity {

    @Bind(R.id.rv_left)
    CustomRecyclerView rvLeft;
    @Bind(R.id.rv_right)
    CustomRecyclerView rvRight;
    private SubordinateUnitsTestAdapter adapter;
    private ArrayList<UseDemoBean> liftList, rightList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_subordinate_attribute, "产品属性");
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        rightList = new ArrayList<>();
        liftList = new ArrayList<>();
        rvLeft.setLayoutManager(new LinearLayoutManager(this));
        rvLeft.setItemAnimator(new DefaultItemAnimator());
        adapter = new SubordinateUnitsTestAdapter(this, R.layout.item_subordinate_units, liftList);
        rvLeft.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();

        for (int i = 0; i < 10; i++) {
            UseDemoBean useDemoBean = new UseDemoBean();
            useDemoBean.name = "名字" + i;
            liftList.add(useDemoBean);
        }
        adapter.notifyDataSetChanged();
    }
}

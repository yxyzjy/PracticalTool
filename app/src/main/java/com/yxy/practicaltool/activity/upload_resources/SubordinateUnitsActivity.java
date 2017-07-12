package com.yxy.practicaltool.activity.upload_resources;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.adapter.SubordinateUnitsAdapter;
import com.yxy.practicaltool.bean.UseDemoBean;
import com.yxy.practicaltool.myview.CustomRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class SubordinateUnitsActivity extends BaseActivity {

    @Bind(R.id.et_sub_units_search)
    EditText etSubUnitsSearch;
    @Bind(R.id.btn_sub_units_search)
    ImageView btnSubUnitsSearch;
    @Bind(R.id.tv_add_units)
    TextView tvAddUnits;
    @Bind(R.id.rv_subordinate_unit)
    CustomRecyclerView rvSubordinateUnit;
    private SubordinateUnitsAdapter adapter;
    private ArrayList<UseDemoBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_subordinate_units, "所属单位");
    }

    @Override
    public void initView() {
        super.initView();
        rvSubordinateUnit.setLayoutManager(new LinearLayoutManager(this));
        rvSubordinateUnit.setItemAnimator(new DefaultItemAnimator());
        adapter = new SubordinateUnitsAdapter(this, R.layout.item_subordinate_units, list);
        rvSubordinateUnit.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        list = new ArrayList<>();
        for (int i = 0;i<10;i++){
            UseDemoBean useDemoBean = new UseDemoBean();
            useDemoBean.name = "名字"+i;
            list.add(useDemoBean);
        }
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btn_sub_units_search)
    public void onViewClicked() {
    }
}

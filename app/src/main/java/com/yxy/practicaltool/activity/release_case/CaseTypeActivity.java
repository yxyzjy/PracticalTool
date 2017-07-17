package com.yxy.practicaltool.activity.release_case;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
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
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CaseTypeActivity extends BaseActivity {

    @Bind(R.id.rv_subordinate_unit)
    CustomRecyclerView rvSubordinateUnit;
    private SubordinateUnitsAdapter adapter;
    private ArrayList<UseDemoBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_case_type, "案例分类");
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        rvSubordinateUnit.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvSubordinateUnit.setItemAnimator(new DefaultItemAnimator());
        adapter = new SubordinateUnitsAdapter(this, R.layout.item_subordinate_units, list);
        rvSubordinateUnit.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();
        for (int i = 0;i<10;i++){
            UseDemoBean useDemoBean = new UseDemoBean();
            useDemoBean.name = "名字"+i;
            list.add(useDemoBean);
        }
        adapter.notifyDataSetChanged();
    }

}

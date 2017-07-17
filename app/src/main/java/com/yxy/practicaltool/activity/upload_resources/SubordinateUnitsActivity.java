package com.yxy.practicaltool.activity.upload_resources;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.adapter.SubordinateUnitsAdapter;
import com.yxy.practicaltool.entity.api.GetCompanyListApi;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.myview.CustomRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    private ArrayList<CompanyListRes.DataBean> list = new ArrayList<>();
    private GetCompanyListApi companyListApi;
    private int lastClickPos =-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_subordinate_units, "所属单位");
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvSubordinateUnit.setLayoutManager(linearLayoutManager);
        rvSubordinateUnit.setItemAnimator(new DefaultItemAnimator());
        adapter = new SubordinateUnitsAdapter(this, R.layout.item_subordinate_units, list);
        rvSubordinateUnit.setAdapter(adapter);
    }

    @Override
    public void initData() {
        super.initData();

        companyListApi = new GetCompanyListApi();
        httpManager.doHttpDeal(companyListApi);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                list.get(position).isSelect = true;
                if (lastClickPos!=-1) {
                    list.get(lastClickPos).isSelect = false;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    @Override
    protected void processSuccessResult(String resulte, String mothead) {
        super.processSuccessResult(resulte, mothead);
        if (mothead.equals(companyListApi.getMethod())){
            CompanyListRes res = JSONObject.parseObject(resulte, CompanyListRes.class);
            list.addAll(res.data);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.btn_sub_units_search)
    public void onViewClicked() {
    }
}

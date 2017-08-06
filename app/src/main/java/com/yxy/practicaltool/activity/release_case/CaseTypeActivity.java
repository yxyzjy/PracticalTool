package com.yxy.practicaltool.activity.release_case;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONObject;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.activity.common.ActivitySimpleEdit;
import com.yxy.practicaltool.adapter.CaseTypeAdapter;
import com.yxy.practicaltool.adapter.SubordinateUnitsTestAdapter;
import com.yxy.practicaltool.bean.CaseTypeSelectBean;
import com.yxy.practicaltool.bean.UseDemoBean;
import com.yxy.practicaltool.common.L;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.entity.api.caseapi.GetCaseApi;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CaseTypeRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.myview.CustomRecyclerView;
import com.yxy.practicaltool.utils.JsonUtils;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import org.json.JSONException;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CaseTypeActivity extends BaseActivity {

    @Bind(R.id.rv_subordinate_unit)
    CustomRecyclerView rvSubordinateUnit;
    private CaseTypeAdapter adapter;
    private ArrayList<CaseTypeRes.DataBean> list = new ArrayList<>();

    private GetCaseApi getCaseApi;
    private int lastClickPos = -1;

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
        adapter = new CaseTypeAdapter(this, R.layout.item_subordinate_units, list);
        rvSubordinateUnit.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        httpManager.doHttpDeal(getCaseApi);
    }

    @Override
    public void initData() {
        super.initData();

        getCaseApi = new GetCaseApi();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                if (position == list.size()-1) {
                    ActivitySimpleEdit.startSimpleEdit(CaseTypeActivity.this, "案例分类", "输入案例分类名称", "", ActivitySimpleEdit.INPUT_NAME, 20, 201);
                    return;
                }
                if (lastClickPos != -1) {
                    list.get(lastClickPos).isSelect = false;
                }
                list.get(position).isSelect = true;
                lastClickPos = position;
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
        if (mothead.equals(getCaseApi.getMethod())) {
            org.json.JSONObject jsonObject = JsonUtils.parseFromJson(resulte);
            org.json.JSONArray dataJsonObject = JsonUtils.getJsonArry(jsonObject, "data");
            list.clear();
            for (int i = 0; i < dataJsonObject.length(); i++) {
                try {
                    org.json.JSONObject itemObject = dataJsonObject.getJSONObject(i);
                    CaseTypeRes.DataBean bean = new CaseTypeRes.DataBean();
                    bean.Id = JsonUtils.getJsonInt(itemObject, "Id");
                    bean.Title = JsonUtils.getJsonString(itemObject, "Title");
                    list.add(bean);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
//            CaseTypeRes res = JSONObject.parseObject(resulte, CaseTypeRes.class);
//            list.addAll(res.data);
            CaseTypeRes.DataBean bean = new CaseTypeRes.DataBean();
            list.add(bean);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 101) {
                lastClickPos = -1;
                httpManager.doHttpDeal(getCaseApi);
            }
        }
    }

    public void rightClickSave(View view) {
        if (lastClickPos == -1) {
            ToastUtils.showToast(mContext, "请选择所属单位");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("case_type", list.get(lastClickPos));
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}

package com.yxy.practicaltool.activity.upload_resources;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.adapter.AttributeAdapter;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.entity.api.GetAttributeListApi;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.myview.CustomRecyclerView;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SubordinateAttributeActivity extends BaseActivity {

    @Bind(R.id.rv_left)
    CustomRecyclerView rvLeft;
    @Bind(R.id.rv_right)
    CustomRecyclerView rvRight;
    private AttributeAdapter leftAdapter, rightAdapter;
    private ArrayList<AttributeListRes.DataBean> liftList;
    private ArrayList<AttributeListRes.DataBean> rightList;
    private Map<Integer, AttributeListRes.DataBean> selectList;
    private String parentId = "0";
    private int leftLastClickPos = -1, rightLastPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_subordinate_attribute, "产品属性");
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        selectList = new HashMap<>();
        rightList = new ArrayList<>();
        liftList = new ArrayList<>();
        rvLeft.setLayoutManager(new LinearLayoutManager(this));
        rvLeft.setItemAnimator(new DefaultItemAnimator());
        leftAdapter = new AttributeAdapter(this, R.layout.item_subordinate_units, liftList);
        rvLeft.setAdapter(leftAdapter);
        rvRight.setLayoutManager(new LinearLayoutManager(this));
        rvRight.setItemAnimator(new DefaultItemAnimator());
        rightAdapter = new AttributeAdapter(this, R.layout.item_subordinate_units, rightList);
        rvRight.setAdapter(rightAdapter);
    }

    @Override
    public void initData() {
        super.initData();

        doHttp();

        leftAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                liftList.get(position).isSelect = true;
                if (leftLastClickPos != -1) {
                    liftList.get(leftLastClickPos).isSelect = false;
                }
                leftLastClickPos = position;
                leftAdapter.notifyDataSetChanged();
                parentId = liftList.get(position).ID + "";
                doHttp();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });

        rightAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                rightList.get(position).isSelect = !rightList.get(position).isSelect;
                if (rightList.get(position).isSelect) {
                    selectList.put(rightList.get(position).ID, rightList.get(position));
                } else {
                    selectList.remove(rightList.get(position).ID);
                }
                rightAdapter.notifyDataSetChanged();
                parentId = rightList.get(position).ID + "";
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    private void doHttp() {
        GetAttributeListApi attributeListApi = new GetAttributeListApi(parentId);
        httpManager.doHttpDeal(attributeListApi);
    }

    @Override
    protected void processSuccessResult(String resulte, String mothead) {
        super.processSuccessResult(resulte, mothead);
        if (mothead.equals("GetAttributeList0")) {
            AttributeListRes res = JSONObject.parseObject(resulte, AttributeListRes.class);
            liftList.addAll(res.data);
            leftAdapter.notifyDataSetChanged();
        } else {
            rightList.clear();
            AttributeListRes res = JSONObject.parseObject(resulte, AttributeListRes.class);
            for (int i = 0; i < res.data.size(); i++) {
                if (selectList.containsKey(res.data.get(i).ID)) {
                    res.data.get(i).isSelect = true;
                }
            }
            rightList.addAll(res.data);
            rightAdapter.notifyDataSetChanged();
        }
    }

    public void rightClickSave(View view) {
        if (selectList.size() < 1) {
            ToastUtils.showToast(mContext, "请选择产品属性");
            return;
        }
        String seleName = "";
        String seleId = "";

        for (Map.Entry<Integer, AttributeListRes.DataBean> entry : selectList.entrySet()) {
            AttributeListRes.DataBean value = entry.getValue();
            if (TextUtils.isEmpty(seleName)) {
                seleName = value.Cname;
                seleId = value.ID + "";
            } else {
                seleName = seleName + "," + value.Cname;
                seleId = seleId + "," + value.ID;
            }
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("selectName", seleName.toString());
        bundle.putSerializable("selectId", seleId.toString());
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}

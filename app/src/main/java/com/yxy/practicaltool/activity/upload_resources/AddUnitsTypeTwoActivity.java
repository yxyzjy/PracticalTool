package com.yxy.practicaltool.activity.upload_resources;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yxy.practicaltool.MyApplication;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.activity.common.ActivitySimpleEdit;
import com.yxy.practicaltool.activity.common.ActivitySimpleEditLines;
import com.yxy.practicaltool.common.L;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.dao.TestDao;
import com.yxy.practicaltool.dao.UploadResourcesDaoDao;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.gen.Test;
import com.yxy.practicaltool.gen.UploadResourcesDao;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class AddUnitsTypeTwoActivity extends BaseActivity {


    @Bind(R.id.tv_typeTwo_1)
    TextView tvTypeTwo1;
    @Bind(R.id.et_typeTwo_2)
    EditText etTypeTwo2;
    @Bind(R.id.et_typeTwo_3)
    EditText etTypeTwo3;
    @Bind(R.id.et_typeTwo_4)
    EditText etTypeTwo4;
    @Bind(R.id.tv_add_units_nexttype)
    TextView tvAddUnitsNexttype;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_add_units_type_two, "添加单位", false);
    }

    @Override
    public void initData() {
        super.initData();
        phone = getIntent().getStringExtra("phone");
        tvTypeTwo1.setText(phone);
    }


    @OnClick(R.id.tv_add_units_nexttype)
    public void onViewClicked() {
    }
}

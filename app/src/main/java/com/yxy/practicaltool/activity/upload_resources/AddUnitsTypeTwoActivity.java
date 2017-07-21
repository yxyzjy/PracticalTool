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
import com.yxy.practicaltool.entity.api.AddCompanyApi;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.gen.Test;
import com.yxy.practicaltool.gen.UploadResourcesDao;
import com.yxy.practicaltool.utils.AppManager;

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

    private AddCompanyApi addCompanyApi;
    private AppManager appManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_add_units_type_two, "添加单位", false);
        appManager = AppManager.getAppManager();
        appManager.addActivity(this);
    }

    @Override
    public void initData() {
        super.initData();
        phone = getIntent().getStringExtra("phone");
        tvTypeTwo1.setText(phone);

        addCompanyApi = new AddCompanyApi();
    }


    @OnClick(R.id.tv_add_units_nexttype)
    public void onViewClicked() {

        if (checkEdit()) {
            addCompanyApi.name = etTypeTwo2.getText().toString().trim();
            addCompanyApi.charge = etTypeTwo3.getText().toString().trim();
            addCompanyApi.address = etTypeTwo4.getText().toString().trim();
            addCompanyApi.phone = phone;
            httpManager.doHttpDeal(addCompanyApi);
        }
    }

    @Override
    protected void processSuccessResult(String resulte, String mothead) {
        super.processSuccessResult(resulte, mothead);
        if (mothead.equals(addCompanyApi.getMethod())) {
            ToastUtils.showToast(mContext, "添加成功");
            appManager.finishAllActivity();
        }
    }

    private boolean checkEdit() {
        if (TextUtils.isEmpty(etTypeTwo2.getText().toString().trim())) {
            ToastUtils.showToast(mContext, "请输入单位名称");
            return false;
        }
        if (TextUtils.isEmpty(etTypeTwo3.getText().toString().trim())) {
            ToastUtils.showToast(mContext, "请输入负责人姓名");
            return false;
        }
        if (TextUtils.isEmpty(etTypeTwo4.getText().toString().trim())) {
            ToastUtils.showToast(mContext, "请输入单位地址");
            return false;
        }
        return true;

    }
}

package com.yxy.practicaltool.activity.upload_resources;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.dialog.EditMsgpop;
import com.yxy.practicaltool.entity.api.CheckCompanyPhoneApi;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CheckCompanyPhoneRes;
import com.yxy.practicaltool.utils.AppManager;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by yxy on 2017/7/21 0021.
 */

public class AddUnitsTypeOneActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.et_add_units_phone)
    EditText etAddUnitsPhone;
    @Bind(R.id.tv_add_units_nexttype)
    TextView tvAddUnitsNexttype;

    private AppManager appManager;
    private CheckCompanyPhoneApi checkCompanyPhoneApi;
    private EditMsgpop editMsgpop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_add_units_type_one, "添加单位", false);

        appManager = AppManager.getAppManager();
        appManager.addActivity(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {
        super.initData();
        checkCompanyPhoneApi = new CheckCompanyPhoneApi();
        editMsgpop = new EditMsgpop(mContext, this, "提示");
    }

    @OnClick(R.id.tv_add_units_nexttype)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etAddUnitsPhone.getText().toString().trim())) {
            ToastUtils.showToast(mContext, "请输入手机号");
            return;
        }
        checkCompanyPhoneApi.phone = etAddUnitsPhone.getText().toString().trim();
        httpManager.doHttpDeal(checkCompanyPhoneApi);

    }

    @Override
    protected void processSuccessResult(String resulte, String mothead) {
        super.processSuccessResult(resulte, mothead);
        if (mothead.equals(checkCompanyPhoneApi.getMethod())) {
            CheckCompanyPhoneRes checkCompanyPhoneRes = JSONObject.parseObject(resulte, CheckCompanyPhoneRes.class);
            if (checkCompanyPhoneRes.data == null) {
                Intent intent = new Intent(mContext, AddUnitsTypeTwoActivity.class);
                intent.putExtra("phone", etAddUnitsPhone.getText().toString().trim());
                startActivity(intent);
            } else {
                editMsgpop.setText("该单位已存在！\n" + checkCompanyPhoneRes.data.CName + "(" + checkCompanyPhoneRes.data.Phone + ")\n" + "地址：" + checkCompanyPhoneRes.data.Address);
                editMsgpop.showAtLocation(etAddUnitsPhone, Gravity.CENTER, 0, 0);
            }

//
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle_msg_dailog:
                if (editMsgpop.isShowing()) {
                    editMsgpop.dismiss();
                    etAddUnitsPhone.setText("");
                }
                break;
            case R.id.tv_ensure_msg_dailog:
                if (editMsgpop.isShowing()) {
                    editMsgpop.dismiss();
                    Intent intent = new Intent(mContext, AddUnitsTypeTwoActivity.class);
                    intent.putExtra("phone", etAddUnitsPhone.getText().toString().trim());
                    startActivity(intent);
                }
                break;
        }

    }
}

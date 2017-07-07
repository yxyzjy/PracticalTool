package com.yxy.practicaltool.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.entity.api.LoginApi;
import com.yxy.practicaltool.entity.resulte.LoginRes;
import com.yxy.practicaltool.utils.SPUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class LoginActivity extends BaseActivity {

    @Bind(R.id.et_username)
    EditText etUsername;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_login)
    TextView tvLogin;

    private LoginApi loginApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_login, "");
        ButterKnife.bind(this);

        if (SPUtil.getBoolean("loginSuccess", false)) {
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
    }

    @Override
    public void initData() {
        super.initData();
        loginApi = new LoginApi();
    }

    @OnClick(R.id.tv_login)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
            ToastUtils.showToast(mContext, "请输入账号");
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            ToastUtils.showToast(mContext, "请输入密码");
            return;
        }
        loginApi.setUsername(etUsername.getText().toString().trim());
        loginApi.setPassword(etPassword.getText().toString().trim());
        httpManager.doHttpDeal(loginApi);
    }

    @Override
    protected void processSuccessResult(String resulte, String mothead) {
        super.processSuccessResult(resulte, mothead);
        if (mothead.equals(loginApi.getMethod())) {
            LoginRes res = JSONObject.parseObject(resulte, LoginRes.class);
            SPUtil.put("real_name", res.real_name);
            SPUtil.put("desUserId", res.desUserId);
            SPUtil.put("loginSuccess", true);
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
    }
}

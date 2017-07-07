package com.yxy.practicaltool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.utils.SPUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.tv_main_1)
    TextView tvMain1;
    @Bind(R.id.tv_main_2)
    TextView tvMain2;
    @Bind(R.id.tv_main_3)
    TextView tvMain3;
    @Bind(R.id.tv_main_4)
    TextView tvMain4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_main, "");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_main_1, R.id.tv_main_2, R.id.tv_main_3, R.id.tv_main_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_main_1:
                break;
            case R.id.tv_main_2:
                break;
            case R.id.tv_main_3:
                break;
            case R.id.tv_main_4:
                SPUtil.clear();
                startActivity(new Intent(mContext,LoginActivity.class));
                finish();
                break;
        }
    }
}




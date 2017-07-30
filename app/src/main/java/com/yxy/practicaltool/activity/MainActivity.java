package com.yxy.practicaltool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yxy.practicaltool.MyApplication;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.release_case.ReleaseCaseActivity;
import com.yxy.practicaltool.activity.upload_resources.LocalCacheActivity;
import com.yxy.practicaltool.activity.upload_resources.UploadResourcesActivity;
import com.yxy.practicaltool.common.Constants;
import com.yxy.practicaltool.dao.UploadResourcesDaoDao;
import com.yxy.practicaltool.utils.FileUtil;
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
    private UploadResourcesDaoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_main, "");
        ButterKnife.bind(this);

        FileUtil ft = new FileUtil();
        ft.creatSDDir(Constants.DATA_DIR);
    }

    @OnClick({R.id.tv_main_1, R.id.tv_main_2, R.id.tv_main_3, R.id.tv_main_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_main_1:
                startActivity(new Intent(mContext, UploadResourcesActivity.class));
                break;
            case R.id.tv_main_2:
                startActivity(new Intent(mContext, LocalCacheActivity.class));
                break;
            case R.id.tv_main_3:
                startActivity(new Intent(mContext, ReleaseCaseActivity.class));
                break;
            case R.id.tv_main_4:
                dao = MyApplication.getInstances().getDaoSession().getUploadResourcesDaoDao();
                dao.deleteAll();
                SPUtil.clear();
                startActivity(new Intent(mContext,LoginActivity.class));
                finish();
                break;
        }
    }
}




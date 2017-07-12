package com.yxy.practicaltool.activity.upload_resources;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class UploadResourcesActivity extends BaseActivity {

    @Bind(R.id.tv_content_1)
    TextView tvContent1;
    @Bind(R.id.ll_upload_1)
    LinearLayout llUpload1;
    @Bind(R.id.tv_content_2)
    TextView tvContent2;
    @Bind(R.id.ll_upload_2)
    LinearLayout llUpload2;
    @Bind(R.id.tv_content_3)
    TextView tvContent3;
    @Bind(R.id.ll_upload_3)
    LinearLayout llUpload3;
    @Bind(R.id.tv_content_4)
    TextView tvContent4;
    @Bind(R.id.ll_upload_4)
    LinearLayout llUpload4;
    @Bind(R.id.tv_content_5)
    TextView tvContent5;
    @Bind(R.id.ll_upload_5)
    LinearLayout llUpload5;
    @Bind(R.id.tv_content_6)
    TextView tvContent6;
    @Bind(R.id.ll_upload_6)
    LinearLayout llUpload6;
    @Bind(R.id.tv_content_7)
    TextView tvContent7;
    @Bind(R.id.ll_upload_7)
    LinearLayout llUpload7;
    @Bind(R.id.rb_1)
    RadioButton rb1;
    @Bind(R.id.rb_2)
    RadioButton rb2;
    @Bind(R.id.gv_select_pic)
    GridView gvSelectPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_upload_resources, "上传苗木资源");
    }

    @OnClick({R.id.ll_upload_1, R.id.ll_upload_2, R.id.ll_upload_3, R.id.ll_upload_4, R.id.ll_upload_5, R.id.ll_upload_6, R.id.rb_1, R.id.rb_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_upload_1:
                startActivity(new Intent(mContext,SubordinateUnitsActivity.class));
                break;
            case R.id.ll_upload_2:
                break;
            case R.id.ll_upload_3:
                break;
            case R.id.ll_upload_4:
                break;
            case R.id.ll_upload_5:
                break;
            case R.id.ll_upload_6:
                break;
            case R.id.rb_1:
                break;
            case R.id.rb_2:
                break;
        }
    }
}

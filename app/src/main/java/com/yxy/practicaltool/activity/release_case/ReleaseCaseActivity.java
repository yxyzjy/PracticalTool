package com.yxy.practicaltool.activity.release_case;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.activity.common.ActivitySimpleEdit;
import com.yxy.practicaltool.activity.common.ActivitySimpleEditLines;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseCaseActivity extends BaseActivity {

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
    @Bind(R.id.gv_select_pic)
    GridView gvSelectPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_release_case, "发布案例");
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_upload_1, R.id.ll_upload_2, R.id.ll_upload_3, R.id.ll_upload_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_upload_1:
                startActivity(new Intent(mContext,CaseTypeActivity.class));
                break;
            case R.id.ll_upload_2:
                ActivitySimpleEdit.startSimpleEdit(mContext,"案例标题","输入案例标题","",ActivitySimpleEdit.INPUT_NAME,20);
                break;
            case R.id.ll_upload_3:
                ActivitySimpleEdit.startSimpleEdit(mContext,"关键字","输入关键字","",ActivitySimpleEdit.INPUT_NAME,20);
                break;
            case R.id.ll_upload_4:
                ActivitySimpleEditLines.startSimpleEdit(mContext,"页面描述","输入页面描述","",ActivitySimpleEdit.INPUT_NUM,200);
                break;
        }
    }
}

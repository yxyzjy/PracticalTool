package com.yxy.practicaltool.activity.upload_resources;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;

public class SubordinateUnitsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_subordinate_units, "所属单位");
    }
}

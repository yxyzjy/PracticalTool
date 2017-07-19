package com.yxy.practicaltool.activity.upload_resources;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afa.tourism.greendao.gen.UploadResourcesDaoDao;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DaoMaster;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.download.DaoSession;
import com.yxy.practicaltool.MyApplication;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.activity.common.ActivitySimpleEdit;
import com.yxy.practicaltool.activity.common.ActivitySimpleEditLines;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.gen.UploadResourcesDao;
import com.yxy.practicaltool.utils.Utils;

import butterknife.Bind;
import butterknife.OnClick;

public class UploadResourcesActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

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
    @Bind(R.id.rg_upload)
    RadioGroup rgUpload;
    private CompanyListRes.DataBean unitsData;
    private String name2;
    private String num4;
    private String des5;
    private String tip6;
    private AttributeListRes.DataBean attributeData;
    private int sallState = -1;
    private CompanyListRes.DataBean pinzhongData;

    private UploadResourcesDaoDao dao;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_upload_resources, "上传苗木资源");
    }

    @Override
    public void initData() {
        super.initData();
        rgUpload.setOnCheckedChangeListener(this);

        dao = MyApplication.getInstances().getDaoSession().getUploadResourcesDaoDao();
        db = MyApplication.getInstances().getDb();
    }

    @OnClick({R.id.ll_upload_1, R.id.ll_upload_2, R.id.ll_upload_3, R.id.ll_upload_4, R.id.ll_upload_5, R.id.ll_upload_6, R.id.ll_upload_7, R.id.rb_1, R.id.rb_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_upload_1:
                startActivityForResult(new Intent(mContext, SubordinateUnitsActivity.class), 101);
                break;
            case R.id.ll_upload_2:
                ActivitySimpleEdit.startSimpleEdit(this, "产品名", "输入产品名", "", ActivitySimpleEdit.INPUT_NAME, 20, 102);
                break;
            case R.id.ll_upload_3:
                startActivityForResult(new Intent(mContext, SubordinatePinZhongActivity.class), 103);
                break;
            case R.id.ll_upload_4:
                ActivitySimpleEdit.startSimpleEdit(this, "产品数量", "输入产品数量", "", ActivitySimpleEdit.INPUT_NUM, 8, 104);
                break;
            case R.id.ll_upload_5:
                ActivitySimpleEditLines.startSimpleEdit(this, "质量描述", "输入质量描述", "", ActivitySimpleEditLines.INPUT_NAME, 200, 105);
                break;
            case R.id.ll_upload_6:
                ActivitySimpleEditLines.startSimpleEdit(this, "产品备注", "输入产品备注", "", ActivitySimpleEditLines.INPUT_NAME, 200, 106);
                break;
            case R.id.ll_upload_7:
                startActivityForResult(new Intent(this, SubordinateAttributeActivity.class), 107);
                break;
            case R.id.rb_1:
                break;
            case R.id.rb_2:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 101) {
                unitsData = (CompanyListRes.DataBean) data.getSerializableExtra("units");
                tvContent1.setText(unitsData.CName + "(" + unitsData.Phone + ")");
            }
            if (requestCode == 102) {
                name2 = data.getStringExtra("result");
                tvContent2.setText(name2);
            }
            if (requestCode == 103) {
                pinzhongData = (CompanyListRes.DataBean) data.getSerializableExtra("pinzhong");
                tvContent3.setText(pinzhongData.CName);
            }
            if (requestCode == 104) {
                num4 = data.getStringExtra("result");
                tvContent4.setText(num4);
            }
            if (requestCode == 105) {
                des5 = data.getStringExtra("result");
                tvContent5.setText(des5);
            }
            if (requestCode == 106) {
                tip6 = data.getStringExtra("result");
                tvContent6.setText(tip6);
            }
            if (requestCode == 107) {
                attributeData = (AttributeListRes.DataBean) data.getSerializableExtra("attribute");
                tvContent7.setText(attributeData.Cname);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_1:
                sallState = 1;
                break;
            case R.id.rb_2:
                sallState = 2;
                break;
        }
    }

    @Override
    public void rightClickSave(View view) {
        super.rightClickSave(view);
        if (checkEditAll()) {
//            if (Utils.isWifiConnected(mContext)) {
//            提交数据

//            } else {
            db.beginTransaction();
            try {

                insertData();
                finish();

            } catch (Exception e) {
            } finally {
                db.endTransaction();
            }
            }
        }

        /**
         * add
         */

    private void insertData() {
        UploadResourcesDao usdao = new UploadResourcesDao(unitsData.ID, tvContent1.getText().toString(),
                name2, pinzhongData.ID, pinzhongData.CName, num4, des5, tip6, sallState, attributeData.ID, tvContent7.getText().toString());

        dao.insert(usdao);
    }

    private boolean checkEditAll() {
        if (unitsData == null) {
            ToastUtils.showToast(mContext, "请选择所属单位");
            return false;
        }
        if (TextUtils.isEmpty(name2)) {
            ToastUtils.showToast(mContext, "请输入产品名称");
            return false;
        }
//        if (TextUtils.isEmpty(num4)) {
//            ToastUtils.showToast(mContext, "请输入产品数量");
//            return false;
//        }
//        if (TextUtils.isEmpty(des5)) {
//            ToastUtils.showToast(mContext, "请输入质量描述");
//            return false;
//        }
//        if (TextUtils.isEmpty(tip6)) {
//            ToastUtils.showToast(mContext, "请输入产品备注");
//            return false;
//        }
//        if (attributeData == null) {
//            ToastUtils.showToast(mContext, "请选择产品属性");
//            return false;
//        }
        return true;
    }


}

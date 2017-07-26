package com.yxy.practicaltool.activity.release_case;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.activity.common.ActivitySimpleEdit;
import com.yxy.practicaltool.activity.common.ActivitySimpleEditLines;
import com.yxy.practicaltool.adapter.GridViewCaseImgAdapter;
import com.yxy.practicaltool.adapter.GridViewImgAdapter;
import com.yxy.practicaltool.bean.PicInfo;
import com.yxy.practicaltool.common.Constants;
import com.yxy.practicaltool.dialog.SelectPhotoDialog;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CaseTypeRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.utils.FileUtil;
import com.yxy.practicaltool.utils.ImageUtils;
import com.yxy.practicaltool.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseCaseActivity extends BaseActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

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

    private SelectPhotoDialog selectPhotoDialog;
    private String title, key, des, fileName, filePath, imageToPath;
    private GridViewCaseImgAdapter viewImgAdapter;
    private ArrayList<PicInfo> picList = new ArrayList<>();
    ImageUtils imageUtils;
    private CaseTypeRes.DataBean caseTypeRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_release_case, "发布案例");
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        viewImgAdapter = new GridViewCaseImgAdapter(this, picList, this);
        gvSelectPic.setAdapter(viewImgAdapter);
        gvSelectPic.setOnItemClickListener(this);
        imageUtils = new ImageUtils(mContext);
    }

    @OnClick({R.id.ll_upload_1, R.id.ll_upload_2, R.id.ll_upload_3, R.id.ll_upload_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_upload_1:
                startActivityForResult(new Intent(mContext, CaseTypeActivity.class), 204);
                break;
            case R.id.ll_upload_2:
                ActivitySimpleEdit.startSimpleEdit(ReleaseCaseActivity.this, "案例标题", "输入案例标题", "", ActivitySimpleEdit.INPUT_NAME, 20, 201);
                break;
            case R.id.ll_upload_3:
                ActivitySimpleEdit.startSimpleEdit(ReleaseCaseActivity.this, "关键字", "输入关键字", "", ActivitySimpleEdit.INPUT_NAME, 20, 202);
                break;
            case R.id.ll_upload_4:
                ActivitySimpleEditLines.startSimpleEdit(ReleaseCaseActivity.this, "页面描述", "输入页面描述", "", ActivitySimpleEdit.INPUT_NAME, 200, 203);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {//拍照
//            String path = FileUtil.getRealFilePath(this, Uri.fromFile(file));
            try {
                ExifInterface exifInterface = new ExifInterface(filePath + fileName);
                String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                imageToPath = imageUtils.compressImageToPath(imageUtils.getBitmapByPathNoRotate(filePath + fileName));
                PicInfo picInfo = new PicInfo();
                picInfo.pic = imageToPath;
                picInfo.latValue = latValue;
                picInfo.lngValue = lngValue;
                picList.add(picInfo);
                viewImgAdapter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
                PicInfo picInfo = new PicInfo();
                picInfo.pic = imageToPath;
                picInfo.latValue = "";
                picInfo.lngValue = "";
                picList.add(picInfo);
                viewImgAdapter.notifyDataSetChanged();
            }
        }
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 201) {
                title = data.getStringExtra("result");
                tvContent2.setText(title);
            }
            if (requestCode == 202) {
                key = data.getStringExtra("result");
                tvContent3.setText(key);
            }
            if (requestCode == 203) {
                des = data.getStringExtra("result");
                tvContent4.setText(key);
            }
            if (requestCode == 204) {
                caseTypeRes = (CaseTypeRes.DataBean) data.getSerializableExtra("attribute");
                tvContent1.setText(caseTypeRes.Title);
            }

            if (requestCode == 112) {//从相册选
                try {
                    String path = FileUtil.getRealFilePath(this, data.getData());
                    ExifInterface exifInterface = new ExifInterface(path);
                    String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                    String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                    imageToPath = imageUtils.compressImageToPath(imageUtils.getBitmapByPathNoRotate(path));
                    PicInfo picInfo = new PicInfo();
                    picInfo.pic = imageToPath;
                    picInfo.latValue = latValue;
                    picInfo.lngValue = lngValue;
                    picList.add(picInfo);
                    viewImgAdapter.notifyDataSetChanged();
                } catch (IOException e) {
                    e.printStackTrace();
                    PicInfo picInfo = new PicInfo();
                    picInfo.pic = imageToPath;
                    picInfo.latValue = "";
                    picInfo.lngValue = "";
                    picList.add(picInfo);
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if (i >= picList.size()) {
            checkPermission();
        }
    }

    //--------------------选择照片----------------
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.CAMERA_REQUEST);
            } else ShowPicDialog();
        } else ShowPicDialog();
    }

    private void ShowPicDialog() {
        //拍照
// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
//从相册选择
        if (selectPhotoDialog == null) {
            selectPhotoDialog = new SelectPhotoDialog(mContext, new SelectPhotoDialog.onBtnClickListener() {
                @Override
                public void onSure() {
                    //拍照
                    if (Utils.checkSDCardAvailable()) {
                        //由于登陆机制。需要在此创建一次文件夹 防止app没创建
                        FileUtil ft = new FileUtil();
                        ft.creatSDDir(Constants.DATA_DIR);
                        File fos = null;
                        filePath = Utils.getSDCardPath() + Constants.DATA_DIR;
                        fileName = Utils.getImageFileName();
                        fos = new File(filePath + fileName);
                        Uri uri = Uri.fromFile(fos);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//保存到指定目录
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 111);
                    }
                }

                @Override
                public void onExit() {
                    //从相册选择
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 112);
                }
            });
            selectPhotoDialog.show();
        } else {
            selectPhotoDialog.show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_case_des:

                break;
        }
    }
}

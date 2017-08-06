package com.yxy.practicaltool.activity.upload_resources;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yxy.practicaltool.MyApplication;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.activity.common.ActivitySimpleEdit;
import com.yxy.practicaltool.activity.common.ActivitySimpleEditLines;
import com.yxy.practicaltool.adapter.GridViewImgAdapter;
import com.yxy.practicaltool.bean.PicInfo;
import com.yxy.practicaltool.bean.PicLoadBean;
import com.yxy.practicaltool.common.Constants;
import com.yxy.practicaltool.common.L;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.dao.TestDao;
import com.yxy.practicaltool.dao.UploadResourcesDaoDao;
import com.yxy.practicaltool.dialog.SelectPhotoDialog;
import com.yxy.practicaltool.entity.api.AddProductApi;
import com.yxy.practicaltool.entity.api.UpImgBase64Api;
import com.yxy.practicaltool.entity.resulte.AddCaseRes;
import com.yxy.practicaltool.entity.resulte.AddProduceRes;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.entity.resulte.Uploadbase64Res;
import com.yxy.practicaltool.gen.UploadResourcesDao;
import com.yxy.practicaltool.nohttp.CallServer;
import com.yxy.practicaltool.nohttp.CustomHttpListener;
import com.yxy.practicaltool.utils.BitmapHelper;
import com.yxy.practicaltool.utils.FileUtil;
import com.yxy.practicaltool.utils.ImageUtils;
import com.yxy.practicaltool.utils.SPUtil;
import com.yxy.practicaltool.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

public class UploadResourcesActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {

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
    private GridViewImgAdapter viewImgAdapter;
    private ArrayList<PicInfo> picList = new ArrayList<>();
    private SelectPhotoDialog selectPhotoDialog;
    private File file;
    private Uri imageUri;
    public static String filePath, fileName;
    private String imageToPath, piclists;
    private AddProductApi addProductApi;
    private UpImgBase64Api upImgBase64Api;
    private int uploadPos = 0;
    private ArrayList<PicLoadBean> picLoadBeanArrayList = new ArrayList<>();
    BitmapFactory.Options opt;
    ImageUtils imageUtils;
    private Request<String> request;
    private String attributeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_upload_resources, "上传苗木资源");
    }

    @Override
    public void initData() {
        super.initData();
        rgUpload.setOnCheckedChangeListener(this);

        imageUtils = new ImageUtils(mContext);
        dao = MyApplication.getInstances().getDaoSession().getUploadResourcesDaoDao();

        viewImgAdapter = new GridViewImgAdapter(this, picList);
        gvSelectPic.setAdapter(viewImgAdapter);
        gvSelectPic.setOnItemClickListener(this);
        addProductApi = new AddProductApi();
        upImgBase64Api = new UpImgBase64Api();
        opt = new BitmapFactory.Options();
        opt.inSampleSize = 4;
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
        if (requestCode == 111) {//拍照
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
                String selectName = data.getStringExtra("selectName");
                attributeId = data.getStringExtra("selectId");

                tvContent7.setText(selectName);
            }


            if (requestCode == 112) {//从相册选
                try {
                    String path = FileUtil.getRealFilePath(this, data.getData());
                    ExifInterface exifInterface = new ExifInterface(path);
                    String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                    String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                    imageToPath = imageUtils.compressImageToPath(imageUtils.getBitmapByPathNoRotate(path));
//                    imageToPath = imageUtils.compressImageToPath(BitmapFactory.decodeFile(filePath));
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

    private void commitPic(String path, int sign) {
        request = NoHttp.createStringRequest(Constants.UpImgBase64, RequestMethod.POST);
        request.add("random", SPUtil.getString("random", ""));
        request.add("desUserId", SPUtil.getString("desUserId", ""));
        request.add("sign", sign);
        request.add("txtFileName", Utils.bitmapToBase64(BitmapHelper.getImage(path, 100)));

        CallServer.getRequestInstance().add(UploadResourcesActivity.this, 1, request, new CustomHttpListener(this, true, Uploadbase64Res.class) {
            @Override
            public void doWork(int what, Object data, boolean isSuccess) {
                if (isSuccess) {
                    Uploadbase64Res res = (Uploadbase64Res) data;
                    picList.get(res.data.sign).serverFileName = res.data.serverFileName;
                    picList.get(res.data.sign).serverThumbnailFileName = res.data.serverThumbnailFileName;
                    if (res.data.sign < picList.size() - 1) {
                        int num = res.data.sign + 1;
                        commitPic(picList.get(num).pic, num);
                    } else {
                        submitData();
                    }
                } else {
                }
            }
        }, true, true);
    }

    public void rightClickSave(View view) {
        commitPic(picList.get(0).pic, 0);
        if (checkEditAll()) {
            if (Utils.isWifiConnected(mContext)) {
                commitPic(picList.get(0).pic, 0);
//            提交数据
            } else {
                try {
                    insertData();
                    finish();
                } catch (Exception e) {
                }
            }
        }
    }

    private void submitData() {
        for (int i = 0; i < picList.size(); i++) {
            PicInfo picInfo = picList.get(i);
            if (i == 0) {
                piclists = "0|" + picInfo.serverFileName + "|" + picInfo.serverThumbnailFileName + "|" + picInfo.lngValue + ";" + picInfo.latValue;
            } else {
                piclists = piclists+",0|" + picInfo.serverFileName + "|" + picInfo.serverThumbnailFileName + "|" + picInfo.lngValue + ";" + picInfo.latValue;
            }
        }
        addProductApi.CName = name2;
        addProductApi.Vid = pinzhongData.ID;
        addProductApi.Cid = unitsData.ID;
        addProductApi.H_State = sallState;
        addProductApi.Num = num4;
        addProductApi.Describe = des5;
        addProductApi.Remarks = tip6;
        addProductApi.PhotoDetail = piclists;
        addProductApi.ProductAttr = attributeId;
        httpManager.doHttpDeal(addProductApi);
    }

    /**
     * add
     */

    private void insertData() {
        for (int i = 0; i < picList.size(); i++) {
            PicInfo picInfo = picList.get(i);
            if (i == 0) {
                piclists = picInfo.pic +"|" + picInfo.lngValue + ";" + picInfo.latValue;
            } else {
                piclists = piclists+"," + picInfo.pic + "|" + picInfo.lngValue + ";" + picInfo.latValue;
            }
        }


        UploadResourcesDao usdao = new UploadResourcesDao(unitsData.ID, unitsData.CName, unitsData.Phone,
                name2, pinzhongData.ID, pinzhongData.CName, num4, des5, tip6, sallState, attributeId,
                tvContent7.getText().toString(), piclists, Utils.getCurrentTime());

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
        if (TextUtils.isEmpty(num4)) {
            ToastUtils.showToast(mContext, "请输入产品数量");
            return false;
        }
        if (TextUtils.isEmpty(des5)) {
            ToastUtils.showToast(mContext, "请输入质量描述");
            return false;
        }
        if (TextUtils.isEmpty(tip6)) {
            ToastUtils.showToast(mContext, "请输入产品备注");
            return false;
        }
        if (TextUtils.isEmpty(attributeId)) {
            ToastUtils.showToast(mContext, "请选择产品属性");
            return false;
        }
        if (picList.size() < 1) {
            ToastUtils.showToast(mContext, "请选择图片");
            return false;
        }
        return true;
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
    protected void processSuccessResult(String resulte, String mothead) {
        super.processSuccessResult(resulte, mothead);
        if (mothead.equals(addProductApi.getMethod())) {
            AddProduceRes res = JSONObject.parseObject(resulte, AddProduceRes.class);
            if (res.ret == 200) {
                ToastUtils.showToast(mContext, res.msg);
                finish();
            }
        }
    }
}

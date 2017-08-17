package com.yxy.practicaltool.activity.release_case;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.activity.common.ActivitySimpleEdit;
import com.yxy.practicaltool.activity.common.ActivitySimpleEditLines;
import com.yxy.practicaltool.activity.upload_resources.UploadResourcesActivity;
import com.yxy.practicaltool.adapter.GridViewCaseImgAdapter;
import com.yxy.practicaltool.adapter.GridViewImgAdapter;
import com.yxy.practicaltool.bean.EmptyBean;
import com.yxy.practicaltool.bean.PicInfo;
import com.yxy.practicaltool.common.Constants;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.dialog.SelectPhotoDialog;
import com.yxy.practicaltool.entity.api.caseapi.AddShipmentCaseApi;
import com.yxy.practicaltool.entity.resulte.AddProduceRes;
import com.yxy.practicaltool.entity.resulte.AttributeListRes;
import com.yxy.practicaltool.entity.resulte.CaseTypeRes;
import com.yxy.practicaltool.entity.resulte.CompanyListRes;
import com.yxy.practicaltool.entity.resulte.UpLoadbase64PostRes;
import com.yxy.practicaltool.entity.resulte.Uploadbase64Res;
import com.yxy.practicaltool.nohttp.CallServer;
import com.yxy.practicaltool.nohttp.CustomHttpListener;
import com.yxy.practicaltool.utils.BitmapHelper;
import com.yxy.practicaltool.utils.FileUtil;
import com.yxy.practicaltool.utils.ImageUtils;
import com.yxy.practicaltool.utils.JsonUtils;
import com.yxy.practicaltool.utils.SPUtil;
import com.yxy.practicaltool.utils.Utils;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseCaseActivity extends BaseActivity implements AdapterView.OnItemClickListener {

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
    @Bind(R.id.gv_select_pic)
    GridView gvSelectPic;

    private SelectPhotoDialog selectPhotoDialog;
    private String title, key, des, fileName, filePath, imageToPath, neirong;
    private GridViewCaseImgAdapter viewImgAdapter;
    private ArrayList<PicInfo> picList = new ArrayList<>();
    ImageUtils imageUtils;
    private CaseTypeRes.DataBean caseTypeRes;
    private int pos, fengmianPos = -1, sign;
    private String piclists;
    private AddShipmentCaseApi shipmentCaseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_release_case, "发布案例");
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        super.initData();
        viewImgAdapter = new GridViewCaseImgAdapter(this);
        gvSelectPic.setAdapter(viewImgAdapter);
        gvSelectPic.setOnItemClickListener(this);
        imageUtils = new ImageUtils(mContext);
        shipmentCaseApi = new AddShipmentCaseApi();
    }

    @OnClick({R.id.ll_upload_1, R.id.ll_upload_2, R.id.ll_upload_3, R.id.ll_upload_4, R.id.ll_upload_5})
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
            case R.id.ll_upload_5:
                ActivitySimpleEditLines.startSimpleEdit(ReleaseCaseActivity.this, "内容", "输入内容", "", ActivitySimpleEdit.INPUT_NAME, 200, 205);
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
                tvContent4.setText(des);
            }
            if (requestCode == 204) {
                caseTypeRes = (CaseTypeRes.DataBean) data.getSerializableExtra("case_type");
                tvContent1.setText(caseTypeRes.Title);
            }
            if (requestCode == 205) {
                neirong = data.getStringExtra("result");
                tvContent5.setText(des);
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

            if (requestCode == 222) {
                key = data.getStringExtra("result");
                pos = data.getIntExtra("pos", -1);
                if (pos != -1) {
                    if (TextUtils.isEmpty(key)) {
                        picList.get(pos).picDes = "";
                    } else {
                        picList.get(pos).picDes = key;
                    }
                }
                viewImgAdapter.notifyDataSetChanged();
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


    class GridViewCaseImgAdapter extends BaseAdapter {

        private Context context;
        private int lastPos = -1;

        public GridViewCaseImgAdapter(Context context) {
            // TODO Auto-generated constructor stub
            this.context = context;
        }

        @Override
        public int getCount() {
            return picList.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_add_image_case, null);
            ImageView iv_release_pic = (ImageView) convertView.findViewById(R.id.iv_release_pic);
            TextView iv_delete_pic = (TextView) convertView.findViewById(R.id.iv_delete_pic);
            TextView tv_fengmian = (TextView) convertView.findViewById(R.id.tv_fengmian);
            TextView tv_case_des = (TextView) convertView.findViewById(R.id.tv_case_des);

            tv_fengmian.setVisibility(View.GONE);
            iv_delete_pic.setVisibility(View.GONE);
            if (position >= picList.size()) {
                Glide.with(context)
                        .load(R.mipmap.icon_add)
                        .into(iv_release_pic);
                tv_case_des.setVisibility(View.GONE);
            } else {
                Glide.with(context)
                        .load(picList.get(position).pic)
                        .into(iv_release_pic);
                tv_case_des.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(picList.get(position).picDes)) {
                    tv_case_des.setText(picList.get(position).picDes);
                } else {
                    tv_case_des.setText("");
                }
                if (picList.get(position).isFengmian) {
                    tv_fengmian.setVisibility(View.VISIBLE);
                    iv_delete_pic.setVisibility(View.GONE);
                } else {
                    tv_fengmian.setVisibility(View.GONE);
                    iv_delete_pic.setVisibility(View.VISIBLE);
                }
            }

            iv_delete_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lastPos != -1) {
                        picList.get(lastPos).isFengmian = false;
                    }
                    picList.get(position).isFengmian = true;
                    fengmianPos = position;
                    lastPos = position;
                    notifyDataSetChanged();
                }
            });
            tv_case_des.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivitySimpleEdit.startSimpleEdit(ReleaseCaseActivity.this, "案例图片说明", "输入案例图片说明", "", ActivitySimpleEdit.INPUT_NAME, 20, 222, position);
                }
            });
            return convertView;
        }
    }

    public void rightClickSave(View view) {
        if (checkEditAll()) {
            commitPic(picList.get(0).pic, 0);
        }
    }

    private boolean checkEditAll() {
        if (caseTypeRes == null) {
            ToastUtils.showToast(mContext, "请选择案例分类");
            return false;
        }
        if (TextUtils.isEmpty(title)) {
            ToastUtils.showToast(mContext, "请输入案例标题");
            return false;
        }
        if (TextUtils.isEmpty(key)) {
            ToastUtils.showToast(mContext, "请输入关键字");
            return false;
        }
        if (TextUtils.isEmpty(des)) {
            ToastUtils.showToast(mContext, "请输入页面描述");
            return false;
        }
        if (picList.size() < 1) {
            ToastUtils.showToast(mContext, "请选择图片");
            return false;
        }
        if (fengmianPos == -1) {
            ToastUtils.showToast(mContext, "请设置图片封面");
            return false;
        }
        return true;
    }

    private Request<String> request;

    String data = Utils.getCurrentDay();

    private void commitPic(String path, final int sign) {
        this.sign = sign;
        request = NoHttp.createStringRequest(Constants.UpImgBase64Post, RequestMethod.POST);
        request.add("secret_key", Utils.md5(data));
        request.add("key_type", "1");
        request.add("txtFileName", Utils.bitmapToBase64(BitmapHelper.getImage(path, 100)));

//        request.add("random", SPUtil.getString("random", ""));
//        request.add("desUserId", SPUtil.getString("desUserId", ""));
//        request.add("sign", sign);
//        request.add("txtFileName", Utils.bitmapToBase64(BitmapHelper.getImage(path, 100)));


        CallServer.getRequestInstance().add(ReleaseCaseActivity.this, 1, request, new CustomHttpListener(this, true, UpLoadbase64PostRes.class) {
            @Override
            public void doWork(int what, Object data, boolean isSuccess) {
                if (isSuccess) {
                    UpLoadbase64PostRes res = (UpLoadbase64PostRes) data;
                    picList.get(sign).serverFileName = res.data.get(0).ImgUrl;
                    if (sign < picList.size() - 1) {
                        int num = sign + 1;
                        commitPic(picList.get(num).pic, num);
                    } else {
                        submitData();
                    }
                }
            }
        }, true, true);
    }

    private void submitData() {

        String centerDes = "";
        for (int i = 0; i < picList.size(); i++) {
            PicInfo picInfo = picList.get(i);
            if (i == picList.size() - 1) {
                centerDes = centerDes + "<img href=\"" + picInfo.serverFileName + "\"/>|" + picInfo.picDes + "</br>" + neirong;
            } else {
                centerDes = centerDes + "<img href=\"" + picInfo.serverFileName + "\"/>|" + picInfo.picDes + "$";
            }
        }
        shipmentCaseApi.id = caseTypeRes.Id;
        shipmentCaseApi.title = title;
        shipmentCaseApi.image = picList.get(fengmianPos).serverFileName;
        shipmentCaseApi.content = centerDes;
        shipmentCaseApi.Seo_key = key;
        shipmentCaseApi.Seo_Description = des;
        if (TextUtils.isEmpty(neirong)) {
            neirong = "";
        }
        shipmentCaseApi.Summary = neirong;
        httpManager.doHttpDeal(shipmentCaseApi);
    }

    @Override
    protected void processSuccessResult(String resulte, String mothead) {
        super.processSuccessResult(resulte, mothead);
        if (mothead.equals(shipmentCaseApi.getMethod())) {
            AddProduceRes res = JSONObject.parseObject(resulte, AddProduceRes.class);
            if (res.ret == 200) {
                ToastUtils.showToast(mContext, res.msg);
                finish();
            }
        }
    }
}

package com.yxy.practicaltool.activity.upload_resources;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Request;
import com.yxy.practicaltool.MyApplication;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.adapter.LocalCacheAdapter;
import com.yxy.practicaltool.bean.PicInfo;
import com.yxy.practicaltool.common.Constants;
import com.yxy.practicaltool.common.L;
import com.yxy.practicaltool.common.ToastUtils;
import com.yxy.practicaltool.dao.TestDao;
import com.yxy.practicaltool.dao.UploadResourcesDaoDao;
import com.yxy.practicaltool.entity.api.AddProductApi;
import com.yxy.practicaltool.entity.resulte.AddProduceRes;
import com.yxy.practicaltool.entity.resulte.CommontRes;
import com.yxy.practicaltool.entity.resulte.Uploadbase64Res;
import com.yxy.practicaltool.gen.Test;
import com.yxy.practicaltool.gen.UploadResourcesDao;
import com.yxy.practicaltool.myview.CustomRecyclerView;
import com.yxy.practicaltool.nohttp.CallServer;
import com.yxy.practicaltool.nohttp.CustomHttpListener;
import com.yxy.practicaltool.nohttp.WaitDialog;
import com.yxy.practicaltool.utils.BitmapHelper;
import com.yxy.practicaltool.utils.SPUtil;
import com.yxy.practicaltool.utils.Utils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocalCacheActivity extends BaseActivity {

    @Bind(R.id.rv_local_cache)
    CustomRecyclerView rvLocalCache;
    private LocalCacheAdapter cacheAdapter;
    private ArrayList<UploadResourcesDao> list = new ArrayList<>();
    private UploadResourcesDaoDao dao;
    private Request<String> request;
    private ArrayList<PicInfo> picInfos = new ArrayList<>();
    private int posI = 0;
    AddProductApi addProductApi;

    private WaitDialog mWaitDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTopBar(R.layout.activity_local_cache, "本地缓存", "全部上传");
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        super.initView();
        rvLocalCache.setLayoutManager(new LinearLayoutManager(this));
        rvLocalCache.setItemAnimator(new DefaultItemAnimator());
        mWaitDialog = new WaitDialog(mContext);
        mWaitDialog.setCancelable(true);
        mWaitDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                request.cancel();
            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        addProductApi = new AddProductApi();
        dao = MyApplication.getInstances().getDaoSession().getUploadResourcesDaoDao();
        list.addAll(dao.loadAll());
        L.e("=====list===" + list.size());
        cacheAdapter = new LocalCacheAdapter(this, R.layout.item_local_cache, list);
        rvLocalCache.setAdapter(cacheAdapter);
    }


    private void getData() {
        list.clear();
        list.addAll(dao.loadAll());
        cacheAdapter.notifyDataSetChanged();
    }

    class LocalCacheAdapter extends CommonAdapter<UploadResourcesDao> {

        private Intent intent = new Intent();
        private Context context;

        public LocalCacheAdapter(Context context, int layoutId, List<UploadResourcesDao> datas) {
            super(context, layoutId, datas);
            this.context = context;
        }

        @Override
        public void convert(ViewHolder holder, final UploadResourcesDao infoBean) {
            ImageView iv_cache_pic = holder.getView(R.id.iv_cache_pic);
            TextView tv_delete = holder.getView(R.id.tv_delete);

            holder.setText(R.id.tv_cache_name, "品种：" + infoBean.getPinzhongName());
            holder.setText(R.id.tv_cache_danwei, "单位：" + infoBean.getUnitsName());
            holder.setText(R.id.tv_cache_tel, "电话：" + infoBean.getPhone());
            holder.setText(R.id.tv_cache_shuxing, "属性：" + infoBean.getAttributeName());
            holder.setText(R.id.tv_cache_time, "时间：" + infoBean.getCurrentTime());

            if (!TextUtils.isEmpty(infoBean.getPicInfos())) {
                int i = infoBean.getPicInfos().indexOf("|");
                String strPic = infoBean.getPicInfos().substring(0, i);
                Glide.with(context)
                        .load(strPic)
                        .into(iv_cache_pic);
            }

            tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dao.deleteByKey(infoBean.getCurrentTime());
                    getData();
                    ToastUtils.showToast(mContext, "刪除成功！");
                }
            });
        }
    }

    public void rightClickSave(View view) {
        if (Utils.isWifiConnected(mContext)) {
            mWaitDialog.show();
            picInfos.clear();
            String[] split = list.get(posI).getPicInfos().split("@");
            for (int j = 0; j < split.length; j++) {
                int index = split[j].indexOf("|");
                PicInfo picInfo = new PicInfo();
                picInfo.pic = split[j].substring(0, index);
                int index1 = split[j].indexOf(";");
                picInfo.lngValue = split[j].substring(index + 1, index1);
                picInfo.latValue = split[j].substring(index1 + 1);
                picInfos.add(picInfo);
            }
            commitPic(picInfos.get(0).pic, 0);
//            提交数据
        } else {
            ToastUtils.showToast(mContext, "请在WIFI网络下上传！");
        }
    }

    private void commitPic(String path, int sign) {
        if (request != null) {
            request.removeAll();
        }
        request = NoHttp.createStringRequest(Constants.UpImgBase64, RequestMethod.POST);
        request.add("random", SPUtil.getString("random", ""));
        request.add("desUserId", SPUtil.getString("desUserId", ""));
        request.add("sign", sign);
        request.add("txtFileName", Utils.bitmapToBase64(BitmapHelper.getImage(path, 100)));

        CallServer.getRequestInstance().add(LocalCacheActivity.this, 1, request, new CustomHttpListener(this, true, Uploadbase64Res.class) {
            @Override
            public void doWork(int what, Object data, boolean isSuccess) {
                if (isSuccess) {
                    Uploadbase64Res res = (Uploadbase64Res) data;
                    int sign = Integer.parseInt(res.data.sign);
                    picInfos.get(sign).serverFileName = res.data.serverFileName;
                    picInfos.get(sign).serverThumbnailFileName = res.data.serverThumbnailFileName;
//                    L.e("==res.data.sign==" + res.data.sign + "=picInfos.size()=" + picInfos.size());
                    if (sign < picInfos.size() - 1) {
                        int num = sign + 1;
                        commitPic(picInfos.get(num).pic, num);
                    } else {
                        submitData();
                    }
                } else {
                }
            }
        }, true, false);
    }

    private void submitData() {
        String piclists = "";
        for (int i = 0; i < picInfos.size(); i++) {
            PicInfo picInfo = picInfos.get(i);
            if (i == 0) {
                piclists = "0|" + picInfo.serverFileName + "|" + picInfo.serverThumbnailFileName + "|" + picInfo.lngValue + ";" + picInfo.latValue;
            } else {
                piclists = piclists + "&0|" + picInfo.serverFileName + "|" + picInfo.serverThumbnailFileName + "|" + picInfo.lngValue + ";" + picInfo.latValue;
            }
        }
        addProductApi.CName = list.get(posI).getName2();
        addProductApi.Vid = list.get(posI).getPinzhongId() + "";
        addProductApi.Cid = list.get(posI).getUnitsId() + "";
        addProductApi.H_State = list.get(posI).getH_State() + "";
        addProductApi.Num = list.get(posI).getNum4();
        addProductApi.Describe = list.get(posI).getDes5();
        addProductApi.Remarks = list.get(posI).getTip6();
        addProductApi.PhotoDetail = piclists;
        addProductApi.ProductAttr = list.get(posI).getAttributeId();
        httpManager.doHttpDeal(addProductApi);

    }

    @Override
    protected void processSuccessResult(String resulte, String mothead) {
        super.processSuccessResult(resulte, mothead);
        if (mothead.equals(addProductApi.getMethod())) {
            try {
                CommontRes commontRes = JSONObject.parseObject(resulte, CommontRes.class);
                if (commontRes.ret == 200) {
                    dao.deleteByKey(list.get(posI).getCurrentTime());
                }
                L.e("==addProductApi=posI=" + posI + "==list.size()=" + list.size());
                if (posI < list.size() - 1) {
                    posI += 1;
                    picInfos.clear();
                    String[] split = list.get(posI).getPicInfos().split(",");
                    for (int j = 0; j < split.length; j++) {
                        int index = split[j].indexOf("|");
                        PicInfo picInfo = new PicInfo();
                        picInfo.pic = split[j].substring(0, index);
                        int index1 = split[j].indexOf(";");
                        picInfo.latValue = split[j].substring(index + 1, index1);
                        picInfo.lngValue = split[j].substring(index1 + 1);
                        picInfos.add(picInfo);
                    }
                    commitPic(picInfos.get(posI).pic, 0);
                } else {
                    mWaitDialog.dismiss();
                    if (commontRes.ret == 200) {
                        list.clear();
                        ToastUtils.showToast(mContext, "上传完成！");
                        cacheAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showToast(mContext, "上传出错！");
                    }
                }
            } catch (Exception e) {
                mWaitDialog.dismiss();
                ToastUtils.showToast(mContext, "上传出错！");
            }
        }
    }

    @Override
    protected void processFalResult(int httpCode, String result, String mothead) {
        super.processFalResult(httpCode, result, mothead);
        mWaitDialog.dismiss();
        ToastUtils.showToast(mContext, "上传出错！");
    }
}
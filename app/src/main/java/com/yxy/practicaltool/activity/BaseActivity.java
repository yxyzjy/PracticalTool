package com.yxy.practicaltool.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.common.Constants;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.common.HttpCode;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.common.L;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;
import com.yxy.practicaltool.utils.JsonUtils;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.common.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by yxy on 2017/7/4 0004.
 */

public class BaseActivity extends RxAppCompatActivity implements HttpOnNextListener{

    TextView tv_title;
    LinearLayout ll_title_bar_left,ll_empty_content;
    RelativeLayout title_bar;
    public Context mContext;
    public HttpManager httpManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;
        httpManager = new HttpManager(this,this);
    }

    public void initData() {
    }

    public void initView() {
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void setTopBar(int layoutResID, String title) {
        View v = LayoutInflater.from(this).inflate(
                R.layout.activity_base, null);
        initMyView(v);
        if (TextUtils.isEmpty(title)){
            title_bar.setVisibility(View.GONE);
        }
        RelativeLayout.LayoutParams p1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        p1.addRule(RelativeLayout.BELOW,v.getId());
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        ll_empty_content.addView(contentView, p1);
        setContentView(v);
        tv_title.setText(title);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    public void initMyView(View v) {
        title_bar = (RelativeLayout) v.findViewById(R.id.title_bar);
        tv_title = (TextView) v.findViewById(R.id.tv_title_bar_center);
        ll_title_bar_left = (LinearLayout) v.findViewById(R.id.ll_title_bar_left);
        ll_empty_content  = (LinearLayout) v.findViewById(R.id.ll_empty_content);
        ll_title_bar_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void baseBack(View view){
        onBackPressed();
    }



    @Override
    public void onNext(String resulte, String mothead) {
        processSuccessResult(resulte, mothead);
    }


    @Override
    public void onError(Throwable e, String mothead) {
        if (e instanceof HttpException) {
            Response response = ((HttpException) e).response();
            int code = response.code();
            String bodyStr = null;
            try {
                bodyStr = response.errorBody().string() + "";
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (code == HttpCode.HTTP_200) {
            } else {
                processFalResult(code, bodyStr, mothead);
                return;
            }
        } else {
            //暂时做静默处理
//            EventBus.getDefault().post(new MsgEvent(e.toString()));
            L.e("解析过程onError", e.toString() + "");
            /*if (e.getMessage().equals(Constants.DPH_NO_NETWORK)) {
                processFalResult(-1, e.getMessage() + "", mothead);
            }*/

        }
    }


    @Override
    public void onCompleted(String mothead) {

    }

    // 请求成功后处理
    protected void processSuccessResult(String resulte, String mothead) {
        if (Constants.IS_LOG_BODY) {
            L.e("processSuccessResult", mothead + "");
            L.e("processSuccessResult", resulte + "");
        }
    }

    // 请求失败后处理
    protected void processFalResult(int httpCode, String result, String mothead) {
        L.e("processFalResult", mothead + "");
        L.e("processFalResult", httpCode + "");
        L.e("processFalResult", result + "");
        switch (httpCode) {
            /*case HttpCode.HTTP_403:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_403));
                break;
            case HttpCode.HTTP_404:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_404));
                break;
            case HttpCode.HTTP_405:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_405));
                break;
            case HttpCode.HTTP_408:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_408));
                break;
            case HttpCode.HTTP_414:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_414));
                break;
            case HttpCode.HTTP_500:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_500));
                break;
            case HttpCode.HTTP_502:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_502));
                break;
            case HttpCode.HTTP_503:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_503));
                break;
            case HttpCode.HTTP_504:
                EventBus.getDefault().post(new MsgEvent(result));
                break;
            case HttpCode.HTTP_400:
                if (!StringUtil.isEmpty(result)) {
                    parseFailToast(result, true, mothead);
                }
                break;
            default:
                EventBus.getDefault().post(new MsgEvent(HttpCode.MSG_HTTP_ERROR));
                break;*/
        }
    }

    private  String deliver(String resulte){

        if (!resulte.startsWith("{")){
            return resulte;
        }
        JSONObject jsonObject = JsonUtils.parseFromJson(resulte);
        if (jsonObject !=null){
            Iterator<String> it = jsonObject.keys();
            List<String> keyListstr = new ArrayList<String>();
            while (it.hasNext()) {
                String key = it.next().toString() + "";
                keyListstr.add(key);
            }
            boolean containsRet = keyListstr.contains("ret");
            boolean containsMsg = keyListstr.contains("msg");
            if (containsRet && JsonUtils.getJsonInt(jsonObject, "ret") != 200){
                if (containsMsg && !TextUtils.isEmpty(JsonUtils.getJsonString(jsonObject, "msg"))){
                    ToastUtils.showToast(mContext,JsonUtils.getJsonString(jsonObject, "msg"));
                }
            }
            boolean containsData = keyListstr.contains("data");
            if (containsData){
                return JsonUtils.getJsonString(jsonObject, "data");
            }else{
                return resulte;
            }
        }else{
            return resulte;
        }
    }
}

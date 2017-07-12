package com.yxy.practicaltool.activity.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * com.itxiaodao.huoduoduo.activity.shop_manage
 *
 * @author 朱啸原 Create at 15/6/2 14:10
 */
public class ActivitySimpleEdit extends BaseActivity implements View.OnClickListener {
    private EditText et_phone;
    private Button btn_save;
    private TextView titleTv, tv_enture;
    private String hintStr, oldText, titleStr, orderNum;
    private int inputType = 0, maxLength;
    public static final int INPUT_NAME = 0;
    public static final int INPUT_NUM = 1;
    public static final int INPUT_PRICE = 2;
    public static final int INPUT_DETAIL = 3;
    private String result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_simple_edit);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            titleStr = bundle.getString("title");
            hintStr = bundle.getString("hint");
            oldText = bundle.getString("old_text");
            inputType = bundle.getInt("input_type");
            maxLength = bundle.getInt("maxLength");
            orderNum = bundle.getString("orderNum");
        }
        initView();
    }

    public RequestListener requestListener() {
        return new RequestListener() {
            Gson gson = MyApp.getInstance().getSimpleGson();

            @Override
            public void requestSuccess(String json, Object tag) {
                if (tag.equals(Constants.REQ_VERIFY_SHOP_NUM)) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONObject data = (JSONObject) jsonObject.get("data");
                        long useTime = (long) data.get("useTime");
                        Bundle bundle = new Bundle();
                        bundle.putLong("result", useTime);
                        Intent intent = new Intent();
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (tag.equals(Constants.REQ_EDIT)) {//申请退款成功后，切换状态
                    Bundle bundle = new Bundle();
                    bundle.putString("result", result);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                if (tag.equals(Constants.REQ_REFEASH_AT)) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        JSONObject data = (JSONObject) jsonObject.get("data");
                        String accessToken = (String) data.get("accessToken");
                        String openId = (String) data.get("openId");
                        MyApp.getInstance().getSpUtil().setAccessToken(accessToken);
                        MyApp.getInstance().getSpUtil().setOpenId(openId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void requestError(String json, String state, Object tag) {
                if (!TextUtils.isEmpty(state)) {
                    if (state.equals("40303")) {
                        httpManager.refreshAccessToken("user", MyApp.getInstance().getSpUtil().getOpenId());
                    }
                }
            }
        };
    }

    public void initView() {
        this.findViewById(R.id.btn_save).setOnClickListener(this);
        titleTv = (TextView) this.findViewById(R.id.tv_title);
        titleTv.setText(titleStr);
        tv_enture = (TextView) this.findViewById(R.id.tv_enture);
        if (titleStr.equals(getString(R.string.srshbh))) {
            tv_enture.setText(getString(R.string.ensure));
        }
        et_phone = (EditText) this.findViewById(R.id.et_phone);
        et_phone.setHint(hintStr);
        et_phone.setSingleLine(true);
        et_phone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        if (inputType == INPUT_NUM || inputType == INPUT_PRICE) {
            et_phone.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (!TextUtils.isEmpty(oldText)) {
            et_phone.setText(oldText);
            et_phone.setSelection(oldText.length());
        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                result = et_phone.getText().toString();
                if (TextUtils.isEmpty(result)) {
                    Utils.showToast(this, getResources().getString(R.string.bnwk));
                    return;
                } else {
                    if (Tools.isfilterEmoji(result)) {
                        Utils.showToast(this, getResources().getString(R.string.bhtszf));
                        return;
                    }
                    if (titleStr.equals(getString(R.string.srshbh))) {
                        String code = MD5.getMessageDigest(result.toString().getBytes());
                        httpManager.verifyShopNum(orderNum, code);
                    } else {
                        httpManager.edit("text", "post", null, result, "userEditorHandler", "nickname", "", null);
                    }
                }
                break;
        }
    }
}
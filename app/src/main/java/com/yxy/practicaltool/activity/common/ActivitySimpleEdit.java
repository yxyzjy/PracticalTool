package com.yxy.practicaltool.activity.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yxy.practicaltool.R;
import com.yxy.practicaltool.activity.BaseActivity;
import com.yxy.practicaltool.common.ToastUtils;

import butterknife.ButterKnife;


/**
 * @author yxy Create at 17/7/14 14:10
 */
public class ActivitySimpleEdit extends BaseActivity {
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
    private int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            titleStr = bundle.getString("title");
            hintStr = bundle.getString("hint");
            oldText = bundle.getString("old_text");
            inputType = bundle.getInt("input_type");
            maxLength = bundle.getInt("maxLength");
            orderNum = bundle.getString("orderNum");
            pos = bundle.getInt("pos");
        }
        setTopBar(R.layout.activity_simple_edit, titleStr);
        ButterKnife.bind(this);
    }

    public static void startSimpleEdit(Context context, String titleStr, String hintStr, String oldText, int inputType, int maxLength) {
        Intent intent = new Intent(context, ActivitySimpleEdit.class);
        intent.putExtra("title", titleStr);
        intent.putExtra("hint", hintStr);
        intent.putExtra("old_text", oldText);
        intent.putExtra("input_type", inputType);
        intent.putExtra("maxLength", maxLength);
        context.startActivity(intent);
    }

    public static void startSimpleEdit(Activity context, String titleStr, String hintStr, String oldText, int inputType, int maxLength, int requestCode) {
        Intent intent = new Intent(context, ActivitySimpleEdit.class);
        intent.putExtra("title", titleStr);
        intent.putExtra("hint", hintStr);
        intent.putExtra("old_text", oldText);
        intent.putExtra("input_type", inputType);
        intent.putExtra("maxLength", maxLength);
        context.startActivityForResult(intent, requestCode);
    }

    public static void startSimpleEdit(Activity context, String titleStr, String hintStr, String oldText, int inputType, int maxLength, int requestCode, int pos) {
        Intent intent = new Intent(context, ActivitySimpleEdit.class);
        intent.putExtra("title", titleStr);
        intent.putExtra("hint", hintStr);
        intent.putExtra("old_text", oldText);
        intent.putExtra("input_type", inputType);
        intent.putExtra("maxLength", maxLength);
        intent.putExtra("pos", pos);
        context.startActivityForResult(intent, requestCode);
    }

    @Override
    public void initView() {
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


    /*@Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_title_right:
                result = et_phone.getText().toString();
                if (TextUtils.isEmpty(result)) {
                    ToastUtils.showToast(this, getResources().getString(R.string.bnwk));
                    return;
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("result", result);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }*/

    public void rightClickSave(View view) {
        result = et_phone.getText().toString();
        if (TextUtils.isEmpty(result)) {
            ToastUtils.showToast(this, getResources().getString(R.string.bnwk));
            return;
        } else {
            Intent intent = new Intent();
            intent.putExtra("result", result);
            intent.putExtra("pos", pos);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
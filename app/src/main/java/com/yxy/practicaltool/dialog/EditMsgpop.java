package com.yxy.practicaltool.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yxy.practicaltool.R;


/**
 * 回复输入编辑框
 */
public class EditMsgpop extends PopupWindow {
    private View view;
    private TextView etInput;
    private TextView tv_ensure, tv_cancel, tv_title_msg_dialog;
    private RelativeLayout ll_waimian;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public EditMsgpop(final Context context, OnClickListener itemsOnClick, String title) {
        super(context);
        view = LayoutInflater.from(context).inflate(
                R.layout.dialog_twobtn, null);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancle_msg_dailog);
        tv_ensure = (TextView) view.findViewById(R.id.tv_ensure_msg_dailog);
        tv_title_msg_dialog = (TextView) view.findViewById(R.id.tv_title_msg_dialog);
        etInput = (TextView) view.findViewById(R.id.et_code_msg_dialog);
        ll_waimian = (RelativeLayout) view.findViewById(R.id.ll_waimian);
        tv_title_msg_dialog.setText(title);
        tv_cancel.setOnClickListener(itemsOnClick);
        tv_ensure.setOnClickListener(itemsOnClick);
        ll_waimian.setOnClickListener(itemsOnClick);


        ColorDrawable dw = new ColorDrawable(0x60000000);
        this.setBackgroundDrawable(dw);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setContentView(view);
        this.setFocusable(true);
        // 设置popwindow如果点击外面区域，便关闭。
        this.setOutsideTouchable(false);
        this.update();
    }

    public void setText(String des) {
        etInput.setText(des);
    }

}

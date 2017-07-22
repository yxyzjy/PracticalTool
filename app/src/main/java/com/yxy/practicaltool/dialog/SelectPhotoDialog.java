package com.yxy.practicaltool.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.base.BaseDialog;
import com.yxy.practicaltool.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/7/21 0021.
 */
public class SelectPhotoDialog extends BaseDialog<SelectPhotoDialog> implements View.OnClickListener {

    @Bind(R.id.btn_dialog_1)
    TextView btnDialog1;
    @Bind(R.id.btn_dialog_2)
    TextView btnDialog2;

    private onBtnClickListener btnClickListener;
    private Context context;
    private String des;

    public SelectPhotoDialog(Context context, onBtnClickListener btnClickListener) {
        super(context);
        this.btnClickListener = btnClickListener;
        this.context = context;
    }


    @Override
    public View onCreateView() {
        widthScale(0.8f);
        showAnim(new BounceTopEnter());
        View inflate = View.inflate(mContext, R.layout.layout_dialog_select_photo, null);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        btnDialog1.setOnClickListener(this);
        btnDialog2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_1:
                btnClickListener.onSure();
                dismiss();
                break;
            case R.id.btn_dialog_2:
                btnClickListener.onExit();
                dismiss();
                break;

        }
    }


    public interface onBtnClickListener {
        public void onSure();

        public void onExit();
    }
}

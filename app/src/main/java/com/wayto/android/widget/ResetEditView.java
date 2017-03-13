package com.wayto.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wayto.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.widget
 * @Description:带重置内置的EditView
 * @date 2016/11/25 10:33
 */

public class ResetEditView extends LinearLayout implements TextWatcher, View.OnClickListener {

    @BindView(R.id.reset_editView_icon_iv)
    ImageView resetEditViewIconIv;
    @BindView(R.id.reset_editView)
    EditText resetEditView;
    @BindView(R.id.reset_editView_close_iv)
    ImageView resetEditViewCloseIv;

    public ResetEditView(Context context) {
        super(context, null);
    }

    public ResetEditView(Context context, AttributeSet attri) {
        super(context, attri, 0);
        init(context);
        parseAttrs(context, attri, 0);
    }


    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.reset_editeview_layout, null);
        ButterKnife.bind(this, view);

        resetEditView.addTextChangedListener(this);
        resetEditViewCloseIv.setOnClickListener(this);

        addView(view);
    }

    /**
     * 初始化样式
     *
     * @param context
     * @param attrs
     * @param defStyle
     */
    private void parseAttrs(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ResetEditView, defStyle, 0);
        Drawable drawable = typedArray.getDrawable(R.styleable.ResetEditView_edite_icon);
        int type = typedArray.getInt(R.styleable.ResetEditView_edit_inputType, InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        String hint = typedArray.getString(R.styleable.ResetEditView_edit_hint);

        if (drawable != null) {
            resetEditViewIconIv.setImageDrawable(drawable);
        }

        if (type == 0x10) {
            resetEditView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            resetEditView.setInputType(type);
        }

        if (!TextUtils.isEmpty(hint)) {
            resetEditView.setHint(hint);
        }

        typedArray.recycle();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (TextUtils.isEmpty(s)) {
            resetEditViewCloseIv.setVisibility(GONE);
        } else {
            resetEditViewCloseIv.setVisibility(VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_editView_close_iv:
                resetEditView.getText().clear();
                resetEditViewCloseIv.setVisibility(GONE);
                break;
        }
    }

    /**
     * 设置内容
     *
     * @param text
     */
    public void setText(String text) {
        resetEditView.setText(text);
    }

    /**
     * 设置内容
     *
     * @param resId
     */
    public void setText(int resId) {
        resetEditView.setText(resId);
    }

    /**
     * 返回内容
     *
     * @return
     */
    public String getText() {
        return resetEditView.getText().toString();
    }
}

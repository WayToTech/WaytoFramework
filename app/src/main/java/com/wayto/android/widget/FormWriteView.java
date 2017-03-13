package com.wayto.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wayto.android.R;

/**
 * @Package: com.yunwei.zaina.widget
 * @Description:填写表单
 * @author: Aaron
 * @date: 2016-06-14
 * @Time: 17:15
 * @version: V1.0
 */
public class FormWriteView extends LinearLayout implements TextWatcher {

    private TextView propertyText;
    private TextView forceFlag;
    private EditText propertyContentText;
    private TextView propertyUnit;

    private String properName;
    private String hint;
    private String mHint = "请输入";
    private boolean force;
    private String unit;

    //Setting
    private final int text = 0;//文字输入
    private final int number = 1;//数组输入
    private final int phone = 2;//号码输入
    private final int code = 3;//编码输入,不能有中文输入，只有数字和字母

    private int inputType = -1;//设置输入类型

    private String digits = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";//设备输入的允许字符为字母和数字

    public FormWriteView(Context context) {
        this(context, null);
    }

    public FormWriteView(Context context, AttributeSet attri) {
        super(context, attri);
        initAttributeset(context, attri, 0);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.form_write_layout, null);
        propertyText = (TextView) view.findViewById(R.id.form_write_title);
        forceFlag = (TextView) view.findViewById(R.id.form_write_force);
        propertyContentText = (EditText) view.findViewById(R.id.form_write_content);
        propertyUnit = (TextView) view.findViewById(R.id.form_write_unit);
        /* 添加冒号分隔符*/
        properName += getContext().getString(R.string.colon_breaker);
        propertyText.setText(properName);
        propertyContentText.setHint(mHint);
        propertyUnit.setText(unit);

        //根据输入类型设置输入的属性设置
        switch (inputType) {
            case text:
                propertyContentText.setInputType(InputType.TYPE_CLASS_TEXT);
                break;

            case number:
                //限制为允许带小数点的数字,只能是8194
//                propertyContentText.setInputType(InputType.TYPE_CLASS_NUMBER);
                propertyContentText.setInputType(8194);
                break;

            case phone:
                propertyContentText.setInputType(InputType.TYPE_CLASS_PHONE);
                break;

            case code:
                propertyContentText.addTextChangedListener(this);
                break;
        }

        if (force) {
            forceFlag.setVisibility(View.VISIBLE);
            propertyText.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            forceFlag.setVisibility(View.GONE);
            propertyText.setTextColor(getResources().getColor(R.color.black));
        }

        addView(view);
    }

//    @Override
//    public void setOnClickListener(OnClickListener l) {
//        super.setOnClickListener(l);
//        propertyContentText.setOnClickListener(l);
//        propertyText.setOnClickListener(l);
//    }

    public void setListener(OnClickItem listener) {
        this.clickItem = listener;
    }

    //设置错误提示
    public void setErrorInfo(@NonNull String info) {
        propertyContentText.setError(info);
    }

    /**
     * 检查是否有错误提示信息 ,如果有错误信息,说明现在是提示状态，返回为true
     */
    public boolean hasErrorInfo() {
        if (!TextUtils.isEmpty(propertyContentText.getError())) {
            propertyContentText.setError(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置没有出错信息
     * by:huangyue
     */
    public void setNoErrorInfo() {
        propertyContentText.setError(null);
    }

    public void setPropertyText(String str) {
        propertyText.setText(str);
    }

    @Deprecated
    public FormWriteView setPropertyContentText(String str) {
        propertyContentText.setText(str);
        return this;
    }

    public FormWriteView setContentText(String str) {
        propertyContentText.setText(str);
        return this;
    }

    public void setPropertyContentTextColor(int color) {
        propertyContentText.setTextColor(color);
    }

    public void setPropertyText(int resId) {
        propertyText.setText(resId);
    }

    public void setPropertyContentText(int resId) {
        propertyContentText.setText(resId);
    }

    public String getContentText() {
        return propertyContentText.getText().toString();
    }

    public void setNonEditable() {
        propertyContentText.setEnabled(false);
        propertyContentText.setFocusable(false);
        propertyContentText.setFocusableInTouchMode(false);
        propertyContentText.setClickable(false);
        propertyText.setTextColor(Color.GRAY);
        propertyContentText.setTextColor(Color.GRAY);
        propertyContentText.setHint("");
    }

    @Deprecated
    public void setPropertyEditEnabled(boolean enabled) {
        propertyContentText.setEnabled(enabled);
        propertyContentText.setFocusable(false);
        propertyContentText.setFocusableInTouchMode(false);
        propertyContentText.setClickable(false);
        /* 设置字体颜色*/
        if (!enabled) {
            propertyText.setTextColor(Color.GRAY);
            propertyContentText.setTextColor(Color.GRAY);
        }
    }

//    public EditText getPropertyContentView(){
//        return propertyContentText;
//    }

    private void initAttributeset(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FormWriteView, defStyle, 0);
        properName = typedArray.getString(R.styleable.FormWriteView_wvItemName);
        hint = typedArray.getString(R.styleable.FormWriteView_wvHint);
        mHint = (TextUtils.isEmpty(hint) ? mHint : hint);
        force = typedArray.getBoolean(R.styleable.FormWriteView_wvForcible, force);
        inputType = typedArray.getInt(R.styleable.FormWriteView_wvInputType, inputType);
        unit = typedArray.getString(R.styleable.FormWriteView_wvUnit);
        typedArray.recycle();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String editText = editable.toString();//编辑内容
        String tempText = "";
        for (int o = 0; o < editText.length(); o++) {
            if (digits.indexOf(editText.charAt(o)) >= 0) {
                tempText += editText.charAt(o);
            } else {
                propertyContentText.setError("输入的字符只能是字母和数字");
            }
        }
    }

    private OnClickItem clickItem;

    public interface OnClickItem {
        void onClick(View view);
    }
}

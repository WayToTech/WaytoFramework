package com.wayto.android.function.batteryFuncation.data;

import java.io.Serializable;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.water.ui.activity.data
 * @Description:电量上传实体类
 * @date 2016/12/27 9:09
 */

public class BatteryEntity implements Serializable{

    private int Id;
    private int Value;
    private String Note;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getValue() {
        return Value;
    }

    public void setValue(int value) {
        Value = value;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}

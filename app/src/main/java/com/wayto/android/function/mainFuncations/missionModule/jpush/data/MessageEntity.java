package com.wayto.android.function.mainFuncations.missionModule.jpush.data;

import java.util.ArrayList;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.mainFuncations.missionModule.jpush.data
 * @Description:
 * @date 2017/2/10 16:48
 */

public class MessageEntity {

    int code;
    private ArrayList<Integer> data;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Integer> getData() {
        return data;
    }

    public void setData(ArrayList<Integer> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

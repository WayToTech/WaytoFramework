package com.wayto.android.entity;

/**
 * @author CBOK
 * @date 2016/11/7 21:45
 * @description:
 */

public class ResponseModel<T> {

    private boolean success;

    private String message;

    private T data;

    /*Git测试*/
    private String Test;

    public ResponseModel(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

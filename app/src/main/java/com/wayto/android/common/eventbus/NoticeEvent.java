package com.wayto.android.common.eventbus;

/**
 * EventBus 常量值定义
 * Created by wuhezhi on 2015/12/7.
 */
public class NoticeEvent {

    private int flag;
    private int num;
    private int num2;
    private int num3;
    private long num4;

    private Object obj;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public int getNum3() {
        return num3;
    }

    public void setNum3(int num3) {
        this.num3 = num3;
    }

    public long getNum4() {
        return num4;
    }

    public void setNum4(long num4) {
        this.num4 = num4;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}

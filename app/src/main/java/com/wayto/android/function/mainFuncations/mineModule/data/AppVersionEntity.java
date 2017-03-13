package com.wayto.android.function.mainFuncations.mineModule.data;

import java.io.Serializable;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.cmcc.ui.mainFunctions.data
 * @Description:
 * @date 2016/11/17 10:51
 */

public class AppVersionEntity implements Serializable{

    private int Ver;
    private String Note;
    private String Url;
    private int Level;

    public int getVer() {
        return Ver;
    }

    public void setVer(int ver) {
        Ver = ver;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        Level = level;
    }
}

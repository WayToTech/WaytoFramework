package com.wayto.android.function.pictureFuncation.data;

import java.io.Serializable;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.pictureFuncation.data
 * @Description:选择图片实体类
 * @date 2016/12/29 11:29
 */

public class PictureEntity implements Serializable {

    private String url;
    private String date;
    private boolean selecte;
    private String newUrl;/*压缩过后图片路径*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSelecte() {
        return selecte;
    }

    public void setSelecte(boolean selecte) {
        this.selecte = selecte;
    }

    public String getNewUrl() {
        return newUrl;
    }

    public void setNewUrl(String newUrl) {
        this.newUrl = newUrl;
    }
}

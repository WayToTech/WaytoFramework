package com.wayto.android.utils;

import com.wayto.android.base.DataApplication;
import com.wayto.android.entity.Image;
import com.wayto.android.entity.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.utils
 * @Description:工具类
 * @date 2016/11/30 11:03
 */

public class IUtil {

    /**
     * 获取字符串资源
     *
     * @param res
     * @return
     */
    public static String getStrToRes(int res) {
        return DataApplication.getInstance().getResources().getString(res);
    }

    /**
     * Url过滤
     *
     * @param url
     * @return
     */
    public static String fitterUrl(String url) {
        if (!url.contains("http://")) {
            url = "file://" + url;
        }
        return url;
    }

    /**
     * 转换 List<String> --> List<Image>
     * @param stringList
     * @Creater chenhaobo
     * @return
     */
    public static List<Image> convertStringListToImgList(List<String> stringList){
        List<Image> imgList = new ArrayList<>();
        if(stringList!=null&&stringList.size()>0){
            for (int i = 0; i < stringList.size(); i++) {
                Image img = new Image();
                img.setKey("");
                img.setUrl(stringList.get(i));
                imgList.add(img);
            }
        }
        return imgList;
    }

    /**
     * 转换 List<String> --> List<Video>
     * @param stringList
     * @Creater zls
     * @return
     */
    public static List<Video> convertStringListToVideoList(List<String> stringList){
        List<Video> videos = new ArrayList<>();
        if(stringList!=null&&stringList.size()>0){
            for (int i = 0; i < stringList.size(); i++) {
                Video video = new Video();
                video.setUrl(stringList.get(i));
                video.setName("");
                videos.add(video);
            }
        }
        return videos;
    }
}

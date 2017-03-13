package com.wayto.android.vendor.qiniu;

import android.text.TextUtils;

import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 配置信息
 * Created by yangdu on 16/6/8.
 */
public class QiNiuConfig {
    /*默认域名*/
    private static final String DEFAULT_IMG_DOMAIN = "http://img.wayto.com.cn/";
    /*文件域名*/
    private static String FILE_DOMAIN;
    /*文件名*/
    private static String FILE_NAME;
    /*配制*/
    private static Configuration config;

    /**
     * 初始化配制
     *
     * @param imgDomain
     * @param fileName
     */
    public static void iniConfig(String imgDomain, String fileName) {
        if (!TextUtils.isEmpty(imgDomain)) {
            FILE_DOMAIN = imgDomain;
        } else {
            FILE_DOMAIN = DEFAULT_IMG_DOMAIN;
        }

        if (!TextUtils.isEmpty(fileName)) {
            FILE_NAME = fileName + "_";
        } else {
            FILE_NAME = "wayto_default_";
        }
        config = new Configuration.Builder().zone(Zone.zone0).build();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static String getFileDomain() {
        return FILE_DOMAIN;
    }

    public static String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String data=format.format(new Date());
        return FILE_NAME+data+"_"+System.currentTimeMillis();
    }
}

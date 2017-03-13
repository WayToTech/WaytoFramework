package com.wayto.android.vendor.qiniu;

import android.text.TextUtils;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 七牛图片上封装类
 * Created by yangdu on 16/6/8.
 */
public class QiNiuImageUploadManager {
    private static final String TAG = "QiNiuImageUploadManager";

    private static int index = 0;
    private static int i = 0;

    /**
     * 图处上传
     *
     * @param token
     * @param path
     * @param listener
     */
    public static void uploadImage(String token, String path, UploadCallBackListener listener) {
        List<String> images = new ArrayList<>();
        images.add(path);
        uploadImage(token, images, listener);
    }

    /**
     * 多张图片上传处理
     *
     * @param token
     * @param images
     */
    public static void uploadImage(final String token, final List<String> images, final UploadCallBackListener listener) {
        if (images == null || images.size() == 0) {
            return;
        }
        if (listener != null) {
            listener.onUploadStart();
        }
        final List<String> mList = new ArrayList<>();
        final UploadManager uploadManager = new UploadManager(QiNiuConfig.getConfig());
        final UploadOptions options = new UploadOptions(null, null, true, new UpProgressHandler() {
            @Override
            public void progress(String s, double v) {
                if (listener != null) {
                    listener.onProgess(v);
                }
            }
        }, null);
        try {
            new Thread() {
                @Override
                public void run() {
                    for (i = 0; i < images.size(); i++) {
                        byte[] data = null;
                        try {
                            data = toByteArray(images.get(i));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        uploadManager.put(data, QiNiuConfig.getFileName() + "", token, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if (info.statusCode == 200) {
                                    try {
                                        String fileName = response.getString("key");
                                        if (!TextUtils.isEmpty(fileName)) {
                                            String path = QiNiuConfig.getFileDomain() + fileName;
                                            mList.add(path);
                                        }
                                        if (index == images.size() - 1 && listener != null) {
                                            index = 0;
                                            listener.onUploadComplete(mList);
                                        } else {
                                            index++;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        listener.onUploadEnd();
                                    }
                                } else {
                                    listener.onUploadFailure();
                                }
                            }
                        }, options);
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onUploadEnd();
            }
        }
    }


    /**
     * the traditional io way
     *
     * @param filename
     * @return
     * @throws IOException
     */
    private static byte[] toByteArray(String filename) throws IOException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));
            int buf_size = 1024;
            byte[] buffer = new byte[buf_size];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, buf_size))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bos.close();
        }
    }
}

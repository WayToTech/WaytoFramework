package com.wayto.android.function.uploadFileFuncation;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.function.uploadFileFuncation
 * @Description:文件上传管理类
 * @date 2017/2/8 15:54
 */

public class UploadFileManager {

    private static int index = 0;

    /**
     * 文件上传
     *
     * @param file
     * @param callBack
     */
    public static void uploadFile(File file, final UploadFileCallBack callBack) {
        new UploadFilePresenter(new UploadFileContract.View() {
            @Override
            public void onUploadFileStart() {
                callBack.onUploadFileStart();
            }

            @Override
            public void onUploadFileEnd() {
                callBack.onUploadFileEnd();
            }

            @Override
            public void onUploadSuccess(String url) {
                List<String> list = new ArrayList<>();
                list.add(url);
                callBack.onUploadFileSuccess(list);
            }

            @Override
            public void onUploadFialure(int code, String msg) {
                callBack.onUploadFileFailure();
            }
        }).uploadFileAction(file);
    }

    /**
     * 文件上传
     *
     * @param files
     * @param callBack
     */
    public static void uploadFile(final List<File> files, final UploadFileCallBack callBack) {
        if (files == null || files.size() == 0) {
            callBack.onUploadFileFailure();
        }

       final List<String> lists = new ArrayList<>();
        callBack.onUploadFileStart();
        for (int i = 0; i < files.size(); i++) {
            new UploadFilePresenter(new UploadFileContract.View() {
                @Override
                public void onUploadFileStart() {

                }

                @Override
                public void onUploadFileEnd() {

                }

                @Override
                public void onUploadSuccess(String url) {
                    lists.add(url);
                    if (index == lists.size() - 1) {
                        callBack.onUploadFileSuccess(lists);
                        callBack.onUploadFileEnd();
                        index = 0;
                    } else {
                        index++;
                    }
                }

                @Override
                public void onUploadFialure(int code, String msg) {
                    if (index == lists.size() - 1) {
                        callBack.onUploadFileFailure();
                        callBack.onUploadFileEnd();
                        index = 0;
                    }
                }
            }).uploadFileAction(files.get(i));
        }
    }

    public interface UploadFileCallBack {
        void onUploadFileStart();

        void onUploadFileEnd();

        void onUploadFileSuccess(List<String> urls);

        void onUploadFileFailure();
    }
}

package com.wayto.android.utils;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

import static com.baidu.tts.tools.FileTools.deleteFile;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.utils
 * @Description:文件工具类
 * @date 2017/1/10 11:42
 */

public class IFileUtils {

    /*文件根目录*/
    public static final String FILE_ROOT_DIRECTORY = "Yunwei";
    /*项目根目录*/
    public static final String PROJECT_ROOT_DIRECTORY = "Base";
    /*图片文件夹*/
    public static final String IMAGE_DEIRECTORY = "image";
    /*缓存文件夹*/
    public static final String IMAGE_CATCH_DIR = "catch";
    /*下载目录*/
    public static final String DOWNLOAD_DIR = "download";

    /**
     * 获取SD卡根目录
     *
     * @return
     */
    public static String getSDROOT() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 图片缓存地址
     *
     * @return
     */
    public static String getImageCatchDir() {
        String path = getSDROOT() + File.separator + FILE_ROOT_DIRECTORY + File.separator + PROJECT_ROOT_DIRECTORY + File.separator + IMAGE_CATCH_DIR + File.separator + UUID.randomUUID().toString() + ".png";
        File file = new File(path);
        if (!file.exists()) {
            File filePath = file.getParentFile();
            filePath.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 下载目录
     *
     * @return
     */
    public static String getDownloadDir() {
        return getSDROOT() + File.separator + FILE_ROOT_DIRECTORY + File.separator + PROJECT_ROOT_DIRECTORY + File.separator + DOWNLOAD_DIR;
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }


    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }
}

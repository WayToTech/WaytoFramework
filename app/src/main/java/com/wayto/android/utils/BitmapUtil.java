package com.wayto.android.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author hezhiWu
 * @version V1.0
 * @Package com.yunwei.frame.utils
 * @Description:图片处理工具类
 * @date 2016/12/29 10:48
 */

public class BitmapUtil {

    /**
     * 图片压缩
     *
     * @param filePath
     * @param cachePath
     * @return 返回保存图片的路径
     */
    public static String compressBitmap(String filePath, String cachePath, String date) {
        String savePath = cachePath;
        BitmapFactory.Options options = new BitmapFactory.Options();
       /* 设置true,只读取尺寸，不占用内存资源*/
        options.inJustDecodeBounds = true;
        /*
         位图位数越高代表其可以存储的颜色信息越多，图像也就越逼真。
         ALPHA_8 代表8位Alpha位图
         ARGB_4444 代表16位ARGB位图
         ARGB_8888 代表32位ARGB位图
         RGB_565 代表8位RGB位图
         */
        options.inPreferredConfig = Bitmap.Config.RGB_565;

       /* 不会把图片读入内存，只会获取图片宽高等信息*/
        int heitht = options.outHeight;
        /* 根据需要设置压缩比例*/
        int size = heitht / 800;
        if (size <= 0) {
            size = 2;
        }
        /*inSampleSize表示缩略图大小为原始图片大小的几分之一，
         即如果这个值为2，则取出的缩略图的宽和高都是原始图片的1/2，
         图片大小就为原始大小的1/4*/
        options.inSampleSize = size;
        /*当系统内存不够时候图片自动被回收*/
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inJustDecodeBounds = false;
        try {
        /*添加当前时间的水印*/
            Bitmap bitmap1 = BitmapFactory.decodeFile(filePath, options);
            float x = bitmap1.getWidth() - 600;
            float y = bitmap1.getHeight() - 100;
            Bitmap bitmap = addTextWatermark(bitmap1, date, 64, 0xFFFFFFFF, x, y, false);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
            int o = 70;
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            while (baos.toByteArray().length / 1024 > 200) {
             /*循环判断如果压缩后图片是否大于500kb继续压缩*/
                baos.reset();
                o -= 10;
             /*这里压缩options%，把压缩后的数据存放到baos中*/
                bitmap.compress(Bitmap.CompressFormat.JPEG, o, baos);
            }
            FileOutputStream out = new FileOutputStream(cachePath);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
            bitmap1.recycle();
            bitmap.recycle();
        } catch (IOException e) {
            e.printStackTrace();
            savePath = "";
        }
        return savePath;
    }

    /**
     * 图片压缩
     *
     * @param filePath
     * @param cachePath
     * @return 返回保存图片的路径
     */
    public static String compressBitmap(String filePath, String cachePath) {
        return compressBitmap(filePath, cachePath, "");
    }


    /**
     * 添加文字水印
     *
     * @param src      源图片
     * @param content  水印文本
     * @param textSize 水印字体大小
     * @param color    水印字体颜色
     * @param x        起始坐标x
     * @param y        起始坐标y
     * @param recycle  是否回收
     * @return 带有文字水印的图片
     */
    private static Bitmap addTextWatermark(Bitmap src, String content, float textSize, int color, float x, float y, boolean recycle) {
        if (isEmptyBitmap(src) || content == null) return null;
        Bitmap ret = src.copy(src.getConfig(), true);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Canvas canvas = new Canvas(ret);
        paint.setColor(color);
        paint.setTextSize(textSize);
        Rect bounds = new Rect();
        paint.getTextBounds(content, 0, content.length(), bounds);
        canvas.drawText(content, x, y + textSize, paint);
        if (recycle && !src.isRecycled()) src.recycle();
        return ret;
    }

    /**
     * 判断bitmap对象是否为空
     *
     * @param src 源图片
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private static boolean isEmptyBitmap(Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }
}

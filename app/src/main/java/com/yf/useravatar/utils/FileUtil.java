package com.yf.useravatar.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by yf on 2016/7/22 0022.
 * 文件的缓存与读取
 */
public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * 获取缓存路径
     *
     * @param context
     * @return
     */
    public static String getCachePath(Context context) {
        String cachePath = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            cachePath = context.getExternalCacheDir().getPath();

        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 删除指定文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void recursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                recursionDeleteFile(f);
            }
            file.delete();
        }
    }


    /**
     * 压缩图片方法，为了上传到服务器，保证图片大小在100k一下
     *
     * @param context
     * @param fileSrc
     * @return
     */
    public static File getSmallBitmap(Context context, String fileSrc) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileSrc, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        Log.i(TAG, "options.inSampleSize-->" + options.inSampleSize);
        options.inJustDecodeBounds = false;
        Bitmap img = BitmapFactory.decodeFile(fileSrc, options);
        Log.i(TAG, "file size after compress-->" + img.getByteCount() / 256);
        String filename = context.getFilesDir() + File.separator + "video-" + img.hashCode() + ".jpg";
        saveBitmap2File(img, filename);
        return new File(filename);
    }

    /**
     * 设置压缩的图片的大小设置的参数
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(height) / reqHeight;
            int widthRatio = Math.round(width) / reqWidth;
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    /**
     * 保存bitmap到文件
     *
     * @param bmp
     * @param filename
     * @return
     */
    public static boolean saveBitmap2File(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 50;//压缩50% 100表示不压缩
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }
}
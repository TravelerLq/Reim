package com.jci.vsd.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Created by lyx on 2017/6/20 15:48.
 * Contact:     lvyongxu@gmail.com
 * Description:
 */

public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 保存的目录名
     */
    private final static String FOLDER_FILE = "/files";

    /**
     * 视频文件夹名称
     */
    private final static String FOLDER_VIDEO = "video";

    /**
     * 视频文件夹名称
     */
    private final static String FOLDER_APK = "Download";

    /**
     * 获取Android/data/com.useeinfo.project/assistant/cache 文件夹路径
     * String folderPath = context.getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + "/Screenshots";
     *
     * @param context
     * @return
     */
    public static String getAppCacheDirPath(Context context) {
        return context.getExternalCacheDir().getPath();
    }

    public static File getAppCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    public static boolean isExternalAvailable() {
        return Environment.isExternalStorageEmulated();

    }

    /**
     * 获取Android/data/com.useeinfo.project/assistant/file 文件夹路径
     *
     * @param context
     * @return
     */
    public static String getExternalFilesDirPath(Context context) {
        return context.getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 获取存储项目信息页面文件的文件夹对象
     * Android/data/com.useeinfo.project/assistant/files
     *
     * @param context
     * @return
     */
    public static File getAppFilesDir(Context context) {
        if (isExternalAvailable()) {
            return context.getExternalFilesDir(null);
        } else {
            return context.getFilesDir();
        }
    }

    /**
     * 根据文件url获取文件的路径名字
     *
     * @param url
     * @return
     */
    public  static String getFileName(String url) {
        int separatorIndex = url.lastIndexOf("/");
        String path = (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());

        return path;
    }

    /**
     * 根据文件url获取文件的类型
     *
     * @param url
     * @return
     */
    public  static String getFileType(String url) {
        int separatorIndex = url.lastIndexOf(".");
        String type = (separatorIndex < 0) ? url : url.substring(separatorIndex + 1, url.length());

        return type;
    }

    /**
     * 获取存储项目信息页面文件的文件夹绝对路径
     * Android/data/com.useeinfo.project/assistant/files
     *
     * @param context
     * @return
     */
    public static String getAppFilesDirPath(Context context) {
        return getAppFilesDir(context).getAbsolutePath();
    }

    /**
     * 获取存储项目实况页面的视频文件的文件夹对象
     * Android/data/com.useeinfo.project/assistant/files/video
     *
     * @param context
     * @return
     */
    public static File getAppFilesVideoDir(Context context) {
        if (isExternalAvailable()) {

            File videoFolder = new File(context.getExternalFilesDir(null), FOLDER_VIDEO);
//            Log.e(TAG, "getAppFilesVideoDir: videoFolder===" + videoFolder.getAbsolutePath());
//            Log.e(TAG, "getAppFilesVideoDir: videoFolder.exists()===" + videoFolder.exists());
            if (!videoFolder.exists()) {
                videoFolder.mkdirs();
            }

            return videoFolder;
        } else {

            File videoFolder = new File(context.getFilesDir(), FOLDER_VIDEO);
//            Log.e(TAG, "getAppFilesVideoDir: videoFolder~~~" + videoFolder.getAbsolutePath());
            if (!videoFolder.exists()) {
                videoFolder.mkdirs();
            }

            return videoFolder;
        }
    }


    /**
     * 获取存储项目实况页面的视频文件的文件夹对象
     * Android/data/com.useeinfo.project/assistant/files/video
     *
     * @param context
     * @return
     */
    public static File getAppFilesApkDir(Context context) {
        if (isExternalAvailable()) {

            File videoFolder = new File(Environment.getExternalStorageDirectory(), FOLDER_APK);
//            Log.e(TAG, "getAppFilesVideoDir: videoFolder===" + videoFolder.getAbsolutePath());
//            Log.e(TAG, "getAppFilesVideoDir: videoFolder.exists()===" + videoFolder.exists());
            if (!videoFolder.exists()) {
                videoFolder.mkdirs();
            }

            return videoFolder;
        } else {

            File videoFolder = new File(context.getFilesDir(), FOLDER_APK);
//            Log.e(TAG, "getAppFilesVideoDir: videoFolder~~~" + videoFolder.getAbsolutePath());
            if (!videoFolder.exists()) {
                videoFolder.mkdirs();
            }

            return videoFolder;
        }
    }


    public static String getAppFilesApkDirPath(Context context) {
        return getAppFilesApkDir(context).getAbsolutePath();
    }


    public static String getAppFilesVideoDirPath(Context context) {
        return getAppFilesVideoDir(context).getAbsolutePath();
    }

//    public static String getFileName(String filePath) {
//        int lastDivideLine = filePath.lastIndexOf("/");
//        String fileName = filePath.substring(lastDivideLine);
//        return fileName;
//    }
//
//    public static File getFile(Context context, String fileName) {
//        return new File(getAppFilesDir(context), fileName);
//    }



    public static byte[] createChecksum(String filename) throws Exception {
        InputStream fis = new FileInputStream(filename);          //将流类型字符串转换为String类型字符串

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5"); //如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
        int numRead;

        do {
            numRead = fis.read(buffer);    //从文件读到buffer，最多装满buffer
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);  //用读到的字节进行MD5的计算，第二个参数是偏移量
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }

    public static String getMD5Checksum(String filename) throws Exception {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);//加0x100是因为有的b[i]的十六进制只有1位
        }
        return result;

    }

}

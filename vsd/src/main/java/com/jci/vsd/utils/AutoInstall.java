package com.jci.vsd.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class AutoInstall {
    private static String mUrl;

    /**
     * 外部传进来的url以便定位需要安装的APK
     *
     * @param url
     */
    public static void setUrl(String url) {
        mUrl = url;
    }

    /**
     * 安装
     *
     * @param context
     *            接收外部传进来的context
     */

//    public static void install(Context context) {
//        // 核心是下面几句代码
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        //intent.setAction("android.intent.action.VIEW");
//
//        intent.addCategory("android.intent.category.DEFAULT");
//        intent.setDataAndType(Uri.fromFile(new File(mUrl)),
//                "application/vnd.android.package-archive");
//        context.startActivity(intent);
//    }

    /*
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            uri = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
        }


     */

    public static void install(Context context) {
        Loger.e("--install");
        // 核心是下面几句代码


        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri;
        Loger.e("--install");
        try {
            String  hashFileReim = FileUtils.getMD5Checksum(mUrl);
            Loger.e("--apkHashFile=="+hashFileReim);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
           uri= Uri.fromFile(new File(mUrl));
           Loger.e("--autoInstall.apkurl--"+mUrl);
           // uri = Uri.fromFile(file);
        } else {
            uri = FileProvider.getUriForFile(context, context.getPackageName()
                    + ".provider", new File(mUrl));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);////添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
       // intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");

        context.startActivity(intent);
    }

}
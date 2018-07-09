package com.jci.vsd.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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

    public static void install(Context context) {
        // 核心是下面几句代码
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File(mUrl)),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}
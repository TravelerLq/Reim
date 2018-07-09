package com.jci.vsd.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by liqing on 18/6/7.
 */

public class ShareperenceUtil {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";

    private Context context;

    private SharedPreferences sharedPreferences;

    public ShareperenceUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    }


    public void setAuth(String authStr){
        if(!TextUtils.isEmpty(authStr)){
          sharedPreferences.edit().putString("auth",authStr).apply();
        }
    }



}

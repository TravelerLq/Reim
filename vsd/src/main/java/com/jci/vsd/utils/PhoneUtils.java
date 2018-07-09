package com.jci.vsd.utils;

import android.content.Context;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/3/21.
 */

public class PhoneUtils {

    private static PhoneUtils mPhoneUtils;
    private Context mContext;

    private PhoneUtils(Context c) {
        mContext = c.getApplicationContext();
    }

    public static PhoneUtils getInstance(Context context) {
        if (mPhoneUtils == null)
            synchronized (PhoneUtils.class) {
                if (mPhoneUtils == null) {
                    mPhoneUtils = new PhoneUtils(context);
                }
            }
        return mPhoneUtils;
    }

    //    public static String getMacAddress(Context context) {
//        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        String mac = wm.getConnectionInfo().getMacAddress();
//        return mac == null ? "" : mac;
//    }
    public float getDensity() {
        return mContext.getResources().getDisplayMetrics().density;
    }

    /**
     * @return 返回屏幕的宽度
     */
    public int getScreenW() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public int getScreenH() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public int dp2px(int value) {
        return (int) (0.5f + getDensity() * value);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        return Math.round(context.getResources().getDisplayMetrics().density * dpValue);
    }

    public int getStatusBarHeight() {
        Class<?> c;
        Object obj;
        Field field;
        int x, statusBar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBar = mContext.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBar;
    }
}

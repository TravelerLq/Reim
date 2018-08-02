package com.jci.vsd.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jci.vsd.R;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.view.widget.SimpleToast;

import java.io.File;
import java.io.FileOutputStream;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class Utils {
    public static String getVersionName() {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = VsdApplication.getInstance().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(VsdApplication.getInstance().getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static Point getSize(Activity activity) {
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point;
    }


    public static String getVersionCode() {
        int version = 0;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = VsdApplication.getInstance().getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(VsdApplication.getInstance().getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf(version);
    }

    public static String stringBuilder(String s) {
        StringBuilder stringBuilder = new StringBuilder(",");

            stringBuilder.append(s);

        return stringBuilder.toString();
    }


    public static void doException(Throwable t) {
        Loger.i("UtilsdoException = "+t.getClass().getSimpleName());
        if (t instanceof IApiException) {
            SimpleToast.ToastMessage(((IApiException) t).getErrorMsg());
            return;
        }

        if (t instanceof SocketException) {
            SimpleToast.ToastMessage(R.string.socket_exception);
            return;
        }

        if (t instanceof SocketTimeoutException) {
            SimpleToast.ToastMessage(R.string.socket_timeout_exception);
            return;
        }

        if (t instanceof UnknownHostException) {
            SimpleToast.ToastMessage(R.string.unknown_host_exception);
            return;
        }
        SimpleToast.ToastMessage(t.getMessage());
    }

    public static boolean compareDate(String beginTime, String endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date bt = null;
        Date et = null;
        try {
            bt = sdf.parse(beginTime);
            et = sdf.parse(endTime);
            Loger.e("bt=" + bt + "et" + et);
            if (bt.after(et)) {
                //表示bt小于et
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return true;
    }

    public static Boolean isGB2312(String str) {
        for (int i = 0; i < str.length(); i++) {
            String bb = str.substring(i, i + 1);
        // 生成一个Pattern,同时编译一个正则表达式,其中的u4E00("一"的unicode编码)-\u9FA5("龥"的unicode编码)
            boolean cc = java.util.regex.Pattern.matches("[\u4E00-\u9FA5]", bb);
            if (cc == false) {
                return cc;
            }
        }
        return true;
    }

    public static void closeSoftKeyBoard(Activity activity){
        try {
            final IBinder token = activity.getCurrentFocus()
                    .getWindowToken();

            final InputMethodManager imm = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    try {
                        imm.hideSoftInputFromWindow(token, 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 50);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeSoftKeyBoardNo(Activity activity){
        InputMethodManager imm =  (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    public static void paintOrderLayout(Context context, LinearLayout layout,List<String> list){
        layout.removeAllViews();
        //工单水平滑动
        for (int i=0;i<list.size();i++){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT);
            TextView textView = new TextView(context);
            textView.setLayoutParams(params);
            textView.setTextSize(16);
            if(i != list.size()-1) {
                textView.setText(list.get(i)+",");
            }else{
                textView.setText(list.get(i));
            }
            textView.setTextColor(context.getResources().getColor(R.color.black_333333));
            layout.addView(textView);
        }
    }

    /**
     * 扫描物料单
     * @param scanMessage
     * @return
     */

    public static String splitScanMessage(String scanMessage){
        String result = scanMessage;
        if(scanMessage.contains("\n")){
            result = scanMessage.split("\n")[1];
        }else if(scanMessage.contains(";")){
            result = scanMessage.split(";")[0];
        }
        return result;
    }

    public static void saveLog(String result){
        try {
            Loger.i("saveLog =start");
            FileOutputStream inputStream = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "log.txt"));
            byte[] message = result.getBytes();
            inputStream.write(message);
            inputStream.flush();
            inputStream.close();
            Loger.i("saveLog =end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public static void setEditRequestFocusAndOpenSoftKeyBoard(Activity activity,EditText editInputNum){
        editInputNum.setFocusableInTouchMode(true);
        editInputNum.requestFocus();
        editInputNum.setFocusable(true);
        Utils.showSoftInputFromWindow(activity,editInputNum);
    }

    private static long currentTime;
    public static boolean isQucikClick(long result){
        long time = System.currentTimeMillis();
        if((time - currentTime) < result){
            currentTime = time;
            return true;
        }
        currentTime = time;
        return false;
    }


    /**
     * 获取单位
     * @param materialNo
     * @return
     */
    public static String getMaterialString(String materialNo){
        String result = "";
        if(!TextUtils.isEmpty(materialNo)){
            result = "("+materialNo+")";
        }
        return result;
    }

    public static String getMaterialUnit(String materialNo) {
        String result = "";
        if (!TextUtils.isEmpty(materialNo)) {
            result = materialNo;
        }
        return result;
    }

    //byte 与 int 的相互转换
    public static Date strToDate(String str) {
        //把string转化为date
        DateFormat fmt =new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = fmt.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}

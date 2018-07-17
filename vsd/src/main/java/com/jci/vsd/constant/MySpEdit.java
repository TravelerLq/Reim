package com.jci.vsd.constant;

import android.app.Activity;
import android.content.SharedPreferences;

import com.jci.vsd.application.VsdApplication;

/**
 * Created by Yso on 2017/11/13.
 */

public class MySpEdit {
    private String name = "config";
    private static MySpEdit mySpEdit;

    private MySpEdit() {

    }

    public static MySpEdit getInstance() {
        if (mySpEdit == null) {
            mySpEdit = new MySpEdit();
        }
        return mySpEdit;
    }

    public boolean getAppEmv() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getBoolean("emv", false);
    }

    public void setAppEmv(boolean result) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putBoolean("emv", result).apply();
    }

    public void setUser(String userName) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("USER", userName).apply();
    }



    public String getUser() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("USER", "");
    }


    public void setPsw(String psw) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("PSW", psw).apply();
    }



    public String getPsw() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("PSW", "");
    }


    public void setCompanyId(int companyId) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putInt("COMP_ID", companyId).apply();
    }



    public int getCompanyId() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getInt("COMP_ID", -1);
    }

    public void setAuthor(String author) {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        sp.edit().putString("AUTHOR", author).apply();
    }

    public String getAuthor() {
        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
        return sp.getString("AUTHOR", "");

    }
//    public void setPsd(String userName) {
//        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
//        sp.edit().putString("Psd", userName).apply();
//    }
//
//    public String getPsd() {
//        SharedPreferences sp = VsdApplication.getInstance().getSharedPreferences(name, Activity.MODE_PRIVATE);
//        return sp.getString("Psd", "");
//    }


}

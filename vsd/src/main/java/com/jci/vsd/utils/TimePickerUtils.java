package com.jci.vsd.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jci.vsd.bean.ReimCategoryBean;
import com.warmtel.expandtab.KeyValueBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.listeners.OnSingleWheelListener;
import cn.addapp.pickers.picker.DatePicker;
import cn.addapp.pickers.picker.SinglePicker;

/**
 * Created by liqing on 17/11/14.
 */

public class TimePickerUtils {

    private static TimePickerUtils mInstance;

    public static TimePickerUtils getInstance() {
        if (mInstance == null) {
            mInstance = new TimePickerUtils();
        }
        return mInstance;
    }

    //日期选择 editView
    public void onYearMonthDayPicker(Context context, final EditText view) {
        Calendar calendar = Calendar.getInstance();
        final String result = null;
        Loger.i("calendar= " + calendar.get(Calendar.YEAR));
        final DatePicker picker = new DatePicker((Activity) context);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(calendar.get(Calendar.YEAR) - 5, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setRangeEnd(calendar.get(Calendar.YEAR) + 10, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setWeightEnable(true);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                view.setText(year + "-" + month + "-" + day);

            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    //日期选择 textview
    public void onYearMonthDayPickerText(Context context, final TextView view) {
        Calendar calendar = Calendar.getInstance();
        final String result = null;
        Loger.i("calendar= " + calendar.get(Calendar.YEAR));
        final DatePicker picker = new DatePicker((Activity) context);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(calendar.get(Calendar.YEAR) - 5, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setRangeEnd(calendar.get(Calendar.YEAR) + 10, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setWeightEnable(true);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                view.setText(year + "-" + month + "-" + day);

            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }


    //失效日期(不能在今日之前)
    public void invalidDatePicker(Context context, final EditText view) {
        Calendar calendar = Calendar.getInstance();
        final String result = null;
        Loger.i("calendar= " + calendar.get(Calendar.YEAR));
        final DatePicker picker = new DatePicker((Activity) context);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setRangeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setRangeEnd(calendar.get(Calendar.YEAR) + 2, calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        picker.setWeightEnable(true);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                view.setText(year + "-" + month + "-" + day);

            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }


    public void onListDataPicker(Activity context, final List<String> allStatus, final View view) {
        final SinglePicker<String> picker = new SinglePicker<String>(context, allStatus);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setSelectedIndex(0);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int i, String s) {

            }
        });

        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                Loger.e("viewName=" + (view instanceof TextView));
                if (view instanceof TextView) {
                    Loger.e("view instanceof TextView---");
                    ((TextView) view).setText(allStatus.get(i));
                } else if (view instanceof EditText) {
                    ((EditText) view).setText(allStatus.get(i));
                }

            }
        });
        picker.setWeightEnable(true);

        picker.show();
    }


    public void onListPicker(Activity context, final List<String> allStatus, final View view) {
        final SinglePicker<String> picker = new SinglePicker<String>(context, allStatus);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setSelectedIndex(0);
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int i, String s) {

            }
        });

        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                Loger.e("viewName=" + (view instanceof TextView));
                if (view instanceof TextView) {
                    Loger.e("view instanceof TextView---");
                    ((TextView) view).setText(allStatus.get(i));
                } else if (view instanceof EditText) {
                    ((EditText) view).setText(allStatus.get(i));
                }

            }
        });
        picker.setWeightEnable(true);

        picker.show();
    }


    //list

    public void onListBeanPicker(Activity context, final List<ReimCategoryBean> keyValueBeanList, List<String> mData, final View view) {

        final SinglePicker<String> picker = new SinglePicker<String>(context, mData);
        picker.setCanLoop(false);
        picker.setWheelModeEnable(true);
        picker.setTopPadding(15);
        picker.setSelectedIndex(0);
        picker.setOnItemPickListener(new OnItemPickListener<String>() {
            @Override
            public void onItemPicked(int i, String s) {
                ReimCategoryBean keyValueBean = keyValueBeanList.get(i);
                view.setTag(keyValueBean.getId());
                if (view instanceof TextView) {
                    Loger.e("view instanceof TextView---");
                    ((TextView) view).setText(keyValueBean.getPerm());

                } else if (view instanceof EditText) {
                    ((EditText) view).setText(keyValueBean.getPerm());
                }
            }

        });
        picker.setOnSingleWheelListener(new OnSingleWheelListener() {
            @Override
            public void onWheeled(int i, String s) {

            }
        });

        picker.setWeightEnable(true);

        picker.show();
    }


}

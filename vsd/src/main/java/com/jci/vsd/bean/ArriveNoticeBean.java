package com.jci.vsd.bean;

import com.jci.vsd.bean.dialog.BaseBean;

/**
 * Created by liqing on 17/11/15.
 * 到料提醒Bean
 */

public class ArriveNoticeBean extends BaseBean {
    String Time ;

    public ArriveNoticeBean(String time) {

        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}

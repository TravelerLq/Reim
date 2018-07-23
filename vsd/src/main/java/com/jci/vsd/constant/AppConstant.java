package com.jci.vsd.constant;

import android.text.TextUtils;

/**
 * Created by Victor on 11/3/2017.
 */

public class AppConstant {
    /**
     * 水平ListView 分页的每页数据大小
     */
    public static final int HZ_VEWPAGE_EVERY_PAGE_NO = 8;
    /**
     * 快捷扫描成功结果
     */
    public static final int SCAN_RESULT_OK = 200;

    /**
     * 快捷扫描失败结果
     */

    public static final int SCAN_RESULT_FAILED = 201;

    public static final String SERIAL_KEY = "serial_key";
    public static final String INT_KEY = "int_key";

    public static final int REQUEST_CODE = 1100;

    /**
     * 水平ListView 分页的每页数据大小
     */
    public static final int HZ_SEND_VEWPAGE_EVERY_PE_NAGO = 9;
    //http://192.168.1.114:8080/yuanshen/public/login
    //http://192.168.1.100:8080/shuidao/chk/listrelas
   // public static final String BASE_URL = "http://192.168.1.111:8080/";
    public static final String BASE_URL = "http://192.168.1.100:8080/";
    private static final String URL_TEST = "http://10.90.65.209:8084/";//http://10.126.211.7/";
    private static final String URL_PRD = "http://10.90.65.209:8082/";//"http://10.126.211.142/";//"http://10.90.65.209:8082/";"10.126.211.7";
    public static final String OUTSIZE_URL = "http://10.90.65.209:8088/";
    public static String TEMP_URL = "";//http://122.97.247.131:8100/

    public static String getURL() {
        String url = "";
        if (!TextUtils.isEmpty(TEMP_URL)) {
            return TEMP_URL;
        }
        if (MySpEdit.getInstance().getAppEmv()) {
            url = URL_PRD;
        } else {
            url = URL_TEST;
        }
        url = BASE_URL;
        return url;
    }

    public static final String JSON_STATUS = "Status";
    public static final String JSON_CODE = "code";
    public static final String JSON_DATA = "data";
    public static final String JSON_MESSAGE = "msg";
    public static final String JSON_SUCCESS_STATUS = "200";
    public static final String JSON_SUCCESS_REPEAT_STATUS = "203";

    public static final String NOTICE_ACTION = "notice_unkown_action";

    public static final int QUCIK_CLICK = 1000;


    /**
     * 存料列表
     */
    public static final int MATERIAL_WAIT = 1;//待存
    public static final int MATERIAL_PROGRESS = 2;//进行中
    public static final int MATERIAL_DONE = 3;//已完成

    /**
     * 公司信息更新
     */
    public static final String UPDATE = "update";//
    public static final String NEW = "new";
    public static final String NEW_LOGIN = "new_login";//已完成

    public static final String KEY_USER="user";

    public static final String KEY_ENTERPRISE="enterprise";
    public static final String KEY_ENTERPRISE_LIST="enterprise_list";

    public static final String KEY_DEPARTMENT="department";
    public static final String KEY_DEPARTMENT_LIST="department_list";


    public static final String KEY_TYPE="type";
    public static final String VALUE_AJUST="ajust";
    public static final String VALUE_ADD="add";
    public static final String VALUE_BUDGET_DEPART="depart";
    public static final String VALUE_BUDGET_CATEGORY="category";
    public static final String VALUE_SUBJECT_TYPE="subject_type";


}

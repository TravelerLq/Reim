package com.jci.vsd.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.activity.enterprise.DepartmentManageActivity;
import com.jci.vsd.bean.ReimCategoryBean;
import com.jci.vsd.bean.enterprise.DepartmentBean;
import com.jci.vsd.bean.enterprise.EnterpriseRequestBean;
import com.jci.vsd.bean.enterprise.EnterpriseResponseBean;
import com.jci.vsd.bean.enterprise.GetEnterInfoBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.DepartmentManageApi;
import com.jci.vsd.network.api.EnterpriseApi;
import com.jci.vsd.utils.Loger;
import com.warmtel.expandtab.KeyValueBean;

import java.security.Key;
import java.sql.Ref;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Headers;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 18/7/11.
 * 部门管理
 */

public class DepartmentManageControl extends BaseControl {

    /**
     * @return
     */
    //获取部门（ new company）
    public Observable<List<DepartmentBean>> getDepartment() {

        Retrofit retrofit = builderJsonRetrofit();
        DepartmentManageApi api = retrofit.create(DepartmentManageApi.class);
        Observable<Response<String>> observable = api.getDepartment();

        return observable.map(new Function<Response<String>, List<DepartmentBean>>() {
            @Override
            public List<DepartmentBean> apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
//                codeHttp = 401;
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                Loger.e("header-authStr" + authStr);
                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());

                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    List<DepartmentBean> list = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA),
                            DepartmentBean.class);

                    //  JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
//                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
//                    loginResponseBean.setList(authorityBeanList);
                    return list;
                }
                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });


    }


    /**
     * @return true  成功
     */
    //新增部门（ new company）
    public Observable<Boolean> addDepartment(DepartmentBean requestBean) {

        Retrofit retrofit = builderJsonRetrofit();
        String paramsStr = JSON.toJSONString(requestBean);
        DepartmentManageApi api = retrofit.create(DepartmentManageApi.class);
        Observable<Response<String>> observable = api.addDepartment(paramsStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
//                codeHttp = 401;
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                Loger.e("header-authStr" + authStr);
                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());

                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    return true;
                }
                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });


    }


    /**
     * @return true  删除
     */
    //删除部门（ new company）
    public Observable<Boolean> deleteDepartment(int departId) {

        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("id", departId);
        String paramsStr = JSON.toJSONString(paraMap);
        DepartmentManageApi api = retrofit.create(DepartmentManageApi.class);
        Observable<Response<String>> observable = api.deleteDepartment(paramsStr);

        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
//                codeHttp = 401;
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                Loger.e("header-authStr" + authStr);
                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());

                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    return true;
                }
                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });


    }


    /**
     * @return true  修改
     */
    //修改部门（ new company）
    public Observable<Boolean> updateDepartment(DepartmentBean requestBean) {

        Retrofit retrofit = builderJsonRetrofit();
        String paramsStr = JSON.toJSONString(requestBean);
        DepartmentManageApi api = retrofit.create(DepartmentManageApi.class);
        Observable<Response<String>> observable = api.updateDepartment(paramsStr);

        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
//                codeHttp = 401;
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                Loger.e("header-authStr" + authStr);
                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());

                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    return true;
                }
                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });


    }

    public Observable<List<ReimCategoryBean>> getAuthority() {
        Retrofit retrofit = builderRetrofit();
        DepartmentManageApi api = retrofit.create(DepartmentManageApi.class);
        Observable<Response<String>> observable = api.getAuthority();


        return observable.map(new Function<Response<String>, List<ReimCategoryBean>>() {
            @Override
            public List<ReimCategoryBean> apply(Response<String> stringResponse) throws Exception {


                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
//                codeHttp = 401;
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                Loger.e("header-authStr" + authStr);
                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());

                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    List<ReimCategoryBean> list = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), ReimCategoryBean.class);
                    return list;
                }
                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }


}

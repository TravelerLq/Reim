package com.jci.vsd.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.enterprise.AjustMembersBean;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.BudgetApi;
import com.jci.vsd.network.api.MembersApi;
import com.jci.vsd.utils.Loger;

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
 * 预算管理
 */

public class BudgetManageControl extends BaseControl {


    //预算管理
    public Observable<List<BudgetBean>> getBudgetList() {
        Retrofit retrofit = builderJsonRetrofit();
        BudgetApi api = retrofit.create(BudgetApi.class);
        Map<String, Object> map = new HashMap<>();
        map.put("type", 1);
        String paramStr = JSON.toJSONString(map);
        Observable<Response<String>> observable = api.getBudgetList(map);
        return observable.map(new Function<Response<String>, List<BudgetBean>>() {
            @Override
            public List<BudgetBean> apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                Loger.e("header-authStr" + authStr);
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }

                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    List<BudgetBean> beanList = JSON.parseArray(dataObj.getString("vos"), BudgetBean.class);

//                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
//                    loginResponseBean.setList(authorityBeanList);
                    return beanList;
                }

                throw new IApiException("", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }


    //add预算

    public Observable<Boolean> addBudget(BudgetBean bean) {
        Retrofit retrofit = builderJsonRetrofit();
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("id", membersBean.getId());
        String paramStr = JSON.toJSONString(bean);
        BudgetApi api = retrofit.create(BudgetApi.class);
        Observable<Response<String>> observable = api.addBudgetItem(paramStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    return true;
                }
                throw new IApiException("预算管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //删除预算

    public Observable<Boolean> deleteBudget(BudgetBean bean) {
        Retrofit retrofit = builderJsonRetrofit();
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("id", membersBean.getId());
        String paramStr = JSON.toJSONString(bean);
        BudgetApi api = retrofit.create(BudgetApi.class);
        Observable<Response<String>> observable = api.deleteBudgetItem(paramStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    return true;
                }
                throw new IApiException("预算管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


    //更新预算管理

    public Observable<Boolean> updateBudget(BudgetBean budgetBean) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paramMap = new HashMap<>();
      //  paramMap.put("id", ajustMembersBean.getId());
        String paramsStr = JSON.toJSONString(budgetBean);
        MembersApi api = retrofit.create(MembersApi.class);
        Observable<Response<String>> observable = api.ajust(paramsStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    return true;
                }
                throw new IApiException("预算管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

}

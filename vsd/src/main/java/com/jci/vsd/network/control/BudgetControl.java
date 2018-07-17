package com.jci.vsd.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.bean.enterprise.EnterpriseRequestBean;
import com.jci.vsd.bean.enterprise.EnterpriseResponseBean;
import com.jci.vsd.bean.enterprise.GetEnterInfoBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.BudgetApi;
import com.jci.vsd.network.api.EnterpriseApi;
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
 * 预算
 */

public class BudgetControl extends BaseControl {

    /**
     * @return
     */
    //预算列表（ new company）
    public Observable<List<BudgetBean>> getBudgetList(String type) {

        Retrofit retrofit = builderRetrofit();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("address", type);
//        final String jsonString = JSON.toJSONString(requestBean);
//        //  final String jsonString=JSON.toJSONString(paramsMap);
//        System.out.println("jsonString:" + jsonString);


        // final String requestStr = JSON.toJSONString(paramsMap);
        BudgetApi api = retrofit.create(BudgetApi.class);
        Observable<Response<String>> observable = api.getBudgetList(paramsMap);

        return observable.map(new Function<Response<String>, List<BudgetBean>>() {
            @Override
            public List<BudgetBean> apply(Response<String> stringResponse) throws Exception {
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
                EnterpriseResponseBean responseBean;
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {

                    List<BudgetBean> beanList = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA),
                            BudgetBean.class);

//                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
//                    loginResponseBean.setList(authorityBeanList);
                    return beanList;
                }


                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });


    }

    //更新预算信息

    public Observable<Boolean> updateBudget(final EnterpriseRequestBean requestBean) {

        Retrofit retrofit = builderJsonRetrofit();
        final String jsonString = JSON.toJSONString(requestBean);
        System.out.println("jsonString:" + jsonString);
        EnterpriseApi api = retrofit.create(EnterpriseApi.class);
        Observable<Response<String>> observable = api.updateEnterprise(jsonString);

        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
                codeHttp = 401;
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                Loger.e("header-authStr---" + authStr);

                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                EnterpriseResponseBean responseBean;
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {

                    //  JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    return true;
                }

                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });

    }

    //获取公司信息

    public Observable<GetEnterInfoBean> getEnterpeiseInfo() {
        Retrofit retrofit = builderJsonRetrofit();
//        final String jsonString = JSON.toJSONString(requestBean);
//        System.out.println("jsonString:" + jsonString);
        EnterpriseApi api = retrofit.create(EnterpriseApi.class);
        Observable<Response<String>> observable = api.getEnterpriseInfo();

        return observable.map(new Function<Response<String>, GetEnterInfoBean>() {
            @Override
            public GetEnterInfoBean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                Loger.e("header-authStr" + authStr);
                MySpEdit.getInstance().setAuthor(authStr);

                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                GetEnterInfoBean responseBean;
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {

                    //  JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    responseBean = JSON.parseObject(jsonObject
                            .getString(AppConstant.JSON_DATA), GetEnterInfoBean.class);
                    responseBean.setStatus("200");
                    return responseBean;
                }
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 50001) {
                    responseBean = new GetEnterInfoBean();
                    responseBean.setStatus("2001");
                    return responseBean;
                }

                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });

    }


}

package com.jci.vsd.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.enterprise.AjustMembersBean;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.bean.enterprise.ProducerAddBean;
import com.jci.vsd.bean.enterprise.ProducerBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.MembersApi;
import com.jci.vsd.network.api.ProducerApi;
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
 * 审批流程管理
 */

public class ProducerManageControl extends BaseControl {


    //审批流程管理
    public Observable<List<ProducerBean>> getProducerList() {
        Retrofit retrofit = builderJsonRetrofit();
        ProducerApi api = retrofit.create(ProducerApi.class);
        Observable<Response<String>> observable = api.getProducerList();
        return observable.map(new Function<Response<String>, List<ProducerBean>>() {
            @Override
            public List<ProducerBean> apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                Loger.e("header-authStr" + authStr);

                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    List<ProducerBean> beanList = JSON.parseArray(dataObj.getString("vos"), ProducerBean.class);

//                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
//                    loginResponseBean.setList(authorityBeanList);
                    return beanList;
                }

                throw new IApiException("", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    //添加审流程

    public Observable<Boolean> addProducer(ProducerAddBean requestBean) {
        Retrofit retrofit = builderJsonRetrofit();
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("id", membersBean.getId());
        String paramStr = JSON.toJSONString(requestBean);
        ProducerApi api = retrofit.create(ProducerApi.class);
        Observable<Response<String>> observable = api.addProducerItem(paramStr);
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
                throw new IApiException("成员管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //删除审流程

    public Observable<Boolean> deleteProducer(int id) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        String paramStr = JSON.toJSONString(paramMap);
        ProducerApi api = retrofit.create(ProducerApi.class);
        Observable<Response<String>> observable = api.deleteProducerItem(paramStr);
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
                throw new IApiException("成员管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


    //更新审批流程

    public Observable<Boolean> updateProducer(ProducerBean requestbean) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paramMap = new HashMap<>();
        //   paramMap.put("id", id);
        //  String paramStr = JSON.toJSONString(paramMap);
        String paramStr = JSON.toJSONString(requestbean);
        ProducerApi api = retrofit.create(ProducerApi.class);
        Observable<Response<String>> observable = api.updateProducerItem(paramStr);
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
                throw new IApiException("成员管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


}

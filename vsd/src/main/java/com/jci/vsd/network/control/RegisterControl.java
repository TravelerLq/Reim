package com.jci.vsd.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.register.RegisterRequestBean;
import com.jci.vsd.bean.register.RegisterResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.RegisterApi;
import com.jci.vsd.utils.Loger;

import java.sql.Ref;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 18/7/11.
 * 注册
 */

public class RegisterControl extends BaseControl {

    public Observable<RegisterResponseBean> register(final RegisterRequestBean requestBean) {
        Retrofit retrofit = builderJsonRetrofit();
//        Map<String, String> paramsMap = new HashMap<>();
//        paramsMap.put("phone", tel);
//        paramsMap.put("password", psw);
        String jsonRequestStr = JSON.toJSONString(requestBean);
        RegisterApi api = retrofit.create(RegisterApi.class);
        Observable<Response<String>> observable = api.register(jsonRequestStr);

        return observable.map(new Function<Response<String>, RegisterResponseBean>() {
            @Override
            public RegisterResponseBean apply(Response<String> stringResponse) throws Exception {
                String authStr = stringResponse.headers().get("Authorization");
                Loger.e("register-author=" + authStr);
                String response = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                JSONObject jsonData = jsonObject.getJSONObject(AppConstant.JSON_DATA);

                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                RegisterResponseBean responseBean ;
                if (code == 20005) {
                    responseBean= new RegisterResponseBean();
                    responseBean.setStatus("205");
                    return responseBean;
                }
                if (code == 200) {
                    responseBean=  JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), RegisterResponseBean.class);
                    responseBean.setStatus("200");
                    return responseBean;
                }
                if (code == 20006) {
                   // responseBean=  JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), RegisterResponseBean.class);
                    responseBean= new RegisterResponseBean();
                    responseBean.setStatus("206");
                    return responseBean;
                }
                if (code == 20007) {
                    // responseBean=  JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), RegisterResponseBean.class);
                    responseBean= new RegisterResponseBean();
                    responseBean.setStatus("207");
                    return responseBean;
                }

                if (code == 20008) {
                    // responseBean=  JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), RegisterResponseBean.class);
                    responseBean= new RegisterResponseBean();
                    responseBean.setStatus("208");
                    return responseBean;
                }
                throw new IApiException("注册", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }
}

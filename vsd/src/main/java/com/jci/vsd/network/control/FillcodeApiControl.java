package com.jci.vsd.network.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.enterprise.RequestCodeBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.CodeApi;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 18/7/11.
 */

public class FillcodeApiControl extends BaseControl {

    //提交邀请码
    public Observable<String> submitCode(String shareCode) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("sc", shareCode);
        CodeApi api = retrofit.create(CodeApi.class);
        String paramStr = JSON.toJSONString(paramsMap);
        Observable<Response<String>> observable = api.submitCode(paramStr);
        return observable.map(new Function<Response<String>, String>() {
            @Override
            public String apply(Response<String> s) throws Exception {
                String responseBody = s.body();
                int code = s.code();
                JSONObject jsonObject = JSON.parseObject(responseBody);
                String status = jsonObject.getString(AppConstant.JSON_CODE);
                if (code == 200) {
                    return status;
                }
                throw new IApiException("获取邀请码", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });
//        Observable<Boolean> observable=loginApi.submitCode(paramsMap);
//        return observable.map(new Function<Boolean, Boolean>() {
//            @Override
//            public Boolean apply(Boolean aBoolean) throws Exception {
//
//                return null;
//            }
//        });

    }


    //获取邀请码 (token)
    public Observable<String> getCode() {
        Retrofit retrofit = builderJsonRetrofit();
        CodeApi api = retrofit.create(CodeApi.class);
        Map<String, String> params = new HashMap<>();
        Observable<Response<String>> observable = api.getCode();


        return observable.map(new Function<Response<String>, String>() {
            @Override
            public String apply(Response<String> stringResponse) throws Exception {
                String responseBody = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseBody);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);

                if (code == 200) {
                    JSONObject objectData = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA));
                    String codeStr = objectData.getString("sc");
                    return codeStr;
                }

                throw new IApiException("获取邀请码", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    ;
}

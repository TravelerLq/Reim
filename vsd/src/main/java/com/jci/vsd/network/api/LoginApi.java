package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Yso on 2017/11/13.ring
 */

public interface LoginApi {
    @GET("api/Login/PDALogin")
    Observable<String> login(@QueryMap Map<String, String> map);

    @GET("api/PersonalInfo/BindingCard")
    Observable<String> bindCard(@QueryMap Map<String, String> map);

    @POST("api/PersonalInfo/PDAUpdatePassword")
    Observable<String> updatePassword(@QueryMap Map<String, String> map);

    @GET("api/PersonalInfo/PesonalInformation")
    Observable<String> personalInformation(@QueryMap Map<String, String> map);

    //yuanshen/public/login
    @POST("yuanshen/public/login")
    Observable<String> newLogin(@Body String str);

    //Response 的返回
    //http://192.168.1.111:8080/shuidao/tokens/login
    @POST("shuidao/notoken/login")
    Observable<Response<String>> loginResponse(@Body String str);

    //填写邀请码
    @POST("yuanshensystem/public/verifysharecode")
    Observable<String> submitCode(@QueryMap Map<String, String> map);

    //退出登录 api
    @DELETE("shuidao/tokens/logout")
    Observable<String> loginOut();
}

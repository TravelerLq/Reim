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

public interface CodeApi {

//    @GET("api/PersonalInfo/BindingCard")
//    Observable<String> bindCard(@QueryMap Map<String, String> map);

//    @POST("api/PersonalInfo/PDAUpdatePassword")
//    Observable<String> updatePassword(@QueryMap Map<String, String> map);
//
//    @GET("api/PersonalInfo/PesonalInformation")
//    Observable<String> personalInformation(@QueryMap Map<String, String> map);
//
//    //yuanshen/public/login
//    @POST("yuanshen/public/login")
//    Observable<String> newLogin(@Body String str);
//
//    //Response 的返回
//    //http://192.168.1.111:8080/shuidao/tokens/login
//    @POST("shuidao/tokens/login")
//    Observable<Response<String>> loginResponse(@Body String str);

    //填写邀请码
    @POST("shuidao/users/verqr")
    Observable<Response<String>> submitCode(@Body String body);

    //获取邀请码
    @POST("/shuidao/users/qr")
    Observable<Response<String>>getCode();


}

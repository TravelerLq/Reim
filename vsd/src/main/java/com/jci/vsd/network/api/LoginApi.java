package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Yso on 2017/11/13.ring
 */

public interface LoginApi {
    @GET("api/Login/PDALogin")
    Observable<String> login(@QueryMap Map<String,String> map);

    @GET("api/PersonalInfo/BindingCard")
    Observable<String> bindCard(@QueryMap Map<String,String> map);

    @POST("api/PersonalInfo/PDAUpdatePassword")
    Observable<String> updatePassword(@QueryMap Map<String,String> map);

    @GET("api/PersonalInfo/PesonalInformation")
    Observable<String> personalInformation(@QueryMap Map<String,String> map);

    //yuanshen/public/login
    @POST("yuanshen/public/login")
    Observable<String> newLogin(@Body String str );

    //Response 的返回
    @POST("yuanshen/public/login")
    Observable<Response<String>> loginResponse (@Body String str) ;


}

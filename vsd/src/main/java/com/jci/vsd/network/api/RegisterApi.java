package com.jci.vsd.network.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by liqing on 18/7/11.
 */

public interface RegisterApi {
    //注册http://192.168.1.111:8080/shuidao/users/reg
    @POST("shuidao/notoken/reg")
    Observable<Response<String>> register(@Body String str);

}

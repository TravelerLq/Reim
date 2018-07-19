package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Yso on 2017/11/13.ring
 * 预算Api
 */

public interface BudgetApi {

    //budget 获取
    @GET("api/Login/PDALogin")
    Observable<Response<String>> getBudgetList(@QueryMap Map<String, Object> map);

    //budget item 删除
    @PATCH("api/PersonalInfo/BindingCard")
    Observable<Response<String>> deleteBudgetItem(@Body String body);

    //budget 新增item
    @POST("api/PersonalInfo/PDAUpdatePassword")
    Observable<Response<String>> addBudgetItem(@Body String body);


    //budget 修改编辑item
    @PATCH("api/PersonalInfo/PDAUpdatePassword")
    Observable<Response<String>> updateBudgetItem(@Body String body);


}

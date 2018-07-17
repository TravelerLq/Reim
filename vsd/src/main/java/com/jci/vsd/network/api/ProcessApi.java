package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Yso on 2017/11/13.ring
 * 审批流程Api
 */

public interface ProcessApi {

    //budget 获取
    @GET("api/Login/PDALogin")
    Observable<String> getBudgetList(@QueryMap Map<String, String> map);

    //budget item 删除
    @GET("api/PersonalInfo/BindingCard")
    Observable<String> deleteBudgetItem(@QueryMap Map<String, String> map);

    //budget 新增item
    @POST("api/PersonalInfo/PDAUpdatePassword")
    Observable<String> addBudgetItem(@Body Map<String, String> map);


    //budget 修改编辑item
    @POST("api/PersonalInfo/PDAUpdatePassword")
    Observable<String> updateBudgetItem(@QueryMap Map<String, String> map);


}

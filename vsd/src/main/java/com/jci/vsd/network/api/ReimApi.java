package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Yso on 2017/11/13.ring
 * 报销Api
 */

public interface ReimApi {

    //budget 获取
    @GET("shuidao/quotas/all")
    Observable<Response<String>> getBudgetList(@QueryMap Map<String, Object> map);

    //budget item 删除
    @PATCH("shuidao/quotas/del")
    Observable<Response<String>> deleteBudgetItem(@Body String body);

    //budget 新增item
    //http://192.168.1.111:8080/shuidao/quotas/add
    @POST("shuidao/quotas/add")
    Observable<Response<String>> addBudgetItem(@Body String body);


    //budget 修改编辑item
    @PATCH("shuidao/quotas/upd")
    Observable<Response<String>> updateBudgetItem(@Body String body);

    //budget 获取可选择的部门    getJsonData();
    @GET("shuidao/quotas/avldpts")
    Observable<Response<String>> getBudgetDeparment();

    //budget 获取 可选择的科目
    @GET("shuidao/quotas/all")
    Observable<Response<String>> getBudgetCategory();

}

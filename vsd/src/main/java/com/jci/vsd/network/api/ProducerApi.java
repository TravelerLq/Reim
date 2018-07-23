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
 * 审批流程Api
 */

public interface ProducerApi {

    // 获取
    @GET("shuidao/quotas/all")
    Observable<Response<String>> getProducerList();

    //budget item 删除
    @DELETE("shuidao/quotas/del")
    Observable<Response<String>> deleteProducerItem(@Body String body);

    //budget 新增item
    //http://192.168.1.111:8080/shuidao/quotas/add
    @POST("shuidao/quotas/add")
    Observable<Response<String>> addProducerItem(@Body String body);


    //budget 修改编辑item
    @PATCH("shuidao/quotas/upd")
    Observable<Response<String>> updateProducerItem(@Body String body);

    //budget 获取可选择的部门
    @GET("shuidao/quotas/all")
    Observable<Response<String>> getBudgetDeparment();

    //http://192.168.1.100:8080/shuidao/chk/listrelas

    //budget 获取 可选择的科目
    @GET("shuidao/chk/listrelas")
    Observable<Response<String>> getBudgetCategory();

}

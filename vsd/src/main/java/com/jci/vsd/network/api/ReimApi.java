package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by Yso on 2017/11/13.ring
 * 报销Api
 */

public interface ReimApi {


    @Multipart
    @POST("shuidao/cost/add")
    Observable<String> upload_reim(@Part MultipartBody.Part image, @PartMap Map<String, RequestBody> map);


    @POST("shuidao/form/gen")
    Observable<Response<String>> submitReim(@Body String body);

    @POST("shuidao/form/ci")
    Observable<Response<String>> submitReimDoc(@Body String body);

    //budget 获取
    @GET("shuidao/quotas/all")
    Observable<Response<String>> getBudgetList(@QueryMap Map<String, Object> map);

    //budget item 删除
    @PATCH("shuidao/quotas/del")
    Observable<Response<String>> deleteBudgetItem(@Body String body);

    //budget 新增item
    //http://192.168.1.111:8080/shuidao/quotas/add


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
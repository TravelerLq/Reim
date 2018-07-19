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
 * 审批流程Api
 */

public interface ProcessApi {

    //审批流程 获取
    @GET("api/Login/PDALogin")
    Observable<Response<String>> getProcessList(@QueryMap Map<String, Object> map);

    //审批流程 item 删除
    @PATCH("api/PersonalInfo/BindingCard")
    Observable<Response<String>> deleteProcessItem(@Body String body);

    //审批流程 新增item
    @POST("api/PersonalInfo/PDAUpdatePassword")
    Observable<Response<String>> addProcessItem(@Body String body);


    //审批流程 修改编辑item
    @PATCH("api/PersonalInfo/PDAUpdatePassword")
    Observable<Response<String>> updateProcessItem(@Body String body);


}

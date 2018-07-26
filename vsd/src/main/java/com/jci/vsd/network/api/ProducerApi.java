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
    @GET("shuidao/chkpnt/listchkpnts")
    Observable<Response<String>> getProducerList();

    //producer item 删除
    @PATCH("shuidao/chkpnt/del")
    Observable<Response<String>> deleteProducerItem(@Body String body);

    //producer 新增item
    //http://192.168.1.111:8080/shuidao/quotas/add
    @POST("shuidao/chkpnt/add")
    Observable<Response<String>> addProducerItem(@Body String body);


    //producer 修改编辑item
    @PATCH("shuidao/chkpnt/upd")
    Observable<Response<String>> updateProducerItem(@Body String body);

    //producer 获取可选择的部门和审核人
    @GET("shuidao/chkpnt/avl")
    Observable<Response<String>> getProducerSettingInfo();

    //producer 获取父节点的配置流程
    @GET("shuidao/chkpnt/fath")
    Observable<Response<String>> getParentProducer(@QueryMap Map<String, Object> map);

    //http://192.168.1.100:8080/shuidao/chk/listrelas

    //producer 获取 可选择的科目
    @GET("shuidao/chk/listrelas")
    Observable<Response<String>> getBudgetCategory();

}

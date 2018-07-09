package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/11/27.
 * 执行移库
 */

public interface ExecuteTransferApi {

//    //获取列表
//    @GET("api/ApplyAndExcute/GetApplyMovements")
//    Observable<String> getListData(@QueryMap Map<String, String> map);
    //获取列表
    @GET("api/ApplyAndExcute/GetApplyMovements")
    Observable<String> getListData(@QueryMap Map<String, String> map);


    //执行移库
    @GET("api/ApplyAndExcute/ExecuteMoveStore")
    Observable<String> executeTransfer(@QueryMap Map<String, String> map);

}

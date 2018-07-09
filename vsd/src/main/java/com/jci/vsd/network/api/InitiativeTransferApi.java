package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/11/22.
 * 主动移库API
 */

public interface InitiativeTransferApi {
    @POST("api/Batch/MoveBatch")
    Observable<String> initiativeTransfer(@Body String str );
    //获取当前库存数量
    @GET("api/Batch/GetMCcount")
    Observable<String> getStoreAmount(@QueryMap Map<String,String> map);
}

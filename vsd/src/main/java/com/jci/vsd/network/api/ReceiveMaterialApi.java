package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/12/13.
 *  收料Api
 */

public interface ReceiveMaterialApi {

    @GET("api/Batch/MaterialReceivingInterface")
    Observable<String> getReceiveMaterial(@QueryMap Map<String,String> map);

    //确定收料
    @POST("api/Batch/CompleteReceiver")
    Observable<String> confirmReceiveMaterail(@Body String str );
}

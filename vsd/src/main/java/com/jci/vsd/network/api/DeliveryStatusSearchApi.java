package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/12/11.
 * 发料状态单状态查询
 */

public interface DeliveryStatusSearchApi {
    @GET("api/Query/QuerySendStatus")
    Observable<String> getDelivertyStatus(@QueryMap Map<String,String> map);
}

package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public interface RevenueMaterialApi {
    @GET("getusertoken")
    Observable<String> getToken(@QueryMap Map<String,String> map);

    @GET("api/Batch/GetMaterialDelivery")
    Observable<String> syncMaterialDelivery(@QueryMap Map<String,String> map);
}

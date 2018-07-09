package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public interface EcoSearchApi {
    @GET("api/Query/QueryEco")
    Observable<String> ecoSearchDetail(@QueryMap Map<String,String> map);

    /**
     * ECN查询
     */
    @GET("api/Batch/QueryEco")
    Observable<String> ecnSearchDetail(@QueryMap Map<String,String> map);
}

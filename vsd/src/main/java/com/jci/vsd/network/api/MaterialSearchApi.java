package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/11/22.
 * 物料查询API
 */

public interface MaterialSearchApi {
    @GET("api/Query/QueryBatch")
    Observable<String> materialSearch(@QueryMap Map<String, String> map);

}

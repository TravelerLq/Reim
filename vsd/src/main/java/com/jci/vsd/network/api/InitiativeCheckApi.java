package com.jci.vsd.network.api;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by liqing on 17/11/23.
 */

public interface InitiativeCheckApi {
    @POST("api/Batch/StoreBatch")
    Observable<String> initiativeCheck(@Body String str );
}

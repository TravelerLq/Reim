package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/12/6.
 */

public interface NoticeCenterApi {
    @GET("api/Message/GetMessages")
    Observable<String> getNotice(@QueryMap Map<String,String> map);
    @GET("api/Message/SetMessageReaded")
    Observable<String> setNoticeRead(@QueryMap Map<String,String> map);
    @GET("api/Message/GetUnReadMessages")
    Observable<String> getPushNotice(@QueryMap Map<String,String> map);

}

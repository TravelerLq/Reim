package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/12/1.
 * 存料差异报告
 */

public interface StoreDifferReportApi {

    @POST("api/Query/ReportStoreDifferent")
    Observable<String> getStoreDifferReport(@Body String str);
    //获取详情
    @GET("api/Query/ReportDifferentDetail")
    Observable<String>getReportDifferentDetail(@QueryMap Map<String,String> map);

}

package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/12/1.
 * 盘点数据报告
 */

public interface CheckDataReportApi {

    @POST("api/Query/ReportInventory")
    Observable<String> getCheckDataReport(@Body String str);

    //获取详情
    @GET("api/Query/ReportDifferentDetail")
    Observable<String>getReportDifferentDetail(@QueryMap Map<String, String> map);
    //获取列表的详情列表
    @GET("api/Query/ReportInventoryDetail")
    Observable<String>getReportDifferentListDetail(@QueryMap Map<String, String> map);

}

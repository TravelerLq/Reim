package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public interface StoreMaterialApi {
    @GET("api/Batch/ViewStockDatail")
    Observable<String> getMaterialDetail(@QueryMap Map<String,String> map);

    @POST("api/Batch/QueryMaterialStock")
    Observable<String> getStoreMaterialList(@Body String requestJson);

    @POST("api/Batch/QueryStock")
    Observable<String> getQueryMaterialOrder(@Body String requestJsoj);

    @POST("api/Batch/QueryMaterialStock")
    Observable<String> getMaterialOrderDetailByNo(@Body String requestJson);

    /**
     * 存料明细查询（综合）
     */
    @GET("api/Batch/QueryAllStockDetails")
    Observable<String> queryAllStockDetails(@QueryMap Map<String, String> map);

    /**
     * 存料完成动作
     * @param requestJson
     * @return
     */
    @POST("api/Batch/StockDatail")
    Observable<String> submitStoreMaterial(@Body String requestJson);

    /**
     * 根据SPA号查询物料详情
     * @param map
     * @return
     */
    @GET("api/Batch/QueryStockDatail")
    Observable<String> searchMaterialBySpa(@QueryMap Map<String,Object> map);

    /**
     * 库位确认
     */
    @GET("api/Batch/MakeSureStoreArea")
    Observable<String> checkOutArea(@QueryMap Map<String,String> map);

    /**
     * 待存料
     */
    @POST("api/Batch/WaiteMaterialStock")
    Observable<String> waitMaterialStock(@Body String map);

    /**
     * 进行中存料
     */
    @POST("api/Batch/ConductMaterialStock")
    Observable<String> conductMaterialStock(@Body String map);

    /**
     * 已完成存料
     */
    @POST("api/Batch/MaterialStocked")
    Observable<String> onductMaterialStock(@Body String map);

    /**
     * 获取存料列表(以上三种状态)
     */
    @POST("api/Batch/QueryAllStock")
    Observable<String> getMaterialStock(@Body String map);

    /**
     * 查询
     */
    @POST("api/Batch/MaterialStockedDetail")
    Observable<String> finishMaterialSend(@Body String result);
}

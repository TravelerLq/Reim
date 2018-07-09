package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public interface SendMaterialApi {
    @POST("api/Batch/QuerySendMaterial")
    Observable<String> sendMaterialQuery(@Body String map);

    /**
     * 发料筛选条件查询
     * @param map
     * @return
     */
    @GET("api/Batch/GetDictionaryInfo")
    Observable<String> productScreening(@QueryMap Map<String,String> map);


    /**
     * 物料合并查询
     * @param result
     * @return
     */
    @POST("api/Batch/MergeMaterialInfo")
    Observable<String> getMergeMaterialInfo(@Body String result);

    /**
     *发料单合并查询
     */
    @POST("api/Batch/MergeMaterialSend")
    Observable<String> mergeMaterialSend(@Body String result);

    /**
     * 完成物料单
     */
    @GET("api/Batch/FinishMaterialSend")
    Observable<String> finishMaterialSend();

    @POST("api/Batch/MaterialSendDetail")
    Observable<String> mergeMaterialInfo(@Body String result);

    /**
     * 执行发料
     */
    @POST("api/Batch/SendMaterial")
    Observable<String> sendMaterial(@Body String result);

    /**
     * 根据工单获取工单
     */
    @GET("api/Batch/BoundMaterialInfo")
    Observable<String> boundMaterialInfo(@QueryMap Map<String,String> result);

    /**
     * 绑定小车
     */
    @POST("api/Batch/BoundMaterialCar")
    Observable<String> bindCarInfo(@Body String result);

    /**
     * 增加车辆
     */
    @POST("api/Batch/AddMaterialCar")
    Observable<String> addCarInfo(@Body String result);

    /**
     * 获取小车列表
     */
    @GET("api/Batch/QueryVehicle")
    Observable<String> getCarLists(@QueryMap Map<String,String> result);

    /**
     * 删除小车
     */
    @GET("api/Batch/DeleteMaterialCar")
    Observable<String> deleteCar(@QueryMap Map<String,String> map);

    /**
     * 提交区域
     */
    @POST("api/Batch/SunmitDivelArea")
    Observable<String> sunmitDivelArea(@Body String result);

    /**
     * ECN查询
     */
    @GET("api/Batch/QueryEco")
    Observable<String> ecnSearchInfo(@QueryMap Map<String,String> map);

    /**
     *根据合并单号查找工单信息
     */
    @GET("api/Batch/QuerySendMaterialMO")
    Observable<String> querySendMaterialMO(@QueryMap Map<String,String> map);

    /**
     根据工单号查找物料信息
     */
    @GET("api/Batch/QuerySendMaterialDetails")
    Observable<String> querySendMaterialDetails(@QueryMap Map<String,String> map);
}

package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 18/7/11.
 * 公司
 */

public interface DepartmentManageApi {

//    //注册公司http://192.168.1.111:8080/shuidao/coms/reg
//    @POST("shuidao/coms/reg")
//    Observable<Response<String>> registerEnterprise(@Body String str);
//
//    //更新公司信息
//    @PATCH("shuidao/coms/upd")
//    Observable<Response<String>> updateEnterprise(@Body String str);
//
//
//    //获取公司信息 (获取list 后期会改的)
//    @GET("shuidao/coms/cos")
//    Observable<Response<String>> getEnterpriseInfo();
//

    //获取部门
    @GET("shuidao/tokens/login")
    Observable<Response<String>> getDepartment();

    //新增部门
    @POST("shuidao/coms/reg")
    Observable<Response<String>> addDepartment(@Body String str);

    //删除部门
    @DELETE("shuidao/tokens/login")
    Observable<Response<String>> deleteDepartment(@Body String str);

    // 更新部门信息
    @PATCH("shuidao/coms/upd")
    Observable<Response<String>> updateDepartment(@Body String str);

    //获取权限
    @GET("shuidao/coms/upd")
    Observable<Response<String>> getAuthority();


}

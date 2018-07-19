package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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
    @GET("shuidao/depts/all")
    Observable<Response<String>> getDepartment();

    //新增部门
    @POST("shuidao/depts/add")
    Observable<Response<String>> addDepartment(@Body String str);

    //删除部门
    @PATCH("shuidao/depts/del")
    Observable<Response<String>> deleteDepartment(@Body String body);


    @HTTP(method = "DELETE", path = "shuidao/depts/del", hasBody = true)
    Observable<Response<String>> deleteBodyDepartment(@QueryMap Map<String, Object> map);

    // 更新部门信息
    @PATCH("shuidao/depts/upd")
    Observable<Response<String>> updateDepartment(@Body String str);

    //获取类别
    @GET("shuidao/depts/perm")
    Observable<Response<String>> getAuthority();


}

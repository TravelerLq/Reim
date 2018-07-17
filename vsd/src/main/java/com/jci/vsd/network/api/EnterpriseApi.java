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

public interface EnterpriseApi {

    //注册公司http://192.168.1.111:8080/shuidao/coms/reg
    @POST("shuidao/coms/reg")
    Observable<Response<String>> registerEnterprise(@Body String str);

    //更新公司信息
    @PATCH("shuidao/coms/upd")
    Observable<Response<String>> updateEnterprise(@Body String str);


    //获取公司信息 (获取list 后期会改的)
    @GET("shuidao/coms/cos")
    Observable<Response<String>> getEnterpriseInfo();


    //成员管理
    @GET("shuidao/tokens/login")
    Observable<Response<String>> getMembers();

    //删除成员
    @DELETE("shuidao/tokens/login")
    Observable<Response<String>> deleteMember(@QueryMap Map<String, Object> map);

    //掉入其他部门

}

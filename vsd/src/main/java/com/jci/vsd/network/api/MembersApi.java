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
 * 成员管理
 */

public interface MembersApi {

    //获取所有的公司员工
    //   http://192.168.1.111:8080/shuidao/users/list
    @GET("shuidao/users/list")
    Observable<Response<String>> getMembers();


    //删除成员
    @PATCH("shuidao/users/del")
    Observable<Response<String>> deleteMember(@Body String body);

    //调入其他部门
    @PATCH("shuidao/users/upd")
    Observable<Response<String>> ajust(@Body String body);


}

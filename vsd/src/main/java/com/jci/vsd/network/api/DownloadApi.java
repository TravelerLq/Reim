package com.jci.vsd.network.api;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * 文件下载功能
 * Created by Administrator on 2017/11/20 0020.
 */

public interface DownloadApi {
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);//直接使用网址下载

    @GET("shuidao/ver/chk")
    io.reactivex.Observable<String> checkUpdate(@QueryMap Map<String, String> map);

    @Streaming
    @GET
    Observable<ResponseBody> download1(@Url String url);//直接使用网址下载


    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(@Url String url);//直接使用网址下载
}

package com.jci.vsd.network.api;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by liqing on 17/11/20.
 * 信息反馈的API
 */

public interface FeedBackApi {
    //获取用户反馈
    @GET("api/PersonalInfo/SuggestionBack")
    Observable<String> getFeedBackInfo(@QueryMap Map<String,String> map);
    //获取用户反馈详情
    @GET("api/PersonalInfo/SuggestionBackDeatail")
    Observable<String> getFeedBackInfoDetail(@QueryMap Map<String,String> map);
    @POST("api/PersonalInfo/SuggestionSubmit")
    Observable<String> submitFeedBack(@Body String map);
//
    @Multipart
    @POST("api/PersonalInfo/SuggestionPicUpload")
    Observable<String>upLoadPhoto(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("api/PersonalInfo/SuggestionPicUpload")
    Observable<String>upLoadPic(@Query("userId") String userId,
                                      @Part("file\"; filename=\"image.png\"")RequestBody imgs);
    @POST("api/PersonalInfo/SuggestionPicUpload")
    Observable<String> upload(@Body MultipartBody body);

    @POST("api/PersonalInfo/SuggestionBackSubmit")
    Observable<String> replyFeedBack(@Body String result);

//    @Multipart
//    @POST("api/PersonalInfo/SuggestionPicUpload")
//    Observable<ResponseBody>upLoadPic(@Query("userId") String userId,
//                                      @Part MultipartBody.Part image);

//    @Multipart
//    @POST("api/PersonalInfo/SuggestionPicUpload")
//    Observable<ResponseBody>upLoadPic(@Part("userId") String userId,
//                                      @Part MultipartBody.Part image);

    //MultipartBody.Part image
}

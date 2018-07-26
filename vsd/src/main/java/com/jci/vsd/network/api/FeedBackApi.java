package com.jci.vsd.network.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
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
    Observable<String> getFeedBackInfo(@QueryMap Map<String, String> map);

    //获取用户反馈详情
    @GET("api/PersonalInfo/SuggestionBackDeatail")
    Observable<String> getFeedBackInfoDetail(@QueryMap Map<String, String> map);

    @POST("api/PersonalInfo/SuggestionSubmit")
    Observable<String> submitFeedBack(@Body String map);

    //
    @Multipart
    @POST("api/PersonalInfo/SuggestionPicUpload")
    Observable<String> upLoadPhoto(@PartMap Map<String, RequestBody> params);

    @POST("api/PersonalInfo/SuggestionPicUpload")
    Observable<String> upLoadPic(@Query("userId") String userId,

                                 @Part("file\"; filename=\"image.png\"") RequestBody imgs);

    @POST("shuidao/cost/add")
    Observable<String> upload(@Body MultipartBody body);

    @Multipart
    @POST("shuidao/cost/add")
    Observable<String> upload_reim_pic(@Part MultipartBody.Part image, @PartMap Map<String,Object> map);

    @Multipart
    @POST("shuidao/cost/add")
    Observable<String> upload_reim(@Part MultipartBody.Part image, @PartMap Map<String,RequestBody> map);

    @POST("api/PersonalInfo/SuggestionBackSubmit")
    Observable<String> replyFeedBack(@Body String result);

    //

    //上传图片
    @Multipart
    @POST("api.php?con=user&act=drvierCertify")
    Observable<String> pic_reim(@Part List<MultipartBody.Part> parts, @PartMap Map<String, RequestBody> params);


//    @Multipart
//    @POST("upload/")
//    Call<ResponseBody> register(
//
//            @QueryMap Map<String , String> usermaps,
//            @Part("avatar\\\\"; filename=\\\\"avatar.jpg") RequestBody avatar,
//            );
//
//Observable<Response<String>> submitCode(@Body String body);


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

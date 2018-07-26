package com.jci.vsd.network.control;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.feedback.FeedBackDetailRequestBean;
import com.jci.vsd.bean.feedback.FeedBackDetailResponseBean;
import com.jci.vsd.bean.feedback.FeedBackReplyRequestBean;
import com.jci.vsd.bean.feedback.FeedBackRequestBean;
import com.jci.vsd.bean.feedback.FeedBackResponseBean;
import com.jci.vsd.bean.feedback.SubmitFeedBackRequestBean;
import com.jci.vsd.bean.feedback.UploadPhotoRequestBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.FeedBackApi;
import com.jci.vsd.utils.BaseUtils;
import com.jci.vsd.utils.Loger;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by liqing on 17/11/20.
 */

public class FeedBackApiControl extends BaseControl {

    public Observable<List<FeedBackResponseBean>> getFeedBack(FeedBackRequestBean feedBackRequestBean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("userId", feedBackRequestBean.getUserId());
        parmsMap.put("pageSize", feedBackRequestBean.getPageSize());
        parmsMap.put("pageNum", feedBackRequestBean.getPageNum());
        FeedBackApi feedBackApi = retrofit.create(FeedBackApi.class);
        Observable<String> observable = feedBackApi.getFeedBackInfo(parmsMap);
        Loger.i("getFeedBack parmsMap= " + parmsMap);
        return observable.map(new Function<String, List<FeedBackResponseBean>>() {

            @Override
            public List<FeedBackResponseBean> apply(String s) throws Exception {
                Loger.e("getFeedBack--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if ("200".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    List<FeedBackResponseBean> feedBackRequestBean = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), FeedBackResponseBean.class);
                    return feedBackRequestBean;
                }
                throw new IApiException("getfeedback", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });

    }

    /**
     * getFeedBackDetail
     * 源头Observable发送的是String类型的数字，利用map转换成Bean型，最终在终点Observer接受到的也是Bean型类型数据。：
     */

    public Observable<List<FeedBackDetailResponseBean>> feedBackDetail(final FeedBackDetailRequestBean feedBackDetailRequestBean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> params = new HashMap<>();
        params.put("userId", feedBackDetailRequestBean.getUserId());
        params.put("submitId", feedBackDetailRequestBean.getSubmitId());
        params.put("type", feedBackDetailRequestBean.getType());
        FeedBackApi feedBackApi = retrofit.create(FeedBackApi.class);
        Loger.e("feedbackDetail--s=" + params);
        Observable<String> observable = feedBackApi.getFeedBackInfoDetail(params);
        return observable.map(new Function<String, List<FeedBackDetailResponseBean>>() {
            @Override
            public List<FeedBackDetailResponseBean> apply(@NonNull String s) throws Exception {
                Loger.e("feedbackDetail--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                String status = jsonObject.getString(AppConstant.JSON_STATUS);
                if (status.equals("200")) {
                    List<FeedBackDetailResponseBean> feedBackDetailResponseBean =
                            JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), FeedBackDetailResponseBean.class);
                    if (feedBackDetailResponseBean != null && feedBackDetailResponseBean.size() > 0) {
                        FeedBackDetailResponseBean responseBean = feedBackDetailResponseBean.get(0);
                        if (!TextUtils.isEmpty(responseBean.getBackId())) {
                            FeedBackDetailResponseBean tempTresponseBean = JSON.parseObject(new Gson().toJson(responseBean).toString(), FeedBackDetailResponseBean.class);
                            feedBackDetailResponseBean.add(tempTresponseBean);
                        }
                    }
                    return feedBackDetailResponseBean;
                }
                throw new IApiException("exception", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    //提交信息反馈
    public Observable<Boolean> submitFeedBack(final SubmitFeedBackRequestBean submitFeedBackRequestBean) {
        Retrofit retrofit = builderJsonRetrofit();
        String params = new Gson().toJson(submitFeedBackRequestBean);
        FeedBackApi feedBackApi = retrofit.create(FeedBackApi.class);
        Loger.i("submitFeedBack = " + params);
        Observable<String> observable = feedBackApi.submitFeedBack(params);
        return observable.map(new Function<String, Boolean>() {
            @Override
            public Boolean apply(@NonNull String s) throws Exception {
                Loger.e("submitFeedBack--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                String status = jsonObject.getString(AppConstant.JSON_STATUS);
                if (status.equals("200")) {
                    return true;
                }
                throw new IApiException("exception", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    public Observable<String> upLoadPic(final UploadPhotoRequestBean bean) {
        Retrofit retrofit = builderUploadPicRetrofit();
        Map<String, RequestBody> params = new HashMap<>();
        params.put("userId", RequestBody.create(MediaType.parse("text/*"), bean.getUserId()));
        if (bean.getFile() == null) {
            Loger.e("-----file is null'");
        } else {
            Loger.e("-----file is not null'" + bean.getFile().getPath());
        }
        // RequestBody body=  RequestBody.create(MediaType.parse("multipart/form-data"), bean.getFile());
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), bean.getFile());
        // RequestBody body = RequestBody.create(MediaType.parse("image/*"), bean.getFile());
        FeedBackApi feedBackApi = retrofit.create(FeedBackApi.class);
        // create multipart
//        final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part body1 = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Observable<String> observable = feedBackApi.upLoadPic(bean.getUserId(), body);
        return observable.map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String s) throws Exception {
                Loger.e("uploadpic--s=" + s);
                return s;
            }
        });


    }
//
//    RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//            .addFormDataPart("name", name)
//            .addFormDataPart("psd", psd)
//            .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
//            .build();


    public Observable<String> submitPic(final File file, final UploadProgressListener uploadProgressListener) {
        Retrofit retrofit = builderUploadPicRetrofit();
        FeedBackApi feedBackApi = retrofit.create(FeedBackApi.class);
        //application/octet-stream


        final ProgressRequestBody progressFileRequestBody = new ProgressRequestBody(file).create(MediaType.parse("application/octet-stream"), file, uploadProgressListener);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        String fileName = android.os.Build.SERIAL + "_" + new Date().getTime() + "_" + file.getName();
        builder.addFormDataPart("original", fileName, progressFileRequestBody);
        //  builder.addFormDataPart("enctype", "multipart/form-data");//告诉das服务上传的是文件
        //  builder.addFormDataPart("userId", VsdApplication.getInstance().getLoginResponseBean().getId());
        builder.addFormDataPart("start", "12");
        builder.addFormDataPart("end", "1");

        builder.addFormDataPart("bp", "1");
        builder.addFormDataPart("dst", "1");
        builder.addFormDataPart("client", "1");//告诉das服务上传的是文件
        builder.addFormDataPart("amount", "1");
        builder.addFormDataPart("remark", "1");
        builder.addFormDataPart("number", "1");
        builder.addFormDataPart("qty", "1");

        builder.addFormDataPart("details", "1");
        builder.addFormDataPart("reason", "1");
        builder.addFormDataPart("cat", "1");
        builder.addFormDataPart("item", "1");


        return feedBackApi.upload(builder.build());
    }

    public Observable<String> submitReim_pic(File file, final UploadProgressListener uploadProgressListener) {

        Retrofit retrofit = builderUploadPicRetrofit();
        FeedBackApi feedBackApi = retrofit.create(FeedBackApi.class);
//
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        // MultipartBody.Part is used to send also the actual file name
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("original", file.getName(), requestFile);
//

        Map<String, RequestBody> params = new HashMap<>();
        params.put("start", BaseUtils.convertToRequestBody("1"));
        params.put("end", BaseUtils.convertToRequestBody("1"));

        params.put("bp", BaseUtils.convertToRequestBody("1"));
        params.put("dst", BaseUtils.convertToRequestBody("1"));

        params.put("amount", BaseUtils.convertToRequestBody("1"));
        params.put("remark", BaseUtils.convertToRequestBody("1"));

        params.put("number", BaseUtils.convertToRequestBody("1"));
        params.put("qty", BaseUtils.convertToRequestBody("1"));


        params.put("details", BaseUtils.convertToRequestBody("1"));
        params.put("reason", BaseUtils.convertToRequestBody("1"));

        params.put("cat", BaseUtils.convertToRequestBody("1"));
        params.put("item", BaseUtils.convertToRequestBody("1"));
        params.put("client", BaseUtils.convertToRequestBody("1"));


//        file = new File("/storage/emulated/0/image/1519718676333.jpg");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("original",file.getName(), requestBody);
       Loger.e("路径图片 "+file.getName());
        //application/octet-strea

//        final ProgressRequestBody progressFileRequestBody = new ProgressRequestBody(file).create(MediaType.parse("application/octet-stream"), file, uploadProgressListener);
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//        String fileName = android.os.Build.SERIAL + "_" + new Date().getTime() + "_" + file.getName();
//        builder.addFormDataPart("original", fileName, progressFileRequestBody);
//        //  builder.addFormDataPart("enctype", "multipart/form-data");//告诉das服务上传的是文件
//        //  builder.addFormDataPart("userId", VsdApplication.getInstance().getLoginResponseBean().getId());
//        builder.addFormDataPart("start", "12");
//        builder.addFormDataPart("end", "1");
//
//        builder.addFormDataPart("bp", "1");
//        builder.addFormDataPart("dst", "1");
//
////        builder.addFormDataPart("client", "1");//告诉das服务上传的是文件
//        builder.addFormDataPart("amount", "1");
//        builder.addFormDataPart("remark", "1");
//        builder.addFormDataPart("number", "1");
//        builder.addFormDataPart("qty", "1");
//
//        builder.addFormDataPart("details", "1");
//        builder.addFormDataPart("reason", "1");
//        builder.addFormDataPart("cat", "1");
//        builder.addFormDataPart("item", "1");
       // feedBackApi.upload(builder.build());

        return feedBackApi.upload_reim(part,params);
    }




    public Observable<String> uplaodPic(final File file, final UploadProgressListener uploadProgressListener) {
        Retrofit retrofit = builderUploadPicRetrofit();
        FeedBackApi feedBackApi = retrofit.create(FeedBackApi.class);
        //application/octet-stream
        final ProgressRequestBody progressFileRequestBody = new ProgressRequestBody(file).
                create(MediaType.parse("multipart/form-data;charset=UTF-8"), file, uploadProgressListener);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("original", file.getName(), reqFile);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");

//test-----

        String token = "dsdsddadad244";
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("original", file.getName(), requestFile);

        // create a map of data to pass along
        RequestBody tokenBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), token);

        HashMap<String, Object> map = new HashMap<>();
        map.put("start", "2345");
        map.put("end", "2345");

        map.put("bp", "2345");
        map.put("dst", "2345");

        map.put("amount", "2345");
        map.put("remark", "2345");

        map.put("number", "2345");
        map.put("qty", "2345");

        map.put("details", "1");
        map.put("reason", "1");
        map.put("cat","1");
        map.put("item", "1");


//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        String fileName = android.os.Build.SERIAL + "_" + new Date().getTime() + "_" + file.getName();
//        builder.addFormDataPart("original", fileName, progressFileRequestBody);
//        //  builder.addFormDataPart("enctype", "multipart/form-data");//告诉das服务上传的是文件
//        //  builder.addFormDataPart("userId", VsdApplication.getInstance().getLoginResponseBean().getId());
//        builder.addFormDataPart("end","12");
//        builder.addFormDataPart("end","1");
//
//        builder.addFormDataPart("bp","1");
//        builder.addFormDataPart("dst", "1");
//        builder.addFormDataPart("client", "1");//告诉das服务上传的是文件
//        builder.addFormDataPart("amount", "1");
//        builder.addFormDataPart("remark","1");
//        builder.addFormDataPart("number","1");
//        builder.addFormDataPart("qty", "1");
//
//        builder.addFormDataPart("details", "1");
//        builder.addFormDataPart("reason", "1");
//        builder.addFormDataPart("cat","1");
//        builder.addFormDataPart("item", "1");

        //构建body
//        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                .addFormDataPart("name", name)
//                .addFormDataPart("psd", psd)
//                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
//                .build();


        return feedBackApi.upload_reim_pic(filePart, map);
    }

    public Observable<Boolean> replyFeedBack(FeedBackReplyRequestBean replyRequestBean) {
        Retrofit retrofit = builderJsonRetrofit();
        String params = new Gson().toJson(replyRequestBean);
        FeedBackApi feedBackApi = retrofit.create(FeedBackApi.class);
        Observable<String> observable = feedBackApi.replyFeedBack(params);
        Loger.e("replyFeedBack--s=" + params);
        return observable.map(new Function<String, Boolean>() {
            @Override
            public Boolean apply(@NonNull String s) throws Exception {
                Loger.e("replyFeedBack--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                String status = jsonObject.getString(AppConstant.JSON_STATUS);
                if (status.equals("200")) {
                    return true;
                }
                throw new IApiException("replyFeedBack", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

}

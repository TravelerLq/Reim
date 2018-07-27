package com.jci.vsd.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.activity.Reim.ReimPicBean;
import com.jci.vsd.activity.Reim.SubmitApprovalBean;
import com.jci.vsd.bean.enterprise.BudgetBean;
import com.jci.vsd.bean.reim.ApprovalBean;
import com.jci.vsd.bean.reim.IdsBean;
import com.jci.vsd.bean.reim.MyReimDetailBean;
import com.jci.vsd.bean.reim.ReimAddItemBean;
import com.jci.vsd.bean.reim.ReimAddResponseBean;
import com.jci.vsd.bean.reim.ReimDocSubmitBean;
import com.jci.vsd.bean.reim.WaitApprovalDetailAllBean;
import com.jci.vsd.bean.reim.WaitApprovalDetailBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.BudgetApi;
import com.jci.vsd.network.api.ReimApi;
import com.jci.vsd.utils.BaseUtils;
import com.jci.vsd.utils.Loger;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 18/7/11.
 * 报销
 */

public class ReimControl extends BaseControl {


    //add预算

    public Observable<String> submitReim_pic(ReimAddItemBean bean, File file, final UploadProgressListener uploadProgressListener) {

        Retrofit retrofit = builderUploadPicRetrofit();
        ReimApi api = retrofit.create(ReimApi.class);

        Map<String, RequestBody> params = new HashMap<>();
        params.put("start", BaseUtils.convertToRequestBody(bean.getStart()));
        params.put("end", BaseUtils.convertToRequestBody(bean.getEnd()));

        params.put("bp", BaseUtils.convertToRequestBody(bean.getBp()));
        params.put("dst", BaseUtils.convertToRequestBody(bean.getDst()));

        params.put("amount", BaseUtils.convertToRequestBody(bean.getAmount()));
        params.put("remark", BaseUtils.convertToRequestBody(bean.getRemark()));

        params.put("number", BaseUtils.convertToRequestBody(bean.getNumber()));
        params.put("qty", BaseUtils.convertToRequestBody(bean.getQty()));


        params.put("details", BaseUtils.convertToRequestBody(bean.getDetails()));
        params.put("reason", BaseUtils.convertToRequestBody(bean.getReason()));

        params.put("cat", BaseUtils.convertToRequestBody(bean.getCat()));
        params.put("item", BaseUtils.convertToRequestBody(bean.getItem()));
        params.put("client", BaseUtils.convertToRequestBody(bean.getClient()));
        params.put("cer", BaseUtils.convertToRequestBody(bean.getCer()));
        params.put("sign", BaseUtils.convertToRequestBody(bean.getSign()));

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("original", file.getName(), requestBody);
        Loger.e("路径图片 " + file.getName());

        //
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//
//        // MultipartBody.Part is used to send also the actual file name
//        MultipartBody.Part filePart = MultipartBody.Part.createFormData("original", file.getName(), requestFile);
//
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

        return api.upload_reim(part, params);
    }


    //submit reim items 提交报销项 (items amount<5)

    public Observable<ReimAddResponseBean> submitReim(IdsBean bean) {

        Retrofit retrofit = builderJsonRetrofit();
        String paramStr = JSON.toJSONString(bean);
        Loger.e("---params--" + paramStr);

        ReimApi api = retrofit.create(ReimApi.class);
        Observable<Response<String>> observable = api.submitReim(paramStr);
        return observable.map(new Function<Response<String>, ReimAddResponseBean>() {
            @Override
            public ReimAddResponseBean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    ReimAddResponseBean reimAddResponseBean =
                            JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), ReimAddResponseBean.class);
                    if (reimAddResponseBean != null) {
                        return reimAddResponseBean;
                    }


                }
                throw new IApiException("新增预算管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //submit reim doc提交报销单 ()

    public Observable<ReimAddResponseBean> submitReimDoc(ReimDocSubmitBean bean) {

        Retrofit retrofit = builderJsonRetrofit();
        String paramStr = JSON.toJSONString(bean);
        Loger.e("---params--" + paramStr);

        ReimApi api = retrofit.create(ReimApi.class);
        Observable<Response<String>> observable = api.submitReimDoc(paramStr);
        return observable.map(new Function<Response<String>, ReimAddResponseBean>() {
            @Override
            public ReimAddResponseBean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    ReimAddResponseBean reimAddResponseBean =
                            JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), ReimAddResponseBean.class);
                    if (reimAddResponseBean != null) {
                        return reimAddResponseBean;
                    }


                }
                throw new IApiException("提交报销单", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


    public Observable<List<ApprovalBean>> getWaitApprovalData() {

        Retrofit retrofit = builderJsonRetrofit();

        ReimApi api = retrofit.create(ReimApi.class);
        Observable<Response<String>> observable = api.getWaitApprovalData();
        return observable.map(new Function<Response<String>, List<ApprovalBean>>() {
            @Override
            public List<ApprovalBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    JSONObject jsonData = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA));
                    List<ApprovalBean> list = JSON.parseArray(jsonData.getString("forms"), ApprovalBean.class);
                    if (list != null) {
                        return list;
                    }

                }
                throw new IApiException("提交报销单", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //get 获取待审批详情数据
    public Observable<WaitApprovalDetailAllBean> getWaitApprovalDetail(int id) {

        Retrofit retrofit = builderRetrofitWithHeader();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);

        ReimApi api = retrofit.create(ReimApi.class);
        Observable<Response<String>> observable = api.getWaitApprovalDetail(paramMap);
        return observable.map(new Function<Response<String>, WaitApprovalDetailAllBean>() {
            @Override
            public WaitApprovalDetailAllBean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    WaitApprovalDetailAllBean bean = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), WaitApprovalDetailAllBean.class);
                    return bean;

                }
                throw new IApiException("待审批详情", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //get 获取待审批详情的 报销单愿票据照片
    public Observable<ReimPicBean> getReimPic(int id) {

        Retrofit retrofit = builderRetrofitWithHeader();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);

        ReimApi api = retrofit.create(ReimApi.class);
        Observable<Response<String>> observable = api.getOriginalReimPic(paramMap);
        return observable.map(new Function<Response<String>, ReimPicBean>() {
            @Override
            public ReimPicBean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    ReimPicBean bean = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), ReimPicBean.class);
                    if (bean != null) {
                        return bean;
                    }


                }
                throw new IApiException("待审批详情", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


    public Observable<Boolean> submitApproval(SubmitApprovalBean bean) {

        Retrofit retrofit = builderJsonRetrofit();
        String paramStr = JSON.toJSONString(bean);
        Loger.e("---params--" + paramStr);

        ReimApi api = retrofit.create(ReimApi.class);
        Observable<Response<String>> observable = api.submitApproval(paramStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    return true;


                }
                throw new IApiException("提交报销单", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


    //我的报销－－－

    //我的报销单

    public Observable<List<ApprovalBean>> getMyReimData(int type) {

        Retrofit retrofit = builderRetrofitWithHeader();
        ReimApi api = retrofit.create(ReimApi.class);
        Observable<Response<String>> observable = api.getWaitApprovalData();
        return observable.map(new Function<Response<String>, List<ApprovalBean>>() {
            @Override
            public List<ApprovalBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    JSONObject jsonData = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA));
                    List<ApprovalBean> list = JSON.parseArray(jsonData.getString("forms"), ApprovalBean.class);
                    if (list != null) {
                        return list;
                    }

                }
                throw new IApiException("提交报销单", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


    //获取我的报销 －报销单审批详情
    public Observable<MyReimDetailBean> getMyReimDetail( int id) {

        Retrofit retrofit = builderRetrofitWithHeader();
        ReimApi api = retrofit.create(ReimApi.class);
        Observable<Response<String>> observable = api.getWaitApprovalData();
        return observable.map(new Function<Response<String>, MyReimDetailBean>() {
            @Override
            public MyReimDetailBean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    JSONObject jsonData = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA));
//                    List<ApprovalBean> list = JSON.parseArray(jsonData.getString("forms"), ApprovalBean.class);
//                    if (list != null) {
//                        return list;
//                    }
                    return null;

                }
                throw new IApiException("提交报销单", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //删除报销

    public Observable<Boolean> deleteMyReim(int id) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        String paramStr = JSON.toJSONString(paramMap);
        BudgetApi api = retrofit.create(BudgetApi.class);
        Observable<Response<String>> observable = api.deleteBudgetItem(paramStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    return true;
                }
                throw new IApiException("删除（部门）", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


    //更新预算管理

    public Observable<Boolean> updateBudget(int id, Double budget) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        paramMap.put("quota", budget);
        String paramsStr = JSON.toJSONString(paramMap);
        BudgetApi api = retrofit.create(BudgetApi.class);
        Observable<Response<String>> observable = api.updateBudgetItem(paramsStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    return true;
                }
                throw new IApiException("预算管理更新", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //获取 可选择预算部门

    public Observable<List<BudgetBean>> getBudgetDepart() {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paramMap = new HashMap<>();
        //  paramMap.put("id", ajustMembersBean.getId());
//        String paramsStr = JSON.toJSONString(budgetBean);
        BudgetApi api = retrofit.create(BudgetApi.class);
        Observable<Response<String>> observable = api.getBudgetDeparment();
        return observable.map(new Function<Response<String>, List<BudgetBean>>() {
            @Override
            public List<BudgetBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    JSONObject jsonData = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA));
                    List<BudgetBean> list = JSON.parseArray(jsonData.getString("depts"), BudgetBean.class);

                    return list;
                }
                throw new IApiException("预算管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

    //获取 可选择预算科目

    public Observable<List<BudgetBean>> getBudgetCategory() {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paramMap = new HashMap<>();
        //  paramMap.put("id", ajustMembersBean.getId());
//        String paramsStr = JSON.toJSONString(budgetBean);
        BudgetApi api = retrofit.create(BudgetApi.class);
        Observable<Response<String>> observable = api.getBudgetDeparment();
        return observable.map(new Function<Response<String>, List<BudgetBean>>() {
            @Override
            public List<BudgetBean> apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {

                    return null;
                }
                throw new IApiException("新增预算（科目）", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

}

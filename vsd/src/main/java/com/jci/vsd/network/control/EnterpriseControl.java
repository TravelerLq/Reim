package com.jci.vsd.network.control;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.enterprise.EnterpriseBean;
import com.jci.vsd.bean.enterprise.EnterpriseRequestBean;
import com.jci.vsd.bean.enterprise.EnterpriseResponseBean;
import com.jci.vsd.bean.enterprise.GetEnterInfoBean;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.bean.login.LoginResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.EnterpriseApi;
import com.jci.vsd.utils.Loger;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.syan.spark.client.sdk.data.entity.App;
import cn.com.syan.spark.client.sdk.service.AppCaRootService;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Headers;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 18/7/11.
 * 公司管理
 */

public class EnterpriseControl extends BaseControl {

    /**
     * @param requestBean {"name":"南京御安神物联网科技有限公司",
     *                    "nature":"有限责任公司",
     *                    "vatColl":"一般纳税人",
     *                    "incomeTaxColl":"查账征收",
     *                    "taxId":"9132010530261362XN",
     *                    "bank":"农行华荣支行",
     *                    "bankAcct":"10100101040010592",
     *                    "address":"南京玄武区玄武大道699-10号5幢",
     *                    "phone":"025-83377373",
     *                    "invoice":"自开",
     *                    "legal":"时辉",
     *                    "legalIdNumber":"321028197505222615",
     *                    "quota":1000000.00,
     *                    "creator":2
     *                    }
     * @return
     */
    //注册公司（ new company）
    public Observable<EnterpriseResponseBean> registerEnterprise(final EnterpriseRequestBean requestBean) {

        Retrofit retrofit = builderJsonRetrofit();
        Map<String, String> paramsMap = new HashMap<>();
//        {"name":"南京御安神物联网科技有限公司",
//                "nature":"有限责任公司",
//                "vatColl":"一般纳税人",
//                "incomeTaxColl":"查账征收",
//                "taxId":"9132010530261362XN",
//                "bank":"农行华荣支行",
//                "bankAcct":"10100101040010592",
//                "address":"南京玄武区玄武大道699-10号5幢",
//                "phone":"025-83377373",
//                "invoice":"自开",
//                "legal":"时辉",
//                "legalIdNumber":"321028197505222615"
//        }
        paramsMap.put("name",requestBean.getName());
        paramsMap.put("nature", requestBean.getNature());
        paramsMap.put("vatColl",requestBean.getVatColl());
        paramsMap.put("incomeTaxColl", requestBean.getIncomeTaxColl());
        paramsMap.put("taxId", requestBean.getTaxId());
        paramsMap.put("bank",requestBean.getBank());
        paramsMap.put("bankAcct",requestBean.getBankAcct());
        paramsMap.put("address", requestBean.getAddress());

        paramsMap.put("phone",requestBean.getPhone());
        paramsMap.put("invoice",requestBean.getInvoice());
        paramsMap.put("legal", requestBean.getLegal());
        paramsMap.put("legalIdNumber", requestBean.getLegalIdNumber());
        final String jsonString = JSON.toJSONString(paramsMap);
        //  final String jsonString=JSON.toJSONString(paramsMap);
        System.out.println("jsonString:" + jsonString);


        // final String requestStr = JSON.toJSONString(paramsMap);
        EnterpriseApi api = retrofit.create(EnterpriseApi.class);
        Observable<Response<String>> observable = api.registerEnterprise(jsonString);

        return observable.map(new Function<Response<String>, EnterpriseResponseBean>() {
            @Override
            public EnterpriseResponseBean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
//                codeHttp = 401;
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }
                Loger.e("header-authStr" + authStr);
                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                EnterpriseResponseBean responseBean;
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {

                    //  JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    responseBean = JSON.parseObject(jsonObject
                            .getString(AppConstant.JSON_DATA), EnterpriseResponseBean.class);
                    responseBean.setStatus("200");

//                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
//                    loginResponseBean.setList(authorityBeanList);
                    return responseBean;
                }
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 20005) {
                    responseBean = new EnterpriseResponseBean();
                    responseBean.setStatus("20005");
                    return responseBean;
                }

                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });

//        return observable.map(new Function<Response<String>, EnterpriseResponseBean>() {
//            @Override
//            public EnterpriseResponseBean apply(Response<String> stringResponse) throws Exception {
//                String resonseBodyStr = stringResponse.body();
//                Loger.e("--responseBody" + resonseBodyStr);
//                JSONObject jsonObject = JSON.parseObject(jsonString);
//                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
//                EnterpriseResponseBean responseBean;
//                if (code == 200) {
//                    responseBean = JSON.parseObject(
//                            jsonObject.getString(AppConstant.JSON_DATA), EnterpriseResponseBean.class);
//                    responseBean.setStatus("200");
//                    return responseBean;
//                }
//                if (code == 20005) {
//                    responseBean = new EnterpriseResponseBean();
//                    responseBean.setStatus("20005");
//                    return responseBean;
//                }
//                throw new IApiException("新建公司", jsonObject.getString(AppConstant.JSON_MESSAGE));
//            }
//        });

    }

    //更新公司信息

    public Observable<Boolean> updateEnterprise(final EnterpriseRequestBean requestBean) {

        Retrofit retrofit = builderJsonRetrofit();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("name",requestBean.getName());
        paramsMap.put("nature", requestBean.getNature());
        paramsMap.put("vatColl",requestBean.getVatColl());
        paramsMap.put("incomeTaxColl", requestBean.getIncomeTaxColl());
        paramsMap.put("taxId", requestBean.getTaxId());
        paramsMap.put("bank",requestBean.getBank());
        paramsMap.put("bankAcct",requestBean.getBankAcct());
        paramsMap.put("address", requestBean.getAddress());
        paramsMap.put("phone",requestBean.getPhone());
        paramsMap.put("invoice",requestBean.getInvoice());
        paramsMap.put("legal", requestBean.getLegal());
        paramsMap.put("legalIdNumber", requestBean.getLegalIdNumber());
        final String jsonString = JSON.toJSONString(paramsMap);
        //  final String jsonString=JSON.toJSONString(paramsMap);
        System.out.println("jsonString:" + jsonString);
        EnterpriseApi api = retrofit.create(EnterpriseApi.class);
        Observable<Response<String>> observable = api.updateEnterprise(jsonString);

        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }

                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                EnterpriseResponseBean responseBean;
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    return true;
                }


                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });

    }

    //获取公司信息

    public Observable<GetEnterInfoBean> getEnterpeiseInfo() {
        Retrofit retrofit = builderJsonRetrofit();
//        final String jsonString = JSON.toJSONString(requestBean);
//        System.out.println("jsonString:" + jsonString);
        EnterpriseApi api = retrofit.create(EnterpriseApi.class);
        Observable<Response<String>> observable = api.getEnterpriseInfo();
        return observable.map(new Function<Response<String>, GetEnterInfoBean>() {
            @Override
            public GetEnterInfoBean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                int codeHttp = stringResponse.code();
                if (codeHttp == 401) {
                    throw new IApiException("401", "401");
                }

                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                GetEnterInfoBean responseBean;
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {

                    //  JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    responseBean = JSON.parseObject(jsonObject
                            .getString(AppConstant.JSON_DATA), GetEnterInfoBean.class);
                    responseBean.setStatus("200");
                    return responseBean;
                }
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 50001) {
                    responseBean = new GetEnterInfoBean();
                    responseBean.setStatus("2001");
                    return responseBean;
                }

                throw new IApiException("--", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });

    }


}

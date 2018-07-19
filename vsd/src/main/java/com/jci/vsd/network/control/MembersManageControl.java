package com.jci.vsd.network.control;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.enterprise.AjustMembersBean;
import com.jci.vsd.bean.enterprise.EnterpriseBean;
import com.jci.vsd.bean.enterprise.EnterpriseResponseBean;
import com.jci.vsd.bean.enterprise.MembersBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.EnterpriseApi;
import com.jci.vsd.network.api.MembersApi;
import com.jci.vsd.utils.Loger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import okhttp3.Headers;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liqing on 18/7/11.
 * 成员管理
 */

public class MembersManageControl extends BaseControl {


    //成员管理
    public Observable<List<MembersBean>> getMembers() {
        Retrofit retrofit = builderJsonRetrofit();
        MembersApi api = retrofit.create(MembersApi.class);
        Observable<Response<String>> observable = api.getMembers();
        return observable.map(new Function<Response<String>, List<MembersBean>>() {
            @Override
            public List<MembersBean> apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                Loger.e("header-authStr" + authStr);

                Loger.e("stringResponse.body=" + stringResponse.body());
                JSONObject jsonObject = JSON.parseObject(stringResponse.body());
                if (jsonObject.getIntValue(AppConstant.JSON_CODE) == 200) {
                    JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    List<MembersBean> membersBeanList = JSON.parseArray(dataObj.getString("vos"), MembersBean.class);

//                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
//                    loginResponseBean.setList(authorityBeanList);
                    return membersBeanList;
                }

                throw new IApiException("", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    //删除成员

    public Observable<Boolean> deleteMember(MembersBean membersBean) {
        Retrofit retrofit = builderJsonRetrofit();
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("id", membersBean.getId());
        String paramStr = JSON.toJSONString(membersBean);
        MembersApi api = retrofit.create(MembersApi.class);
        Observable<Response<String>> observable = api.deleteMember(paramStr);
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
                throw new IApiException("成员管理", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }


    //调入其他部门

    public Observable<Boolean> ajustMember(AjustMembersBean ajustMembersBean) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", ajustMembersBean.getId());
        String paramsStr = JSON.toJSONString(ajustMembersBean);
        MembersApi api = retrofit.create(MembersApi.class);
        Observable<Response<String>> observable = api.ajust(paramsStr);
        return observable.map(new Function<Response<String>, Boolean>() {
            @Override
            public Boolean apply(Response<String> stringResponse) throws Exception {
                String responseStr = stringResponse.body();
                JSONObject jsonObject = JSON.parseObject(responseStr);
                int code = jsonObject.getIntValue(AppConstant.JSON_CODE);
                if (code == 200) {
                    return true;
                }
                throw new IApiException("登陆", jsonObject.getString(AppConstant.JSON_MESSAGE));


            }


        });
    }

}

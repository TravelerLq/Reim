package com.jci.vsd.network.control;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.login.BindCardRequestBean;
import com.jci.vsd.bean.login.HomeAuthorityBean;
import com.jci.vsd.bean.login.LoginRequestbean;
import com.jci.vsd.bean.login.LoginResponseBean;
import com.jci.vsd.bean.login.PersonalInfoRequestBean;
import com.jci.vsd.bean.login.PersonalInformationResponseBean;
import com.jci.vsd.bean.login.UpdatePwdRequestBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.LoginApi;
import com.jci.vsd.utils.Loger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Headers;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Yso on 2017/11/13.
 */

public class LoginApiControl extends BaseControl {

    public Observable<LoginResponseBean> login(LoginRequestbean loginRequestbean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("userName", loginRequestbean.getUserName());
        parmsMap.put("password", loginRequestbean.getPassword());
        //  parmsMap.put("type",loginRequestbean.getType());
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<String> observable = loginApi.login(parmsMap);
        Loger.i("login = " + parmsMap);
        return observable.map(new Function<String, LoginResponseBean>() {
            @Override
            public LoginResponseBean apply(@NonNull String s) throws Exception {
                Loger.e("login = " + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if ("200".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    LoginResponseBean loginResponseBean = JSON.parseObject(dataObj.getString("UserInfo"), LoginResponseBean.class);
                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
                    loginResponseBean.setList(authorityBeanList);
                    return loginResponseBean;
                }

                throw new IApiException("登陆", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }


    //


    public Observable<LoginResponseBean> loginNew(String name, String psw) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("telephoneNumber", name);
        parmsMap.put("password", psw);
        // String json = JSONObject.toJSONString(requestBean);
        String jsonRequest = JSONObject.toJSONString(parmsMap);

        //  parmsMap.put("type",loginRequestbean.getType());
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<String> observable = loginApi.newLogin(jsonRequest);

        // Loger.i("login = "+parmsMap);
        return observable.map(new Function<String, LoginResponseBean>() {
            @Override
            public LoginResponseBean apply(@NonNull String s) throws Exception {
                Loger.e("loginResponseStr = " + s);

                JSONObject jsonObject = JSON.parseObject(s);


                if ("200".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    LoginResponseBean loginResponseBean = JSON.parseObject(dataObj.getString("UserInfo"), LoginResponseBean.class);
                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
                    loginResponseBean.setList(authorityBeanList);
                    return loginResponseBean;
                }

                throw new IApiException("登陆", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    public Observable<LoginResponseBean> loginResponse(String name, String psw) {
        Retrofit retrofit = builderJsonRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("telephoneNumber", name);
        parmsMap.put("password", psw);
        // String json = JSONObject.toJSONString(requestBean);
        String jsonRequest = JSONObject.toJSONString(parmsMap);

        //  parmsMap.put("type",loginRequestbean.getType());
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<Response<String>> observable = loginApi.loginResponse(jsonRequest);
        return observable.map(new Function<Response<String>, LoginResponseBean>() {
            @Override
            public LoginResponseBean apply(Response<String> stringResponse) throws Exception {
                Headers headers = stringResponse.headers();
                String authStr = headers.get("Authorization");
                Loger.e("header-auth" + authStr);


                JSONObject jsonObject = JSON.parseObject(stringResponse.body());

                if ("200".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    JSONObject dataObj = jsonObject.getJSONObject(AppConstant.JSON_DATA);
                    LoginResponseBean loginResponseBean = JSON.parseObject(dataObj.getString("UserInfo"), LoginResponseBean.class);
                    List<HomeAuthorityBean> authorityBeanList = JSON.parseArray(dataObj.getString("Authority"), HomeAuthorityBean.class);
                    loginResponseBean.setList(authorityBeanList);
                    return loginResponseBean;
                }

                throw new IApiException("登陆", jsonObject.getString(AppConstant.JSON_MESSAGE));

            }
        });

    }


    public Observable<Boolean> bindCard(BindCardRequestBean bindCardRequestBean) {
        SharedPreferences prefs;
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("userId", bindCardRequestBean.getUserId());
        parmsMap.put("card", bindCardRequestBean.getCardNo());
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<String> observable = loginApi.bindCard(parmsMap);
        return observable.map(new Function<String, Boolean>() {
            @Override
            public Boolean apply(@NonNull String s) throws Exception {
                JSONObject jsonObject = JSON.parseObject(s);
                if ("200".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    return true;
                }

                throw new IApiException("登陆", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

    public Observable<PersonalInformationResponseBean> getPersionInformation(PersonalInfoRequestBean personalInfoRequestBean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("userId", personalInfoRequestBean.getUserId());
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<String> observable = loginApi.personalInformation(parmsMap);
        Loger.i("getPersionInformation = " + parmsMap.toString());
        return observable.map(new Function<String, PersonalInformationResponseBean>() {
            @Override
            public PersonalInformationResponseBean apply(@NonNull String s) throws Exception {
                Loger.i("getPersionInformation = " + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if ("200".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    PersonalInformationResponseBean personalInformationResponseBean = JSON.parseObject(jsonObject.getString(AppConstant.JSON_DATA), PersonalInformationResponseBean.class);
                    return personalInformationResponseBean;
                }
                throw new IApiException("个人信息", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }


    public Observable<Boolean> updatePersonlPwd(UpdatePwdRequestBean personalInfoRequestBean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("userId", personalInfoRequestBean.getUserId());
        parmsMap.put("oldPassword", personalInfoRequestBean.getOldPassword());
        parmsMap.put("newPassword", personalInfoRequestBean.getNewPassword());
        LoginApi loginApi = retrofit.create(LoginApi.class);
        Observable<String> observable = loginApi.updatePassword(parmsMap);
        Loger.i("updatePersonlPwd = " + parmsMap.toString());
        return observable.map(new Function<String, Boolean>() {
            @Override
            public Boolean apply(@NonNull String s) throws Exception {
                Loger.i("updatePersonlPwd == " + s.toString());
                JSONObject jsonObject = JSON.parseObject(s);
                if ("200".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    return true;
                }
                throw new IApiException("修改密码", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }
}

package com.jci.vsd.network.control;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jci.vsd.bean.notice.NoticeRequestBean;
import com.jci.vsd.bean.notice.UnreadNoticeBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.exception.IApiException;
import com.jci.vsd.network.api.NoticeCenterApi;
import com.jci.vsd.utils.Loger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;

/**
 * Created by liqing on 17/12/6.
 */

public class NoticeCenterControl extends BaseControl {
    public Observable<List<UnreadNoticeBean>> getNotice(NoticeRequestBean requestBean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("Receiver", requestBean.getReceiver());
        if(!TextUtils.isEmpty(requestBean.getStatus())) {
            parmsMap.put("Status", requestBean.getStatus());
        }
        parmsMap.put("PageSize", requestBean.getPageSize());
        parmsMap.put("PageIndex",requestBean.getPageIndex());
        NoticeCenterApi api = retrofit.create(NoticeCenterApi.class);
        Observable<String> observable = api.getNotice(parmsMap);
        Loger.i(" parmsMap= " + parmsMap);
        return observable.map(new Function<String, List<UnreadNoticeBean>>() {
            @Override
            public List<UnreadNoticeBean> apply(String s) throws Exception {
                Loger.e("notice--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if (!"500".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    List<UnreadNoticeBean> beanList = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), UnreadNoticeBean.class);
                    return beanList;
                }
                throw new IApiException("notice", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });

    }

    /**
     * 到料提醒
     */
    public Observable<List<UnreadNoticeBean>> getMessage(NoticeRequestBean requestBean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("Receiver", requestBean.getReceiver());
        if (!TextUtils.isEmpty(requestBean.getStatus())) {
            parmsMap.put("Status", requestBean.getStatus());
        }
        parmsMap.put("PageSize", requestBean.getPageSize());
        parmsMap.put("PageIndex", requestBean.getPageIndex());
        parmsMap.put("msgType", "msg");
        NoticeCenterApi api = retrofit.create(NoticeCenterApi.class);
        Observable<String> observable = api.getNotice(parmsMap);
        Loger.i(" parmsMap= " + parmsMap);
        return observable.map(new Function<String, List<UnreadNoticeBean>>() {
            @Override
            public List<UnreadNoticeBean> apply(String s) throws Exception {
                Loger.e("notice--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if (!"500".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    List<UnreadNoticeBean> beanList = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), UnreadNoticeBean.class);
                    return beanList;
                }
                throw new IApiException("notice", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });

    }


    //setRead
    public Observable<Boolean> setNoticeRead(String id){
        Retrofit retrofit = builderRetrofit();
        Map<String,String> parmsMap = new HashMap<>();
        parmsMap.put("msgid",id);
        NoticeCenterApi api = retrofit.create(NoticeCenterApi.class);
        Observable<String> observable = api.setNoticeRead(parmsMap);
        return observable.map(new Function<String, Boolean>() {
            @Override
            public Boolean apply(@NonNull String s) throws Exception {
                JSONObject jsonObject = JSON.parseObject(s);
                if("200".equals(jsonObject.getString(AppConstant.JSON_STATUS))){
                    return true;
                }
                throw new IApiException("read",jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });
    }

// 消息推送的获取

    public Observable<List<UnreadNoticeBean>> getPushNotice(NoticeRequestBean requestBean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("Receiver", requestBean.getReceiver());
        NoticeCenterApi api = retrofit.create(NoticeCenterApi.class);
        Observable<String> observable = api.getPushNotice(parmsMap);
        Loger.i(" parmsMap= " + parmsMap);
        return observable.map(new Function<String, List<UnreadNoticeBean>>() {
            @Override
            public List<UnreadNoticeBean> apply(String s) throws Exception {
                Loger.e("pushnotice--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if (!"500".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    List<UnreadNoticeBean> beanList = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), UnreadNoticeBean.class);
                    return beanList;
                }
                throw new IApiException("pushnotice", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });

    }

    /**
     * 到料提醒未读消息
     *
     * @param requestBean
     * @return
     */
    public Observable<List<UnreadNoticeBean>> getUnreadMsg(NoticeRequestBean requestBean) {
        Retrofit retrofit = builderRetrofit();
        Map<String, String> parmsMap = new HashMap<>();
        parmsMap.put("Receiver", requestBean.getReceiver());
        parmsMap.put("msgType", "msg");
        NoticeCenterApi api = retrofit.create(NoticeCenterApi.class);
        Observable<String> observable = api.getPushNotice(parmsMap);
        Loger.i(" parmsMap= " + parmsMap);
        return observable.map(new Function<String, List<UnreadNoticeBean>>() {
            @Override
            public List<UnreadNoticeBean> apply(String s) throws Exception {
                Loger.e("pushnotice--s=" + s);
                JSONObject jsonObject = JSON.parseObject(s);
                if (!"500".equals(jsonObject.getString(AppConstant.JSON_STATUS))) {
                    List<UnreadNoticeBean> beanList = JSON.parseArray(jsonObject.getString(AppConstant.JSON_DATA), UnreadNoticeBean.class);
                    return beanList;
                }
                throw new IApiException("pushnotice", jsonObject.getString(AppConstant.JSON_MESSAGE));
            }
        });

    }
}

package com.jci.vsd.network.control;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.ProgressInterceptor;
import com.jci.vsd.bean.download.ProgressResponseBody;
import com.jci.vsd.bean.login.LoginResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.network.factory.StringConverterFactory;
import com.jci.vsd.network.factory.StringConverterJsonFactory;
import com.jci.vsd.network.factory.StringConverterUploadFactory;
import com.jci.vsd.utils.Loger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yso on 2017/11/13.
 */

public class BaseControl {
    private static final int SOCKET_TIMEOUT = 60;
    private static final int CONNECT_TIMEOUT = 60;

    protected Retrofit builderRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        LoginResponseBean loginResponseBean = VsdApplication.getInstance().getLoginResponseBean();
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("userId", loginResponseBean == null ? "" : loginResponseBean.getId())
                                .build();
                        return chain.proceed(request);
                    }
                });//设置写入超时时间
        OkHttpClient client = builder.build();
        Loger.i("=======AppConstant.getURL()=========" + AppConstant.getURL());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.getURL())
                //这个方法必须有,这样才能返回含有数据的实体类
                .addConverterFactory(StringConverterFactory.create())
                //要使用retroift和rexjava配合使用这个方法必须有,不然会报Unable to create call adapter
                // for rx.Observable<com.ethanco.retrofit2_0test.HomeTopBean>异常
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)//整合ok
                .build();
        return retrofit;
    }


    protected  Retrofit buildRetrofit(){

        HttpLoggingInterceptor interceptor =new HttpLoggingInterceptor();


         return  null;
    }

    protected Retrofit builderJsonRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("User-Agent", "ios6s")
                                // .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1Mjc3MzY3MzA0MjYsInVpZCI6IjE1MiIsImlhdCI6MTUyNzczNjY3MDQyNn0.Yb6SyjzKCixg2CIVYt7VtAnMsFcB_hDmzalHmxjO0cI")
                                .addHeader("Authorization", "")
                                .build();


                        return chain.proceed(request);
                    }

                })
                .addInterceptor(interceptor);//设置写入超时时间


        OkHttpClient client = builder.build();
        Loger.i("=======AppConstant.getURL()=========" + AppConstant.getURL());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.getURL())
                //这个方法必须有,这样才能返回含有数据的实体类
                .addConverterFactory(StringConverterJsonFactory.create())
                //要使用retroift和rexjava配合使用这个方法必须有,不然会报Unable to create call adapter
                // for rx.Observable<com.ethanco.retrofit2_0test.HomeTopBean>异常
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)//整合ok
                .build();

        return retrofit;
    }

    protected Retrofit buildDownloadRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new ProgressInterceptor())
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://10.0.2.2:8080/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }


    protected Retrofit buildGetTokenRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS);//设置写入超时时间
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstant.OUTSIZE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .build();
        return retrofit;
    }

    protected Retrofit buildUploadRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS);//设置写入超时时间
        OkHttpClient client = builder.build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://139.224.15.28:806/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(StringConverterFactory.create())
                .build();
        return retrofit;
    }


    protected Retrofit builderUploadPicRetrofit() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .build();
                        return chain.proceed(request);
                    }
                });//设置写入超时时间
        OkHttpClient client = builder.build();
        Loger.i("=======AppConstant.getURL()=========" + AppConstant.getURL());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.getURL())
                //这个方法必须有,这样才能返回含有数据的实体类
                .addConverterFactory(StringConverterUploadFactory.create())
                //要使用retroift和rexjava配合使用这个方法必须有,不然会报Unable to create call adapter
                // for rx.Observable<com.ethanco.retrofit2_0test.HomeTopBean>异常
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)//整合ok
                .build();
        return retrofit;
    }
}

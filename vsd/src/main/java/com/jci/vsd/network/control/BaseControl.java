package com.jci.vsd.network.control;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jci.vsd.application.VsdApplication;
import com.jci.vsd.bean.download.ProgressInterceptor;
import com.jci.vsd.bean.download.ProgressResponseBody;
import com.jci.vsd.bean.login.LoginResponseBean;
import com.jci.vsd.constant.AppConstant;
import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.interceptor.LoggingInterceptor;
import com.jci.vsd.network.factory.StringConverterFactory;
import com.jci.vsd.network.factory.StringConverterJsonFactory;
import com.jci.vsd.network.factory.StringConverterUploadFactory;
import com.jci.vsd.utils.Loger;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.com.syan.spark.client.sdk.service.AppCaRootService;
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


    final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

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

    protected Retrofit builderRetrofitWithHeader() {
        final String requestAuth = MySpEdit.getInstance().getAuthor();
        Loger.e("---RequestAuth---" + requestAuth);
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
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
                                .addHeader("Authorization", requestAuth)
                                .addHeader("Version1", "1")
                                .build();


                        return chain.proceed(request);
                    }

                })
                .addInterceptor(interceptor);//设置写入超时时间
        //     .addInterceptor(loggingInterceptor);


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

    //Register an application interceptor by calling addInterceptor() on OkHttpClient.Builder

    protected Retrofit builderJsonRetrofit() {
        final String requestAuth = MySpEdit.getInstance().getAuthor();
        Loger.e("---RequestAuth---" + requestAuth);
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
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
                                .addHeader("Authorization", requestAuth)
                                .addHeader("Version1", "1")
                                .build();


                        return chain.proceed(request);
                    }

                })
                .addInterceptor(interceptor);//设置写入超时时间
        //     .addInterceptor(loggingInterceptor);


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

    //ssl
    protected Retrofit builderJsonRetrofitWithHttps() {
        final String requestAuth = MySpEdit.getInstance().getAuthor();
        Loger.e("---RequestAuth---" + requestAuth);
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
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
                                .addHeader("Authorization", requestAuth)
                                .addHeader("Version1", "1")
                                .build();


                        return chain.proceed(request);
                    }

                })
                .addInterceptor(interceptor)
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                });//设置写入超时时间
        //     .addInterceptor(loggingInterceptor);
        //ssl

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
                .baseUrl(AppConstant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    //下载文件以及图片
    protected Retrofit buildDownloadWithTokenRetrofit() {
        final String requestAuth = MySpEdit.getInstance().getAuthor();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()

                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("User-Agent", "ios6s")
                                // .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1Mjc3MzY3MzA0MjYsInVpZCI6IjE1MiIsImlhdCI6MTUyNzczNjY3MDQyNn0.Yb6SyjzKCixg2CIVYt7VtAnMsFcB_hDmzalHmxjO0cI")
                                .addHeader("Authorization", requestAuth)
                                .addHeader("Version1", "1")
                                .build();


                        return chain.proceed(request);
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(AppConstant.BASE_URL)
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
        final String requestAuth = MySpEdit.getInstance().getAuthor();
        Loger.e("---RequestAuth---" + requestAuth);
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder()
                .connectTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(SOCKET_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
//                .addHeader("Connection", "close")
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("User-Agent", "ios6s")
                                // .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1Mjc3MzY3MzA0MjYsInVpZCI6IjE1MiIsImlhdCI6MTUyNzczNjY3MDQyNn0.Yb6SyjzKCixg2CIVYt7VtAnMsFcB_hDmzalHmxjO0cI")
                                .addHeader("Authorization", requestAuth)
                                .addHeader("Version1", "1")
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
                .addConverterFactory(StringConverterUploadFactory.create())
                //要使用retroift和rexjava配合使用这个方法必须有,不然会报Unable to create call adapter
                // for rx.Observable<com.ethanco.retrofit2_0test.HomeTopBean>异常
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)//整合ok
                .build();
        return retrofit;
    }
}

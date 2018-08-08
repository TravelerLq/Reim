package com.jci.vsd.bean.download;

import com.jci.vsd.constant.MySpEdit;
import com.jci.vsd.utils.Loger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by air on 2016/12/5.
 */
public class ProgressInterceptorFile implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String requestAuth = MySpEdit.getInstance().getAuthor();
        //request
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("User-Agent", "ios6s")
                // .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHQiOjE1Mjc3MzY3MzA0MjYsInVpZCI6IjE1MiIsImlhdCI6MTUyNzczNjY3MDQyNn0.Yb6SyjzKCixg2CIVYt7VtAnMsFcB_hDmzalHmxjO0cI")
                .addHeader("Authorization", requestAuth)
                .addHeader("Version1", "1")
                .build();

        Response originalResponse = chain.proceed(request);


        return originalResponse.newBuilder()
                .body(new ProgressResponseBody(originalResponse.body()))
                .build();
    }

//    Request request = chain.request();
//    request = request.newBuilder()
//            .addHeader("appid", "hello")
//                .addHeader("deviceplatform", "android")
//                .removeHeader("User-Agent")
//                .addHeader("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:38.0) Gecko/20100101 Firefox/38.0")
//                .build();
//    Response response = chain.proceed(request);
//        return response;


}

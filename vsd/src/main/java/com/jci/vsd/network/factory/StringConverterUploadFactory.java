package com.jci.vsd.network.factory;

import com.jci.vsd.utils.Loger;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by liqing on 17/11/24.
 */

public class StringConverterUploadFactory extends Converter.Factory {
    public static StringConverterUploadFactory create() {
        return new StringConverterUploadFactory();
    }

    public StringConverterUploadFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new StringResponseUploadBodyConverter();
    }


    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return new StringRequestUploadBodyConverter();
    }
}

class StringResponseUploadBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        String result = value.string();
        Loger.i("StringResponseBodyConverter = "+result);
        return result;
    }
}

class StringRequestUploadBodyConverter  implements Converter<String, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    StringRequestUploadBodyConverter() {
    }

    @Override public RequestBody convert(String value) throws IOException {
      return RequestBody.create(MediaType.parse("multipart/form-data"), value);
        //return RequestBody.create(MediaType.parse("image/png"), value);
    }
}









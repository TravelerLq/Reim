package com.jci.vsd.network.factory;

import android.util.Log;

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
 * Created by liqing on 17/11/22.
 */

public class StringConverterJsonFactory extends Converter.Factory {

    public static StringConverterJsonFactory create() {
        return new StringConverterJsonFactory();
    }

    private StringConverterJsonFactory() {

    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {

        return new StringResponseJsonBodyConverter();
    }

    //    @Override
//    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
//                                                            Retrofit retrofit) {
//        return new StringRequestJsonBodyConverter();
//    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {


        StringRequestJsonBodyConverter requestJsonBodyConverter = new StringRequestJsonBodyConverter();
        Loger.e("requestJsonBodyConverter-" + requestJsonBodyConverter.toString());

        return requestJsonBodyConverter;
    }
}


class StringResponseJsonBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        String result = value.string();

        Loger.e("StringResponseBodyConverter = " + result);
        return result;
    }
}

class StringRequestJsonBodyConverter implements Converter<String, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    StringRequestJsonBodyConverter() {
    }

    @Override
    public RequestBody convert(String value) throws IOException {


        return RequestBody.create(MediaType.parse("application/json"), value);
    }
}

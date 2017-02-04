package com.lihonghui.vinci.data.remote;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yq05481 on 2016/11/29.
 */

public class RetrofitManager {
    public static final String API_BASE_URL = "https://api.dribbble.com/v1/";
    public static final String BASE_URL = "https://dribbble.com/";

    public static Retrofit createRetrofit(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).addInterceptor(new LoggingInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit;
    }

    private static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            // LogUtil.printLog(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
            //LogUtil.printLog("request body: "+request.body().toString());

            Response response = chain.proceed(request);

            if (request.cacheControl() != null && !"".equals(request.cacheControl().toString())) {
                String cacheControl = request.cacheControl().toString();
                // LogUtil.printLog("cacheControl:"+cacheControl);
            } else {
                // LogUtil.printLog("cacheControl not config");
            }

            long t2 = System.nanoTime();
//            LogUtil.printLog(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));

            if (response.body() != null) {
                okhttp3.MediaType mediaType = response.body().contentType();
                String content = response.body().string();

               // LogUtil.printLog("body:" + content);

                return response.newBuilder()
                        .body(okhttp3.ResponseBody.create(mediaType, content))
                        .build();
            }

            return response;
        }
    }
}

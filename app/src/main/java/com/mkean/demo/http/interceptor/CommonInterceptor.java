package com.mkean.demo.http.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/6.
 */
public class CommonInterceptor implements Interceptor {
    private final String mApiKey;
    private final String mApiSecret;
    private final String versionCode;
    private final String versionName;

    public CommonInterceptor(String apiKey, String apiSecret,String versionName,String versionCode) {
        mApiKey = apiKey;
        mApiSecret = apiSecret;
        this.versionCode = versionCode;
        this.versionName = versionName;

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter(versionName,versionCode)
                .addQueryParameter(mApiKey, mApiSecret);

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}

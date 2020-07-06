package com.mkean.demo.http.manager;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit mRetrofit;
    private static RetrofitManager mRetrofitManager;

    private RetrofitManager(String baseUrl) {
        mRetrofit = new Retrofit.Builder()
                .client(OkHttpUtils.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public static RetrofitManager getInstance(String baseUrl) {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                mRetrofitManager = new RetrofitManager(baseUrl);
            }
        }
        return mRetrofitManager;
    }

    public static Retrofit getRetrofit() {
        return mRetrofit;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }
}

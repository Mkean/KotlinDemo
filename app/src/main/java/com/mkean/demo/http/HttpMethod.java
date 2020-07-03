package com.mkean.demo.http;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.bidanet.android.common.utils.http.Cookies.CookiesManager;
import com.mkean.demo.app.BaseApplication;
import com.mkean.demo.app.Constants;
import com.mkean.demo.http.interceptor.CommonInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by Administrator on 2016/ic_yello_ball/23.
 */
public class HttpMethod {
    //        public static final String BASEHTTP = "http://life.yfcanshi.com/api/v2/";
    public static final String BASEHTTP = BaseApplication.baseurlGCX;
//        public static final String BASEHTTP = "http://118.178.95.214:10999/api/v2/";
//        public static final String BASEHTTP = "http://gucxsql.free.ngrok.cc/api/v2/";
//        public static final String BASEHTTP = "http://192.168.0.125:8080/api/v2/";
//        public static final String BASEHTTP = "http://192.168.0.122:8080/api/v2/";
//    public static final String BASEHTTP = "http://192.168.100.239:8060/api/v2/";
//    public static final String BASEHTTP = "http://10.110.111.123:8081/api/";
//    public static final String BASEHTTP = "http://10.110.111.232:8080/api/";
//    public static final String BASEHTTP = "http://118.178.226.161/api/v2/"; //第一道
//    public static final String BASEHTTP = "http://118.178.227.25/api/v2/";     //第二道
//    public static final String BASEHTTP = "http://192.168.2.15:8080/api/";
//    public static final String BASEHTTP = "http://192.168.2.2:8080/api/";
//    public static final String BASEHTTP = "http://api.yingegou.com/api/v2/";
//    public static final String BASEHTTP = "http://ygg.yingegou.com/api/";
//    public static final String BASEHTTP = "http://114.215.187.134:8888/api/v2/";
//    public static final String BASEHTTP = "http://debug.yingegou.cn:8080/api/";
//    public static final String BASEHTTP = "http://192.168.2.45:8080/api/";
//    public static final String BASEHTTP = "http://192.168.2.33:8060/api/";
//    public static final String BASEHTTP = "http://10.110.111.238:8060/api/";
//    public static final String BASEHTTP = "http://10.110.111.104:8060/api/";
//    public static final String BASEHTTP = "http://10.110.111.65:8060/api/";
//    public static final String BASEHTTP = "http://10.110.111.59:8080/api/,,";
//    public static final String BASEHTTP = "http://10.110.111.65:8060/api/";

    private static HttpMethod INSTANCE = null;

    private Retrofit retrofit;
    private Context context;

    private HttpMethod(Context context) {
        this.context = context;
        String versionCode = "";

        try {
            versionCode = String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        CommonInterceptor commonInterceptor = new CommonInterceptor("from_client", "com.yingegou.android", Constants.REQUEST_VERSION, versionCode);

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new CookiesManager(context))
                .addInterceptor(commonInterceptor)
                //自定义连接时间
                .connectTimeout(30, TimeUnit.SECONDS)
                // 读取超时时间
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


        //默认连接时间
//        client.connectTimeoutMillis();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASEHTTP)
                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

    }

    public static HttpMethod getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new HttpMethod(context);
        }
        return INSTANCE;
    }

    public <T> T getServices(Class<T> T) {
//        T t = retrofit.create(T);
        T t = com.bidanet.android.common.utils.http.HttpMethod.getHttp().create(T);
        return t;
    }

    public <T> T getServicesNoToken(Class<T> T) {
        T t = retrofit.create(T);
        return t;
    }
}

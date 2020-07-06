package com.mkean.demo.app;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bidanet.android.common.CommonConfig;
import com.bidanet.android.common.utils.http.ApiResultHandler;
import com.bidanet.android.common.utils.http.HttpMethod;
import com.bidanet.android.common.utils.http.api.ApiException;
import com.bidanet.android.common.utils.http.api.NoLoginException;
import com.mkean.demo.BuildConfig;
import com.mkean.demo.activity.BaseActivity;
import com.mkean.demo.entity.MyApiResult;
import com.mkean.demo.entity.event.AppBackEvent;
import com.mkean.demo.entity.event.AppFrontEvent;
import com.mkean.demo.view.smartRefresh.CommonRefreshBeeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.jessyan.autosize.AutoSize;

public class BaseApplication extends Application {

    public static BaseApplication app;

    public final static String baseurlH5 = "http://h5-test.canslife.com/";

    public final static String baseurlFYJ = "http://zmnjz.bidanet.com/api/v2/";

    public final static String baseurlGCX = "http://118.178.95.214:10999/api/v2/";

    private static LinkedList<Activity> store = new LinkedList<>();

    private NetWorkStatusReceiver mNetworkStatusReceiver; // 用于监听网络改变

    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static final String VERSION_CODE = "1.0";

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        AutoSize.checkAndInit(this);
        initNetwork();

        initSmartRefresh();
        initARouter();
        initX5(this);
        registerActivityLifecycleCallbacks(new MonitorActivityStatusCallback());
    }

    private void initSmartRefresh() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new CommonRefreshBeeHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
    }

    private void initNetwork() {
        CommonConfig.http(baseurlGCX, this).setApiResultHandler(new ApiResultHandler<MyApiResult>() {

            @Override
            protected Object handler(MyApiResult o) {
                int statusCode = o.getStatusCode();
                switch (statusCode) {
                    case 200:
                        return o.getData();
                    case 300:
                        throw new ApiException(o.getMessage());
                    case 301:
                        throw new NoLoginException("未登录");
                }

                int status = o.getStatus();
                switch (status) {
                    case 401:
                        throw new NoLoginException("未登录");
                    case 403:
                    case 404:
                    case 405:
                        throw new ApiException(o.getMessage());
                    case 422:
                        String msg = "";
                        List errors = o.getErrors();
                        if (errors != null && errors.size() != 0) {
                            Map map = (Map) errors.get(0);
                            String message = (String) map.get("message");
                            msg = message;
                        }
                        throw new ApiException(msg);
                    case 500:
                        throw new NoLoginException("系统错误");
                    default:
                        return o.getData();
                }
            }
        });

        HttpMethod.urlParams = new HashMap<>();
        HttpMethod.formParams = new HashMap<>();
        HttpMethod.headers = new HashMap<>();
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog(); // 打开日志
            ARouter.openDebug(); // 开启调试模式（如果在InstanceRun模式下运行，必须开启调试模式！线上版本需要关闭，否则有安全风险）
        }
        ARouter.init(this);
    }

    private void initX5(Context context) {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean b) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };

        // 允许使用流量下载
        QbSdk.setDownloadWithoutWifi(true);
        // x5内核初始化接口
        QbSdk.initX5Environment(context, cb);
        // 下载相关结果
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {

            }

            @Override
            public void onInstallFinish(int i) {

            }

            @Override
            public void onDownloadProgress(int i) {

            }
        });
    }

    private class MonitorActivityStatusCallback implements ActivityLifecycleCallbacks {
        // 打开的Activity数量统计
        private int activityStartCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            if (store.size() == 0 && mNetworkStatusReceiver == null) {
                mNetworkStatusReceiver = new NetWorkStatusReceiver();
            }
            store.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;
            //数值从0变到1说明是从后台切到前台
            if (activityStartCount == 1) {
                EventBus.getDefault().post(new AppFrontEvent());
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityStartCount--;
            //数值从1到0说明是从前台切到后台
            if (activityStartCount == 0) {
                EventBus.getDefault().post(new AppBackEvent());
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            store.remove(activity);
            if (store.size() == 0 && mNetworkStatusReceiver != null) {
                try {
                    mNetworkStatusReceiver.unregister();
                    mNetworkStatusReceiver = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 监听联网状态，可在联网恢复后自动加载数据
     */
    public static class NetWorkStatusReceiver extends BroadcastReceiver {

        public NetWorkStatusReceiver() {
            register();
        }

        public void unregister() {
            app.unregisterReceiver(this);
        }

        private void register() {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            app.registerReceiver(this, filter);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected() && !store.isEmpty()) {
                for (Activity activity : store) {
                    if (activity instanceof BaseActivity) {
                        ((BaseActivity) activity).onNetWorkConnectedCore(info.getType());
                    }
                }
            }
        }
    }

    /**
     * 在主线程延迟执行
     *
     * @param runnable
     * @param delayMillis 毫秒
     */
    public static void runOnUiThreadDelayed(Runnable runnable, long delayMillis) {
        mHandler.postDelayed(runnable, delayMillis);
    }
}

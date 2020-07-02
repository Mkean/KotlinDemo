package com.mkean.demo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;
import com.mkean.demo.fragment.BaseFragment;
import com.mkean.demo.manager.SkipActivityLifeStack;
import com.mkean.demo.utils.statusbar.StatusBarCompat;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends RxFragmentActivity {

    protected final String TAG = this.getClass().getSimpleName();

    protected Context context;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null) {
            onPrepareIntent(getIntent());
        }

        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }

        context = this;
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        if (registerEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        SkipActivityLifeStack.getInstance().pushActivity(this);

        onViewCreate();
    }

    protected void onViewCreate() {
        StatusBarCompat.setStatusBarTranslucent(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarCompat.setLightStatusBar(this, true);
            StatusBarCompat.setStatusBarColorFullScreen(this, Color.TRANSPARENT);
        } else {
            StatusBarCompat.setStatusBarColorFullScreen(this, Color.BLACK);
        }
    }

    protected abstract int getLayoutId();

    /**
     * intent 下发，顺序在 setContentView 之前
     *
     * @param intent
     */
    protected void onPrepareIntent(Intent intent) {
    }

    @Override
    protected void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (registerEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
        SkipActivityLifeStack.getInstance().popActivity(this);
    }

    /**
     * 处理网络连接，广播分发
     *
     * @param type
     */
    public void onNetWorkConnectedCore(int type) {
        onNetworkConnected(type);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment frag : fragments) {
                if (frag != null && frag instanceof BaseFragment) {
                    ((BaseFragment) frag).onNetworkConnected(type);
                }
            }
        }
    }

    /**
     * 每当网络连接后,回调到这里
     *
     * @param type 网络类型 one of {@link ConnectivityManager#TYPE_MOBILE}, {@link
     *             ConnectivityManager#TYPE_WIFI}, {@link ConnectivityManager#TYPE_WIMAX}, {@link
     *             ConnectivityManager#TYPE_ETHERNET},  {@link ConnectivityManager#TYPE_BLUETOOTH}, or other
     *             types defined by {@link ConnectivityManager}
     */
    private void onNetworkConnected(int type) {
        //个别情况下在子类收到该回调时会出现presenter为空的情况。猜测可能是手机的WIFI进入了睡眠，
        // 在客户端显示的瞬间网络状态发生了改变，而客户端在收到该通知时未能及时完成presenter创建，从而造成空指针
    }

    protected boolean registerEventBus() {
        return false;
    }
}

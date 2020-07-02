package com.mkean.demo.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.launcher.ARouter;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();
    protected Activity mActivity;
    protected Context mContext;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = this.getActivity();
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        ARouter.getInstance().inject(this);

        if (registerEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        createView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initEventAndData();
    }

    /**
     * 布局 ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * view 创建时操作
     *
     * @param view
     */
    protected abstract void createView(View view);

    /**
     * activity 创建时操作
     */
    protected abstract void initEventAndData();

    private boolean registerEventBus() {
        return false;
    }

    @Override
    public void onDestroy() {
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (registerEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
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
    public void onNetworkConnected(int type) {

    }
}

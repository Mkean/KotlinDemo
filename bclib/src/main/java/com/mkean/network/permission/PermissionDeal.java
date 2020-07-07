package com.mkean.network.permission;

import android.os.Build;

import androidx.fragment.app.FragmentActivity;

import com.mkean.network.permission.request.BaseRequest;
import com.mkean.network.permission.request.LRequest;
import com.mkean.network.permission.request.MRequest;

/**
 * 权限申请实际处理类
 */
public class PermissionDeal {
    private BaseRequest mRequest;

    public PermissionDeal(BaseRequest.DealPermissionListener listener) {
        this.mRequest = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? new MRequest(listener) : new LRequest(listener);
    }

    public void dealPermissions(FragmentActivity activity, String... permission) {
        if (activity != null && mRequest != null) {
            mRequest.requestPermission(activity, permission);
        }
    }
}

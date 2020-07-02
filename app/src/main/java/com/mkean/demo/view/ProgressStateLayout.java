package com.mkean.demo.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mkean.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于网络请求中的四种状态之间相互转换
 * （默认加载成功的页面）
 * TAG_LOADING: 加载中
 * TAG_ERROR:加载错误
 * TAG_EMPTY:空页面（无数据）
 */
public class ProgressStateLayout extends RelativeLayout {

    private LayoutParams layoutParams;
    private LayoutInflater inflater;
    private List<View> views = null;
    private static final String TAG_LOADING = "loading";
    private static final String TAG_ERROR = "error";
    private static final String TAG_EMPTY = "empty";

    private View viewLoading, viewEmpty, viewError;
    private ReloadListener listener;
    private CallServiceListener callListener;

    /**
     * 重新加载按钮的接口，用于监听重新加载按钮的监听回调
     */
    public interface ReloadListener {
        void onClick(View view);
    }

    /**
     * 联系客服的接口，用于监听联系客服点击事件的监听回调
     */
    public interface CallServiceListener {
        void onClick(View view);
    }

    private enum Type {
        LOADING, ERROR, CONTENT, EMPTY
    }

    public ProgressStateLayout(Context context) {
        this(context, null);
    }

    public ProgressStateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressStateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        views = new ArrayList<>();
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_IN_PARENT);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        // 把 ProgressStateView 内的子控件内容添加到list集合中，保证不同状态间相互切换内容的显示与隐藏
        if (child.getTag() == null || (!child.getTag().equals(TAG_LOADING) &&
                !child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR))) {
            views.add(child);
        }
    }


    /**
     * 改变状态
     *
     * @param type
     * @param resId
     * @param title
     * @param msg
     * @param btnText
     */
    private void switchState(Type type, int resId, String title, String msg, String btnText) {
        switch (type) {
            case LOADING:
                hideEmptyView();
                hideErrorView();
                setContentView(false);
                setLoadingView();
                break;
            case ERROR:
                hideEmptyView();
                hideLoadingView();
                setContentView(false);
                setErrorView(resId, title, msg, btnText);
                break;
            case EMPTY:
                hideErrorView();
                hideLoadingView();
                setContentView(false);
                setEmptyView(resId, msg);
                break;
            case CONTENT:
                hideLoadingView();
                hideEmptyView();
                hideErrorView();
                setContentView(true);
                break;
        }
    }

    private void setLoadingView() {
        if (viewLoading == null) {
            viewLoading = inflater.inflate(R.layout.layout_loading, null);

            viewLoading.setTag(TAG_LOADING);

            viewLoading.requestLayout();
            addView(viewLoading, layoutParams);
        } else {
            viewLoading.setVisibility(View.VISIBLE);
        }
    }

    private void setEmptyView(int resId, String msg) {
        if (viewEmpty == null) {
            viewEmpty = inflater.inflate(R.layout.layout_empty, null);
            if (resId != 0) {
                ImageView imageView = viewEmpty.findViewById(R.id.img_no_data);
                imageView.setImageResource(resId);
            }

            if (!TextUtils.isEmpty(msg)) {
                TextView tv_msg = viewEmpty.findViewById(R.id.tv_no_data_tips);
                tv_msg.setText(msg);
            }
            viewEmpty.setTag(TAG_EMPTY);
            viewEmpty.requestLayout();

            addView(viewEmpty, layoutParams);
        } else {
            viewEmpty.setVisibility(View.VISIBLE);
        }
    }

    private void setErrorView(int resId, String title, String msg, String btnText) {
        if (viewError == null) {
            viewError = inflater.inflate(R.layout.layout_error, null);
            if (resId != 0) {
                ImageView imageView = viewError.findViewById(R.id.img_error);
                imageView.setImageResource(resId);
            }
            if (!TextUtils.isEmpty(title)) {
                TextView tv_title = viewError.findViewById(R.id.tv_title);
                tv_title.setText(title);
            }
            if (!TextUtils.isEmpty(msg)) {
                TextView tv_msg = viewError.findViewById(R.id.tv_msg);
                tv_msg.setText(msg);
            }
            TextView tv_reload = viewError.findViewById(R.id.btn_reload);
            if (!TextUtils.isEmpty(btnText)) {
                tv_reload.setText(btnText);
            }
            tv_reload.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onClick(view);
                }
            });
            viewError.setTag(TAG_ERROR);
            viewError.requestLayout();

            addView(viewError, layoutParams);
        } else {
            viewError.setVisibility(View.VISIBLE);
        }
    }


    private void hideLoadingView() {
        if (viewLoading != null) {
            viewLoading.setVisibility(GONE);
        }
    }

    private void hideEmptyView() {
        if (viewEmpty != null) {
            viewEmpty.setVisibility(GONE);
        }
    }

    private void hideErrorView() {
        if (viewError != null) {
            viewError.setVisibility(GONE);
        }
    }

    private void setContentView(boolean flag) {
        for (View v : views) {
            v.setVisibility(flag ? VISIBLE : GONE);
        }
    }

    public void showLoading() {
        switchState(Type.LOADING, 0, null, null, null);
    }

    public void showError(int resId, String title, String msg,
                          String btnText, ReloadListener listener) {
        this.listener = listener;
        switchState(Type.ERROR, resId, title, msg, btnText);
    }

    public void showEmpty(int resId, String msg) {
        switchState(Type.EMPTY, resId, msg, null, null);
    }

    public void showContent() {
        switchState(Type.CONTENT, 0, null, null, null);
    }

    public void showError(String title, String msg, String btnText, ReloadListener listener) {
        this.listener = listener;
        switchState(Type.ERROR, 0, title, msg, btnText);
    }

    public void showEmpty(String title) {
        switchState(Type.EMPTY, 0, title, null, null);
    }

}

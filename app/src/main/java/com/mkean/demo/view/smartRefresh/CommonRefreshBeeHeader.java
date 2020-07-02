package com.mkean.demo.view.smartRefresh;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.mkean.demo.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * 公共蜜蜂样式下拉刷新布局
 */
public class CommonRefreshBeeHeader extends RelativeLayout implements RefreshHeader {

    private Context context;

    private ImageView inIv;
    private ImageView idleIv;
    private ImageView outIv;

    private AnimationDrawable inAnimationDrawable;
    private AnimationDrawable idleAnimationDrawable;
    private AnimationDrawable outAnimationDrawable;


    public CommonRefreshBeeHeader(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CommonRefreshBeeHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public CommonRefreshBeeHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        View.inflate(context, R.layout.common_view_header_bee, this);
        inIv = findViewById(R.id.view_header_bee_in_iv);
        inAnimationDrawable = (AnimationDrawable) inIv.getBackground();

        idleIv = findViewById(R.id.view_header_bee_idle_iv);
        idleAnimationDrawable = (AnimationDrawable) idleIv.getBackground();

        outIv = findViewById(R.id.view_header_bee_out_iv);
        outAnimationDrawable = (AnimationDrawable) outIv.getBackground();

    }

    /**
     * 获取真实视图（必须返回，不能为 null）
     *
     * @return
     */
    @NonNull
    @Override
    public View getView() {
        return this; // 真实视图就是自己，不能返回null
    }

    /**
     * 获取变换方式（必须指定一个：平移、拉伸、固定、全屏）
     * Translate： 平移行动 特点：最常见，HeaderView 高度不会改变
     * Scale：拉伸形变 特点：在下拉和上弹（HeaderView高度改变）的时候，会自动触发onDraw事件
     * FixedFront：固定在前面 特点：不会上下移动，HeaderView高度不会改变
     * FixedBehind：固定在后面 特点：不会上下移动，HeaderView高度不会改变（类似微信浏览器效果）
     * Screen：全屏幕 特点：固定在前面，尺寸充满整个布局
     *
     * @return
     */
    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate; // 指定为平移，不能 null
    }

    /**
     * 动画结束
     *
     * @param refreshLayout
     * @param success       数据是否成功刷新或加载 {@code true} 刷新完成， {@code false} 刷新失败
     * @return 完成动画所需时间 如果返回 Integer.MAX_VALUE 将取消本次完成事件，继续保持原有状态
     */
    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 950;// 停止动画 数字是 延迟多少毫秒后在弹回。
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        switch (newState) {
            case None:
                inIv.setVisibility(GONE);
                outIv.setVisibility(GONE);
                idleIv.setVisibility(GONE);
                inAnimationDrawable.stop();
                idleAnimationDrawable.stop();
                outAnimationDrawable.stop();
                break;
            case PullDownToRefresh:
                //下拉开始刷新
                inIv.setVisibility(VISIBLE);
                idleIv.setVisibility(GONE);
                outIv.setVisibility(GONE);
                if (inAnimationDrawable != null && !inAnimationDrawable.isRunning()) {
                    inAnimationDrawable.start();
                }
                // 下拉刷新
                break;
            case Refreshing:
                //正在刷新
                idleIv.setVisibility(VISIBLE);
                inIv.setVisibility(GONE);
                outIv.setVisibility(GONE);

                inAnimationDrawable.stop();

                if (idleAnimationDrawable != null && !idleAnimationDrawable.isRunning()) {
                    idleAnimationDrawable.start();
                }
                break;
            case RefreshFinish:
                //刷新完成
                outIv.setVisibility(VISIBLE);
                inIv.setVisibility(GONE);
                idleIv.setVisibility(GONE);
                inAnimationDrawable.stop();
                idleAnimationDrawable.stop();

                if (outAnimationDrawable != null && !outAnimationDrawable.isRunning()) {
                    outAnimationDrawable.start();
                }
                break;

        }

    }

    /**
     * 设置主题颜色（如果自定义的Header没有注意颜色，本方法可以什么都不处理）
     *
     * @param colors
     */
    @Override
    public void setPrimaryColors(int... colors) {

    }

    /**
     * 尺寸定义初始化完成（如果高度不改变（代码修改：setHeader）），只调用一次，在{@link RefreshLayout#onMeasure} 中调用
     *
     * @param kernel        核心接口（用于完成高级Header功能）
     * @param height        HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 开始动画（开始刷新或者开始加载动画）
     * @param refreshLayout
     * @param height HeaderHeight or FooterHeight
     * @param maxDragHeight 最大拖动高度
     */
    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}

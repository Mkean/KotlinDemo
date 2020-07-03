package com.mkean.demo.utils;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.mkean.demo.R;

public class CountTimer extends CountDownTimer {

    private TextView button;
    private TextView textView;

    public  int countTime;
    private boolean voice;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public CountTimer(long millisInFuture, long countDownInterval, TextView button, TextView textView, boolean voice) {
        super(millisInFuture, countDownInterval);
        this.button = button;
        this.textView = textView;
        this.countTime = 0;
        this.voice = voice;
    }

    public void startCount() {
        button.setEnabled(false);
        start();
    }

    public void stopCount(){
        cancel();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        button.setText(millisUntilFinished / 1000 + "s后重新获取");
    }

    @Override
    public void onFinish() {
        if (button != null){
            button.setEnabled(true);
            button.setText(R.string.login_btn_send_code);
        }
        countTime++;
        if (voice && textView != null){
            textView.setVisibility(View.VISIBLE);
        }else if (countTime >= 2 && textView != null){
            textView.setVisibility(View.VISIBLE);
        }
    }
}

package com.mkean.demo.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mkean.demo.R;
import com.mkean.demo.http.HttpMethod;
import com.mkean.demo.httpService.LoginServices;
import com.orhanobut.logger.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GraphicVerificationUtil implements View.OnClickListener {

    public Activity activity;
    private View view;
    private ImageView ivIcon;
    private EditText etInPut;
    private Button btnOk;
    private Button btnNo;

    private HttpMethod httpMethod;
    public String iconNumber = "";

    public String IMAGE_VIEW_URL = HttpMethod.BASEHTTP + "user/getPicVerificationCode.do?uuid=";

    private String phone;
    private String uuid;
    private CountTimer count;

    public GraphicVerificationUtil(Activity activity) {
        this.activity = activity;
        httpMethod = HttpMethod.getInstance(activity);
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(activity).inflate(R.layout.dialog_graphic_verification, null);
        ivIcon = view.findViewById(R.id.iv_graphic_verification_icon);
        etInPut = view.findViewById(R.id.et_input_graphic_verification);
        btnOk = view.findViewById(R.id.btn_ok);
        btnNo = view.findViewById(R.id.btn_no);

        ivIcon.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    public void show(String phone, String uuid) {
        this.phone = phone;
        this.uuid = uuid;
        initView();
        // TODO：未完成 dialog
        // 初始图片
        Glide.with(activity).load(IMAGE_VIEW_URL + uuid).into(ivIcon);
    }

    public void show(String phone, String uuid, CountTimer count) {
        this.phone = phone;
        this.uuid = uuid;
        this.count = count;
        initView();
        // TODO：未完成 dialog
        // 初始图片
        Glide.with(activity).load(IMAGE_VIEW_URL + uuid).into(ivIcon);
    }

    private void showIcon() {
        Glide.with(activity)
                .load(IMAGE_VIEW_URL + uuid + "&time=" + System.currentTimeMillis())
                .error(R.drawable.ic_show_error)
                .placeholder(R.drawable.ic_show_loading)
                .fitCenter()
                .override(400, 300)
                .into(ivIcon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_graphic_verification_icon:
                showIcon();
                break;
            case R.id.btn_ok:
                if (!TextUtils.isEmpty(etInPut.getText())) {
                    iconNumber = etInPut.getText().toString();
                    // 发送验证码
                    sendSMS(phone, iconNumber, uuid);
                } else {
                    Toast.makeText(activity, "请输入验证码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_no:
                break;
        }
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @param picCode
     * @param uuid
     */
    @SuppressLint("CheckResult")
    private void sendSMS(String phone, String picCode, String uuid) {
        httpMethod.getServicesNoToken(LoginServices.class)
                .getSmsCode(phone, picCode, uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringResultData -> {
                    if (stringResultData.getStatusCode() == 200) {
                        Logger.json(stringResultData.getData());
                        count.startCount();
                    }
                }, throwable -> {
                    Toast.makeText(activity, "网络异常", Toast.LENGTH_SHORT).show();
                });

    }
}

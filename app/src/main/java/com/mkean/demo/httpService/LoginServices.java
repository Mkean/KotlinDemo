package com.mkean.demo.httpService;

import com.mkean.demo.entity.LoginResult;
import com.mkean.demo.entity.ResultData;
import com.mkean.demo.entity.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginServices {

    /**
     * 手机密码登录
     *
     * @param username
     * @param password
     * @param s
     * @param registerType
     * @return
     */
    @FormUrlEncoded
    @POST("user/appLoginV2.do")
    Observable<LoginResult> loginWithoutCode(@Field("username") String username, @Field("pwd") String password, @Field("token_type") String s, @Field("registerType") String registerType);

    /**
     * 手机验证码登录
     *
     * @param mobile
     * @param code
     * @param s
     * @param registerType
     * @return
     */
    @FormUrlEncoded
    @POST("user/mobileLoginV2.do?")
    Observable<LoginResult> loginByMobileWithCode(@Field("mobile") String mobile, @Field("code") String code, @Field("token_type") String s, @Field("registerType") String registerType);

    /**
     * 获取用户信息
     *
     * @return
     */
    @GET("user/getUser.do")
    Observable<ResultData<UserInfo>> getUserInfo();

    /**
     * 获取短信验证码
     * 未注册
     *
     * @param mobile
     * @return
     */
    @POST("user/getSmsCodeByMobileAndNoExist.do")
    Observable<ResultData<String>> getSmsCodeNoExist(@Query("mobile") String mobile);

    /**
     * 获取短信验证码
     * 已注册
     *
     * @param mobile
     * @return
     */
    @POST("user/getSmsCodeByMobileAndExist.do")
    Observable<ResultData<String>> getSmsCodeAndExist(@Query("mobile") String mobile);

    /**
     * 发送短信验证码
     * 带验证图片
     *
     * @param mobile
     * @param picCode
     * @param uuid
     * @return
     */
    @POST("user/getSmsCode.do")
    Observable<ResultData<String>> getSmsCode(@Query("mobile") String mobile, @Query("picCode") String picCode, @Query("uuid") String uuid);

    /**
     * 获取电话语音验证码
     *
     * @param mobile
     * @return
     */
    @POST("user/getVoiceSmsCodeByMobileAndExist.do")
    Observable<ResultData<String>> requestPhoneVerificationCodeAndExist(@Query("mobile") String mobile);
}

package com.mkean.demo.httpService;

import com.mkean.demo.entity.ResultData;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginServices {

    @POST("user/getSmsCode.do")
    Observable<ResultData<String>> getSmsCode(@Query("mobile") String mobile,
                                              @Query("picCode") String picCode, @Query("uuid") String uuid);
}

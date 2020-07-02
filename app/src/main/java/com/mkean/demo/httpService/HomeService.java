package com.mkean.demo.httpService;

import com.mkean.demo.entity.MenuBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface HomeService {

    @GET("app/app-home-menu")
    Observable<MenuBean> getMenuList();
}

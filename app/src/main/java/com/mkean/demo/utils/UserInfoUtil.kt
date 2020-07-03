package com.mkean.demo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.mkean.demo.entity.UserInfo
import com.mkean.demo.http.HttpMethod
import com.mkean.demo.httpService.LoginServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

object UserInfoUtil {

    @SuppressLint("CheckResult")
    fun getUserInfo(context: Context) {
        val userInfo = UserInfo.getInstance(context)
        val snappyDBUtil = SnappyDBUtil(context)
        val httpMethod = HttpMethod.getInstance(context)
        httpMethod.getServices(LoginServices::class.java)
                .userInfo
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.statusCode) {
                        200 -> {
                            userInfo.isLogin = true
                            userInfo.moneyAccount = it.data.moneyAccount
                            userInfo.pensionMoneyAccount = it.data.pensionMoneyAccount
                            userInfo.nickname = it.data.nickname
                            userInfo.headUrl = it.data.headUrl
                            userInfo.mobile = it.data.mobile
                            userInfo.pwd = it.data.pwd
                            userInfo.accumulativeIntegral = it.data.accumulativeIntegral
                            userInfo.userLevel = it.data.userLevel
                            userInfo.sex = it.data.sex
                            userInfo.createTime = it.data.createTime
                            userInfo.realName = it.data.realName
                            snappyDBUtil.putObject("userInfo", userInfo)
                            EventBus.getDefault().post("UserInfoRefresh")
                        }
                        300 -> {
                            userInfo.isLogin = false
                        }
                    }
                }, {
                    Log.e(UserInfoUtil.javaClass.simpleName, it.message)
                })

    }

}
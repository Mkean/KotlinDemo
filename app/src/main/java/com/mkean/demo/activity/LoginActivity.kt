package com.mkean.demo.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mkean.demo.R
import com.mkean.demo.app.BaseApplication
import com.mkean.demo.app.Constants
import com.mkean.demo.entity.UserInfo
import com.mkean.demo.http.HttpMethod
import com.mkean.demo.httpService.LoginServices
import com.mkean.demo.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_by_third.*
import kotlinx.android.synthetic.main.login_choose_login_type.*
import kotlinx.android.synthetic.main.title_bar_wait_assess.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val standsLogin: Int = 101
        const val fastLogin: Int = 102
    }

    private lateinit var httpMethod: HttpMethod

    private lateinit var count: CountTimer
    private lateinit var phoneCount: CountTimer
    private lateinit var graphicVerificationUtil: GraphicVerificationUtil
    private lateinit var snappyDBUtil: SnappyDBUtil
    private lateinit var userInfo: UserInfo

    private var h5Login: Boolean = false
    private var isShowPwd: Boolean = false


    override fun registerEventBus(): Boolean = true


    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onPrepareIntent(intent: Intent?) {
        super.onPrepareIntent(intent)
        if (intent != null) {
            h5Login = intent.getBooleanExtra("H5Login", false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        snappyDBUtil = SnappyDBUtil(this)
        com.bidanet.android.common.utils.http.HttpMethod.headers["x-platform"] = "android"

        var adCode = snappyDBUtil.getDbInteger(Constants.CITY_AD_CODE)
        if (adCode == 0) {
            adCode = 320506
            snappyDBUtil.putInteger(Constants.CITY_AD_CODE, adCode)
        }
        com.bidanet.android.common.utils.http.HttpMethod.headers["x-city"] = adCode.toString()
        com.bidanet.android.common.utils.http.HttpMethod.headers["x-version"] = BaseApplication.VERSION_CODE

        httpMethod = HttpMethod.getInstance(context)
        count = CountTimer(30 * 1000, 1000, btn_login_send_code, tv_phone_verification_code, false)
        phoneCount = CountTimer(60 * 1000, 1000, btn_login_send_code, tv_phone_verification_code, true)

        graphicVerificationUtil = GraphicVerificationUtil(context as Activity)
        snappyDBUtil = SnappyDBUtil(context)
        userInfo = UserInfo.getInstance(context)

    }

    override fun onViewCreate() {
        super.onViewCreate()

        tv_wait_assess_title_name.text = resources.getString(R.string.login)
        btn_login_login.isEnabled = false

        chooseModel(fastLogin)
        checkLogin()

        pwd_is_show.setOnClickListener(this)
        tv_login_stands_login.setOnClickListener(this)
        tv_login_fast_login.setOnClickListener(this)
        tv_login_look_back_password.setOnClickListener(this)
        tv_phone_verification_code.setOnClickListener(this)
        btn_login_login.setOnClickListener(this)
        btn_login_send_code.setOnClickListener(this)
        ll_login_wechat_login.setOnClickListener(this)
        ll_login_qq_login.setOnClickListener(this)
        ll_login_register.setOnClickListener(this)
        ll_back.setOnClickListener(this)

    }

    private var loginType: Int = standsLogin
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ll_back -> {
                finish()
            }
            R.id.ll_login_register -> {
            }
            R.id.ll_login_qq_login -> {
            }
            R.id.ll_login_wechat_login -> {
            }
            R.id.btn_login_send_code -> {
                sendCode()
            }
            R.id.btn_login_login -> {
                login()
            }
            R.id.tv_phone_verification_code -> {
                callCode()
            }
            R.id.tv_login_look_back_password -> {
            }
            R.id.tv_login_fast_login -> {
                chooseModel(fastLogin)
                tv_password_error.text = ""
            }
            R.id.tv_login_stands_login -> {
                chooseModel(standsLogin)
            }
            R.id.pwd_is_show -> {
                if (isShowPwd) {
                    pwd_is_show.setImageResource(R.mipmap.according_show)
                    et_login_password.inputType = InputType.TYPE_CLASS_TEXT
                    isShowPwd = false
                } else {
                    pwd_is_show.setImageResource(R.mipmap.according_hide)
                    et_login_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    isShowPwd = true
                }
                et_login_password.selectionEnd
            }

        }
    }

    /**
     * 登录
     */
    private fun login() {
        if (!ValidationUtils.get().checkTelPhone(et_login_phone.text.toString().trim())) {
            tv_phone_error.text = "手机号码错误"
            tv_phone_error.visibility = View.VISIBLE
            return
        }

        when (loginType) {
            standsLogin -> {
                httpMethod.getServices(LoginServices::class.java)
                        .loginWithoutCode(et_login_phone.text.toString().trim(), et_login_password.text.toString().trim(), "api", "ORIGIN_APP_ANDROID")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            when (it.statusCode) {
                                200 -> {
                                    UserInfoUtil.getUserInfo(context)
                                    userInfo.isLogin = true
                                    com.bidanet.android.common.utils.http.HttpMethod.urlParams["token"] = it.data.token
                                    com.bidanet.android.common.utils.http.HttpMethod.formParams["token"] = it.data.token
                                    snappyDBUtil.putString("token", it.data.token)
                                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                                    EventBus.getDefault().post("loginSuccess")
                                }
                                300 -> {
                                    try {
                                        val json = JSONObject(it.message)
                                        if (json.get("code") != null) {
                                            if (json.get("code") == "101") {
                                                tv_phone_error.text = json.get("message").toString()
                                                tv_phone_error.visibility = View.VISIBLE
                                            } else if (json.get("code") == 201) {
                                                tv_password_error.text = json.get("message").toString()
                                                tv_password_error.visibility = View.VISIBLE
                                            }
                                        } else {
                                            Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                                else -> Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                            }
                        }, {
                            Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show()
                            Log.i(TAG, it.message)
                        })


            }

            fastLogin -> {
                httpMethod.getServices(LoginServices::class.java)
                        .loginByMobileWithCode(et_login_phone.text.toString().trim(), et_login_password.text.toString().trim(),
                                "api", "ORIGIN_APP_ANDROID")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            when (it.statusCode) {
                                200 -> {
                                    com.bidanet.android.common.utils.http.HttpMethod.urlParams["token"] = it.data.token
                                    com.bidanet.android.common.utils.http.HttpMethod.formParams["token"] = it.data.token
                                    userInfo.isLogin = true
                                    UserInfoUtil.getUserInfo(context)
                                    snappyDBUtil.putString("token", it.data.token)
                                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show()
                                    EventBus.getDefault().post("loginSuccess")
                                    if (!h5Login) {
                                        // TODO: 未完成
                                    }

                                }
                                300 -> {
                                    try {
                                        val json = JSONObject(it.message)
                                        if (json.get("code") != null) {
                                            if (json.get("code") == "101") {
                                                tv_phone_error.text = json.get("message").toString()
                                                tv_phone_error.visibility = View.VISIBLE
                                            } else if (json.get("code") == "201") {
                                                tv_password_error.text = json.get("message").toString()
                                                tv_password_error.visibility = View.VISIBLE
                                            }
                                        } else {
                                            Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
                                }
                                else -> {
                                    Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }, {
                            Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show()
                        })
            }
        }
    }

    /**
     * 发送验证码
     */
    @SuppressLint("CheckResult")
    private fun sendCode() {
        if (!ValidationUtils.get().checkTelPhone(et_login_phone.text.toString().trim())) {
            tv_phone_error.text = "手机号码错误"
            tv_phone_error.visibility = View.VISIBLE
            return
        }

        // TODO：有错误
        httpMethod.getServicesNoToken(LoginServices::class.java)
                .getSmsCodeAndExist(et_login_phone.text.toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.statusCode) {
                        200 -> {
                            count.startCount()
                            Toast.makeText(context, "短信验证码已发送，请注意查收短信", Toast.LENGTH_SHORT).show()
                        }
                        300 -> {
                            tv_phone_error.text = it.message
                            tv_phone_error.visibility = View.VISIBLE
                        }
                        302 -> {
                            graphicVerificationUtil.show(et_login_phone.text.toString().trim(), it.message, count)
                        }
                        else -> {
                            Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, {
                    Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show()
                })
    }

    /**
     *未收到验证码
     */
    @SuppressLint("CheckResult")
    private fun callCode() {
        if (!ValidationUtils.get().checkTelPhone(et_login_phone.text.toString().trim())) {
            tv_phone_error.text = "手机号码错误"
            tv_phone_error.visibility = View.VISIBLE
            return
        }

        if (phoneCount.countTime > 2) {
            Toast.makeText(context, "电话验证过于频繁，请稍后再试", Toast.LENGTH_SHORT).show()
            return
        }
        tv_phone_verification_code.visibility = View.INVISIBLE

        httpMethod.getServicesNoToken(LoginServices::class.java)
                .requestPhoneVerificationCodeAndExist(et_login_phone.text.toString().trim())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.statusCode) {
                        200 -> {
                            Toast.makeText(context, "验证码将以电话形式通知您，请留意来电", Toast.LENGTH_SHORT).show()
                            phoneCount.startCount()
                        }
                        300
                        -> {
                            tv_phone_error.text = it.message
                            tv_phone_error.visibility = View.VISIBLE
                        }
                        else -> {
                            Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                        }

                    }
                }, { Toast.makeText(context, "网络异常", Toast.LENGTH_SHORT).show() })
    }

    private fun chooseModel(type: Int) {
        when (type) {
            standsLogin -> {
                isShowPwd = false
                loginType = standsLogin
                pwd_is_show.setImageDrawable(resources.getDrawable(R.mipmap.according_hide))
                iv_phone.setImageResource(R.mipmap.login_account)
                iv_password.setImageResource(R.mipmap.login_password)

                et_login_password.setText("")
                et_login_phone.hint = resources.getString(R.string.login_et_phone)
                et_login_password.hint = resources.getString(R.string.login_et_password)
                et_login_password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                tv_login_stands_login.setTextColor(resources.getColor(R.color.new_theme_color))
                tv_login_fast_login.setTextColor(resources.getColor(R.color.three))

                btn_login_send_code.visibility = View.GONE
                view_divider.visibility = View.GONE
                view_login_fast_login.visibility = View.GONE
                view_login_stands_login.visibility = View.VISIBLE
                pwd_is_show.visibility = View.VISIBLE
                tv_login_look_back_password.visibility = View.VISIBLE
                tv_phone_verification_code.visibility = View.INVISIBLE

            }
            fastLogin -> {
                loginType = fastLogin
                iv_phone.setImageResource(R.mipmap.login_phone)
                iv_password.setImageResource(R.mipmap.login_verification)

                pwd_is_show.visibility = View.GONE
                view_login_stands_login.visibility = View.GONE
                tv_login_look_back_password.visibility = View.INVISIBLE
                btn_login_send_code.visibility = View.VISIBLE
                view_divider.visibility = View.VISIBLE
                view_login_fast_login.visibility = View.VISIBLE

                et_login_password.setText("")
                et_login_phone.hint = resources.getString(R.string.login_et_phone)
                et_login_password.hint = resources.getString(R.string.login_et_phone_pass)
                et_login_password.inputType = InputType.TYPE_CLASS_NUMBER
                tv_login_fast_login.setTextColor(resources.getColor(R.color.new_theme_color))
                tv_login_stands_login.setTextColor(resources.getColor(R.color.three))

            }
        }
    }

    private fun checkLogin() {
        et_login_phone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btn_login_login.isEnabled = et_login_phone.text.length == 11 && et_login_password.text.length >= 6

                if (et_login_phone.text.isEmpty()) {
                    tv_phone_verification_code.visibility = View.INVISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (before == 0 || count == 0) {
                    tv_phone_error.text = ""
                    tv_phone_error.visibility = View.INVISIBLE

                    tv_password_error.text = ""
                    tv_password_error.visibility = View.INVISIBLE
                }
            }

        })

        et_login_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btn_login_login.isEnabled = et_login_phone.text.length == 11 && et_login_password.text.length >= 6

                if (et_login_phone.text.isEmpty()) {
                    tv_phone_verification_code.visibility = View.INVISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (before == 0 || count == 0) {
                    tv_phone_error.text = ""
                    tv_phone_error.visibility = View.INVISIBLE

                    tv_password_error.text = ""
                    tv_password_error.visibility = View.INVISIBLE
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun logout(event: String) {
        if (event == "loginSuccess") {
            finish()
        }
    }
}

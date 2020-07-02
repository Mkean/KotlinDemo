package com.mkean.demo.activity

import android.content.Intent
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import com.mkean.demo.R
import com.mkean.demo.entity.UserInfo
import com.mkean.demo.http.HttpMethod
import com.mkean.demo.utils.CountTimer
import com.mkean.demo.utils.GraphicVerificationUtil
import com.mkean.demo.utils.SnappyDBUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.login_by_third.*
import kotlinx.android.synthetic.main.login_choose_login_type.*
import kotlinx.android.synthetic.main.title_bar_wait_assess.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    companion object {
        const val standsLogin: Int = 101
        const val fastLogin: Int = 102
    }

    private var httpMethod: HttpMethod? = null

    private var count: CountTimer? = null
    private var phoneCount: CountTimer? = null

    private var graphicVerificationUtil: GraphicVerificationUtil? = null
    private var snappyDBUtil: SnappyDBUtil? = null
    private var userInfo: UserInfo? = null

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


    override fun onViewCreate() {
        super.onViewCreate()
        snappyDBUtil = SnappyDBUtil(this)
        userInfo = UserInfo.getInstance(this)
        httpMethod = HttpMethod.getInstance(this)
        graphicVerificationUtil = GraphicVerificationUtil(this)
        count = CountTimer(30 * 1000, 1000, btn_login_send_code, tv_phone_verification_code, false)
        phoneCount = CountTimer(60 * 1000, 1000, btn_login_send_code, tv_phone_verification_code, true)

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

    var loginType: Int = standsLogin
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

    }

    /**
     * 发送验证码
     */
    private fun sendCode() {

    }

    /**
     *未收到验证码
     */
    private fun callCode() {
    }

    private fun chooseModel(type: Int) {
        when (type) {
            standsLogin -> {
                isShowPwd = false
                loginType = standsLogin
                pwd_is_show.setImageDrawable(resources.getDrawable(R.mipmap.according_hide))
                iv_phone.setImageResource(R.mipmap.login_account)
                iv_password.setImageResource(R.mipmap.login_password)

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

                et_login_phone.hint = resources.getString(R.string.login_et_phone)
                et_login_password.hint = resources.getString(R.string.login_et_phone_pass)
                et_login_password.inputType = InputType.TYPE_CLASS_NUMBER
                tv_login_fast_login.setTextColor(resources.getColor(R.color.new_theme_color));
                tv_login_stands_login.setTextColor(resources.getColor(R.color.three));

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
}

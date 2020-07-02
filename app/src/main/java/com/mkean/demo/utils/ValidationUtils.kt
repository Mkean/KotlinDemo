package com.mkean.demo.utils

import java.util.regex.Pattern

class ValidationUtils {

    companion object {
        private var instance: ValidationUtils? = null

        @Synchronized
        fun get(): ValidationUtils {
            if (instance == null) {
                instance = ValidationUtils()
            }
            return instance!!
        }
    }


    /**
     * 验证用户名是否合法
     *
     * @param s
     * @return
     */
    fun checkUserName(s: String?): Boolean {
        val reg = "[\u4e00-\u9fa5]{2,4}$"

        val result: Boolean = Pattern.compile(reg).matcher(s).find()

        if (!result) { // 验证失败
            return false
        }
        return true
    }

    /**
     * 验证手机号是否合法
     *
     * @param tel
     * @return
     */
    fun checkTelPhone(tel: String?): Boolean {
        val reg = "^1\\d{10}$"
        val result: Boolean = Pattern.compile(reg).matcher(tel).find()
        if (!result) {
            return false
        }
        return true
    }

    /**
     * 验证qq号码是否合法
     *
     * @param text
     * @return
     */
    fun checkQQNumber(text: String?): Boolean {
        val reg = "[1-9][0-9]{4,}"
        val result = Pattern.compile(reg).matcher(text).find()
        if (!result) { // 验证失败
            return false
        }
        return true
    }

    /**
     * 验证邮箱是否合法
     *
     * @param text
     * @return
     */
    fun checkEmail(text: String?): Boolean {
        val reg = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"
        val result = Pattern.compile(reg).matcher(text).find()
        if (!result) { // 验证失败
            return false
        }
        return true
    }

    /**
     * 验证身份证号码是否合法
     *
     * @param text
     * @return
     */
    fun checkCard(text: String?): Boolean {
        val reg = "[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$"
        val result = Pattern.compile(reg).matcher(text).find()
        if (!result) { // 验证失败
            return false
        }
        return true
    }

    /**
     * 验证住址是否合法
     *
     * @param text
     * @return
     */
    fun checkAddress(text: String?): Boolean {
        val reg = "^[\\u4E00-\\u9FA5A-Za-z\\d\\-\\_]{5,60}$"
        val result = Pattern.compile(reg).matcher(text).find()
        if (!result) { // 验证失败
            return false
        }
        return true
    }

    /**
     * 验证邮政编码是否合法
     *
     * @return
     */
    fun checkYb(text: String?): Boolean {
        val reg = "^[1-9]\\d{5}$"
        val result = Pattern.compile(reg).matcher(text).find()
        if (!result) { // 验证失败
            return false
        }
        return true
    }

    /**
     * 验证昵称是否合法
     *
     * @param text
     * @return
     */
    fun checkNickName(text: String?): Boolean {
        val reg = "[^?!@#$%\\^&*()]+"
        val result = Pattern.compile(reg).matcher(text).find()
        if (!result) { // 验证失败
            return false
        }
        return true
    }

    /**
     * 验证密码是否合法
     *
     * @param text
     * @return
     */
    fun checkPassWord(text: String?): Boolean {
        val reg = "^([\u4e00-\u9fa5]+|[a-zA-Z0-9]+){6,20}$"
        val result = Pattern.compile(reg).matcher(text).find()
        if (!result) { // 验证失败
            return false
        }
        return true
    }
}
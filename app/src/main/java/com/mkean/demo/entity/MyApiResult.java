package com.mkean.demo.entity;

import com.bidanet.android.common.utils.http.api.ApiResult;

import java.util.List;
import java.util.Map;

public class MyApiResult<T> extends ApiResult<T> {

    public static final int STATUS_OK = 200;
    public static final int STATUS_ERROR = 300;
    public static final int STATUS_TIMEOUT = 301;

    protected int status;
    protected String message;
    protected T data;
    protected List<Map<String, String>> errors;

    public MyApiResult() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<Map<String, String>> getErrors() {
        return errors;
    }

    public void setErrors(List<Map<String, String>> errors) {
        this.errors = errors;
    }
}

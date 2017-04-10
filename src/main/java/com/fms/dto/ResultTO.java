package com.fms.dto;

public class ResultTO<T> {

    private int code = 200;
    private boolean success = true;
    private String msg;
    private T result;
    private PageTO page;

    public ResultTO() {
    }

    public ResultTO(T data) {
        this.result = data;
    }

    public ResultTO(T data,String message) {
        this.result = data;
        this.msg=message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PageTO getPage() {
        return page;
    }

    public void setPage(PageTO page) {
        this.page = page;
    }

    public void authException(String msg) {
        this.code = 403;
        this.msg = msg;
        this.success = false;
    }

    public void commonException(String msg) {
        this.code = 400;
        this.msg = msg;
        this.success = false;
    }

    public void serverException(String msg) {
        this.code = 500;
        this.msg = msg;
        this.success = false;
    }
}

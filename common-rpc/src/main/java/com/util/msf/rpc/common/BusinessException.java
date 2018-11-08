package com.util.msf.rpc.common;

/**
 * 应用异常的封装，重写了fillInStackTrace方法，剔除了同步，提供方便的转换Result
 *
 * Created by wencheng on 2018/11/8.
 */
public class BusinessException extends RuntimeException implements Resultable {
    private int code;
    private String msg;

    public BusinessException() {
    }

    public BusinessException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public BusinessException(Resultable result) {
        this.code = result.getCode();
        this.msg = result.getMsg();
    }


    /**
     * 构建异常
     *
     * @param code
     * @param msg
     * @return
     */
    public static BusinessException of(int code, String msg) {
        return new BusinessException(code, msg);
    }


    /**
     * 构建异常，常用于使用ResultCode构建异常
     *
     * @param result
     * @return
     */
    public static BusinessException of(Resultable result) {
        return new BusinessException(result);
    }


    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @Override
    public String getMessage() {
        return "BusinessException{" + "code=" + code + ", msg='" + msg + '\'' + '}';
    }

    @Override
    public String toString() {
        return "BusinessException{" + "code=" + code + ", msg='" + msg + '\'' + '}';
    }
}

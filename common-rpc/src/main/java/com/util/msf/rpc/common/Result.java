package com.util.msf.rpc.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * RPC调用的结果封装，约定如下：1、主键查询无结果返回失败，2、列表查询无结果返回空集合，3、更新幂等性支持重复调用返回成功
 *
 * @param <T> 具体返回的结构
 * Created by wencheng on 2018/11/8.
 */
public final class Result<T> implements Resultable {

    private static final long serialVersionUID = 7196738187866457409L;

    /**
     * 错误码
     */
    private int code = ResultCode.Success.getCode();

    /**
     * 错误消息
     */
    private String msg;

    /**
     * 符合条件的记录
     */
    private T data;

    public Result() {
    }

    public Result(T data) {
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 返回结果
     *
     * @param code 场景的代码
     * @param msg  场景的消息
     * @param <T>  返回的数据类型
     * @return
     */
    public static <T> Result<T> of(int code, String msg) {
        return of(code, msg, null);
    }

    /**
     * 返回结果
     *
     * @param code 场景的代码
     * @param msg  场景的消息
     * @param data 返回的数据
     * @param <T>  返回的数据类型
     * @return
     */
    public static <T> Result<T> of(int code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    /**
     * 用枚举构造返回结果
     *
     * @param resultCode 场景代码枚举
     * @return
     */
    public static <T> Result<T> of(Resultable resultCode) {
        return of(resultCode.getCode(), resultCode.getMsg(), null);
    }

    /**
     * 通过枚举和data构造
     *
     * @param resultCode 场景代码枚举
     * @param data       返回的数据
     * @return
     */
    public static <T> Result<T> of(Resultable resultCode, T data) {
        return of(resultCode.getCode(), resultCode.getMsg(), data);
    }


    /**
     * 返回默认成功结果
     *
     * @return
     */
    public static <T> Result<T> succeed() {
        return ResultCode.Success.result();
    }

    /**
     * 返回默认失败结果
     *
     * @return
     */
    public static <T> Result<T> failure() {
        return ResultCode.Failure.result();
    }


    /**
     * 返回成功结果，数据使用默认根节点
     *
     * @param data 返回的数据
     * @param <T>
     * @return
     */
    public static <T> Result<T> succeed(T data) {
        return new Result<>(data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 指定成功消息
     * @param <T>
     * @return
     */
    public static <T> Result<T> succeed(String msg) {
        return ResultCode.Success.result(msg);
    }

    /**
     * 返回成功结果，数据指定根节点
     *
     * @param field 指定节点名称
     * @param data  返回的数据
     * @param <T>
     * @return
     */
    public static <T> Result<Map<String, T>> succeed(String field, T data) {
        Map<String, T> map = new HashMap<>();
        map.put(field, data);
        return new Result<>(map);
    }

    /**
     * 返回成功结果，数据指定根节点
     *
     * @param field 指定节点名称
     * @param data  返回的数据
     * @return
     */
    public static Result<Map<String, Object>> succeed(String field, Object data, String field1, Object data1) {
        Map<String, Object> map = new TreeMap<>();
        map.put(field, data);
        map.put(field1, data1);
        return new Result<>(map);
    }

    /**
     * 返回成功结果
     *
     * @param field 指定节点名称
     * @param data  返回的数据
     * @return
     */

    /**
     * 返回成功结果，数据指定根节点
     *
     * @param field  节点名称
     * @param data   节点数据
     * @param field1 节点名称
     * @param data1  节点数据
     * @param args   成对返回的数据
     * @return
     */
    public static Result<Map<String, Object>> succeed(String field, Object data, String field1, Object data1, Object... args) {
        Map<String, Object> map = new TreeMap<>();
        map.put(field, data);
        map.put(field1, data1);
        if (args != null) {
            if (args.length % 2 != 0) {
                throw new IllegalArgumentException("参数个数不对");
            }
            for (int i = 0; i < args.length; i++) {
                if (args[i] == null) {
                    continue;
                }
                map.put(args[i].toString(), args[++i]);
            }
        }
        return new Result<>(map);
    }

    /**
     * 返回失败结果
     *
     * @return
     */
    public static <T> Result<T> failure(String msg) {
        return ResultCode.Failure.result(msg);
    }


    /**
     * 是否成功，条件code == 0
     *
     * @return
     */
    @JsonIgnore
    public boolean isSucceed() {
        return code == 0;
    }

    /**
     * 是否成功完成业务调用，条件code == 0 && data != null
     *
     * @return
     */
    @JsonIgnore
    public boolean isCompleted() {
        return code == 0 && data != null;
    }

    /**
     * 是否失败，条件code != 0
     *
     * @return
     */
    @JsonIgnore
    public boolean isFailure() {
        return code != 0;
    }

    @Override
    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Result{code=");
        builder.append(code);
        if (msg != null) {
            builder.append(", msg='").append(msg).append("'");
        }
        if (data != null) {
            builder.append(", data=").append(data);
        }
        builder.append("}");
        return builder.toString();
    }
}

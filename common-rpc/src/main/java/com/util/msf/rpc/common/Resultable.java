package com.util.msf.rpc.common;

import java.io.Serializable;

/**
 * Created by wencheng on 2018/11/8.
 */
public interface Resultable extends Serializable {

    /**
     * 返回响应代码
     *
     * @return
     */
    int getCode();

    /**
     * 返回响应消息
     *
     * @return
     */
    String getMsg();

    /**
     * 返回默认的结果，code=0
     *
     * @param <T>
     * @return
     */
    default <T> Result<T> result() {
        return Result.of(getCode(), getMsg());
    }

    /**
     * 返回包括指定消息的结果，code=0
     *
     * @param msg
     * @param <T>
     * @return
     */
    default <T> Result<T> result(String msg) {
        return Result.of(getCode(), msg);
    }
}

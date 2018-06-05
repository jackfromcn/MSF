package com.msf.core.common.utils;

import java.util.*;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:39
 * @Description:
 */
public class N {

    /**
     * 判断对象是否为null
     *
     * @param obj
     * @return
     */
    public static boolean isNull(Object obj) {
        return Objects.isNull(obj);
    }

    /**
     * 判断是否存在任意一个为null的参数
     *
     * @param params 参数
     * @return
     */
    public static boolean anyNull(Object... params) {
        if (params == null || params.length == 0) {
            return true;
        }
        for (Object param : params) {
            if (param == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断集合中是否存在任意一个为空的元素
     *
     * @param collection
     * @return
     */
    public static boolean anyNull(Collection collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }
        for (Object param : collection) {
            if (param == null) {
                return true;
            }
        }
        return false;
    }


    /**
     * 判断参数是否全部为null
     *
     * @param params 参数
     * @return
     */
    public static boolean allNull(Object... params) {
        if (params == null || params.length == 0) {
            return true;
        }
        for (Object param : params) {
            if (param != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断集合是否为空，不用因为该方法引入collection或者spring的依赖
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * 判断集合是否不为空，不用因为该方法引入collection或者spring的依赖
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    /**
     * 判断Map是否为空，不用因为该方法引入collection或者spring的依赖
     *
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否不为空，不用因为该方法引入collection或者spring的依赖
     *
     * @param map
     * @return
     */
    public static boolean isNotEmpty(Map map) {
        return map != null && !map.isEmpty();
    }

    /**
     * 获取默认对象，如果不为null，返回原对象，如果为null，返回空List
     *
     * @param elm
     * @param <T>
     * @return
     */
    public static <T extends List> T orEmpty(T elm) {
        if (elm != null) {
            return elm;
        }
        return (T) Collections.emptyList();
    }

    /**
     * 获取默认对象，如果不为null，返回原对象，如果为null，返回空Map
     *
     * @param elm
     * @param <T>
     * @return
     */
    public static <T extends Map> T orEmpty(T elm) {
        return elm != null ? elm : (T) Collections.emptyMap();
    }

}

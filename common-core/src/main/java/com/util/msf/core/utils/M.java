package com.util.msf.core.utils;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:38
 * @Description: Map构建工具，将key,value,key1,value1形式转换为Map
 */
public class M {

    public static <T extends Object> Map<String, T> asMap(String key, T value) {
        Map<String, T> params = new HashMap<String, T>();
        params.put(key, value);
        return params;
    }

    public static <T extends Object> Map<String, T> asMap(String key, T value, String key1, T value1) {
        Map<String, T> params = asMap(key, value);
        params.put(key1, value1);
        return params;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Object> Map<String, T> asMap(String key, T value, String key1, T value1, T... others) {
        Map<String, T> params = asMap(key, value, key1, value1);
        if (others == null || others.length == 0) {
            return params;
        }
        Object val;
        int next;
        for (int current = 0; current < others.length; ) {
            next = current + 1;
            if (next > others.length - 1) {
                val = null;
            } else {
                val = others[next];
            }
            params.put(others[current].toString(), (T) val);
            current = current + 2;
        }

        return params;
    }

    public static <K extends Object, T extends Object> Map.Entry<K, T> asEntry(K key, T value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }
}

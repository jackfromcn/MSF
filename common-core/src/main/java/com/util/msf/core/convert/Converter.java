package com.util.msf.core.convert;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @Auther: wencheng
 * @Date: 2018/6/5 14:24
 * @Description: bean转换工具
 */
public class Converter {

    public static MapperFacade MAPPER = new DefaultMapperFactory.Builder().build().getMapperFacade();

    private Converter() {
    }

    /**
     * 将原对象转换为目标类型的对象
     *
     * @param sourceObject 原对象
     * @param targetClass  目标对象的类型对象
     * @param <T>          目标对象的类型
     * @return 目标对象
     */
    public static <T> T map(Object sourceObject, Class<T> targetClass) {
        return sourceObject == null ? null : MAPPER.map(sourceObject, targetClass);
    }

    /**
     * 将原对象的集合转换为目标类型的对象列表
     *
     * @param sourceObjects 原对象列表
     * @param targetClass   目标对象的类型对象
     * @param <T>           目标对象的类型
     * @return 目标对象列表
     */
    public static <T> List<T> asList(Iterable<?> sourceObjects, Class<T> targetClass) {
        if (sourceObjects == null) {
            return Collections.emptyList();
        }
        return MAPPER.mapAsList(sourceObjects, targetClass);
    }

    /**
     * 将原对象的集合转换为目标类型的对象Set
     *
     * @param sourceObjects 原对象列表
     * @param targetClass   目标对象的类型对象
     * @param <T>           目标对象的类型
     * @return 目标对象Set
     */
    public static <T> Set<T> asSet(Iterable<?> sourceObjects, Class<T> targetClass) {
        if (sourceObjects == null) {
            return Collections.emptySet();
        }
        return MAPPER.mapAsSet(sourceObjects, targetClass);
    }
}

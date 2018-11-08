package com.util.msf.rpc.common.serializer;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * Created by wencheng on 2018/11/8.
 */
public class ModuleFactory {
    /**
     * 定制前端序列化，防止丢失精度，将Long类型转换为字符串
     *
     * @return
     */
    public static SimpleModule createFrontend() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        module.addSerializer(Long.class, ToStringSerializer.instance);
        return module;
    }
}

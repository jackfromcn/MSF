package com.msf.fuxi.xdiamond.init;

import com.msf.fuxi.xdiamond.annotation.XDiamondValue;
import com.msf.fuxi.xdiamond.client.XDiamondConfig;
import com.msf.fuxi.xdiamond.client.spring.XDiamondConfigFactoryBean;
import com.msf.fuxi.xdiamond.event.BeanFieldEvent;
import com.msf.fuxi.xdiamond.event.FieldEvent;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class XDiamondValueInit implements ApplicationListener<ContextRefreshedEvent> {

    Logger logger = org.slf4j.LoggerFactory.getLogger(XDiamondValueInit.class);

    @Autowired
    XDiamondConfigFactoryBean xDiamondConfig;

    public static Map<String, String> map = new ConcurrentHashMap<String, String>();


    @SuppressWarnings(value = { "all" })
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("==============begin initializing the Xdiamond configuration======================");
        XDiamondConfig x;
        try {
            x = xDiamondConfig.getObject();
        } catch (Exception e) {
            logger.error("get XDiamondConfig failed", e);
            return;
        }
        Properties p = x.getProperties();
        Set<Object> set = p.keySet();
        for (Object obj : set) {
            map.put(String.valueOf(obj), p.getProperty(obj.toString()));
            logger.info("XDiamond Properties is,{}={}", obj, p.getProperty(obj.toString()));
        }
        Map<String, List<FieldEvent>> oneKeyListenerMap = BeanFieldEvent.oneKeyListenerMap;
        for (String key : oneKeyListenerMap.keySet()) {
            List<FieldEvent> list = oneKeyListenerMap.get(key);
            for (FieldEvent fieldEvent : list) {
                String xDiamondValue = map.get(key);
                Field field = fieldEvent.getField();
                try {
                    field.setAccessible(true);
                    if (StringUtils.isEmpty(xDiamondValue)) {
                        logger.info("get key {} from XDiamond is null", key);
                        Annotation annotation = field.getAnnotation(XDiamondValue.class);
                        Method defaultValueMethod = annotation.getClass().getDeclaredMethod("defaultValue", null);
                        String defaultValue = (String) defaultValueMethod.invoke(annotation, null);
                        Object value = ConvertUtils.convert(defaultValue, field.getType());
                        field.set(fieldEvent.getBean(), value);
                    } else {
                        Object value = ConvertUtils.convert(xDiamondValue, field.getType());
                        field.set(fieldEvent.getBean(), value);
                    }
                } catch (Exception e) {
                    logger.error("init XDiamondValue failed in class {},key->{},value->{}", key, xDiamondValue, fieldEvent.getBean().getClass().getName(), e);
                }
            }
        }
        logger.info("==============Initialize Xdiamond configuration is complete======================");

    }
}

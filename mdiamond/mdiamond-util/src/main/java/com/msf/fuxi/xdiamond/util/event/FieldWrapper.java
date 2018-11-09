package com.msf.fuxi.xdiamond.util.event;

import com.msf.fuxi.xdiamond.client.XDiamondConfig;
import com.msf.fuxi.xdiamond.client.event.ConfigEvent;
import com.msf.fuxi.xdiamond.client.event.EventType;
import com.msf.fuxi.xdiamond.client.event.OneKeyListener;
import com.msf.fuxi.xdiamond.util.annotation.MDiamondValue;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author liujianchao
 */
public class FieldWrapper {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(FieldWrapper.class);

    private Object bean;

    private Field field;

    private String key;

    private XDiamondConfig xDiamondConfig;

    public FieldWrapper(Object bean, Field field, String key, XDiamondConfig xDiamondConfig) {
        this.bean = bean;
        this.field = field;
        this.key = key;
        this.xDiamondConfig = xDiamondConfig;
    }

    public void init(){
        xDiamondConfig.addOneKeyListener(key, new OneKeyListener() {
            @Override
            public void onConfigEvent(ConfigEvent event) {
                LOGGER.info("Class: {}, field: {}, eventType: {}, MDiamondValue: { 'key': {}, 'value': {}, 'oldvalue': {}}.",
                        bean.getClass().getName(), field.getName(), event.getEventType(), event.getKey(), event.getValue(), event.getOldValue());

                if (event.getEventType() == EventType.UPDATE || event.getEventType() == EventType.ADD) {
                    field.setAccessible(true);
                    try {
                        Object value = ConvertUtils.convert(event.getValue(), field.getType());
                        field.set(bean, value);
                    } catch (Exception e) {
                        LOGGER.error("Modify MDiamondValue failed, class: {}, fieldName: {}, " +
                                        "MDiamondValue: { 'key': {}, 'value': {}, 'oldvalue': {}}",
                                bean.getClass().getName(), field.getName(), event.getKey(),
                                event.getValue(), event.getOldValue(), e);
                    }
                } else {
                    field.setAccessible(true);
                    try {
                        MDiamondValue mDiamondValueAnnotation = field.getAnnotation(MDiamondValue.class);
                        String defaultValue = mDiamondValueAnnotation.defaultValue();
                        field.set(bean, ConvertUtils.convert(defaultValue, field.getType()));

                    } catch (Exception e) {
                        LOGGER.error("Modify MDiamondValue failed, class: {}, fieldName: {}, " +
                                        "MDiamondValue: { 'key': {}, 'value': {}, 'oldvalue': {}}",
                                bean.getClass().getName(), field.getName(), event.getKey(),
                                event.getValue(), event.getOldValue(), e);
                    }
                }
            }
        });
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public XDiamondConfig getxDiamondConfig() {
        return xDiamondConfig;
    }

    public void setxDiamondConfig(XDiamondConfig xDiamondConfig) {
        this.xDiamondConfig = xDiamondConfig;
    }
}

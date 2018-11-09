package com.msf.fuxi.xdiamond.listener;

import com.msf.fuxi.xdiamond.annotation.XDiamondValue;
import com.msf.fuxi.xdiamond.client.annotation.AllKeyListener;
import com.msf.fuxi.xdiamond.client.event.ConfigEvent;
import com.msf.fuxi.xdiamond.client.event.EventType;
import com.msf.fuxi.xdiamond.event.BeanFieldEvent;
import com.msf.fuxi.xdiamond.event.FieldEvent;
import com.msf.fuxi.xdiamond.init.XDiamondValueInit;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class XDiamondListenerService {

    Logger logger = org.slf4j.LoggerFactory.getLogger(XDiamondListenerService.class);


    @AllKeyListener
    @SuppressWarnings(value = { "all" })
    public void allKeyListener(ConfigEvent event) {
        logger.info(event.getEventType() + " XDiamondValue,key->{},value->{},oldvalue->{}", event.getKey(), event.getValue(), event.getOldValue());
        List<FieldEvent> list = BeanFieldEvent.oneKeyListenerMap.get(event.getKey());
        if (event.getEventType() == EventType.UPDATE || event.getEventType() == EventType.ADD) {
            XDiamondValueInit.map.put(event.getKey(), event.getValue());
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    FieldEvent fieldEvent = list.get(i);
                    Field field = fieldEvent.getField();
                    field.setAccessible(true);
                    try {
                        Object value = ConvertUtils.convert(event.getValue(), field.getType());
                        field.set(fieldEvent.getBean(), value);
                    } catch (Exception e) {
                        logger.error("modify XDiamondValue failed,class->{},key->{},value->{},oldvalue->{}", fieldEvent.getBean().getClass().getName(),
                                event.getKey(), event.getValue(), event.getOldValue(), e);
                    }
                }
            }
        } else {
            XDiamondValueInit.map.remove(event.getKey());
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    FieldEvent fieldEvent = list.get(i);
                    Field field = fieldEvent.getField();
                    field.setAccessible(true);
                    try {
                        Annotation annotation = field.getAnnotation(XDiamondValue.class);
                        Method defaultValueMethod = annotation.getClass().getDeclaredMethod("defaultValue", null);
                        String defaultValue = (String) defaultValueMethod.invoke(annotation, null);
                        Object value = ConvertUtils.convert(defaultValue, field.getType());
                        field.set(fieldEvent.getBean(), value);
                    } catch (Exception e) {
                        logger.error("delete XDiamondValue failed,class->{},key->{},value->{},oldvalue->{}", fieldEvent.getBean().getClass().getName(),
                                event.getKey(), event.getValue(), event.getOldValue(), e);
                    }
                }
            }
        }
    }
}

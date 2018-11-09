package com.msf.fuxi.xdiamond.event;

import java.lang.reflect.Field;


public class FieldEvent {

    private Object bean;

    private Field field;


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


}

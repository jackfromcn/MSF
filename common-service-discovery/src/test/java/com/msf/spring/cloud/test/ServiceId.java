package com.msf.spring.cloud.test;


import com.msf.spring.cloud.feign.Servicable;

/**
 * @author wencheng
 * @since 2018/5/18
 */
public enum ServiceId implements Servicable {
    CONFIG_CENTER {
        @Override
        public String id() {
            return "CONFIG-CENTER";
        }
    };


    @Override
    public String id() {
        return null;
    }
}

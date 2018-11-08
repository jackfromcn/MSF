package com.util.msf.common.monitor;

import java.io.Serializable;

/**
 * @author wencheng
 * @ClassName DataCollectorLabel
 * @Description 数据采集标签项
 * @date 2017-11-02 上午11:23
 */
public class DataCollectorLabel implements Serializable {
    /**
     * 应用名
     */
    private String app;

    /**
     * 实例
     */
    private String instance;

    /**
     * 环境
     */
    private String env;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }


    @Override
    public String toString() {
        return "DataCollectorLabel{" +
                "app='" + app + '\'' +
                ", instance='" + instance + '\'' +
                ", env='" + env + '\'' +
                '}';
    }

    public DataCollectorLabel() {
    }

    public DataCollectorLabel(String app, String instance, String env) {
        this.app = app;
        this.instance = instance;
        this.env = env;
    }
}

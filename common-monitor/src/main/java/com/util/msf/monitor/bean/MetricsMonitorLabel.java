package com.util.msf.monitor.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * @author wencheng
 * @ClassName MetricsLabel
 * @Description 数据采集标签项
 * @date 2017-11-02 上午11:23
 */
@Data
public class MetricsMonitorLabel implements Serializable {
    /**
     * 应用名
     */
    @Value("${spring.application.name}")
    private String app;

    /**
     * 实例
     */
    @Value("${spring.cloud.client.ipAddress}:${server.port}")
    private String instance;

    /**
     * 环境
     */
    @Value("${spring.profiles.active:unknown}")
    private String env;

    @Override
    public String toString() {
        return "MetricsLabel{" +
                "app='" + app + '\'' +
                ", instance='" + instance + '\'' +
                ", env='" + env + '\'' +
                '}';
    }

    public MetricsMonitorLabel() {
    }

    public MetricsMonitorLabel(String app, String instance, String env) {
        this.app = app;
        this.instance = instance;
        this.env = env;
    }
}

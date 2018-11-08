package com.util.msf.monitor.util;

import io.micrometer.core.instrument.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author wencheng
 * @ClassName MetricsFactory
 * @Description 度量指标工厂类
 * @date 2018-01-09 上午10:57
 */
public class MetricsFactory {
    private static MeterRegistry meterRegistry;

    private static final String CLASS_NAME = "className";

    private static final String METHOD_NAME = "methodName";

    private static final String CODE = "code";

    private static final String TYPE = "type";

    private static final String URL = "url";

    private static final String TOPIC = "topic";

    private static final String DESC = "desc";

    private static AtomicLong httpCost = new AtomicLong();

    private MetricsFactory() {
    }

    private MetricsFactory(MeterRegistry meterRegistry) {
        MetricsFactory.meterRegistry = meterRegistry;
    }

    public static MetricsFactory init(MeterRegistry meterRegistry) {
        return new MetricsFactory(meterRegistry);
    }


    /**
     * 设置http请求耗时 lease_http_millis
     *
     * @param cost 耗时
     */
    public static void buildHttpCost(long cost) {
        httpCost.set(cost);
        Gauge.builder("lease.http.millis", httpCost, AtomicLong::get)
                .description("http请求耗时统计")
                .register(meterRegistry);
    }


    /**
     * @param code       响应码
     * @param className  类名
     * @param methodName 方法名
     * @return 响应码指标 lease_code_total
     */
    public static Counter buildCodeCounter(String code, String className, String methodName) {
        return Counter.builder("lease.code").description("响应码统计")
                .tags(Arrays.asList(Tag.of(CODE, code),
                        Tag.of(CLASS_NAME, className), Tag.of(METHOD_NAME, methodName)))
                .register(meterRegistry);
    }

    /**
     * @param typeName   类型
     * @param desc       描述
     * @param className  类名
     * @param methodName 方法名
     * @param url        url
     * @param topic      topic
     * @return 请求计数指标 lease_request_duration_seconds_count、lease_request_duration_seconds_sum、lease_request_duration_seconds_max
     */
    public static Timer buildRequestTimer(String typeName, String desc, String className, String methodName, String url, String topic) {
        Timer.Builder builder = Timer.builder("lease.request").description("请求耗时统计");
        return builder.tags(Arrays.asList(Tag.of(TYPE, typeName), Tag.of(DESC, desc), Tag.of(CLASS_NAME, className), Tag.of(METHOD_NAME, methodName),
                Tag.of(URL, url), Tag.of(TOPIC, topic)))
                .register(meterRegistry);
    }

    /**
     * @param typeName   类型
     * @param desc       描述
     * @param className  类名
     * @param methodName 方法名
     * @param url        url
     * @param topic      topic
     * @return 失败计数指标 lease_fail_total
     */
    public static Counter buildFailCounter(String typeName, String desc, String className, String methodName, String url, String topic) {
        Counter.Builder builder = Counter.builder("lease.fail").description("请求失败统计");
        return builder.tags(Arrays.asList(Tag.of(TYPE, typeName), Tag.of(DESC, desc), Tag.of(CLASS_NAME, className), Tag.of(METHOD_NAME, methodName),
                Tag.of(URL, url), Tag.of(TOPIC, topic)))
                .register(meterRegistry);
    }
}

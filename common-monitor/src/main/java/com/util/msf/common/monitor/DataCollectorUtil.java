package com.util.msf.common.monitor;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;

/**
 * @author wencheng
 * @ClassName DataCollectorUtil
 * @Description 度量指标工具类
 * @date 2017-11-02 下午12:23
 */
public class DataCollectorUtil {
    private static final String NAMESPACE = "lease";

    private static final String[] HTTP_LABEL_NAMES = {"app", "app_instance", "env", "identifyKey", "url", "remoteIp"};

    private static final String[] CODE_LABEL_NAMES = {"app", "app_instance", "env", "identifyKey", "code", "className", "methodName"};

    private static final String[] RPC_LABEL_NAMES = {"app", "app_instance", "env", "identifyKey", "type", "url", "className", "methodName", "topic"};

    static final Gauge HTTP_REQUEST_COST_SECONDS = Gauge.build().namespace(NAMESPACE)
            .name("http_request_cost_seconds")
            .help("http请求耗时(Gauge)")
            .labelNames(HTTP_LABEL_NAMES)
            .register();

    static final Histogram HTTP_REQUEST_DURATION_SECONDS = Histogram.build().namespace(NAMESPACE)
            .name("http_request_duration_seconds")
            .help("http请求耗时(Histogram)")
            .labelNames(HTTP_LABEL_NAMES)
            .register();

    static final Summary HTTP_REQUEST_DURATION_SECONDS1 = Summary.build().namespace(NAMESPACE)
            .name("http_request_duration_seconds")
            .help("http请求耗时(Histogram)")
            .labelNames(HTTP_LABEL_NAMES)
            .register();

    static final Counter HTTP_REQUEST_EXCEPTION_COUNT = Counter.build().namespace(NAMESPACE)
            .name("http_request_exception_count")
            .help("http请求异常次数统计(Counter)")
            .labelNames(HTTP_LABEL_NAMES)
            .register();


    static final Counter RESPONSE_CODE_COUNT = Counter.build().namespace(NAMESPACE)
            .name("response_code_count")
            .help("响应码计数(Counter)")
            .labelNames(CODE_LABEL_NAMES)
            .register();

    static final Gauge RPC_REQUEST_COST_SECONDS = Gauge.build().namespace(NAMESPACE)
            .name("rpc_request_cost_seconds")
            .help("rpc请求耗时(Gauge)")
            .labelNames(RPC_LABEL_NAMES)
            .register();

    static final Histogram RPC_REQUEST_DURATION_SECONDS = Histogram.build().namespace(NAMESPACE)
            .name("rpc_request_duration_seconds")
            .help("rpc请求耗时(Histogram)")
            .labelNames(RPC_LABEL_NAMES)
            .register();

    static final Counter RPC_REQUEST_FAIL_COUNT = Counter.build().namespace(NAMESPACE)
            .name("rpc_request_fail_count")
            .help("rpc失败请求计数(Counter)")
            .labelNames(RPC_LABEL_NAMES)
            .register();


    static final Gauge RPC_MQ_SEND_RECEIVE_STA = Gauge.build().namespace(NAMESPACE)
            .name("rpc_mq_send_receive")
            .help("mq收发统计(Gauge)")
            .labelNames(RPC_LABEL_NAMES)
            .register();

}

//package com.util.msf.monitor.interceptor;
//
//import SpELUtils;
//import MetricsMonitor;
//import MetricsMonitorEnum;
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.Tag;
//import io.micrometer.core.instrument.Timer;
//import lombok.Data;
//import lombok.experimental.Accessors;
//import lombok.extern.slf4j.Slf4j;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.apache.commons.lang3.StringUtils;
//
//import java.lang.reflect.Method;
//import java.util.Arrays;
//import java.util.concurrent.TimeUnit;
//
//
///**
// * @author wencheng
// * @ClassName MetricsMonitorRpcInterceptor
// * @Description 数据采集拦截器
// * @date 2017-11-02 上午11:13
// */
////@Component("metricsRpcInterceptor")
////@ConditionalOnBean(MeterRegistry.class)
//@Data
//@Accessors(chain = true)
//@Slf4j
//public class MetricsMonitorRpcInterceptor implements MethodInterceptor {
//
//    private MeterRegistry meterRegistry;
//
//
//    private static final String TYPE = "type";
//
//    private static final String URL = "url";
//
//    private static final String TOPIC = "topic";
//
//    private static final String DESC = "desc";
//
//    private static final String CLASS_NAME = "className";
//
//    private static final String METHOD_NAME = "methodName";
//
//    public MetricsMonitorRpcInterceptor() {
//    }
//
//    public MetricsMonitorRpcInterceptor(MeterRegistry meterRegistry) {
//        this.meterRegistry = meterRegistry;
//    }
//
//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        long oldMillis = System.currentTimeMillis();
//        MetricsMonitor metrics = invocation.getMethod().getAnnotation(MetricsMonitor.class);
//        metrics = metrics == null ? invocation.getMethod().getDeclaringClass().getAnnotation(MetricsMonitor.class) : metrics;
//        if (metrics == null) {
//            return invocation.proceed();
//        }
//
//        Object[] args = invocation.getArguments();
//        Method method = invocation.getMethod();
//        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
//        String methodName = className + "." + invocation.getMethod().getName();
//
//        Object result;
//        MetricsMonitorEnum typeEnum = metrics.type();
//        String typeName = typeEnum.name();
//        String desc = metrics.desc();
//
//        String url = getSpelValue(metrics.url(), method, args);
//        String topic = getSpelValue(metrics.topic(), method, args);
//        if ((MetricsMonitorEnum.KAFKA_CONSUME.equals(typeEnum) || MetricsMonitorEnum.KAFKA_PRODUCE.equals(typeEnum))
//                && StringUtils.isBlank(topic)) {
//            log.error("数据采集,{}类型采集参数topic为空,methodName={},topic={}", typeEnum, methodName, topic);
//            throw new IllegalAccessException(typeEnum.name() + "类型采集参数topic不能为空");
//        }
//        if (MetricsMonitorEnum.HTTP.equals(typeEnum) && StringUtils.isBlank(url)) {
//            log.error("数据采集,{}类型采集参数url为空,methodName={},url={}", typeEnum, methodName, url);
//            throw new IllegalAccessException(typeEnum.name() + "类型采集参数url不能为空");
//        }
//        try {
//            result = invocation.proceed();
//            long costMillis = System.currentTimeMillis() - oldMillis;
//            buildRpcRequest(typeName, desc, className, methodName, url, topic).record(costMillis, TimeUnit.MILLISECONDS);
//            log.debug("数据采集,type={},url={},耗时={}", typeName, url, costMillis);
//        } catch (Throwable t) {
//            Counter counter = buildRpcFail(typeName, desc, className, methodName, url, topic);
//            counter.increment();
//            log.debug("数据采集,type={},url={},异常次数={}", typeName, url, counter.count());
//            throw t;
//        }
//
//        return result;
//    }
//
//    private String getSpelValue(String expression, Method method, Object[] args) {
//        if (StringUtils.isBlank(expression)) {
//            return StringUtils.EMPTY;
//        }
//        if (!expression.startsWith("#")) {
//            return expression;
//        }
//        String str = SpELUtils.parseExpression(expression, method, args, String.class);
//        return str == null ? "" : str;
//    }
//
//    //rpc_fail_total
//    private Timer buildRpcRequest(String typeName, String desc, String className, String methodName, String url, String topic) {
//        return Timer.builder("rpc.request").description("rpc请求耗时")
//                .tags(Arrays.asList(Tag.of(TYPE, typeName), Tag.of(DESC, desc), Tag.of(CLASS_NAME, className), Tag.of(METHOD_NAME, methodName),
//                        Tag.of(URL, url), Tag.of(TOPIC, topic)))
//                .register(meterRegistry);
//    }
//
//    //rpc_request_fail_total
//    private Counter buildRpcFail(String typeName, String desc, String className, String methodName, String url, String topic) {
//        return Counter.builder("rpc.request.fail").description("rpc失败请求计数(Counter)")
//                .tags(Arrays.asList(Tag.of(TYPE, typeName), Tag.of(DESC, desc), Tag.of(CLASS_NAME, className), Tag.of(METHOD_NAME, methodName),
//                        Tag.of(URL, url), Tag.of(TOPIC, topic)))
//                .register(meterRegistry);
//    }
//
////    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsMonitorRpcInterceptor.class);
////
////
////    private Timer.Builder RPC_TIMER_BUILDER = Timer.builder("rpc.request").description("rpc请求耗时");
////
////    /**
////     * rpc_request_fail_total
////     */
////    private Counter.Builder RPC_FAIL_COUNT_BUILDER = Counter.builder("rpc.request.fail").description("rpc失败请求计数(Counter)");
////
////    private MeterRegistry meterRegistry;
////
////    public MetricsMonitorRpcInterceptor() {
////    }
////
////    public MetricsMonitorRpcInterceptor(MeterRegistry meterRegistry) {
////        this.meterRegistry = meterRegistry;
////    }
////
////    @Override
////    public Object invoke(MethodInvocation invocation) throws Throwable {
////        long oldMillis = System.currentTimeMillis();
////        MetricsMonitor metrics = invocation.getMethod().getAnnotation(MetricsMonitor.class);
////        metrics = metrics == null ? invocation.getMethod().getDeclaringClass().getAnnotation(MetricsMonitor.class) : metrics;
////        if (metrics == null) {
////            return invocation.proceed();
////        }
////        Object[] args = invocation.getArguments();
////        Method method = invocation.getMethod();
////        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
////        String methodName = className + "." + invocation.getMethod().getName();
////
////
////        Object result;
////        MetricsMonitorEnum typeEnum = metrics.type();
////        switch (typeEnum) {
////            case EXTERNAL_HTTP:
////            case EXTERNAL_RPC:
////                String typeName = typeEnum.name();
////                String url = getSpelValue(metrics.url(), method, args);
////                if (EXTERNAL_HTTP == typeEnum && StringUtils.isBlank(url)) {
////                    LOGGER.error("{}类型采集参数url为空,methodName={}", typeEnum, methodName);
////                    throw new IllegalAccessException(typeEnum.name() + "类型采集参数url不能为空");
////                }
////
////                try {
////                    result = invocation.proceed();
////                    RPC_TIMER_BUILDER
////                            .tags(Arrays.asList(Tag.of("type", typeName), Tag.of("url", url),
////                                    Tag.of("className", className), Tag.of("methodName", methodName)))
////                            .register(meterRegistry)
////                            .record(System.currentTimeMillis() - oldMillis, TimeUnit.MILLISECONDS);
////                } catch (Throwable t) {
////                    RPC_FAIL_COUNT_BUILDER
////                            .tags(Arrays.asList(Tag.of("type", typeName), Tag.of("url", url),
////                                    Tag.of("className", className), Tag.of("methodName", methodName)))
////                            .register(meterRegistry)
////                            .increment();
////                    throw t;
////                }
////
////                break;
////            default:
////                result = invocation.proceed();
////                break;
////        }
////
////        return result;
////    }
////
////    private String getSpelValue(String expression, Method method, Object[] args) {
////        if (StringUtils.isBlank(expression)) {
////            return StringUtils.EMPTY;
////        }
////        if (!expression.startsWith("#")) {
////            return expression;
////        }
////        String str = SpELUtils.parseExpression(expression, method, args, String.class);
////        return str == null ? "" : str;
////    }
//
//}

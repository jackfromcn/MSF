//package com.util.msf.monitor.interceptor;
//
//import com.util.msf.rpc.common.Result;
//import io.micrometer.core.instrument.Counter;
//import io.micrometer.core.instrument.MeterRegistry;
//import io.micrometer.core.instrument.Tag;
//import lombok.Data;
//import lombok.experimental.Accessors;
//import org.aopalliance.intercept.MethodInterceptor;
//import org.aopalliance.intercept.MethodInvocation;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//
///**
// * @author wencheng
// * @ClassName MetricsMonitorCodeInterceptor
// * @Description 数据采集拦截器
// * @date 2017-11-02 上午11:13
// */
////@Component("metricsInterceptor")
////@ConditionalOnBean(MeterRegistry.class)
//@Data
//@Accessors(chain = true)
//@Slf4j
//public class MetricsMonitorCodeInterceptor implements MethodInterceptor {
//
//    private MeterRegistry meterRegistry;
//
//
//    private static final String CODE = "code";
//
//    private static final String CLASS_NAME = "className";
//
//    private static final String METHOD_NAME = "methodName";
//
//    public MetricsMonitorCodeInterceptor() {
//    }
//
//    public MetricsMonitorCodeInterceptor(MeterRegistry meterRegistry) {
//        this.meterRegistry = meterRegistry;
//    }
//
//    @Override
//    public Object invoke(MethodInvocation invocation) throws Throwable {
//        Object result = invocation.proceed();
//        if (result != null && result instanceof Result) {
//            String className = invocation.getMethod().getDeclaringClass().getSimpleName();
//            String methodName = className + "." + invocation.getMethod().getName();
//            Counter counter = buildResponseCode(((Result) result).getCode() + "", className, methodName);
//            counter.increment();
//            log.debug("数据采集,响应码计数统计(response_code_total),response.code={},counter={}", ((Result) result).getCode(), counter.count());
//        }
//        return result;
//    }
//
//    //response_code_total
//    private Counter buildResponseCode(String code, String className, String methodName) {
//        return Counter.builder("response.code").description("响应码计数统计")
//                .tags(Arrays.asList(Tag.of(CODE, code),
//                        Tag.of(CLASS_NAME, className), Tag.of(METHOD_NAME, methodName)))
//                .register(meterRegistry);
//    }
//
////    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsMonitorCodeInterceptor.class);
////
////    /**
////     * response_code_total
////     */
////    private Counter.Builder CODE_COUNT_BUILDER = Counter.builder("response.code").description("响应码计数统计");
////
////    private MeterRegistry meterRegistry;
////
////    public MetricsMonitorCodeInterceptor() {
////    }
////
////    public MetricsMonitorCodeInterceptor(MeterRegistry meterRegistry) {
////        this.meterRegistry = meterRegistry;
////    }
////
////    @Override
////    public Object invoke(MethodInvocation invocation) throws Throwable {
////        String className = invocation.getMethod().getDeclaringClass().getSimpleName();
////        String methodName = className + "." + invocation.getMethod().getName();
////        Object result;
////        try {
////            result = invocation.proceed();
////            if (result instanceof Result) {
////                if (!((Result) result).isSucceed()) {
////                    try {
////                        CODE_COUNT_BUILDER
////                                .tags(Arrays.asList(Tag.of("code", ((Result) result).getCode() + ""),
////                                        Tag.of("className", className), Tag.of("methodName", methodName)))
////                                .register(meterRegistry)
////                                .increment();
////                    } catch (Exception e) {
////                        LOGGER.error("响应码计数统计异常", e);
////                    }
////                }
////            }
////        } catch (Throwable throwable) {
////            throw throwable;
////        }
////
////        return result;
////    }
//
//
//}
